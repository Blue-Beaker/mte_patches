import io.bluebeaker.mtepatches.MTEPatchesConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class EntityPatchHandler {
    private static final Minecraft minecraft = Minecraft.getMinecraft();
    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event){
        if(!MTEPatchesConfig.vanilla.dontReceiveItemsWhenDead || !event.getWorld().isRemote) return;
        if(minecraft.currentScreen instanceof GuiGameOver && event.getEntity() instanceof EntityItem){
            event.setCanceled(true);
        }
    }
}
