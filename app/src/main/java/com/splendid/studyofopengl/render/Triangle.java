package com.splendid.studyofopengl.render;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by WeiHuiHuang on 2019/5/14. 三角形
 */
public class Triangle implements GLSurfaceView.Renderer {

    //矩阵转换的一些参数介绍： https://www.jianshu.com/p/f24f7e35a1ab
    //关于坐标系的知识：https://glumes.com/post/opengl/opengl-tutorial-projection-matrix/

    //缓冲区
    private FloatBuffer floatBuffer;

    //顶点着色器,gl_Position是Shader的内置变量，表示顶点位置

    //attribute 代表全局只读向量，只能在顶点着色器中使用

    //vec4 代表四维浮点数向量

    //uniform变量是全局且只读的,在整个shader执行完毕前其值不会改变,他可以和任意基本类型变量组合,
    // 一般我们使用uniform变量来放置外部程序传递来的环境数据(如点光源位置,模型的变换矩阵等等)
    // 这些数据在运行中显然是不需要被改变的.

    //mat4代表4*4浮点数矩阵

    //gl_Position = vPosition执行之后的意思是将顶点的坐标位置赋值给顶点着色器中的顶点
    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "uniform mat4 vMatrix;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
                    "}";

    //片元着色器，gl_FragColor 是内置变量，表示片元颜色

    //关于精度限定 : 我们一般在片元着色器(fragment shader)最开始的地方加上 precision mediump float;
    // 便设定了默认的精度.这样所有没有显式表明精度的变量 都会按照设定好的默认精度来处理.

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    private int mProgram;

    static final int COORDS_PER_VERTEX = 3;

    //三角形三个顶点的坐标位置，分别表示x、y、z的值
    static float triangleCoords[] = {
            0.5f, 0.5f, 0.0f, // top 顶点
            0f, 0f, 0f, // bottom left 左下角
            0f, 0.5f, 0.0f  // bottom right  右下角
    };

    private int mPositionHandle;
    private int mColorHandle;

    //顶点个数
    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    //顶点之间的偏移量
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 每个顶点四个字节

    private float color[] = {1.0f, 1.0f, 1.0f, 1.0f}; //白色


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.6f, 0.5f, 1f, 1.0f);
        //申请顶点着色器底层空间，每个顶点占用4个字节
        ByteBuffer bb = ByteBuffer.allocateDirect(
                triangleCoords.length * 4);
        //告诉字节缓冲区读取的的内容序列，最重要的是保持同样的顺序
        bb.order(ByteOrder.nativeOrder());

        //坐标数据需转换成FloatBuffer传给OpenGL ES
        floatBuffer = bb.asFloatBuffer();
        //将数据放入缓冲区
        floatBuffer.put(triangleCoords);

        floatBuffer.position(0);
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        //创建一个空的OpenGLES程序
        mProgram = GLES20.glCreateProgram();
        //将顶点着色器加入到程序
        GLES20.glAttachShader(mProgram, vertexShader);
        //将片元着色器加入到程序中
        GLES20.glAttachShader(mProgram, fragmentShader);
        //连接到着色器程序
        GLES20.glLinkProgram(mProgram);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //设置视图窗口
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        //将程序加入到OpenGLES2.0环境
        GLES20.glUseProgram(mProgram);
        //获取顶点着色器的vPosition成员句柄
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        //启用三角形顶点的句柄
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        //准备三角形的坐标数据
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, floatBuffer);
        //获取片元着色器的vColor成员的句柄
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        //设置绘制三角形的颜色
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);
        //绘制三角形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        //禁止顶点数组的句柄
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }

    public int loadShader(int type, String shaderCode) {
        //根据type创建顶点着色器或者片元着色器
        int shader = GLES20.glCreateShader(type);
        //将资源加入到着色器中
        GLES20.glShaderSource(shader, shaderCode);
        //对加入着色器中的资源进行编译
        GLES20.glCompileShader(shader);
        return shader;
    }
}
