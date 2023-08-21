package trinity.util;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import trinity.blocks.ThermonuclearCore;

import java.util.List;
import java.util.Set;

public class ThermonuclearBomb{

public int blastRadius;

public boolean salted;
	
private static Set<Block> lead = null;
//private static Set<Block> uranium = null;
private static Set<Block> uranium238 = null;
private static Set<Block> neptunium = null;
private static Set<Block> plutonium = null;
private static Set<Block> salt = null;;
	
	public static Set<Block> getSaltingBlocks() {
		
        if (salt != null) {
            return salt;
        }
        salt = Sets.newHashSet();
        List<ItemStack> zinc = OreDictionary.getOres("blockZinc");
        for (ItemStack stack : zinc) {
            Item item = stack.getItem();
            if (item instanceof ItemBlock) {
                ItemBlock itemBlock = (ItemBlock) item;
                if (itemBlock.getBlock() != null) {
                    salt.add(itemBlock.getBlock());
                }
            }
        }
        List<ItemStack> cobalt = OreDictionary.getOres("blockCobalt");
        for (ItemStack stack : cobalt) {
            Item item = stack.getItem();
            if (item instanceof ItemBlock) {
                ItemBlock itemBlock = (ItemBlock) item;
                if (itemBlock.getBlock() != null) {
                    salt.add(itemBlock.getBlock());
                }
            }
        }
        List<ItemStack> gold = OreDictionary.getOres("blockGold");
        for (ItemStack stack : gold) {
            Item item = stack.getItem();
            if (item instanceof ItemBlock) {
                ItemBlock itemBlock = (ItemBlock) item;
                if (itemBlock.getBlock() != null) {
                    salt.add(itemBlock.getBlock());
                }
            }
        }
		return null;
	}

	public static Set<Block> getPlutoniumBlocks() {
		
        if (plutonium != null) {
            return plutonium;
        }
        plutonium = Sets.newHashSet();
        List<ItemStack> stacks = OreDictionary.getOres("blockPlutonium242");
        for (ItemStack stack : stacks) {
            Item item = stack.getItem();
            if (item instanceof ItemBlock) {
                ItemBlock itemBlock = (ItemBlock) item;
                if (itemBlock.getBlock() != null) {
                    plutonium.add(itemBlock.getBlock());
                }
            }
        }

        return plutonium;
	}
	
	public static Set<Block> getNeptuniumBlocks() {
		
        if (neptunium != null) {
            return neptunium;
        }
        neptunium = Sets.newHashSet();
        List<ItemStack> stacks = OreDictionary.getOres("blockNeptunium237");
        for (ItemStack stack : stacks) {
            Item item = stack.getItem();
            if (item instanceof ItemBlock) {
                ItemBlock itemBlock = (ItemBlock) item;
                if (itemBlock.getBlock() != null) {
                    neptunium.add(itemBlock.getBlock());
                }
            }
        }

        return neptunium;
	}

	
	public static Set<Block> getUranium238Blocks() {

        if (uranium238 != null) {
            return uranium238;
        }
        uranium238 = Sets.newHashSet();
        List<ItemStack> stacks2 = OreDictionary.getOres("blockUranium238");
        for (ItemStack stack : stacks2) {
            Item item = stack.getItem();
            if (item instanceof ItemBlock) {
                ItemBlock itemBlock = (ItemBlock) item;
                if (itemBlock.getBlock() != null) {
                    uranium238.add(itemBlock.getBlock());
                }
            }
        }
		return uranium238;
	}
	
	public static Set<Block> getLeadBlocks() {
		
        if (lead != null) {
            return lead;
        }
        lead = Sets.newHashSet();
        List<ItemStack> stacks = OreDictionary.getOres("blockLead");
        for (ItemStack stack : stacks) {
            Item item = stack.getItem();
            if (item instanceof ItemBlock) {
                ItemBlock itemBlock = (ItemBlock) item;
                if (itemBlock.getBlock() != null) {
                    lead.add(itemBlock.getBlock());
                }
            }
        }
        return lead;
	}

	public static boolean isSaltingAgent(World world, BlockPos pos)
	{
		Block block = world.getBlockState(pos).getBlock();
		if(getSaltingBlocks()!=null && getSaltingBlocks().contains(block))
		{
			return true;
		}
		return false;
	}
	
