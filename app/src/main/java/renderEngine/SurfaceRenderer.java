package renderEngine;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import entity.Camera;
import entity.Entity;
import entity.Light;
import model.OBJLoader;
import model.RawModel;
import model.TexturedModel;
import shaders.BgShader;
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
    private EntityRenderer renderer;
    private static int width;
    private static int height;
    private Camera camera;
    private Light light;

    private BgShader bgShader;
    private BgRenderer bgRenderer;

    private Map<TexturedModel,List<Entity>> entities = new HashMap<TexturedModel,List<Entity>>();

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
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glCullFace(GLES20.GL_BACK);
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
        modelTexture.setReflectivity(1);
        modelTexture.setShineDamper(10);
        model =new TexturedModel(rawModel,modelTexture);
        entity= new Entity(model,new Vector3f(0.0f,-10.0f,-10.0f),0,0,0,1);
        camera = new Camera();
        light = new Light(new Vector3f(0,100,5),new Vector3f(0.0f,1.0f,1.0f));
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        renderer = new EntityRenderer(staticShader, Matrix4f.createProjectionMatrix(width,height));





    }

    @Override
    public void onDrawFrame(GL10 gl) {
       // entity.increaseRotation(0.0f,0.1f,0.0f);
        prepare();
        staticShader.runProgram();
        staticShader.loadLight(light);
        staticShader.loadViewMatrix(camera);
        processEntity(entity);

        renderer.render(entities);
        staticShader.stopProgram();
        entities.clear();
        fps.logFrame();

    }
    public void processEntity(Entity entity){
        TexturedModel entityModel = entity.getTexturedModel();
        List<Entity> batch = entities.get(entityModel);
        if(batch!=null){
            batch.add(entity);
        }
        else {
            List<Entity> newBatch = new ArrayList<Entity>();
            newBatch.add(entity);
            entities.put(entityModel,newBatch);
        }
    }
    private void prepare(){
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT|GLES20.GL_DEPTH_BUFFER_BIT);
    }
}
