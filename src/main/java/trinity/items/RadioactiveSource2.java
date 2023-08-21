package trinity.items;

import nc.capability.radiation.source.IRadiationSource;
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

public class RadioactiveSource2 extends Item{

	public double rads;
	
	private static final String RADIATION = Lang.localise("item.nuclearcraft.rads");
	
	public RadioactiveSource2(String nameIn, double radioactivity) {
		this.setTranslationKey(Global.MOD_ID + "." + nameIn);
		this.rads = radioactivity;
		this.setRegistryName(new ResourceLocation(Global.MOD_ID, nameIn));
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		IRadiationSource stackSource = RadiationHelper.getRadiationSource(stack);
		if(stackSource==null)
		{
			if(this.rads>NCConfig.radiation_lowest_rate)
			{
				tooltip.add(RadiationHelper.getRadiationTextColor(this.rads*stack.getCount()) + RADIATION + " " + RadiationHelper.radsPrefix(this.rads*stack.getCount(), true));
			}
		}
	}
}