	public static boolean isFissionable(World world, BlockPos pos)
	{
		Block block = world.getBlockState(pos).getBlock();

		if((getUranium238Blocks()!=null && getUranium238Blocks().contains(block)))
		{
			return true;
		}
		if((getNeptuniumBlocks()!=null && getNeptuniumBlocks().contains(block)))
		{
			return true;
		}
		if((getPlutoniumBlocks()!=null && getPlutoniumBlocks().contains(block)))
		{
			return true;
		}
		return false;
	}
	
	public static boolean isLead(World world, BlockPos pos)
	{
		Block block = world.getBlockState(pos).getBlock();
		if(getLeadBlocks()!=null && getLeadBlocks().contains(block))
		{
			return true;
		}
		return false;
	}

	public static boolean isValidTamper(World world, BlockPos pos)
	{
		Block block = world.getBlockState(pos).getBlock();
		if(getLeadBlocks()!=null && getLeadBlocks().contains(block))
		{
			return true;
		}

		if(getUranium238Blocks()!=null && getUranium238Blocks().contains(block))
		{
			return true;
		}
		if(getNeptuniumBlocks()!=null && getNeptuniumBlocks().contains(block))
		{
			return true;
		}
		if(getPlutoniumBlocks()!=null && getPlutoniumBlocks().contains(block))
		{
			return true;
		}
		if(getSaltingBlocks()!=null && getSaltingBlocks().contains(block))
		{
			return true;
		}
		return false;
	}
	
