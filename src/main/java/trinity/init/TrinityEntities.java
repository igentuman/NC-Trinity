package trinity.init;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import trinity.*;
import trinity.entities.*;

public class TrinityEntities {
	
	public static void register() {
		// EntityRegistry.registerModEntity("entity_nuke_mk4", EntityNukeExplosionMK4.class, 89, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, "entity_nuclear_blast"), EntityNuclearExplosion.class, "entity_nuclear_blast", 0, Trinity.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, "entity_nuclear_cloud"), EntityNuclearCloud.class, "entity_nuclear_cloud", 1, Trinity.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, "entity_fallout"), EntityFalloutRain.class, "entity_fallout", 2, Trinity.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, "entity_shockwave"), EntityShockwave.class, "entity_shockwave", 3, Trinity.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, "entity_dirty_bomb"), EntityDirtyBomb.class, "entity_dirty_bomb", 4, Trinity.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, "entity_thermonuclear_blast"), EntityThermonuclearBlast.class, "entity_thermonuclear_blast", 5, Trinity.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, "entity_thermal_blast"), EntityThermalBlast.class, "entity_thermal_blast", 6, Trinity.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, "entity_black_hole"), EntityBlackHole.class, "entity_black_hole", 7, Trinity.instance, 1000, 1, true);
	}
}
