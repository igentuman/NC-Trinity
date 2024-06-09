package trinity.items;

import trinity.Reference;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class BasicItem extends Item {
	
	public BasicItem(String nameIn) {
		this.setTranslationKey(Reference.MOD_ID + "." + nameIn);
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, nameIn));
	}
}
