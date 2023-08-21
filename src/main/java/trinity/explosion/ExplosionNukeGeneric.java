package trinity.explosion;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

import nc.capability.radiation.entity.IEntityRads;
import nc.config.NCConfig;
import nc.radiation.RadiationHelper;
import nc.util.DamageSources;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import trinity.handler.Vec3;
import trinity.init.ModDamageSources;
// import net.minecraft.world.WorldSettings.GameType;
// import net.minecraftforge.common.util.ForgeDirection;

/* import com.hbm.blocks.ModBlocks; import com.hbm.blocks.generic.DecoBlockAlt; import com.hbm.entity.effect.EntityBlackHole; import com.hbm.entity.effect.EntityNukeCloudSmall; import com.hbm.entity.grenade.EntityGrenadeASchrab; import com.hbm.entity.grenade.EntityGrenadeNuclear; import com.hbm.entity.missile.EntityMIRV; import com.hbm.entity.projectile.EntityExplosiveBeam; import com.hbm.entity.projectile.EntityMiniMIRV; import com.hbm.entity.projectile.EntityMiniNuke; */
// import com.hbm.interfaces.IConsumer;
// import com.hbm.interfaces.ISource;
// import com.hbm.items.ModItems;
// import com.hbm.lib.Library;
// import com.hbm.lib.ModDamageSource;
// import com.hbm.tileentity.bomb.TileEntityTurretBase;
// import com.hbm.tileentity.machine.TileEntityDummy;

// import cofh.api.energy.IEnergyProvider;

public class ExplosionNukeGeneric {
	
	private final static Random random = new Random();
	
	public static boolean isObstructed(World world, BlockPos pos1, BlockPos pos2) {
		
		Vec3 vector = Vec3.createVectorHelper(pos2.getX() - pos1.getX(), pos2.getY() - pos1.getY(), pos2.getZ() - pos1.getZ());
		double length = vector.lengthVector();
		Vec3 nVec = vector.normalize();
		for (float i = 0; i < length; i += 0.25F) {
			BlockPos pos3 = new BlockPos((int) Math.round(pos1.getX() + (nVec.xCoord * i)), (int) Math.round(pos1.getY() + (nVec.yCoord * i)), (int) Math.round(pos1.getZ() + (nVec.zCoord * i)));
			if (world.getBlockState(pos3).getBlock() != Blocks.AIR && world.getBlockState(pos3).isNormalCube())
				return true;
		}
		return false;
	}
	
