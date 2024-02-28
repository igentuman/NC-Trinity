package trinity.entities;

import icbm.classic.api.refs.ICBMExplosives;
import icbm.classic.content.blast.BlastEMP;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import trinity.Trinity;
import trinity.config.TrinityConfig;
import trinity.explosion.ExplosionNukeGeneric;
import trinity.explosion.ExplosionThermonuclear;

public class EntityThermonuclearBlast extends Entity {
	
	private static final DataParameter<Integer> SCALE = EntityDataManager.<Integer>createKey(EntityThermonuclearBlast.class, DataSerializers.VARINT);
	// private static final DataParameter<Boolean> SALTED = EntityDataManager.<Boolean>createKey(EntityThermonuclearBlast.class, DataSerializers.BOOLEAN);
	// private static final DataParameter<Boolean> THERMONUCLEAR = EntityDataManager.<Boolean>createKey(EntityThermonuclearBlast.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> INTENSITY = EntityDataManager.<Integer>createKey(EntityThermonuclearBlast.class, DataSerializers.VARINT);
	
	// public int revProgress;
	// public int radProgress;
	public ExplosionThermonuclear exp;
	
	public int age = 0;
	public int destructionRange = 0;
	public int falloutIntensity = 0;
	// public ExplosionBalefire exp;
	public int speed = 1;
	public boolean did = false;
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		age = nbt.getInteger("age");
		destructionRange = nbt.getInteger("destructionRange");
		falloutIntensity = nbt.getInteger("falloutIntensity");
		speed = nbt.getInteger("speed");
		did = nbt.getBoolean("did");
		
		exp = new ExplosionThermonuclear((int) this.posX, (int) this.posY, (int) this.posZ, this.world, this.destructionRange);
		exp.readFromNbt(nbt, "exp_");
		
		this.did = true;
		
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("age", age);
		nbt.setInteger("destructionRange", destructionRange);
		nbt.setInteger("falloutIntensity", falloutIntensity);
		nbt.setInteger("speed", speed);
		nbt.setBoolean("did", did);
		
