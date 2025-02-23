# MTE Patches
Fixes bugs and flaws in varoius mods.  
Made for the modpack Minetech Evolution. Use freely if you want any fixes in the mod. All fixes are togglable via config. 
This mod works outside of the modpack as well.  
## Current fixes:  
### Railcraft
#### Multiblock Desync  
  Fix a desync bug of multiblock that when the multiblock is across chunks.
  This can be easily reproduced by building a multiblock tank across 4 chunks, forceload one chunk of them, walk away from the tank, quit and rejoin world, then walk back slowly. 
  When desync, client can get its inventory scrambled when right-clicking at the bugged multiblock.  
  
#### Free Turbine Repair  
  Fix turbine being fixed for free when putting in crafting slot with a blade and take it out.

### IndustrialCraft 2
#### Mass Fabricator Stuck  
Mass fabricator stuck forever when its output is blocked once. This mod can fix this.  
#### Crops and Farmlands  
This is a enhancement for IC2, allowing crop sticks to be put on other mod's farmlands, as long as it extends from BlockFarmland.  
#### Block Keybinds in GUI  
Prevent IC2 keybinds in GUIs. Fixes jetpack being activated when pressing jump with the inventory open.  

### Thermal Series
#### Fuzzy NBT Recycling  
Enhancement for Thermal Expansion, allows recycling enchanted items with induction smelter and sawmill.  

### Forestry  
#### Faulty Queen Bee Fix  
Prevent crashing when placing a faulty queen bee into apiary  

### RFTools
#### Modular Storage GUI Tweaks  
- Shift Tweak  

  When pulling out items with shift, don't refresh the list until the mouse is released.  Improves compatibility with MouseTweaks.  

- Insertion Tweak  

  When putting your held item into the storage, prevent swapping the hovered item out from the storage.  

### Biomes O Plenty
#### Connection Timeout
Add a timeout when it's retrieving trail info, prevent it from freezing the loading progress forever on a lossy internet connection.  

### BuildCraft
#### Item Pipe Accepts Ejection
Allows BuildCraft item pipes to accept items ejected from vanilla hoppers and machines from other mods.  

### Moar Tinkers
#### Disable Mining Boost when Sneaking
A QoL Feature, disables server-side mining speed boost of traits when sneaking, to prevent over-mining with it

### Industrial Foregoing
#### Insertion Converyor Upgrade Duplication Fix
Fixed a item duplication bug for insertion converyor upgrade.