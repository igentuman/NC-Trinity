package trinity.init;

import net.minecraftforge.fml.common.Mod;
import trinity.Global;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import trinity.util.Reference;

@Mod.EventBusSubscriber(modid= Reference.MODID)
public class ClientEvents {
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void textureStitch(TextureStitchEvent.Pre evt) {
		evt.getMap().registerSprite(new ResourceLocation(Global.MOD_ID, "blocks/liquid_still"));
		evt.getMap().registerSprite(new ResourceLocation(Global.MOD_ID, "blocks/liquid_flow"));
	}
}



