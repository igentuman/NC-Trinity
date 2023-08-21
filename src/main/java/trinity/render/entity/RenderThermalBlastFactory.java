package trinity.render.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import trinity.entities.EntityThermalBlast;

public class RenderThermalBlastFactory implements IRenderFactory<EntityThermalBlast> {
	
	@Override
	public Render<? super EntityThermalBlast> createRenderFor(RenderManager manager) {
		return new RenderThermalBlast(manager);
	}
	
}
