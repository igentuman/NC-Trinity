package trinity.init;


import org.lwjgl.opengl.GL11;

import nc.init.NCFissionFluids;
import trinity.Global;
import trinity.blocks.BasicBlock;
//import trinity.blocks.BasicBlock2;
import trinity.blocks.ExplosiveCharge;
import trinity.blocks.NuclearCore;
import trinity.render.Tessellator;
//import nca.blocks.MoissaniteBlock;
//import nca.blocks.bunkerblocks.BunkerBlocks;
//import nca.blocks.bunkerblocks.MoissaniteOre;
//import nca.blocks.bunkerblocks.TritiumLamp;
//import nca.blocks.bunkerblocks.TritiumLightFrame;
import trinity.tabs.TrinityTab;
import trinity.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

@Mod.EventBusSubscriber(modid=Reference.MODID)
public class ModDamageSources {
	
	public static final DamageSource NUCLEAR_EXPLOSION = new DamageSource("nuclear_explosion");
}



