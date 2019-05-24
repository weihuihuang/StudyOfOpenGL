package com.splendid.studyofopengl.render;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.splendid.studyofopengl.OpenGlESUtil;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by WeiHuiHuang on 2019/5/21.  三角形状、颜色变换
 */
public class DemoTriangle implements GLSurfaceView.Renderer {

    //缓冲区
    private FloatBuffer floatBuffer;
    private FloatBuffer colorSingleBuffer;
    private FloatBuffer colorfulBuffer;

    //顶点着色器
    private String vertexShader;

    //片元着色器
    private String fragmentShader;

    private float triangleCoords[] = {
            0.5f, 0.5f, 0.0f, // top
            -0.5f, -0.5f, 0.0f, // bottom left
            0.5f, -0.5f, 0.0f  // bottom right
    };


    //单色
    float colorSingle[] = {1.0f, 0.0f, 0.0f, 1.0f,};

    //彩色
    float colorful[] = {
            0.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f
    };

    private float[] mViewMatrix = new float[16];
    private float[] mProjectMatrix = new float[16];
    private float[] mMVPMatrix = new float[16];

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.6f, 0.5f, 1f, 1.0f);
        initBuffers();
    }

    private void initBuffers() {
        floatBuffer = OpenGlESUtil.getFloatBuffer(triangleCoords);
        colorSingleBuffer = OpenGlESUtil.getFloatBuffer(colorSingle);
        colorfulBuffer = OpenGlESUtil.getFloatBuffer(colorful);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }
}
