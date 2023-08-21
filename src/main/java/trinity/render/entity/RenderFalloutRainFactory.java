package trinity.render.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import trinity.entities.EntityFalloutRain;

public class RenderFalloutRainFactory implements IRenderFactory<EntityFalloutRain> {
	
	@Override
	public Render<? super EntityFalloutRain> createRenderFor(RenderManager manager) {
		return new RenderFallout(manager);
	}
	
}
