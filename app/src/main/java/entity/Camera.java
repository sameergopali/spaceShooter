package entity;

import utility.Math3D.Vector;
import utility.Math3D.Vector3f;

/**
 * Created by VRlab on 1/16/2018.
 */

public class Camera {

    private Vector3f position = new Vector3f(0,0,0);
    private float pitch =0;
    private float yaw =0;
    private float roll =0;

    public Camera(){

    }

    public void handleTouchDrag(float normalizedX, float normalizedY){
 /* TODO
  implement touchDrag;
 * */
    }
    public void handleTouchPress(float normalizedX, float normalizedY){
        /* TodO *
        implement touchPress/
         */
        position.z ++;

    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }
}
