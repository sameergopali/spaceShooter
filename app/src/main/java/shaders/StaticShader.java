package shaders;

import android.content.Context;
import android.content.Intent;

import utility.Math3D.Matrix4f;

/**
 * Created by sameer on 1/15/2018.
 */

public class StaticShader extends ShaderProgram {

    private static final String VERTEX_FILE="vertex.glsl";
    private static final String FRAG_FILE= "frag.glsl";

    private int modelMatrix;

   public StaticShader(Context context){
        super(context,VERTEX_FILE,FRAG_FILE);

    }

    @Override
    protected void getAllUniforms() {
       modelMatrix=super.getUniformLocation("modelMatrix");

    }
    public void loadMatrix(Matrix4f matrix){
       super.loadMatrix(modelMatrix,matrix);

    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0,"position");
        super.bindAttribute(1,"texCoord");
    }
}
