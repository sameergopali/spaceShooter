package renderEngine;

import android.opengl.GLES20;
import android.opengl.GLES30;

import java.util.TreeMap;

import model.TexturedModel;
import shaders.BgShader;
import texture.ModelTexture;

/**
 * Created by VRlab on 1/17/2018.
 */

public class BgRenderer {



    public void render(TexturedModel texturedModel){
        GLES30.glBindVertexArray(texturedModel.getRawModel().getVaoID());
        GLES20.glEnableVertexAttribArray(0);
        GLES20.glEnableVertexAttribArray(1);
        ModelTexture texture = texturedModel.getModelTexture();
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,texturedModel.getModelTexture().getTextureId());

        GLES20.glDrawElements(GLES20.GL_TRIANGLES,  texturedModel.getRawModel().getVertexCount(), GLES20.GL_UNSIGNED_INT, 0);

        GLES20.glDisableVertexAttribArray(0);
        GLES20.glDisableVertexAttribArray(1);
        GLES30.glBindVertexArray(0);

    }


}
