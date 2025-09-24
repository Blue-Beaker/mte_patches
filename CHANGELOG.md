## 1.0
initial release

## 1.1.0
Added initial patches for IC2

## 1.2.0
Added tweaks for Thermal Expansion:  
- Fuzzy NBT for recycling recipes

## 1.3.0
Added patch for Forestry:  
- Prevent crashing when placing a faulty queen bee into apiary  

## 1.3.1
Added tweak for IC2:
- Prevent IC2 keybinds in GUIs. Fixes jetpack being activated when pressing jump with the inventory open.  

## 1.3.2
Added tweaks for the RFTools Modular Storage GUI:  
- Shift Tweak  
- Insertion Tweak  

Added tweaks for Biomes O Plenty:  
- Connection Timeout: Add a timeout when it's retrieving trail info, prevent it from sticking the loading progress forever on a lossy internet connection.  

## 1.3.3
Added tweaks for BuildCraft:  
- Item Pipe Accepts Ejection

## 1.3.4
Added tweaks for Moar Tinkers:
- Disable Mining Boost when Sneaking

## 1.3.5
Fixed RFTools Insertion Tweak inserting items to storage on slots in player's inventory

## 1.3.6
Fixed Industrial Foregoing insertion converyor upgrade duplicating items  

## 1.3.7
Fixed a null pointer crash of BC Item Pipe tweak in this mod   

## 1.3.8
Fix IC2 LAN message bug  

## 1.3.9
Added "Connection Timeout" tweak for industrial foregoing as well  
Connection Timeout in config is now moved to a new category  
When used on server, no longer require this mod on clients (It's still suggested to install on client as well)  

## 1.4.0
Added tweaks for BuildCraft:  
- RF/FE Compatibility  
- Enforce Power Limits  
Also improved performance of adaptors for BC  

## 1.4.1
Fixed connection timeout feature  

## 1.4.2
Added patch for ProjectRed:
- Breaking Speed Fix

## 1.4.3
Added patch for forestry:  
- MultiFarm Soil Replacement Fix  

## 1.4.4
- Patched IC2 server message causing stack overflow  
- Added cramming limit feature for ProjectRed's pressure tubes

## 1.4.5
- Increased default cramming limit for pressure tubes

## 1.4.6
- QoL feature for Storage Drawers: Unmark empty slot  
- Fix a crash on IC2 message fix  

## 1.4.7
- QoL feature for IC2: Crop Trample Prevention  

## 1.4.8
- Performance patches for Biomes o' Plenty

## 1.4.9
- Patch a potential lag at death screen on client-side for vanilla  

## 1.4.10
- Fix Forestry's BuildCraft compat modules not working with BC8.0.  

## 1.4.11
- Fix IC2 Crop Dirt Detection.  

## 1.4.12
- Fix inventory size of ProjectRed TileFilteredImporter.  

## 1.5.0
Added patches for RailCraft:  
- Fix block drops of outfitted track.  
- Allow track relayer to replace outfitted track.  

## 1.5.1
Added item loader/unloader optimization for RailCraft  

## 1.5.2
Added Tile Cache Purging for RailCraft  
Fix crash with RailCraft 12.1.0-beta. TileMultiBlock patch still not work with that version.  

## 1.5.3
Reduce debug output and make them configurable  

## 1.5.4
Fix the 'fitted track drop' patch causing NullPointerException  

## 1.5.5
Added experimental charge network patch to RailCraft  

## 1.5.6
- Fix forestry bio generator collision box
- Patch Industrial Foregoing, add null handler check before Meat Feeder ticks: Do null check before meat feeder drains fluid from containers. Prevents potential NullPointerException

## 1.5.7
Added More Bridger Blocks Feature to Steve's Carts Reborn

## 1.5.8
Added Calculator GUI fix for "IC2 crop breeding plugin"