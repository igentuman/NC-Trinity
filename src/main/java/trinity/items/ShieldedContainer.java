package trinity.items;

import nc.config.NCConfig;
import nc.radiation.RadiationHelper;
import nc.util.Lang;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import trinity.Global;

import javax.annotation.Nullable;
import java.util.List;

public class ShieldedContainer extends Item{

	public static double rads;
	private double resistance;
	
	private static final String RADIATION = Lang.localise("item.nuclearcraft.rads");
	
	public ShieldedContainer(String nameIn, double radioresistance) {
		this.setTranslationKey(Global.MOD_ID + "." + nameIn);
		this.resistance = radioresistance;
		this.setRegistryName(new ResourceLocation(Global.MOD_ID, nameIn));
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if(this.rads>NCConfig.radiation_lowest_rate)
		{
			tooltip.add(RadiationHelper.getRadiationTextColor(this.rads*stack.getCount()) + RADIATION + " " + RadiationHelper.radsPrefix(this.rads*stack.getCount(), true));
		}
	}
}
