#!/usr/bin/env python3
"""
CurseForge upload script for Minecraft mods using RetroFuturaGradle/CleanroomMC template.

Reads all configuration from gradle.properties automatically.
Designed to be copy-pasted between mod projects.

Usage:
    # Upload the latest built jar (reads all config from gradle.properties)
    python scripts/upload_curseforge.py

    # Upload a specific jar
    python scripts/upload_curseforge.py --file build/libs/MyMod-1.0.0.jar

    # Dry run (only print what would be uploaded)
    python scripts/upload_curseforge.py --dry-run

Requires:
    - curl_cffi library (pip install curl_cffi)
    - CURSEFORGE_TOKEN environment variable set
"""

import argparse
import json
import os
import re
import sys
from pathlib import Path

from curl_cffi import requests, CurlMime
import tempfile


# --- Configuration ---
# API endpoint
API_ENDPOINT = "https://legacy.curseforge.com/api"

# Game version IDs for Minecraft 1.12.2, Forge, Java 8
# These are the string names (the API also accepts names via gameVersionNames)
GAME_VERSIONS = ["1.12.2", "Forge", "Java 8"]
ENVIRONMENTS = ["Client", "Server"]

# Paths
SCRIPT_DIR = Path(__file__).resolve().parent
PROJECT_DIR = SCRIPT_DIR.parent
GRADLE_PROPERTIES = PROJECT_DIR / "gradle.properties"
CHANGELOG_FILE = PROJECT_DIR / "CHANGELOG.md"
BUILD_LIBS_DIR = PROJECT_DIR / "build" / "libs"


def parse_gradle_properties(filepath: Path) -> dict:
    """Parse gradle.properties into a dict."""
    props = {}
    if not filepath.exists():
        return props
    with open(filepath, "r", encoding="utf-8") as f:
        for line in f:
            line = line.strip()
            if not line or line.startswith("#") or "=" not in line:
                continue
            # Handle Gradle template expressions like ${{ ... }}
            if "${{" in line or "${" in line:
                continue
            key, _, value = line.partition("=")
            props[key.strip()] = value.strip()
    return props


def get_changelog_for_version(version: str) -> str:
    """Extract changelog for a specific version from CHANGELOG.md."""
    if not CHANGELOG_FILE.exists():
        return ""
    with open(CHANGELOG_FILE, "r", encoding="utf-8") as f:
        content = f.read()

    # Find the section for this version
    pattern = rf"##\s*{re.escape(version)}\b(.*?)(?=##\s|\Z)"
    match = re.search(pattern, content, re.DOTALL)
    if match:
        return match.group(1).strip()
    return ""


def find_jar_file(mod_id: str, version: str, dev_suffix: bool = False) -> Path:
    """Find the built jar file for the given version."""
    if dev_suffix:
        jar_name = f"{mod_id}-{version}-dev.jar"
    else:
        jar_name = f"{mod_id}-{version}.jar"

    jar_path = BUILD_LIBS_DIR / jar_name
    if jar_path.exists():
        return jar_path

    # Try to find any matching jar
    pattern = re.compile(rf"{re.escape(mod_id)}-{re.escape(version)}.*\.jar$")
    for f in BUILD_LIBS_DIR.glob("*.jar"):
        if pattern.match(f.name):
            return f

    return None


def upload_to_curseforge(
    api_token: str,
    project_id: str,
    file_path: Path,
    release_type: str,
    changelog: str,
    changelog_type: str = "markdown",
    display_name: str = None,
    dry_run: bool = False,
):
    """Upload a file to CurseForge using curl_cffi (bypasses Cloudflare TLS fingerprinting)."""
    metadata = {
        "releaseType": release_type,
        "gameVersionNames": GAME_VERSIONS + ENVIRONMENTS,
        "changelog": changelog,
        "changelogType": changelog_type,
    }
    if display_name:
        metadata["displayName"] = display_name

    if dry_run:
        print("=== DRY RUN ===")
        print(f"Project ID: {project_id}")
        print(f"File: {file_path}")
        print(f"File size: {file_path.stat().st_size} bytes")
        print(f"Release type: {release_type}")
        print(f"Game versions: {GAME_VERSIONS + ENVIRONMENTS}")
        print(f"Display name: {display_name}")
        print(f"Changelog ({changelog_type}):")
        print("-" * 40)
        print(changelog if changelog else "(empty)")
        print("-" * 40)
        print("Metadata JSON:")
        print(json.dumps(metadata, indent=2))
        print("=== END DRY RUN ===")
        return True

    url = f"{API_ENDPOINT}/projects/{project_id}/upload-file"
    metadata_json = json.dumps(metadata)

    print(f"Uploading to: {url}")
    print(f"File: {file_path.name} ({file_path.stat().st_size / 1024:.1f} KB)")

    # curl_cffi requires CurlMime object for multipart uploads
    with open(file_path, "rb") as f:
        file_data = f.read()
    mp = CurlMime()
    mp.addpart(
        name="metadata",
        content_type="application/json",
        data=metadata_json.encode(),
    )
    mp.addpart(
        name="file",
        content_type="application/java-archive",
        filename=file_path.name,
        data=file_data,
    )
    headers = {
        "X-Api-Token": api_token,
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36",
    }
    response = requests.post(url, headers=headers, multipart=mp, impersonate="chrome")
    mp.close()

    if response.status_code == 200:
        result = response.json()
        file_id = result.get("id", "unknown")
        print(f"✅ Upload successful! File ID: {file_id}")
        return True
    else:
        print(f"❌ Upload failed (HTTP {response.status_code}):")
        try:
            error = response.json()
            print(json.dumps(error, indent=2))
        except json.JSONDecodeError:
            print(response.text[:2000])
        return False


