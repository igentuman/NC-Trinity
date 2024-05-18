package trinity.entities;

import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import trinity.Trinity;
import trinity.config.TrinityConfig;
import trinity.explosion.*;

public class EntityNuclearExplosion extends Entity {
	
	// Strength of the blast
	public int strength;
	public int count;
	public int speed;
	public int length;
	
	public boolean fallout = true;
	
	public boolean salted = false;
	public boolean thermonuclear = false;
	public boolean thermal = true;
	public boolean Void = false;
	public int chance = 0;
	
	private ForgeChunkManager.Ticket ticket = null;
	private boolean isChunkLoaded = false;
	
	ExplosionNukeRay explosion;
	
	public EntityNuclearExplosion(World p_i1582_1_) {
		super(p_i1582_1_);
	}
	
	public EntityNuclearExplosion(World world, int strength, int count, int speed, int length) {
		super(world);
		this.strength = strength;
		this.count = count;
		this.speed = speed;
		this.length = length;
	}
	
	@Override
	public void onUpdate() {
		
		if (strength == 0) {
			this.setDead();
			return;
		}
		
		if (!isChunkLoaded && !this.world.isRemote) {
			int chunkX = (int) Math.floor(this.posX) >> 4;
			int chunkZ = (int) Math.floor(this.posZ) >> 4;
			ticket = ForgeChunkManager.requestTicket(Trinity.instance, this.world, ForgeChunkManager.Type.NORMAL);
			if (ticket != null) {
				// System.out.println(ticket + " being loaded for " + chunkX + ", " + chunkZ);
				ForgeChunkManager.forceChunk(ticket, new ChunkPos(chunkX, chunkZ));
				isChunkLoaded = true;
			}
		}
		
		BlockPos pos = new BlockPos(this.posX, this.posY, this.posZ);
		this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.AMBIENT, 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F, false);
		
