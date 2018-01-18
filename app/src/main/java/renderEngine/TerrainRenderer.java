package renderEngine;

import android.opengl.GLES20;
import android.opengl.GLES30;

import java.util.List;
import java.util.Map;

import entity.Entity;
import model.RawModel;
import model.TexturedModel;
import shaders.TerrainShader;
import terrain.Terrain;
import texture.ModelTexture;
import utility.Math3D.Matrix4f;
import utility.Math3D.Vector3f;

/**
 * Created by VRlab on 1/18/2018.
 */

public class TerrainRenderer {
    private TerrainShader terrainShader;

    public TerrainRenderer(TerrainShader terrainShader,Matrix4f projectionMatrix) {
        this.terrainShader = terrainShader;
        terrainShader.runProgram();
        terrainShader.loadProjectionMatrix(projectionMatrix);
        terrainShader.stopProgram();
    }

    public void render(List<Terrain> terrains ){
        for(  Terrain terrain : terrains){
            prepareTerrain(terrain);
            loadModel(terrain);
            GLES20.glDrawElements(GLES20.GL_TRIANGLES,  terrain.getModel().getVertexCount(), GLES20.GL_UNSIGNED_INT, 0);
            unbindTextureModel();
        }

    }

    private void prepareTerrain(Terrain terrain ){
        RawModel rawModel =terrain.getModel();
        GLES30.glBindVertexArray(rawModel.getVaoID());
        GLES20.glEnableVertexAttribArray(0);
        GLES20.glEnableVertexAttribArray(1);
        GLES20.glEnableVertexAttribArray(2);
        ModelTexture texture = terrain.getTexture();
        terrainShader.loadShineValue(texture.getShineDamper(), texture.getReflectivity());
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,texture.getTextureId());

    }
    private void unbindTextureModel(){
        GLES20.glDisableVertexAttribArray(0);
        GLES20.glDisableVertexAttribArray(1);
        GLES20.glDisableVertexAttribArray(2);
        GLES30.glBindVertexArray(0);
    }
    private void loadModel(Terrain terrain){
        Matrix4f modelMatrix = Matrix4f.createTransformationMatrix(new Vector3f(terrain.getX(),0,terrain.getZ()),1,0,0,0);
        terrainShader.loadModelMatrix(modelMatrix);
    }



}

