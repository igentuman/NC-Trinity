package trinity.blocks;

import trinity.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.*;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ThermonuclearCore extends Block {
	
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	
	public ThermonuclearCore(String nameIn, Material material) {
		super(material);
		setTranslationKey(Reference.MOD_ID + "." + nameIn);
		setRegistryName(new ResourceLocation(Reference.MOD_ID, nameIn));
		setHarvestLevel("Pickaxe", 1);
		setSoundType(blockSoundType.METAL);
		this.setHardness(2F);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}
	
	public static EnumFacing getFacingFromEntity(BlockPos clickedBlock, EntityLivingBase entity) {
		return EnumFacing.getFacingFromVector((float) (entity.posX - clickedBlock.getX()), (float) (entity.posY - clickedBlock.getY()), (float) (entity.posZ - clickedBlock.getZ()));
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TextFormatting.GRAY + "Valid reflective blocks: Lead");
		tooltip.add(TextFormatting.GOLD + "Valid salting blocks: Gold, Cobalt, Zinc");
		tooltip.add(TextFormatting.GREEN + "Valid fissionable blocks: Uranium-238, Neptunium-237, Plutonium-242");
		tooltip.add(TextFormatting.DARK_RED + "Danger: Extremely powerful! Expect massive amounts of lag!");
	}
	
	// @SideOnly(Side.CLIENT)
	// public void initModel() {
	// ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	// }
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		world.setBlockState(pos, state.withProperty(FACING, getFacingFromEntity(pos, placer)), 2);
		// System.out.println(getMetaFromState(state));
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta & 7));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex();
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}
	
	/*	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
	{
			IBlockState state2 = world.getBlockState(fromPos);
			if(state2.getBlock()==Blocks.FIRE || state2.getMaterial()==Material.LAVA)
			{
				Explode(state, world, pos);
			}
			if(world.isBlockPowered(pos))
			{
				Explode(state, world, pos);
			}
	}
	public void Explode(IBlockState state, World world, BlockPos pos)
	{
			if(this==ModBlocks.baratol)
			{
				world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 3, true);
				world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 6, false);
			}
			if(state.getValue(FACING)==EnumFacing.NORTH)
			{
					world.setBlockState(pos, Blocks.AIR.getDefaultState());
					world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ()-2, 4, true);
			}
			if(state.getValue(FACING)==EnumFacing.SOUTH)
			{
					world.setBlockState(pos, Blocks.AIR.getDefaultState());
					world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ()+2, 4, true);
			}
			if(state.getValue(FACING)==EnumFacing.EAST)
			{
					world.setBlockState(pos, Blocks.AIR.getDefaultState());
					world.createExplosion(null, pos.getX()+2, pos.getY(), pos.getZ(), 4, true);
			}
			if(state.getValue(FACING)==EnumFacing.WEST)
			{
	
					world.setBlockState(pos, Blocks.AIR.getDefaultState());
					world.createExplosion(null, pos.getX()-2, pos.getY(), pos.getZ(), 4, true);
			}
			if(state.getValue(FACING)==EnumFacing.UP)
			{
					world.setBlockState(pos, Blocks.AIR.getDefaultState());
					world.createExplosion(null, pos.getX(), pos.getY()+2, pos.getZ(), 4, true);
			}
			if(state.getValue(FACING)==EnumFacing.DOWN)
			{
					world.setBlockState(pos, Blocks.AIR.getDefaultState());
					world.createExplosion(null, pos.getX(), pos.getY()-2, pos.getZ(), 4, true);
			}
	}
		
		@Override
		public void onBlockExploded(World world, BlockPos pos, Explosion explosion)
		{
			world.setBlockToAir(pos);
			world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 3, true);
			world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 6, false);
		}*/
}
