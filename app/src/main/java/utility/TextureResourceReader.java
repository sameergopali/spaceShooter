package utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES11Ext;
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


    private static Bitmap getBitmap(Context context, String filename){
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            is = context.getAssets().open(filename);

        }
        catch (final IOException e)
        {
            bitmap = null;
            Log.e(TAG, "FAILED TO get getBitmapFromAsset: " + e.getMessage());
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        bitmap = BitmapFactory.decodeStream(is, null, options);
        return bitmap;

    }

    public  static int loadTexture(Context context, String filename){
        int[] texturehandle = new int[1];
        GLES20.glGenTextures(1,texturehandle,0);
       Bitmap bitmap = getBitmap(context,filename);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,texturehandle[0]);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D,0,bitmap,0);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
        bitmap.recycle();
        return texturehandle[0];

}

public static int loadBgTexture(){
    int [] hTex = new int[1];
    GLES20.glGenTextures ( 1, hTex, 0 );
    GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, hTex[0]);
    GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
    GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
    GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
    GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

    return  hTex[0];
}
public static int loadCubeMap(Context context,String[] textureFiles){
    int[] texturehandle = new int[1];
    GLES20.glGenTextures(1,texturehandle,0);
    GLES20.glBindTexture(GLES20.GL_TEXTURE_CUBE_MAP, texturehandle[0]);


    for(int i =0; i< textureFiles.length; i++){
        Bitmap bitmap=getBitmap(context,textureFiles[i]);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_CUBE_MAP_POSITIVE_X+i,0,bitmap,0);
        bitmap.recycle();

    }
    GLES20.glTexParameteri(GLES20.GL_TEXTURE_CUBE_MAP,GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_LINEAR);
    GLES20.glTexParameteri(GLES20.GL_TEXTURE_CUBE_MAP,GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
     //GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,0);

     return texturehandle[0];
}

}
