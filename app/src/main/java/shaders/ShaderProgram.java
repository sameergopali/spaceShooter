package shaders;

import android.content.Context;
import android.opengl.GLES20;

import com.example.sameer.spaceshooter.R;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.microedition.khronos.opengles.GL11;

import utility.TextResourceReader;

/**
 * Created by sameer on 1/7/2018.
 */

public abstract class ShaderProgram {
    private int programID;
    private int vertexShaderID;
    private  int fragmentShaderID;

    public ShaderProgram(Context context, int vsResourceID, int fsResourceId){
        this.vertexShaderID = loadShader(vsResourceID,GLES20.GL_VERTEX_SHADER,context);
        this.fragmentShaderID = loadShader(fsResourceId,GLES20.GL_FRAGMENT_SHADER,context);
        this.programID = GLES20.glCreateProgram();
        GLES20.glAttachShader(programID,vertexShaderID);
        GLES20.glAttachShader(programID,fragmentShaderID);
        bindAttribute();
        GLES20.glLinkProgram(programID);
        GLES20.glValidateProgram(programID);
        //GLES20.glUseProgram(programID);
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
    protected abstract  void bindAttribute();

    protected void bindattribute(int attribute, String variableName){
        GLES20.glBindAttribLocation(programID,attribute,variableName);

    }

    private static int loadShader(int resorceId, int type, Context context){
        String shaderSource = TextResourceReader.readTextFileFromResource(context,resorceId) ;
        int shaderID;
        shaderID = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shaderID, shaderSource);
        GLES20.glCompileShader(shaderID);
/* // TODO: 1/7/2018 Debug Shader message */
        return shaderID;
    }
}
