package renderEngine;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import entity.ViewCamera;

/**
 * Created by sameer on 1/7/2018.
 */

public class DrawingSurface extends GLSurfaceView {
   private final  SurfaceRenderer surfaceRender;
   private ViewCamera camera;
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;

    public DrawingSurface(Context context) {
        super(context);

        setEGLContextClientVersion(3);
        surfaceRender= new SurfaceRenderer();
        setRenderer(surfaceRender);
        surfaceRender.setContext(context);
        setRenderMode(RENDERMODE_CONTINUOUSLY);

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
