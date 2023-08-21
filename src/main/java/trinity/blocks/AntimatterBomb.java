package trinity.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import io.netty.util.internal.ThreadLocalRandom;
import nc.capability.radiation.source.IRadiationSource;
import nc.init.NCBlocks;
import nc.radiation.RadSources;
import nc.radiation.RadiationHelper;
import trinity.Global;
import trinity.config.TrinityConfig;
import trinity.entities.EntityFalloutRain;
import trinity.entities.EntityNuclearCloud;
import trinity.entities.EntityNuclearExplosion;
import trinity.entities.EntityThermonuclearBlast;
import trinity.explosion.ExplosionNukeGeneric;
import trinity.init.ModBlocks;
//import nca.handler.NuclearExplosion;
//import nca.handler.ProcessHandler;
import trinity.tabs.TrinityTab;
import trinity.util.ThermonuclearBomb;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class AntimatterBomb extends Block {

public int blastRadius;
	
public AntimatterBomb(String nameIn, Material material, int blastRadius) {
	super(material);
	this.blastRadius=blastRadius;
	setTranslationKey(Global.MOD_ID + "." + nameIn);
	setRegistryName(new ResourceLocation(Global.MOD_ID, nameIn));
	setHarvestLevel("Pickaxe", 1);
	setSoundType(blockSoundType.METAL);
	this.setHardness(2F);
	}

@Override
public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
{
		tooltip.add("Crater Radius: "+this.blastRadius);	
		tooltip.add("Thermal Radius: "+this.blastRadius*TrinityConfig.fallout_multiplier*2);

		tooltip.add(TextFormatting.DARK_BLUE+"Leaves no radioactivity behind.");
		tooltip.add(TextFormatting.DARK_RED+"Danger: Will explode if exposed to explosions!");
}

@SuppressWarnings("null")
@Override
public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
{
	AtomicBomb(world,pos,this.blastRadius);
}

@Override
public void onBlockExploded(World world, BlockPos pos, Explosion explosion)
{
		AtomicBomb(world,pos,this.blastRadius);
}


	@Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
    {
		if(world.isBlockPowered(pos))
		{
			world.scheduleUpdate(pos, this, 600);
			//AtomicBomb(world,pos,this.blastRadius, this.salted);
		}		
    }
	
/*    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World world, BlockPos pos, Random rand)
    {
    	world.spawnParticle(EnumParticleTypes.SUSPENDED_DEPTH, pos.getX()-4 + (ThreadLocalRandom.current().nextDouble(0, 9)), pos.getY()-4 + (ThreadLocalRandom.current().nextDouble(0, 9)), pos.getZ()-4 + (ThreadLocalRandom.current().nextDouble(0, 9)), 0, 0, 0);
    	//this.randomDisplayTick(stateIn, world, pos, rand);
    }*/

	@SuppressWarnings("null")
	public static void AtomicBomb(World world, BlockPos pos, int blastRadius)
	{
		//boolean antimatter = world.getBlockState(pos).getBlock()==ModBlocks.bomb_antimatter;
		//System.out.println("This is not a thermonuclear bomb");
		Chunk chunk = world.getChunk(pos);

			EntityNuclearCloud entity2 = new EntityNuclearCloud(world, 1000, (Math.min(blastRadius,TrinityConfig.max_radius)*2) * 0.005F);
			entity2.posX = pos.getX();
			entity2.posY = pos.getY();
			entity2.posZ = pos.getZ();
			world.spawnEntity(entity2);
			world.setBlockToAir(pos);
			ExplosionNukeGeneric.irradiate(world, pos.getX(), pos.getY(), pos.getZ(), Math.min(blastRadius,TrinityConfig.max_radius)*6);
			world.spawnEntity(EntityNuclearExplosion.statFacAntimatter(world, Math.min(blastRadius,TrinityConfig.max_radius), pos.getX() + 0.0, pos.getY() + 0.0, pos.getZ() + 0.0));
		}			
	//}	
}