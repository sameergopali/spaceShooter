package renderEngine;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import entity.Camera;
import entity.Entity;
import model.OBJLoader;
import model.RawModel;
import model.TexturedModel;
import shaders.StaticShader;
import texture.ModelTexture;
import utility.FPSCounter;
import utility.Math3D.Matrix4f;
import utility.Math3D.Vector3f;
import utility.TextureResourceReader;

import static android.content.ContentValues.TAG;

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
    private Renderer renderer;
    private static int width;
    private static int height;
    private Camera camera;

    public Camera getCamera() {
        return camera;
    }



    public void setContext(Context context) {
        this.context = context;
    }

    private Context context;





    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        fps = new FPSCounter();


        float[] vertices = {
                -0.5f,0.5f,-0.5f,
                -0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                0.5f,0.5f,-0.5f,

                -0.5f,0.5f,0.5f,
                -0.5f,-0.5f,0.5f,
                0.5f,-0.5f,0.5f,
                0.5f,0.5f,0.5f,

                0.5f,0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,0.5f,
                0.5f,0.5f,0.5f,

                -0.5f,0.5f,-0.5f,
                -0.5f,-0.5f,-0.5f,
                -0.5f,-0.5f,0.5f,
                -0.5f,0.5f,0.5f,

                -0.5f,0.5f,0.5f,
                -0.5f,0.5f,-0.5f,
                0.5f,0.5f,-0.5f,
                0.5f,0.5f,0.5f,

                -0.5f,-0.5f,0.5f,
                -0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,0.5f

        };

        float[] textureCoords = {

                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0


        };

        int[] indices = {
                0,1,3,
                3,1,2,
                4,5,7,
                7,5,6,
                8,9,11,
                11,9,10,
                12,13,15,
                15,13,14,
                16,17,19,
                19,17,18,
                20,21,23,
                23,21,22

        };

        staticShader = new StaticShader(context);
        loader=new Loader();
        rawModel= OBJLoader.loadObjModel(context ,"stall.obj",loader);
        modelTexture =new ModelTexture(TextureResourceReader.loadTexturue(context,"stallTexture.png"));
        model =new TexturedModel(rawModel,modelTexture);
        entity= new Entity(model,new Vector3f(0.0f,0.0f,-1.0f),0,0,0,1);
        renderer = new Renderer();
        camera = new Camera();

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        renderer.setHeight(height);
        renderer.setWidth(width);
        renderer.setProjectionMatrix(staticShader);




    }

    @Override
    public void onDrawFrame(GL10 gl) {
        entity.increaseRotation(0.5f,0.0f,0.0f);
        renderer.prepare();
        staticShader.runProgram();
        staticShader.loadViewMatrix(camera);
        renderer.render(entity,staticShader);
        staticShader.stopProgram();
        fps.logFrame();

    }
}
