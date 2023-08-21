package trinity.render;

import trinity.render.IModelCustom;
import trinity.render.IModelCustomLoader;
import trinity.render.ModelFormatException;

import net.minecraft.util.ResourceLocation;

public class HmfModelLoader implements IModelCustomLoader {

    @Override
    public String getType()
    {
        return "HMF model";
    }

    private static final String[] types = { "hmf" };
    
    @Override
    public String[] getSuffixes()
    {
        return types;
    }

    @Override
    public IModelCustom loadInstance(ResourceLocation resource) throws ModelFormatException
    {
        return new ModelObject(resource);
    }
}
