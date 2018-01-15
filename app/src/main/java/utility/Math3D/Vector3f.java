package utility.Math3D;

/**
 * Created by sameer on 1/15/2018.
 */

public class Vector3f extends Vector {
    public float x,y,z;

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public  Vector3f(float[ ] arr){
        this.x= arr[0];
        this.y= arr[1];
        this.z =arr[2];


    }
    @Override
    public float[] getarray() {
        return new float[]{
                x,y,z
        };
    }
}
