package trinity.entities;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.MathHelper;
// import net.minecraft.util.AxisAlignedBB;
// import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
// import net.minecraftforge.common.util.ForgeDirection;
import trinity.handler.Vec3;

public class EntityShockwave extends Entity {
	
	private static final DataParameter<Integer> SCALE = EntityDataManager.<Integer>createKey(EntityShockwave.class, DataSerializers.VARINT);
	
	public int revProgress;
	public int radProgress;
	
	private int iCounter = 0;
	private boolean needsInit = true;
	
	private int blastState = 0; // Not started
	private int blastCount = 0;
	
	private MutableBlockPos pos;
	private MutableBlockPos pos2;
	private MutableBlockPos pos3;
	private MutableBlockPos pos4;
	private MutableBlockPos pos5;
	
	private double dist;
	private double circum;
	
	public EntityShockwave(World p_i1582_1_) {
		super(p_i1582_1_);
		this.setSize(0, 0);
		// this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
	}
	
	public EntityShockwave(World p_i1582_1_, int maxAge) {
		super(p_i1582_1_);
		this.setSize(0, 0);
		// this.ignoreFrustumCheck = true;
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
	
	@Override
	public void onUpdate() {
		
		if (!world.isRemote) {
			if (needsInit) {
				pos = new BlockPos.MutableBlockPos();
				pos2 = new BlockPos.MutableBlockPos();
				pos3 = new BlockPos.MutableBlockPos();
				pos4 = new BlockPos.MutableBlockPos();
				pos5 = new BlockPos.MutableBlockPos();
				needsInit = false;
			}
			if (iCounter < 512 * 3) {
				if (blastState == 0) { // Not started
					Vec3 vec = Vec3.createVectorHelper(radProgress * 0.5, 0, 0);
					circum = radProgress * 2 * Math.PI * 2;
					
					///
					if (circum == 0)
						circum = 1;
					///
					
					double part = 360D / circum;
					
					vec.rotateAroundY((float) (part * revProgress));
					
					int x = (int) (posX + vec.xCoord);
					int z = (int) (posZ + vec.zCoord);
					
					dist = radProgress * 100 / getScale() * 0.5;
					pos.setPos(x, posY, z);
					pos2.setPos(x - 1, posY, z);
					pos3.setPos(x + 1, posY, z);
					pos4.setPos(x, posY, z - 1);
					pos5.setPos(x, posY, z - 1);
					blastState = 1;
				}
				else if (blastState == 1) {
					switch (blastCount) {
						case 0:
							blast(pos, dist);
							blastCount++;
							break;
						case 1:
							blast(pos2, dist);
							blastCount++;
							break;
						case 2:
							blast(pos3, dist);
							blastCount++;
							break;
						case 3:
							blast(pos4, dist);
							blastCount++;
							break;
						case 4:
							blast(pos5, dist);
							blastCount++;
							break;
						case 5:
							blastState = 2;
							blastCount = 0;
							break;
					}
				}
				else if (blastState == 2) {
					revProgress++;
					
					if (revProgress > circum) {
						revProgress = 0;
						radProgress++;
					}
					
					if (radProgress > getScale() * 2D) {
						
						this.setDead();
						blastState = 4;
					}
					blastState = 0;
				}
			}
			iCounter++;
		}
	}
	
	private void blast(MutableBlockPos pos, double dist) {
		
		int depth = 0;
		
		int topBlock = world.getTopSolidOrLiquidBlock(pos).getY();
		
		for (int y = (topBlock - 8); y <= topBlock + 48; y++) {
			pos.setY(y);
			IBlockState b = world.getBlockState(pos);
			// int meta = world.getBlockMetadata(x, y, z);
			
			BlockPos left = new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ());
			BlockPos right = new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ());
			BlockPos up = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
			// BlockPos down = pos.add(0, -1, 0);
			BlockPos down2 = new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());
			BlockPos front = pos.add(0, 0, 1);
			BlockPos back = pos.add(0, 0, -1);
			
			boolean LR = (world.isAirBlock(left) && world.isAirBlock(right));
			// boolean UD = (world.isAirBlock(up) && world.isAirBlock(down));
			boolean FB = (world.isAirBlock(front) && world.isAirBlock(back));
			
			IBlockState bd = world.getBlockState(down2);
			
			if ((b.getMaterial() != Material.AIR || (b.getBlock() instanceof BlockFluidClassic)) && bd.getMaterial() == Material.AIR) {
				if (b.getBlock().getExplosionResistance(null) <= 100) {
					// EntityFallingBlock dislodged = new EntityFallingBlock(world, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, b);
					// world.spawnEntity(dislodged);
					world.setBlockToAir(pos);
					continue;
				}
			}
			
			if (b.getMaterial() != Material.AIR && (LR || FB)) {
				world.setBlockToAir(pos);
				continue;
			}
			
			if (b.getMaterial() == Material.AIR)
				continue;
				
			// if(b.getMaterial() == Material.SNOW || b.getMaterial() == Material.CRAFTED_SNOW || b.getMaterial() == Material.ICE)
			// world.setBlockToAir(pos);
		}
	}
	
	@Override
	protected void entityInit() {
		this.dataManager.register(SCALE, Integer.valueOf(0));
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		setScale(p_70037_1_.getInteger("scale"));
		revProgress = p_70037_1_.getInteger("revProgress");
		radProgress = p_70037_1_.getInteger("radProgress");
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		p_70014_1_.setInteger("scale", getScale());
		p_70014_1_.setInteger("revProgress", revProgress);
		p_70014_1_.setInteger("radProgress", radProgress);
		
	}
	
	public void setScale(int i) {
		
		this.dataManager.set(SCALE, Integer.valueOf(i));
	}
	
	public int getScale() {
		
		int scale = this.dataManager.get(SCALE);
		
		return scale == 0 ? 1 : scale;
	}
}