	public static double ThermonuclearMultiplier(World world, BlockPos pos, boolean salt)
	{
		int fission1 = 0;
		int inert1 = 0;
		int salted1 = 0;
		
		int fission2 = 0;
		int inert2 = 0;
		int salted2 = 0;
		
		int fission3 = 0;
		int inert3 = 0;
		int salted3 = 0;
		
		int fission4 = 0;
		int inert4 = 0;
		int salted4 = 0;
		
		int fission = 0;
		int inert = 0;
		int salted = 0;
		
		int startpoint = pos.getY()+1;
		int endpoint = pos.getY()+3;

		int startpoint2 = pos.getY()-1;
		int endpoint2 = pos.getY()-3;
		
		int startpoint3 = pos.getX()+1;
		int endpoint3 = pos.getX()+3;
		
		int startpoint4 = pos.getX()-1;
		int endpoint4 = pos.getX()-3;
		
		int startpoint5 = pos.getZ()+1;
		int endpoint5 = pos.getZ()+3;
		
		int startpoint6 = pos.getZ()-1;
		int endpoint6 = pos.getZ()-3;
		
		int depth1 = 0;
		int depth2 = 0;
		int depth3 = 0;
		int depth4 = 0;
		for(int y = startpoint;y<=endpoint;y++)
		{
			BlockPos p1 = new BlockPos(pos.getX(),y,pos.getZ());
			BlockPos p2 = new BlockPos(pos.getX()+1,y,pos.getZ());
			BlockPos p3 = new BlockPos(pos.getX()-1,y,pos.getZ());
			BlockPos p4 = new BlockPos(pos.getX(),y,pos.getZ()+1);
			BlockPos p5 = new BlockPos(pos.getX(),y,pos.getZ()-1);
			IBlockState s1 = world.getBlockState(p1);
			IBlockState s2 = world.getBlockState(p1);
			IBlockState s3 = world.getBlockState(p1);
			IBlockState s4 = world.getBlockState(p1);
			IBlockState s5 = world.getBlockState(p1);
			boolean b1 = s1.getBlock()instanceof ThermonuclearCore && (s1.getValue(ThermonuclearCore.FACING)==EnumFacing.UP || s1.getValue(ThermonuclearCore.FACING)==EnumFacing.DOWN);
			Block block = world.getBlockState(pos).getBlock();
			boolean t1 = isValidTamper(world, p2);
			boolean t2 = isValidTamper(world, p3);
			boolean t3 = isValidTamper(world, p4);
			boolean t4 = isValidTamper(world, p5);

			if(t1)
			{				
				depth1++;
				if(depth1<=3)
				{
					if(isLead(world, p2))
					{
						inert1++;
					}
					if(isFissionable(world, p2))
					{
						fission1++;
					}
					if(isSaltingAgent(world, p2))
					{
						salted1++;
					}
				}
				//continue;
			}
			if(t2)
			{				
				depth2++;
				if(depth2<=3)
				{
					if(isLead(world, p3))
					{
						inert2++;
					}
					if(isFissionable(world, p3))
					{
						fission2++;
					}
					if(isSaltingAgent(world, p3))
					{
						salted2++;
					}
				}
				//continue;
			}
			if(t3)
			{				
				depth3++;
				if(depth3<=3)
				{
					if(isLead(world, p4))
					{
						inert3++;
					}
					if(isFissionable(world, p4))
					{
						fission3++;
					}
					if(isSaltingAgent(world, p4))
					{
						salted3++;
					}
				}
				//continue;
			}
			if(t4)
			{				
				depth4++;
				if(depth4<=3)
				{
					if(isLead(world, p5))
					{
						inert4++;
					}
					if(isFissionable(world, p5))
					{
						fission4++;
					}
					if(isSaltingAgent(world, p5))
					{
						salted4++;
					}
				}
				inert = (inert1+inert2+inert3+inert4);
				fission = (fission1+fission2+fission3+fission4);
				salted = (salted1+salted2+salted3+salted4);
				continue;
			}
			if(!(b1 && t1 && t2 && t3 && t4))
			{
				break;
			}
			return 0;
		}
		for(int y = endpoint2;y<=startpoint2;y++)
		{
			BlockPos p1 = new BlockPos(pos.getX(),y,pos.getZ());
			BlockPos p2 = new BlockPos(pos.getX()+1,y,pos.getZ());
			BlockPos p3 = new BlockPos(pos.getX()-1,y,pos.getZ());
			BlockPos p4 = new BlockPos(pos.getX(),y,pos.getZ()+1);
			BlockPos p5 = new BlockPos(pos.getX(),y,pos.getZ()-1);
			IBlockState s1 = world.getBlockState(p1);
			IBlockState s2 = world.getBlockState(p1);
			IBlockState s3 = world.getBlockState(p1);
			IBlockState s4 = world.getBlockState(p1);
			IBlockState s5 = world.getBlockState(p1);
			boolean b1 = s1.getBlock()instanceof ThermonuclearCore && (s1.getValue(ThermonuclearCore.FACING)==EnumFacing.UP || s1.getValue(ThermonuclearCore.FACING)==EnumFacing.DOWN);
			boolean t1 = isValidTamper(world, p2);
			boolean t2 = isValidTamper(world, p3);
			boolean t3 = isValidTamper(world, p4);
			boolean t4 = isValidTamper(world, p5);
			if(t1)
			{				
				depth1++;
				if(depth1<=3)
				{
					if(isLead(world, p2))
					{
						inert1++;
					}
					if(isFissionable(world, p2))
					{
						fission1++;
					}
					if(isSaltingAgent(world, p2))
					{
						salted1++;
					}
				}
				//continue;
			}
			if(t2)
			{				
				depth2++;
				if(depth2<=3)
				{
					if(isLead(world, p3))
					{
						inert2++;
					}
					if(isFissionable(world, p3))
					{
						fission2++;
					}
					if(isSaltingAgent(world, p3))
					{
						salted2++;
					}
				}
				//continue;
			}
			if(t3)
			{				
				depth3++;
				if(depth3<=3)
				{
					if(isLead(world, p4))
					{
						inert3++;
					}
					if(isFissionable(world, p4))
					{
						fission3++;
					}
					if(isSaltingAgent(world, p4))
					{
						salted3++;
					}
				}
				//continue;
			}
			if(t4)
			{				
				depth4++;
				if(depth4<=3)
				{
					if(isLead(world, p5))
					{
						inert4++;
					}
					if(isFissionable(world, p5))
					{
						fission4++;
					}
					if(isSaltingAgent(world, p5))
					{
						salted4++;
					}
				}
				fission = (fission1+fission2+fission3+fission4);
				inert = (inert1+inert2+inert3+inert4);
				salted = (salted1+salted2+salted3+salted4);
				continue;
			}
			if(!(b1 && t1 && t2 && t3 && t4))
			{
				break;
			}
			return 0;
		}
		
		for(int x = startpoint3;x<=endpoint3;x++)
		{
			BlockPos p1 = new BlockPos(x,pos.getY(),pos.getZ());
			BlockPos p2 = new BlockPos(x,pos.getY()+1,pos.getZ());
			BlockPos p3 = new BlockPos(x,pos.getY()-1,pos.getZ());
			BlockPos p4 = new BlockPos(x,pos.getY(),pos.getZ()+1);
			BlockPos p5 = new BlockPos(x,pos.getY(),pos.getZ()-1);
			IBlockState s1 = world.getBlockState(p1);
			IBlockState s2 = world.getBlockState(p1);
			IBlockState s3 = world.getBlockState(p1);
			IBlockState s4 = world.getBlockState(p1);
			IBlockState s5 = world.getBlockState(p1);
			boolean b1 = s1.getBlock()instanceof ThermonuclearCore && (s1.getValue(ThermonuclearCore.FACING)==EnumFacing.WEST || s1.getValue(ThermonuclearCore.FACING)==EnumFacing.EAST);
			boolean t1 = isValidTamper(world, p2);
			boolean t2 = isValidTamper(world, p3);
			boolean t3 = isValidTamper(world, p4);
			boolean t4 = isValidTamper(world, p5);
			if(t1)
			{				
				depth1++;
				if(depth1<=3)
				{
					if(isLead(world, p2))
					{
						inert1++;
					}
					if(isFissionable(world, p2))
					{
						fission1++;
					}
					if(isSaltingAgent(world, p2))
					{
						salted1++;
					}
				}
				//continue;
			}
			if(t2)
			{				
				depth2++;
				if(depth2<=3)
				{
					if(isLead(world, p3))
					{
						inert2++;
					}
					if(isFissionable(world, p3))
					{
						fission2++;
					}
					if(isSaltingAgent(world, p3))
					{
						salted2++;
					}
				}
				//continue;
			}
			if(t3)
			{				
				depth3++;
				if(depth3<=3)
				{
					if(isLead(world, p4))
					{
						inert3++;
					}
					if(isFissionable(world, p4))
					{
						fission3++;
					}
					if(isSaltingAgent(world, p4))
					{
						salted3++;
					}
				}
				//continue;
			}
			if(t4)
			{				
				depth4++;
				if(depth4<=3)
				{
					if(isLead(world, p5))
					{
						inert4++;
					}
					if(isFissionable(world, p5))
					{
						fission4++;
					}
					if(isSaltingAgent(world, p5))
					{
						salted4++;
					}
				}
				fission = (fission1+fission2+fission3+fission4);
				inert = (inert1+inert2+inert3+inert4);
				salted = (salted1+salted2+salted3+salted4);
				continue;
			}
			if(!(b1 && t1 && t2 && t3 && t4))
			{
				break;
			}
			return 0;
		}
		
		for(int x = endpoint4;x<=startpoint4;x++)
		{
			BlockPos p1 = new BlockPos(x,pos.getY(),pos.getZ());
			BlockPos p2 = new BlockPos(x,pos.getY()+1,pos.getZ());
			BlockPos p3 = new BlockPos(x,pos.getY()-1,pos.getZ());
			BlockPos p4 = new BlockPos(x,pos.getY(),pos.getZ()+1);
			BlockPos p5 = new BlockPos(x,pos.getY(),pos.getZ()-1);
			IBlockState s1 = world.getBlockState(p1);
			IBlockState s2 = world.getBlockState(p1);
			IBlockState s3 = world.getBlockState(p1);
			IBlockState s4 = world.getBlockState(p1);
			IBlockState s5 = world.getBlockState(p1);
			boolean b1 = s1.getBlock()instanceof ThermonuclearCore && (s1.getValue(ThermonuclearCore.FACING)==EnumFacing.WEST || s1.getValue(ThermonuclearCore.FACING)==EnumFacing.EAST);
			boolean t1 = isValidTamper(world, p2);
			boolean t2 = isValidTamper(world, p3);
			boolean t3 = isValidTamper(world, p4);
			boolean t4 = isValidTamper(world, p5);
			if(t1)
			{				
				depth1++;
				if(depth1<=3)
				{
					if(isLead(world, p2))
					{
						inert1++;
					}
					if(isFissionable(world, p2))
					{
						fission1++;
					}
					if(isSaltingAgent(world, p2))
					{
						salted1++;
					}
				}
				//continue;
			}
			if(t2)
			{				
				depth2++;
				if(depth2<=3)
				{
					if(isLead(world, p3))
					{
						inert2++;
					}
					if(isFissionable(world, p3))
					{
						fission2++;
					}
					if(isSaltingAgent(world, p3))
					{
						salted2++;
					}
				}
				//continue;
			}
			if(t3)
			{				
				depth3++;
				if(depth3<=3)
				{
					if(isLead(world, p4))
					{
						inert3++;
					}
					if(isFissionable(world, p4))
					{
						fission3++;
					}
					if(isSaltingAgent(world, p4))
					{
						salted3++;
					}
				}
				//continue;
			}
			if(t4)
			{				
				depth4++;
				if(depth4<=3)
				{
					if(isLead(world, p5))
					{
						inert4++;
					}
					if(isFissionable(world, p5))
					{
						fission4++;
					}
					if(isSaltingAgent(world, p5))
					{
						salted4++;
					}
				}
				fission = (fission1+fission2+fission3+fission4);
				inert = (inert1+inert2+inert3+inert4);
				salted = (salted1+salted2+salted3+salted4);
				continue;
			}
			if(!(b1 && t1 && t2 && t3 && t4))
			{
				break;
			}
			return 0;
		}
		
		for(int z = startpoint5;z<=endpoint5;z++)
		{
			BlockPos p1 = new BlockPos(pos.getX(),pos.getY(),z);
			BlockPos p2 = new BlockPos(pos.getX(),pos.getY()+1,z);
			BlockPos p3 = new BlockPos(pos.getX(),pos.getY()-1,z);
			BlockPos p4 = new BlockPos(pos.getX()+1,pos.getY(),z);
			BlockPos p5 = new BlockPos(pos.getX(),pos.getY(),z);
			IBlockState s1 = world.getBlockState(p1);
			IBlockState s2 = world.getBlockState(p1);
			IBlockState s3 = world.getBlockState(p1);
			IBlockState s4 = world.getBlockState(p1);
			IBlockState s5 = world.getBlockState(p1);
			boolean b1 = s1.getBlock()instanceof ThermonuclearCore && (s1.getValue(ThermonuclearCore.FACING)==EnumFacing.NORTH || s1.getValue(ThermonuclearCore.FACING)==EnumFacing.SOUTH);
			boolean t1 = isValidTamper(world, p2);
			boolean t2 = isValidTamper(world, p3);
			boolean t3 = isValidTamper(world, p4);
			boolean t4 = isValidTamper(world, p5);
			if(t1)
			{				
				depth1++;
				if(depth1<=3)
				{
					if(isLead(world, p2))
					{
						inert1++;
					}
					if(isFissionable(world, p2))
					{
						fission1++;
					}
					if(isSaltingAgent(world, p2))
					{
						salted1++;
					}
				}
				//continue;
			}
			if(t2)
			{				
				depth2++;
				if(depth2<=3)
				{
					if(isLead(world, p3))
					{
						inert2++;
					}
					if(isFissionable(world, p3))
					{
						fission2++;
					}
					if(isSaltingAgent(world, p3))
					{
						salted2++;
					}
				}
				//continue;
			}
			if(t3)
			{				
				depth3++;
				if(depth3<=3)
				{
					if(isLead(world, p4))
					{
						inert3++;
					}
					if(isFissionable(world, p4))
					{
						fission3++;
					}
					if(isSaltingAgent(world, p4))
					{
						salted3++;
					}
				}
				//continue;
			}
			if(t4)
			{				
				depth4++;
				if(depth4<=3)
				{
					if(isLead(world, p5))
					{
						inert4++;
					}
					if(isFissionable(world, p5))
					{
						fission4++;
					}
					if(isSaltingAgent(world, p5))
					{
						salted4++;
					}
				}
				fission = (fission1+fission2+fission3+fission4);
				inert = (inert1+inert2+inert3+inert4);
				salted = (salted1+salted2+salted3+salted4);
				continue;
			}
			if(!(b1 && t1 && t2 && t3 && t4))
			{
				break;
			}
			return 0;
		}
		
		for(int z = endpoint6;z<=startpoint6;z++)
		{
			BlockPos p1 = new BlockPos(pos.getX(),pos.getY(),z);
			BlockPos p2 = new BlockPos(pos.getX(),pos.getY()+1,z);
			BlockPos p3 = new BlockPos(pos.getX(),pos.getY()-1,z);
			BlockPos p4 = new BlockPos(pos.getX()+1,pos.getY(),z);
			BlockPos p5 = new BlockPos(pos.getX(),pos.getY(),z);
			IBlockState s1 = world.getBlockState(p1);
			IBlockState s2 = world.getBlockState(p1);
			IBlockState s3 = world.getBlockState(p1);
			IBlockState s4 = world.getBlockState(p1);
			IBlockState s5 = world.getBlockState(p1);
			boolean b1 = s1.getBlock()instanceof ThermonuclearCore && (s1.getValue(ThermonuclearCore.FACING)==EnumFacing.NORTH || s1.getValue(ThermonuclearCore.FACING)==EnumFacing.SOUTH);
			boolean t1 = isValidTamper(world, p2);
			boolean t2 = isValidTamper(world, p3);
			boolean t3 = isValidTamper(world, p4);
			boolean t4 = isValidTamper(world, p5);
			if(t1)
			{				
				depth1++;
				if(depth1<=3)
				{
					if(isLead(world, p2))
					{
						inert1++;
					}
					if(isFissionable(world, p2))
					{
						fission1++;
					}
					if(isSaltingAgent(world, p2))
					{
						salted1++;
					}
				}
				//continue;
			}
			if(t2)
			{				
				depth2++;
				if(depth2<=3)
				{
					if(isLead(world, p3))
					{
						inert2++;
					}
					if(isFissionable(world, p3))
					{
						fission2++;
					}
					if(isSaltingAgent(world, p3))
					{
						salted2++;
					}
				}
				//continue;
			}
			if(t3)
			{				
				depth3++;
				if(depth3<=3)
				{
					if(isLead(world, p4))
					{
						inert3++;
					}
					if(isFissionable(world, p4))
					{
						fission3++;
					}
					if(isSaltingAgent(world, p4))
					{
						salted3++;
					}
				}
				//continue;
			}
			if(t4)
			{				
				depth4++;
				if(depth4<=3)
				{
					if(isLead(world, p5))
					{
						inert4++;
					}
					if(isFissionable(world, p5))
					{
						fission4++;
					}
					if(isSaltingAgent(world, p5))
					{
						salted4++;
					}
				}
				fission = (fission1+fission2+fission3+fission4);
				inert = (inert1+inert2+inert3+inert4);
				salted = (salted1+salted2+salted3+salted4);
				continue;
			}
			if(!(b1 && t1 && t2 && t3 && t4))
			{
				break;
			}
			return 0;
		}
		double iM;
		double fM;
		double sM;
		
		iM = Math.pow(1.1, inert);
		fM = Math.pow(1.2, fission);
		sM = Math.pow(1.05, salted);
		
		if(inert<1)
		{
			iM=0;
		}
		if(fission<1)
		{
			fM=0;
		}
		if(salted<1)
		{
			sM=0;
		}
		//System.out.println(inert);
		//System.out.println(fission);
		//System.out.println(salted);
		
		//System.out.println(iM);
		//System.out.println(fM);
		//System.out.println(sM);
		double total = (iM+fM+sM);
		//System.out.println(" iM: "+iM+" fM: "+fM+" sM: "+sM+" total: "+total);		
		//System.out.println("Total: "+Math.max(1,total));
		if(salt)
		{
			return salted;
		}
		return Math.max(1, total);
	}
	
