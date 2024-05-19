package trinity.world;

import com.blamejared.ctgui.reference.Reference;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

@ObjectHolder(Reference.MOD_ID)
public class TrinityBiomes {
	
	public final static BiomeNuclearCrater NUCLEAR_CRATER = new BiomeNuclearCrater();
	
	public final static BiomeContaminatedOcean CONTAMINATED_OCEAN = new BiomeContaminatedOcean();
	
	@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
	public static class RegistrationHandler {
		
		@SubscribeEvent
		public static void onEvent(final RegistryEvent.Register<Biome> event) {
			final IForgeRegistry<Biome> registry = event.getRegistry();
			registry.register(new BiomeNuclearCrater().setRegistryName(Reference.MOD_ID, BiomeNuclearCrater.BIOME_REGISTRY_NAME));
			registry.register(new BiomeContaminatedOcean().setRegistryName(Reference.MOD_ID, BiomeContaminatedOcean.BIOME_REGISTRY_NAME));
		}
	}
	
	public static void initBiomeManagerAndDictionary() {
		// if (NCConfig.wasteland_biome) {
		// zBiomeManager.addBiome(BiomeType.DESERT, new BiomeEntry(NUCLEAR_WASTELAND, NCConfig.wasteland_biome_weight));
		// BiomeManager.addSpawnBiome(NUCLEAR_WASTELAND);
		// BiomeManager.addStrongholdBiome(NUCLEAR_WASTELAND);
		BiomeDictionary.addTypes(NUCLEAR_CRATER, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.DRY, BiomeDictionary.Type.HOT, BiomeDictionary.Type.SPARSE, BiomeDictionary.Type.WASTELAND);
		BiomeDictionary.addTypes(CONTAMINATED_OCEAN, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.OCEAN);
		// }
	}
}
