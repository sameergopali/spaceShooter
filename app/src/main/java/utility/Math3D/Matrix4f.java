package utility.Math3D;

import android.opengl.Matrix;

import entity.ViewCamera;

/**
 * Created by sameer on 1/15/2018.
 */

public class Matrix4f {
    private float[]  array;
    public Matrix4f(){
        this.array= new float[16];
        Matrix.setIdentityM(this.array,0);
    }

    public float[] getArray() {
        return array;
    }

    public Matrix4f(float[] array){
        this.array= array;

    }

    public static void rotate(float angle, Vector3f axis, Matrix4f source){
          Matrix.rotateM(source.getArray(),0,angle,axis.x,axis.y,axis.z);
    }
    public static void translate(Vector3f translation, Matrix4f source){
        Matrix.translateM(source.getArray(),0,translation.x,translation.y,translation.z);

    }
    public static void scale(Vector3f scale, Matrix4f source){
        Matrix.scaleM(source.getArray(),0,scale.x,scale.y,scale.z);
    }
    public static Matrix4f createTransformationMatrix(Vector3f traslation, float scale,float rx, float ry, float rz){;
        Matrix4f m = new Matrix4f();
        Matrix4f.translate(traslation,m);

        Matrix4f.rotate(rx,new Vector3f(1,0,0),m);
        Matrix4f.rotate(ry,new Vector3f(0,1,0),m);
        Matrix4f.rotate(rz,new Vector3f(0,0,1),m);
        Matrix4f.scale(new Vector3f(scale,scale,scale),m);
        return m;

    }
    public static Matrix4f createViewMatrix(ViewCamera camera){;
        Matrix4f m = new Matrix4f();
        Vector3f cameraPos = camera.getPosition();
        Vector3f negativeCameraPos= new Vector3f(-cameraPos.x,-cameraPos.y, -cameraPos.z);
        Matrix4f.translate(negativeCameraPos,m);
        Matrix4f.rotate(camera.getPitch(),new Vector3f(1,0,0),m);
        Matrix4f.rotate(camera.getYaw(),new Vector3f(0,1,0),m);

        return m;

    }

    public static Matrix4f createProjectionMatrix(int width, int height){
         float FOV = 70.0f;
         float NEAR_PLANE = 0.1f;
         float FAR_PLANE = 100f;
        float [] m = new float[16];

        float aspectRatio = (float) width / (float) height;
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV/2f)))*aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;
        m[0] =x_scale;
        m[1] = 0f;
        m[2] = 0f;
        m[3] = 0f;
        m[4] = 0f;
        m[5] = y_scale;
        m[6] = 0f;
        m[7] = 0f;
        m[8] = 0f;
        m[9] = 0f;
        m[10] = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        m[11] = -1f;

        m[12] = 0f;
        m[13] = 0f;
        m[14] = -((2f * FAR_PLANE * NEAR_PLANE) / (frustum_length));
        m[15] = 0f;


        return  new Matrix4f(m);
        //projectionMatrix = new Matrix4f();
    }

}

