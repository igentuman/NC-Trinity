package trinity.init;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import trinity.items.BasicItem;
import trinity.items.ItemDetonator;
// import trinity.items.RadioactiveSource;
import trinity.items.RadioactiveSource2;
import trinity.items.ShieldedContainer;
import trinity.radiation.RadiationHandler;
// import trinity.items.RadioactiveSource;
import trinity.tabs.TrinityTab;
import trinity.util.Reference;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class ModItems {
	
	public static Item gem_witherite;
	public static Item gem_dust_witherite;
	public static Item barium;
	public static Item compound_barium_nitrate;
	public static Item compound_barium_oxide;
	public static Item compound_lithium_deuteride;
	public static Item dust_au_198;
	public static Item ingot_au_198;
	public static Item source_au_198;
	
	public static Item bomb_pit_u233;
	public static Item bomb_pit_u235;
	public static Item bomb_pit_np237;
	public static Item bomb_pit_pu239;
	public static Item bomb_pit_am242;
	public static Item bomb_pit_cm247;
	public static Item bomb_pit_bk248;
	public static Item bomb_pit_cf249;
	public static Item bomb_pit_cf251;
	
	public static Item detonator;
	public static Item trinitite;
	
	public static Item neutron_initiator;
	
	public static Item light_shielded_pig;
	public static Item medium_shielded_pig;
	public static Item heavy_shielded_pig;
	
	// Initialize
	public static void init() {
		
		light_shielded_pig = new ShieldedContainer("light_shielded_pig", 0.1);
		light_shielded_pig.setCreativeTab(TrinityTab.TRINITY_TAB);
		
		medium_shielded_pig = new ShieldedContainer("medium_shielded_pig", 1);
		medium_shielded_pig.setCreativeTab(TrinityTab.TRINITY_TAB);
		
		medium_shielded_pig = new ShieldedContainer("heavy_shielded_pig", 10);
		medium_shielded_pig.setCreativeTab(TrinityTab.TRINITY_TAB);
		
		trinitite = new BasicItem("trinitite_shard");
		trinitite.setCreativeTab(TrinityTab.TRINITY_TAB);
		
		gem_witherite = new BasicItem("gem_witherite");
		gem_witherite.setCreativeTab(TrinityTab.TRINITY_TAB);
		
		gem_dust_witherite = new BasicItem("gem_dust_witherite");
		gem_dust_witherite.setCreativeTab(TrinityTab.TRINITY_TAB);
		
		barium = new BasicItem("barium");
		barium.setCreativeTab(TrinityTab.TRINITY_TAB);
		
		compound_barium_nitrate = new BasicItem("compound_barium_nitrate");
		compound_barium_nitrate.setCreativeTab(TrinityTab.TRINITY_TAB);
		
		compound_barium_oxide = new BasicItem("compound_barium_oxide");
		compound_barium_oxide.setCreativeTab(TrinityTab.TRINITY_TAB);
		
		compound_lithium_deuteride = new BasicItem("compound_lithium_deuteride");
		compound_lithium_deuteride.setCreativeTab(TrinityTab.TRINITY_TAB);
		
		dust_au_198 = new BasicItem("dust_au_198");
		dust_au_198.setCreativeTab(TrinityTab.TRINITY_TAB);
		
		ingot_au_198 = new BasicItem("ingot_au_198");
		ingot_au_198.setCreativeTab(TrinityTab.TRINITY_TAB);
		// if(Trinity.QMDLoaded)
		// {
		// source_au_198 = new RadioactiveSource("source_au_198", RadiationHandler.GOLD_198/2);
		// source_au_198.setCreativeTab(TrinityTab.TRINITY_TAB);
		// }
		// else if(!Trinity.QMDLoaded)
		// {
		source_au_198 = new RadioactiveSource2("source_au_198", RadiationHandler.GOLD_198 / 2);
		source_au_198.setCreativeTab(TrinityTab.TRINITY_TAB);
		// }
		bomb_pit_u233 = new BasicItem("u233_pit");
		bomb_pit_u233.setCreativeTab(TrinityTab.TRINITY_TAB);
		bomb_pit_u233.setMaxStackSize(1);
		
		bomb_pit_u235 = new BasicItem("u235_pit");
		bomb_pit_u235.setCreativeTab(TrinityTab.TRINITY_TAB);
		bomb_pit_u235.setMaxStackSize(1);
		
		bomb_pit_np237 = new BasicItem("np237_pit");
		bomb_pit_np237.setCreativeTab(TrinityTab.TRINITY_TAB);
		bomb_pit_np237.setMaxStackSize(1);
		
		bomb_pit_pu239 = new BasicItem("pu239_pit");
		bomb_pit_pu239.setCreativeTab(TrinityTab.TRINITY_TAB);
		bomb_pit_pu239.setMaxStackSize(1);
		
		bomb_pit_am242 = new BasicItem("am242_pit");
		bomb_pit_am242.setCreativeTab(TrinityTab.TRINITY_TAB);
		bomb_pit_am242.setMaxStackSize(1);
		
		bomb_pit_cm247 = new BasicItem("cm247_pit");
		bomb_pit_cm247.setCreativeTab(TrinityTab.TRINITY_TAB);
		bomb_pit_cm247.setMaxStackSize(1);
		
		bomb_pit_bk248 = new BasicItem("bk248_pit");
		bomb_pit_bk248.setCreativeTab(TrinityTab.TRINITY_TAB);
		bomb_pit_bk248.setMaxStackSize(1);
		
		bomb_pit_cf249 = new BasicItem("cf249_pit");
		bomb_pit_cf249.setCreativeTab(TrinityTab.TRINITY_TAB);
		bomb_pit_cf249.setMaxStackSize(1);
		
		bomb_pit_cf251 = new BasicItem("cf251_pit");
		bomb_pit_cf251.setCreativeTab(TrinityTab.TRINITY_TAB);
		bomb_pit_cf251.setMaxStackSize(1);
		
		detonator = new ItemDetonator("detonator");
		detonator.setCreativeTab(TrinityTab.TRINITY_TAB);
		detonator.setMaxStackSize(1);
		
		neutron_initiator = new RadioactiveSource2("neutron_initiator", RadiationHandler.INITIATOR);
		neutron_initiator.setCreativeTab(TrinityTab.TRINITY_TAB);
		neutron_initiator.setMaxStackSize(1);
	}
	
	// Register
	public static void register() {
		registerItem(trinitite);
		registerItem(gem_witherite);
		registerItem(gem_dust_witherite);
		registerItem(barium);
		registerItem(compound_barium_nitrate);
		registerItem(compound_barium_oxide);
		registerItem(compound_lithium_deuteride);
		registerItem(dust_au_198);
		registerItem(ingot_au_198);
		registerItem(source_au_198);
		
		registerItem(bomb_pit_u233);
		registerItem(bomb_pit_u235);
		registerItem(bomb_pit_np237);
		registerItem(bomb_pit_pu239);
		registerItem(bomb_pit_am242);
		registerItem(bomb_pit_cm247);
		registerItem(bomb_pit_bk248);
		registerItem(bomb_pit_cf249);
		registerItem(bomb_pit_cf251);
		
		registerItem(detonator);
		registerItem(neutron_initiator);
	}
	
	public static void registerRenders() {
		registerRender(trinitite);
		registerRender(gem_witherite);
		registerRender(gem_dust_witherite);
		registerRender(barium);
		registerRender(compound_barium_nitrate);
		registerRender(compound_barium_oxide);
		registerRender(compound_lithium_deuteride);
		registerRender(dust_au_198);
		registerRender(ingot_au_198);
		registerRender(source_au_198);
		
		registerRender(bomb_pit_u233);
		registerRender(bomb_pit_u235);
		registerRender(bomb_pit_np237);
		registerRender(bomb_pit_pu239);
		registerRender(bomb_pit_am242);
		registerRender(bomb_pit_cm247);
		registerRender(bomb_pit_bk248);
		registerRender(bomb_pit_cf249);
		registerRender(bomb_pit_cf251);
		
		registerRender(detonator);
		registerRender(neutron_initiator);
	}
	
	public static void registerItem(Item item) {
		ForgeRegistries.ITEMS.register(item);
		
	}
	
	public static void registerRender(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
		
	}
	
	/*	public static void registerRender(Item item, int meta, String fileName) {
			ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation(Reference.MODID, fileName), "inventory"));
		}*/
}
