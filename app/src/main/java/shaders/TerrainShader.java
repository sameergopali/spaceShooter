package shaders;

import android.content.Context;

import entity.Light;
import entity.ViewCamera;
import shaders.ShaderProgram;
import utility.Math3D.Matrix4f;
import utility.Math3D.Vector3f;

/**
 * Created by VRlab on 1/18/2018.
 */

public class TerrainShader extends ShaderProgram {
    private static final String VERTEX_FILE="terrainVertex.glsl";
    private static final String FRAG_FILE= "terrainFrag.glsl";
    private int skyColor;

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
    private int backTexture;
    private int rTexture;
    private int gTexture;
    private int bTexture;
    private int blendMap;


    @Override
    protected void getAllUniforms() {
        modelMatrix=super.getUniformLocation("modelMatrix");
        projectionMatrix = super.getUniformLocation("projectionMatrix");
        viewMatrix = super.getUniformLocation("viewMatrix");
        lightColor = super.getUniformLocation("lightColor");
        lightPosition = super.getUniformLocation("lightPosition");
        shineDamper = super.getUniformLocation("shineDamper");
        reflectivity = super.getUniformLocation("reflectivity");
        skyColor = super.getUniformLocation("skyColor");
        backTexture = super.getUniformLocation("backgroundSampler");
        rTexture = super.getUniformLocation("rTexture");
        gTexture = super.getUniformLocation("gTexture");
        bTexture = super.getUniformLocation("bTexture");
        blendMap = super.getUniformLocation("blendMap");

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
    public void loadSkyColor(float r, float g, float b ){
        super.loadVector3(skyColor,new Vector3f(r,g,b));

    }
    public void connectTextureUnits(){
        super.loadInt(backTexture,0);
        super.loadInt(rTexture,1);
        super.loadInt(gTexture,2);
        super.loadInt(bTexture,3);
        super.loadInt(blendMap,4);
    }
}
