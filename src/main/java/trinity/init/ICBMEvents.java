package trinity.init;

import icbm.classic.api.EnumTier;
import icbm.classic.api.refs.ICBMExplosives;
import icbm.classic.content.blast.BlastEMP;
import icbm.classic.content.blast.threaded.*;
import icbm.classic.content.blocks.explosive.ItemBlockExplosive;
import icbm.classic.content.items.ItemMissile;
import net.minecraft.item.*;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.*;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.*;
import trinity.*;
import trinity.config.TrinityConfig;
import trinity.entities.*;
import trinity.explosion.ExplosionNukeGeneric;

import java.util.List;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ICBMEvents {
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void addAdditionalTooltips(ItemTooltipEvent event) {
		if (Trinity.ICBMLoaded) {
			ItemStack stack = event.getItemStack();
			Item item = stack.getItem();
			if (item instanceof ItemBlockExplosive || item instanceof ItemMissile) {
				if (stack.getItemDamage() == ICBMExplosives.NUCLEAR.getRegistryID()) {
					addNukeTooltip(event.getToolTip(), stack);
				}
			}
		}
		
	}
	
	@SideOnly(Side.CLIENT)
	private static void addNukeTooltip(List<String> tooltip, ItemStack stack) {
		tooltip.add(TextFormatting.RED + "Warning: Trinity installed. ICBM nuclear explosives cause Trinity's explosions instead of ICBM's");
	}
	
	@SubscribeEvent
	// @SideOnly(Side.SERVER)
	public void explosion(ExplosionEvent.Start event) {
		World world = event.getWorld();
		Explosion exp = event.getExplosion();
		Vec3d position = exp.getPosition();
		
		if (exp instanceof BlastNuclear) {
			if (((BlastNuclear) exp).getExplosiveData().getTier() == EnumTier.THREE) {
				event.setCanceled(true);
				EntityNuclearCloud entity2 = new EntityNuclearCloud(world, 1000, (Math.min(TrinityConfig.icbm_radius, TrinityConfig.max_radius) * 2) * 0.005F);
				entity2.posX = position.x;
				entity2.posY = position.y;
				entity2.posZ = position.z;
				world.spawnEntity(entity2);
				// world.setBlockToAir(pos);
				new BlastEMP().setBlastWorld(event.getWorld()).setBlastSource(exp.getExplosivePlacedBy()).setBlastPosition(((BlastNuclear) exp).location.x(), ((BlastNuclear) exp).location.y(), ((BlastNuclear) exp).location.z()).setBlastSize(((BlastNuclear) exp).getBlastRadius() * 2).setExplosiveData(ICBMExplosives.EMP).buildBlast().runBlast();
				ExplosionNukeGeneric.irradiate(world, (int) position.x, (int) position.y, (int) position.z, Math.min(TrinityConfig.icbm_radius, TrinityConfig.max_radius) * 2);
				world.spawnEntity(EntityNuclearExplosion.statFac(world, Math.min(TrinityConfig.icbm_radius, TrinityConfig.max_radius), position.x, position.y, position.z));
				// System.out.println("If you are seeing this line, this means your blast detector is at least partially working.");
			}
		}
		else if (exp instanceof BlastAntimatter) {
			if (((BlastAntimatter) exp).getExplosiveData().getTier() == EnumTier.FOUR) {
				event.setCanceled(true);
				EntityNuclearCloud entity2 = new EntityNuclearCloud(world, 1000, (Math.min(TrinityConfig.antimatter_radius, TrinityConfig.max_radius) * 2) * 0.005F);
				entity2.posX = position.x;
				entity2.posY = position.y;
				entity2.posZ = position.z;
				world.spawnEntity(entity2);
				// exp.world.setBlockToAir(pos);
				ExplosionNukeGeneric.irradiate(world, (int) position.x, (int) position.y, (int) position.z, Math.min(TrinityConfig.antimatter_radius, TrinityConfig.max_radius) * 6);
				world.spawnEntity(EntityNuclearExplosion.statFacAntimatter(world, Math.min(TrinityConfig.antimatter_radius, TrinityConfig.max_radius), position.x, position.y, position.z));
				new BlastEMP().setBlastWorld(event.getWorld()).setBlastSource(exp.getExplosivePlacedBy()).setBlastPosition(((BlastNuclear) exp).location.x(), ((BlastNuclear) exp).location.y(), ((BlastNuclear) exp).location.z()).setBlastSize(((BlastNuclear) exp).getBlastRadius() * 2).setExplosiveData(ICBMExplosives.EMP).buildBlast().runBlast();
				//System.out.println("If you are seeing this line, this means your blast detector is at least partially working.");
			}
		}
	}
}
