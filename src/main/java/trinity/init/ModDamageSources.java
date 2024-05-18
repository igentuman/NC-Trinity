package trinity.init;

import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.Mod;
import trinity.Reference;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ModDamageSources {
	
	public static final DamageSource NUCLEAR_EXPLOSION = new DamageSource("nuclear_explosion");
}
