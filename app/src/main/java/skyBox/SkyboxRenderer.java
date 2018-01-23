package skyBox;

import android.content.Context;
import android.graphics.Camera;
import android.opengl.GLES20;
import android.opengl.GLES30;

import javax.microedition.khronos.opengles.GL;

import entity.ViewCamera;
import model.RawModel;
import renderEngine.Loader;
import shaders.SkyboxShader;
import utility.FPSCounter;
import utility.Math3D.Matrix4f;
import utility.TextureResourceReader;

/**
 * Created by VRlab on 1/22/2018.
 */

public class SkyboxRenderer {


    private float time =8000 ;

    private static final float SIZE = 300f;
    private static final float[] VERTICES = {
            -SIZE,  SIZE, -SIZE,
            -SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE,  SIZE, -SIZE,
            -SIZE,  SIZE, -SIZE,

            -SIZE, -SIZE,  SIZE,
            -SIZE, -SIZE, -SIZE,
            -SIZE,  SIZE, -SIZE,
            -SIZE,  SIZE, -SIZE,
            -SIZE,  SIZE,  SIZE,
            -SIZE, -SIZE,  SIZE,

            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,

            -SIZE, -SIZE,  SIZE,
            -SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE, -SIZE,  SIZE,
            -SIZE, -SIZE,  SIZE,

            -SIZE,  SIZE, -SIZE,
            SIZE,  SIZE, -SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            -SIZE,  SIZE,  SIZE,
            -SIZE,  SIZE, -SIZE,

            -SIZE, -SIZE, -SIZE,
            -SIZE, -SIZE,  SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            -SIZE, -SIZE,  SIZE,
            SIZE, -SIZE,  SIZE
    };

    private static String[] TEXTURE_FILES ={"right.png", "left.png", "top.png", "bottom.png", "back.png", "front.png"};
    private static String[] TEXTURE_FILES_NIGHT ={"nightRight.png", "nightLeft.png", "nightTop.png", "nightBottom.png", "nightBack.png", "nightFront.png"};

    private RawModel cube;
    private SkyboxShader skyboxShader;
    private int nightTexutreID;

    private int texture;
    public SkyboxRenderer(Context context, Loader loader, Matrix4f projectionMatrix){
        cube = loader. loadtoVAO(VERTICES,3);
        texture = TextureResourceReader.loadCubeMap(context,TEXTURE_FILES);
        nightTexutreID =TextureResourceReader.loadCubeMap(context,TEXTURE_FILES_NIGHT);
        skyboxShader = new SkyboxShader(context);
        skyboxShader.runProgram();
        skyboxShader.loadProjectionMatrix(projectionMatrix);
        skyboxShader.connectTextureUnits();
        skyboxShader.stopProgram();
    }
    public void render(ViewCamera viewCamera,float r, float g, float b){

        skyboxShader.runProgram();
        skyboxShader.loadViewMatrix(viewCamera);
        skyboxShader.loadFogColor(r,g,b);
        GLES30.glBindVertexArray(cube.getVaoID());
        GLES20.glEnableVertexAttribArray(0);
        bindTextures();
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,cube.getVertexCount());
        GLES20.glDisableVertexAttribArray(0);
        GLES30.glBindVertexArray(0);
        skyboxShader.stopProgram();
    }
    private void bindTextures(){


        time += FPSCounter.getTime() *1000;
        time %= 24000;
        int texture1;
        int texture2;
        float blendFactor;
        if(time >= 0 && time < 5000){
            texture1 = nightTexutreID;
            texture2 = nightTexutreID;
            blendFactor = (time - 0)/(5000 - 0);
        }else if(time >= 5000 && time < 8000){
            texture1 = nightTexutreID;
            texture2 = texture;
            blendFactor = (time - 5000)/(8000 - 5000);
        }else if(time >= 8000 && time < 21000){
            texture1 = texture;
            texture2 = texture;
            blendFactor = (time - 8000)/(21000 - 8000);
        }else{
            texture1 = texture;
            texture2 = nightTexutreID;
            blendFactor = (time - 21000)/(24000 - 21000);
        }
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_CUBE_MAP,texture1);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_CUBE_MAP,texture2);
        skyboxShader.loadBlendFactor(blendFactor);

    }

}
