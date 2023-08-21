package trinity.render.entity;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import trinity.entities.EntityNuclearCloud;
import trinity.render.AdvancedModelLoader;
import trinity.render.IModelCustom;
import trinity.render.Tessellator;
import trinity.util.ResourceManager;

import java.util.Random;

public class RenderNuclearExplosion extends Render<EntityNuclearCloud> {	
		private IModelCustom blastModel;		
		private IModelCustom ringModel;
		private IModelCustom ringBigModel;
	    public float scale = 0;
	    public float ring = 0;
	    private float previousPartialTicks =-1;  
	public RenderNuclearExplosion(RenderManager renderManager) {
		super(renderManager);
		blastModel = AdvancedModelLoader.loadModel(ResourceManager.objTesterModelRL);
    	ringModel = AdvancedModelLoader.loadModel(ResourceManager.ringModelRL);
    	ringBigModel = AdvancedModelLoader.loadModel(ResourceManager.ringBigModelRL);
    	scale = 0;
    	ring = 0;

	}

	@Override
    public boolean shouldRender(EntityNuclearCloud livingEntity, ICamera camera, double camX, double camY, double camZ)
    {
		return true;
    }
	
	@Override
	public void doRender(EntityNuclearCloud cloud, double x, double y, double z, float entityYaw, float partialTicks) {
		if(previousPartialTicks==partialTicks)
		{
			return;
		}
		else
		{
			previousPartialTicks =partialTicks;
		}
		
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_LIGHTING_BIT);
        GL11.glTranslatef((float)x, (float)y + 0.25F, (float)z);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
    	float size = cloud.getDataManager().get(EntityNuclearCloud.SCALE);
        GL11.glScalef(size, size, size);
		int age = cloud.age;
        int shockScale = age * 4;
        int fireScale = (int)((age - 25) * 1.5);
        if(age < 50) {
    		GL11.glPushMatrix();
    		GL11.glColor4f(0.2F, 0.2F, 0.2F, 0.9F);
    		
    		GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
	
	        GL11.glScalef(shockScale, shockScale, shockScale);
	        GL11.glScalef(2, 2, 2);
	        
	        for(float i = 0.9F; i <= 1; i += 0.05F) {
	            GL11.glScalef(i, i, i);
	        	ResourceManager.sphere_ruv.renderAll();
	        	ResourceManager.sphere_iuv.renderAll();
	            GL11.glScalef(1/i, 1/i, 1/i);
	        }
	        
			GL11.glDisable(GL11.GL_BLEND);
	        
			GL11.glColor4f(0.4F, 0.4F, 0.4F, 1F);
	
	        GL11.glScalef(0.6F, 1F / shockScale * 5, 0.6F);
			ringModel.renderAll();
	        GL11.glScalef(1.1F, 1F, 1.1F);
			ringModel.renderAll();
	        GL11.glScalef(1.1F, 1F, 1.1F);
			ringModel.renderAll();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glPopMatrix();
			
			{
		        Tessellator tessellator = Tessellator.instance;
				RenderHelper.disableStandardItemLighting();
				float f1 = (cloud.ticksExisted + 2.0F) / 200.0F;
				float f2 = 0.0F;
				int count = 250;
				
				if(cloud.ticksExisted < 250)
				{
					count = cloud.ticksExisted * 3;
				}
		        if (f1 > 0.8F)
		        {
		            f2 = (f1 - 0.8F) / 0.2F;
		        }

		        Random random = new Random(432L);
		        GL11.glDisable(GL11.GL_TEXTURE_2D);
		        GL11.glShadeModel(GL11.GL_SMOOTH);
		        GL11.glEnable(GL11.GL_BLEND);
		        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		        GL11.glDisable(GL11.GL_ALPHA_TEST);
		        GL11.glEnable(GL11.GL_CULL_FACE);
		        GL11.glDepthMask(false);
		        GL11.glPushMatrix();
		        GL11.glTranslatef((float)x, (float)y, (float)z);
		        GL11.glScalef(shockScale, shockScale, shockScale);
		        
		        for(int i = 0; i < count; i++)
		        {
		            GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
		            GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
		            GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
		            GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
		            GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
		            GL11.glRotatef(random.nextFloat() * 360.0F + f1 * 90.0F, 0.0F, 0.0F, 1.0F);
		            tessellator.startDrawing(6);
		            float f3 = random.nextFloat() * 20.0F + 5.0F + f2 * 10.0F;
		            float f4 = random.nextFloat() * 2.0F + 1.0F + f2 * 2.0F;
		            tessellator.setColorRGBA_I(59345715, (int)(255.0F * (1.0F - f2)));
		            tessellator.addVertex(0.0D, 0.0D, 0.0D);
		            tessellator.setColorRGBA_I(59345735, 0);
		            tessellator.addVertex(-0.866D * f4, f3, -0.5F * f4);
		            tessellator.addVertex(0.866D * f4, f3, -0.5F * f4);
		            tessellator.addVertex(0.0D, f3, 1.0F * f4);
		            tessellator.addVertex(-0.866D * f4, f3, -0.5F * f4);
		            tessellator.draw();
		        }

		        GL11.glPopMatrix();
		        GL11.glDepthMask(true);
		        GL11.glDisable(GL11.GL_CULL_FACE);
		        GL11.glDisable(GL11.GL_BLEND);
		        GL11.glShadeModel(GL11.GL_FLAT);
		        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		        GL11.glEnable(GL11.GL_TEXTURE_2D);
		        GL11.glEnable(GL11.GL_ALPHA_TEST);
		        RenderHelper.enableStandardItemLighting();
			}
        }
        
