package trinity.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import trinity.Global;
import trinity.init.ModBlocks;
import trinity.init.ModItems;

public class BasicBlock extends Block {
	
	public BasicBlock(String nameIn, Material material) {
		super(material);
		setTranslationKey(Global.MOD_ID + "." + nameIn);
		setRegistryName(new ResourceLocation(Global.MOD_ID, nameIn));
		setHarvestLevel("Pickaxe", 0);
		// setSoundType(blockSoundType.GROUND);
		this.setHardness(2F);
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
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		if (this == ModBlocks.trinitite) {
			ItemStack shard = new ItemStack(ModItems.trinitite, 1);
			ItemStack block = new ItemStack(Blocks.SAND, 1);
			drops.add(block);
			drops.add(shard);
		}
		else {
			super.getDrops(drops, world, pos, state, fortune);
		}
	}
}
