package trinity.render.entity;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import trinity.config.TrinityConfig;
import trinity.entities.EntityThermalBlast;
//import trinity.entities.EntityFalloutRain;
import trinity.util.Reference;
import trinity.handler.Vec3;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.IRenderHandler;

public class RenderThermalBlast extends Render<EntityThermalBlast> {

	private Minecraft mc;
	private Random random = new Random();
	float[] rainXCoords;
	float[] rainYCoords;
	private int rendererUpdateCount;
	private DynamicTexture lightmapTexture;
	private ResourceLocation locationLightMap;
	long lastTime = System.nanoTime();
	private static final ResourceLocation BLACK_RAIN_TEXTURES = new ResourceLocation("textures/environment/snow.png");
	private static final ResourceLocation falloutTexture = new ResourceLocation(Reference.MODID, "textures/fallout.png");
	private float previousPartialTicks =-1;  
	
	public RenderThermalBlast(RenderManager renderManager) {
        super(renderManager);
        this.mc = Minecraft.getMinecraft();
        this.rainXCoords = new float[1024];
        this.rainYCoords = new float[1024];
        for (int i = 0; i < 32; ++i)
        {
            for (int j = 0; j < 32; ++j)
            {
                float f = (float)(j - 16);
                float f1 = (float)(i - 16);
                float f2 = MathHelper.sqrt(f * f + f1 * f1);
                this.rainXCoords[i << 5 | j] = -f1 / f2;
                this.rainYCoords[i << 5 | j] = f / f2;
            }
        }
    }
   
