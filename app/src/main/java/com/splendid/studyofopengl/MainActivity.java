package com.splendid.studyofopengl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.splendid.graphicsrender.IsoscelesTriangle;
import com.splendid.graphicsrender.PartOfFilterRender;
import com.splendid.graphicsrender.TextureLoadImgFilterRender;
import com.splendid.graphicsrender.TextureLoadImgRender;
import com.splendid.graphicsrender.Triangle;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        glSurfaceView = findViewById(R.id.glsurface_view);
        glSurfaceView.setEGLContextClientVersion(2);
        TextureLoadImgRender partOfFilterRender = new TextureLoadImgRender();
        partOfFilterRender.init(this);
        glSurfaceView.setRenderer(partOfFilterRender);
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
//        setContentView(glSurfaceView);
    }
}
