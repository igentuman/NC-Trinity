package trinity.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.*;
import trinity.tiles.TileEntityShieldedContainer;

import java.awt.*;

/** User: brandon3055 Date: 06/01/2015
 *
 * GuiInventoryBasic is a simple gui that does nothing but draw a background image and a line of text on the screen everything else is handled by the vanilla container code */
@SideOnly(Side.CLIENT)
public class GuiShieldedContainer extends GuiContainer {
	
	// This is the resource location for the background image for the GUI
	private static final ResourceLocation texture = new ResourceLocation("trinity", "textures/gui/nuclear_pig.png");
	private TileEntityShieldedContainer tileEntityInventoryBasic;
	
	public GuiShieldedContainer(InventoryPlayer invPlayer, TileEntityShieldedContainer tile) {
		super(new ContainerBasic(invPlayer, tile));
		tileEntityInventoryBasic = tile;
		// Set the width and height of the gui. Should match the size of the texture!
		xSize = 176;
		ySize = 169;
	}
	
	// draw the background for the GUI - rendered first
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int x, int y) {
		// Bind the image texture of our custom container
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		// Draw the image
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
	// draw the foreground for the GUI - rendered after the slots, but before the dragged items and tooltips
	// renders relative to the top left corner of the background
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		final int LABEL_XPOS = 5;
		final int LABEL_YPOS = 5;
		fontRenderer.drawString(tileEntityInventoryBasic.getDisplayName().getUnformattedText(), LABEL_XPOS, LABEL_YPOS, Color.darkGray.getRGB());
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
}
