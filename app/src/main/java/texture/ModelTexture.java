package texture;

/**
 * Created by sameer on 1/15/2018.
 */

public class ModelTexture {
    private int textureId;

    public float getShineDamper() {
        return shineDamper;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }

    private  float shineDamper =1;
    private float reflectivity =0;


    public int getTextureId() {
        return textureId;
    }
    public ModelTexture(int textureId){
        this.textureId=textureId;
    }



}
