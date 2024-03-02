package trinity.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import nc.util.Lang;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import trinity.util.Reference;

public class TrinityConfig {
	
	private static Configuration config = null;
	
	private static double salted = 50;
	
	private static int u233 = 130 / 2;
	private static int u235 = 108 / 2;
	private static int np237 = 81 / 2;
	private static int pu239 = 140 / 2;
	private static int am242 = 173 / 2;
	private static int cm247 = 124 / 2;
	private static int bk248 = 122 / 2;
	private static int cf249 = 194 / 2;
	private static int cf251 = 203 / 2;
	private static int cust1 = 100 / 2;
	private static int cust2 = 100 / 2;
	private static int cust3 = 100 / 2;
	private static int cust4 = 100 / 2;
	private static int icbm = 100 / 2;
	private static int anti = 50 / 2;
	private static int max = 1000;
	private static int speed = 512;
	// private static int cap = 300;
	private static float multiplier = 5;
	private static boolean render = true;
	
	private static boolean thermo = true;
	
	private static boolean custom = false;
	
	private static boolean lava = true;
	
	public static final String CATEGORY_NAME_OTHER = "Nuclear Weapons";
	
	public static double salted_burst;
	
	public static int u233_radius;
	
	public static int u235_radius;
	
	public static int np237_radius;
	
	public static int pu239_radius;
	
	public static int am242_radius;
	
	public static int cm247_radius;
	
	public static int bk248_radius;
	
	public static int cf249_radius;
	
	public static int cf251_radius;
	
	public static int custom_1_radius;
	
	public static int custom_2_radius;
	
	public static int custom_3_radius;
	
	public static int custom_4_radius;
	
	public static int antimatter_radius;
	
	public static int max_radius;
	
	public static int icbm_radius;
	
	public static int fallout_speed;
	
	public static double fallout_multiplier;
	
	public static boolean fallout_rendering;
	
	public static boolean thermonuclear;
	
	public static boolean custom_nukes;
	
	public static boolean lava_gen;
	
	// public static int capacity;
	
	public static void preInit() {
		File configFile = new File(Loader.instance().getConfigDir(), "trinity.cfg");
		config = new Configuration(configFile);
		syncFromFiles();
		
	}
	
	public static Configuration getConfig() {
		return config;
		
	}
	
	public static void clientPreInit() {
		MinecraftForge.EVENT_BUS.register(new ConfigEventHandler());
		
	}
	
	public static void syncFromFiles() {
		syncConfig(true, true);
		
	}
	
	public static void syncFromGui() {
		syncConfig(false, true);
		
	}
	
	public static void syncFromFields() {
		syncConfig(false, false);
		
	}
	
