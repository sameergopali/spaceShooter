package entity;

import utility.Math3D.Vector;
import utility.Math3D.Vector3f;

/**
 * Created by VRlab on 1/16/2018.
 */

public class ViewCamera {

    private Vector3f position = new Vector3f(0,5,0);
    private float pitch =0;
    private float yaw =0;
    private float roll =0;

    public ViewCamera(){

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
        position.z -=0.05f;

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