		if (exp != null)
			exp.saveToNbt(nbt, "exp_");
		
	}
	
	public EntityThermonuclearBlast(World p_i1582_1_) {
		super(p_i1582_1_);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if (!this.did) {
			// if(GeneralConfig.enableExtendedLogging && !world.isRemote)
			// MainRegistry.logger.log(Level.INFO, "[NUKE] Initialized BF explosion at " + posX + " / " + posY + " / " + posZ + " with strength " + destructionRange + "!");
			
			exp = new ExplosionThermonuclear((int) this.posX, (int) this.posY, (int) this.posZ, this.world, getScale());
			
			EntityFalloutRain fallout = new EntityFalloutRain(this.world);
			fallout.posX = this.posX;
			fallout.posY = this.posY;
			fallout.posZ = this.posZ;
			fallout.setScale((int) (this.getScale() * TrinityConfig.fallout_multiplier));
			fallout.setThermonuclear(true);
			fallout.setIntensity(this.getIntensity());
			this.world.spawnEntity(fallout);
			
			EntityShockwave shock = new EntityShockwave(this.world);
			shock.posX = this.posX;
			shock.posY = this.posY;
			shock.posZ = this.posZ;
			shock.setScale(this.getScale() * 2);
			this.world.spawnEntity(shock);
			
			if (Trinity.ICBMLoaded) {
				new BlastEMP().setBlastWorld(this.world).setBlastSource(this).setBlastPosition(this.posX, this.posY, this.posZ).setBlastSize(getScale() * 2).setExplosiveData(ICBMExplosives.EMP).buildBlast().runBlast();
			}
			
			this.did = true;
		}
		
		speed += 1; // increase speed to keep up with expansion
		
		boolean shouldDie = false;
		
		for (int i = 0; i < this.speed; i++) {
			shouldDie = exp.update();
			
			if (shouldDie) {
				this.setDead();
			}
		}
		
		if (rand.nextInt(5) == 0)
			this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.AMBIENT, 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
		
		if (!shouldDie) {
			this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.AMBIENT, 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
			ExplosionNukeGeneric.dealDamage(this.world, (int) this.posX, (int) this.posY, (int) this.posZ, this.destructionRange * 2);
		}
		
		age++;
	}
	
	@Override
	protected void entityInit() {}
	
	/*	public int speed = 1;
	//private boolean salted;
	//private boolean thermonuclear;
	
	public EntityThermonuclearBlast(World p_i1582_1_) {
		super(p_i1582_1_);
		this.setSize(0, 0);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
	}
	
	public EntityThermonuclearBlast(World p_i1582_1_, boolean salt) {
		super(p_i1582_1_);
		this.setSize(0, 0);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
		//this.salted = salt;
	}
	
	public EntityThermonuclearBlast(World p_i1582_1_, int maxAge) {
		super(p_i1582_1_);
		this.setSize(0, 0);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		double X = this.posX;
		double X2 = MathHelper.floor(X - getScale() - 1.0D);
		double X3 = MathHelper.floor(X + getScale() - 1.0D);
		double Y = this.posY;
		double Y2 = MathHelper.floor(Y - getScale() - 1.0D);
		double Y3 = MathHelper.floor(Y + getScale() - 1.0D);
		double Z = this.posZ;
		double Z2 = MathHelper.floor(Z - getScale() - 1.0D);
		double Z3 = MathHelper.floor(Z + getScale() - 1.0D);
		AxisAlignedBB bb = new AxisAlignedBB(X2,Y2,Z2,X3,Y3,Z3);
		return bb;
	    //return this.getEntityBoundingBox();
	}
	
	@Override
	public void onUpdate() {
		boolean spawn = false;
	
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
	    }
	
	    if(!world.isRemote) {
	    	MutableBlockPos pos = new BlockPos.MutableBlockPos();
	    	for(int i = 0; i < 512; i++) {
	    		
	        	Vec3 vec = Vec3.createVectorHelper(radProgress * 0.5, 0, 0);
	        	double circum = radProgress * 2 * Math.PI * 2;
	        	
	        	///
	        	if(circum == 0)
	        		circum = 1;
	        	///
	        	
	        	double part = 360D / circum;
	        	
	        	vec.rotateAroundY((float) (part * revProgress));
	        	
	        	int x = (int) (posX + vec.xCoord);
	        	int z = (int) (posZ + vec.zCoord);
	        	
	        	double dist = radProgress * 100 / getScale() * 0.5;
	        	pos.setPos(x, posY, z);
	        	contaminate(pos, dist);
	        	
	        	revProgress++;
	        	
	        	if(revProgress > circum) {
	        		revProgress = 0;
	        		radProgress++;
	        	}
	        	
	        	if(radProgress > getScale() * 2D) {
					this.setDead();
					if(!spawn)
					{						
						spawn=true;
						EntityShockwave shock = new EntityShockwave(this.world);
						shock.posX = this.posX;
						shock.posY = this.posY;
						shock.posZ = this.posZ;
						shock.setScale((int)(this.getScale() * 2) * 100 / 100);
						this.world.spawnEntity(shock);
	        		
						EntityFalloutRain fallout = new EntityFalloutRain(this.world);
						fallout.posX = this.posX;
						fallout.posY = this.posY;
						fallout.posZ = this.posZ;
						fallout.setScale((int)(this.getScale() * TrinityConfig.fallout_multiplier) * 100 / 100);
						fallout.setThermonuclear(true);
						fallout.setIntensity(this.getIntensity());
						this.world.spawnEntity(fallout);
					}
	        	}
	    	}
	    }
	}
	
	private void contaminate(MutableBlockPos pos, double dist) {
		
		int depth = 0;
		
		//int mult = 1;
		int maxDepth = (int) (dist/10);
		int topBlock =world.getTopSolidOrLiquidBlock(pos).getY();
		
		for(int y = topBlock+35; y >= topBlock-maxDepth; y--) {
			pos.setY(y);
			IBlockState b =  world.getBlockState(pos);
			//int meta = world.getBlockMetadata(x, y, z);
	
			if(b.getBlock()!=Blocks.AIR && b.getBlock().getExplosionResistance(this)<=200) {
				depth++;
				if(depth<=dist/10)
				{
					world.setBlockToAir(pos);
					return;
				}
				//continue;
			}
		}
	}
	
	@Override
	protected void entityInit() {
		this.dataManager.register(SCALE, Integer.valueOf(0));
	//		this.dataManager.register(SALTED, Boolean.valueOf(false));
	//		this.dataManager.register(THERMONUCLEAR, Boolean.valueOf(false));
		this.dataManager.register(INTENSITY, Integer.valueOf(0));
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		setScale(p_70037_1_.getInteger("scale"));
	//		setSalted(p_70037_1_.getBoolean("salted"));
	//		setThermonuclear(p_70037_1_.getBoolean("thermonuclear"));
		setIntensity(p_70037_1_.getInteger("intensity"));
		revProgress = p_70037_1_.getInteger("revProgress");
		radProgress = p_70037_1_.getInteger("radProgress");
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		p_70014_1_.setInteger("scale", getScale());
	//		p_70014_1_.setBoolean("salted", getSalted());
	//		p_70014_1_.setBoolean("thermonuclear", getThermonuclear());
		p_70014_1_.setInteger("intensity", getIntensity());
		p_70014_1_.setInteger("revProgress", revProgress);
		p_70014_1_.setInteger("radProgress", radProgress);
		
	}
	
	/*	public void setSalted(boolean i) {
	
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
	*/
	public void setIntensity(int i) {
		this.falloutIntensity = i;
	}
	
	public int getIntensity() {
		
		int intensity = this.falloutIntensity;
		
		return intensity == 0 ? 1 : intensity;
	}
	
	public void setScale(int i) {
		this.destructionRange = i;
	}
	
	public int getScale() {
		
		int scale = this.destructionRange;
		
		return scale == 0 ? 1 : scale;
	}
}
