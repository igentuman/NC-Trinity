package trinity.handler;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import trinity.init.ModBlocks;
import trinity.init.ModItems;

public class OredictHandler {
	
	public static void registerOres() {
		OreDictionary.registerOre("gemWitherite", new ItemStack(ModItems.gem_witherite, 1, 0));
		OreDictionary.registerOre("dustWitherite", new ItemStack(ModItems.gem_dust_witherite, 1, 0));
		OreDictionary.registerOre("dustBarium", new ItemStack(ModItems.barium, 1, 0));
		OreDictionary.registerOre("dustBarium137", new ItemStack(ModItems.barium, 1, 0));
		OreDictionary.registerOre("dustBariumNitrate", new ItemStack(ModItems.compound_barium_nitrate, 1, 0));
		OreDictionary.registerOre("dustBariumOxide", new ItemStack(ModItems.compound_barium_oxide, 1, 0));
		OreDictionary.registerOre("ingotLithium6Deuteride", new ItemStack(ModItems.compound_lithium_deuteride, 1, 0));
		OreDictionary.registerOre("ingotGold198", new ItemStack(ModItems.ingot_au_198, 1, 0));
		OreDictionary.registerOre("dustGold198", new ItemStack(ModItems.dust_au_198, 1, 0));
		
		/*OreDictionary.registerOre("sphereUranium233", new ItemStack(ModItems.bomb_pit_u233, 1, 0));
		OreDictionary.registerOre("sphereUranium235", new ItemStack(ModItems.bomb_pit_u235, 1, 0));
		OreDictionary.registerOre("sphereNeptunium237", new ItemStack(ModItems.bomb_pit_np237, 1, 0));
		OreDictionary.registerOre("spherePlutonium239", new ItemStack(ModItems.bomb_pit_pu239, 1, 0));
		OreDictionary.registerOre("sphereAmericium242", new ItemStack(ModItems.bomb_pit_am242, 1, 0));
		OreDictionary.registerOre("sphereCurium247", new ItemStack(ModItems.bomb_pit_cm247, 1, 0));
		OreDictionary.registerOre("sphereBerkelium248", new ItemStack(ModItems.bomb_pit_bk248, 1, 0));
		OreDictionary.registerOre("sphereCalifornium249", new ItemStack(ModItems.bomb_pit_cf249, 1, 0));
		OreDictionary.registerOre("sphereCalifornium251", new ItemStack(ModItems.bomb_pit_cf251, 1, 0));
		
		OreDictionary.registerOre("coreUranium233", new ItemStack(ModBlocks.core_u233, 1, 0));
		OreDictionary.registerOre("coreUranium235", new ItemStack(ModBlocks.core_u235, 1, 0));
		OreDictionary.registerOre("coreNeptunium237", new ItemStack(ModBlocks.core_np237, 1, 0));
		OreDictionary.registerOre("corePlutonium239", new ItemStack(ModBlocks.core_pu239, 1, 0));
		OreDictionary.registerOre("coreAmericium242", new ItemStack(ModBlocks.core_am242, 1, 0));
		OreDictionary.registerOre("coreCurium247", new ItemStack(ModBlocks.core_cm247, 1, 0));
		OreDictionary.registerOre("coreBerkelium248", new ItemStack(ModBlocks.core_bk248, 1, 0));
		OreDictionary.registerOre("coreCalifornium249", new ItemStack(ModBlocks.core_cf249, 1, 0));
		OreDictionary.registerOre("coreCalifornium251", new ItemStack(ModBlocks.core_cf251, 1, 0));
		
		OreDictionary.registerOre("saltedCoreUranium233", new ItemStack(ModBlocks.salted_core_u233, 1, 0));
		OreDictionary.registerOre("saltedCoreUranium235", new ItemStack(ModBlocks.salted_core_u235, 1, 0));
		OreDictionary.registerOre("saltedCoreNeptunium237", new ItemStack(ModBlocks.salted_core_np237, 1, 0));
		OreDictionary.registerOre("saltedCorePlutonium239", new ItemStack(ModBlocks.salted_core_pu239, 1, 0));
		OreDictionary.registerOre("saltedCoreAmericium242", new ItemStack(ModBlocks.salted_core_am242, 1, 0));
		OreDictionary.registerOre("saltedCoreCurium247", new ItemStack(ModBlocks.salted_core_cm247, 1, 0));
		OreDictionary.registerOre("saltedCoreBerkelium248", new ItemStack(ModBlocks.salted_core_bk248, 1, 0));
		OreDictionary.registerOre("saltedCoreCalifornium249", new ItemStack(ModBlocks.salted_core_cf249, 1, 0));
		OreDictionary.registerOre("saltedCoreCalifornium251", new ItemStack(ModBlocks.salted_core_cf251, 1, 0));
		
		OreDictionary.registerOre("bombUranium233", new ItemStack(ModBlocks.bomb_u233, 1, 0));
		OreDictionary.registerOre("bombUranium235", new ItemStack(ModBlocks.bomb_u235, 1, 0));
		OreDictionary.registerOre("bombNeptunium237", new ItemStack(ModBlocks.bomb_np237, 1, 0));
		OreDictionary.registerOre("bombPlutonium239", new ItemStack(ModBlocks.bomb_pu239, 1, 0));
		OreDictionary.registerOre("bombAmericium242", new ItemStack(ModBlocks.bomb_am242, 1, 0));
		OreDictionary.registerOre("bombCurium247", new ItemStack(ModBlocks.bomb_cm247, 1, 0));
		OreDictionary.registerOre("bombBerkelium248", new ItemStack(ModBlocks.bomb_bk248, 1, 0));
		OreDictionary.registerOre("bombCalifornium249", new ItemStack(ModBlocks.bomb_cf249, 1, 0));
		OreDictionary.registerOre("bombCalifornium251", new ItemStack(ModBlocks.bomb_cf251, 1, 0));
		
		OreDictionary.registerOre("saltedBombUranium233", new ItemStack(ModBlocks.salted_bomb_u233, 1, 0));
		OreDictionary.registerOre("saltedBombUranium235", new ItemStack(ModBlocks.salted_bomb_u235, 1, 0));
		OreDictionary.registerOre("saltedBombNeptunium237", new ItemStack(ModBlocks.salted_bomb_np237, 1, 0));
		OreDictionary.registerOre("saltedBombPlutonium239", new ItemStack(ModBlocks.salted_bomb_pu239, 1, 0));
		OreDictionary.registerOre("saltedBombAmericium242", new ItemStack(ModBlocks.salted_bomb_am242, 1, 0));
		OreDictionary.registerOre("saltedBombCurium247", new ItemStack(ModBlocks.salted_bomb_cm247, 1, 0));
		OreDictionary.registerOre("saltedBombBerkelium248", new ItemStack(ModBlocks.salted_bomb_bk248, 1, 0));
		OreDictionary.registerOre("saltedBombCalifornium249", new ItemStack(ModBlocks.salted_bomb_cf249, 1, 0));
		OreDictionary.registerOre("saltedBombCalifornium251", new ItemStack(ModBlocks.salted_bomb_cf251, 1, 0));*/
		
		OreDictionary.registerOre("blockRadioactiveEarth", new ItemStack(ModBlocks.radioactive_earth, 1, 0));
		OreDictionary.registerOre("blockRadioactiveSand", new ItemStack(ModBlocks.salted_sand, 1, 0));
		OreDictionary.registerOre("blockHighlyRadioactiveEarth", new ItemStack(ModBlocks.radioactive_earth2, 1, 0));
		OreDictionary.registerOre("blockHighlyRadioactiveSand", new ItemStack(ModBlocks.salted_sand2, 1, 0));
		OreDictionary.registerOre("oreTrinitite", new ItemStack(ModBlocks.trinitite, 1, 0));
		OreDictionary.registerOre("blockTrinitite", new ItemStack(ModBlocks.solid_trinitite, 1, 0));
		OreDictionary.registerOre("gemTrinitite", new ItemStack(ModItems.trinitite, 1, 0));
		
		// OreDictionary.registerOre("dirtyBomb", new ItemStack(ModBlocks.dirty_bomb, 1, 0));
		// OreDictionary.registerOre("thermonuclearCore", new ItemStack(ModBlocks.thermonuclear_core_pu239, 1, 0));
	}
}
