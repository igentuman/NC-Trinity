package trinity.render.entity;

import trinity.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.lwjgl.opengl.GL11;
import trinity.entities.EntityBlackHole;
import trinity.render.*;

public class RenderBlackHole extends Render<EntityBlackHole> {
	
	public static final IRenderFactory<EntityBlackHole> FACTORY = (RenderManager man) -> {
		return new RenderBlackHole(man);
	};
	
	private static final ResourceLocation objTesterModelRL = new ResourceLocation(/*"/assets/" + */Reference.MOD_ID, "models/Sphere.obj");
	private IModelCustom blastModel;
	private ResourceLocation hole;
	private ResourceLocation disk;
	
	public RenderBlackHole(RenderManager renderManager) {
		super(renderManager);
		blastModel = AdvancedModelLoader.loadModel(objTesterModelRL);
		hole = new ResourceLocation(Reference.MOD_ID, "textures/models/black.png");
		disk = new ResourceLocation(Reference.MOD_ID, "textures/models/disk.png");
	}
	
	@Override
	public void doRender(EntityBlackHole entity, double x, double y, double z, float entityYaw, float partialTicks) {
		
		EntityPlayer player = Minecraft.getMinecraft().player;
		long time = Minecraft.getMinecraft().world.getTotalWorldTime();
		float speed = 4;
		// double resonateSpeed = 0.2;
		double radius = entity.getDataManager().get(EntityBlackHole.SCALE);
		
		Tessellator tessellator = Tessellator.instance;
		
		// radius = radius;// * Math.sin(time * resonateSpeed) * 0.1 + radius * 0.9;
		
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		
		GL11.glTranslated(x, y, z);
		GL11.glScaled(radius, radius, radius);
		
		GL11.glDisable(GL11.GL_CULL_FACE);
		bindTexture(hole);
		GL11.glColor4d(0, 0, 0, 1);
		blastModel.renderAll();
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
		
		// =======================================================
		// Draw Vortex
		GL11.glPushMatrix();
		GL11.glDepthMask(false);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LIGHTING);
		
		GL11.glTranslated(x, y, z);
		GL11.glRotatef(-time, 0, 1, 0);
		
		double size = radius * 6;
		
		this.bindTexture(disk);
		
