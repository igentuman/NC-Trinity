package trinity.proxy;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import trinity.Global;
import trinity.config.TrinityConfig;
import trinity.entities.*;
import trinity.fluid.TrinityFluids;
import trinity.handler.INuclearEffect;
import trinity.init.ClientEvents;
import trinity.init.ModBlocks;
import trinity.init.ModItems;
import trinity.render.AdvancedModelLoader;
import trinity.render.HmfModelLoader;
import trinity.render.entity.*;

import java.util.List;

public class ClientProxy extends CommonProxy {

private final Minecraft mc = Minecraft.getMinecraft();
	//private boolean nukeEffects = false;
	@Override
	public void preInit(FMLPreInitializationEvent preEvent) {
		super.preInit(preEvent);

		TrinityConfig.clientPreInit();

		ModItems.registerRenders();
		ModBlocks.registerRenders();
		MinecraftForge.EVENT_BUS.register(new ClientEvents());
		TrinityFluids.renderFluids();
	}

	@Override
	public void registerRenderInfo()
	{
		MinecraftForge.EVENT_BUS.register(new NuclearRender());
		AdvancedModelLoader.registerModelHandler(new HmfModelLoader());
		RenderingRegistry.registerEntityRenderingHandler(EntityNuclearCloud.class, new RenderSmallNukeMK3Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityFalloutRain.class, new RenderFalloutRainFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntityDirtyBomb.class, new RenderDirtyBombFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntityThermalBlast.class, new RenderThermalBlastFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntityBlackHole.class, new RenderBlackHoleFactory());
	}
	
	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}

	@Override
	public void postInit(FMLPostInitializationEvent postEvent) {
		super.postInit(postEvent);
		
	}
	
	public class RenderSmallNukeMK3Factory implements IRenderFactory<EntityNuclearCloud> {

		@Override
		public Render<? super EntityNuclearCloud> createRenderFor(RenderManager manager) {
			return new RenderNuclearExplosion(manager);
		}
	}
	public class RenderBlackHoleFactory implements IRenderFactory<EntityBlackHole> {

		@Override
		public Render<? super EntityBlackHole> createRenderFor(RenderManager manager) {
			return new RenderBlackHole(manager);
		}
	}
	public class RenderDirtyBombFactory implements IRenderFactory<EntityDirtyBomb> {

		@Override
		public Render<? super EntityDirtyBomb> createRenderFor(RenderManager manager) {
			return new RenderPrimedDirtyBomb(manager);
		}
	}
	
	public class NuclearRender
	{
		@SubscribeEvent(priority = EventPriority.LOWEST)
		public void renderNuclearEffects(RenderWorldLastEvent event)
		{
			List<Entity> list = Minecraft.getMinecraft().world.loadedEntityList;
			//nukeEffects = true;
			for(Entity entity : list) {
				if(entity instanceof INuclearEffect)
				{
					float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
					double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks;
					double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks;
					double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks;
					float f = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks;
					Entity entity2 = Minecraft.getMinecraft().getRenderViewEntity();
					double d3 = entity2.lastTickPosX + (entity2.posX - entity2.lastTickPosX) * (double) partialTicks;
					double d4 = entity2.lastTickPosY + (entity2.posY - entity2.lastTickPosY) * (double) partialTicks;
					double d5 = entity2.lastTickPosZ + (entity2.posZ - entity2.lastTickPosZ) * (double) partialTicks;

					Render<Entity> render = Minecraft.getMinecraft().getRenderManager().getEntityRenderObject(entity);
					render.doRender(entity, d0 - d3, d1 - d4, d2 - d5, f, partialTicks);
				}
			}
			//nukeEffects = false;
		}
		
	}
	
	@Override
	public void registerFluidBlockRendering(Block block, String name)
	{
		super.registerFluidBlockRendering(block, name);
		FluidStateMapper mapper = new FluidStateMapper(name);

		Item item = Item.getItemFromBlock(block);
		ModelBakery.registerItemVariants(item);
		ModelLoader.setCustomMeshDefinition(item, mapper);

		// ModelLoader.setCustomStateMapper(block, new
		// StateMap.Builder().ignore(block.LEVEL).build());
		ModelLoader.setCustomStateMapper(block, mapper);
	}

	public static class FluidStateMapper extends StateMapperBase implements ItemMeshDefinition
	{
		public final ModelResourceLocation location;

		public FluidStateMapper(String name)
		{
			location = new ModelResourceLocation(Global.MOD_ID + ":fluids", name);
		}

		@Override
		protected ModelResourceLocation getModelResourceLocation(IBlockState state)
		{
			return location;
		}

		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack)
		{
			return location;
		}
	}
}