package trinity.radiation;

import nc.radiation.*;
import trinity.init.*;
import trinity.world.TrinityBiomes;

public class RadiationHandler {
	
	public static final double INITIATOR = RadSources.POLONIUM / 1000;
	
	public static final double D_T = RadSources.TRITIUM / 2;
	
	public static final double URANIUM_233_PIT = RadSources.getFuelRadiation(RadSources.URANIUM_233, 8, (INITIATOR / 900), 1);
	public static final double URANIUM_235_PIT = RadSources.getFuelRadiation(RadSources.URANIUM_235, 8, (INITIATOR / 900), 1);
	public static final double NEPTUNIUM_237_PIT = RadSources.getFuelRadiation(RadSources.NEPTUNIUM_237, 8, (INITIATOR / 900), 1);
	public static final double PLUTONIUM_239_PIT = RadSources.getFuelRadiation(RadSources.PLUTONIUM_239, 8, (INITIATOR / 900), 1);
	public static final double AMERICIUM_242_PIT = RadSources.getFuelRadiation(RadSources.AMERICIUM_242, 8, (INITIATOR / 900), 1);
	public static final double CURIUM_247_PIT = RadSources.getFuelRadiation(RadSources.CURIUM_247, 8, (INITIATOR / 900), 1);
	public static final double BERKELIUM_248_PIT = RadSources.getFuelRadiation(RadSources.BERKELIUM_248, 8, (INITIATOR / 900), 1);
	public static final double CALIFORNIUM_249_PIT = RadSources.getFuelRadiation(RadSources.CALIFORNIUM_249, 8, (INITIATOR / 900), 1);
	public static final double CALIFORNIUM_251_PIT = RadSources.getFuelRadiation(RadSources.CALIFORNIUM_251, 8, (INITIATOR / 900), 1);
	
	public static final double URANIUM_233_CORE = ((URANIUM_233_PIT / 1000) + (RadSources.URANIUM_238 * 8));
	public static final double URANIUM_235_CORE = ((URANIUM_235_PIT / 1000) + (RadSources.URANIUM_238 * 8));
	public static final double NEPTUNIUM_237_CORE = ((NEPTUNIUM_237_PIT / 1000) + (RadSources.URANIUM_238 * 8));
	public static final double PLUTONIUM_239_CORE = ((PLUTONIUM_239_PIT / 1000) + (RadSources.URANIUM_238 * 8));
	public static final double AMERICIUM_242_CORE = ((AMERICIUM_242_PIT / 1000) + (RadSources.URANIUM_238 * 8));
	public static final double CURIUM_247_CORE = ((CURIUM_247_PIT / 1000) + (RadSources.URANIUM_238 * 8));
	public static final double BERKELIUM_248_CORE = ((BERKELIUM_248_PIT / 1000) + (RadSources.URANIUM_238 * 8));
	public static final double CALIFORNIUM_249_CORE = ((CALIFORNIUM_249_PIT / 1000) + (RadSources.URANIUM_238 * 8));
	public static final double CALIFORNIUM_251_CORE = ((CALIFORNIUM_251_PIT / 1000) + (RadSources.URANIUM_238 * 8));
	
	public static final double URANIUM_233_BOMB = (URANIUM_233_CORE / 100);
	public static final double URANIUM_235_BOMB = (URANIUM_235_CORE / 100);
	public static final double NEPTUNIUM_237_BOMB = (NEPTUNIUM_237_CORE / 100);
	public static final double PLUTONIUM_239_BOMB = (PLUTONIUM_239_CORE / 100);
	public static final double AMERICIUM_242_BOMB = (AMERICIUM_242_CORE / 100);
	public static final double CURIUM_247_BOMB = (CURIUM_247_CORE / 100);
	public static final double BERKELIUM_248_BOMB = (BERKELIUM_248_CORE / 100);
	public static final double CALIFORNIUM_249_BOMB = (CALIFORNIUM_249_CORE / 100);
	public static final double CALIFORNIUM_251_BOMB = (CALIFORNIUM_251_CORE / 100);
	
	public static final double TRINITITE = 0.0001;
	public static final double SALTED_EARTH = 1;
	public static final double NUCLEAR_CRATER = 0.5;
	public static final double HEAVY_SALTED_EARTH = 5;
	public static final double CONTAMINATED_OCEAN = 0.001;
	public static final double GOLD_198 = 135.428;
	
