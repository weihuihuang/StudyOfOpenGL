package com.splendid.studyofopengl.render;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;

import com.splendid.studyofopengl.OpenGlESUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES10.GL_LINEAR;
import static android.opengl.GLES10.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES10.GL_TEXTURE_WRAP_S;
import static android.opengl.GLES10.GL_TEXTURE_WRAP_T;
import static android.opengl.GLES20.GL_MIRRORED_REPEAT;
import static android.opengl.GLES20.GL_REPEAT;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLUtils.texImage2D;

/**
 * Created by WeiHuiHuang on 2019/5/16.
 */
public class TextureLoadImgRender implements GLSurfaceView.Renderer {

    //顶点着色器和片元着色器
    private FloatBuffer sPosBuffer, sCoordBuffer;

    private Bitmap bitmap;

    private int mProgram;

    //顶点着色器
    private String vertexShaderCode;

    //片元着色器
    private String fragmentShaderCode;

    private int glHPosition;

    private Context context;

    private int aTextureCoordinates;

    private int vMatrix;

    private int uTextureUnit;

    private int textureId;


    //转换矩阵
    private float[] mViewMatrix = new float[16];
    private float[] mProjectMatrix = new float[16];
    private float[] mMVPMatrix = new float[16];


    //实际坐标
    private final float[] sPos = {
            -1f, 1f,
            -1f, -1f,
            1f, 1f,
            1f, -1f
    };

    //对应的纹理坐标
    private final float[] sCoord = {
            0.0f, 0.0f,
            0.0f, 1f,
            1f, 0.0f,
            1f, 1f,
    };

    public void init(Context context) {
        this.context = context;
        //加载图片
        try {
            bitmap = BitmapFactory.decodeStream(context.getResources().getAssets().open("texture/test.png"));
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
        createBuffer();
    }

    private void createTextrue() {
        int[] texture = new int[1];
        //生成纹理
        GLES20.glGenTextures(1, texture, 0);
        //绑定纹理
//        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture[0]);
//        //设置缩小过滤为使用纹理中坐标最接近的一个像素的颜色作为需要绘制的像素颜色
//        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
//        //设置放大过滤为使用纹理中坐标最接近的若干个颜色，通过加权平均算法得到需要绘制的像素颜色
//        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
//        //设置环绕方向S，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
//        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
//        //设置环绕方向T，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
//        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

        // S轴设置镜像翻转，T轴设置重复  GL_REPEAT
        GLES20.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        GLES20.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_MIRRORED_REPEAT);
        GLES20.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        GLES20.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        //根据以上指定的参数，生成一个2D纹理
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

        textureId = texture[0];
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        createParams();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
//        //设置视图窗口
//        int xOffset = (bitmap.getWidth() - width) / 2;
//        int yOffset = (height - bitmap.getHeight() - 32) / 2;
//        GLES20.glViewport(xOffset, yOffset, width, height);
//
//        float ratio = (float) width / height;
//        //设置透视投影
//        Matrix.frustumM(mProjectMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
//        //设置相机位置
//        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 7f, 0f, 0f, 0f, 0f, 1f, 0f);
//        //计算变换矩阵
//        Matrix.multiplyMM(mMVPMatrix, 0, mProjectMatrix, 0, mViewMatrix, 0);


        GLES20.glViewport(0, 0, width, height);

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        float sWH = w / (float) h;
        float sWidthHeight = width / (float) height;
        if (width > height) {
            if (sWH > sWidthHeight) {
                Matrix.orthoM(mProjectMatrix, 0, -sWidthHeight * sWH, sWidthHeight * sWH, -1, 1, 3, 7);
            } else {
                Matrix.orthoM(mProjectMatrix, 0, -sWidthHeight / sWH, sWidthHeight / sWH, -1, 1, 3, 7);
            }
        } else {
            if (sWH > sWidthHeight) {
                Matrix.orthoM(mProjectMatrix, 0, -1, 1, -1 / sWidthHeight * sWH, 1 / sWidthHeight * sWH, 3, 7);
            } else {
                Matrix.orthoM(mProjectMatrix, 0, -1, 1, -sWH / sWidthHeight, sWH / sWidthHeight, 3, 7);
            }
        }
        //设置相机位置
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 7.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        //计算变换矩阵
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectMatrix, 0, mViewMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glUseProgram(mProgram);
        //给矩阵赋值
        GLES20.glUniformMatrix4fv(vMatrix, 1, false, mMVPMatrix, 0);

        GLES20.glEnableVertexAttribArray(glHPosition);
        GLES20.glEnableVertexAttribArray(aTextureCoordinates);

        createTextrue();

        GLES20.glVertexAttribPointer(glHPosition, 2, GLES20.GL_FLOAT, false, 0, sPosBuffer);
        GLES20.glVertexAttribPointer(aTextureCoordinates, 2, GLES20.GL_FLOAT, false, 0, sCoordBuffer);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
    }

    public void createParams() {
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glEnable(GLES20.GL_TEXTURE_2D);

        //加载顶点着色器
        vertexShaderCode = "texture/textrueVertext.sh";
        fragmentShaderCode = "texture/textrueFragment.sh";
        //创建一个空的OpenGLES程序
        mProgram = OpenGlESUtil.createProgram(context.getResources(), vertexShaderCode, fragmentShaderCode);

        glHPosition = GLES20.glGetAttribLocation(mProgram, "vPosition");

        aTextureCoordinates = GLES20.glGetAttribLocation(mProgram, "a_TextureCoordinates");

        vMatrix = GLES20.glGetUniformLocation(mProgram, "vMatrix");

        uTextureUnit = GLES20.glGetUniformLocation(mProgram, "u_TextureUnit");
    }

    public void createBuffer() {
        ByteBuffer bb = ByteBuffer.allocateDirect(sPos.length * 4);
        bb.order(ByteOrder.nativeOrder());
        sPosBuffer = bb.asFloatBuffer();
        sPosBuffer.put(sPos);
        sPosBuffer.position(0);


        ByteBuffer cc = ByteBuffer.allocateDirect(sCoord.length * 4);
        cc.order(ByteOrder.nativeOrder());
        sCoordBuffer = cc.asFloatBuffer();
        sCoordBuffer.put(sCoord);
        sCoordBuffer.position(0);
    }
}
