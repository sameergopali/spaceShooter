package shaders;

import android.content.Context;

import entity.Light;
import entity.ViewCamera;
import shaders.ShaderProgram;
import utility.Math3D.Matrix4f;

/**
 * Created by VRlab on 1/18/2018.
 */

public class TerrainShader extends ShaderProgram {
    private static final String VERTEX_FILE="terrainVertex.glsl";
    private static final String FRAG_FILE= "terrainFrag.glsl";
    public TerrainShader(Context context) {
        super(context, VERTEX_FILE, FRAG_FILE);
    }


    private int modelMatrix;
    private  int projectionMatrix;
    private int viewMatrix;
    private int lightPosition;
    private int lightColor;
    private int  shineDamper;
    private int  reflectivity;


    @Override
    protected void getAllUniforms() {
        modelMatrix=super.getUniformLocation("modelMatrix");
        projectionMatrix = super.getUniformLocation("projectionMatrix");
        viewMatrix = super.getUniformLocation("viewMatrix");
        lightColor = super.getUniformLocation("lightColor");
        lightPosition = super.getUniformLocation("lightPosition");
        shineDamper = super.getUniformLocation("shineDamper");
        reflectivity = super.getUniformLocation("reflectivity");
    }

    public void loadModelMatrix(Matrix4f matrix){
        super.loadMatrix(modelMatrix,matrix);

    }
    public void loadProjectionMatrix(Matrix4f matrix){
        super.loadMatrix(projectionMatrix, matrix);
    }

    public void loadViewMatrix(ViewCamera camera){

        Matrix4f matrix=Matrix4f.createViewMatrix(camera);
        super.loadMatrix(viewMatrix, matrix);
    }
    public void loadShineValue(float damper, float reflectivity){
        super.loadFloat(shineDamper,damper);
        super.loadFloat(this.reflectivity,reflectivity );
    }
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0,"position");
        super.bindAttribute(1,"texCoord");
        super.bindAttribute(2,"normal");
    }
    public void loadLight(Light light){
        super.loadVector3(lightColor,light.getColor());
        super.loadVector3(lightPosition,light.getPosition());
    }
}
