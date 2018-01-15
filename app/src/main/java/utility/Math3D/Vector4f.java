package utility.Math3D;

/**
 * Created by sameer on 1/15/2018.
 */

public class Vector4f extends Vector {
    public float x,y,z,w;

    public Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    public  Vector4f(float[ ] arr){
        this.x = arr[0];
        this.y= arr[1];
        this.z =arr[2];
        this.w = arr[3];


    }
    @Override
    public float[] getarray() {
        return new float[]{
                x,y,z,w
        };
    }


}
