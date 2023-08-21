package trinity.render;

import java.util.ArrayList;

import trinity.render.Tessellator;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class GroupObject2 {
	
    public String name;
    public ArrayList<Face2> faces = new ArrayList<Face2>();
    public int glDrawingMode;

    public GroupObject2()
    {
        this("");
    }

    public GroupObject2(String name)
    {
        this(name, -1);
    }

    public GroupObject2(String name, int glDrawingMode)
    {
        this.name = name;
        this.glDrawingMode = glDrawingMode;
    }

    @SideOnly(Side.CLIENT)
    public void render()
    {
        if (faces.size() > 0)
        {
            Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawing(glDrawingMode);
            render(tessellator);
            tessellator.draw();
        }
    }

    @SideOnly(Side.CLIENT)
    public void render(Tessellator tessellator)
    {
        if (faces.size() > 0)
        {
            for (Face2 face : faces)
            {
                face.addFaceForRender(tessellator);
            }
        }
    }
}
