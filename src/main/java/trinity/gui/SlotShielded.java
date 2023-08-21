package trinity.gui;

import nc.capability.radiation.source.IRadiationSource;
import nc.radiation.RadiationHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import trinity.Trinity;
//import trinity.items.RadioactiveSource;
import trinity.items.RadioactiveSource2;
import trinity.tiles.TileEntityShieldedContainer;

/**
 * User: brandon3055
 * Date: 06/01/2015
 *
 * The container is used to link the client side gui to the server side inventory and it is where
 * you add the slots to your gui. It can also be used to sync server side data with the client but
 * that will be covered in a later tutorial
 */
public class SlotShielded extends Slot {

public SlotShielded(IInventory inventory, int par2, int par3, int par4) {

super(inventory, par2, par3, par4);

}

@Override
public boolean isItemValid(ItemStack itemstack) {

	IRadiationSource stackSource = RadiationHelper.getRadiationSource(itemstack);
	/*if(Trinity.QMDLoaded)
	{
		if(itemstack.getItem()instanceof RadioactiveSource)
		{
			return true;
		}
	}*/
	if(stackSource !=null || itemstack.getItem()instanceof RadioactiveSource2)
	{
		return true;
	}
	return false;
}

 

@Override

public int getSlotStackLimit() {

return 1;

}
}
