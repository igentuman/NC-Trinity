package trinity.render.entity;

import trinity.entities.EntityFalloutRain;
import trinity.render.entity.RenderFallout;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderFalloutRainFactory implements IRenderFactory<EntityFalloutRain> {

	@Override
	public Render<? super EntityFalloutRain> createRenderFor(RenderManager manager) {
		return new RenderFallout(manager);
	}

}