	private static void syncConfig(boolean loadFromFile, boolean setFromConfig) {
		if (loadFromFile)
			config.load();
		
		Property propertyCustom = config.get(CATEGORY_NAME_OTHER, "custom_nukes", new Boolean(custom), Lang.localise("gui.config.nuke.custom_nukes.comment"));
		propertyCustom.setLanguageKey("gui.config.nuke.custom_nukes");
		
		Property propertyThermonuclear = config.get(CATEGORY_NAME_OTHER, "thermonuclear", new Boolean(thermo), Lang.localise("gui.config.nuke.thermonuclear.comment"));
		propertyThermonuclear.setLanguageKey("gui.config.nuke.thermonuclear");
		
		Property propertyFalloutRender = config.get(CATEGORY_NAME_OTHER, "fallout_rendering", new Boolean(render), Lang.localise("gui.config.fallout.fallout_rendering.comment"));
		propertyFalloutRender.setLanguageKey("gui.config.fallout.fallout_rendering");
		
		Property propertySaltedBurst = config.get(CATEGORY_NAME_OTHER, "salted_burst", new Double(salted), Lang.localise("gui.config.fallout.salted_burst.comment"), 1, Double.MAX_VALUE);
		propertySaltedBurst.setLanguageKey("gui.config.fallout.salted_burst");
		
		Property propertyFalloutRadius = config.get(CATEGORY_NAME_OTHER, "fallout_multiplier", new Double(multiplier), Lang.localise("gui.config.fallout.fallout_multiplier.comment"), 1, Double.MAX_VALUE);
		propertyFalloutRadius.setLanguageKey("gui.config.fallout.fallout_multiplier");
		
		Property propertyU233Radius = config.get(CATEGORY_NAME_OTHER, "u233_radius", new Integer(u233), Lang.localise("gui.config.nuke.u233_radius.comment"), 1, Integer.MAX_VALUE);
		propertyU233Radius.setLanguageKey("gui.config.nuke.u233_radius");
		
		Property propertyU235Radius = config.get(CATEGORY_NAME_OTHER, "u235_radius", new Integer(u235), Lang.localise("gui.config.nuke.u235_radius.comment"), 1, Integer.MAX_VALUE);
		propertyU233Radius.setLanguageKey("gui.config.nuke.u235_radius");
		
		Property propertyNp237Radius = config.get(CATEGORY_NAME_OTHER, "np237_radius", new Integer(np237), Lang.localise("gui.config.nuke.np237_radius.comment"), 1, Integer.MAX_VALUE);
		propertyNp237Radius.setLanguageKey("gui.config.nuke.np237_radius");
		
		Property propertyPu239Radius = config.get(CATEGORY_NAME_OTHER, "pu239_radius", new Integer(pu239), Lang.localise("gui.config.nuke.pu239_radius.comment"), 1, Integer.MAX_VALUE);
		propertyPu239Radius.setLanguageKey("gui.config.nuke.pu239_radius");
		
		Property propertyAm242Radius = config.get(CATEGORY_NAME_OTHER, "am242_radius", new Integer(am242), Lang.localise("gui.config.nuke.am242_radius.comment"), 1, Integer.MAX_VALUE);
		propertyPu239Radius.setLanguageKey("gui.config.nuke.am242_radius");
		
		Property propertyCm247Radius = config.get(CATEGORY_NAME_OTHER, "cm247_radius", new Integer(cm247), Lang.localise("gui.config.nuke.cm247_radius.comment"), 1, Integer.MAX_VALUE);
		propertyCm247Radius.setLanguageKey("gui.config.nuke.cm247_radius");
		
		Property propertyBk248Radius = config.get(CATEGORY_NAME_OTHER, "bk248_radius", new Integer(bk248), Lang.localise("gui.config.nuke.bk248_radius.comment"), 1, Integer.MAX_VALUE);
		propertyBk248Radius.setLanguageKey("gui.config.nuke.bk248_radius");
		
		Property propertyCf249Radius = config.get(CATEGORY_NAME_OTHER, "cf249_radius", new Integer(cf249), Lang.localise("gui.config.nuke.cf249_radius.comment"), 1, Integer.MAX_VALUE);
		propertyCf249Radius.setLanguageKey("gui.config.nuke.cf249_radius");
		
		Property propertyCf251Radius = config.get(CATEGORY_NAME_OTHER, "cf251_radius", new Integer(cf251), Lang.localise("gui.config.nuke.cf251_radius.comment"), 1, Integer.MAX_VALUE);
		propertyCf251Radius.setLanguageKey("gui.config.nuke.cf251_radius");
		
		Property propertyCustom1Radius = config.get(CATEGORY_NAME_OTHER, "custom1_radius", new Integer(cust1), Lang.localise("gui.config.nuke.cust1_radius.comment"), 1, Integer.MAX_VALUE);
		propertyCustom1Radius.setLanguageKey("gui.config.nuke.cust1_radius");
		
		Property propertyCustom2Radius = config.get(CATEGORY_NAME_OTHER, "custom2_radius", new Integer(cust2), Lang.localise("gui.config.nuke.cust2_radius.comment"), 1, Integer.MAX_VALUE);
		propertyCustom2Radius.setLanguageKey("gui.config.nuke.cust2_radius");
		
		Property propertyCustom3Radius = config.get(CATEGORY_NAME_OTHER, "custom3_radius", new Integer(cust3), Lang.localise("gui.config.nuke.cust3_radius.comment"), 1, Integer.MAX_VALUE);
		propertyCustom3Radius.setLanguageKey("gui.config.nuke.cust3_radius");
		
		Property propertyCustom4Radius = config.get(CATEGORY_NAME_OTHER, "custom4_radius", new Integer(cust4), Lang.localise("gui.config.nuke.cust4_radius.comment"), 1, Integer.MAX_VALUE);
		propertyCustom4Radius.setLanguageKey("gui.config.nuke.cust4_radius");
		
		Property propertyICBMRadius = config.get(CATEGORY_NAME_OTHER, "icbm_radius", new Integer(icbm), Lang.localise("gui.config.nuke.icbm_radius.comment"), 1, Integer.MAX_VALUE);
		propertyICBMRadius.setLanguageKey("gui.config.nuke.icbm_radius");
		
		Property propertyAntimatterRadius = config.get(CATEGORY_NAME_OTHER, "antimatter_radius", new Integer(anti), Lang.localise("gui.config.nuke.antimatter_radius.comment"), 1, Integer.MAX_VALUE);
		propertyAntimatterRadius.setLanguageKey("gui.config.nuke.antimatter_radius");
		
		Property propertyMaxRadius = config.get(CATEGORY_NAME_OTHER, "max_radius", new Integer(max), Lang.localise("gui.config.nuke.max_radius.comment"), 1, Integer.MAX_VALUE);
		propertyU233Radius.setLanguageKey("gui.config.nuke.max_radius");
		
		Property propertySpeed = config.get(CATEGORY_NAME_OTHER, "speed", new Integer(speed), Lang.localise("gui.config.nuke.speed.comment"), 1, Integer.MAX_VALUE);
		propertyU233Radius.setLanguageKey("gui.config.nuke.speed");
		
		Property propertyLava = config.get(CATEGORY_NAME_OTHER, "lava_gen", new Boolean(lava), Lang.localise("gui.config.fallout.lava_gen.comment"));
		propertyThermonuclear.setLanguageKey("gui.config.fallout.lava_gen");
		
		// Property propertyCapacity = config.get(CATEGORY_NAME_OTHER, "capacity", new Integer(cap), Lang.localise("gui.config.nuke.capacity.comment"), 1, Integer.MAX_VALUE);
		// propertyU233Radius.setLanguageKey("gui.config.nuke.capacity");
		
		List<String> propertyOrderOther = new ArrayList<String>();
		config.setCategoryPropertyOrder(CATEGORY_NAME_OTHER, propertyOrderOther);
		
		if (setFromConfig) {
			custom_nukes = propertyCustom.getBoolean();
			lava_gen = propertyLava.getBoolean();
			thermonuclear = propertyThermonuclear.getBoolean();
			fallout_rendering = propertyFalloutRender.getBoolean();
			fallout_multiplier = propertyFalloutRadius.getDouble();
			salted_burst = propertySaltedBurst.getDouble();
			u233_radius = propertyU233Radius.getInt();
			u235_radius = propertyU235Radius.getInt();
			np237_radius = propertyNp237Radius.getInt();
			pu239_radius = propertyPu239Radius.getInt();
			am242_radius = propertyAm242Radius.getInt();
			cm247_radius = propertyCm247Radius.getInt();
			bk248_radius = propertyBk248Radius.getInt();
			cf249_radius = propertyCf249Radius.getInt();
			cf251_radius = propertyCf251Radius.getInt();
			custom_1_radius = propertyCustom1Radius.getInt();
			custom_2_radius = propertyCustom2Radius.getInt();
			custom_3_radius = propertyCustom3Radius.getInt();
			custom_4_radius = propertyCustom4Radius.getInt();
			icbm_radius = propertyICBMRadius.getInt();
			antimatter_radius = propertyAntimatterRadius.getInt();
			max_radius = propertyMaxRadius.getInt();
			fallout_speed = propertySpeed.getInt();
			// capacity = propertyCapacity.getInt();
		}
		propertyCustom.set(custom_nukes);
		propertyLava.set(lava_gen);
		propertyThermonuclear.set(thermonuclear);
		propertyFalloutRender.set(fallout_rendering);
		propertySaltedBurst.set(salted_burst);
		propertyFalloutRadius.set(fallout_multiplier);
		propertyU233Radius.set(u233_radius);
		propertyU235Radius.set(u235_radius);
		propertyNp237Radius.set(np237_radius);
		propertyPu239Radius.set(pu239_radius);
		propertyAm242Radius.set(am242_radius);
		propertyCm247Radius.set(cm247_radius);
		propertyBk248Radius.set(bk248_radius);
		propertyCf249Radius.set(cf249_radius);
		propertyCf251Radius.set(cf251_radius);
		propertyCustom1Radius.set(custom_1_radius);
		propertyCustom2Radius.set(custom_2_radius);
		propertyCustom3Radius.set(custom_3_radius);
		propertyCustom4Radius.set(custom_4_radius);
		propertyICBMRadius.set(icbm_radius);
		propertyAntimatterRadius.set(antimatter_radius);
		propertyMaxRadius.set(max_radius);
		propertySpeed.set(fallout_speed);
		// propertyCapacity.set(capacity);
		
		if (config.hasChanged())
			config.save();
		
	}
	
	private static double[] readDoubleArrayFromConfig(Property property) {
		int currentLength = property.getDoubleList().length;
		int defaultLength = property.getDefaults().length;
		if (currentLength == defaultLength) {
			return property.getDoubleList();
		}
		double[] newArray = new double[defaultLength];
		if (currentLength > defaultLength) {
			for (int i = 0; i < defaultLength; i++) {
				newArray[i] = property.getDoubleList()[i];
			}
		}
		else {
			for (int i = 0; i < currentLength; i++) {
				newArray[i] = property.getDoubleList()[i];
			}
			for (int i = currentLength; i < defaultLength; i++) {
				newArray[i] = property.setToDefault().getDoubleList()[i];
			}
		}
		return newArray;
	}
	
	public static class ConfigEventHandler {
		
		@SubscribeEvent(priority = EventPriority.LOWEST)
		public void onEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(Reference.MODID)) {
				syncFromGui();
				
			}
		}
	}
}
