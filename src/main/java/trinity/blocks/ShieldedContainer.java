package trinity.blocks;

import nc.util.Lang;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import trinity.Global;
import trinity.Trinity;
import trinity.gui.GuiHandlerTrinity;
import trinity.tiles.TileEntityShieldedContainer;

public class ShieldedContainer extends BlockContainer {
	
	public double shielding;
	public static double rads;
	private static final String RADIATION = Lang.localise("item.nuclearcraft.rads");
	
	public ShieldedContainer(String nameIn, Material material, double shielding) {
		super(material);
		setTranslationKey(Global.MOD_ID + "." + nameIn);
		setRegistryName(new ResourceLocation(Global.MOD_ID, nameIn));
		setHarvestLevel("Pickaxe", 1);
		this.shielding = shielding * 8;
		// setSoundType(blockSoundType.GROUND);
		this.setHardness(2F);
		this.setLightOpacity(0);
		// this.setTickRandomly(true);
	}
	
	/*	@Override
		public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player)
		{
			if(this==ModBlocks.trinitite)
			{
				ItemStack block = new ItemStack(Blocks.SAND, 1);
				EntityItem sand = new EntityItem(world);
				sand.setItem(block);
				
				ItemStack shard = new ItemStack(ModItems.trinitite, 1);
				EntityItem trinitite = new EntityItem(world);
				trinitite.setItem(shard);
				trinitite.posX = pos.getX();
				trinitite.posY = pos.getY();
				trinitite.posZ = pos.getZ();
				world.spawnEntity(trinitite);
				world.spawnEntity(sand);
			}
			super.onBlockHarvested(world, pos, state, player);
		}*/
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityShieldedContainer();
	}
	
	@Override
	public boolean isTranslucent(IBlockState state) {
		return true;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	// Called when the block is right clicked
	// In this block it is used to open the blocks gui when right clicked by a player
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		// Uses the gui handler registered to your mod to open the gui for the given gui id
		// open on the server side only (not sure why you shouldn't open client side too... vanilla doesn't, so we better not either)
		if (worldIn.isRemote)
			return true;
		
		playerIn.openGui(Trinity.instance, GuiHandlerTrinity.getGuiID(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	
	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState blockState, float chance, int fortune) {
		// Disable super to prevent standard drop.
		// super.dropBlockAsItemWithChance(worldIn, pos, blockState, chance, fortune);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		TileEntity tile = worldIn.getTileEntity(pos);
		if (tile != null && tile instanceof TileEntityShieldedContainer) {
			TileEntityShieldedContainer container = ((TileEntityShieldedContainer) tile);
			if (stack.hasTagCompound()) {
				container.readFromNBT(stack.getTagCompound());
				container.setPos(pos);
			}
		}
	}
	
	/*@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
	//		tooltip.add("Light Shielding: "+NCConfig.radiation_shielding_level[0]);	
	//		tooltip.add("Medium Shielding: "+NCConfig.radiation_shielding_level[1]);
	//		tooltip.add("Heavy Shielding: "+NCConfig.radiation_shielding_level[2]);	
		rads = stack.getTagCompound().getDouble("Radioactivity");
		tooltip.add(RadiationHelper.getRadiationTextColor(this.rads*stack.getCount()) + RADIATION + " " + RadiationHelper.radsPrefix(this.rads*stack.getCount(), true));
	}*/
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		
		// IInventory inventory = worldIn.getTileEntity(pos) instanceof IInventory ? (IInventory)worldIn.getTileEntity(pos) : null;
		TileEntity tile = worldIn.getTileEntity(pos);
		if (tile instanceof TileEntityShieldedContainer) {
			TileEntityShieldedContainer te = ((TileEntityShieldedContainer) tile);
			rads = te.getRadioactivity();
			
		}
		NBTTagCompound nbt = new NBTTagCompound();
		tile.writeToNBT(nbt);
		EntityItem item = new EntityItem(worldIn);
		ItemStack container = new ItemStack(Item.getItemFromBlock(this), 1);
		container.setTagCompound(nbt);
		container.getTagCompound().setDouble("Radioactivity", rads);
		item.setPosition(pos.getX() + 0.5f, pos.getY(), pos.getZ() + 0.5f);
		item.entityDropItem(container, 0.5f);
		/*if (inventory != null){
			// For each slot in the inventory
			for (int i = 0; i < inventory.getSizeInventory(); i++){
				// If the slot is not empty
				if (!inventory.getStackInSlot(i).isEmpty())  // isEmpty
				{
					// Create a new entity item with the item stack in the slot
					EntityItem item = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, inventory.getStackInSlot(i));
		
					// Apply some random motion to the item
					float multiplier = 0.1f;
					float motionX = worldIn.rand.nextFloat() - 0.5f;
					float motionY = worldIn.rand.nextFloat() - 0.5f;
					float motionZ = worldIn.rand.nextFloat() - 0.5f;
		
					item.motionX = motionX * multiplier;
					item.motionY = motionY * multiplier;
					item.motionZ = motionZ * multiplier;
		
					// Spawn the item in the world
					worldIn.spawnEntity(item);
				}
			}
		
			// Clear the inventory so nothing else (such as another mod) can do anything with the items
			inventory.clear();
		}*/
		
		// Super MUST be called last because it removes the tile entity
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState iBlockState) {
		return EnumBlockRenderType.MODEL;
	}
	
}
