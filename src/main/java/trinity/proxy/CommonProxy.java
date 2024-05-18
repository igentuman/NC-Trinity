package trinity.proxy;

import net.minecraft.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.GameRegistry;
import trinity.Trinity;
import trinity.fluid.TrinityFluids;
import trinity.gui.*;
import trinity.handler.OredictHandler;
import trinity.init.*;
import trinity.radiation.RadiationHandler;
import trinity.tiles.TileEntityShieldedContainer;
import trinity.world.TrinityBiomes;

import java.util.Locale;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent preEvent) {
		ModItems.init();
		TrinityFluids.registerFluids();
		TrinityFluids.init();
		TrinityFluids.register();
		ModBlocks.init();
		ModItems.register();
		ModBlocks.register();
		OredictHandler.registerOres();
		MinecraftForge.EVENT_BUS.register(new TrinityRecipes());
		MinecraftForge.EVENT_BUS.register(new CommonEvents());
		if (Trinity.ICBMLoaded)
			MinecraftForge.EVENT_BUS.register(new ICBMEvents());
		GuiHandlerRegistry.getInstance().registerGuiHandler(new GuiHandlerTrinity(), GuiHandlerTrinity.getGuiID());
		GameRegistry.registerTileEntity(TileEntityShieldedContainer.class, "trinity:tile_inventory_basic");
		// TrinityRecipes.init();
	}
	
	public void registerRenderInfo() {
	
	}
	
	public void init(FMLInitializationEvent event) {
		RadiationHandler.radiation();
		// TrinityRecipes.init();
		TrinityEntities.register();
		TrinityBiomes.initBiomeManagerAndDictionary();
	}
	
	public void postInit(FMLPostInitializationEvent postEvent) {
	
	}
	
	public void onIdMapping(FMLModIdMappingEvent idMappingEvent) {
		// QMDRecipes.refreshRecipeCaches();
		RadiationHandler.radiation();
	}
	
	public void registerRenders() {
	
	}
	
	public void registerFluidBlockRendering(Block block, String name) {
		name = name.toLowerCase(Locale.ROOT);
	}
}
