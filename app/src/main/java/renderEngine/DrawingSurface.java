package renderEngine;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by sameer on 1/7/2018.
 */

public class DrawingSurface extends GLSurfaceView {
   private final  SurfaceRenderer surfaceRender;

    public DrawingSurface(Context context) {
        super(context);

        setEGLContextClientVersion(3);
        surfaceRender= new SurfaceRenderer();
        setRenderer(surfaceRender);
        surfaceRender.setContext(context);
    }

}