def main():
    parser = argparse.ArgumentParser(description="Upload Unconfusion mod to CurseForge")
    parser.add_argument(
        "--file", "-f",
        type=str,
        help="Path to the jar file to upload (default: auto-detect from gradle.properties)",
    )
    parser.add_argument(
        "--project-id",
        type=str,
        default=None,
        help="CurseForge project ID (default: read from gradle.properties)",
    )
    parser.add_argument(
        "--release-type",
        type=str,
        choices=["alpha", "beta", "release"],
        default=None,
        help="Release type (default: read from gradle.properties)",
    )
    parser.add_argument(
        "--dry-run",
        action="store_true",
        help="Print what would be uploaded without actually uploading",
    )
    parser.add_argument(
        "--dev",
        action="store_true",
        help="Upload the -dev.jar variant",
    )
    args = parser.parse_args()

    # Get API token from environment
    api_token = os.environ.get("CURSEFORGE_TOKEN")
    if not api_token and not args.dry_run:
        print("Error: CURSEFORGE_TOKEN environment variable is not set!")
        print("Set it with: export CURSEFORGE_TOKEN='your_token_here'")
        sys.exit(1)

    # Parse gradle.properties for all configurable values
    props = parse_gradle_properties(GRADLE_PROPERTIES)
    mod_version = props.get("mod_version", "")
    mod_name = props.get("mod_name", "")
    mod_id = props.get("mod_id", "")
    curseforge_project_id = props.get("curseforge_project_id", "")
    release_type = args.release_type or props.get("release_type", "release")

    if not mod_id:
        print("Error: Could not read mod_id from gradle.properties")
        sys.exit(1)
    if not mod_name:
        print("Error: Could not read mod_name from gradle.properties")
        sys.exit(1)
    if not mod_version:
        print("Error: Could not read mod_version from gradle.properties")
        sys.exit(1)
    if not curseforge_project_id:
        print("Error: Could not read curseforge_project_id from gradle.properties")
        sys.exit(1)

    print(f"Mod: {mod_name} ({mod_id})")
    print(f"Version: {mod_version}")
    print(f"CurseForge Project ID: {curseforge_project_id}")
    print(f"Release type: {release_type}")

    # Find the jar file
    if args.file:
        jar_path = Path(args.file)
        if not jar_path.exists():
            print(f"Error: File not found: {jar_path}")
            sys.exit(1)
    else:
        jar_path = find_jar_file(mod_id, mod_version, dev_suffix=args.dev)
        if not jar_path:
            print(f"Error: Could not find jar for {mod_id}-{mod_version} in {BUILD_LIBS_DIR}")
            print("Build the project first or specify --file")
            sys.exit(1)

    # Get changelog
    changelog = get_changelog_for_version(mod_version)
    if not changelog:
        print("Warning: No changelog found for this version")

    # Display name
    display_name = f"{mod_name} {mod_version}"

    # Use project ID from gradle.properties, allow override via --project-id
    project_id = args.project_id or curseforge_project_id

    # Upload
    success = upload_to_curseforge(
        api_token=api_token,
        project_id=project_id,
        file_path=jar_path,
        release_type=release_type,
        changelog=changelog,
        changelog_type="markdown",
        display_name=display_name,
        dry_run=args.dry_run,
    )

    sys.exit(0 if success else 1)


if __name__ == "__main__":
    main()
