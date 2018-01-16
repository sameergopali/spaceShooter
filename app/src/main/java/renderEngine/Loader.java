package renderEngine;

import android.content.Context;
import android.opengl.GLES10;
import android.opengl.GLES20;
import android.opengl.GLES30;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import model.RawModel;
import model.TexturedModel;
import texture.ModelTexture;
import utility.TextureResourceReader;

/**
 * Created by sameer on 1/7/2018.
 */

public class Loader {
    private static final int BYTES_PER_INT = 2;
    private  final int  BYTES_PER_FLOAT =4;


    public RawModel loadtoVAO(float[] positions,int [] indicies, float[] textureCoords){
        int vaoID =createVAO();
        bindIndexBuffer(indicies);

        storeDatainAttribule(0,3,positions);
        storeDatainAttribule(1,2,textureCoords);
        unbindVao();
        return new RawModel( vaoID, indicies.length);
    }

    private void bindIndexBuffer(int[] indicies) {
        int[] vbo = new int[1];
        GLES20.glGenBuffers(1,vbo,0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER,vbo[0]);
        IntBuffer data = createIntBuffer(indicies);
        GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER,data.capacity()*BYTES_PER_FLOAT,data,GLES20.GL_STATIC_DRAW);
    }

    private IntBuffer createIntBuffer(int[] indicies) {
        IntBuffer buffer =ByteBuffer
                .allocateDirect(indicies.length*BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asIntBuffer()
                .put(indicies);
        buffer.position(0);
        return  buffer;
    }

    private void unbindVao() {
        GLES30.glBindVertexArray(0);
    }


    private void storeDatainAttribule(int attributeNumber, int coordPerVertex, float[] positions) {
        int[] vbo = new  int[1];
        GLES20.glGenBuffers(vbo.length,vbo,0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,vbo[0]);
        FloatBuffer data= createFloatBuffer(positions);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER,data.capacity()*BYTES_PER_FLOAT,data,GLES20.GL_STATIC_DRAW);
        GLES20.glEnableVertexAttribArray(0);
        GLES20.glVertexAttribPointer(attributeNumber,coordPerVertex,GLES20.GL_FLOAT,false,0,0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,0);

    }

    private FloatBuffer createFloatBuffer(float[] positions) {
        // Transfer data to native memory.
        FloatBuffer vertexArray = ByteBuffer
                .allocateDirect(positions.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(positions);
        vertexArray.position(0);
        return  vertexArray;
    }

    private int createVAO() {
        int[] vao= new int[1];
        GLES30.glGenVertexArrays(vao.length,vao,0);
        GLES30.glBindVertexArray(vao[0]);
        return  vao[0];
    }



}
