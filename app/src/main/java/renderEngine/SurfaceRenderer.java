package renderEngine;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import entity.Entity;
import model.RawModel;
import model.TexturedModel;
import shaders.StaticShader;
import texture.ModelTexture;
import utility.FPSCounter;
import utility.Math3D.Matrix4f;
import utility.Math3D.Vector3f;
import utility.TextureResourceReader;

/**
 * Created by sameer on 1/7/2018.
 */

public class SurfaceRenderer implements GLSurfaceView.Renderer {
    private  FPSCounter fps;
    private  Loader loader;
    private StaticShader staticShader;
    private RawModel rawModel;
    private ModelTexture modelTexture;
    private TexturedModel model;
    private Entity entity;

    public void setContext(Context context) {
        this.context = context;
    }

    private Context context;
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
        fps = new FPSCounter();

        float[] vertices = {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f
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
        staticShader = new StaticShader(context);
        loader=new Loader();
        rawModel=loader.loadtoVAO(vertices,indices,textureCoords);
        modelTexture =new ModelTexture(TextureResourceReader.loadTexturue(context,"tex.png"));
        model =new TexturedModel(rawModel,modelTexture);
        entity= new Entity(model,new Vector3f(-1.0f,0.0f,0.0f),0,0,0,1);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        staticShader.runProgram();
        GLES30.glBindVertexArray(model.getRawModel().getVaoID());
        GLES20.glEnableVertexAttribArray(0);
        GLES20.glEnableVertexAttribArray(1);

        Matrix4f modelMatrix = Matrix4f.createTransformationMatrix(entity.getPosition(),entity.getScale(), entity.getRx(),entity.getRy(), entity.getRz());
        staticShader.loadMatrix(modelMatrix);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,model.getModelTexture().getTextureId());
        GLES20.glDrawElements(GLES20.GL_TRIANGLES,model.getRawModel().getVertexCount(),GLES20.GL_UNSIGNED_INT,0);
        GLES20.glDisableVertexAttribArray(0);
        GLES20.glDisableVertexAttribArray(1);
        GLES30.glBindVertexArray(0);
        staticShader.stopProgram();
        fps.logFrame();

    }
}
