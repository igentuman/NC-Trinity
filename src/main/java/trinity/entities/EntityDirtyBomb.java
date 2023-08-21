package trinity.entities;

// import trinity.util.IConstantRenderer;

import javax.annotation.Nullable;

import nc.capability.radiation.source.IRadiationSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class EntityDirtyBomb extends Entity {
	
	private static final DataParameter<Integer> FUSE = EntityDataManager.<Integer>createKey(EntityDirtyBomb.class, DataSerializers.VARINT);
	private static final DataParameter<Float> RADS = EntityDataManager.<Float>createKey(EntityDirtyBomb.class, DataSerializers.FLOAT);
	@Nullable
	private EntityLivingBase tntPlacedBy;
	/** How long the fuse is */
	private int fuse;
	public float rads;
	
	public EntityDirtyBomb(World worldIn) {
		super(worldIn);
		this.fuse = 80;
		this.preventEntitySpawning = true;
		this.isImmuneToFire = true;
		this.setSize(0.98F, 0.98F);
	}
	
	public EntityDirtyBomb(World worldIn, double x, double y, double z, EntityLivingBase igniter, double radioactivity) {
		this(worldIn);
		this.setPosition(x, y, z);
		float f = (float) (Math.random() * (Math.PI * 2D));
		this.motionX = (double) (-((float) Math.sin((double) f)) * 0.02F);
		this.motionY = 0.20000000298023224D;
		this.motionZ = (double) (-((float) Math.cos((double) f)) * 0.02F);
		this.setFuse(80);
		this.rads = (float) radioactivity;
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
		this.tntPlacedBy = igniter;
	}
	
	protected void entityInit() {
		this.dataManager.register(FUSE, Integer.valueOf(80));
		this.dataManager.register(RADS, Float.valueOf(0));
	}
	
	/** returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to prevent them from trampling crops */
	protected boolean canTriggerWalking() {
		return false;
	}
	
	/** Returns true if other Entities should be prevented from moving through this Entity. */
	public boolean canBeCollidedWith() {
		return !this.isDead;
	}
	
	/** Called to update the entity's position/logic. */
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		
		if (!this.hasNoGravity()) {
			this.motionY -= 0.03999999910593033D;
		}
		
		this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.9800000190734863D;
		this.motionY *= 0.9800000190734863D;
		this.motionZ *= 0.9800000190734863D;
		
		if (this.onGround) {
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
			this.motionY *= -0.5D;
		}
		
		--this.fuse;
		
		if (this.fuse <= 0) {
			this.setDead();
			
			if (!this.world.isRemote) {
				this.explode((double) this.rads);
			}
		}
		else {
			this.handleWaterMovement();
			this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
		}
	}
	
	@SuppressWarnings("null")
	private void explode(double radioactivity) {
		BlockPos pos = new BlockPos(this.posX, this.posY, this.posZ);
		Chunk chunk = world.getChunk(pos);
		if (chunk != null || chunk.hasCapability(IRadiationSource.CAPABILITY_RADIATION_SOURCE, null)) {
			IRadiationSource chunkRadation = chunk.getCapability(IRadiationSource.CAPABILITY_RADIATION_SOURCE, null);
			if (chunkRadation != null) {
				if (chunkRadation.getRadiationBuffer() < (radioactivity)) {
					chunkRadation.setRadiationBuffer(radioactivity);
				}
				else {
					chunkRadation.setRadiationBuffer(chunkRadation.getRadiationLevel() + radioactivity);
				}
			}
		}
		
		float f = 4.0F;
		this.world.createExplosion(this, this.posX, this.posY + (double) (this.height / 16.0F), this.posZ, 4.0F, true);
	}
	
	/** (abstract) Protected helper method to write subclass entity data to NBT. */
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setShort("Fuse", (short) this.getFuse());
		compound.setShort("Radioactivity", (short) this.getRadioactivity());
	}
	
	/** (abstract) Protected helper method to read subclass entity data from NBT. */
	protected void readEntityFromNBT(NBTTagCompound compound) {
		this.setFuse(compound.getShort("Fuse"));
		this.setFuse(compound.getShort("Radioactivity"));
	}
	
	/** returns null or the entityliving it was placed or ignited by */
	@Nullable
	public EntityLivingBase getTntPlacedBy() {
		return this.tntPlacedBy;
	}
	
	public float getEyeHeight() {
		return 0.0F;
	}
	
	public void setFuse(int fuseIn) {
		this.dataManager.set(FUSE, Integer.valueOf(fuseIn));
		this.fuse = fuseIn;
	}
	
	public void notifyDataManagerChange(DataParameter<?> key) {
		if (FUSE.equals(key)) {
			this.fuse = this.getFuseDataManager();
		}
		if (RADS.equals(key)) {
			this.rads = this.getRadDataManager();
		}
	}
	
	/** Gets the fuse from the data manager */
	public int getFuseDataManager() {
		return ((Integer) this.dataManager.get(FUSE)).intValue();
	}
	
	public void setRadioactivity(float rads) {
		this.dataManager.set(RADS, Float.valueOf(rads));
		this.rads = rads;
	}
	
	public float getRadDataManager() {
		return ((Float) this.dataManager.get(RADS)).floatValue();
	}
	
	public int getFuse() {
		return this.fuse;
	}
	
	public double getRadioactivity() {
		return this.rads;
	}
}
