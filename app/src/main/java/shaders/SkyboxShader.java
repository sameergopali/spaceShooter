package shaders;

import android.content.Context;

import entity.ViewCamera;
import utility.FPSCounter;
import utility.Math3D.Matrix4f;
import utility.Math3D.Vector3f;

/**
 * Created by VRlab on 1/22/2018.
 */

public class SkyboxShader extends ShaderProgram{

    private static final String VERTEX_FILE = "skyboxVer.glsl";
    private static final String FRAGMENT_FILE = "skyboxFrag.glsl";

    private static float ROTATION_SPEED =2.0f/3.14f;


    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int fogColor;
    private int blendFactor;
    private int cubeMap;
    private int cubeMap2;
    private float rotation;
    public SkyboxShader(Context context) {
        super(context,VERTEX_FILE, FRAGMENT_FILE);
    }

    public void loadProjectionMatrix(Matrix4f matrix){
        super.loadMatrix(location_projectionMatrix, matrix);
    }

    public void loadViewMatrix(ViewCamera camera){
        Matrix4f matrix = Matrix4f.createViewMatrix(camera);
        matrix.getArray()[12]=0.0f;
        matrix.getArray()[13]=0.0f;
        matrix.getArray()[14]=0.0f;

        rotation += ROTATION_SPEED * FPSCounter.getTime();
        Matrix4f.rotate(rotation,new Vector3f(0,1,0),matrix);

        super.loadMatrix(location_viewMatrix, matrix);
    }



    @Override
    protected void getAllUniforms() {
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        fogColor = super.getUniformLocation("fogColor");
        blendFactor = super.getUniformLocation("blendFactor");
        cubeMap = super.getUniformLocation("cubeMap");
        cubeMap2 = super.getUniformLocation("cubeMap1");
    }

    public void loadBlendFactor(float blend){
        super.loadFloat(blendFactor,blend);
    }
    public void connectTextureUnits(){
        super.loadInt(cubeMap,0);
        super.loadInt(cubeMap2,1);
    }

    public  void loadFogColor(float r, float g, float b){
        super.loadVector3(fogColor,new Vector3f(r,g,b));
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
      //  super.bindAttribute(0, "textureCoords");
    }

}