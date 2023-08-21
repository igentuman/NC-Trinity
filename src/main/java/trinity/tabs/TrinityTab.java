package trinity.tabs;

import java.util.Comparator;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import trinity.init.ModBlocks;

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
	
	/*	@Override
		public void displayAllRelevantItems(NonNullList<ItemStack> p_78018_1_) {
			super.displayAllRelevantItems(p_78018_1_);
			
			List<Item> order = Arrays.asList(ModItems.bomb_pit_u233,ModItems.bomb_pit_u235,ModItems.bomb_pit_np237,ModItems.bomb_pit_pu239,ModItems.bomb_pit_am242,ModItems.bomb_pit_cm247,ModItems.bomb_pit_bk248,ModItems.bomb_pit_cf249,ModItems.bomb_pit_cf251,Item.getItemFromBlock(ModBlocks.baratol),Item.getItemFromBlock(ModBlocks.compression_charge),Item.getItemFromBlock(ModBlocks.core_u233),Item.getItemFromBlock(ModBlocks.core_u235),Item.getItemFromBlock(ModBlocks.core_np237),Item.getItemFromBlock(ModBlocks.core_pu239),Item.getItemFromBlock(ModBlocks.core_am242),Item.getItemFromBlock(ModBlocks.core_cm247),Item.getItemFromBlock(ModBlocks.core_bk248),Item.getItemFromBlock(ModBlocks.core_cf249),Item.getItemFromBlock(ModBlocks.core_cf251),Item.getItemFromBlock(ModBlocks.bomb_u233),Item.getItemFromBlock(ModBlocks.bomb_u235),Item.getItemFromBlock(ModBlocks.bomb_np237),Item.getItemFromBlock(ModBlocks.bomb_pu239),Item.getItemFromBlock(ModBlocks.bomb_am242),Item.getItemFromBlock(ModBlocks.bomb_cm247),Item.getItemFromBlock(ModBlocks.bomb_bk248),Item.getItemFromBlock(ModBlocks.bomb_cf249),Item.getItemFromBlock(ModBlocks.bomb_cf251),Item.getItemFromBlock(ModBlocks.salted_core_u233),Item.getItemFromBlock(ModBlocks.salted_core_u235),Item.getItemFromBlock(ModBlocks.salted_core_np237),Item.getItemFromBlock(ModBlocks.salted_core_pu239),Item.getItemFromBlock(ModBlocks.salted_core_am242),Item.getItemFromBlock(ModBlocks.salted_core_cm247),Item.getItemFromBlock(ModBlocks.salted_core_bk248),Item.getItemFromBlock(ModBlocks.salted_core_cf249),Item.getItemFromBlock(ModBlocks.salted_core_cf251),Item.getItemFromBlock(ModBlocks.salted_bomb_u233),Item.getItemFromBlock(ModBlocks.salted_bomb_u235),Item.getItemFromBlock(ModBlocks.salted_bomb_np237),Item.getItemFromBlock(ModBlocks.salted_bomb_pu239),Item.getItemFromBlock(ModBlocks.salted_bomb_am242),Item.getItemFromBlock(ModBlocks.salted_bomb_cm247),Item.getItemFromBlock(ModBlocks.salted_bomb_bk248),Item.getItemFromBlock(ModBlocks.salted_bomb_cf249),Item.getItemFromBlock(ModBlocks.salted_bomb_cf251),ModItems.detonator,Item.getItemFromBlock(ModBlocks.trinitite),Item.getItemFromBlock(ModBlocks.solid_trinitite),Item.getItemFromBlock(ModBlocks.radioactive_earth),ModItems.trinitite,ModItems.gem_witherite,ModItems.gem_dust_witherite,ModItems.compound_barium_nitrate);
			Comparator<Item> c = Ordering.explicit(order);
			
			Trinity.trinityOrder = Ordering.explicit(order).onResultOf(ItemStack::getItem);
			p_78018_1_.sort(Trinity.trinityOrder){
			
			@Override
			public int compare(ItemStack o1, ItemStack o2) {
	
				return o1.getDisplayName().compareTo(o2.getDisplayName());
	
			}
		}*/
	
	public static final CreativeTabs TRINITY_TAB = new TrinityTab();
}
