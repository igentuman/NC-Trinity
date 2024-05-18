package trinity.fluid;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.*;

public class FluidTrinity extends Fluid {
	
	public FluidTrinity(String fluidName, ResourceLocation still, ResourceLocation flow, Integer colour) {
		super(fluidName, still, flow, colour);
		// setDensity(1200);
		// setViscosity(1200);
		// setTemperature(363);
		FluidRegistry.addBucketForFluid(this);
	}
}
