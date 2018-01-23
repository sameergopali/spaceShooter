package utility;

import android.util.Log;

/**
 * Created by sameer on 1/7/2018.
 */

public class FPSCounter {

    long startTime ;
    long lastTime;
    int frames;
    private static float delta;
    public FPSCounter(){
        startTime= System.nanoTime();
        lastTime = System.nanoTime();
        frames = 0;
    }

    public static float getTime(){
        return  delta;
    }
    public void logFrame() {
        frames++;
        if(System.nanoTime() - startTime >= 1000000000) {
            Log.d("FPSCounter", "fps: " + frames);
            frames = 0;
            startTime = System.nanoTime();
        }
    }
    public   void  logTime(){
        Log.d("FPSCounter", "delta: " + delta);
        delta = (System.nanoTime() -lastTime)/1000000000f;
        lastTime = System.nanoTime();

    }
}
