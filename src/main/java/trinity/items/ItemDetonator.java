package trinity.items;

import trinity.Reference;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import trinity.blocks.*;

import javax.annotation.Nullable;
import java.util.List;

public class ItemDetonator extends Item {
	
	public ItemDetonator(String nameIn) {
		this.setTranslationKey(Reference.MOD_ID + "." + nameIn);
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, nameIn));
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("Sneak right-click to set position,");
		tooltip.add("right-click to detonate!");
		if (stack.getTagCompound() == null) {
			tooltip.add("No position set!");
		}
		else {
			tooltip.add("Set pos to " + stack.getTagCompound().getInteger("x") + ", " + stack.getTagCompound().getInteger("y") + ", " + stack.getTagCompound().getInteger("z"));
		}
	}
	
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		if (stack.getTagCompound() == null) {
			stack.setTagCompound(new NBTTagCompound());
		}
		// player.sendMessage(new TextComponentString("hitX: "+pos.getX()+"hitY: "+pos.getY()+"hitZ: "+pos.getZ()));
		if (player.isSneaking()) {
			stack.getTagCompound().setInteger("x", pos.getX());
			stack.getTagCompound().setInteger("y", pos.getY());
			stack.getTagCompound().setInteger("z", pos.getZ());
			
			if (world.isRemote) {
				player.sendMessage(new TextComponentString("Position set!"));
			}
			
			// world.playSoundAtEntity(player, "hbm:item.techBoop", 2.0F, 1.0F);
			
			return EnumActionResult.SUCCESS;
		}
		
		return EnumActionResult.FAIL;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if (!player.isSneaking()) {
			if (stack.getTagCompound() == null) {
				if (world.isRemote)
					player.sendMessage(new TextComponentString("Error: Position not set."));
			}
			else {
				int x = stack.getTagCompound().getInteger("x");
				int y = stack.getTagCompound().getInteger("y");
				int z = stack.getTagCompound().getInteger("z");
				BlockPos bomb = new BlockPos(x, y, z);
				if (world.getBlockState(bomb).getBlock() instanceof NuclearCore) {
					// world.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);
					if (!world.isRemote) {
						((NuclearCore) world.getBlockState(bomb).getBlock()).AtomicBomb(world, bomb, ((NuclearCore) world.getBlockState(bomb).getBlock()).blastRadius, ((NuclearCore) world.getBlockState(bomb).getBlock()).salted);
						
						// if(MainRegistry.enableExtendedLogging)
						// MainRegistry.logger.log(Level.INFO, "[DET] Tried to detonate block at " + x + " / " + y + " / " + z + " by " + player.getDisplayName() + "!");
					}
					if (world.isRemote) {
						player.sendMessage(new TextComponentString("Detonated!"));
					}
				}
				else if (world.getBlockState(bomb).getBlock() instanceof ExplosiveCharge) {
					// world.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);
					if (!world.isRemote) {
						((ExplosiveCharge) world.getBlockState(bomb).getBlock()).Explode(world.getBlockState(bomb), world, bomb);
						
						// if(MainRegistry.enableExtendedLogging)
						// MainRegistry.logger.log(Level.INFO, "[DET] Tried to detonate block at " + x + " / " + y + " / " + z + " by " + player.getDisplayName() + "!");
					}
					if (world.isRemote) {
						player.sendMessage(new TextComponentString("Detonated!"));
					}
				}
				else if (world.getBlockState(bomb).getBlock() instanceof AntimatterBomb) {
					// world.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);
					if (!world.isRemote) {
						((AntimatterBomb) world.getBlockState(bomb).getBlock()).AtomicBomb(world, bomb, ((AntimatterBomb) world.getBlockState(bomb).getBlock()).blastRadius);
						
						// if(MainRegistry.enableExtendedLogging)
						// MainRegistry.logger.log(Level.INFO, "[DET] Tried to detonate block at " + x + " / " + y + " / " + z + " by " + player.getDisplayName() + "!");
					}
					if (world.isRemote) {
						player.sendMessage(new TextComponentString("Detonated!"));
					}
				}
				else if (world.getBlockState(bomb).getBlock() instanceof ExoticBomb) {
					// world.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);
					if (!world.isRemote) {
						((ExoticBomb) world.getBlockState(bomb).getBlock()).AtomicBomb(world, bomb, ((ExoticBomb) world.getBlockState(bomb).getBlock()).blastRadius);
						
						// if(MainRegistry.enableExtendedLogging)
						// MainRegistry.logger.log(Level.INFO, "[DET] Tried to detonate block at " + x + " / " + y + " / " + z + " by " + player.getDisplayName() + "!");
					}
					if (world.isRemote) {
						player.sendMessage(new TextComponentString("Detonated!"));
					}
				}
				else if (world.getBlockState(bomb).getBlock() instanceof DirtyBomb) {
					// world.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);
					if (!world.isRemote) {
						((DirtyBomb) world.getBlockState(bomb).getBlock()).explode(world, bomb, true, player);
						
						// if(MainRegistry.enableExtendedLogging)
						// MainRegistry.logger.log(Level.INFO, "[DET] Tried to detonate block at " + x + " / " + y + " / " + z + " by " + player.getDisplayName() + "!");
					}
					if (world.isRemote) {
						player.sendMessage(new TextComponentString("Detonated!"));
					}
					
				}
				else {
					if (world.isRemote) {
						player.sendMessage(new TextComponentString("Error: Target incompatible or too far away."));
					}
				}
			}
		}
		
		return super.onItemRightClick(world, player, hand);
		
	}
	
}
