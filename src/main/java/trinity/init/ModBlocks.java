package trinity.init;

import nc.config.NCConfig;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import trinity.blocks.BasicBlock;
import trinity.blocks.DirtyBomb;
import trinity.blocks.AntimatterBomb;
import trinity.blocks.ExoticBomb;
import trinity.blocks.ExplosiveCharge;
import trinity.blocks.FallingRadioactiveBlock;
import trinity.blocks.NuclearCore;
import trinity.blocks.RadioactiveBlock;
import trinity.blocks.ShieldedContainer;
import trinity.blocks.ThermonuclearCore;
import trinity.config.TrinityConfig;
import trinity.items.ShieldedContainerItem;
import trinity.tabs.TrinityTab;
import trinity.util.Reference;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class ModBlocks {
	
	public static Block core_u233;
	public static Block core_u235;
	public static Block core_np237;
	public static Block core_pu239;
	public static Block core_am242;
	public static Block core_cm247;
	public static Block core_bk248;
	public static Block core_cf249;
	public static Block core_cf251;
	public static Block core_custom_1;
	public static Block core_custom_2;
	public static Block core_custom_3;
	public static Block core_custom_4;
	
	public static Block salted_core_u233;
	public static Block salted_core_u235;
	public static Block salted_core_np237;
	public static Block salted_core_pu239;
	public static Block salted_core_am242;
	public static Block salted_core_cm247;
	public static Block salted_core_bk248;
	public static Block salted_core_cf249;
	public static Block salted_core_cf251;
	public static Block salted_core_custom_1;
	public static Block salted_core_custom_2;
	public static Block salted_core_custom_3;
	public static Block salted_core_custom_4;
	
	// public static Block thermonuclear_core_u233;
	// public static Block thermonuclear_core_u235;
	// public static Block thermonuclear_core_np237;
	public static Block thermonuclear_core_pu239;
	// public static Block thermonuclear_core_am242;
	// public static Block thermonuclear_core_cm247;
	// public static Block thermonuclear_core_bk248;
	// public static Block thermonuclear_core_cf249;
	// public static Block thermonuclear_core_cf251;
	
	public static Block bomb_u233;
	public static Block bomb_u235;
	public static Block bomb_np237;
	public static Block bomb_pu239;
	public static Block bomb_am242;
	public static Block bomb_cm247;
	public static Block bomb_bk248;
	public static Block bomb_cf249;
	public static Block bomb_cf251;
	public static Block bomb_custom1;
	public static Block bomb_custom2;
	public static Block bomb_custom3;
	public static Block bomb_custom4;
	public static Block bomb_antimatter;
	// public static Block bomb_wormhole;
	
	public static Block salted_bomb_u233;
	public static Block salted_bomb_u235;
	public static Block salted_bomb_np237;
	public static Block salted_bomb_pu239;
	public static Block salted_bomb_am242;
	public static Block salted_bomb_cm247;
	public static Block salted_bomb_bk248;
	public static Block salted_bomb_cf249;
	public static Block salted_bomb_cf251;
	public static Block salted_bomb_custom1;
	public static Block salted_bomb_custom2;
	public static Block salted_bomb_custom3;
	public static Block salted_bomb_custom4;
	
	public static Block dirty_bomb;
	public static Block fusion_bomb;
	public static Block empty_fusion_bomb;
	public static Block gold_bomb;
	
	// public static Block type1_fallout;
	// public static Block type2_fallout;
	public static Block radioactive_earth;
	public static Block radioactive_earth2;
	public static Block trinitite;
	public static Block solid_trinitite;
	public static Block salted_sand;
	public static Block salted_sand2;
	// public static Block red_trinitite;
	public static Block baratol;
	public static Block compression_charge;
	public static Block light_container;
	public static Block medium_container;
	public static Block heavy_container;
	
	public static void init() {
		light_container = new ShieldedContainer("light_container", Material.IRON, NCConfig.radiation_shielding_level[0]);
		medium_container = new ShieldedContainer("medium_container", Material.IRON, NCConfig.radiation_shielding_level[1]);
		heavy_container = new ShieldedContainer("heavy_container", Material.IRON, NCConfig.radiation_shielding_level[2]);
		
		salted_core_u233 = new BasicBlock("salted_core_u233", Material.IRON);
		salted_core_u235 = new BasicBlock("salted_core_u235", Material.IRON);
		salted_core_np237 = new BasicBlock("salted_core_np237", Material.IRON);
		salted_core_pu239 = new BasicBlock("salted_core_pu239", Material.IRON);
		salted_core_am242 = new BasicBlock("salted_core_am242", Material.IRON);
		salted_core_cm247 = new BasicBlock("salted_core_cm247", Material.IRON);
		salted_core_bk248 = new BasicBlock("salted_core_bk248", Material.IRON);
		salted_core_cf249 = new BasicBlock("salted_core_cf249", Material.IRON);
		salted_core_cf251 = new BasicBlock("salted_core_cf251", Material.IRON);
		salted_core_custom_1 = new BasicBlock("salted_core_custom_1", Material.IRON);
		salted_core_custom_2 = new BasicBlock("salted_core_custom_2", Material.IRON);
		salted_core_custom_3 = new BasicBlock("salted_core_custom_3", Material.IRON);
		salted_core_custom_4 = new BasicBlock("salted_core_custom_4", Material.IRON);
		
		core_u233 = new BasicBlock("core_u233", Material.IRON);
		core_u235 = new BasicBlock("core_u235", Material.IRON);
		core_np237 = new BasicBlock("core_np237", Material.IRON);
		core_pu239 = new BasicBlock("core_pu239", Material.IRON);
		core_am242 = new BasicBlock("core_am242", Material.IRON);
		core_cm247 = new BasicBlock("core_cm247", Material.IRON);
		core_bk248 = new BasicBlock("core_bk248", Material.IRON);
		core_cf249 = new BasicBlock("core_cf249", Material.IRON);
		core_cf251 = new BasicBlock("core_cf251", Material.IRON);
		core_custom_1 = new BasicBlock("core_custom_1", Material.IRON);
		core_custom_2 = new BasicBlock("core_custom_2", Material.IRON);
		core_custom_3 = new BasicBlock("core_custom_3", Material.IRON);
		core_custom_4 = new BasicBlock("core_custom_4", Material.IRON);
		
		thermonuclear_core_pu239 = new ThermonuclearCore("thermonuclear_core_pu239", Material.IRON);
		
		compression_charge = new ExplosiveCharge("compression_charge", Material.TNT);
		baratol = new ExplosiveCharge("solid_baratol", Material.TNT);
		
		// blockTNT = new BlockFluidExplosive(ModFluids.tnt);
		// blockBaratol = new BlockFluidExplosive(ModFluids.baratol);
		
		bomb_u233 = new NuclearCore("bomb_u233", Material.IRON, TrinityConfig.u233_radius, false);
		bomb_u235 = new NuclearCore("bomb_u235", Material.IRON, TrinityConfig.u235_radius, false);
		bomb_np237 = new NuclearCore("bomb_np237", Material.IRON, TrinityConfig.np237_radius, false);
		bomb_pu239 = new NuclearCore("bomb_pu239", Material.IRON, TrinityConfig.pu239_radius, false);
		bomb_am242 = new NuclearCore("bomb_am242", Material.IRON, TrinityConfig.am242_radius, false);
		bomb_cm247 = new NuclearCore("bomb_cm247", Material.IRON, TrinityConfig.cm247_radius, false);
		bomb_bk248 = new NuclearCore("bomb_bk248", Material.IRON, TrinityConfig.bk248_radius, false);
		bomb_cf249 = new NuclearCore("bomb_cf249", Material.IRON, TrinityConfig.cf249_radius, false);
		bomb_cf251 = new NuclearCore("bomb_cf251", Material.IRON, TrinityConfig.cf251_radius, false);
		bomb_custom1 = new NuclearCore("bomb_custom_1", Material.IRON, TrinityConfig.custom_1_radius, false);
		bomb_custom2 = new NuclearCore("bomb_custom_2", Material.IRON, TrinityConfig.custom_2_radius, false);
		bomb_custom3 = new NuclearCore("bomb_custom_3", Material.IRON, TrinityConfig.custom_3_radius, false);
		bomb_custom4 = new NuclearCore("bomb_custom_4", Material.IRON, TrinityConfig.custom_4_radius, false);
		bomb_antimatter = new AntimatterBomb("bomb_antimatter", Material.IRON, TrinityConfig.antimatter_radius);
		// bomb_wormhole = new ExoticBomb("bomb_exotic", Material.IRON, TrinityConfig.antimatter_radius);
		
		salted_bomb_u233 = new NuclearCore("salted_bomb_u233", Material.IRON, TrinityConfig.u233_radius, true);
		salted_bomb_u235 = new NuclearCore("salted_bomb_u235", Material.IRON, TrinityConfig.u235_radius, true);
		salted_bomb_np237 = new NuclearCore("salted_bomb_np237", Material.IRON, TrinityConfig.np237_radius, true);
		salted_bomb_pu239 = new NuclearCore("salted_bomb_pu239", Material.IRON, TrinityConfig.pu239_radius, true);
		salted_bomb_am242 = new NuclearCore("salted_bomb_am242", Material.IRON, TrinityConfig.am242_radius, true);
		salted_bomb_cm247 = new NuclearCore("salted_bomb_cm247", Material.IRON, TrinityConfig.cm247_radius, true);
		salted_bomb_bk248 = new NuclearCore("salted_bomb_bk248", Material.IRON, TrinityConfig.bk248_radius, true);
		salted_bomb_cf249 = new NuclearCore("salted_bomb_cf249", Material.IRON, TrinityConfig.cf249_radius, true);
		salted_bomb_cf251 = new NuclearCore("salted_bomb_cf251", Material.IRON, TrinityConfig.cf251_radius, true);
		salted_bomb_custom1 = new NuclearCore("salted_bomb_custom_1", Material.IRON, TrinityConfig.custom_1_radius, false);
		salted_bomb_custom2 = new NuclearCore("salted_bomb_custom_2", Material.IRON, TrinityConfig.custom_2_radius, false);
		salted_bomb_custom3 = new NuclearCore("salted_bomb_custom_3", Material.IRON, TrinityConfig.custom_3_radius, false);
		salted_bomb_custom4 = new NuclearCore("salted_bomb_custom_4", Material.IRON, TrinityConfig.custom_4_radius, false);
		
		trinitite = new BasicBlock("trinitite", Material.ROCK);
		solid_trinitite = new BasicBlock("solid_trinitite", Material.GLASS);
		radioactive_earth = new RadioactiveBlock("radioactive_earth", Material.ROCK);
		radioactive_earth2 = new RadioactiveBlock("radioactive_earth2", Material.ROCK);
		salted_sand = new FallingRadioactiveBlock("salted_sand", Material.SAND);
		salted_sand2 = new FallingRadioactiveBlock("salted_sand2", Material.SAND);
		
		dirty_bomb = new DirtyBomb("dirty_bomb", Material.TNT, SoundType.PLANT);
		gold_bomb = new DirtyBomb("gold_bomb", Material.TNT, SoundType.PLANT);
		fusion_bomb = new DirtyBomb("fusion_bomb", Material.IRON, SoundType.METAL);
		empty_fusion_bomb = new BasicBlock("empty_fusion_bomb", Material.IRON);
		
		// Creative Tab
		core_u233.setCreativeTab(null);
		core_u235.setCreativeTab(null);
		core_np237.setCreativeTab(null);
		core_pu239.setCreativeTab(null);
		core_am242.setCreativeTab(null);
		core_cm247.setCreativeTab(null);
		core_bk248.setCreativeTab(null);
		core_cf249.setCreativeTab(null);
		core_cf251.setCreativeTab(null);
		core_custom_1.setCreativeTab(null);
		core_custom_2.setCreativeTab(null);
		core_custom_3.setCreativeTab(null);
		core_custom_4.setCreativeTab(null);
		
		salted_core_u233.setCreativeTab(null);
		salted_core_u235.setCreativeTab(null);
		salted_core_np237.setCreativeTab(null);
		salted_core_pu239.setCreativeTab(null);
		salted_core_am242.setCreativeTab(null);
		salted_core_cm247.setCreativeTab(null);
		salted_core_bk248.setCreativeTab(null);
		salted_core_cf249.setCreativeTab(null);
		salted_core_cf251.setCreativeTab(null);
		salted_core_custom_1.setCreativeTab(null);
		salted_core_custom_2.setCreativeTab(null);
		salted_core_custom_3.setCreativeTab(null);
		salted_core_custom_4.setCreativeTab(null);
		
		thermonuclear_core_pu239.setCreativeTab(TrinityTab.TRINITY_TAB);
		
		compression_charge.setCreativeTab(TrinityTab.TRINITY_TAB);
		baratol.setCreativeTab(TrinityTab.TRINITY_TAB);
		dirty_bomb.setCreativeTab(TrinityTab.TRINITY_TAB);
		gold_bomb.setCreativeTab(TrinityTab.TRINITY_TAB);
		fusion_bomb.setCreativeTab(TrinityTab.TRINITY_TAB);
		empty_fusion_bomb.setCreativeTab(TrinityTab.TRINITY_TAB);
		
		light_container.setCreativeTab(TrinityTab.TRINITY_TAB);
		medium_container.setCreativeTab(TrinityTab.TRINITY_TAB);
		heavy_container.setCreativeTab(TrinityTab.TRINITY_TAB);
		
		bomb_u233.setCreativeTab(TrinityTab.TRINITY_TAB);
		bomb_u235.setCreativeTab(TrinityTab.TRINITY_TAB);
		bomb_np237.setCreativeTab(TrinityTab.TRINITY_TAB);
		bomb_pu239.setCreativeTab(TrinityTab.TRINITY_TAB);
		bomb_am242.setCreativeTab(TrinityTab.TRINITY_TAB);
		bomb_cm247.setCreativeTab(TrinityTab.TRINITY_TAB);
		bomb_bk248.setCreativeTab(TrinityTab.TRINITY_TAB);
		bomb_cf249.setCreativeTab(TrinityTab.TRINITY_TAB);
		bomb_cf251.setCreativeTab(TrinityTab.TRINITY_TAB);
		if (TrinityConfig.custom_nukes) {
			bomb_custom1.setCreativeTab(TrinityTab.TRINITY_TAB);
			bomb_custom2.setCreativeTab(TrinityTab.TRINITY_TAB);
			bomb_custom3.setCreativeTab(TrinityTab.TRINITY_TAB);
			bomb_custom4.setCreativeTab(TrinityTab.TRINITY_TAB);
			
			salted_bomb_custom1.setCreativeTab(TrinityTab.TRINITY_TAB);
			salted_bomb_custom2.setCreativeTab(TrinityTab.TRINITY_TAB);
			salted_bomb_custom3.setCreativeTab(TrinityTab.TRINITY_TAB);
			salted_bomb_custom4.setCreativeTab(TrinityTab.TRINITY_TAB);
		}
		bomb_antimatter.setCreativeTab(TrinityTab.TRINITY_TAB);
		
		salted_bomb_u233.setCreativeTab(TrinityTab.TRINITY_TAB);
		salted_bomb_u235.setCreativeTab(TrinityTab.TRINITY_TAB);
		salted_bomb_np237.setCreativeTab(TrinityTab.TRINITY_TAB);
		salted_bomb_pu239.setCreativeTab(TrinityTab.TRINITY_TAB);
		salted_bomb_am242.setCreativeTab(TrinityTab.TRINITY_TAB);
		salted_bomb_cm247.setCreativeTab(TrinityTab.TRINITY_TAB);
		salted_bomb_bk248.setCreativeTab(TrinityTab.TRINITY_TAB);
		salted_bomb_cf249.setCreativeTab(TrinityTab.TRINITY_TAB);
		salted_bomb_cf251.setCreativeTab(TrinityTab.TRINITY_TAB);
		
		trinitite.setCreativeTab(TrinityTab.TRINITY_TAB);
		solid_trinitite.setCreativeTab(TrinityTab.TRINITY_TAB);
		salted_sand.setCreativeTab(TrinityTab.TRINITY_TAB);
		radioactive_earth.setCreativeTab(TrinityTab.TRINITY_TAB);
		radioactive_earth2.setCreativeTab(TrinityTab.TRINITY_TAB);
	}
	
	public static void register() {
		
		registerBlock2(light_container);
		registerBlock2(medium_container);
		registerBlock2(heavy_container);
		registerBlock(compression_charge);
		registerBlock(baratol);
		registerBlock(dirty_bomb);
		registerBlock(fusion_bomb);
		registerBlock(empty_fusion_bomb);
		registerBlock(gold_bomb);
		registerBlock(core_u233);
		registerBlock(core_u235);
		registerBlock(core_np237);
		registerBlock(core_pu239);
		registerBlock(core_am242);
		registerBlock(core_cm247);
		registerBlock(core_bk248);
		registerBlock(core_cf249);
		registerBlock(core_cf251);
		
		registerBlock(salted_core_u233);
		registerBlock(salted_core_u235);
		registerBlock(salted_core_np237);
		registerBlock(salted_core_pu239);
		registerBlock(salted_core_am242);
		registerBlock(salted_core_cm247);
		registerBlock(salted_core_bk248);
		registerBlock(salted_core_cf249);
		registerBlock(salted_core_cf251);
		
		registerBlock(thermonuclear_core_pu239);
		
		registerBlock(bomb_u233);
		registerBlock(bomb_u235);
		registerBlock(bomb_np237);
		registerBlock(bomb_pu239);
		registerBlock(bomb_am242);
		registerBlock(bomb_cm247);
		registerBlock(bomb_bk248);
		registerBlock(bomb_cf249);
		registerBlock(bomb_cf251);
		
		registerBlock(salted_bomb_u233);
		registerBlock(salted_bomb_u235);
		registerBlock(salted_bomb_np237);
		registerBlock(salted_bomb_pu239);
		registerBlock(salted_bomb_am242);
		registerBlock(salted_bomb_cm247);
		registerBlock(salted_bomb_bk248);
		registerBlock(salted_bomb_cf249);
		registerBlock(salted_bomb_cf251);
		
		registerBlock(bomb_antimatter);
		
		registerBlock(salted_sand);
		registerBlock(salted_sand2);
		registerBlock(radioactive_earth);
		registerBlock(radioactive_earth2);
		registerBlock(trinitite);
		registerBlock(solid_trinitite);
		
		if (TrinityConfig.custom_nukes) {
			registerBlock(core_custom_1);
			registerBlock(core_custom_2);
			registerBlock(core_custom_3);
			registerBlock(core_custom_4);
			
			registerBlock(salted_core_custom_1);
			registerBlock(salted_core_custom_2);
			registerBlock(salted_core_custom_3);
			registerBlock(salted_core_custom_4);
			
			registerBlock(bomb_custom1);
			registerBlock(bomb_custom2);
			registerBlock(bomb_custom3);
			registerBlock(bomb_custom4);
			
			registerBlock(salted_bomb_custom1);
			registerBlock(salted_bomb_custom2);
			registerBlock(salted_bomb_custom3);
			registerBlock(salted_bomb_custom4);
		}
	}
	
	public static void registerRenders() {
		registerRender(light_container);
		registerRender(medium_container);
		registerRender(heavy_container);
		registerRender(compression_charge);
		registerRender(baratol);
		registerRender(dirty_bomb);
		registerRender(fusion_bomb);
		registerRender(empty_fusion_bomb);
		registerRender(gold_bomb);
		registerRender(core_u233);
		registerRender(core_u235);
		registerRender(core_np237);
		registerRender(core_pu239);
		registerRender(core_am242);
		registerRender(core_cm247);
		registerRender(core_bk248);
		registerRender(core_cf249);
		registerRender(core_cf251);
		
		registerRender(salted_core_u233);
		registerRender(salted_core_u235);
		registerRender(salted_core_np237);
		registerRender(salted_core_pu239);
		registerRender(salted_core_am242);
		registerRender(salted_core_cm247);
		registerRender(salted_core_bk248);
		registerRender(salted_core_cf249);
		registerRender(salted_core_cf251);
		
		registerRender(thermonuclear_core_pu239);
		
		registerRender(bomb_u233);
		registerRender(bomb_u235);
		registerRender(bomb_np237);
		registerRender(bomb_pu239);
		registerRender(bomb_am242);
		registerRender(bomb_cm247);
		registerRender(bomb_bk248);
		registerRender(bomb_cf249);
		registerRender(bomb_cf251);
		
		registerRender(salted_bomb_u233);
		registerRender(salted_bomb_u235);
		registerRender(salted_bomb_np237);
		registerRender(salted_bomb_pu239);
		registerRender(salted_bomb_am242);
		registerRender(salted_bomb_cm247);
		registerRender(salted_bomb_bk248);
		registerRender(salted_bomb_cf249);
		registerRender(salted_bomb_cf251);
		registerRender(bomb_antimatter);
		registerRender(radioactive_earth);
		registerRender(radioactive_earth2);
		registerRender(trinitite);
		registerRender(solid_trinitite);
		registerRender(salted_sand);
		registerRender(salted_sand2);
		
		if (TrinityConfig.custom_nukes) {
			registerRender(core_custom_1);
			registerRender(core_custom_2);
			registerRender(core_custom_3);
			registerRender(core_custom_4);
			
			registerRender(salted_core_custom_1);
			registerRender(salted_core_custom_2);
			registerRender(salted_core_custom_3);
			registerRender(salted_core_custom_4);
			
			registerRender(bomb_custom1);
			registerRender(bomb_custom2);
			registerRender(bomb_custom3);
			registerRender(bomb_custom4);
			
			registerRender(salted_bomb_custom1);
			registerRender(salted_bomb_custom2);
			registerRender(salted_bomb_custom3);
			registerRender(salted_bomb_custom4);
		}
	}
	
	public static void registerRender(Block block) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}
	
	public static void registerBlock(Block block) {
		ForgeRegistries.BLOCKS.register(block);
		ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
		
	}
	
	public static void registerBlock2(Block block) {
		ForgeRegistries.BLOCKS.register(block);
		ForgeRegistries.ITEMS.register(new ShieldedContainerItem(block).setRegistryName(block.getRegistryName()));
		
	}
}
