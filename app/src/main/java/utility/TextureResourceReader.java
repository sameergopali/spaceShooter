package utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL;

import static android.content.ContentValues.TAG;

/**
 * Created by sameer on 1/15/2018.
 */

public class TextureResourceReader {
    public  static int loadTexturue(Context context, String filename){
        int[] texturehandle = new int[1];
        GLES20.glGenTextures(1,texturehandle,0);
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            is = context.getAssets().open(filename);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            bitmap = BitmapFactory.decodeStream(is, null, options);
        }
        catch (final IOException e)
        {
            bitmap = null;
            Log.e(TAG, "FAILED TO get getBitmapFromAsset: " + e.getMessage());
        }
        finally
        {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignored) {
                }
            }
        }
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,texturehandle[0]);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D,0,bitmap,0);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
        bitmap.recycle();
        return texturehandle[0];
}
}