        if(age >= 50 && age < 150) {
    		GL11.glPushMatrix();
    		if(cloud.getDataManager().get(EntityNuclearCloud.SOMETHING) == 1)
    			GL11.glColor4f(0.2F, 0.7F, 0.0F, 0.9F);
    		else
    			GL11.glColor4f(0.4F, 0.15F, 0.0F, 0.9F);
    		
    		GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
	        GL11.glTranslatef(0, fireScale * 0.75F, 0);
	
	        GL11.glScalef(fireScale * 0.85F, fireScale, fireScale * 0.85F);
	        
	        for(float i = 0.6F; i <= 1; i += 0.2F) {
	            GL11.glScalef(i, i, i);
	        	ResourceManager.sphere_ruv.renderAll();
	            GL11.glScalef(1/i, 1/i, 1/i);
	        }
	        
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glPopMatrix();
        }
        
        if(age >= 150) {
    		GL11.glPushMatrix();
            bindTexture(getEntityTexture(cloud));
	        GL11.glTranslatef(0, -40, 0);
	        GL11.glScalef(6, 6, 6);
	        GL11.glDisable(GL11.GL_CULL_FACE);
            blastModel.renderAll();
            GL11.glEnable(GL11.GL_CULL_FACE);
    		GL11.glPopMatrix();
    		
    		GL11.glPushMatrix();
    		GL11.glDisable(GL11.GL_TEXTURE_2D);
	        GL11.glScalef(1.5F, 1.5F, 1.5F);
			GL11.glColor4f(0.4F, 0.4F, 0.4F, 1F);
	        GL11.glScalef(10, 10, 10);
	        float f = 1.8F + (((float)Math.sin(((double)age) / 20 + 90) * 0.25F) * 0.5F);
	        float f1 = 1 + ((float)Math.sin(((double)age) / 10) * 0.15F);
	        GL11.glScalef(f, 1, f);
	        GL11.glTranslatef(0, 3.5F + f1 * 0.25F, 0);
			ringModel.renderAll();
	        GL11.glTranslatef(0, - f1 * 0.25F * 2, 0);
			ringModel.renderAll();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
    		GL11.glPopMatrix();
        }
        
        if(age >= 50) {
    		GL11.glPushMatrix();
    		GL11.glDisable(GL11.GL_TEXTURE_2D);
	        GL11.glScalef(2, 2, 2);
			GL11.glColor4f(0.4F, 0.4F, 0.4F, 1F);
			float f = (float)Math.min((age - 50) * 0.5, 20);
	        GL11.glScalef(f, 15, f);
	        GL11.glScalef(1.5F, 1, 1.5F);
	        GL11.glTranslatef(0, -0.15F, 0);
	        GL11.glScalef(1.5F, 1, 1.5F);
	        GL11.glTranslatef(0, -0.15F, 0);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glPopMatrix();
			
    		GL11.glPushMatrix();
    		GL11.glDisable(GL11.GL_TEXTURE_2D);
	        GL11.glScalef(2, 2, 2);
			GL11.glColor4f(0.6F, 0.6F, 0.6F, 1F);
			float f0 = (float)Math.min((age - 50) * 0.25, 20) * 5F;
	        GL11.glScalef(f0, 15, f0);
	        GL11.glTranslatef(0, 3.5F, 0);
			ringBigModel.renderAll();
	        GL11.glTranslatef(0, 1F, 0);
	        GL11.glScalef(0.65F, 1, 0.65F);
			ringModel.renderAll();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glPopMatrix();
			
	        /*GL11.glPushMatrix();
	        GL11.glDepthMask(false);
	        
	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glDisable(GL11.GL_LIGHTING);
	        
	        GL11.glTranslatef(0, 50, 0);

	        //double size = radius * 4;

	        this.bindTexture(ResourceManager.shockwave);

	        //top render
	        Tessellator tessellator = Tessellator.instance;
	        tessellator.startDrawingQuads();
	        tessellator.setBrightness(240);
	        tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1F);
	        tessellator.addVertexWithUV(-f0*2, 0, -f0*2, 0, 0);
	        tessellator.addVertexWithUV(-f0*2, 0, +f0*2, 0, 1);
	        tessellator.addVertexWithUV(+f0*2, 0, +f0*2, 1, 1);
	        tessellator.addVertexWithUV(+f0*2, 0, -f0*2, 1, 0);
	        tessellator.draw();

	        //bottom render
	        GL11.glRotatef(180, 1, 0, 0);
	        tessellator.startDrawingQuads();
	        tessellator.setBrightness(240);
	        tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1F);
	        tessellator.addVertexWithUV(-size, 0, -size, 1, 1);
	        tessellator.addVertexWithUV(-size, 0, +size, 1, 0);
	        tessellator.addVertexWithUV(+size, 0, +size, 0, 0);
	        tessellator.addVertexWithUV(+size, 0, -size, 0, 1);
	        tessellator.draw();
	        GL11.glPopMatrix();*/

        }
        
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glPopAttrib();
		GL11.glPopMatrix();
	}
	@Override
	protected ResourceLocation getEntityTexture(EntityNuclearCloud entity) {
		return ResourceManager.fireball;
	}

}
