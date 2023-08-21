package trinity.fluid;

import nc.block.fluid.NCBlockFluid;
import nc.block.item.NCItemBlock;
import nc.enumm.FluidType;
import nc.util.ColorHelper;
import nc.util.NCUtil;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;
import trinity.Global;
import trinity.Trinity;

import java.util.ArrayList;
import java.util.List;

public class TrinityFluids {

	public static FluidTrinity witherite_water;
	public static FluidTrinity nitric_oxide;
	public static FluidTrinity nitrogen_dioxide;
	public static FluidTrinity nitric_acid;
	public static FluidTrinity barium_nitrate_solution;
	public static FluidTrinity tnt;
	public static FluidTrinity baratol;
	
	public static List<Pair<Fluid, NCBlockFluid>> fluidPairList = new ArrayList<Pair<Fluid, NCBlockFluid>>();
	
	public static void registerFluids() {
		tnt = new FluidTrinity("tnt", new ResourceLocation(Global.MOD_ID, "blocks/liquid_still"), new ResourceLocation(Global.MOD_ID, "blocks/liquid_flow"), 0xFD2800);
		baratol = new FluidTrinity("baratol", new ResourceLocation(Global.MOD_ID, "blocks/liquid_still"), new ResourceLocation(Global.MOD_ID, "blocks/liquid_flow"), 0xFB8D56);

		FluidRegistry.registerFluid(tnt);
		FluidRegistry.registerFluid(baratol);
	}
	
	
	
	private static <T extends Fluid, V extends NCBlockFluid> void addFluidPair(FluidType fluidType, Object... fluidArgs)
	{
		try
		{
			T fluid = NCUtil.newInstance(fluidType.getFluidClass(), fluidArgs);
			V block = NCUtil.newInstance(fluidType.getBlockClass(), fluid);
			fluidPairList.add(Pair.of(fluid, block));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void init()
	{
		try
		{
			addFluidPair(FluidType.SALT_SOLUTION, "witherite_water", waterBlend(0xC4C1A2));
			addFluidPair(FluidType.GAS, "nitric_oxide", 0xB2C68A);
			addFluidPair(FluidType.GAS, "nitrogen_dioxide", 0x381D05);
			addFluidPair(FluidType.ACID, "nitric_acid", 0x0A8375);
			addFluidPair(FluidType.SALT_SOLUTION, "barium_nitrate_solution", waterBlend(0xBFBFBF));
			addFluidPair(FluidType.GAS, "deuterium-tritium_mixture", deuteriumBlend(0x5DBBD6, 0.5F));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void renderFluids() {
		//blockTNT.render();
		//blockBaratol.render();
	}
	
	public static void register()
	{
		for (Pair<Fluid, NCBlockFluid> fluidPair : fluidPairList)
		{
			Fluid fluid = fluidPair.getLeft();
			
			boolean defaultFluid = FluidRegistry.registerFluid(fluid);
			if (!defaultFluid)
				fluid = FluidRegistry.getFluid(fluid.getName());
			
			//if(!(fluidPair.getRight() instanceof BlockFluidExotic))
			//{
			FluidRegistry.addBucketForFluid(fluid);
			//}
			registerBlock(fluidPair.getRight());
		}
		
		
		
	}
	
	public static void registerBlock(NCBlockFluid block)
	{
		ForgeRegistries.BLOCKS.register(withName(block));
		ForgeRegistries.ITEMS.register(new NCItemBlock(block, TextFormatting.AQUA).setRegistryName(block.getRegistryName()));
		Trinity.proxy.registerFluidBlockRendering(block, block.name);
	}
	
	public static <T extends NCBlockFluid> Block withName(T block)
	{
		return block.setTranslationKey(Global.MOD_ID + "." + block.name).setRegistryName(new ResourceLocation(Global.MOD_ID, block.name));
	}


	private static int waterBlend(int soluteColor, float blendRatio)
	{
		return ColorHelper.blend(0x2F43F4, soluteColor, blendRatio);
	}

	private static int deuteriumBlend(int soluteColor, float blendRatio)
	{
		return ColorHelper.blend(0x9E6FEF, soluteColor, blendRatio);
	}
	
	private static int waterBlend(int soluteColor)
	{
		return waterBlend(soluteColor, 0.5F);
	}
    /*
    NCFluids.fluidPairList.add(NCFluids.fluidPair(FluidType.GAS, "nitric_oxide", 0xB2C68A));
    NCFluids.fluidPairList.add(NCFluids.fluidPair(FluidType.GAS, "nitrogen_dioxide", 0x381D05));
    NCFluids.fluidPairList.add(NCFluids.fluidPair(FluidType.ACID, "nitric_acid", 0x0A8375));
    NCFluids.fluidPairList.add(fluidPair(TrinityFluidType.EXPLOSIVE, "TNT", 0xFD2800));
    NCFluids.fluidPairList.add(fluidPair(TrinityFluidType.EXPLOSIVE, "baratol", 0xFB8D56));
    */
}

