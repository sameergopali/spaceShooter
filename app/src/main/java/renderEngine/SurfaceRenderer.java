package renderEngine;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import model.RawModel;
import shaders.StaticShader;
import utility.FPSCounter;

/**
 * Created by sameer on 1/7/2018.
 */

public class SurfaceRenderer implements GLSurfaceView.Renderer {
    private  FPSCounter fps;
    private RawModel model;
    private  Loader loader;
    private StaticShader staticShader;

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
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
                -0.5f, 0.5f, 0f
        };
        staticShader = new StaticShader(context);
        loader=new Loader();
        model=loader.loadtoVAO(vertices);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        staticShader.runProgram();
        GLES30.glBindVertexArray(model.getVaoID());
        GLES20.glEnableVertexAttribArray(0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,model.getVertexCount());
        GLES20.glDisableVertexAttribArray(0);
        GLES30.glBindVertexArray(0);
        staticShader.stopProgram();
        fps.logFrame();

    }
}
