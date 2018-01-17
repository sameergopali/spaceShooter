package model;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import renderEngine.Loader;
import utility.Math3D.Vector2f;
import utility.Math3D.Vector3f;

/**
 * Created by VRlab on 1/17/2018.
 */

public class OBJLoader {
    public static RawModel loadObjModel(Context context, String fileName, Loader loader) {



        try {
            InputStream inputStream = context.getAssets().open(fileName);
            InputStreamReader inputStreamReader =new InputStreamReader(inputStream);

            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line;
            List<Vector3f> vertices = new ArrayList<Vector3f>();
            List<Vector2f> textures = new ArrayList<Vector2f>();
            List<Vector3f> normals = new ArrayList<Vector3f>();
            List<Integer> indices = new ArrayList<Integer>();
            float[] verticesArray = null;
            float[] normalsArray = null;
            float[] textureArray = null;
            int[] indicesArray = null;


            while (true) {
                line = reader.readLine();
                String[] currentLine = line.split(" ");
                if (line.startsWith("v ")) {
                    Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]),
                            Float.parseFloat(currentLine[3]));
                    vertices.add(vertex);
                } else if (line.startsWith("vt ")) {
                    Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
                    textures.add(texture);
                } else if (line.startsWith("vn ")) {
                    Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]),
                            Float.parseFloat(currentLine[3]));
                    normals.add(normal);
                } else if (line.startsWith("f ")) {
                    textureArray = new float[vertices.size() * 2];
                    normalsArray = new float[vertices.size() * 3];
                    break;
                }
            }
            while (line != null) {
                if (!line.startsWith("f ")) {
                    line = reader.readLine();
                    continue;
                }
                String[] currentLine = line.split(" ");
                String[] vertex1 = currentLine[1].split("/");
                String[] vertex2 = currentLine[2].split("/");
                String[] vertex3 = currentLine[3].split("/");

                processVertex(vertex1, indices, textures, normals, textureArray, normalsArray);
                processVertex(vertex2, indices, textures, normals, textureArray, normalsArray);
                processVertex(vertex3, indices, textures, normals, textureArray, normalsArray);
                line = reader.readLine();
            }



            verticesArray = new float[vertices.size() * 3];
            indicesArray = new int[indices.size()];

            int vertexPointer = 0;
            for (Vector3f vertex : vertices) {
                verticesArray[vertexPointer++] = vertex.x;
                verticesArray[vertexPointer++] = vertex.y;
                verticesArray[vertexPointer++] = vertex.z;
            }
            for (int i = 0; i < indices.size(); i++) {
                indicesArray[i] = indices.get(i);
            }
            return loader.loadtoVAO(verticesArray, indicesArray, textureArray);


        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not open resource: " + fileName, e);
        } catch (Resources.NotFoundException nfe) {
            throw new RuntimeException("Resource not found: " + fileName, nfe);
        }


    }

    private static void processVertex(String[] vertexData, List<Integer> indices, List<Vector2f> textures,
                                      List<Vector3f> normals, float[] textureArray, float[] normalsArray) {
        int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
        indices.add(currentVertexPointer);
        Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1]) - 1);
        textureArray[currentVertexPointer * 2] = currentTex.x;
        textureArray[currentVertexPointer * 2 + 1] = 1 - currentTex.y;
        Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2]) - 1);
        normalsArray[currentVertexPointer * 3] = currentNorm.x;
        normalsArray[currentVertexPointer * 3 + 1] = currentNorm.y;
        normalsArray[currentVertexPointer * 3 + 2] = currentNorm.z;
    }
}







