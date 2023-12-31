package trinity.proxy;

import java.util.Locale;

import net.minecraft.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLModIdMappingEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import trinity.Trinity;
import trinity.fluid.TrinityFluids;
import trinity.gui.GuiHandlerRegistry;
import trinity.gui.GuiHandlerTrinity;
import trinity.handler.OredictHandler;
import trinity.init.CommonEvents;
import trinity.init.ICBMEvents;
import trinity.init.ModBlocks;
import trinity.init.ModItems;
import trinity.init.TrinityEntities;
import trinity.init.TrinityRecipes;
import trinity.radiation.RadiationHandler;
import trinity.tiles.TileEntityShieldedContainer;
import trinity.world.TrinityBiomes;

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
