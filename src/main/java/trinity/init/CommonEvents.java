package trinity.init;

import nc.capability.radiation.entity.IEntityRads;
import nc.config.NCConfig;
import nc.network.radiation.PlayerRadsUpdatePacket;
import nc.radiation.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.*;
import trinity.Reference;
import trinity.items.*;
import trinity.radiation.FalloutSavedData;
import trinity.tiles.TileEntityShieldedContainer;

import java.util.List;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class CommonEvents {
	
	public static String Pu_238 = "c95fdfd3-bea7-4255-a44b-d21bc3df95e3";
	
	@SubscribeEvent(priority = EventPriority.LOW)
	@SideOnly(Side.SERVER)
	public void sealedRadiationSources(TickEvent.PlayerTickEvent event) {
		if (!NCConfig.radiation_enabled_public) {
			return;
		}
		
		if (event.phase != TickEvent.Phase.START || ((event.player.world.getTotalWorldTime() + event.player.getUniqueID().hashCode()) % NCConfig.radiation_player_tick_rate) != 0) {
			return;
		}
		
		if (event.player instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) event.player;
			
			IEntityRads playerRads = RadiationHelper.getEntityRadiation(player);
			
			if (player.getUniqueID().toString().equals(Pu_238)) {
				if (playerRads.getInternalRadiationResistance() < 500) {
					playerRads.setInternalRadiationResistance(2800);
				}
			}
			
			double radiationLevel = 0D;
			FalloutSavedData data = FalloutSavedData.getData(event.player.world);
			if (data.worldObj == null) {
				data.worldObj = event.player.world;
			}
			
			radiationLevel += data.getRadNumFromCoord((int) player.posX, (int) player.posZ);
			List Tes = player.world.loadedTileEntityList;
			for (int i = 0; i < Tes.size(); ++i) {
				TileEntity tile = (TileEntity) Tes.get(i);
				int tileDim = tile.getWorld().provider.getDimension();
				int playerDim = player.getEntityWorld().provider.getDimension();
				if (tile instanceof TileEntityShieldedContainer && (tileDim == playerDim)) {
					TileEntityShieldedContainer te = ((TileEntityShieldedContainer) tile);
					double X = player.posX;
					double Y = player.posY;
					double Z = player.posZ;
					double dist = te.getDistanceSq(X, Y, Z);
					radiationLevel += Math.min(te.getRadiation(), (te.getRadiation() / dist));
					// System.out.println("Radioactivity: "+te.getRadioactivity()+" Rad/t");
				}
			}
			if (!player.getUniqueID().toString().equals(Pu_238)) {
				List entities = player.world.playerEntities;
				for (int i = 0; i < entities.size(); ++i) {
					EntityPlayer p = (EntityPlayer) entities.get(i);
					if (p.getUniqueID().toString().equals(Pu_238)) {
						double X = player.posX;
						double Y = player.posY;
						double Z = player.posZ;
						double dist = p.getDistanceSq(X, Y, Z);
						radiationLevel += Math.min(RadSources.PLUTONIUM_238, (RadSources.PLUTONIUM_238 / dist));
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
					if (item instanceof RadioactiveSource2) {
						radiationLevel += (((RadioactiveSource2) item).rads) * stack.getCount();
						// System.out.println("Radioactivity: "+radiationLevel);
					}
					
					if (item instanceof ShieldedContainerItem) {
						radiationLevel += (((ShieldedContainerItem) item).getRadiation(stack)) * stack.getCount();
					}
				}
			}
			double appliedRads = RadiationHelper.addRadsToEntity(playerRads, player, radiationLevel, false, false, NCConfig.radiation_player_tick_rate);
			playerRads.setRadiationLevel(playerRads.getRadiationLevel() + appliedRads);
			// System.out.println("Radiation: "+playerRads.getRadiationLevel()+" Rad/t");
			// System.out.println("Adjusted Radiation: "+playerRads.getRadiationLevel()/3+" Rad/t");
			new PlayerRadsUpdatePacket(playerRads).sendTo(player);
		}
	}
}
