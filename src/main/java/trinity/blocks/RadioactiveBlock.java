package trinity.blocks;

import trinity.Reference;
import nc.capability.radiation.source.IRadiationSource;
import nc.init.NCBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import trinity.init.ModBlocks;
import trinity.radiation.RadiationHandler;

import java.util.Random;

public class RadioactiveBlock extends Block {
	
	public RadioactiveBlock(String nameIn, Material material) {
		super(material);
		setTranslationKey(Reference.MOD_ID + "." + nameIn);
		setRegistryName(new ResourceLocation(Reference.MOD_ID, nameIn));
		// setHarvestLevel("Pickaxe", 1);
		// setSoundType(blockSoundType.GROUND);
		this.setHardness(2F);
		this.setTickRandomly(true);
	}
	
	@SuppressWarnings("null")
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		Chunk chunk = world.getChunk(pos);
		if (this == ModBlocks.radioactive_earth2) {
			if (chunk != null || chunk.hasCapability(IRadiationSource.CAPABILITY_RADIATION_SOURCE, null)) {
				IRadiationSource chunkRadation = chunk.getCapability(IRadiationSource.CAPABILITY_RADIATION_SOURCE, null);
				if (chunkRadation != null) {
					double radioactivity = RadiationHandler.HEAVY_SALTED_EARTH;
					if (chunkRadation.getRadiationBuffer() < (radioactivity)) {
						chunkRadation.setRadiationBuffer(radioactivity);
					}
					else {
						chunkRadation.setRadiationBuffer(chunkRadation.getRadiationLevel() + radioactivity);
					}
				}
			}
			if (rand.nextInt(5) == 0) {
				world.setBlockState(pos, ModBlocks.radioactive_earth.getDefaultState());
			}
		}
		if (this == ModBlocks.radioactive_earth) {
			if (chunk != null || chunk.hasCapability(IRadiationSource.CAPABILITY_RADIATION_SOURCE, null)) {
				IRadiationSource chunkRadation = chunk.getCapability(IRadiationSource.CAPABILITY_RADIATION_SOURCE, null);
				if (chunkRadation != null) {
					double radioactivity = RadiationHandler.SALTED_EARTH;
					if (chunkRadation.getRadiationBuffer() < (radioactivity)) {
						chunkRadation.setRadiationBuffer(radioactivity);
					}
					else {
						chunkRadation.setRadiationBuffer(chunkRadation.getRadiationLevel() + radioactivity);
					}
				}
			}
			if (rand.nextInt(5) == 0) {
				world.setBlockState(pos, NCBlocks.wasteland_earth.getDefaultState());
			}
		}
	}
	
	/*@Override
	public void onBlockExploded(World world, BlockPos pos, Explosion explosion)
	{
		world.setBlockToAir(pos);
		world.spawnEntity(EntityNukeExplosionMK4.statFac(world, 100, pos.getX() + 0.0, pos.getY() + 0.0, pos.getZ() + 0.0));
	}*/
}
