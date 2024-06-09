package trinity.blocks;

import trinity.Reference;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import trinity.config.TrinityConfig;
import trinity.entities.EntityDirtyBomb;
import trinity.explosion.ExplosionNukeGeneric;
import trinity.init.ModBlocks;

public class DirtyBomb extends Block {
	// public static final PropertyBool EXPLODE = PropertyBool.create("explode");
	
	public DirtyBomb(String nameIn, Material mat, SoundType sound) {
		super(mat);
		setTranslationKey(Reference.MOD_ID + "." + nameIn);
		setRegistryName(new ResourceLocation(Reference.MOD_ID, nameIn));
		// this.setDefaultState(this.blockState.getBaseState().withProperty(EXPLODE, Boolean.valueOf(false)));
		// this.setCreativeTab(CreativeTabs.REDSTONE);
		this.setSoundType(sound);
		
	}
	
	/** Called after the block is set in the Chunk data, but before the Tile Entity is set */
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		super.onBlockAdded(worldIn, pos, state);
		
		if (worldIn.isBlockPowered(pos)) {
			this.onPlayerDestroy(worldIn, pos, true);
			worldIn.setBlockToAir(pos);
		}
	}
	
	/** Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid block, etc. */
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (worldIn.isBlockPowered(pos)) {
			// if(this==ModBlocks.fusion_bomb)
			// {
			// this.explode(worldIn, pos, true, (EntityLivingBase)null);
			// }
			this.onPlayerDestroy(worldIn, pos, true);
			worldIn.setBlockToAir(pos);
		}
	}
	
	/** Called when this Block is destroyed by an Explosion */
	public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
		if (this == ModBlocks.dirty_bomb) {
			if (!worldIn.isRemote) {
				EntityDirtyBomb entitytntprimed = new EntityDirtyBomb(worldIn, (double) ((float) pos.getX() + 0.5F), (double) pos.getY(), (double) ((float) pos.getZ() + 0.5F), explosionIn.getExplosivePlacedBy(), 0.5D);
				entitytntprimed.setFuse((short) (worldIn.rand.nextInt(50) + 25));
				worldIn.spawnEntity(entitytntprimed);
			}
		}
		if (this == ModBlocks.gold_bomb) {
			if (!worldIn.isRemote) {
				EntityDirtyBomb entitytntprimed = new EntityDirtyBomb(worldIn, (double) ((float) pos.getX() + 0.5F), (double) pos.getY(), (double) ((float) pos.getZ() + 0.5F), explosionIn.getExplosivePlacedBy(), 300D);
				entitytntprimed.setFuse((short) (worldIn.rand.nextInt(50) + 25));
				worldIn.spawnEntity(entitytntprimed);
			}
		}
	}
	
	/** Called after a player destroys this Block - the posiiton pos may no longer hold the state indicated. */
	public void onPlayerDestroy(World worldIn, BlockPos pos, boolean explode) {
		// if(this!=ModBlocks.fusion_bomb)
		// {
		this.explode(worldIn, pos, explode, (EntityLivingBase) null);
		// }
	}
	
	public void explode(World worldIn, BlockPos pos, boolean explode, EntityLivingBase igniter) {
		if (!worldIn.isRemote) {
			if (explode) {
				worldIn.setBlockToAir(pos);
				if (this == ModBlocks.fusion_bomb) {
					worldIn.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 6, true);
					ExplosionNukeGeneric.irradiate(worldIn, pos.getX(), pos.getY(), pos.getZ(), Math.min(16, TrinityConfig.max_radius) * 6);
				}
				if (this == ModBlocks.dirty_bomb) {
					EntityDirtyBomb entitytntprimed = new EntityDirtyBomb(worldIn, (double) ((float) pos.getX() + 0.5F), (double) pos.getY(), (double) ((float) pos.getZ() + 0.5F), igniter, 0.5D);
					entitytntprimed.setFuse(80);
					worldIn.spawnEntity(entitytntprimed);
					worldIn.playSound((EntityPlayer) null, entitytntprimed.posX, entitytntprimed.posY, entitytntprimed.posZ, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}
				if (this == ModBlocks.gold_bomb) {
					EntityDirtyBomb entitytntprimed = new EntityDirtyBomb(worldIn, (double) ((float) pos.getX() + 0.5F), (double) pos.getY(), (double) ((float) pos.getZ() + 0.5F), igniter, 300D);
					entitytntprimed.setFuse(80);
					worldIn.spawnEntity(entitytntprimed);
					worldIn.playSound((EntityPlayer) null, entitytntprimed.posX, entitytntprimed.posY, entitytntprimed.posZ, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}
			}
		}
	}
	
	/** Called when the block is right clicked by a player. */
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack itemstack = playerIn.getHeldItem(hand);
		
		if (!itemstack.isEmpty() && (itemstack.getItem() == Items.FLINT_AND_STEEL || itemstack.getItem() == Items.FIRE_CHARGE)) {
			this.explode(worldIn, pos, true, playerIn);
			worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
			
			if (itemstack.getItem() == Items.FLINT_AND_STEEL) {
				itemstack.damageItem(1, playerIn);
			}
			else if (!playerIn.capabilities.isCreativeMode) {
				itemstack.shrink(1);
			}
			
			return true;
		}
		else {
			return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
		}
	}
	
	/** Called When an Entity Collided with the Block */
	public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		if (!worldIn.isRemote && entityIn instanceof EntityArrow) {
			EntityArrow entityarrow = (EntityArrow) entityIn;
			
			if (entityarrow.isBurning()) {
				this.explode(worldIn, pos, true, entityarrow.shootingEntity instanceof EntityLivingBase ? (EntityLivingBase) entityarrow.shootingEntity : null);
				worldIn.setBlockToAir(pos);
			}
		}
	}
	
	/** Return whether this block can drop from an explosion. */
	public boolean canDropFromExplosion(Explosion explosionIn) {
		return false;
	}
	
	/** Convert the given metadata into a BlockState for this Block */
	/*public IBlockState getStateFromMeta(int meta)
	{
	    return this.getDefaultState().withProperty(EXPLODE, Boolean.valueOf((meta & 1) > 0));
	}
	
	/**
	 * Convert the BlockState into the correct metadata value
	 */
	/*public int getMetaFromState(IBlockState state)
	{
	    return ((Boolean)state.getValue(EXPLODE)).booleanValue() ? 1 : 0;
	}
	
	protected BlockStateContainer createBlockState()
	{
	    return new BlockStateContainer(this, new IProperty[] {EXPLODE});
	}*/
}
