package trinity.blocks;

import trinity.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.*;
import net.minecraft.world.chunk.Chunk;
import trinity.config.TrinityConfig;
import trinity.entities.*;
import trinity.explosion.ExplosionNukeGeneric;

import javax.annotation.Nullable;
import java.util.*;

public class AntimatterBomb extends Block {
	
	public int blastRadius;
	
	public AntimatterBomb(String nameIn, Material material, int blastRadius) {
		super(material);
		this.blastRadius = blastRadius;
		setTranslationKey(Reference.MOD_ID + "." + nameIn);
		setRegistryName(new ResourceLocation(Reference.MOD_ID, nameIn));
		setHarvestLevel("Pickaxe", 1);
		setSoundType(blockSoundType.METAL);
		this.setHardness(2F);
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("Crater Radius: " + this.blastRadius);
		tooltip.add("Thermal Radius: " + this.blastRadius * TrinityConfig.fallout_multiplier * 2);
		
		tooltip.add(TextFormatting.DARK_BLUE + "Leaves no radioactivity behind.");
		tooltip.add(TextFormatting.DARK_RED + "Danger: Will explode if exposed to explosions!");
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		AtomicBomb(world, pos, this.blastRadius);
	}
	
	@Override
	public void onBlockExploded(World world, BlockPos pos, Explosion explosion) {
		AtomicBomb(world, pos, this.blastRadius);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
		if (world.isBlockPowered(pos)) {
			world.scheduleUpdate(pos, this, 600);
			// AtomicBomb(world,pos,this.blastRadius, this.salted);
		}
	}
	
	/*    @SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World world, BlockPos pos, Random rand)
	{
		world.spawnParticle(EnumParticleTypes.SUSPENDED_DEPTH, pos.getX()-4 + (ThreadLocalRandom.current().nextDouble(0, 9)), pos.getY()-4 + (ThreadLocalRandom.current().nextDouble(0, 9)), pos.getZ()-4 + (ThreadLocalRandom.current().nextDouble(0, 9)), 0, 0, 0);
		//this.randomDisplayTick(stateIn, world, pos, rand);
	}*/
	
	public static void AtomicBomb(World world, BlockPos pos, int blastRadius) {
		// boolean antimatter = world.getBlockState(pos).getBlock()==ModBlocks.bomb_antimatter;
		// System.out.println("This is not a thermonuclear bomb");
		Chunk chunk = world.getChunk(pos);
		
		EntityNuclearCloud entity2 = new EntityNuclearCloud(world, 1000, (Math.min(blastRadius, TrinityConfig.max_radius) * 2) * 0.005F);
		entity2.posX = pos.getX();
		entity2.posY = pos.getY();
		entity2.posZ = pos.getZ();
		world.spawnEntity(entity2);
		world.setBlockToAir(pos);
		ExplosionNukeGeneric.irradiate(world, pos.getX(), pos.getY(), pos.getZ(), Math.min(blastRadius, TrinityConfig.max_radius) * 6);
		world.spawnEntity(EntityNuclearExplosion.statFacAntimatter(world, Math.min(blastRadius, TrinityConfig.max_radius), pos.getX() + 0.0, pos.getY() + 0.0, pos.getZ() + 0.0));
	}
	// }
}
