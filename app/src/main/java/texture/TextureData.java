package texture;

import java.nio.ByteBuffer;

import utility.TextureResourceReader;

/**
 * Created by VRlab on 1/22/2018.
 */

public class TextureData {
        private int width;
        private int height;
        private ByteBuffer buffer;

        public int getWidth() {
                return width;
        }

        public int getHeight() {
                return height;
        }

        public ByteBuffer getBuffer() {
                return buffer;
        }

        public TextureData(ByteBuffer buffer, int width, int height){
                this.buffer = buffer;
                this.width = width;
                this.height = height;

        }
}
