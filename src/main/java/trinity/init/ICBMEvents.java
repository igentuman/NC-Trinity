package trinity.init;


import icbm.classic.api.EnumTier;
import icbm.classic.content.blast.BlastEMP;
import icbm.classic.content.blast.threaded.BlastAntimatter;
import icbm.classic.content.blast.threaded.BlastNuclear;
import icbm.classic.content.blocks.explosive.ItemBlockExplosive;
import icbm.classic.content.items.ItemMissile;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import trinity.Trinity;
import trinity.config.TrinityConfig;
import trinity.entities.EntityNuclearCloud;
import trinity.entities.EntityNuclearExplosion;
import trinity.explosion.ExplosionNukeGeneric;
import trinity.util.Reference;

import java.util.List;

import static icbm.classic.api.ExplosiveRefs.EMP;
import static icbm.classic.api.ExplosiveRefs.NUCLEAR;


@Mod.EventBusSubscriber(modid=Reference.MODID)
public class ICBMEvents {
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void addAdditionalTooltips(ItemTooltipEvent event) {
		if(Trinity.ICBMLoaded)
		{
			ItemStack stack = event.getItemStack();
			Item item = stack.getItem();
			if(item instanceof ItemBlockExplosive || item instanceof ItemMissile)
			{
				if(stack.getItemDamage() == NUCLEAR.getRegistryID())
				{
					addNukeTooltip(event.getToolTip(), stack);
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	private static void addNukeTooltip(List<String> tooltip, ItemStack stack) {
		tooltip.add(TextFormatting.RED+"Warning: Trinity installed. ICBM nuclear explosives cause Trinity's explosions instead of ICBM's");
	}

	@SubscribeEvent
	// @SideOnly(Side.SERVER)
	public void explosion(ExplosionEvent.Start event)
	{
		World world = event.getWorld();
		Explosion exp = event.getExplosion();
		Vec3d position = exp.getPosition();

		if(exp instanceof BlastNuclear)
		{
			if(((BlastNuclear)exp).getExplosiveData().getTier()==EnumTier.THREE)
			{
				event.setCanceled(true);
				EntityNuclearCloud entity2 = new EntityNuclearCloud(world, 1000, (Math.min(TrinityConfig.icbm_radius,TrinityConfig.max_radius)*2) * 0.005F);
				entity2.posX = position.x;
				entity2.posY = position.y;
				entity2.posZ = position.z;
				world.spawnEntity(entity2);
				new BlastEMP().setBlastWorld(event.getWorld()).setBlastSource(exp.getExplosivePlacedBy()).setBlastPosition(((BlastNuclear) exp).location.x(), ((BlastNuclear) exp).location.y(), ((BlastNuclear) exp).location.z())
				.setBlastSize(((BlastNuclear) exp).getBlastRadius()*2)
				.setExplosiveData(EMP)
				.buildBlast().runBlast();
				ExplosionNukeGeneric.irradiate(world, (int)position.x, (int)position.y, (int)position.z, Math.min(TrinityConfig.icbm_radius,TrinityConfig.max_radius)*2);
				world.spawnEntity(EntityNuclearExplosion.statFac(world, Math.min(TrinityConfig.icbm_radius,TrinityConfig.max_radius), position.x, position.y, position.z));
			}
		}
		else if(exp instanceof BlastAntimatter)
		{
			if(((BlastNuclear)exp).getExplosiveData().getTier()==EnumTier.THREE)
			{
				event.setCanceled(true);
				EntityNuclearCloud entity2 = new EntityNuclearCloud(world, 1000, (Math.min(TrinityConfig.antimatter_radius,TrinityConfig.max_radius)*2) * 0.005F);
				entity2.posX = position.x;
				entity2.posY = position.y;
				entity2.posZ = position.z;
				world.spawnEntity(entity2);
				ExplosionNukeGeneric.irradiate(world, (int)position.x, (int)position.y, (int)position.z, Math.min(TrinityConfig.antimatter_radius,TrinityConfig.max_radius)*6);
				world.spawnEntity(EntityNuclearExplosion.statFacAntimatter(world, Math.min(TrinityConfig.antimatter_radius,TrinityConfig.max_radius), position.x, position.y, position.z));
				new BlastEMP().setBlastWorld(event.getWorld()).setBlastSource(exp.getExplosivePlacedBy()).setBlastPosition(((BlastNuclear) exp).location.x(), ((BlastNuclear) exp).location.y(), ((BlastNuclear) exp).location.z())
				.setBlastSize(((BlastNuclear) exp).getBlastRadius()*2)
				.setExplosiveData(EMP)
				.buildBlast().runBlast();
			}
		}
	}
}



