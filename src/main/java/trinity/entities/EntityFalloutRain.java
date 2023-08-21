package trinity.entities;

import nc.init.NCBlocks;
// import com.hbm.explosion.NukeEnvironmentalEffect;
// import com.hbm.lib.Library;
// import com.hbm.main.MainRegistry;
// import com.hbm.potion.HbmPotion;
// import com.hbm.saveddata.AuxSavedData;
import nc.worldgen.biome.NCBiomes;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockMobSpawner;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
// import net.minecraft.util.AxisAlignedBB;
// import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeOcean;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import trinity.config.TrinityConfig;
import trinity.handler.INuclearEffect;
// import net.minecraftforge.common.util.ForgeDirection;
import trinity.handler.Vec3;
import trinity.init.ModBlocks;
import trinity.world.TrinityBiomes;

public class EntityFalloutRain extends Entity implements INuclearEffect {
	
	private static final DataParameter<Integer> SCALE = EntityDataManager.<Integer>createKey(EntityFalloutRain.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> SALTED = EntityDataManager.<Boolean>createKey(EntityFalloutRain.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> THERMONUCLEAR = EntityDataManager.<Boolean>createKey(EntityFalloutRain.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> INTENSITY = EntityDataManager.<Integer>createKey(EntityFalloutRain.class, DataSerializers.VARINT);
	// private static final DataParameter<Boolean> RADIOACTIVE = EntityDataManager.<Boolean>createKey(EntityFalloutRain.class, DataSerializers.BOOLEAN);
	
	public int revProgress;
	public int radProgress;
	private boolean salted;
	private boolean thermonuclear;
	
	private ForgeChunkManager.Ticket ticket = null;
	private boolean isChunkLoaded = false;
	
	public EntityFalloutRain(World p_i1582_1_) {
		super(p_i1582_1_);
		this.setSize(4, 20);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
	}
	
	public EntityFalloutRain(World p_i1582_1_, boolean salt) {
		super(p_i1582_1_);
		this.setSize(4, 20);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
		this.salted = salt;
	}
	
	public EntityFalloutRain(World p_i1582_1_, int maxAge) {
		super(p_i1582_1_);
		this.setSize(4, 20);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		double X = this.posX;
		double X2 = MathHelper.floor(X - getScale() - 1.0D);
		double X3 = MathHelper.floor(X + getScale() - 1.0D);
		double Y = this.posY;
		double Y2 = MathHelper.floor(Y - getScale() - 1.0D);
		double Y3 = MathHelper.floor(Y + getScale() - 1.0D);
		double Z = this.posZ;
		double Z2 = MathHelper.floor(Z - getScale() - 1.0D);
		double Z3 = MathHelper.floor(Z + getScale() - 1.0D);
		AxisAlignedBB bb = new AxisAlignedBB(X2, Y2, Z2, X3, Y3, Z3);
		return bb;
		// return this.getEntityBoundingBox();
	}
	
	public void setChunkTicket(ForgeChunkManager.Ticket ticket) {
		this.ticket = ticket;
		isChunkLoaded = true;
	}
	
	@Override
	public void onUpdate() {
		
		/*if (this.world.isRemote)
		{
		    for (int x = (int) (this.posX-getScale()); this.posX < (int) (this.posX+getScale()); ++x)
		    {
		        for (int z = (int) (this.posZ-getScale()); this.posZ < (int) (this.posZ+getScale()); ++x)
		        {
		            for (int y = 0; this.posY < 256; ++x)
		            {
		            	this.world.spawnParticle(EnumParticleTypes.PORTAL, (double)x, (double)y, (double)z, x, y-0.5, z);
		            }
		        }
		    }
		}*/
		
		if (!world.isRemote) {
			MutableBlockPos pos = new BlockPos.MutableBlockPos();
			for (int i = 0; i < TrinityConfig.fallout_speed; i++) {
				
				Vec3 vec = Vec3.createVectorHelper(radProgress * 0.5, 0, 0);
				double circum = radProgress * 2 * Math.PI * 2;
				
				///
				if (circum == 0)
					circum = 1;
				///
				
				double part = 360D / circum;
				
				vec.rotateAroundY((float) (part * revProgress));
				
				int x = (int) (posX + vec.xCoord);
				int z = (int) (posZ + vec.zCoord);
				
				double dist = radProgress * 100 / getScale() * 0.5;
				pos.setPos(x, posY, z);
				// System.out.println("Is this radioactive? "+getRadioactive());
				contaminate(pos, dist);
				
				revProgress++;
				
				if (revProgress > circum) {
					revProgress = 0;
					radProgress++;
				}
				
				if (radProgress > getScale() * 2D) {
					if (isChunkLoaded && !this.world.isRemote && ticket != null) {
						int chunkX = (int) Math.floor(this.posX) >> 4;
						int chunkZ = (int) Math.floor(this.posZ) >> 4;
						// System.out.println(ticket + " being unloaded for " + chunkX + ", " + chunkZ);
						ForgeChunkManager.unforceChunk(ticket, new ChunkPos(chunkX, chunkZ));
						ForgeChunkManager.releaseTicket(ticket);
						isChunkLoaded = false;
					}
					this.setDead();
				}
			}
		}
	}
	
	private void contaminate(MutableBlockPos pos, double dist) {
		
		int depth = 0;
		
		// int mult = 1;
		
		int topBlock = world.getTopSolidOrLiquidBlock(pos).getY();
		
		for (int y = topBlock + 35; y >= (topBlock - 10); y--) {
			pos.setY(y);
			IBlockState b = world.getBlockState(pos);
			// int meta = world.getBlockMetadata(x, y, z);
			if (dist <= 100 / TrinityConfig.fallout_multiplier) {
				Chunk chunk = this.world.getChunk(pos.getX() >> 4, pos.getZ() >> 4);
				if (!(chunk.getBiome(pos, world.getBiomeProvider()) instanceof BiomeOcean)) {
					chunk.getBiomeArray()[(pos.getZ() & 15) << 4 | pos.getX() & 15] = (byte) Biome.getIdForBiome(TrinityBiomes.NUCLEAR_CRATER);
					chunk.setModified(true);
				}
			}
			if (dist > 100 / TrinityConfig.fallout_multiplier) {
				Chunk chunk = this.world.getChunk(pos.getX() >> 4, pos.getZ() >> 4);
				if ((chunk.getBiome(pos, world.getBiomeProvider()) instanceof BiomeOcean)) {
					chunk.getBiomeArray()[(pos.getZ() & 15) << 4 | pos.getX() & 15] = (byte) Biome.getIdForBiome(TrinityBiomes.CONTAMINATED_OCEAN);
					chunk.setModified(true);
				}
				else if (!(chunk.getBiome(pos, world.getBiomeProvider()) instanceof BiomeOcean || chunk.getBiome(pos, world.getBiomeProvider()) == TrinityBiomes.CONTAMINATED_OCEAN)) {
					chunk.getBiomeArray()[(pos.getZ() & 15) << 4 | pos.getX() & 15] = (byte) Biome.getIdForBiome(NCBiomes.NUCLEAR_WASTELAND);
					chunk.setModified(true);
				}
			}
			
			if (b.getMaterial() == Material.AIR)
				continue;
			
			if (b.getMaterial() == Material.SNOW || b.getMaterial() == Material.CRAFTED_SNOW || b.getMaterial() == Material.ICE)
				world.setBlockToAir(pos);
			
			if (b.getBlock().isFlammable(world, pos, EnumFacing.UP) && !(b.getMaterial() == Material.LEAVES)) {
				if (dist < 65) {
					if (rand.nextInt(10) == 0)
						world.setBlockState(pos.add(0, 1, 0), Blocks.FIRE.getDefaultState());
					else
						world.setBlockToAir(pos);
				}
				if (dist > 65 && dist < 70)
					world.setBlockToAir(pos.add(0, 1, 0));
			}
			
			if (b.getMaterial() == Material.LEAVES || b.getBlock() instanceof BlockBush || b.getBlock() instanceof BlockMobSpawner) {
				world.setBlockToAir(pos);
			}
			else if (b.getMaterial() == Material.GRASS) {
				BlockPos up = pos.add(0, 1, 0);
				if (world.getBlockState(up).getMaterial() == Material.PLANTS) {
					world.setBlockToAir(up);
				}
				/*if(!getRadioactive())
				{
					world.setBlockState(pos, Blocks.DIRT.getDefaultState());
				}*/
				if (getThermonuclear()) {
					if (getIntensity() > 0) {
						int chance = rand.nextInt(12);
						if (chance < getIntensity() && chance > 0) {
							world.setBlockState(pos, ModBlocks.radioactive_earth2.getDefaultState());
						}
						else
							world.setBlockState(pos, ModBlocks.radioactive_earth.getDefaultState());
					}
				}
				if (getSalted()) {
					world.setBlockState(pos, ModBlocks.radioactive_earth.getDefaultState());
					return;
				}
				// if(getRadioactive())
				// {
				world.setBlockState(pos, NCBlocks.wasteland_earth.getDefaultState());
				// }
				return;
				
			}
			else if (b.getBlock() == Blocks.MYCELIUM) {
				if (getThermonuclear()) {
					if (getIntensity() > 0) {
						int chance = rand.nextInt(12);
						if (chance < getIntensity() && chance > 0) {
							world.setBlockState(pos, ModBlocks.radioactive_earth2.getDefaultState());
						}
						else
							world.setBlockState(pos, ModBlocks.radioactive_earth.getDefaultState());
					}
				}
				if (getSalted()) {
					world.setBlockState(pos, ModBlocks.radioactive_earth.getDefaultState());
					return;
				}
				// if(getRadioactive())
				// {
				world.setBlockState(pos, NCBlocks.wasteland_earth.getDefaultState());
				// }
				return;
			}
			else if (b.getMaterial() == Material.GROUND) {
				depth++;
				BlockPos up = pos.add(0, 1, 0);
				
				if (depth < 2)
					if (world.getBlockState(up).getMaterial() == Material.PLANTS) {
						world.setBlockToAir(up);
					}
				if (getThermonuclear()) {
					if (getIntensity() > 0) {
						int chance = rand.nextInt(12);
						if (chance < getIntensity() && chance > 0) {
							world.setBlockState(pos, ModBlocks.radioactive_earth2.getDefaultState());
						}
						else
							world.setBlockState(pos, ModBlocks.radioactive_earth.getDefaultState());
					}
				}
				if (getSalted()) {
					world.setBlockState(pos, ModBlocks.radioactive_earth.getDefaultState());
					return;
				}
				// if(getRadioactive())
				// {
				world.setBlockState(pos, NCBlocks.wasteland_earth.getDefaultState());
				// }
				return;
			}
			else if (b.getBlock() == Blocks.SAND) {
				if (dist < 30) {
					BlockPos up = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
					if (world.isAirBlock(up)) {
						world.setBlockState(pos, ModBlocks.trinitite.getDefaultState());
						return;
					}
					return;
				}
				if (getThermonuclear()) {
					if (getIntensity() > 0) {
						int chance = rand.nextInt(12);
						if (chance < getIntensity() && chance > 0) {
							world.setBlockState(pos, ModBlocks.salted_sand2.getDefaultState());
						}
						else
							world.setBlockState(pos, ModBlocks.salted_sand.getDefaultState());
					}
				}
				if (getSalted() && dist >= 30) {
					world.setBlockState(pos, ModBlocks.salted_sand.getDefaultState());
				}
			}
			
			else if (b.getBlock() == Blocks.CLAY && dist < 65) {
				world.setBlockState(pos, Blocks.HARDENED_CLAY.getDefaultState());
				return;
			}
			
			else if (b.getBlock() == NCBlocks.wasteland_earth) {
				if (getThermonuclear()) {
					if (getIntensity() > 0) {
						int chance = rand.nextInt(12);
						if (chance <= getIntensity() && chance > 0) {
							world.setBlockState(pos, ModBlocks.radioactive_earth2.getDefaultState());
						}
						else
							world.setBlockState(pos, ModBlocks.radioactive_earth.getDefaultState());
					}
				}
				if (getSalted()) {
					world.setBlockState(pos, ModBlocks.radioactive_earth.getDefaultState());
				}
				return;
			}
			
			/*if(Trinity.TCLoaded)
			{
				float vis=AuraHelper.getVis(world, pos);
				float flux=AuraHelper.getFlux(world, pos);
				AuraHelper.drainVis(world, pos, vis, false);
				AuraHelper.drainFlux(world, pos, flux, false);
				Block block = b.getBlock();
				if(block==BlocksTC.crystalAir||block==BlocksTC.crystalEarth||block==BlocksTC.crystalEntropy||block==BlocksTC.crystalFire||block==BlocksTC.crystalOrder||block==BlocksTC.crystalTaint||block==BlocksTC.crystalWater)
				{
					world.setBlockToAir(pos);
				}
			}*/
			
			else if (b.getBlock() instanceof BlockFluidClassic) {
				// world.setBlockState(pos, ModBlocks.radioactive_earth.getDefaultState());
				break;
			}
			
			else if (b.getBlock() == Blocks.MOSSY_COBBLESTONE) {
				world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
				return;
			}
			
			/*else if (b.getBlock() == Blocks.MONSTER_EGG) {
				if(b.getValue(BlockSilverfish.VARIANT)==BlockSilverfish.EnumType.COBBLESTONE)
				{
					world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
					return;
				}
				if(b.getValue(BlockSilverfish.VARIANT)==BlockSilverfish.EnumType.STONE)
				{
					world.setBlockState(pos, Blocks.STONE.getDefaultState());
					return;
				}
				if(b.getValue(BlockSilverfish.VARIANT)==BlockSilverfish.EnumType.STONEBRICK)
				{
					world.setBlockState(pos, Blocks.STONEBRICK.getDefaultState());
					return;
				}
				if(b.getValue(BlockSilverfish.VARIANT)==BlockSilverfish.EnumType.MOSSY_STONEBRICK)
				{
					world.setBlockState(pos, Blocks.STONEBRICK.getDefaultState());
					return;
				}
			}*/
			
			else if (b.getMaterial() == Material.WOOD) {
				if (dist < 65) {
					if (rand.nextInt(10) == 0)
						world.setBlockState(pos, Blocks.FIRE.getDefaultState());
					else
						world.setBlockToAir(pos);
					// world.setBlockState(pos, Blocks.FIRE.getDefaultState());
					return;
				}
				if (dist > 65 && dist < 70) {
					world.setBlockToAir(pos);
					return;
				}
				continue;
			}
			
			else if (b.getMaterial() == Material.CACTUS && dist < 65) {
				world.setBlockState(pos, Blocks.FIRE.getDefaultState());
				continue;
			}
			
			else if (b.getMaterial() == Material.GOURD) {
				if (dist < 65) {
					world.setBlockState(pos, Blocks.FIRE.getDefaultState());
					continue;
				}
				else {
					world.setBlockToAir(pos);
				}
			}
			
			else if (b.getBlock() instanceof BlockCactus && dist < 65) {
				world.setBlockState(pos, Blocks.FIRE.getDefaultState());
				continue;
			}
			
			else if (b.getBlock() == Blocks.STONE && dist < (100 / TrinityConfig.fallout_multiplier) && getThermonuclear()) {
				if (rand.nextInt((int) ((dist + 1) * 10)) <= 1)
					world.setBlockState(pos, Blocks.LAVA.getDefaultState());
				continue;
			}
			
			else if (b.getBlock() == Blocks.BROWN_MUSHROOM_BLOCK || b.getBlock() == Blocks.RED_MUSHROOM_BLOCK) {
				world.setBlockToAir(pos);
				continue;
			}
			
			else if (b.getBlock().isNormalCube(world.getBlockState(pos))) {
				
				return;
			}
		}
	}
	
	@Override
	protected void entityInit() {
		this.dataManager.register(SCALE, Integer.valueOf(0));
		this.dataManager.register(SALTED, Boolean.valueOf(false));
		this.dataManager.register(THERMONUCLEAR, Boolean.valueOf(false));
		// this.dataManager.register(RADIOACTIVE, Boolean.valueOf(false));
		this.dataManager.register(INTENSITY, Integer.valueOf(0));
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		setScale(p_70037_1_.getInteger("scale"));
		setSalted(p_70037_1_.getBoolean("salted"));
		setThermonuclear(p_70037_1_.getBoolean("thermonuclear"));
		// setRadioactive(p_70037_1_.getBoolean("radioactive"));
		setIntensity(p_70037_1_.getInteger("intensity"));
		revProgress = p_70037_1_.getInteger("revProgress");
		radProgress = p_70037_1_.getInteger("radProgress");
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		p_70014_1_.setInteger("scale", getScale());
		p_70014_1_.setBoolean("salted", getSalted());
		p_70014_1_.setBoolean("thermonuclear", getThermonuclear());
		// p_70014_1_.setBoolean("radioactive", getRadioactive());
		p_70014_1_.setInteger("intensity", getIntensity());
		p_70014_1_.setInteger("revProgress", revProgress);
		p_70014_1_.setInteger("radProgress", radProgress);
		
	}
	
	public void setSalted(boolean i) {
		
		this.dataManager.set(SALTED, Boolean.valueOf(i));
	}
	
	public boolean getSalted() {
		
		boolean salt = this.dataManager.get(SALTED);
		
		return salt == false ? false : salt;
	}
	
	public void setThermonuclear(boolean i) {
		
		this.dataManager.set(THERMONUCLEAR, Boolean.valueOf(i));
	}
	
	public boolean getThermonuclear() {
		
		boolean thermo = this.dataManager.get(THERMONUCLEAR);
		
		return thermo == false ? false : thermo;
	}
	
	/*public void setRadioactive(boolean i) {
	
		this.dataManager.set(RADIOACTIVE, Boolean.valueOf(i));
	}
	
	public boolean getRadioactive() {
	
		boolean thermo = this.dataManager.get(RADIOACTIVE);
		
		return thermo == false ? false : thermo;
	}*/
	
	public void setIntensity(int i) {
		
		this.dataManager.set(INTENSITY, Integer.valueOf(i));
	}
	
	public int getIntensity() {
		
		int intensity = this.dataManager.get(INTENSITY);
		
		return intensity == 0 ? 1 : intensity;
	}
	
	public void setScale(int i) {
		
		this.dataManager.set(SCALE, Integer.valueOf(i));
	}
	
	public int getScale() {
		
		int scale = this.dataManager.get(SCALE);
		
		return scale == 0 ? 1 : scale;
	}
}
