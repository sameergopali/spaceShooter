package renderEngine;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import entity.Camera;

import static android.content.ContentValues.TAG;

/**
 * Created by sameer on 1/7/2018.
 */

public class DrawingSurface extends GLSurfaceView {
   private final  SurfaceRenderer surfaceRender;
   private Camera camera;
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;

    public DrawingSurface(Context context) {
        super(context);

        setEGLContextClientVersion(3);
        surfaceRender= new SurfaceRenderer();
        setRenderer(surfaceRender);
        surfaceRender.setContext(context);

    }
    @Override
    public boolean onTouchEvent(MotionEvent e) {

        final float normx = e.getX()/(float)getWidth() *2 -1;
        final float normy =- e.getY() /(float)getHeight() *2 -1;
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
             queueEvent(new Runnable() {
                 @Override
                 public void run() {
                     surfaceRender.getCamera().handleTouchDrag(normx, normy);
                 }
             });
            case MotionEvent.ACTION_DOWN:
                queueEvent(new Runnable() {
                    @Override
                    public void run() {
                        surfaceRender.getCamera().handleTouchPress(normx, normy);
                    }
                });

        }


        return  true;
    }




}
