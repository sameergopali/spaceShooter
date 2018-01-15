package entity;

import model.TexturedModel;
import utility.Math3D.Vector3f;

/**
 * Created by sameer on 1/15/2018.
 */

public class Entity {
    private TexturedModel texturedModel;
    private Vector3f position;
    private float rx,ry,rz;
    private float scale;

    public Entity(TexturedModel texturedModel, Vector3f position, float rx, float ry, float rz, float scale) {
        this.texturedModel = texturedModel;
        this.position = position;
        this.rx = rx;
        this.ry = ry;
        this.rz = rz;
        this.scale = scale;
    }

    public  void increasePosition(float dx, float dy, float dz){
        position.x +=dx;
        position.y +=dy;
        position.z += dz;
    }

    public  void increaseRotation(float drx, float dry, float drz){
        rx += drx;
        ry += dry;
        rz += drz;
    }

    public TexturedModel getTexturedModel() {
        return texturedModel;
    }



    public Vector3f getPosition() {
        return position;
    }

    public float getRx() {
        return rx;
    }

    public float getRy() {
        return ry;
    }

    public float getRz() {
        return rz;
    }

    public void setTexturedModel(TexturedModel texturedModel) {
        this.texturedModel = texturedModel;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setRx(float rx) {
        this.rx = rx;
    }

    public void setRy(float ry) {
        this.ry = ry;
    }

    public void setRz(float rz) {
        this.rz = rz;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getScale() {
        return scale;

    }

}