	@SuppressWarnings("unused")
	public static boolean ThermonuclearCheck(World world, BlockPos pos)
	{
		//System.out.println("Checking thermonuclear status.");
		int startpoint = pos.getY()+1;
		int endpoint = pos.getY()+3;

		int startpoint2 = pos.getY()-1;
		int endpoint2 = pos.getY()-3;
		
		int startpoint3 = pos.getX()+1;
		int endpoint3 = pos.getX()+3;
		
		int startpoint4 = pos.getX()-1;
		int endpoint4 = pos.getX()-3;
		
		int startpoint5 = pos.getZ()+1;
		int endpoint5 = pos.getZ()+3;
		
		int startpoint6 = pos.getZ()-1;
		int endpoint6 = pos.getZ()-3;
		
		int depth = 0;
		for(int y = startpoint;y<=endpoint;y++)
		{
			BlockPos p1 = new BlockPos(pos.getX(),y,pos.getZ());
			BlockPos p2 = new BlockPos(pos.getX()+1,y,pos.getZ());
			BlockPos p3 = new BlockPos(pos.getX()-1,y,pos.getZ());
			BlockPos p4 = new BlockPos(pos.getX(),y,pos.getZ()+1);
			BlockPos p5 = new BlockPos(pos.getX(),y,pos.getZ()-1);
			IBlockState s1 = world.getBlockState(p1);
			//IBlockState s2 = world.getBlockState(p1);
			//IBlockState s3 = world.getBlockState(p1);
			//IBlockState s4 = world.getBlockState(p1);
			//IBlockState s5 = world.getBlockState(p1);
			boolean b1 = s1.getBlock()instanceof ThermonuclearCore && (s1.getValue(ThermonuclearCore.FACING)==EnumFacing.UP || s1.getValue(ThermonuclearCore.FACING)==EnumFacing.DOWN);
			boolean t1 = isValidTamper(world, p2);
			boolean t2 = isValidTamper(world, p3);
			boolean t3 = isValidTamper(world, p4);
			boolean t4 = isValidTamper(world, p5);
			/*System.out.println("is this a valid T1 tamper? "+t1);
			System.out.println("is this a valid T2 tamper? "+t2);
			System.out.println("is this a valid T3 tamper? "+t3);
			System.out.println("is this a valid T4 tamper? "+t4);
			System.out.println("Checking thermonuclear status 2.");*/
			if(b1 && t1 && t2 && t3 && t4)
			{
				//System.out.println("Checking thermonuclear status 3.");
				depth++;
				if(depth==3)
				{
					return true;
				}
				continue;
			}
			if(!(b1 && t1 && t2 && t3 && t4))
			{
				break;
			}
			return false;
		}
		
		for(int y = endpoint2;y<=startpoint2;y++)
		{
			BlockPos p1 = new BlockPos(pos.getX(),y,pos.getZ());
			BlockPos p2 = new BlockPos(pos.getX()+1,y,pos.getZ());
			BlockPos p3 = new BlockPos(pos.getX()-1,y,pos.getZ());
			BlockPos p4 = new BlockPos(pos.getX(),y,pos.getZ()+1);
			BlockPos p5 = new BlockPos(pos.getX(),y,pos.getZ()-1);
			IBlockState s1 = world.getBlockState(p1);
			IBlockState s2 = world.getBlockState(p1);
			IBlockState s3 = world.getBlockState(p1);
			IBlockState s4 = world.getBlockState(p1);
			IBlockState s5 = world.getBlockState(p1);
			boolean b1 = s1.getBlock()instanceof ThermonuclearCore && (s1.getValue(ThermonuclearCore.FACING)==EnumFacing.UP || s1.getValue(ThermonuclearCore.FACING)==EnumFacing.DOWN);
			boolean t1 = isValidTamper(world, p2);
			boolean t2 = isValidTamper(world, p3);
			boolean t3 = isValidTamper(world, p4);
			boolean t4 = isValidTamper(world, p5);
			if(b1 && t1 && t2 && t3 && t4)
			{
				depth++;
				if(depth==3)
				{
					return true;
				}
				continue;
			}
			if(!(b1 && t1 && t2 && t3 && t4))
			{
				break;
			}
			return false;
		}
		
		for(int x = startpoint3;x<=endpoint3;x++)
		{
			BlockPos p1 = new BlockPos(x,pos.getY(),pos.getZ());
			BlockPos p2 = new BlockPos(x,pos.getY()+1,pos.getZ());
			BlockPos p3 = new BlockPos(x,pos.getY()-1,pos.getZ());
			BlockPos p4 = new BlockPos(x,pos.getY(),pos.getZ()+1);
			BlockPos p5 = new BlockPos(x,pos.getY(),pos.getZ()-1);
			IBlockState s1 = world.getBlockState(p1);
			IBlockState s2 = world.getBlockState(p1);
			IBlockState s3 = world.getBlockState(p1);
			IBlockState s4 = world.getBlockState(p1);
			IBlockState s5 = world.getBlockState(p1);
			boolean b1 = s1.getBlock()instanceof ThermonuclearCore && (s1.getValue(ThermonuclearCore.FACING)==EnumFacing.WEST || s1.getValue(ThermonuclearCore.FACING)==EnumFacing.EAST);
			boolean t1 = isValidTamper(world, p2);
			boolean t2 = isValidTamper(world, p3);
			boolean t3 = isValidTamper(world, p4);
			boolean t4 = isValidTamper(world, p5);
			if(b1 && t1 && t2 && t3 && t4)
			{
				depth++;
				if(depth==3)
				{
					return true;
				}
				continue;
			}
			if(!(b1 && t1 && t2 && t3 && t4))
			{
				break;
			}
			return false;
		}
		
		for(int x = endpoint4;x<=startpoint4;x++)
		{
			BlockPos p1 = new BlockPos(x,pos.getY(),pos.getZ());
			BlockPos p2 = new BlockPos(x,pos.getY()+1,pos.getZ());
			BlockPos p3 = new BlockPos(x,pos.getY()-1,pos.getZ());
			BlockPos p4 = new BlockPos(x,pos.getY(),pos.getZ()+1);
			BlockPos p5 = new BlockPos(x,pos.getY(),pos.getZ()-1);
			IBlockState s1 = world.getBlockState(p1);
			IBlockState s2 = world.getBlockState(p1);
			IBlockState s3 = world.getBlockState(p1);
			IBlockState s4 = world.getBlockState(p1);
			IBlockState s5 = world.getBlockState(p1);
			boolean b1 = s1.getBlock()instanceof ThermonuclearCore && (s1.getValue(ThermonuclearCore.FACING)==EnumFacing.WEST || s1.getValue(ThermonuclearCore.FACING)==EnumFacing.EAST);
			boolean t1 = isValidTamper(world, p2);
			boolean t2 = isValidTamper(world, p3);
			boolean t3 = isValidTamper(world, p4);
			boolean t4 = isValidTamper(world, p5);
			if(b1 && t1 && t2 && t3 && t4)
			{
				depth++;
				if(depth==3)
				{
					return true;
				}
				continue;
			}
			if(!(b1 && t1 && t2 && t3 && t4))
			{
				break;
			}
			return false;
		}
		
		for(int z = startpoint5;z<=endpoint5;z++)
		{
			BlockPos p1 = new BlockPos(pos.getX(),pos.getY(),z);
			BlockPos p2 = new BlockPos(pos.getX(),pos.getY()+1,z);
			BlockPos p3 = new BlockPos(pos.getX(),pos.getY()-1,z);
			BlockPos p4 = new BlockPos(pos.getX()+1,pos.getY(),z);
			BlockPos p5 = new BlockPos(pos.getX(),pos.getY(),z);
			IBlockState s1 = world.getBlockState(p1);
			IBlockState s2 = world.getBlockState(p1);
			IBlockState s3 = world.getBlockState(p1);
			IBlockState s4 = world.getBlockState(p1);
			IBlockState s5 = world.getBlockState(p1);
			boolean b1 = s1.getBlock()instanceof ThermonuclearCore && (s1.getValue(ThermonuclearCore.FACING)==EnumFacing.NORTH || s1.getValue(ThermonuclearCore.FACING)==EnumFacing.SOUTH);
			boolean t1 = isValidTamper(world, p2);
			boolean t2 = isValidTamper(world, p3);
			boolean t3 = isValidTamper(world, p4);
			boolean t4 = isValidTamper(world, p5);
			if(b1 && t1 && t2 && t3 && t4)
			{
				depth++;
				if(depth==3)
				{
					return true;
				}
				continue;
			}
			if(!(b1 && t1 && t2 && t3 && t4))
			{
				break;
			}
			return false;
		}
		
		for(int z = endpoint6;z<=startpoint6;z++)
		{
			BlockPos p1 = new BlockPos(pos.getX(),pos.getY(),z);
			BlockPos p2 = new BlockPos(pos.getX(),pos.getY()+1,z);
			BlockPos p3 = new BlockPos(pos.getX(),pos.getY()-1,z);
			BlockPos p4 = new BlockPos(pos.getX()+1,pos.getY(),z);
			BlockPos p5 = new BlockPos(pos.getX(),pos.getY(),z);
			IBlockState s1 = world.getBlockState(p1);
			IBlockState s2 = world.getBlockState(p1);
			IBlockState s3 = world.getBlockState(p1);
			IBlockState s4 = world.getBlockState(p1);
			IBlockState s5 = world.getBlockState(p1);
			boolean b1 = s1.getBlock()instanceof ThermonuclearCore && (s1.getValue(ThermonuclearCore.FACING)==EnumFacing.NORTH || s1.getValue(ThermonuclearCore.FACING)==EnumFacing.SOUTH);
			boolean t1 = isValidTamper(world, p2);
			boolean t2 = isValidTamper(world, p3);
			boolean t3 = isValidTamper(world, p4);
			boolean t4 = isValidTamper(world, p5);
			if(b1 && t1 && t2 && t3 && t4)
			{
				depth++;
				if(depth==3)
				{
					return true;
				}
				continue;
			}
			if(!(b1 && t1 && t2 && t3 && t4))
			{
				break;
			}
			return false;
		}
		return false;
	}	
}