	public static void dealDamage(World world, int x, int y, int z, int bombStartStrength) {
		float f = bombStartStrength;
		HashSet hashset = new HashSet();
		int i;
		int j;
		int k;
		double d5;
		double d6;
		double d7;
		double wat = bombStartStrength/** 2 */
		;
		boolean isOccupied = false;
		
		// bombStartStrength *= 2.0F;
		i = MathHelper.floor(x - wat - 1.0D);
		j = MathHelper.floor(x + wat + 1.0D);
		k = MathHelper.floor(y - wat - 1.0D);
		int i2 = MathHelper.floor(y + wat + 1.0D);
		int l = MathHelper.floor(z - wat - 1.0D);
		int j2 = MathHelper.floor(z + wat + 1.0D);
		AxisAlignedBB bb = new AxisAlignedBB(i, k, l, j, i2, j2);
		List list = world.getEntitiesWithinAABBExcludingEntity(null, bb);
		Vec3d vec3 = new Vec3d(x, y, z);
		
		for (int i1 = 0; i1 < list.size(); ++i1) {
			Entity entity = (Entity) list.get(i1);
			double d4 = entity.getDistance(x, y, z) / bombStartStrength;
			
			if (d4 <= 1.0D) {
				d5 = entity.posX - x;
				d6 = entity.posY + entity.getEyeHeight() - y;
				d7 = entity.posZ - z;
				BlockPos pos1 = new BlockPos(x, y, z);
				BlockPos pos2 = new BlockPos(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
				double d9 = MathHelper.sqrt(d5 * d5 + d6 * d6 + d7 * d7);
				if (!isObstructed(world, pos1, pos2)) {
					if (d9 < wat && !(entity instanceof EntityPlayer)) {
						/*
						 						&& !(entity instanceof EntityPlayer	&& Library.checkArmor((EntityPlayer) entity, ModItems.euphemium_helmet,
																ModItems.euphemium_plate, ModItems.euphemium_legs, ModItems.euphemium_boots))) {
						 */
						
						d5 /= d9;
						d6 /= d9;
						d7 /= d9;
						double d11 = (1.0D - d4);// * d10;
						if (!(entity instanceof EntityPlayerMP) || (entity instanceof EntityPlayerMP && !((EntityPlayerMP) entity).isCreative())) {
							// entity.attackEntityFrom(DamageSource.generic,
							// ((int)((d11 * d11 + d11) / 2.0D * 8.0D *
							// bombStartStrength + 1.0D)));
							double realisticDamage = 4 * (bombStartStrength * bombStartStrength) / entity.getDistance(x, y, z);
							double damage = entity.getDistance(x, y, z) / bombStartStrength * 250;
							// entity.attackEntityFrom(ModDamageSource.nuclearBlast, (float)damage);
							entity.attackEntityFrom(ModDamageSources.NUCLEAR_EXPLOSION, (float) damage);
							entity.setFire(5);
							if (entity instanceof EntityLivingBase) {
								double d8 = EnchantmentProtection.getBlastDamageReduction((EntityLivingBase) entity, d11);
								entity.motionX += d5 * d8 * 0.2D;
								entity.motionY += d6 * d8 * 0.2D;
								entity.motionZ += d7 * d8 * 0.2D;
							}
							entity.motionX += d5 * 0.2D;
							entity.motionY += d6 * 0.2D;
							entity.motionZ += d7 * 0.2D;
						}
					}
				}
			}
		}
		
		bombStartStrength = (int) f;
	}
	
	public static void irradiate(World world, int x, int y, int z, int bombStartStrength) {
		float f = bombStartStrength;
		HashSet hashset = new HashSet();
		int i;
		int j;
		int k;
		double d5;
		double d6;
		double d7;
		double wat = bombStartStrength/** 2 */
		;
		boolean isOccupied = false;
		
		// bombStartStrength *= 2.0F;
		i = MathHelper.floor(x - wat - 1.0D);
		j = MathHelper.floor(x + wat + 1.0D);
		k = MathHelper.floor(y - wat - 1.0D);
		int i2 = MathHelper.floor(y + wat + 1.0D);
		int l = MathHelper.floor(z - wat - 1.0D);
		int j2 = MathHelper.floor(z + wat + 1.0D);
		AxisAlignedBB bb = new AxisAlignedBB(i, k, l, j, i2, j2);
		List list = world.getEntitiesWithinAABBExcludingEntity(null, bb);
		Vec3d vec3 = new Vec3d(x, y, z);
		
		for (int i1 = 0; i1 < list.size(); ++i1) {
			Entity entity = (Entity) list.get(i1);
			double d4 = entity.getDistance(x, y, z) / bombStartStrength;
			
			if (d4 <= 1.0D) {
				d5 = entity.posX - x;
				d6 = entity.posY + entity.getEyeHeight() - y;
				d7 = entity.posZ - z;
				double d9 = MathHelper.sqrt(d5 * d5 + d6 * d6 + d7 * d7);
				if (d9 < wat) {
					d5 /= d9;
					d6 /= d9;
					d7 /= d9;
					double d11 = (1.0D - d4);
					double rads = 100 * (Math.pow(bombStartStrength, 2)) / Math.pow(entity.getDistance(x, y, z), 2);
					
					if (entity instanceof EntityLivingBase) {
						IEntityRads eRads = RadiationHelper.getEntityRadiation((EntityLivingBase) entity);
						eRads.setRadiationLevel(RadiationHelper.addRadsToEntity(eRads, ((EntityLivingBase) entity), rads, false, false, 1));
						if (rads >= NCConfig.max_player_rads) {
							entity.setEntityInvulnerable(false);
							entity.attackEntityFrom(DamageSources.FATAL_RADS, Float.MAX_VALUE);
							// ((EntityLivingBase) entity).onDeath(DamageSources.FATAL_RADS);
							// ((EntityLivingBase) entity).setHealth(0);
							// entity.attackEntityFrom(DamageSources.FATAL_RADS, Float.MAX_VALUE);
						}
					}
				}
			}
		}
		
		bombStartStrength = (int) f;
	}
	
}