		if (rand.nextInt(5) == 0)
			this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.AMBIENT, 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F, false);
		
		if (thermal) {
			ExplosionNukeGeneric.dealDamage(this.world, (int) this.posX, (int) this.posY, (int) this.posZ, this.length * 10);
		}
		else if (!thermal) {
			ExplosionNukeGeneric.dealDamage(this.world, (int) this.posX, (int) this.posY, (int) this.posZ, this.length * 2);
		}
		
		if (explosion == null) {
			explosion = new ExplosionNukeRay(world, (int) this.posX, (int) this.posY, (int) this.posZ, this.strength, this.count, this.speed, this.length, this.Void);
		}
		
		if (!explosion.isAusf3Complete) {
			explosion.collectTipMk4_5(speed * 10);
		}
		else if (explosion.getStoredSize() > 0) {
			explosion.processTip(1024);
		}
		else if (fallout) {
			EntityShockwave shock = new EntityShockwave(this.world);
			shock.posX = this.posX;
			shock.posY = this.posY;
			shock.posZ = this.posZ;
			shock.setScale((int) (this.length * 2) * 100 / 100);
			this.world.spawnEntity(shock);
			if (thermonuclear) {
				EntityFalloutRain fallout = new EntityFalloutRain(this.world);
				fallout.setChunkTicket(ticket);
				fallout.posX = this.posX;
				fallout.posY = this.posY;
				fallout.posZ = this.posZ;
				fallout.setScale((int) (this.length * TrinityConfig.fallout_multiplier) * 100 / 100);
				fallout.setThermonuclear(true);
				fallout.setIntensity(chance);
				this.world.spawnEntity(fallout);
			}
			if (salted) {
				EntityFalloutRain fallout = new EntityFalloutRain(this.world);
				fallout.setChunkTicket(ticket);
				fallout.posX = this.posX;
				fallout.posY = this.posY;
				fallout.posZ = this.posZ;
				fallout.setScale((int) (this.length * TrinityConfig.fallout_multiplier) * 100 / 100);
				fallout.setSalted(true);
				this.world.spawnEntity(fallout);
			}
			else if (!salted) {
				EntityFalloutRain fallout = new EntityFalloutRain(this.world);
				fallout.setChunkTicket(ticket);
				fallout.posX = this.posX;
				fallout.posY = this.posY;
				fallout.posZ = this.posZ;
				fallout.setScale((int) (this.length * TrinityConfig.fallout_multiplier) * 100 / 100);
				fallout.setSalted(false);
				this.world.spawnEntity(fallout);
			}
			/*if(!radioactive)
			{
				//System.out.println("should this be radioactive? "+radioactive);
				EntityThermalBlast fallout = new EntityThermalBlast(this.world);
				fallout.posX = this.posX;
				fallout.posY = this.posY;
				fallout.posZ = this.posZ;
				fallout.setScale((int)(this.length * TrinityConfig.fallout_multiplier) * 100 / 100);
				this.world.spawnEntity(fallout);
			}*/
			this.setDead();
		}
		else {
			EntityShockwave shock = new EntityShockwave(this.world);
			shock.posX = this.posX;
			shock.posY = this.posY;
			shock.posZ = this.posZ;
			shock.setScale((int) (this.length * 1.5) * 100 / 100);
			this.world.spawnEntity(shock);
			if (thermal) {
				EntityThermalBlast fallout = new EntityThermalBlast(this.world);
				fallout.posX = this.posX;
				fallout.posY = this.posY;
				fallout.posZ = this.posZ;
				fallout.setScale((int) (this.length * TrinityConfig.fallout_multiplier * 2) * 100 / 100);
				this.world.spawnEntity(fallout);
			}
			this.setDead();
		}
	}
	
	@Override
	protected void entityInit() {
	
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
	
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
	
	}
	
	public static EntityNuclearExplosion statFac(World world, int r, double x, double y, double z) {
		
		if (r == 0)
			r = 25;
		
		r *= 2;
		
		EntityNuclearExplosion mk4 = new EntityNuclearExplosion(world);
		mk4.strength = (int) (r);
		mk4.count = (int) (4 * Math.PI * Math.pow(mk4.strength, 2) * 25);
		mk4.speed = (int) Math.ceil(100000 / mk4.strength);
		mk4.setPosition(x, y, z);
		mk4.length = mk4.strength / 2;
		return mk4;
	}
	
	public static EntityNuclearExplosion statFacExperimental(World world, int r, double x, double y, double z) {
		
		r *= 2;
		
		EntityNuclearExplosion mk4 = new EntityNuclearExplosion(world);
		mk4.strength = (int) (r);
		mk4.count = (int) (4 * Math.PI * Math.pow(mk4.strength, 2) * 25);
		mk4.speed = (int) Math.ceil(100000 / mk4.strength);
		mk4.setPosition(x, y, z);
		mk4.length = mk4.strength / 2;
		mk4.salted = false;
		return mk4;
	}
	
	public static EntityNuclearExplosion statFacNoRad(World world, int r, double x, double y, double z) {
		
		r *= 2;
		
		EntityNuclearExplosion mk4 = new EntityNuclearExplosion(world);
		mk4.strength = (int) (r);
		mk4.count = (int) (4 * Math.PI * Math.pow(mk4.strength, 2) * 25);
		mk4.speed = (int) Math.ceil(100000 / mk4.strength);
		mk4.setPosition(x, y, z);
		mk4.length = mk4.strength / 2;
		mk4.fallout = false;
		mk4.thermal = false;
		return mk4;
	}
	
	public static EntityNuclearExplosion statFacAntimatter(World world, int r, double x, double y, double z) {
		
		r *= 2;
		
		EntityNuclearExplosion mk4 = new EntityNuclearExplosion(world);
		mk4.strength = (int) (r);
		mk4.count = (int) (4 * Math.PI * Math.pow(mk4.strength, 2) * 25);
		mk4.speed = (int) Math.ceil(100000 / mk4.strength);
		mk4.setPosition(x, y, z);
		mk4.length = mk4.strength / 2;
		mk4.fallout = false;
		mk4.thermal = true;
		return mk4;
	}
	
	public static EntityNuclearExplosion statFacSalted(World world, int r, double x, double y, double z) {
		
		r *= 2;
		
		EntityNuclearExplosion mk4 = new EntityNuclearExplosion(world);
		mk4.strength = (int) (r * (0.75f));
		mk4.count = (int) (4 * Math.PI * Math.pow(mk4.strength, 2) * 25);
		mk4.speed = (int) Math.ceil(100000 / mk4.strength);
		mk4.setPosition(x, y, z);
		mk4.length = mk4.strength / 2;
		mk4.salted = true;
		return mk4;
	}
	
	public static EntityNuclearExplosion statFacThermo(World world, int r, double x, double y, double z, int chance) {
		
		if (r == 0)
			r = 25;
		
		r *= 2;
		
		EntityNuclearExplosion mk4 = new EntityNuclearExplosion(world);
		mk4.strength = (int) (r);
		mk4.count = (int) (4 * Math.PI * Math.pow(mk4.strength, 2) * 25);
		mk4.speed = (int) Math.ceil(100000 / mk4.strength);
		mk4.setPosition(x, y, z);
		mk4.length = mk4.strength / 2;
		mk4.thermonuclear = true;
		mk4.chance = chance;
		return mk4;
	}
	
	public static EntityNuclearExplosion statFacHyperspace(World world, int r, double x, double y, double z) {
		
		r *= 2;
		
		EntityNuclearExplosion mk4 = new EntityNuclearExplosion(world);
		mk4.strength = (int) (r);
		mk4.count = (int) (4 * Math.PI * Math.pow(mk4.strength, 2) * 25);
		mk4.speed = (int) Math.ceil(100000 / mk4.strength);
		mk4.setPosition(x, y, z);
		mk4.length = mk4.strength / 2;
		mk4.fallout = false;
		mk4.thermal = false;
		mk4.Void = true;
		return mk4;
	}
}