	@Override
	public boolean shouldRender(EntityThermalBlast livingEntity, ICamera camera, double camX, double camY, double camZ) {
		if(TrinityConfig.fallout_rendering)
		{	
			return true;
		}
		else
			return false;
	}
    @Override
    public void doRender(EntityThermalBlast entity, double x, double y, double z, float entityYaw, float partialTicks) {
		if(TrinityConfig.fallout_rendering)
		{
			if(previousPartialTicks==partialTicks)
			{
				return;
			}
			else
			{
				previousPartialTicks =partialTicks;
			}
    	GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);
        Entity ent = this.mc.getRenderViewEntity();
        Vec3 vector = Vec3.createVectorHelper(ent.posX - entity.posX,
                ent.posY - entity.posY, ent.posZ - entity.posZ);
       
        double d = vector.lengthVector();
       
        if (d <= entity.getScale()) {
            rendererUpdateCount++;
            long time = System.nanoTime();
            float t = (time - lastTime) / 50000000;
            if (t <= 1.0F)
                renderRainSnow2(t);
            else
                renderRainSnow2(1.0F);
 
            lastTime = time;
        }
        GL11.glPopMatrix();
		}
    }
   
    protected void renderRainSnow(float p_78474_1_) {
        MutableBlockPos pos = new BlockPos.MutableBlockPos();
        IRenderHandler renderer = null;
        if ((renderer = this.mc.world.provider.getWeatherRenderer()) != null) {
            renderer.render(p_78474_1_, this.mc.world, mc);
            return;
        }
 
        float f1 = 1;
 
        if (f1 > 0.0F) {
 
            if (this.rainXCoords == null) {
                this.rainXCoords = new float[1024];
                this.rainYCoords = new float[1024];
 
                for (int i = 0; i < 32; ++i) {
                    for (int j = 0; j < 32; ++j) {
                        float f2 = j - 16;
                        float f3 = i - 16;
                        float f4 = MathHelper.sqrt(f2 * f2 + f3 * f3);
                        this.rainXCoords[i << 5 | j] = -f3 / f4;
                        this.rainYCoords[i << 5 | j] = f2 / f4;
                    }
                }
            }
 
            Entity entitylivingbase = this.mc.getRenderViewEntity();
            WorldClient worldclient = this.mc.world;
            int k2 = MathHelper.floor(entitylivingbase.posX);
            int l2 = MathHelper.floor(entitylivingbase.posY);
            int i3 = MathHelper.floor(entitylivingbase.posZ);
            Tessellator tessellator = Tessellator.getInstance();
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            double d0 = entitylivingbase.lastTickPosX
                    + (entitylivingbase.posX - entitylivingbase.lastTickPosX) * p_78474_1_;
            double d1 = entitylivingbase.lastTickPosY
                    + (entitylivingbase.posY - entitylivingbase.lastTickPosY) * p_78474_1_;
            double d2 = entitylivingbase.lastTickPosZ
                    + (entitylivingbase.posZ - entitylivingbase.lastTickPosZ) * p_78474_1_;
            int k = MathHelper.floor(d1);
            byte b0 = 5;
 
            if (this.mc.gameSettings.fancyGraphics) {
                b0 = 10;
            }
 
            boolean flag = false;
            byte b1 = -1;
            float f5 = this.rendererUpdateCount + p_78474_1_;
 
            if (this.mc.gameSettings.fancyGraphics) {
                b0 = 10;
            }
 
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            flag = false;
 
            for (int l = i3 - b0; l <= i3 + b0; ++l) {
                for (int i1 = k2 - b0; i1 <= k2 + b0; ++i1) {
                    int j1 = (l - i3 + 16) * 32 + i1 - k2 + 16;
                    float f6 = this.rainXCoords[j1] * 0.5F;
                    float f7 = this.rainYCoords[j1] * 0.5F;
                    pos.setPos(i1, 50, l);
                    Biome biomegenbase = worldclient.getBiomeForCoordsBody(pos);
 
                    {
                        int k1 = worldclient.getPrecipitationHeight(pos).getY();
                        int l1 = l2 - b0;
                        int i2 = l2 + b0;
 
                        if (l1 < k1) {
                            l1 = k1;
                        }
 
                        if (i2 < k1) {
                            i2 = k1;
                        }
 
                        float f8 = 1.0F;
                        int j2 = k1;
 
                        if (k1 < k) {
                            j2 = k;
                        }
 
                        if (l1 != i2) {
                            pos.setY(l1);
                            this.random.setSeed(i1 * i1 * 3121 + i1 * 45238971 ^ l * l * 418711 + l * 13761);
                            float f9 = biomegenbase.getTemperature(pos);
                            float f10;
                            double d4;
                            {
                                if (b1 != 1) {
                                    if (b1 >= 0) {
                                        tessellator.draw();
                                    }
                                    b1 = 1;
                                    this.mc.getTextureManager().bindTexture(RenderThermalBlast.falloutTexture);
                                    tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);;
                                }
 
                                f10 = ((this.rendererUpdateCount & 511) + p_78474_1_) / 512.0F;
                                float f16 = this.random.nextFloat() + f5 * 0.01F * (float) this.random.nextGaussian();
                                float f11 = this.random.nextFloat() + f5 * (float) this.random.nextGaussian() * 0.001F;
                                d4 = i1 + 0.5F - entitylivingbase.posX;
                                double d5 = l + 0.5F - entitylivingbase.posZ;
                                float f14 = MathHelper.sqrt(d4 * d4 + d5 * d5) / b0;
                                float f15 = 1.0F;
                                BufferBuilder buf = tessellator.getBuffer();
                                int bright = (int)(worldclient.getLightBrightness(pos.setPos(i1, j2, l)) * 3 + 15728880 / 4);
                                buf.color(f15, f15, f15, ((1.0F - f14 * f14) * 0.3F + 0.5F) * f1);
                                buf.setTranslation(-d0 * 1.0D, -d1 * 1.0D, -d2 * 1.0D);
                               
                                buf.pos(i1 - f6 + 0.5D, l1, l - f7 + 0.5D).tex(0.0F * f8 + f16, l1 * f8 / 4.0F + f10 * f8 + f11).endVertex();
                                buf.pos(i1 + f6 + 0.5D, l1, l + f7 + 0.5D).tex(1.0F * f8 + f16, l1 * f8 / 4.0F + f10 * f8 + f11).endVertex();
                                buf.pos(i1 + f6 + 0.5D, i2, l + f7 + 0.5D).tex(1.0F * f8 + f16, i2 * f8 / 4.0F + f10 * f8 + f11).endVertex();
                                buf.pos(i1 - f6 + 0.5D, i2, l - f7 + 0.5D).tex(0.0F * f8 + f16, i2 * f8 / 4.0F + f10 * f8 + f11).endVertex();
                                buf.setTranslation(0.0D, 0.0D, 0.0D);
                            }
                        }
                    }
                }
            }
 
            if (b1 >= 0) {
                tessellator.draw();
            }
 
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
        }
    }
 
    protected void renderRainSnow2(float partialTicks)
    {
        float f = 1;

        if (f > 0.0F)
        {
        	GL11.glEnable(GL11.GL_LIGHTING);
            //EntityRenderer.enableLightmap();
            Entity entity = this.mc.getRenderViewEntity();
            World world = this.mc.world;
            int i = MathHelper.floor(entity.posX);
            int j = MathHelper.floor(entity.posY);
            int k = MathHelper.floor(entity.posZ);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            GlStateManager.disableCull();
            GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.alphaFunc(516, 0.1F);
            double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks;
            double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks;
            double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks;
            int l = MathHelper.floor(d1);
            int i1 = 5;

            if (this.mc.gameSettings.fancyGraphics)
            {
                i1 = 10;
            }

            int j1 = -1;
            float f1 = (float)this.rendererUpdateCount + partialTicks;
            bufferbuilder.setTranslation(-d0, -d1, -d2);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for (int k1 = k - i1; k1 <= k + i1; ++k1)
            {
                for (int l1 = i - i1; l1 <= i + i1; ++l1)
                {
                    int i2 = (k1 - k + 16) * 32 + l1 - i + 16;
                    double d3 = (double)this.rainXCoords[i2] * 0.5D;
                    double d4 = (double)this.rainYCoords[i2] * 0.5D;
                    blockpos$mutableblockpos.setPos(l1, 0, k1);
                    Biome biome = world.getBiome(blockpos$mutableblockpos);

                    //if (biome.canRain() || biome.getEnableSnow())
                    //{
                        int j2 = world.getPrecipitationHeight(blockpos$mutableblockpos).getY();
                        int k2 = j - i1;
                        int l2 = j + i1;

                        if (k2 < j2)
                        {
                            k2 = j2;
                        }

                        if (l2 < j2)
                        {
                            l2 = j2;
                        }

                        int i3 = j2;

                        if (j2 < l)
                        {
                            i3 = l;
                        }

                        if (k2 != l2)
                        {
                            this.random.setSeed((long)(l1 * l1 * 3121 + l1 * 45238971 ^ k1 * k1 * 418711 + k1 * 13761));
                            blockpos$mutableblockpos.setPos(l1, k2, k1);
                            float f2 = biome.getTemperature(blockpos$mutableblockpos);

                            //if (world.getBiomeProvider().getTemperatureAtHeight(f2, j2) >= 0.15F)
                            /*if (world.getRainStrength(partialTicks) > 0.15F)
                            {
                                if (j1 != 0)
                                {
                                    if (j1 >= 0)
                                    {
                                        tessellator.draw();
                                    }

                                    j1 = 0;
                                    this.mc.getTextureManager().bindTexture(BLACK_RAIN_TEXTURES);
                                    bufferbuilder.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
                                }

                                double d5 = -((double)(this.rendererUpdateCount + l1 * l1 * 3121 + l1 * 45238971 + k1 * k1 * 418711 + k1 * 13761 & 31) + (double)partialTicks) / 32.0D * (3.0D + this.random.nextDouble());
                                double d6 = (double)((float)l1 + 0.5F) - entity.posX;
                                double d7 = (double)((float)k1 + 0.5F) - entity.posZ;
                                float f3 = MathHelper.sqrt(d6 * d6 + d7 * d7) / (float)i1;
                                float f4 = ((1.0F - f3 * f3) * 0.5F + 0.5F) * f;
                                blockpos$mutableblockpos.setPos(l1, i3, k1);
                                int j3 = world.getCombinedLight(blockpos$mutableblockpos, 0);
                                int k3 = j3 >> 16 & 65535;
                                int l3 = j3 & 65535;
                                bufferbuilder.pos((double)l1 - d3 + 0.5D, (double)l2, (double)k1 - d4 + 0.5D).tex(0.0D, (double)k2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f4).lightmap(k3, l3).endVertex();
                                bufferbuilder.pos((double)l1 + d3 + 0.5D, (double)l2, (double)k1 + d4 + 0.5D).tex(1.0D, (double)k2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f4).lightmap(k3, l3).endVertex();
                                bufferbuilder.pos((double)l1 + d3 + 0.5D, (double)k2, (double)k1 + d4 + 0.5D).tex(1.0D, (double)l2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f4).lightmap(k3, l3).endVertex();
                                bufferbuilder.pos((double)l1 - d3 + 0.5D, (double)k2, (double)k1 - d4 + 0.5D).tex(0.0D, (double)l2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f4).lightmap(k3, l3).endVertex();
                            }
                            else*/
                            {
                                if (j1 != 1)
                                {
                                    if (j1 >= 0)
                                    {
                                        tessellator.draw();
                                    }

                                    j1 = 1;
                                    this.mc.getTextureManager().bindTexture(falloutTexture);
                                    bufferbuilder.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
                                }

                                double d8 = (double)(-((float)(this.rendererUpdateCount & 511) + partialTicks) / 512.0F);
                                double d9 = this.random.nextDouble() + (double)f1 * 0.01D * (double)((float)this.random.nextGaussian());
                                double d10 = this.random.nextDouble() + (double)(f1 * (float)this.random.nextGaussian()) * 0.001D;
                                double d11 = (double)((float)l1 + 0.5F) - entity.posX;
                                double d12 = (double)((float)k1 + 0.5F) - entity.posZ;
                                float f6 = MathHelper.sqrt(d11 * d11 + d12 * d12) / (float)i1;
                                float f5 = ((1.0F - f6 * f6) * 0.3F + 0.5F) * f;
                                blockpos$mutableblockpos.setPos(l1, i3, k1);
                                int i4 = (world.getCombinedLight(blockpos$mutableblockpos, 0) * 3 + 15728880) / 4;
                                int j4 = i4 >> 16 & 65535;
                                int k4 = i4 & 65535;
                                bufferbuilder.pos((double)l1 - d3 + 0.5D, (double)l2, (double)k1 - d4 + 0.5D).tex(0.0D + d9, (double)k2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f5).lightmap(j4, k4).endVertex();
                                bufferbuilder.pos((double)l1 + d3 + 0.5D, (double)l2, (double)k1 + d4 + 0.5D).tex(1.0D + d9, (double)k2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f5).lightmap(j4, k4).endVertex();
                                bufferbuilder.pos((double)l1 + d3 + 0.5D, (double)k2, (double)k1 + d4 + 0.5D).tex(1.0D + d9, (double)l2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f5).lightmap(j4, k4).endVertex();
                                bufferbuilder.pos((double)l1 - d3 + 0.5D, (double)k2, (double)k1 - d4 + 0.5D).tex(0.0D + d9, (double)l2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f5).lightmap(j4, k4).endVertex();
                            }
                        //}
                    }
                }
            }

            if (j1 >= 0)
            {
                tessellator.draw();
            }

            bufferbuilder.setTranslation(0.0D, 0.0D, 0.0D);
            GlStateManager.enableCull();
            GlStateManager.disableBlend();
            GlStateManager.alphaFunc(516, 0.1F);
            GL11.glDisable(GL11.GL_LIGHTING);
            //this.disableLightmap();
        }
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntityThermalBlast entity) {
        return null;
    }

}
