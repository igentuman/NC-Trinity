package trinity.init;

import com.google.common.collect.Lists;
import nc.init.NCItems;
import nc.recipe.NCRecipes;
import nc.recipe.ingredient.FluidIngredient;
import nc.recipe.vanilla.CraftingRecipeHandler;
import nc.util.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.registry.GameRegistry;
import trinity.config.TrinityConfig;

public class TrinityRecipes {
	
	private static boolean initialized = false;
	
	public static FluidIngredient fluidStack(String fluidName, int stackSize) {
		if (!FluidRegHelper.fluidExists(fluidName))
			return null;
		return new FluidIngredient(fluidName, stackSize);
	}
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		if (initialized)
			return;
		
		NCRecipes.chemical_reactor.addRecipe(fluidStack("witherite_water", FluidStackHelper.BUCKET_VOLUME), fluidStack("nitric_acid", FluidStackHelper.BUCKET_VOLUME * 2), fluidStack("barium_nitrate_solution", FluidStackHelper.BUCKET_VOLUME), fluidStack("carbon_dioxide", FluidStackHelper.BUCKET_VOLUME), 1D, 1D);
		NCRecipes.chemical_reactor.addRecipe(fluidStack("nitrogen", FluidStackHelper.BUCKET_VOLUME), fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME), fluidStack("nitric_oxide", FluidStackHelper.BUCKET_VOLUME * 2), NCRecipes.chemical_reactor.emptyFluidStack(), 1D, 1D);
		NCRecipes.chemical_reactor.addRecipe(fluidStack("nitric_oxide", FluidStackHelper.BUCKET_VOLUME * 2), fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME), fluidStack("nitrogen_dioxide", FluidStackHelper.BUCKET_VOLUME * 2), NCRecipes.chemical_reactor.emptyFluidStack(), 1D, 1D);
		NCRecipes.chemical_reactor.addRecipe(fluidStack("nitrogen_dioxide", FluidStackHelper.BUCKET_VOLUME * 3), fluidStack("water", FluidStackHelper.BUCKET_VOLUME), fluidStack("nitric_acid", FluidStackHelper.BUCKET_VOLUME * 2), fluidStack("nitric_oxide", FluidStackHelper.BUCKET_VOLUME), 1D, 1D);
		NCRecipes.electrolyzer.addRecipe(fluidStack("nitric_oxide", FluidStackHelper.BUCKET_VOLUME), fluidStack("nitrogen", FluidStackHelper.BUCKET_VOLUME / 2), fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME / 2), NCRecipes.electrolyzer.emptyFluidStack(), NCRecipes.electrolyzer.emptyFluidStack(), 1D, 0.5D);
		NCRecipes.enricher.addRecipe("dustWitherite", fluidStack("water", FluidStackHelper.BUCKET_VOLUME), fluidStack("witherite_water", FluidStackHelper.GEM_VOLUME), 1D, 1D);
		NCRecipes.enricher.addRecipe("dustBariumOxide", fluidStack("nitric_acid", FluidStackHelper.GEM_VOLUME * 2), fluidStack("barium_nitrate_solution", FluidStackHelper.GEM_VOLUME * 2), 1D, 1D);
		NCRecipes.enricher.addRecipe("dustBariumNitrate", fluidStack("water", FluidStackHelper.BUCKET_VOLUME), fluidStack("barium_nitrate_solution", FluidStackHelper.GEM_VOLUME), 1D, 1D);
		NCRecipes.enricher.addRecipe("dustBariumNitrate", fluidStack("tnt", FluidStackHelper.BUCKET_VOLUME / 5), fluidStack("baratol", FluidStackHelper.BUCKET_VOLUME / 5), 1D, 1D);
		NCRecipes.infuser.addRecipe("ingotLithium6", fluidStack("deuterium", FluidStackHelper.BUCKET_VOLUME), "ingotLithium6Deuteride", 1D, 1D);
		NCRecipes.infuser.addRecipe("dustBarium", fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME), "dustBariumOxide", 1D, 1D);
		NCRecipes.infuser.addRecipe(ModBlocks.empty_fusion_bomb, fluidStack("deuterium-tritium_mixture", FluidStackHelper.BUCKET_VOLUME), ModBlocks.fusion_bomb, 1D, 1D);
		NCRecipes.crystallizer.addRecipe(fluidStack("barium_nitrate_solution", FluidStackHelper.GEM_VOLUME), "dustBariumNitrate", 1D, 1D);
		NCRecipes.crystallizer.addRecipe(fluidStack("witherite_water", FluidStackHelper.GEM_VOLUME), "dustWitherite", 1D, 1D);
		NCRecipes.ingot_former.addRecipe(fluidStack("baratol", FluidStackHelper.BUCKET_VOLUME), ModBlocks.baratol, 1D, 1D);
		NCRecipes.fission_irradiator.addRecipe(Lists.newArrayList("ingotGold", "dustGold"), "dustGold198", 1600000L, 0, 0);
		NCRecipes.salt_mixer.addRecipe(fluidStack("deuterium", FluidStackHelper.BUCKET_VOLUME / 2), fluidStack("tritium", FluidStackHelper.BUCKET_VOLUME / 2), fluidStack("deuterium-tritium_mixture", FluidStackHelper.BUCKET_VOLUME), 0.5D, 0.5D);
		
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.light_container, 1, 0), new Object[] {"SSS", "SCS", "SSS", 'S', new ItemStack(NCItems.rad_shielding, 1, 0), 'C', Blocks.CHEST});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.medium_container, 1, 0), new Object[] {"SSS", "SCS", "SSS", 'S', new ItemStack(NCItems.rad_shielding, 1, 1), 'C', Blocks.CHEST});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.heavy_container, 1, 0), new Object[] {"SSS", "SCS", "SSS", 'S', new ItemStack(NCItems.rad_shielding, 1, 2), 'C', Blocks.CHEST});
		
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.solid_trinitite, 1, 0), new Object[] {"TTT", "TTT", "TTT", 'T', ModItems.trinitite});
		
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModItems.detonator, 1, 0), new Object[] {"TP ", "PBP", " P ", 'T', Blocks.REDSTONE_TORCH, 'P', "ingotPlutonium242", 'B', Blocks.STONE_BUTTON});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.compression_charge, 1, 0), new Object[] {"STB", "TB ", "STB", 'S', "ingotLead", 'T', Blocks.TNT, 'B', ModBlocks.baratol});
		
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModItems.neutron_initiator, 1, 0), new Object[] {" B ", "BPB", " B ", 'P', "dustPolonium", 'B', "ingotBeryllium"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModItems.neutron_initiator, 1, 0), new Object[] {" B ", "BPB", " B ", 'P', "ingotPolonium", 'B', "ingotBeryllium"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModItems.neutron_initiator, 1, 0), new Object[] {" B ", "BPB", " B ", 'P', "dustPolonium210", 'B', "ingotBeryllium"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModItems.neutron_initiator, 1, 0), new Object[] {" B ", "BPB", " B ", 'P', "ingotPolonium210", 'B', "ingotBeryllium"});
		
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.empty_fusion_bomb, 1, 0), new Object[] {"CCC", "M M", "CCC", 'C', ModBlocks.compression_charge, 'M', "solenoidCopper"});
		
		// CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModItems.source_au_198, 1, 0), new Object[] {"BBB", "BSB", "BBB", 'S', "ingotGold198",'B', "bioplastic"});
		// CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModItems.source_au_198, 1, 0), new Object[] {"BBB", "BSB", "BBB", 'S', "dustGold198",'B', "bioplastic"});
		
		// if(Trinity.QMDLoaded)
		// {
		// QMDRecipes.irradiator_fuel.addRecipe(new ItemStack(ModItems.source_au_198, 1, 0), 20D);
		// }
		
		if (TrinityConfig.thermonuclear) {
			CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.thermonuclear_core_pu239, 1, 0), new Object[] {"LLL", "PPP", "LLL", 'L', "ingotLithium6Deuteride", 'P', "ingotPlutonium239"});
		}
		
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.bomb_antimatter, 1, 0), new Object[] {"ESE", "SAS", "ESE", 'A', "cellAntimatter", 'E', "plateElite", 'S', "solenoidCopper"});
		
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.bomb_u233, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.core_u233});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.bomb_u235, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.core_u235});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.bomb_np237, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.core_np237});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.bomb_pu239, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.core_pu239});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.bomb_am242, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.core_am242});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.bomb_cm247, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.core_cm247});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.bomb_bk248, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.core_bk248});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.bomb_cf249, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.core_cf249});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.bomb_cf251, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.core_cf251});
		
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_bomb_u233, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.salted_core_u233});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_bomb_u235, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.salted_core_u235});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_bomb_np237, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.salted_core_np237});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_bomb_pu239, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.salted_core_pu239});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_bomb_am242, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.salted_core_am242});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_bomb_cm247, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.salted_core_cm247});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_bomb_bk248, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.salted_core_bk248});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_bomb_cf249, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.salted_core_cf249});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_bomb_cf251, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.salted_core_cf251});
		
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModItems.bomb_pit_u233, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'F', "ingotUranium233", 'N', ModItems.neutron_initiator});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModItems.bomb_pit_u235, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'F', "ingotUranium235", 'N', ModItems.neutron_initiator});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModItems.bomb_pit_np237, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'F', "ingotNeptunium237", 'N', ModItems.neutron_initiator});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModItems.bomb_pit_pu239, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'F', "ingotPlutonium239", 'N', ModItems.neutron_initiator});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModItems.bomb_pit_am242, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'F', "ingotAmericium242", 'N', ModItems.neutron_initiator});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModItems.bomb_pit_cm247, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'F', "ingotCurium247", 'N', ModItems.neutron_initiator});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModItems.bomb_pit_bk248, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'F', "ingotBerkelium248", 'N', ModItems.neutron_initiator});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModItems.bomb_pit_cf249, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'F', "ingotCalifornium249", 'N', ModItems.neutron_initiator});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModItems.bomb_pit_cf251, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'F', "ingotCalifornium251", 'N', ModItems.neutron_initiator});
		
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.core_u233, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_u233, 'F', "ingotUranium238"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.core_u235, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_u235, 'F', "ingotUranium238"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.core_np237, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_np237, 'F', "ingotUranium238"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.core_pu239, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_pu239, 'F', "ingotUranium238"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.core_am242, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_am242, 'F', "ingotUranium238"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.core_cm247, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_cm247, 'F', "ingotUranium238"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.core_bk248, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_bk248, 'F', "ingotUranium238"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.core_cf249, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_cf249, 'F', "ingotUranium238"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.core_cf251, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_cf251, 'F', "ingotUranium238"});
		
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_core_u233, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_u233, 'F', "ingotGold"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_core_u235, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_u235, 'F', "ingotGold"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_core_np237, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_np237, 'F', "ingotGold"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_core_pu239, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_pu239, 'F', "ingotGold"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_core_am242, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_am242, 'F', "ingotGold"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_core_cm247, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_cm247, 'F', "ingotGold"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_core_bk248, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_bk248, 'F', "ingotGold"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_core_cf249, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_cf249, 'F', "ingotGold"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_core_cf251, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_cf251, 'F', "ingotGold"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.dirty_bomb, 1, 0), new Object[] {"RRR", "RTR", "RRR", 'T', Blocks.TNT, 'R', NCItems.depleted_fuel_thorium});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.dirty_bomb, 1, 0), new Object[] {"RRR", "RTR", "RRR", 'T', Blocks.TNT, 'R', NCItems.depleted_fuel_uranium});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.dirty_bomb, 1, 0), new Object[] {"RRR", "RTR", "RRR", 'T', Blocks.TNT, 'R', NCItems.depleted_fuel_neptunium});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.dirty_bomb, 1, 0), new Object[] {"RRR", "RTR", "RRR", 'T', Blocks.TNT, 'R', NCItems.depleted_fuel_plutonium});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.dirty_bomb, 1, 0), new Object[] {"RRR", "RTR", "RRR", 'T', Blocks.TNT, 'R', NCItems.depleted_fuel_americium});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.dirty_bomb, 1, 0), new Object[] {"RRR", "RTR", "RRR", 'T', Blocks.TNT, 'R', NCItems.depleted_fuel_curium});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.dirty_bomb, 1, 0), new Object[] {"RRR", "RTR", "RRR", 'T', Blocks.TNT, 'R', NCItems.depleted_fuel_berkelium});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.dirty_bomb, 1, 0), new Object[] {"RRR", "RTR", "RRR", 'T', Blocks.TNT, 'R', NCItems.depleted_fuel_californium});
		CraftingRecipeHandler.addShapelessOreRecipe(new ItemStack(ModBlocks.gold_bomb, 1, 0), Blocks.TNT, "dustGold198");
		CraftingRecipeHandler.addShapelessOreRecipe(new ItemStack(ModBlocks.gold_bomb, 1, 0), Blocks.TNT, "ingotGold198");
		GameRegistry.addSmelting(ModItems.dust_au_198, new ItemStack(ModItems.ingot_au_198, 1, 0), 0);
		if (TrinityConfig.custom_nukes) {
			CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.bomb_custom1, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.core_custom_1});
			CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.bomb_custom2, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.core_custom_2});
			CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.bomb_custom3, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.core_custom_3});
			CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.bomb_custom4, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.core_custom_4});
			
			CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_bomb_custom1, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.salted_core_custom_1});
			CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_bomb_custom2, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.salted_core_custom_2});
			CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_bomb_custom3, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.salted_core_custom_3});
			CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_bomb_custom4, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.salted_core_custom_4});
		}
		NCRecipes.melter.addRecipe(Blocks.TNT, fluidStack("tnt", FluidStackHelper.BUCKET_VOLUME), 1D, 1D);
		NCRecipes.pressurizer.addRecipe(ModItems.gem_dust_witherite, ModItems.gem_witherite, 1D, 1D);
		NCRecipes.manufactory.addRecipe(ModItems.gem_witherite, ModItems.gem_dust_witherite, 1.5D, 1.5D);
		NCRecipes.rock_crusher.addRecipe(NCRecipes.rock_crusher.oreStackList(Lists.newArrayList("obsidian"), 1), NCRecipes.rock_crusher.chanceOreStack("dustSulfur", 2, 45), NCRecipes.rock_crusher.chanceOreStack("dustWitherite", 1, 25), NCRecipes.rock_crusher.chanceOreStack("dustMagnesium", 1, 30), 1D, 1D);
		NCRecipes.rock_crusher.addRecipe(NCRecipes.rock_crusher.oreStackList(Lists.newArrayList("blockTrinitite"), 1), NCRecipes.rock_crusher.chanceOreStack("dustCaesium137", 1, 10), NCRecipes.rock_crusher.chanceOreStack("dustStrontium90", 1, 10), NCRecipes.rock_crusher.chanceOreStack("dustMolybdenum", 1, 5), 1D, 1D);
		NCRecipes.rock_crusher.addRecipe(NCRecipes.rock_crusher.oreStackList(Lists.newArrayList("blockRadioactiveEarth"), 1), NCRecipes.rock_crusher.chanceOreStack("dustCaesium137", 1, 50), NCRecipes.rock_crusher.chanceOreStack("dustStrontium90", 1, 25), NCRecipes.rock_crusher.chanceOreStack("dustPromethium147", 1, 10), 1D, 1D);
		NCRecipes.rock_crusher.addRecipe(NCRecipes.rock_crusher.oreStackList(Lists.newArrayList("blockRadioactiveSand"), 1), NCRecipes.rock_crusher.chanceOreStack("dustCaesium137", 1, 50), NCRecipes.rock_crusher.chanceOreStack("dustStrontium90", 1, 25), NCRecipes.rock_crusher.chanceOreStack("dustPromethium147", 1, 10), 1D, 1D);
		NCRecipes.rock_crusher.addRecipe(NCRecipes.rock_crusher.oreStackList(Lists.newArrayList("blockHighlyRadioactiveEarth"), 1), NCRecipes.rock_crusher.chanceOreStack("dustEuropium155", 1, 20), NCRecipes.rock_crusher.chanceOreStack("dustPromethium147", 1, 25), NCRecipes.rock_crusher.chanceOreStack("dustGold198", 1, 1), 1D, 1D);
		NCRecipes.rock_crusher.addRecipe(NCRecipes.rock_crusher.oreStackList(Lists.newArrayList("blockHighlyRadioactiveSand"), 1), NCRecipes.rock_crusher.chanceOreStack("dustEuropium155", 1, 20), NCRecipes.rock_crusher.chanceOreStack("dustPromethium147", 1, 25), NCRecipes.rock_crusher.chanceOreStack("dustGold198", 1, 1), 1D, 1D);
		
		initialized = true;
	}
	//
	/*public static void init() {
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.solid_trinitite, 1, 0), new Object[] {"TTT", "TTT", "TTT", 'T', ModItems.trinitite});
		
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModItems.detonator, 1, 0), new Object[] {"TP ", "PBP", " P ", 'T', Blocks.REDSTONE_TORCH, 'P', "ingotPlutonium242", 'B', Blocks.STONE_BUTTON});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.compression_charge, 1, 0), new Object[] {"STB", "TB ", "STB", 'S', "ingotLead", 'T', Blocks.TNT, 'B', ModBlocks.baratol});
		
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModItems.neutron_initiator, 1, 0), new Object[] {" B ", "BPB", " B ", 'P', "dustPolonium", 'B', "ingotBeryllium"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModItems.neutron_initiator, 1, 0), new Object[] {" B ", "BPB", " B ", 'P', "ingotPolonium", 'B', "ingotBeryllium"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModItems.neutron_initiator, 1, 0), new Object[] {" B ", "BPB", " B ", 'P', "dustPolonium210", 'B', "ingotBeryllium"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModItems.neutron_initiator, 1, 0), new Object[] {" B ", "BPB", " B ", 'P', "ingotPolonium210", 'B', "ingotBeryllium"});
		
		if(TrinityConfig.thermonuclear)
		{
			CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.thermonuclear_core_pu239, 1, 0), new Object[] {"LLL", "PPP", "LLL", 'L', "ingotLithium6Deuteride", 'P', "ingotPlutonium239"});
		}
		
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.bomb_antimatter, 1, 0), new Object[] {"ESE", "SAS", "EPE", 'A', "cellAntimatter", 'E', "plateElite", 'S', "solenoidCopper", 'P', NCBlocks.rtg_plutonium});
		
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.bomb_u233, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.core_u233});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.bomb_u235, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.core_u235});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.bomb_np237, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.core_np237});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.bomb_pu239, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.core_pu239});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.bomb_am242, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.core_am242});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.bomb_cm247, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.core_cm247});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.bomb_bk248, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.core_bk248});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.bomb_cf249, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.core_cf249});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.bomb_cf251, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.core_cf251});
	
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_bomb_u233, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.salted_core_u233});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_bomb_u235, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.salted_core_u235});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_bomb_np237, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.salted_core_np237});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_bomb_pu239, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.salted_core_pu239});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_bomb_am242, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.salted_core_am242});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_bomb_cm247, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.salted_core_cm247});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_bomb_bk248, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.salted_core_bk248});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_bomb_cf249, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.salted_core_cf249});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_bomb_cf251, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.salted_core_cf251});
		
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModItems.bomb_pit_u233, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'F', "ingotUranium233", 'N', ModItems.neutron_initiator});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModItems.bomb_pit_u235, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'F', "ingotUranium235", 'N', ModItems.neutron_initiator});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModItems.bomb_pit_np237, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'F', "ingotNeptunium237", 'N', ModItems.neutron_initiator});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModItems.bomb_pit_pu239, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'F', "ingotPlutonium239", 'N', ModItems.neutron_initiator});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModItems.bomb_pit_am242, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'F', "ingotAmericium242", 'N', ModItems.neutron_initiator});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModItems.bomb_pit_cm247, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'F', "ingotCurium247", 'N', ModItems.neutron_initiator});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModItems.bomb_pit_bk248, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'F', "ingotBerkelium248", 'N', ModItems.neutron_initiator});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModItems.bomb_pit_cf249, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'F', "ingotCalifornium249", 'N', ModItems.neutron_initiator});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModItems.bomb_pit_cf251, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'F', "ingotCalifornium251", 'N', ModItems.neutron_initiator});
	
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.core_u233, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_u233, 'F', "ingotUranium238"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.core_u235, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_u235, 'F', "ingotUranium238"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.core_np237, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_np237, 'F', "ingotUranium238"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.core_pu239, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_pu239, 'F', "ingotUranium238"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.core_am242, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_am242, 'F', "ingotUranium238"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.core_cm247, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_cm247, 'F', "ingotUranium238"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.core_bk248, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_bk248, 'F', "ingotUranium238"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.core_cf249, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_cf249, 'F', "ingotUranium238"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.core_cf251, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_cf251, 'F', "ingotUranium238"});
	
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_core_u233, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_u233, 'F', "ingotGold"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_core_u235, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_u235, 'F', "ingotGold"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_core_np237, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_np237, 'F', "ingotGold"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_core_pu239, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_pu239, 'F', "ingotGold"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_core_am242, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_am242, 'F', "ingotGold"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_core_cm247, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_cm247, 'F', "ingotGold"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_core_bk248, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_bk248, 'F', "ingotGold"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_core_cf249, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_cf249, 'F', "ingotGold"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_core_cf251, 1, 0), new Object[] {"FFF", "FNF", "FFF", 'N', ModItems.bomb_pit_cf251, 'F', "ingotGold"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.dirty_bomb, 1, 0), new Object[] {"RRR", "RTR", "RRR", 'T', Blocks.TNT, 'R', NCItems.depleted_fuel_thorium});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.dirty_bomb, 1, 0), new Object[] {"RRR", "RTR", "RRR", 'T', Blocks.TNT, 'R', NCItems.depleted_fuel_uranium});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.dirty_bomb, 1, 0), new Object[] {"RRR", "RTR", "RRR", 'T', Blocks.TNT, 'R', NCItems.depleted_fuel_neptunium});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.dirty_bomb, 1, 0), new Object[] {"RRR", "RTR", "RRR", 'T', Blocks.TNT, 'R', NCItems.depleted_fuel_plutonium});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.dirty_bomb, 1, 0), new Object[] {"RRR", "RTR", "RRR", 'T', Blocks.TNT, 'R', NCItems.depleted_fuel_americium});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.dirty_bomb, 1, 0), new Object[] {"RRR", "RTR", "RRR", 'T', Blocks.TNT, 'R', NCItems.depleted_fuel_curium});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.dirty_bomb, 1, 0), new Object[] {"RRR", "RTR", "RRR", 'T', Blocks.TNT, 'R', NCItems.depleted_fuel_berkelium});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.dirty_bomb, 1, 0), new Object[] {"RRR", "RTR", "RRR", 'T', Blocks.TNT, 'R', NCItems.depleted_fuel_californium});
		
		if(TrinityConfig.custom_nukes)
		{
			CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.bomb_custom1, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.core_custom_1});
			CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.bomb_custom2, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.core_custom_2});
			CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.bomb_custom3, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.core_custom_3});
			CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.bomb_custom4, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.core_custom_4});
	
			CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_bomb_custom1, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.salted_core_custom_1});
			CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_bomb_custom2, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.salted_core_custom_2});
			CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_bomb_custom3, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.salted_core_custom_3});
			CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(ModBlocks.salted_bomb_custom4, 1, 0), new Object[] {"CCC", "CNC", "CCC", 'C', ModBlocks.compression_charge, 'N', ModBlocks.salted_core_custom_4});
		}
		NCRecipes.melter.addRecipe(Blocks.TNT, fluidStack("tnt", FluidStackHelper.BUCKET_VOLUME), 1D, 1D);
		NCRecipes.pressurizer.addRecipe(ModItems.gem_dust_witherite, ModItems.gem_witherite, 1D, 1D);
		NCRecipes.manufactory.addRecipe(ModItems.gem_witherite, ModItems.gem_dust_witherite, 1.5D, 1.5D);
		NCRecipes.rock_crusher.addRecipe(NCRecipes.rock_crusher.oreStackList(Lists.newArrayList("obsidian"), 1), NCRecipes.rock_crusher.chanceOreStack("dustSulfur", 2, 45), NCRecipes.rock_crusher.chanceOreStack("dustWitherite", 1, 25), NCRecipes.rock_crusher.chanceOreStack("dustMagnesium", 1, 30), 1D, 1D);
		NCRecipes.rock_crusher.addRecipe(NCRecipes.rock_crusher.oreStackList(Lists.newArrayList("blockTrinitite"), 1), NCRecipes.rock_crusher.chanceOreStack("dustCaesium137", 1, 10), NCRecipes.rock_crusher.chanceOreStack("dustStrontium90", 1, 10), NCRecipes.rock_crusher.chanceOreStack("dustMolybdenum", 1, 5), 1D, 1D);
		NCRecipes.rock_crusher.addRecipe(NCRecipes.rock_crusher.oreStackList(Lists.newArrayList("blockRadioactiveEarth"), 1), NCRecipes.rock_crusher.chanceOreStack("dustCaesium137", 1, 50), NCRecipes.rock_crusher.chanceOreStack("dustStrontium90", 1, 25), NCRecipes.rock_crusher.chanceOreStack("dustPromethium147", 1, 10), 1D, 1D);
		NCRecipes.rock_crusher.addRecipe(NCRecipes.rock_crusher.oreStackList(Lists.newArrayList("blockRadioactiveSand"), 1), NCRecipes.rock_crusher.chanceOreStack("dustCaesium137", 1, 50), NCRecipes.rock_crusher.chanceOreStack("dustStrontium90", 1, 25), NCRecipes.rock_crusher.chanceOreStack("dustPromethium147", 1, 10), 1D, 1D);
		NCRecipes.rock_crusher.addRecipe(NCRecipes.rock_crusher.oreStackList(Lists.newArrayList("blockHighlyRadioactiveEarth"), 1), NCRecipes.rock_crusher.chanceOreStack("dustEuropium155", 1, 20), NCRecipes.rock_crusher.chanceOreStack("dustPromethium147", 1, 25), NCRecipes.rock_crusher.chanceOreStack("dustRuthenium106", 1, 20), 1D, 1D);
		NCRecipes.rock_crusher.addRecipe(NCRecipes.rock_crusher.oreStackList(Lists.newArrayList("blockHighlyRadioactiveSand"), 1), NCRecipes.rock_crusher.chanceOreStack("dustEuropium155", 1, 20), NCRecipes.rock_crusher.chanceOreStack("dustPromethium147", 1, 25), NCRecipes.rock_crusher.chanceOreStack("dustRuthenium106", 1, 20), 1D, 1D);
	}*/
}
