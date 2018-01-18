package model;

import renderEngine.Loader;

/**
 * Created by VRlab on 1/17/2018.
 */

public class BgModelLoader {

  public static RawModel createBgModel(Loader loader){
    float[] vertices = {
            -1.0f, 1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, 1.0f, -1.0f
    };
    int [] indices ={
            0,1,3,
            3,1,2
    };
    float[] textureCoords={
            0,0,
            0,1,
            1,1,
            1,0

    };

   return loader.loadtoVAO(vertices,indices,textureCoords);
  }


}