		// top render
		tessellator.startDrawingQuads();
		tessellator.setBrightness(240);
		tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1F);
		tessellator.addVertexWithUV(-size, 0, -size, 0, 0);
		tessellator.addVertexWithUV(-size, 0, +size, 0, 1);
		tessellator.addVertexWithUV(+size, 0, +size, 1, 1);
		tessellator.addVertexWithUV(+size, 0, -size, 1, 0);
		tessellator.draw();
		
		// bottom render
		GL11.glRotatef(180, 1, 0, 0);
		tessellator.startDrawingQuads();
		tessellator.setBrightness(240);
		tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1F);
		tessellator.addVertexWithUV(-size, 0, -size, 1, 1);
		tessellator.addVertexWithUV(-size, 0, +size, 1, 0);
		tessellator.addVertexWithUV(+size, 0, +size, 0, 0);
		tessellator.addVertexWithUV(+size, 0, -size, 0, 1);
		tessellator.draw();
		
		// Enable Lighting/Glow Off
		GL11.glEnable(GL11.GL_LIGHTING);
		
		// Disable Blending
		GL11.glDisable(GL11.GL_BLEND);
		
		GL11.glDepthMask(true);
		GL11.glPopMatrix();
		
		/*GL11.glPushMatrix();
		GL11.glDepthMask(false);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LIGHTING);
		
		GL11.glTranslated(x, y, z);
		GL11.glRotatef(-time/2, 0, 1, 0);
		
		double size2 = size * 3;
		
		this.bindTexture(disk);
		
		//top render
		tessellator.startDrawingQuads();
		tessellator.setBrightness(240);
		tessellator.setColorRGBA_F(1.0F, 0.757F, 0.49F, 1F);
		tessellator.addVertexWithUV(-size2, 0, -size2, 0, 0);
		tessellator.addVertexWithUV(-size2, 0, +size2, 0, 1);
		tessellator.addVertexWithUV(+size2, 0, +size2, 1, 1);
		tessellator.addVertexWithUV(+size2, 0, -size2, 1, 0);
		tessellator.draw();
		
		//bottom render
		GL11.glRotatef(180, 1, 0, 0);
		tessellator.startDrawingQuads();
		tessellator.setBrightness(240);
		tessellator.setColorRGBA_F(1.0F, 0.757F, 0.49F, 1F);
		tessellator.addVertexWithUV(-size2, 0, -size2, 1, 1);
		tessellator.addVertexWithUV(-size2, 0, +size2, 1, 0);
		tessellator.addVertexWithUV(+size2, 0, +size2, 0, 0);
		tessellator.addVertexWithUV(+size2, 0, -size2, 0, 1);
		tessellator.draw();
		
		// Enable Lighting/Glow Off
		GL11.glEnable(GL11.GL_LIGHTING);
		
		// Disable Blending
		GL11.glDisable(GL11.GL_BLEND);
		
		GL11.glDepthMask(true);
		GL11.glPopMatrix();*/
	}
	/*GL11.glPushMatrix();
	GL11.glTranslated(x, y, z);
	//GL11.glRotatef((entity.ticksExisted % 360) * 10, 1, 1, 1);
	GlStateManager.disableLighting();
	GlStateManager.disableCull();
	
	float size = entity.getDataManager().get(EntityBlackHole.SCALE);
	
	renderSwirl(entity, true, partialTicks, size);
	
	GL11.glScalef(size, size, size*10);
	
	bindTexture(blastTexture);
	blastModel.renderAll();
	
	
	GL11.glScalef(0.2F, 0.2F, 0.2F);
	
	/*FLARE START
	Tessellator tessellator = Tessellator.getInstance();
	BufferBuilder buf = tessellator.getBuffer();
	RenderHelper.disableStandardItemLighting();
	int j = 75;//entity.ticksExisted > 250 ? 250 : entity.ticksExisted;
	float f1 = (j + 2.0F) / 200.0F;
	float f2 = 0.0F;
	int count = 250;
	
	/*if(entity.ticksExisted < 250)
	{
		count = entity.ticksExisted * 3;
	}*/
	
	/*count = j;
	
	if (f1 > 0.8F)
	{
	    f2 = (f1 - 0.8F) / 0.2F;
	}
	
	Random random = new Random(432L);
	GlStateManager.disableTexture2D();
	GlStateManager.shadeModel(GL11.GL_SMOOTH);
	GlStateManager.enableBlend();
	GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
	GlStateManager.disableAlpha();
	GlStateManager.enableCull();
	GlStateManager.depthMask(false);
	GL11.glPushMatrix();
	
	//for (int i = 0; (float)i < (f1 + f1 * f1) / 2.0F * 60.0F; ++i)
	for(int i = 0; i < count; i++)
	{
	    GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
	    GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
	    GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
	    GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
	    GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
	    GL11.glRotatef(random.nextFloat() * 360.0F + f1 * 90.0F, 0.0F, 0.0F, 1.0F);
	    buf.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
	    float f3 = random.nextFloat() * 20.0F + 5.0F + f2 * 10.0F;
	    float f4 = random.nextFloat() * 2.0F + 1.0F + f2 * 2.0F;
	    //Drillgon200: Ah yes, numbers even more magic than the original. '0.53725490196F, 0.54509803921F, 0.2F' essentially means 'light yellow'
	    buf.pos(0.0D, 0.0D, 0.0D).color(0.53725490196F, 0.54509803921F, 0.2F, 1.0F).endVertex();
	    buf.pos(-0.866D * f4, f3, -0.5F * f4).color(0.53725490196F, 0.54509803921F, 0.2F, 0.0F).endVertex();
	    buf.pos(0.866D * f4, f3, -0.5F * f4).color(0.53725490196F, 0.54509803921F, 0.2F, 0.0F).endVertex();
	    buf.pos(0.0D, f3, 1.0F * f4).color(0.53725490196F, 0.54509803921F, 0.2F, 0.0F).endVertex();
	    buf.pos(-0.866D * f4, f3, -0.5F * f4).color(0.53725490196F, 0.54509803921F, 0.2F, 0.0F).endVertex();
	    tessellator.draw();
	}
	
	GL11.glPopMatrix();
	GL11.glPopMatrix();
	GlStateManager.depthMask(true);
	GlStateManager.disableCull();
	GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
	GlStateManager.disableBlend();
	GlStateManager.shadeModel(GL11.GL_FLAT);
	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	GlStateManager.enableTexture2D();
	GlStateManager.enableAlpha();
	RenderHelper.enableStandardItemLighting();
	}
	
	private void renderSwirl(Entity entity, boolean hasJet, float interp, float size) {
	
	bindTexture(diskTexture);
	
	GL11.glScalef(size, size, size);
	GL11.glRotatef(entity.getEntityId() % 90 - 45, 1, 0, 0);
	GL11.glRotatef(entity.getEntityId() % 360, 0, 1, 0);
	GL11.glRotatef((entity.ticksExisted + interp % 360) * -5, 0, 1, 0);
	GL11.glShadeModel(GL11.GL_SMOOTH);
	OpenGlHelper.glBlendFunc(770, 771, 1, 0);
	Vec3 vec = Vec3.createVectorHelper(1, 0, 0);
	
	Tessellator tess = Tessellator.instance;
	tess.startDrawingQuads();
	
	double s = 3;
	int count = 16;
	
	for(int i = 0; i < count; i++) {
	
		tess.setColorRGBA_F(0.0F, 0.0F, 0.0F, 1.0F);
		tess.addVertexWithUV(vec.xCoord * 0.9, 0, vec.zCoord * 0.9, 0.5 + vec.xCoord * 0.25 / s * 0.9, 0.5 + vec.zCoord * 0.25 / s * 0.9);
		//this.setColorFull(entity, tess);
		tess.setColorRGBA_I(0xFFFFFF, (int) (255.0F * (1.0F)));
		tess.addVertexWithUV(vec.xCoord * s, 0, vec.zCoord * s, 0.5 + vec.xCoord * 0.25, 0.5 + vec.zCoord * 0.25);
		
		vec.rotateAroundY((float)(Math.PI * 2 / count));
		//this.setColorFull(entity, tess);
		tess.setColorRGBA_I(0xFFFFFF, (int) (255.0F * (1.0F)));
		tess.addVertexWithUV(vec.xCoord * s, 0, vec.zCoord * s, 0.5 + vec.xCoord * 0.25, 0.5 + vec.zCoord * 0.25);
		tess.setColorRGBA_F(0.0F, 0.0F, 0.0F, 1.0F);
		tess.addVertexWithUV(vec.xCoord * 0.9, 0, vec.zCoord * 0.9, 0.5 + vec.xCoord * 0.25 / s * 0.9, 0.5 + vec.zCoord * 0.25 / s * 0.9);
	}
	
	tess.draw();
	
	tess.startDrawingQuads();
	
	GL11.glDisable(GL11.GL_ALPHA_TEST);
	GL11.glDepthMask(false);
	GL11.glAlphaFunc(GL11.GL_GEQUAL, 0.0F);
	GL11.glEnable(GL11.GL_BLEND);
	
	for(int i = 0; i < count; i++) {
	
		//this.setColorFull(entity, tess);
		tess.setColorRGBA_I(0xFFFFFF, (int) (255.0F * (1.0F)));
		tess.addVertexWithUV(vec.xCoord * s, 0, vec.zCoord * s, 0.5 + vec.xCoord * 0.25, 0.5 + vec.zCoord * 0.25);
		//this.setColorNone(entity, tess);
		tess.setColorRGBA_I(0xFFB900, 0);
		tess.addVertexWithUV(vec.xCoord * s * 2, 0, vec.zCoord * s * 2, 0.5 + vec.xCoord * 0.5, 0.5 + vec.zCoord * 0.5);
		
		vec.rotateAroundY((float)(Math.PI * 2 / count));
		tess.setColorRGBA_I(0xFFB900, 0);
		tess.addVertexWithUV(vec.xCoord * s * 2, 0, vec.zCoord * s * 2, 0.5 + vec.xCoord * 0.5, 0.5 + vec.zCoord * 0.5);
		tess.setColorRGBA_I(0xFFFFFF, (int) (255.0F * (1.0F)));
		tess.addVertexWithUV(vec.xCoord * s, 0, vec.zCoord * s, 0.5 + vec.xCoord * 0.25, 0.5 + vec.zCoord * 0.25);
	}
	
	tess.draw();
	
	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
	GL11.glDisable(GL11.GL_TEXTURE_2D);
	
	/*for(int j = -1; j <= 1; j += 2) {
		tess.startDrawing(GL11.GL_TRIANGLE_FAN);
	
		tess.setColorRGBA_F(1.0F, 1.0F, 1.0F, 0.35F);
		tess.addVertex(0, 0, 0);
		tess.setColorRGBA_F(1.0F, 1.0F, 1.0F, 0.0F);
		
		Vec3 jet = Vec3.createVectorHelper(0.5, 0, 0);
		
		for(int i = 0; i <= 12; i++) {
	
			tess.addVertex(jet.xCoord, 10 * j, jet.zCoord);
			jet.rotateAroundY((float)(Math.PI / 6 * -j));
		}
		
		tess.draw();
	}
	
	GL11.glEnable(GL11.GL_TEXTURE_2D);
	GL11.glShadeModel(GL11.GL_FLAT);
	GL11.glDisable(GL11.GL_BLEND);
	GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
	GL11.glDepthMask(true);
	GL11.glEnable(GL11.GL_ALPHA_TEST);
	}*/
	
	@Override
	protected ResourceLocation getEntityTexture(EntityBlackHole entity) {
		return hole;
	}
	
}
