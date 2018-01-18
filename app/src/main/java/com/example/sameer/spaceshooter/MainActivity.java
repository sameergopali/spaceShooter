package com.example.sameer.spaceshooter;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import renderEngine.DrawingSurface;
import renderEngine.SurfaceRenderer;
import utility.CameraPermissionHelper;

public class MainActivity extends AppCompatActivity {
    private final int CONTEXT_CLIENT_VERSION = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DrawingSurface display;

        CameraPermissionHelper.requestCameraPermission(this);
        if ( detectOpenGLES30() )
        {
            display = new DrawingSurface(this);
            // Tell the surface view we want to create an OpenGL ES 3.0-compatible
            // context, and set an OpenGL ES 3.0-compatible renderer.
          //  display.setEGLContextClientVersion ( CONTEXT_CLIENT_VERSION );
          //  display.setRenderer ( new SurfaceRenderer(  ) );
            setContentView(display);

            /////////////
        }
        else
        {
            Log.e ( "HelloTriangle", "OpenGL ES 3.0 not supported on device.  Exiting..." );
            finish();

        }
    }
    private boolean detectOpenGLES30()
    {
        ActivityManager am =
                ( ActivityManager ) getSystemService ( Context.ACTIVITY_SERVICE );
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return ( info.reqGlEsVersion >= 0x30000 );
    }
}
