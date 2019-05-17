package com.splendid.graphicsrender;

import android.opengl.GLSurfaceView;

import com.splendid.util.CommonUtil;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by WeiHuiHuang on 2019/5/14.
 */
public class MyRender implements GLSurfaceView.Renderer {

    //顶点数组
    private float[] mArray = {1f, 1f, 0f};

    //缓冲区
    private FloatBuffer mBuffer;

    public MyRender(){
        mBuffer = CommonUtil.getFloatBuffer(mArray);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0f, 0f, 0f, 0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0 , width , height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mBuffer);
        gl.glColor4f(0f, 1f, 1f, 0f);
        gl.glPointSize(100f);
        gl.glDrawArrays(GL10.GL_POINTS, 0, 1);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}
