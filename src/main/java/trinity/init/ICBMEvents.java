package trinity.init;

import icbm.classic.api.actions.data.ActionFields;
import icbm.classic.api.refs.ICBMExplosives;
import icbm.classic.content.actions.emp.ActionDataEmpArea;
import icbm.classic.content.blast.threaded.*;
import icbm.classic.content.blocks.explosive.ItemBlockExplosive;
import icbm.classic.content.items.ItemMissile;
import icbm.classic.lib.actions.PotentialActionKnown;
import icbm.classic.lib.actions.fields.ActionFieldProvider;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.*;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.*;
import trinity.*;
import trinity.config.TrinityConfig;
import trinity.entities.*;
import trinity.explosion.ExplosionNukeGeneric;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ICBMEvents {
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void addAdditionalTooltips(ItemTooltipEvent event) {
		if (Trinity.ICBMLoaded) {
			addAdditionalTrinityTooltips(event);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Optional.Method(modid = "icbmclassic")
	public void addAdditionalTrinityTooltips(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();
		Item item = stack.getItem();
		
		if (item instanceof ItemBlockExplosive || item instanceof ItemMissile) {
			int damage = stack.getItemDamage();
			if (damage == ICBMExplosives.NUCLEAR.getRegistryID()) {
				event.getToolTip().add(TextFormatting.RED + "Warning! Trinity installed - ICBM nuclear explosives cause Trinity's explosions instead of ICBM's!");
			}
			else if (damage == ICBMExplosives.ANTIMATTER.getRegistryID()) {
				event.getToolTip().add(TextFormatting.RED + "Warning! Trinity installed - ICBM antimatter explosives cause Trinity's explosions instead of ICBM's!");
			}
		}
	}
	
	@SubscribeEvent
	public void onExplosion(ExplosionEvent.Start event) {
		if (Trinity.ICBMLoaded) {
			onTrinityExplosion(event);
		}
	}
	
	@Optional.Method(modid = "icbmclassic")
	public void onTrinityExplosion(ExplosionEvent.Start event) {
		World world = event.getWorld();
		Explosion explosion = event.getExplosion();
		Vec3d vec3d = explosion.getPosition();
		
		if (explosion instanceof BlastNuclear blastNuclear) {
			event.setCanceled(true);
			EntityNuclearCloud entity = new EntityNuclearCloud(world, 1000, (Math.min(TrinityConfig.icbm_radius, TrinityConfig.max_radius) * 2) * 0.005F);
			entity.posX = vec3d.x;
			entity.posY = vec3d.y;
			entity.posZ = vec3d.z;
			world.spawnEntity(entity);
			BlockPos pos = blastNuclear.getPos();
			// world.setBlockToAir(pos);
			new PotentialActionKnown(ActionDataEmpArea.REG_NAME).withProvider(new ActionFieldProvider().field(ActionFields.AREA_SIZE, () -> blastNuclear.getBlastRadius() * 2)).doAction(world, pos, null);
			ExplosionNukeGeneric.irradiate(world, pos.getX(), pos.getY(), pos.getZ(), Math.min(TrinityConfig.icbm_radius, TrinityConfig.max_radius) * 2);
			world.spawnEntity(EntityNuclearExplosion.statFac(world, Math.min(TrinityConfig.icbm_radius, TrinityConfig.max_radius), vec3d.x, vec3d.y, vec3d.z));
			// System.out.println("If you are seeing this line, this means your blast detector is at least partially working.");
		}
		else if (explosion instanceof BlastAntimatter blastAntimatter) {
			event.setCanceled(true);
			EntityNuclearCloud entity = new EntityNuclearCloud(world, 1000, (Math.min(TrinityConfig.antimatter_radius, TrinityConfig.max_radius) * 2) * 0.005F);
			entity.posX = vec3d.x;
			entity.posY = vec3d.y;
			entity.posZ = vec3d.z;
			world.spawnEntity(entity);
			BlockPos pos = blastAntimatter.getPos();
			// world.setBlockToAir(pos);
			new PotentialActionKnown(ActionDataEmpArea.REG_NAME).withProvider(new ActionFieldProvider().field(ActionFields.AREA_SIZE, () -> blastAntimatter.getBlastRadius() * 2)).doAction(world, pos, null);
			ExplosionNukeGeneric.irradiate(world, pos.getX(), pos.getY(), pos.getZ(), Math.min(TrinityConfig.antimatter_radius, TrinityConfig.max_radius) * 6);
			world.spawnEntity(EntityNuclearExplosion.statFacAntimatter(world, Math.min(TrinityConfig.antimatter_radius, TrinityConfig.max_radius), vec3d.x, vec3d.y, vec3d.z));
			// System.out.println("If you are seeing this line, this means your blast detector is at least partially working.");
		}
	}
}
