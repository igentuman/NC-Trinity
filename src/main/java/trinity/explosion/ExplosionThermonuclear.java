package trinity.explosion;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

import nc.capability.radiation.entity.IEntityRads;
import nc.config.NCConfig;
import nc.radiation.RadiationHelper;
import nc.util.DamageSources;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import trinity.handler.Vec3;
import trinity.init.ModDamageSources;

public class ExplosionThermonuclear {

	private final static Random random = new Random();

	public int posX;
	public int posY;
	public int posZ;
	public int lastposX = 0;
	public int lastposZ = 0;
	public int radius;
	public int radius2;
	public World worldObj;
	private int n = 1;
	private int nlimit;
	private int shell;
	private int leg;
	private int element;
	
	public void saveToNbt(NBTTagCompound nbt, String name) {
		nbt.setInteger(name + "posX", posX);
		nbt.setInteger(name + "posY", posY);
		nbt.setInteger(name + "posZ", posZ);
		nbt.setInteger(name + "lastposX", lastposX);
		nbt.setInteger(name + "lastposZ", lastposZ);
		nbt.setInteger(name + "radius", radius);
		nbt.setInteger(name + "radius2", radius2);
		nbt.setInteger(name + "n", n);
		nbt.setInteger(name + "nlimit", nlimit);
		nbt.setInteger(name + "shell", shell);
		nbt.setInteger(name + "leg", leg);
		nbt.setInteger(name + "element", element);
	}
	
	public void readFromNbt(NBTTagCompound nbt, String name) {
		posX = nbt.getInteger(name + "posX");
		posY = nbt.getInteger(name + "posY");
		posZ = nbt.getInteger(name + "posZ");
		lastposX = nbt.getInteger(name + "lastposX");
		lastposZ = nbt.getInteger(name + "lastposZ");
		radius = nbt.getInteger(name + "radius");
		radius2 = nbt.getInteger(name + "radius2");
		n = nbt.getInteger(name + "n");
		nlimit = nbt.getInteger(name + "nlimit");
		shell = nbt.getInteger(name + "shell");
		leg = nbt.getInteger(name + "leg");
		element = nbt.getInteger(name + "element");
	}

	
	public ExplosionThermonuclear(int x, int y, int z, World world, int rad)
	{
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		
		this.worldObj = world;
		
		this.radius = rad;
		this.radius2 = this.radius * this.radius;

		this.nlimit = this.radius2 * 4;
	}
	
	public boolean update()
	{
		breakColumn(this.lastposX, this.lastposZ);
		this.shell = (int) Math.floor((Math.sqrt(n) + 1) / 2);
		int shell2 = this.shell * 2;
		this.leg = (int) Math.floor((this.n - (shell2 - 1) * (shell2 - 1)) / shell2);
		this.element = (this.n - (shell2 - 1) * (shell2 - 1)) - shell2 * this.leg - this.shell + 1;
		this.lastposX = this.leg == 0 ? this.shell : this.leg == 1 ? -this.element : this.leg == 2 ? -this.shell : this.element;
		this.lastposZ = this.leg == 0 ? this.element : this.leg == 1 ? this.shell : this.leg == 2 ? -this.element : -this.shell;
		this.n++;
		return this.n > this.nlimit;
	}

	private void breakColumn(int x, int z)
	{
		int dist = (int) (radius - Math.sqrt(x * x + z * z));
		
		if (dist > 0) {
			MutableBlockPos pos = new MutableBlockPos();
			int pX = posX + x;
			int pZ = posZ + z;
			
			int y  = worldObj.getHeight(pX, pZ);
			pos.setPos(pX, y, pZ);
			int maxdepth = (int) (10 + radius * 0.0625);
			int depth = (int) ((maxdepth * dist / radius) + (Math.sin(dist * 0.15 + 2) * 2));//
			
			depth = Math.max(y - depth, 0);
			
			while(y > depth) {

				
				worldObj.setBlockToAir(pos);
				
				y--;
			}
			
			/*if(worldObj.rand.nextInt(10) == 0) {
				worldObj.setBlock(pX, depth + 1, pZ, ModBlocks.balefire);
				
				if(worldObj.getBlock(pX, y, pZ) == ModBlocks.block_schrabidium_cluster)
					worldObj.setBlock(pX, y, pZ, ModBlocks.block_euphemium_cluster, worldObj.getBlockMetadata(pX, y, pZ), 3);
			}

			for(int i = depth; i > depth - 5; i--) {
				if(worldObj.getBlock(pX, i, pZ) == Blocks.stone)
					worldObj.setBlock(pX, i, pZ, ModBlocks.sellafield_slaked);
			}*/
		}
	}



}
