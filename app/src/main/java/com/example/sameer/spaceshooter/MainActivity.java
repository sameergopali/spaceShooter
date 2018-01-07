package com.example.sameer.spaceshooter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import renderEngine.DrawingSurface;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DrawingSurface display = new DrawingSurface(this);
        setContentView(display);
    }
}
