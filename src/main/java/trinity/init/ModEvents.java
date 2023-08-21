package trinity.init;


import java.util.List;

import org.lwjgl.opengl.GL11;

//import icbm.classic.api.EnumTier;
//import icbm.classic.api.refs.ICBMExplosives;
//import icbm.classic.content.blast.BlastEMP;
//import icbm.classic.content.blast.threaded.BlastAntimatter;
//import icbm.classic.content.blast.threaded.BlastNuclear;
//import icbm.classic.content.blocks.explosive.ItemBlockExplosive;
//import icbm.classic.content.items.ItemMissile;
//import icbm.classic.lib.explosive.ExplosiveHandler;
import nc.capability.radiation.entity.IEntityRads;
import nc.config.NCConfig;
import nc.init.NCFissionFluids;
import nc.network.PacketHandler;
import nc.network.radiation.PlayerRadsUpdatePacket;
import nc.radiation.RadSources;
import nc.radiation.RadiationHelper;
import trinity.Global;
import trinity.Trinity;
import trinity.blocks.BasicBlock;
//import trinity.blocks.BasicBlock2;
import trinity.blocks.ExplosiveCharge;
import trinity.blocks.NuclearCore;
import trinity.config.TrinityConfig;
import trinity.entities.EntityNuclearCloud;
import trinity.entities.EntityNuclearExplosion;
import trinity.explosion.ExplosionNukeGeneric;
import trinity.handler.INuclearEffect;
//import trinity.items.RadioactiveSource;
import trinity.items.RadioactiveSource2;
import trinity.items.ShieldedContainerItem;
import trinity.radiation.FalloutSavedData;
import trinity.render.Tessellator;
//import nca.blocks.MoissaniteBlock;
//import nca.blocks.bunkerblocks.BunkerBlocks;
//import nca.blocks.bunkerblocks.MoissaniteOre;
//import nca.blocks.bunkerblocks.TritiumLamp;
//import nca.blocks.bunkerblocks.TritiumLightFrame;
import trinity.tabs.TrinityTab;
import trinity.tiles.TileEntityShieldedContainer;
import trinity.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.Explosion;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid=Reference.MODID)
public class ModEvents {
	
	private static boolean renderingNukeEffects = false;
	public static String Pu_238 = "c95fdfd3-bea7-4255-a44b-d21bc3df95e3";
	
	@SubscribeEvent
	public void textureStitch(TextureStitchEvent.Pre evt) {
		evt.getMap().registerSprite(new ResourceLocation(Global.MOD_ID, "blocks/liquid_still"));
		evt.getMap().registerSprite(new ResourceLocation(Global.MOD_ID, "blocks/liquid_flow"));
	}
	
	@SubscribeEvent(priority=EventPriority.LOW)
	public void sealedRadiationSources(TickEvent.PlayerTickEvent event) {
		
		if (!NCConfig.radiation_enabled_public) { return; }
		
		if (event.phase != TickEvent.Phase.START ||
				((event.player.world.getTotalWorldTime() + event.player.getUniqueID().hashCode()) % 
						NCConfig.radiation_player_tick_rate) != 0) { return; }
		
		if (event.side == Side.SERVER && event.player instanceof EntityPlayerMP) {			
			EntityPlayerMP player = (EntityPlayerMP)event.player;
			
			IEntityRads playerRads = RadiationHelper.getEntityRadiation(player);
			
			if (player.getUniqueID().toString().equals(Pu_238) )
			{
				if(playerRads.getInternalRadiationResistance()<500)
				{
					playerRads.setInternalRadiationResistance(2800);
				}
			}
			
			double radiationLevel = 0D;
			FalloutSavedData data = FalloutSavedData.getData(event.player.world);
			if(data.worldObj == null) {
				data.worldObj = event.player.world;
			}
			
			radiationLevel += data.getRadNumFromCoord((int)player.posX, (int)player.posZ);
			List Tes = player.world.loadedTileEntityList;
			for (int i = 0; i < Tes.size(); ++i)
			{
				TileEntity tile = (TileEntity) Tes.get(i);
				int tileDim = tile.getWorld().provider.getDimension();
				int playerDim = player.getEntityWorld().provider.getDimension();
				if(tile instanceof TileEntityShieldedContainer && (tileDim == playerDim))
				{
					TileEntityShieldedContainer te = ((TileEntityShieldedContainer)tile);
					double X = player.posX;
					double Y = player.posY;
					double Z = player.posZ;
					double dist = te.getDistanceSq(X, Y, Z);
					radiationLevel += Math.min(te.getRadiation(), (te.getRadiation()/dist));
					//System.out.println("Radioactivity: "+te.getRadioactivity()+" Rad/t");
				}
				//if(event.player.world.getTileEntity(pos))
			}
			if (!player.getUniqueID().toString().equals(Pu_238))
			{
				List entities = player.world.playerEntities;
				for (int i = 0; i < entities.size(); ++i)
				{
					EntityPlayer p = (EntityPlayer) entities.get(i);
					if (p.getUniqueID().toString().equals(Pu_238) )
					{
						double X = player.posX;
						double Y = player.posY;
						double Z = player.posZ;
						double dist = p.getDistanceSq(X, Y, Z);
						radiationLevel += Math.min(RadSources.PLUTONIUM_238, (RadSources.PLUTONIUM_238/dist));
					}
				}
			}

			InventoryPlayer inventory = player.inventory;
			for (ItemStack stack : inventory.mainInventory) {
				if (!stack.isEmpty()) {
					Item item = stack.getItem();
					/*if(Trinity.QMDLoaded)
					{
						if(item instanceof RadioactiveSource)
						{
							radiationLevel += (((RadioactiveSource)item).rads)*stack.getCount();
							//System.out.println("Radioactivity: "+radiationLevel);
						}
					}*/
					if(item instanceof RadioactiveSource2)
					{
						radiationLevel += (((RadioactiveSource2)item).rads)*stack.getCount();
						//System.out.println("Radioactivity: "+radiationLevel);
					}
					
					if(item instanceof ShieldedContainerItem)
					{
						radiationLevel += (((ShieldedContainerItem)item).getRadiation(stack))*stack.getCount();
					}
				}
			}
			double appliedRads = RadiationHelper.addRadsToEntity(playerRads, player, radiationLevel, false, false, NCConfig.radiation_player_tick_rate);
			playerRads.setRadiationLevel(playerRads.getRadiationLevel() + appliedRads);
			//System.out.println("Radiation: "+playerRads.getRadiationLevel()+" Rad/t");
			//System.out.println("Adjusted Radiation: "+playerRads.getRadiationLevel()/3+" Rad/t");
			PacketHandler.instance.sendTo(new PlayerRadsUpdatePacket(playerRads), player);
		}
	}
	/*@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void addAdditionalTooltips(ItemTooltipEvent event) {
		if(Trinity.ICBMLoaded)
		{
			ItemStack stack = event.getItemStack();
			Item item = stack.getItem();
			if(item instanceof ItemBlockExplosive || item instanceof ItemMissile)
			{
				if(stack.getItemDamage() == ICBMExplosives.NUCLEAR.getRegistryID())
				{
					addNukeTooltip(event.getToolTip(), stack);
				}
			//if(((ItemBlockExplosive)item).
			}
		}
	
	}*/
	