	public static void radiation() {
		
		RadSources.put(INITIATOR, ModItems.neutron_initiator);
		
		RadSources.put(URANIUM_233_PIT, ModItems.bomb_pit_u233);
		RadSources.put(URANIUM_235_PIT, ModItems.bomb_pit_u235);
		RadSources.put(NEPTUNIUM_237_PIT, ModItems.bomb_pit_np237);
		RadSources.put(PLUTONIUM_239_PIT, ModItems.bomb_pit_pu239);
		RadSources.put(AMERICIUM_242_PIT, ModItems.bomb_pit_am242);
		RadSources.put(CURIUM_247_PIT, ModItems.bomb_pit_cm247);
		RadSources.put(BERKELIUM_248_PIT, ModItems.bomb_pit_bk248);
		RadSources.put(CALIFORNIUM_249_PIT, ModItems.bomb_pit_cf249);
		RadSources.put(CALIFORNIUM_251_PIT, ModItems.bomb_pit_cf251);
		
		RadSources.put(URANIUM_233_CORE, ModBlocks.core_u233);
		RadSources.put(URANIUM_235_CORE, ModBlocks.core_u235);
		RadSources.put(NEPTUNIUM_237_CORE, ModBlocks.core_np237);
		RadSources.put(PLUTONIUM_239_CORE, ModBlocks.core_pu239);
		RadSources.put(AMERICIUM_242_CORE, ModBlocks.core_am242);
		RadSources.put(CURIUM_247_CORE, ModBlocks.core_cm247);
		RadSources.put(BERKELIUM_248_CORE, ModBlocks.core_bk248);
		RadSources.put(CALIFORNIUM_249_CORE, ModBlocks.core_cf249);
		RadSources.put(CALIFORNIUM_251_CORE, ModBlocks.core_cf251);
		
		RadSources.put(URANIUM_233_CORE, ModBlocks.salted_core_u233);
		RadSources.put(URANIUM_235_CORE, ModBlocks.salted_core_u235);
		RadSources.put(NEPTUNIUM_237_CORE, ModBlocks.salted_core_np237);
		RadSources.put(PLUTONIUM_239_CORE, ModBlocks.salted_core_pu239);
		RadSources.put(AMERICIUM_242_CORE, ModBlocks.salted_core_am242);
		RadSources.put(CURIUM_247_CORE, ModBlocks.salted_core_cm247);
		RadSources.put(BERKELIUM_248_CORE, ModBlocks.salted_core_bk248);
		RadSources.put(CALIFORNIUM_249_CORE, ModBlocks.salted_core_cf249);
		RadSources.put(CALIFORNIUM_251_CORE, ModBlocks.salted_core_cf251);
		
		RadSources.put(URANIUM_233_BOMB, ModBlocks.bomb_u233);
		RadSources.put(URANIUM_235_BOMB, ModBlocks.bomb_u235);
		RadSources.put(NEPTUNIUM_237_BOMB, ModBlocks.bomb_np237);
		RadSources.put(PLUTONIUM_239_BOMB, ModBlocks.bomb_pu239);
		RadSources.put(AMERICIUM_242_BOMB, ModBlocks.bomb_am242);
		RadSources.put(CURIUM_247_BOMB, ModBlocks.bomb_cm247);
		RadSources.put(BERKELIUM_248_BOMB, ModBlocks.bomb_bk248);
		RadSources.put(CALIFORNIUM_249_BOMB, ModBlocks.bomb_cf249);
		RadSources.put(CALIFORNIUM_251_BOMB, ModBlocks.bomb_cf251);
		
		RadSources.put(URANIUM_233_BOMB, ModBlocks.salted_bomb_u233);
		RadSources.put(URANIUM_235_BOMB, ModBlocks.salted_bomb_u235);
		RadSources.put(NEPTUNIUM_237_BOMB, ModBlocks.salted_bomb_np237);
		RadSources.put(PLUTONIUM_239_BOMB, ModBlocks.salted_bomb_pu239);
		RadSources.put(AMERICIUM_242_BOMB, ModBlocks.salted_bomb_am242);
		RadSources.put(CURIUM_247_BOMB, ModBlocks.salted_bomb_cm247);
		RadSources.put(BERKELIUM_248_BOMB, ModBlocks.salted_bomb_bk248);
		RadSources.put(CALIFORNIUM_249_BOMB, ModBlocks.salted_bomb_cf249);
		RadSources.put(CALIFORNIUM_251_BOMB, ModBlocks.salted_bomb_cf251);
		
		RadSources.putOre(TRINITITE, "oreTrinitite");
		RadSources.putOre(TRINITITE * 9, "blockTrinitite");
		RadSources.putOre(TRINITITE, "gemTrinitite");
		RadSources.putOre(SALTED_EARTH, "blockRadioactiveEarth");
		RadSources.putOre(SALTED_EARTH, "blockRadioactiveSand");
		RadSources.putOre(HEAVY_SALTED_EARTH, "blockHighlyRadioactiveEarth");
		RadSources.putOre(HEAVY_SALTED_EARTH, "blockHighlyRadioactiveSand");
		RadSources.putMaterial(GOLD_198, "Gold198");
		// RadSources.putOre(GOLD_198, "dustGold198");
		RadSources.put(RadSources.PLUTONIUM_239 * 3, ModBlocks.thermonuclear_core_pu239);
		RadSources.put(RadSources.CAESIUM_137 / 10, ModBlocks.dirty_bomb);
		RadSources.put(GOLD_198 / 10, ModBlocks.gold_bomb);
		RadBiomes.RAD_MAP.put(TrinityBiomes.NUCLEAR_CRATER, NUCLEAR_CRATER);
		RadBiomes.RAD_MAP.put(TrinityBiomes.CONTAMINATED_OCEAN, CONTAMINATED_OCEAN);
		RadSources.putFluid(D_T, "deuterium-tritium_mixture");
		// RadSources.refreshRadSources();
	}
	
}
