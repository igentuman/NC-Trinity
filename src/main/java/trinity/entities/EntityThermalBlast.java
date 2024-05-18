package trinity.entities;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.*;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.*;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.relauncher.*;
import trinity.handler.*;

public class EntityThermalBlast extends Entity implements INuclearEffect {
	
    private static final DataParameter<Integer> SCALE = EntityDataManager.<Integer>createKey(EntityThermalBlast.class, DataSerializers.VARINT);
    
	public int revProgress;
	public int radProgress;
	private boolean salted;
	private boolean thermonuclear;

	public EntityThermalBlast(World p_i1582_1_) {
		super(p_i1582_1_);
		this.setSize(4, 20);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
	}
	
	public EntityThermalBlast(World p_i1582_1_, boolean salt) {
		super(p_i1582_1_);
		this.setSize(4, 20);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
		this.salted = salt;
	}

	public EntityThermalBlast(World p_i1582_1_, int maxAge) {
		super(p_i1582_1_);
		this.setSize(4, 20);
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
	        	//System.out.println("This is a thermal blast");
	        	contaminate(pos, dist);
	        	
	        	revProgress++;
	        	
	        	if(revProgress > circum) {
	        		revProgress = 0;
	        		radProgress++;
	        	}
	        	
	        	if(radProgress > getScale() * 2D) {
	        		
	        		this.setDead();
	        	}
        	}
        }
    }
    
    private void contaminate(MutableBlockPos pos, double dist) {
    	
    	int depth = 0;
    	
    	//int mult = 1;
    	
    	int topBlock =world.getTopSolidOrLiquidBlock(pos).getY();
    	
    	for(int y = topBlock+35; y >= (topBlock-10); y--) {
    		pos.setY(y);
    		IBlockState b =  world.getBlockState(pos);
    		//int meta = world.getBlockMetadata(x, y, z);
    		if(b.getMaterial() == Material.AIR)
    			continue;

    		if(b.getMaterial() == Material.SNOW || b.getMaterial() == Material.CRAFTED_SNOW || b.getMaterial() == Material.ICE)
    			world.setBlockToAir(pos);
    		
    		if(b.getBlock().isFlammable(world, pos, EnumFacing.UP) && !(b.getMaterial()==Material.LEAVES) ) {
    			if(dist<65)
    			{
    				if(rand.nextInt(10) == 0)
    					world.setBlockState(pos.add(0, 1, 0), Blocks.FIRE.getDefaultState());
    				else
    					world.setBlockToAir(pos);
    			}
    			if(dist>65 && dist<70)
    				world.setBlockToAir(pos.add(0, 1, 0));
    		}
    		
			if (b.getMaterial()==Material.LEAVES || b.getBlock() instanceof BlockBush) {
				world.setBlockToAir(pos);
			} else if(b.getMaterial() == Material.GRASS) {
				BlockPos up = pos.add(0, 1, 0);
				if(world.getBlockState(up).getMaterial()==Material.PLANTS)
				{
					world.setBlockToAir(up);
				}
    			world.setBlockState(pos, Blocks.DIRT.getDefaultState());
    			return;
    			
    		} else if(b.getBlock() == Blocks.MYCELIUM) {
    			world.setBlockState(pos, Blocks.DIRT.getDefaultState());
    			return;
    		} else if(b.getMaterial() == Material.GROUND) {
    			depth++;
    			BlockPos up = pos.add(0, 1, 0);
    				world.setBlockState(pos, Blocks.DIRT.getDefaultState());
    			if(depth < 2)
    				if(world.getBlockState(up).getMaterial()==Material.PLANTS)
    				{
    					world.setBlockToAir(up);
    				}
    			return;
    		}
    		/*} else if(b.getBlock() == Blocks.SAND) {
    			if(dist<30)
    			{
    				BlockPos up = new BlockPos(pos.getX(),pos.getY()+1,pos.getZ());
    				if (world.isAirBlock(up))
    				{
    					world.setBlockState(pos, ModBlocks.trinitite.getDefaultState());
    					return;
    				}
    				return;
    			}
    		}*/

			else if (b.getBlock() == Blocks.CLAY && dist<65) {
				world.setBlockState(pos, Blocks.HARDENED_CLAY.getDefaultState());
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
				//world.setBlockState(pos, ModBlocks.radioactive_earth.getDefaultState());
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
			
			else if (b.getMaterial()==Material.WOOD) {
				if(dist<65)
				{
    				if(rand.nextInt(10) == 0)
    					world.setBlockState(pos, Blocks.FIRE.getDefaultState());
    				else
    					world.setBlockToAir(pos);
					//world.setBlockState(pos, Blocks.FIRE.getDefaultState());
    				contaminate(pos, dist);
					return;
				}
				if(dist>65 && dist<70)
				{
					world.setBlockToAir(pos);
					return;
				}
				continue;
			}

			else if (b.getMaterial()==Material.CACTUS && dist<65) {
				world.setBlockState(pos, Blocks.FIRE.getDefaultState());
				continue;
			}

			else if (b.getMaterial()==Material.GOURD) {
				if(dist<65)
				{
					world.setBlockState(pos, Blocks.FIRE.getDefaultState());
					continue;
				}
				else
				{
					world.setBlockToAir(pos);
				}
			}
			
			else if (b.getBlock() instanceof BlockCactus && dist<65) {
				world.setBlockState(pos, Blocks.FIRE.getDefaultState());
				continue;
			}
			
			else if (b.getBlock() == Blocks.BROWN_MUSHROOM_BLOCK || b.getBlock() == Blocks.RED_MUSHROOM_BLOCK) {
				world.setBlockToAir(pos);
				continue;
			}
			
			else if(b.getBlock().isNormalCube(world.getBlockState(pos))) {

				return;
			}
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
