package trinity.fluid;

//import nc.fluid.FluidBase;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidTrinity extends Fluid {
	
	public FluidTrinity(String fluidName, ResourceLocation still, ResourceLocation flow, Integer colour) {
		super(fluidName, still, flow, colour);
		FluidRegistry.addBucketForFluid(this);
	}
}

