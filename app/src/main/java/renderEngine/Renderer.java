package renderEngine;

import android.opengl.GLES20;
import android.opengl.GLES30;
import android.util.Log;

import entity.Entity;
import shaders.StaticShader;
import texture.ModelTexture;
import utility.Math3D.Matrix4f;

import static android.content.ContentValues.TAG;

/**
 * Created by VRlab on 1/16/2018.
 */

public class Renderer {
    private static  final float FOV =70.0f;
    private static final float NEAR_PLANE =0.1f;
    private static final  float FAR_PLANE =100f;
    private  int width , height;
    private Matrix4f projectionMatrix;

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }



    public void setProjectionMatrix(StaticShader staticShader){
        createProjectionMatrix();
        staticShader.runProgram();
        staticShader.loadProjectionMatrix(projectionMatrix);
        staticShader.stopProgram();


    }

    public void prepare(){
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT|GLES20.GL_DEPTH_BUFFER_BIT);

    }

    public void render(Entity entity, StaticShader staticShader){
        GLES30.glBindVertexArray(entity.getTexturedModel().getRawModel().getVaoID());
        GLES20.glEnableVertexAttribArray(0);
        GLES20.glEnableVertexAttribArray(1);
        GLES20.glEnableVertexAttribArray(2);

        Matrix4f modelMatrix = Matrix4f.createTransformationMatrix(entity.getPosition(),entity.getScale(), entity.getRx(),entity.getRy(), entity.getRz());
        staticShader.loadModelMatrix(modelMatrix);

        ModelTexture texture = entity.getTexturedModel().getModelTexture();
        staticShader.loadShineValue(texture.getShineDamper(),texture.getReflectivity() );
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,entity.getTexturedModel().getModelTexture().getTextureId());
        GLES20.glDrawElements(GLES20.GL_TRIANGLES,entity.getTexturedModel().getRawModel().getVertexCount(),GLES20.GL_UNSIGNED_INT,0);
        GLES20.glDisableVertexAttribArray(0);
        GLES20.glDisableVertexAttribArray(1);
        GLES20.glDisableVertexAttribArray(2);
        GLES30.glBindVertexArray(0);
    }


    private void createProjectionMatrix(){

      float [] m = new float[16];

        float aspectRatio = (float) width / (float) height;
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV/2f)))*aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;
        m[0] =x_scale;
        m[1] = 0f;
        m[2] = 0f;
        m[3] = 0f;
        m[4] = 0f;
        m[5] = y_scale;
        m[6] = 0f;
        m[7] = 0f;
        m[8] = 0f;
        m[9] = 0f;
        m[10] = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        m[11] = -1f;

        m[12] = 0f;
        m[13] = 0f;
        m[14] = -((2f * FAR_PLANE * NEAR_PLANE) / (frustum_length));
        m[15] = 0f;


       projectionMatrix = new Matrix4f(m);
        //projectionMatrix = new Matrix4f();
    }

}
