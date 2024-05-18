package trinity.items;

import nc.config.NCConfig;
import nc.radiation.RadiationHelper;
import nc.util.*;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.world.World;
import trinity.blocks.ShieldedContainer;

import java.util.List;

public class ShieldedContainerItem extends ItemBlock {
	
	// public double rads;
	private static final String RADIATION = Lang.localize("item.nuclearcraft.rads");
	
	public ShieldedContainerItem(Block block) {
		super(block);
	}
	
	// @Override
	// public int getItemBurnTime(ItemStack itemStack) {
	// return 4000;
	// }
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		// rads = ShieldedContainer.rads;
		double rads = getRadiation(stack);
		if (rads > 0 && rads >= NCConfig.radiation_lowest_rate) {
			tooltip.add(RadiationHelper.getRadiationTextColor(rads * stack.getCount()) + RADIATION + " " + RadiationHelper.radsPrefix(rads * stack.getCount(), true));
		}
		// tooltip.add("Light Shielding: "+NCConfig.radiation_shielding_level[0]);
		// tooltip.add("Medium Shielding: "+NCConfig.radiation_shielding_level[1]);
		// tooltip.add("Heavy Shielding: "+NCConfig.radiation_shielding_level[2]);
	}
	
	public void setRadioactivity(ItemStack stack, double radioactivity) {
		if (stack.getTagCompound() == null) {
		
		}
	}
	
	public double getRadioactivity(ItemStack stack) {
		if (stack.hasTagCompound()) {
			double radioactivity = stack.getTagCompound().getDouble("Radioactivity");
			// System.out.println("Radioactivity: "+radioactivity);
			return radioactivity;
		}
		else {// if(radioactivity != null)
			return 0;
		}
	}
	
	public double getShielding() {
		Block block = this.block;
		if (block instanceof ShieldedContainer) {
			ShieldedContainer shield = ((ShieldedContainer) block);
			return shield.shielding;
		}
		return 0;
	}
	
	public double getRadiation(ItemStack stack) {
		double rads = getRadioactivity(stack);
		double shieldedRads = 0D;
		shieldedRads = NCMath.sq(rads) / (rads + (Math.exp(getShielding()) - 1));
		// System.out.println("Emitted Radiation: "+shieldedRads+" Rad/t");
		return shieldedRads;
	}
}
