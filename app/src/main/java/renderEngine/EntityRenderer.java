package renderEngine;

import android.opengl.GLES20;
import android.opengl.GLES30;
import android.util.Log;

import java.util.List;
import java.util.Map;

import entity.Entity;
import model.TexturedModel;
import shaders.StaticShader;
import texture.ModelTexture;
import utility.Math3D.Matrix4f;

import static android.content.ContentValues.TAG;

/**
 * Created by VRlab on 1/16/2018.
 */

public class EntityRenderer {

   private  StaticShader staticShader;

    public EntityRenderer(StaticShader staticShader,Matrix4f projectionMatrix){
        this.staticShader = staticShader;
        staticShader.runProgram();
        staticShader.loadProjectionMatrix(projectionMatrix);
        staticShader.stopProgram();
    }


    public void render(Map<TexturedModel,List<Entity>> entities ){
            for(TexturedModel model : entities.keySet()){
                prepareTexturedModel(model);
                List<Entity> batch = entities.get(model);
                for(Entity entity: batch){
                    prepareInstance(entity);
                    GLES20.glDrawElements(GLES20.GL_TRIANGLES,  model.getRawModel().getVertexCount(), GLES20.GL_UNSIGNED_INT, 0);

                }
            unbindTextureModel();
            }

    }

    private void prepareTexturedModel(TexturedModel texturedModel){
        GLES30.glBindVertexArray(texturedModel.getRawModel().getVaoID());
        GLES20.glEnableVertexAttribArray(0);
        GLES20.glEnableVertexAttribArray(1);
        GLES20.glEnableVertexAttribArray(2);
        ModelTexture texture = texturedModel.getModelTexture();
        staticShader.loadShineValue(texture.getShineDamper(), texture.getReflectivity());
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,texturedModel.getModelTexture().getTextureId());

    }
    private void unbindTextureModel(){
        GLES20.glDisableVertexAttribArray(0);
        GLES20.glDisableVertexAttribArray(1);
        GLES20.glDisableVertexAttribArray(2);
        GLES30.glBindVertexArray(0);
    }
    private void prepareInstance(Entity entity){
        Matrix4f modelMatrix = Matrix4f.createTransformationMatrix(entity.getPosition(), entity.getScale(), entity.getRx(), entity.getRy(), entity.getRz());
        staticShader.loadModelMatrix(modelMatrix);
    }


}


