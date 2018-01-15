package utility.Math3D;

import android.opengl.Matrix;

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
        Matrix4f.rotate(rx,new Vector3f(1,0,0),m);
        Matrix4f.rotate(ry,new Vector3f(0,1,0),m);
        Matrix4f.rotate(rz,new Vector3f(0,0,1),m);
        Matrix4f.translate(traslation,m);
        Matrix4f.scale(new Vector3f(scale,scale,scale),m);
        return m;

    }
}
