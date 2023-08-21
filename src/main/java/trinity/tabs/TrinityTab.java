package trinity.tabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import trinity.init.ModBlocks;

import java.util.Comparator;

public class TrinityTab extends CreativeTabs {

	public TrinityTab() {
		super("Trinity_Tab");
	}	
	
	@Override
	public ItemStack createIcon() {
		return new ItemStack(ModBlocks.bomb_u235);

	}

	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> p_78018_1_) {
		super.displayAllRelevantItems(p_78018_1_);
		p_78018_1_.sort(new Comparator<ItemStack>() {

			@Override
			public int compare(ItemStack o1, ItemStack o2) {

				return o1.getDisplayName().compareTo(o2.getDisplayName());

			}
		});
	}

	public static final CreativeTabs TRINITY_TAB = new TrinityTab();
}
