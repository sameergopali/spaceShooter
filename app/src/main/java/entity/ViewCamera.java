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
    private float mPreviousX=0 ;
    private float mPreviousY=0;

    private float distancePlayer = 25f;
    private float angleAroundPlayer = 0;
    private Entity player;



    public ViewCamera(Entity player){
        this.player = player;
    }


    public void handleTouchDrag(float normalizedX, float normalizedY){
 /* TODO
  implement touchDrag;
  po
 * */

        float dx = normalizedX - mPreviousX;
        float dy = normalizedY - mPreviousY;
        mPreviousX = normalizedX;
        mPreviousY = normalizedY;
        /*
        if (dx >0.010){
            yaw++;
        }
        else if(dx<-0.01){
            yaw--;
        }
        if (dy >0.01){
            pitch++;
        }
        else if(dy <-0.01){
            pitch--;
        }*/
        calculateAngleAroundPlayer(dx);
        calculatePitch(dy);
        float h = calculateHorizontalDistance();
        float  v =calculateVerticalDistance();
        calculateCameraPosition(h,v);

    }
    public void handleTouchPress(float normalizedX, float normalizedY){
        /* TODO */




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






    private void calculateZoom(){

    }
     private void  calculateAngleAroundPlayer(float dx){
         float angleChange = dx;
         angleAroundPlayer -= angleChange;
     }
     private void calculatePitch(float dy){
         float angleChange = dy;
         pitch -= angleChange;

     }
     private  float calculateHorizontalDistance(){
        return (float)(distancePlayer * Math.cos(pitch));
    }
    private  float calculateVerticalDistance(){
        return (float)(distancePlayer * Math.sin(pitch));
    }

    private void calculateCameraPosition(float hd, float vd){
        float theta = player.getRy()+ angleAroundPlayer;
        float offsetx = (float)(hd*Math.sin(theta));
        float offsetz = (float)(hd*Math.cos(theta));
        position.y = player.getPosition().y +vd;
      /*  position.x= player.getPosition().x - offsetx;
        position.z= player.getPosition().z - offsetz;
        yaw =3.14f -(player.getRy()+angleAroundPlayer);*/

    }


}
