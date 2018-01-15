package utility.Math3D;

/**
 * Created by sameer on 1/15/2018.
 */

public class Vector2f extends Vector {
public float x,y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public  Vector2f(float[ ] arr){
        this.x= arr[0];
        this.y= arr[1];


    }
    @Override
    public float[] getarray() {
        return new float[]{
          x,y
        };
    }
}

