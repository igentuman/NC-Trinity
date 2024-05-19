package trinity.tiles;

import nc.capability.radiation.resistance.IRadiationResistance;
import nc.capability.radiation.source.IRadiationSource;
import nc.radiation.RadiationHelper;
import nc.util.NCMath;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.*;
import trinity.blocks.ShieldedContainer;
import trinity.items.RadioactiveSource2;

import java.util.Arrays;

/** User: brandon3055 Date: 06/01/2015
 *
 * This is a simple tile entity implementing IInventory that can store 9 item stacks */
public class TileEntityShieldedContainer extends TileEntity implements IInventory {
	
	// Create and initialize the items variable that will store store the items
	private final int NUMBER_OF_SLOTS = 9;
	private ItemStack[] itemStacks;
	
	public TileEntityShieldedContainer() {
		itemStacks = new ItemStack[NUMBER_OF_SLOTS];
		clear();
	}
	
	/* The following are some IInventory methods you are required to override */
	
	// Gets the number of slots in the inventory
	@Override
	public int getSizeInventory() {
		return itemStacks.length;
	}
	
	// returns true if all of the slots in the inventory are empty
	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : itemStacks) {
			if (!itemstack.isEmpty()) { // isEmpty()
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public void onLoad() {
		IRadiationResistance resistance = this.getCapability(IRadiationResistance.CAPABILITY_RADIATION_RESISTANCE, null);
		if (resistance == null) {
			return;
		}
		Block block = this.getBlockType();
		if (block instanceof ShieldedContainer) {
			ShieldedContainer shield = ((ShieldedContainer) block);
			resistance.setShieldingRadResistance(Math.exp(Double.POSITIVE_INFINITY));
			// resistance.setShieldingRadResistance(Math.exp(shield.shielding)-1);
		}
		
	}
	
	// Gets the stack in the given slot
	@Override
	public ItemStack getStackInSlot(int slotIndex) {
		return itemStacks[slotIndex];
	}
	
	/** Removes some of the units from itemstack in the given slot, and returns as a separate itemstack
	 *
	 * @param slotIndex
	 *            the slot number to remove the items from
	 * @param count
	 *            the number of units to remove
	 * @return a new itemstack containing the units removed from the slot */
	@Override
	public ItemStack decrStackSize(int slotIndex, int count) {
		ItemStack itemStackInSlot = getStackInSlot(slotIndex);
		if (itemStackInSlot.isEmpty())
			return ItemStack.EMPTY; // isEmpt(); EMPTY_ITEM
			
		ItemStack itemStackRemoved;
		if (itemStackInSlot.getCount() <= count) { // getStackSize()
			itemStackRemoved = itemStackInSlot;
			setInventorySlotContents(slotIndex, ItemStack.EMPTY); // EMPTY_ITEM
		}
		else {
			itemStackRemoved = itemStackInSlot.splitStack(count);
			if (itemStackInSlot.getCount() == 0) { // getStackSize
				setInventorySlotContents(slotIndex, ItemStack.EMPTY); // EMPTY_ITEM
			}
		}
		markDirty();
		return itemStackRemoved;
	}
	
	// overwrites the stack in the given slotIndex with the given stack
	@Override
	public void setInventorySlotContents(int slotIndex, ItemStack itemstack) {
		itemStacks[slotIndex] = itemstack;
		if (itemstack.isEmpty() && itemstack.getCount() > getInventoryStackLimit()) { // isEmpty(); getStackSize()
			itemstack.setCount(getInventoryStackLimit()); // setStackSize
		}
		markDirty();
	}
	
	// This is the maximum number if items allowed in each slot
	// This only affects things such as hoppers trying to insert items you need to use the container to enforce this for players
	// inserting items via the gui
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	// Return true if the given player is able to use this block. In this case it checks that
	// 1) the world tileentity hasn't been replaced in the meantime, and
	// 2) the player isn't too far away from the centre of the block
	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		if (this.world.getTileEntity(this.pos) != this)
			return false;
		final double X_CENTRE_OFFSET = 0.5;
		final double Y_CENTRE_OFFSET = 0.5;
		final double Z_CENTRE_OFFSET = 0.5;
		final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
		return player.getDistanceSq(pos.getX() + X_CENTRE_OFFSET, pos.getY() + Y_CENTRE_OFFSET, pos.getZ() + Z_CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ;
	}
	
	// Return true if the given stack is allowed to go in the given slot. In this case, we can insert anything.
	// This only affects things such as hoppers trying to insert items you need to use the container to enforce this for players
	// inserting items via the gui
	@Override
	public boolean isItemValidForSlot(int slotIndex, ItemStack itemstack) {
		return true;
	}
	
	// This is where you save any data that you don't want to lose when the tile entity unloads
	// In this case, it saves the itemstacks stored in the container
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound parentNBTTagCompound) {
		super.writeToNBT(parentNBTTagCompound); // The super call is required to save and load the tileEntity's location
		
		// to use an analogy with Java, this code generates an array of hashmaps
		// The itemStack in each slot is converted to an NBTTagCompound, which is effectively a hashmap of key->value pairs such
		// as slot=1, id=2353, count=1, etc
		// Each of these NBTTagCompound are then inserted into NBTTagList, which is similar to an array.
		NBTTagList dataForAllSlots = new NBTTagList();
		for (int i = 0; i < this.itemStacks.length; ++i) {
			if (!this.itemStacks[i].isEmpty()) { // isEmpty()
				NBTTagCompound dataForThisSlot = new NBTTagCompound();
				dataForThisSlot.setByte("Slot", (byte) i);
				this.itemStacks[i].writeToNBT(dataForThisSlot);
				dataForAllSlots.appendTag(dataForThisSlot);
			}
		}
		// the array of hashmaps is then inserted into the parent hashmap for the container
		parentNBTTagCompound.setTag("Items", dataForAllSlots);
		// return the NBT Tag Compound
		return parentNBTTagCompound;
	}
	
	public double getRadioactivity() {
		double rads = 0D;// +getSealedSources();
		for (int i = 0; i < this.itemStacks.length; ++i) {
			if (!this.itemStacks[i].isEmpty()) { // isEmpty()
				/*if(Trinity.QMDLoaded)
				{
					if(this.itemStacks[i].getItem()instanceof RadioactiveSource)
					{
						RadioactiveSource source = ((RadioactiveSource)this.itemStacks[i].getItem());
						rads+=source.rads*this.itemStacks[i].getCount();
						continue;
					}
				}
				if(this.itemStacks[i].getItem()instanceof RadioactiveSource2)
				{
					RadioactiveSource2 source = ((RadioactiveSource2)this.itemStacks[i].getItem());
					rads+=source.rads*this.itemStacks[i].getCount();
					continue;
				}*/
				IRadiationSource stackSource = RadiationHelper.getRadiationSource(this.itemStacks[i]);
				if (stackSource == null && !(this.itemStacks[i].getItem() instanceof RadioactiveSource2)) {
					continue;
				}
				rads += stackSource.getRadiationLevel() * this.itemStacks[i].getCount();
			}
		}
		// System.out.println("True Radioactivity: "+rads+" Rad/t");
		return rads;
	}
	
	public double getShielding() {
		Block block = this.getBlockType();
		if (block instanceof ShieldedContainer) {
			ShieldedContainer shield = ((ShieldedContainer) block);
			return shield.shielding;
		}
		return 0;
	}
	
	public double getRadiation() {
		double rads = getRadioactivity();
		double shieldedRads = 0D;
		shieldedRads = NCMath.sq(rads) / (rads + (Math.exp(getShielding()) - 1));
		// System.out.println("Emitted Radiation: "+shieldedRads+" Rad/t");
		return shieldedRads;
	}
	
	// This is where you load the data that you saved in writeToNBT
	@Override
	public void readFromNBT(NBTTagCompound parentNBTTagCompound) {
		super.readFromNBT(parentNBTTagCompound); // The super call is required to save and load the tiles location
		final byte NBT_TYPE_COMPOUND = 10; // See NBTBase.createNewByType() for a listing
		NBTTagList dataForAllSlots = parentNBTTagCompound.getTagList("Items", NBT_TYPE_COMPOUND);
		
		Arrays.fill(itemStacks, ItemStack.EMPTY); // set all slots to empty EMPTY_ITEM
		for (int i = 0; i < dataForAllSlots.tagCount(); ++i) {
			NBTTagCompound dataForOneSlot = dataForAllSlots.getCompoundTagAt(i);
			int slotIndex = dataForOneSlot.getByte("Slot") & 255;
			
			if (slotIndex >= 0 && slotIndex < this.itemStacks.length) {
				this.itemStacks[slotIndex] = new ItemStack(dataForOneSlot);
			}
		}
	}
	
	// set all slots to empty
	@Override
	public void clear() {
		Arrays.fill(itemStacks, ItemStack.EMPTY); // empty item
	}
	
	// will add a key for this container to the lang file so we can name it in the GUI
	@Override
	public String getName() {
		return "container.nuclear_pig.name";
	}
	
	@Override
	public boolean hasCustomName() {
		return false;
	}
	
	// standard code to look up what the human-readable name is
	@Override
	public ITextComponent getDisplayName() {
		return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
	}
	
	// -----------------------------------------------------------------------------------------------------------
	// The following methods are not needed for this example but are part of IInventory so they must be implemented
	
	/** This method removes the entire contents of the given slot and returns it. Used by containers such as crafting tables which return any items in their slots when you close the GUI
	 *
	 * @param slotIndex
	 * @return */
	@Override
	public ItemStack removeStackFromSlot(int slotIndex) {
		ItemStack itemStack = getStackInSlot(slotIndex);
		if (!itemStack.isEmpty())
			setInventorySlotContents(slotIndex, ItemStack.EMPTY); // isEmpty(), EMPTY_ITEM
		return itemStack;
	}
	
	@Override
	public void openInventory(EntityPlayer player) {}
	
	@Override
	public void closeInventory(EntityPlayer player) {}
	
	@Override
	public int getField(int id) {
		return 0;
	}
	
	@Override
	public void setField(int id, int value) {}
	
	@Override
	public int getFieldCount() {
		return 0;
	}
}
