package shaders;

import android.content.Context;

/**
 * Created by VRlab on 1/17/2018.
 */

public class BgShader extends ShaderProgram {
    private static final String VERTEX_FILE="bgVertex.glsl";
    private static final String FRAG_FILE= "bgFrag.glsl";

    public BgShader(Context context, String vsResourceID, String fsResourceId) {
        super(context, vsResourceID, fsResourceId);
    }

    @Override
    protected void getAllUniforms() {

    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0,"position");
        super.bindAttribute(1,"texCoord");
    }
}
