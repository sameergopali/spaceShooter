package texture;

/**
 * Created by VRlab on 1/19/2018.
 */

public class TerrainTexturePack {

    private TerrainTexture background;
    private TerrainTexture rTexture;
    private TerrainTexture gTexture;
    private TerrainTexture bTexture;


    public TerrainTexturePack(TerrainTexture background, TerrainTexture rTexture, TerrainTexture gTexture, TerrainTexture bTexture) {
        this.background = background;
        this.rTexture = rTexture;
        this.gTexture = gTexture;
        this.bTexture = bTexture;
    }

    public TerrainTexture getBackground() {
        return background;
    }

    public TerrainTexture getrTexture() {
        return rTexture;
    }

    public TerrainTexture getgTexture() {
        return gTexture;
    }

    public TerrainTexture getbTexture() {
        return bTexture;
    }
}
