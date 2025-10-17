package io.bluebeaker.mtepatches.render;

import codechicken.lib.vec.uv.IconTransformation;
import codechicken.lib.vec.uv.UV;
import codechicken.lib.vec.uv.UVTransformation;
import net.minecraftforge.client.model.ModelLoader;

/**
 * UV Transformation to get pure white color from the texture.
 */
public class WhiteColorUV extends UVTransformation {
    @Override
    public void apply(UV uv) {
        uv.u= ModelLoader.White.INSTANCE.getInterpolatedU(8);
        uv.v= ModelLoader.White.INSTANCE.getInterpolatedV(8);
    }

    @Override
    public UVTransformation inverse() {
        return new IconTransformation(ModelLoader.White.INSTANCE).inverse();
    }
}
