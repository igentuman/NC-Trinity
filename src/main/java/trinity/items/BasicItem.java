package trinity.items;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import trinity.Global;

public class BasicItem extends Item {
	
	public BasicItem(String nameIn) {
		this.setTranslationKey(Global.MOD_ID + "." + nameIn);
		this.setRegistryName(new ResourceLocation(Global.MOD_ID, nameIn));
	}
}
