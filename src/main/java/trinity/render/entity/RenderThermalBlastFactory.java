package trinity.render.entity;

import trinity.entities.EntityThermalBlast;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderThermalBlastFactory implements IRenderFactory<EntityThermalBlast> {

	@Override
	public Render<? super EntityThermalBlast> createRenderFor(RenderManager manager) {
		return new RenderThermalBlast(manager);
	}

}
