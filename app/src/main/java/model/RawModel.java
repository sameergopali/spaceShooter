package model;

/**
 * Created by sameer on 1/7/2018.
 */

public class RawModel {
      private int vaoID;
      private int vertexCount;

      public RawModel(int vaoID, int vertexCount){
          this.vaoID = vaoID;
          this.vertexCount = vertexCount;
      }

    public int getVertexCount() {
        return vertexCount;
    }

    public int getVaoID() {
        return vaoID;
    }
}
