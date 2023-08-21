package trinity.handler;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import trinity.init.ModBlocks;
import trinity.init.ModItems;

public class OredictHandler {

	public static void registerOres(){
		OreDictionary.registerOre("gemWitherite", new ItemStack(ModItems.gem_witherite, 1, 0));
		OreDictionary.registerOre("dustWitherite", new ItemStack(ModItems.gem_dust_witherite, 1, 0));
		OreDictionary.registerOre("dustBarium", new ItemStack(ModItems.barium, 1, 0));
		OreDictionary.registerOre("dustBarium137", new ItemStack(ModItems.barium, 1, 0));
		OreDictionary.registerOre("dustBariumNitrate", new ItemStack(ModItems.compound_barium_nitrate, 1, 0));
		OreDictionary.registerOre("dustBariumOxide", new ItemStack(ModItems.compound_barium_oxide, 1, 0));
		OreDictionary.registerOre("ingotLithium6Deuteride", new ItemStack(ModItems.compound_lithium_deuteride, 1, 0));
		OreDictionary.registerOre("ingotGold198", new ItemStack(ModItems.ingot_au_198, 1, 0));
		OreDictionary.registerOre("dustGold198", new ItemStack(ModItems.dust_au_198, 1, 0));
		

		
		OreDictionary.registerOre("blockRadioactiveEarth", new ItemStack(ModBlocks.radioactive_earth, 1, 0));
		OreDictionary.registerOre("blockRadioactiveSand", new ItemStack(ModBlocks.salted_sand, 1, 0));
		OreDictionary.registerOre("blockHighlyRadioactiveEarth", new ItemStack(ModBlocks.radioactive_earth2, 1, 0));
		OreDictionary.registerOre("blockHighlyRadioactiveSand", new ItemStack(ModBlocks.salted_sand2, 1, 0));
		OreDictionary.registerOre("oreTrinitite", new ItemStack(ModBlocks.trinitite, 1, 0));
		OreDictionary.registerOre("blockTrinitite", new ItemStack(ModBlocks.solid_trinitite, 1, 0));
		OreDictionary.registerOre("gemTrinitite", new ItemStack(ModItems.trinitite, 1, 0));

	}		
}
