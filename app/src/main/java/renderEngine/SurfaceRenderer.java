package renderEngine;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import entity.ViewCamera;
import entity.Entity;
import entity.Light;
import model.BgModelLoader;
import model.OBJLoader;
import model.RawModel;
import model.TexturedModel;
import shaders.BgShader;
import shaders.StaticShader;
import shaders.TerrainShader;
import skyBox.SkyboxRenderer;
import terrain.Terrain;
import texture.ModelTexture;
import texture.TerrainTexture;
import texture.TerrainTexturePack;
import utility.FPSCounter;
import utility.Math3D.Matrix4f;
import utility.Math3D.Vector3f;
import utility.TextureResourceReader;

/**
 * Created by sameer on 1/7/2018.
 */

public class SurfaceRenderer implements GLSurfaceView.Renderer,SurfaceTexture.OnFrameAvailableListener {

    private static final float RED =0.5f;
    private static  final float GREEN = 0.5f;
    private  static  final float BLUE =0.5f;


    private  FPSCounter fps;
    private  Loader loader;
    private StaticShader staticShader;
    private RawModel rawModel;
    private ModelTexture modelTexture;
    private TexturedModel model;
    private TexturedModel bgModel;
    private Entity entity;
    private EntityRenderer renderer;
    private static int width;
    private static int height;
    private ViewCamera camera;
    private Light light;
    private BgShader bgShader;
    private BgRenderer bgRenderer;

    private Map<TexturedModel,List<Entity>> entities = new HashMap<TexturedModel,List<Entity>>();
    private ModelTexture bgModelTexture;


    private Context context;

    private SurfaceTexture mSTexture;
    private android.hardware.Camera mCamera;

    private boolean mUpdateST = false;

    private TerrainShader terrainShader ;
    private  TerrainRenderer terrainRenderer;
    private  List<Terrain> terrains = new ArrayList<Terrain>();
    private Terrain terrain ,terrain1;
    private SkyboxRenderer skyboxRenderer;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(RED, GREEN, BLUE, 1.0f);

        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glCullFace(GLES20.GL_BACK);
        fps = new FPSCounter();


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
        bgShader = new BgShader(context);
        staticShader = new StaticShader(context);
        terrainShader = new TerrainShader(context);

        loader=new Loader();
        rawModel= OBJLoader.loadObjModel(context ,"dragon.obj",loader);
        bgModelTexture =new ModelTexture(TextureResourceReader.loadBgTexture());
        bgModel = new TexturedModel(BgModelLoader.createBgModel(loader),bgModelTexture );
        modelTexture =new ModelTexture(TextureResourceReader.loadTexture(context,"mud.png"));
        modelTexture.setReflectivity(3);
        modelTexture.setShineDamper(10);
        model =new TexturedModel(rawModel,modelTexture);
        entity= new Entity(model,new Vector3f(0.0f,0.0f,-20.0f),0,0,0,1);
        camera = new ViewCamera(entity);
        light = new Light(new Vector3f(0,100,5),new Vector3f(1.0f,1.0f,1.0f));
        bgRenderer = new BgRenderer(bgShader);




        TerrainTexture background = new TerrainTexture(TextureResourceReader.loadTexture(context,"terrain.png"));
        TerrainTexture rTexture = new TerrainTexture(TextureResourceReader.loadTexture(context,"mud.png"));
        TerrainTexture gTexture = new TerrainTexture(TextureResourceReader.loadTexture(context,"grassFlowers.png"));
        TerrainTexture bTexture = new TerrainTexture(TextureResourceReader.loadTexture(context,"path.png"));

        TerrainTexture blendMap = new TerrainTexture(TextureResourceReader.loadTexture(context,"blendMap.png"));

        TerrainTexturePack terrainTexturePack = new TerrainTexturePack(background,rTexture,gTexture,bTexture);

        terrain =  new Terrain(0,-1, loader, terrainTexturePack,blendMap );
        terrain1 = new Terrain(-1,-1, loader, terrainTexturePack,blendMap);




        mSTexture = new SurfaceTexture ( bgModelTexture.getTextureId() );
        mSTexture.setOnFrameAvailableListener(this);
        mCamera = android.hardware.Camera.open();
        try {
            mCamera.setPreviewTexture(mSTexture);
        } catch ( IOException ioe ) {
        }


    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        Matrix4f projectionMatrix = Matrix4f.createProjectionMatrix(width,height);
        renderer = new EntityRenderer(staticShader,projectionMatrix );
        terrainRenderer= new TerrainRenderer(terrainShader,projectionMatrix);
        skyboxRenderer = new SkyboxRenderer(context,loader,projectionMatrix);
       // android.hardware.Camera.Parameters param = mCamera.getParameters();
       // param.setRotation(90);
       // mCamera.setParameters ( param );
        mCamera.startPreview();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
       //entity.increaseRotation(0.0f,0.1f,0.0f);
        prepare();
        /*synchronized(this) {
            if ( mUpdateST ) {
                mSTexture.updateTexImage();
                mUpdateST = false;
            }}
        bgShader.runProgram();
        bgRenderer.render(bgModel);
        bgShader.stopProgram();*/
        //GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT);
        staticShader.runProgram();
        staticShader.loadSkyColor(RED,GREEN,BLUE);
        staticShader.loadLight(light);
        staticShader.loadViewMatrix(camera);
        processEntity(entity);
        renderer.render(entities);
        staticShader.stopProgram();


        terrainShader.runProgram();
        terrainShader.loadLight(light);
        terrainShader.loadViewMatrix(camera);
        terrainShader.loadSkyColor(RED,GREEN,BLUE);
        processTerrain(terrain);
        processTerrain(terrain1);
        terrainRenderer.render(terrains);
        terrainShader.stopProgram();
         skyboxRenderer.render(camera,RED,GREEN,BLUE);

        entities.clear();
        terrains.clear();
        fps.logFrame();
        fps.logTime();


    }

    private  void processTerrain( Terrain terrain){
        terrains.add(terrain);
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

    @Override
    public  synchronized  void onFrameAvailable(SurfaceTexture surfaceTexture) {
        mUpdateST = true;
    }
    public ViewCamera getCamera() {
        return camera;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
