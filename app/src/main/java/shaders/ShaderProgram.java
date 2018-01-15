package shaders;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.util.Log;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.microedition.khronos.opengles.GL11;

import utility.TextResourceReader;

import static android.content.ContentValues.TAG;

/**
 * Created by sameer on 1/7/2018.
 */

public abstract class ShaderProgram {
    private int programID;
    private int vertexShaderID;
    private  int fragmentShaderID;

    public ShaderProgram(Context context, String vsResourceID, String fsResourceId){
        int[] linked = new int[1];

        this.vertexShaderID = loadShader(vsResourceID,GLES20.GL_VERTEX_SHADER,context);
        this.fragmentShaderID = loadShader(fsResourceId,GLES20.GL_FRAGMENT_SHADER,context);
        this.programID = GLES20.glCreateProgram();
        GLES20.glAttachShader(programID,vertexShaderID);
        GLES20.glAttachShader(programID,fragmentShaderID);
        bindAttributes();
        GLES20.glLinkProgram(programID);
        GLES30.glGetProgramiv ( programID, GLES30.GL_LINK_STATUS, linked, 0 );

        if ( linked[0] == 0 )
        {
            Log.e ( TAG, "Error linking program:" );
            Log.e ( TAG, GLES30.glGetProgramInfoLog ( programID ) );
            GLES30.glDeleteProgram ( programID );
            return;
        }
        GLES20.glValidateProgram(programID);

    }
     public void runProgram(){
         GLES20.glUseProgram(programID);

     }
    public void stopProgram(){
        GLES20.glUseProgram(0);
    }
    public void cleanUp()
    {
        stopProgram();
        GLES20.glDetachShader(programID,vertexShaderID);
        GLES20.glDetachShader(programID,fragmentShaderID);
        GLES20.glDeleteShader(vertexShaderID);
        GLES20.glDeleteShader(fragmentShaderID);
        GLES20.glDeleteProgram(programID);
    }
    protected abstract  void bindAttributes();

    protected void bindAttribute(int attribute, String variableName){
        GLES20.glBindAttribLocation(programID,attribute,variableName);

    }

    private static int loadShader(String resorceId, int type, Context context){
        String shaderSource = TextResourceReader.readTextFileFromResource(context,resorceId) ;
        int shaderID;
        int[] compiled = new int[1];

        shaderID = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shaderID, shaderSource);
        GLES20.glCompileShader(shaderID);


        GLES30.glGetShaderiv ( shaderID, GLES30.GL_COMPILE_STATUS, compiled, 0 );

        if ( compiled[0] == 0 )
        {
            Log.e ( TAG, GLES30.glGetShaderInfoLog ( shaderID ) );
            GLES30.glDeleteShader ( shaderID );
            return 0;
        }

        return shaderID;
    }
}
