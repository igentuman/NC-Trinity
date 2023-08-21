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
//import trinity.entities.EntityCaldera;
import trinity.entities.EntityFalloutRain;
import trinity.entities.EntityBlackHole;
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

public class NuclearCore extends Block {

public int blastRadius;

public boolean salted;
	
public NuclearCore(String nameIn, Material material, int blastRadius, boolean salted) {
	super(material);
	this.blastRadius=blastRadius;
	this.salted=salted;
	setTranslationKey(Global.MOD_ID + "." + nameIn);
	setRegistryName(new ResourceLocation(Global.MOD_ID, nameIn));
	setHarvestLevel("Pickaxe", 1);
	setSoundType(blockSoundType.METAL);
	this.setHardness(2F);
	}

@Override
public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
{
	if(this.salted)
	{
		tooltip.add("Crater Radius: "+this.blastRadius*0.75);
	}
	if(!this.salted)
	{
		tooltip.add("Crater Radius: "+this.blastRadius);
	}
	tooltip.add("Wasteland Radius: "+this.blastRadius*TrinityConfig.fallout_multiplier);	
	
	if(this.salted)
	{
		tooltip.add(TextFormatting.DARK_RED+"Danger: Leaves extremely radioactive fallout behind!");
	}
}

/*@SuppressWarnings("null")
@Override
public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
{
	AtomicBomb(world,pos,this.blastRadius, this.salted);
}*/

	@Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
    {
		if(world.isBlockPowered(pos))
		{
			//world.scheduleUpdate(pos, this, 600);
			AtomicBomb(world,pos,this.blastRadius, this.salted);
		}		
    }
	
/*    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World world, BlockPos pos, Random rand)
    {
    	world.spawnParticle(EnumParticleTypes.SUSPENDED_DEPTH, pos.getX()-4 + (ThreadLocalRandom.current().nextDouble(0, 9)), pos.getY()-4 + (ThreadLocalRandom.current().nextDouble(0, 9)), pos.getZ()-4 + (ThreadLocalRandom.current().nextDouble(0, 9)), 0, 0, 0);
    	//this.randomDisplayTick(stateIn, world, pos, rand);
    }*/

	@SuppressWarnings("null")
	public static void AtomicBomb(World world, BlockPos pos, int blastRadius, boolean salted)
	{
		//boolean antimatter = world.getBlockState(pos).getBlock()==ModBlocks.bomb_antimatter;
		//System.out.println("This is not a thermonuclear bomb");
		Chunk chunk = world.getChunk(pos);

			if(ThermonuclearBomb.ThermonuclearCheck(world,pos)==true && TrinityConfig.thermonuclear)
			{
				//System.out.println("This is sa thermonuclear bomb");
				int X = pos.getX();
				int Y = pos.getY();
				int Z = pos.getZ();
				for(int x=X-3;x<=X+3;x++)
				{
					for(int y=Y-3;y<=Y+3;y++)
					{
						for(int z=Z-3;z<=Z+3;z++)
						{
							BlockPos clear = new BlockPos(x,y,z);
							Block block = world.getBlockState(clear).getBlock();
							if(block != Blocks.BEDROCK && clear.getY()<=1)
							{
								world.setBlockToAir(clear);	
							}
						}
					}
				}
				double multiplier = ThermonuclearBomb.ThermonuclearMultiplier(world,pos, false);
				int radius = (int) (Math.min(blastRadius,TrinityConfig.max_radius)*multiplier);
				//System.out.println("Radius: "+radius+" Multiplier: "+multiplier);
				int salt = (int) ThermonuclearBomb.ThermonuclearMultiplier(world,pos, true);
				ExplosionNukeGeneric.irradiate(world, pos.getX(), pos.getY(), pos.getZ(), Math.min(radius,TrinityConfig.max_radius)*2);
				EntityNuclearCloud entity2 = new EntityNuclearCloud(world, 1000, (radius*2) * 0.005F);
				entity2.posX = pos.getX();
				entity2.posY = pos.getY();
				entity2.posZ = pos.getZ();
				world.spawnEntity(entity2);
				
				world.spawnEntity(EntityNuclearExplosion.statFacNoRad(world, Math.min((int) (blastRadius*1.5f),TrinityConfig.max_radius), pos.getX() + 0.0, pos.getY() + 0.0, pos.getZ() + 0.0));
				
				EntityThermonuclearBlast blast = new EntityThermonuclearBlast(world);
				blast.posX = pos.getX();
				blast.posY = pos.getY();
				blast.posZ = pos.getZ();
				blast.setScale(radius);
				//fallout.setThermonuclear(true);
				blast.setIntensity(salt);
				world.spawnEntity(blast);
				
				//world.spawnEntity(EntityNuclearExplosion.statFacThermo(world, radius, pos.getX() + 0.0, pos.getY() + 0.0, pos.getZ() + 0.0, salt));
				//System.out.println("Radius: "+radius);
				//System.out.println("Salt modifier: "+salt);
				return;
			}
			EntityNuclearCloud entity2 = new EntityNuclearCloud(world, 1000, (Math.min(blastRadius,TrinityConfig.max_radius)*2) * 0.005F);
			entity2.posX = pos.getX();
			entity2.posY = pos.getY();
			entity2.posZ = pos.getZ();
			world.spawnEntity(entity2);
			world.setBlockToAir(pos);
			ExplosionNukeGeneric.irradiate(world, pos.getX(), pos.getY(), pos.getZ(), Math.min(blastRadius,TrinityConfig.max_radius)*2);
			if(!salted)
			{
				/*EntityImplosion shock = new EntityImplosion(world);
				shock.posX = pos.getX();
				shock.posY = pos.getY();
				shock.posZ = pos.getZ();
				shock.setScale(20);
				world.spawnEntity(shock);*/
				world.spawnEntity(EntityNuclearExplosion.statFac(world, Math.min(blastRadius,TrinityConfig.max_radius), pos.getX() + 0.0, pos.getY() + 0.0, pos.getZ() + 0.0));
			}
			else if(salted)
			{
				if (chunk != null || chunk.hasCapability(IRadiationSource.CAPABILITY_RADIATION_SOURCE, null))
				{
					IRadiationSource chunkRadation = chunk.getCapability(IRadiationSource.CAPABILITY_RADIATION_SOURCE, null);
					if (chunkRadation != null)
					{
						double promptRads = TrinityConfig.salted_burst;
						if (chunkRadation.getRadiationBuffer() < (promptRads)) {
							chunkRadation.setRadiationBuffer(promptRads);
						} else {
							chunkRadation.setRadiationBuffer(chunkRadation.getRadiationLevel() + promptRads);
						}
					}
				} 
				world.spawnEntity(EntityNuclearExplosion.statFacSalted(world, Math.min(blastRadius,TrinityConfig.max_radius), pos.getX() + 0.0, pos.getY() + 0.0, pos.getZ() + 0.0));
			}
		}
		/*else if(antimatter)
		{
			EntityNuclearCloud entity2 = new EntityNuclearCloud(world, 1000, (blastRadius*2) * 0.005F);
			entity2.posX = pos.getX();
			entity2.posY = pos.getY();
			entity2.posZ = pos.getZ();
			world.spawnEntity(entity2);
			world.setBlockToAir(pos);
			ExplosionNukeGeneric.irradiate(world, pos.getX(), pos.getY(), pos.getZ(), blastRadius*6);
			world.spawnEntity(EntityNuclearExplosion.statFacNoRad(world, blastRadius, pos.getX() + 0.0, pos.getY() + 0.0, pos.getZ() + 0.0));
		}*/			
	//}	
}