	@SideOnly(Side.CLIENT)
	private static void addNukeTooltip(List<String> tooltip, ItemStack stack) {
		tooltip.add(TextFormatting.RED+"Warning: Trinity installed. ICBM nuclear explosives cause Trinity's explosions instead of ICBM's");
	}
	
	/*@SubscribeEvent
	public void explosion(ExplosionEvent.Start event)
	{
		if(Trinity.ICBMLoaded)
		{
			Explosion exp = event.getExplosion();
			if(exp instanceof BlastNuclear)
			{
				if(((BlastNuclear)exp).getExplosiveData().getTier()==EnumTier.THREE)
				{
					event.setCanceled(true);
					EntityNuclearCloud entity2 = new EntityNuclearCloud(exp.world, 1000, (Math.min(TrinityConfig.icbm_radius,TrinityConfig.max_radius)*2) * 0.005F);
					entity2.posX = exp.x;
					entity2.posY = exp.y;
					entity2.posZ = exp.z;
					exp.world.spawnEntity(entity2);
				//	exp.world.setBlockToAir(pos);
					new BlastEMP().setBlastWorld(event.getWorld()).setBlastSource(exp.exploder).setBlastPosition(((BlastNuclear) exp).location.x(), ((BlastNuclear) exp).location.y(), ((BlastNuclear) exp).location.z())
                    .setBlastSize(((BlastNuclear) exp).getBlastRadius()*2)
                    .setExplosiveData(ICBMExplosives.EMP)
                    .buildBlast().runBlast();
					ExplosionNukeGeneric.irradiate(exp.world, (int)exp.x, (int)exp.y, (int)exp.z, Math.min(TrinityConfig.icbm_radius,TrinityConfig.max_radius)*2);
					exp.world.spawnEntity(EntityNuclearExplosion.statFac(exp.world, Math.min(TrinityConfig.icbm_radius,TrinityConfig.max_radius), exp.x + 0.0, exp.y + 0.0, exp.z + 0.0));
					//System.out.println("If you are seeing this line, this means your blast detector is at least partially working.");
				}
			}
			/*else if(exp instanceof BlastAntimatter)
			{
				//if(((BlastNuclear)exp).getExplosiveData().getTier()==EnumTier.THREE)
				//{
					event.setCanceled(true);
					EntityNuclearCloud entity2 = new EntityNuclearCloud(exp.world, 1000, (Math.min(TrinityConfig.antimatter_radius,TrinityConfig.max_radius)*2) * 0.005F);
					entity2.posX = exp.x;
					entity2.posY = exp.y;
					entity2.posZ = exp.z;
					exp.world.spawnEntity(entity2);
				//	exp.world.setBlockToAir(pos);
					ExplosionNukeGeneric.irradiate(exp.world, (int)exp.x, (int)exp.y, (int)exp.z, Math.min(TrinityConfig.antimatter_radius,TrinityConfig.max_radius)*6);
					exp.world.spawnEntity(EntityNuclearExplosion.statFacAntimatter(exp.world, Math.min(TrinityConfig.antimatter_radius,TrinityConfig.max_radius), exp.x + 0.0, exp.y + 0.0, exp.z + 0.0));
					new BlastEMP().setBlastWorld(event.getWorld()).setBlastSource(exp.exploder).setBlastPosition(((BlastNuclear) exp).location.x(), ((BlastNuclear) exp).location.y(), ((BlastNuclear) exp).location.z())
                    .setBlastSize(((BlastNuclear) exp).getBlastRadius()*2)
                    .setExplosiveData(ICBMExplosives.EMP)
                    .buildBlast().runBlast();
					//System.out.println("If you are seeing this line, this means your blast detector is at least partially working.");
				//}
			}
		}
	}*/
}



