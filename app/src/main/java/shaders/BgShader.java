package shaders;

import android.content.Context;

/**
 * Created by VRlab on 1/17/2018.
 */

public class BgShader extends ShaderProgram {
    private static final String VERTEX_FILE="bgVertex.glsl";
    private static final String FRAG_FILE= "bgFrag.glsl";

    private int sTexture;

    public BgShader(Context context) {
        super(context, VERTEX_FILE, FRAG_FILE);
    }

    @Override
    protected void getAllUniforms() {
       sTexture= super.getUniformLocation("sTexture");

    }
    public void lodadUniformi(){
        super.loadInt(sTexture,0);
    }
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0,"position");
        super.bindAttribute(1,"texCoord");
    }
}
