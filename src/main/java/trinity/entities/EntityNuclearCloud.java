package trinity.entities;

import trinity.handler.INuclearEffect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityNuclearCloud extends Entity implements INuclearEffect {

	private static final DataParameter<Integer> AGE = EntityDataManager.createKey(EntityNuclearCloud.class,
			DataSerializers.VARINT);

	private static final DataParameter<Integer> MAXAGE = EntityDataManager.createKey(EntityNuclearCloud.class,
			DataSerializers.VARINT);

	public static final DataParameter<Float> SCALE = EntityDataManager.createKey(EntityNuclearCloud.class,
			DataSerializers.FLOAT);

	public static final DataParameter<Byte> SOMETHING = EntityDataManager.createKey(EntityNuclearCloud.class,
			DataSerializers.BYTE);
	public int maxAge = 1000;
	public int age;

	public EntityNuclearCloud(World p_i1582_1_) {
		super(p_i1582_1_);
		this.setSize(1, 80);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
		this.age = 0;

	}
//	@Override
//	public boolean shouldRenderInPass() {
//		
//	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
		double X = this.posX;
		double X2 = MathHelper.floor(X - 200 - 1.0D);
		double X3 = MathHelper.floor(X + 200 - 1.0D);
		double Y = this.posY;
		double Y2 = MathHelper.floor(Y - 200 - 1.0D);
		double Y3 = MathHelper.floor(Y + 200 - 1.0D);
		double Z = this.posZ;
		double Z2 = MathHelper.floor(Z - 200 - 1.0D);
		double Z3 = MathHelper.floor(Z + 200 - 1.0D);
		AxisAlignedBB bb = new AxisAlignedBB(X2,Y2,Z2,X3,Y3,Z3);
		return bb;
        //return this.getEntityBoundingBox();
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender() {
		return 15728880;
	}

	@Override
	public float getBrightness() {
		return 1.0F;
	}

	public EntityNuclearCloud(World p_i1582_1_, int maxAge, float scale) {
		super(p_i1582_1_);
		this.setSize(20, 40);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
		this.maxAge = maxAge;
		this.dataManager.set(SCALE, scale);
	}

	@Override
	public void onUpdate() {

		this.age++;
		this.world.spawnEntity(new EntityLightningBolt(this.world, this.posX, this.posY + 400, this.posZ, true));

		if (this.age >= this.maxAge) {
			this.age = 0;
			this.setDead();
		}

		this.dataManager.set(MAXAGE, maxAge);
		this.dataManager.set(AGE, age);
	}

	@Override
	protected void entityInit() {
		this.dataManager.register(MAXAGE, maxAge);
		this.dataManager.register(AGE, age);
		this.dataManager.register(SCALE, 1.0F);
		this.dataManager.register(SOMETHING, Byte.valueOf((byte) 0));
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("maxAge"))
			maxAge = nbt.getShort("maxAge");
		if (nbt.hasKey("age"))
			age = nbt.getShort("age");
		if (nbt.hasKey("scale"))
			this.dataManager.set(SCALE, nbt.getFloat("scale"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		p_70014_1_.setShort("maxAge", (short) maxAge);
		p_70014_1_.setShort("age", (short) age);
		p_70014_1_.setFloat("scale", this.dataManager.get(SCALE));

	}

	public static EntityNuclearCloud statFac(World world, double x, double y, double z, float radius) {

		EntityNuclearCloud cloud = new EntityNuclearCloud(world, (int) radius * 5, radius * 0.005F);
		cloud.posX = x;
		cloud.posY = y;
		cloud.posZ = z;
		cloud.dataManager.set(SOMETHING, (byte) 0);

		return cloud;
	}

	public static EntityNuclearCloud statFacBale(World world, double x, double y, double z, float radius,
			int maxAge) {

		EntityNuclearCloud cloud = new EntityNuclearCloud(world, (int) radius * 5, radius * 0.005F);
		cloud.posX = x;
		cloud.posY = y;
		cloud.posZ = z;
		cloud.dataManager.set(SOMETHING, (byte) 1);

		return cloud;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return distance < 25000;
	}
}
