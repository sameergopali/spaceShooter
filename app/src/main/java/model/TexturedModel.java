package model;

import texture.ModelTexture;

/**
 * Created by sameer on 1/15/2018.
 */

public class   TexturedModel {
    public RawModel getRawModel() {
        return rawModel;
    }

    public ModelTexture getModelTexture() {
        return modelTexture;
    }

    private RawModel rawModel;
    private ModelTexture modelTexture;

    public TexturedModel(RawModel rawModel,ModelTexture modelTexture){
        this.rawModel=rawModel;
        this.modelTexture=modelTexture;

    }
}
