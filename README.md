# MTE Patches
Fixes bugs in mods for the modpack Minetech:Evolution.  

## Current fixes:  
### Railcraft
__Multiblock Desync__  
  Fix a desync bug of multiblock that when the multiblock is across chunks.
  This can be easily reproduced by building a multiblock tank across 4 chunks, forceload one chunk of them, walk away from the tank, quit and rejoin world, then walk back slowly. 
  When desync, client can get its inventory scrambled when right-clicking at the bugged multiblock.  
  
__Free Turbine Repair__  
  Fix turbine being fixed for free when putting in crafting slot with a blade and take it out.