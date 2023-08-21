package trinity.entities;

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

import static icbm.classic.api.ExplosiveRefs.EMP;

public class EntityThermonuclearBlast extends Entity {
	
    private static final DataParameter<Integer> SCALE = EntityDataManager.<Integer>createKey(EntityThermonuclearBlast.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> INTENSITY = EntityDataManager.<Integer>createKey(EntityThermonuclearBlast.class, DataSerializers.VARINT);
    
	public ExplosionThermonuclear exp;

	public int age = 0;
	public int destructionRange = 0;
    public int falloutIntensity = 0;
	public int speed = 1;
	public boolean did = false;

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		age = nbt.getInteger("age");
		destructionRange = nbt.getInteger("destructionRange");
        falloutIntensity = nbt.getInteger("falloutIntensity");
		speed = nbt.getInteger("speed");
		did = nbt.getBoolean("did");
		
    	
		exp = new ExplosionThermonuclear((int)this.posX, (int)this.posY, (int)this.posZ, this.world, this.destructionRange);
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
    	
		if(exp != null)
			exp.saveToNbt(nbt, "exp_");
		
	}

	public EntityThermonuclearBlast(World p_i1582_1_) {
		super(p_i1582_1_);
	}

    @Override
	public void onUpdate() {
        super.onUpdate();
        	
        if(!this.did)
        {
        	exp = new ExplosionThermonuclear((int)this.posX, (int)this.posY, (int)this.posZ, this.world, getScale());

            EntityFalloutRain fallout = new EntityFalloutRain(this.world);
            fallout.posX = this.posX;
            fallout.posY = this.posY;
            fallout.posZ = this.posZ;
            fallout.setScale((int)(this.getScale() * TrinityConfig.fallout_multiplier));
            fallout.setThermonuclear(true);
            fallout.setIntensity(this.getIntensity());
            this.world.spawnEntity(fallout);

            EntityShockwave shock = new EntityShockwave(this.world);
            shock.posX = this.posX;
            shock.posY = this.posY;
            shock.posZ = this.posZ;
            shock.setScale(this.getScale() * 2);
            this.world.spawnEntity(shock);

            if(Trinity.ICBMLoaded)
            {
                new BlastEMP().setBlastWorld(this.world).setBlastSource(this).setBlastPosition(this.posX, this.posY, this.posZ)
                .setBlastSize(getScale()*2)
                .setExplosiveData(EMP)
                .buildBlast().runBlast();
            }

        	this.did = true;
        }
        
        speed += 1;	//increase speed to keep up with expansion

        boolean shouldDie = false;

        for(int i = 0; i < this.speed; i++)
        {
        	shouldDie = exp.update();

        	if(shouldDie) {
        		this.setDead();
        	}
        }
        
    	if(rand.nextInt(5) == 0)
        	this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.AMBIENT, 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);

        if(!shouldDie)
        {
        	this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.AMBIENT, 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
        	ExplosionNukeGeneric.dealDamage(this.world, (int)this.posX, (int)this.posY, (int)this.posZ, this.destructionRange * 2);
        }
        
        age++;
    }

	@Override
	protected void entityInit() { }

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
