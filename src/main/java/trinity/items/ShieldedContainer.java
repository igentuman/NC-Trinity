package trinity.items;

import java.util.List;

import javax.annotation.Nullable;

import nc.config.NCConfig;
import nc.radiation.RadiationHelper;
import nc.util.Lang;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import trinity.Global;

public class ShieldedContainer extends Item {
	
	public static double rads;
	private double resistance;
	
	private static final String RADIATION = Lang.localise("item.nuclearcraft.rads");
	
	public ShieldedContainer(String nameIn, double radioresistance) {
		this.setTranslationKey(Global.MOD_ID + "." + nameIn);
		this.resistance = radioresistance;
		this.setRegistryName(new ResourceLocation(Global.MOD_ID, nameIn));
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (this.rads > NCConfig.radiation_lowest_rate) {
			tooltip.add(RadiationHelper.getRadiationTextColor(this.rads * stack.getCount()) + RADIATION + " " + RadiationHelper.radsPrefix(this.rads * stack.getCount(), true));
		}
	}
	
	/* public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		EntityLivingBase living = ((EntityLivingBase)entity);
		IEntityRads playerRads = RadiationHelper.getEntityRadiation(living);
		playerRads.setRadiationLevel(RadiationHelper.addRadsToEntity(playerRads, living, playerRads.getRawRadiationLevel()+(this.rads*stack.getCount()), false, false, NCConfig.radiation_player_tick_rate/4));
		//if(!world.isRemote)
		//{
		//RadiationHelper.transferRadsFromInventoryToPlayer(arg0, arg1, arg2)
			//RadiationHelper.addRadsToEntity(playerRads, living, this.rads*stack.getCount(), false, false, NCConfig.radiation_player_tick_rate/4);
		//RadiationHelper.addRadsToEntity(playerRads, living, this.rads, false, false, NCConfig.radiation_player_tick_rate/2));
		//}
		//if(world.isRemote)
		//{
			//playerRads.setRadiationLevel(this.rads*stack.getCount());
		//}
	}*/
}
