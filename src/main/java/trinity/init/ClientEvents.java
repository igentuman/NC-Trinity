package trinity.init;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.*;
import trinity.Reference;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ClientEvents {
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void textureStitch(TextureStitchEvent.Pre evt) {
		evt.getMap().registerSprite(new ResourceLocation(Reference.MOD_ID, "blocks/liquid_still"));
		evt.getMap().registerSprite(new ResourceLocation(Reference.MOD_ID, "blocks/liquid_flow"));
	}
}
