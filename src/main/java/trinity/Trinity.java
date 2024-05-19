package trinity;

import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import trinity.config.TrinityConfig;
import trinity.gui.GuiHandlerRegistry;
import trinity.world.ChunkLoader;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, dependencies = Reference.REQUIREMENT)

public class Trinity {
	
	static {
		FluidRegistry.enableUniversalBucket();
	}
	
	@Instance
	public static Trinity instance;
	
	@SidedProxy(clientSide = "trinity.proxy.ClientProxy", serverSide = "trinity.proxy.CommonProxy")
	public static trinity.proxy.CommonProxy proxy;
	
	// public static Comparator<ItemStack> trinityOrder;
	
	public static boolean TCLoaded;
	
	public static boolean ICBMLoaded;
	
	// public static boolean DELoaded;
	
	public static boolean QMDLoaded;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent preEvent) {
		TCLoaded = Loader.isModLoaded("thaumcraft");
		ICBMLoaded = Loader.isModLoaded("icbmclassic");
		QMDLoaded = Loader.isModLoaded("qmd");
		ForgeChunkManager.setForcedChunkLoadingCallback(Trinity.instance, ChunkLoader.getInstance());
		TrinityConfig.preInit();
		proxy.preInit(preEvent);
		proxy.registerRenderInfo();
		
		// trinityOrder = Ordering<ItemStack>.explicit(valuesInOrder)
	}
	
	@EventHandler
	public void Init(FMLInitializationEvent event) {
		proxy.init(event);
		// NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		NetworkRegistry.INSTANCE.registerGuiHandler(this, GuiHandlerRegistry.getInstance());
	}
	
	@EventHandler
	public void PostInit(FMLPostInitializationEvent postEvent) {
		proxy.postInit(postEvent);
		
	}
	
	@EventHandler
	public void onIdMapping(FMLModIdMappingEvent idMappingEvent) {
		proxy.onIdMapping(idMappingEvent);
	}
}
