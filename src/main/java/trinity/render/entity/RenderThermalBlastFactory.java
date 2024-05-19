package trinity.render.entity;

import net.minecraft.client.renderer.entity.*;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import trinity.entities.EntityThermalBlast;

public class RenderThermalBlastFactory implements IRenderFactory<EntityThermalBlast> {
	
	@Override
	public Render<? super EntityThermalBlast> createRenderFor(RenderManager manager) {
		return new RenderThermalBlast(manager);
	}
	
}
