# MTE Patches
Fix bugs, add useful patches, and maybe some optimizations for various mods.  
Made for the modpack Minetech Evolution. Use freely if you want any fixes in the mod. Most fixes are toggleable via config. 
This mod works outside of the modpack as well.  
## Current patches:  
### Railcraft
#### Multiblock Desync fix  
  Fix a desync bug of multiblock that when the multiblock is across chunks.
  This can be easily reproduced by building a multiblock tank across 4 chunks, forceload one chunk of them, walk away from the tank, quit and rejoin world, then walk back slowly.  
  When desync, client can get its inventory scrambled when right-clicking at the bugged multiblock.  
  RC 12.1.0-beta8 doesn't need this fix. Other fixes may still work.  
#### Free Turbine Repair fix  
  Fix turbine being fixed for free when putting in crafting slot with a blade and take it out.
#### Outfitted Track Drops fix
Fix outfitted tracks drops only the kit but not the track when destroyed.  
#### Replace Outfitted Track
Allow track relayer to replace outfitted track.  
#### Item Loader/Unloader Optimization
Optimize item loader/unloaders by moving many items at an interval, instead of moving one item every tick  
#### Tile Cache Purging
Tile caches can miss some updates occuring in neighbouring tileentities, like multiblock formation.  
This patch adds an interval(in ticks) to purge the cache.  
#### Charge Network Fix[EXPERIMENTAL]
Charge Network in RC is very buggy. This fix improves its stability by a lot, but it's still a bit buggy yet.  

### IndustrialCraft 2
#### Mass Fabricator Stuck  
Mass fabricator stuck forever when its output is blocked once. This mod can fix this.  
#### Crops and Farmlands  
This is a enhancement for IC2, allowing crop sticks to be put on other mod's farmlands, as long as it extends from BlockFarmland.  
#### Crop Trample Prevention
Prevent breaking crop sticks when walking over it  
#### Block Keybinds in GUI  
Prevent IC2 keybinds in GUIs. Fixes jetpack being activated when pressing jump with the inventory open.  
#### LAN message fix
Fixes a bug that on a LAN server, all IC2 messages are sent to the host, instead of the player that should receive the message.  
#### Scale Generator Buffers
Increase buffer size of generators that had very small buffers with energy generation factors in config, to prevent output capping when a large factor is set.

### Thermal Series
#### Fuzzy NBT Recycling  
Enhancement for Thermal Expansion, allows recycling enchanted items with induction smelter and sawmill.  

### Forestry  
#### Faulty Queen Bee Fix  
Prevent crashing when placing a faulty queen bee into apiary  
#### MultiFarm Soil Replacement Fix
Fix multi-farm does not return dirt or sand when replacing soil  
#### BuildCraft Compat Fix
Fix Forestry's BuildCraft compat modules not working with BC8.0.  

### RFTools
#### [Client] Modular Storage GUI Tweaks  
- Shift Tweak  

  When pulling out items with shift, don't refresh the list until the mouse is released.  Improves compatibility with MouseTweaks.  

- Insertion Tweak  

  When putting your held item into the storage, prevent swapping the hovered item out from the storage.

### BuildCraft
#### Item Pipe Accepts Ejection
Allows BuildCraft item pipes to accept items ejected from vanilla hoppers and machines from other mods.  
#### RF/FE Compatibility
(Disabled by default) Simply make BuildCraft works with RF/FE.  
#### Enforce Power Limits
(Disabled by default) Apply the transfer rate limits to kinesis pipes. Also limits how much power can be stored in it.  

### Moar Tinkers
#### Disable Mining Boost when Sneaking
A QoL Feature, disables server-side mining speed boost of traits when sneaking, to prevent over-mining with it  

### Industrial Foregoing
#### Insertion Conveyor Upgrade Duplication Fix
Fixed a item duplication bug for insertion conveyor upgrade.  
#### Meat Feeder Null Handler Check
Do null check before meat feeder drains fluid from containers. Prevents potential NullPointerException

### ProjectRed
#### Breaking Speed Fix
Fix breaking speed for machines and parts on ProjectRed. Now their breaking speed are affected by player's tool and effects.  
#### Pressure Tube Item Limit
Items can cram up in ProjectRed's pressure tubes under some conditions.  
With this feature, pressure tubes with item stacks more than this value will be considered unpassable when pathfinding, so items won't cram up infinitely and cause huge lags.  
#### Machine inventory size fix
Fix inventory sizes of certain machines to fix related crashes.  

### Storage Drawers
#### Clear empty slot
QoL feature: Unmark an empty slot from an formerly-locked drawer with a left-click.
#### Trim Pick Block Patch
Fixes pick block on trims from storage drawers and storage drawers extra.  
### Storage Drawers Extras
#### Extra trim subtypes
Fixes silk touch drops for Extra Trims
#### Localization key fix
Fix unlocalized keys in trim tooltips

### Biomes o' Plenty
#### [Client] Fog performance fix
Improves FPS by reducing looped calculations on every frame in BOP's event listener.  

### Steve's Carts Reborn
#### More Bridger Blocks
Allows all solid, opaque, non-falling blocks to be used in bridger module.  

### IC2 crop breeding plugin
#### Calculator GUI fix
Fix wrong items appearing in crop calculator GUI on inventory update.  

### Vanilla
#### [Client] Don't receive item entities when dead
Stops receiving new item entities on client when player is dead. Item entities doesn't remove correctly on client-side when player is dead, so they can get more and more over time, causing huge lags.  
#### Falling block dupe glitch
(Disabled by default) Stops vanilla falling block duplication with end portal, by blocking falling block entities from being teleported by the portal.  

### Connection Timeout
Add a timeout when it's retrieving info for some mods, prevent it from freezing the loading progress forever on a lossy internet connection.  
Supported mods:  
- Biomes o' Plenty  
- Industrial Foregoing  

### Render Culling
Skips rendering of certain complex things of some tileentities, when:
- It's far away from the player (distance configurable)
- It's rendering shadows for shaders.  

Including:
- Buildcraft(Items, Fluid, Energy in pipes)
- Forestry(Fluid in machines)
- Thermal Dynamics(Items/Fluid in ducts)
- Project Red(Items in pipes)
- Any custom TileEntity with a special renderer

Culled TileEntity types can be configured separately for far and shadow.