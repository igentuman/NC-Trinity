package trinity.init;

import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.Mod;
import trinity.util.Reference;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class ModDamageSources {
	
	public static final DamageSource NUCLEAR_EXPLOSION = new DamageSource("nuclear_explosion");
}
