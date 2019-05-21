package com.splendid.studyofopengl;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.content.res.Resources;
import android.opengl.GLES20;
import android.os.Build;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by WeiHuiHuang on 2019/5/14.
 */
public class OpenGlESUtil {

    private static final int GLES_VERSION_2 = 0x2000;

    public static boolean isSupportES2(Context context){
        //检查是否支持2.0
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            ConfigurationInfo deviceConfigurationInfo = activityManager.getDeviceConfigurationInfo();
            int reqGlEsVersion = deviceConfigurationInfo.reqGlEsVersion;
            return reqGlEsVersion >= GLES_VERSION_2 || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                    && (Build.FINGERPRINT.startsWith("generic")
                    || Build.FINGERPRINT.startsWith("unknown")
                    || Build.MODEL.contains("google_sdk")
                    || Build.MODEL.contains("Emulator")
                    || Build.MODEL.contains("Android SDK build for x86")));
        } else {
            return false;
        }
    }

    public static FloatBuffer getFloatBuffer(float[] vertexs){
        FloatBuffer buffer;
        ByteBuffer qbb = ByteBuffer.allocateDirect(vertexs.length * 4);
        qbb.order(ByteOrder.nativeOrder());
        buffer = qbb.asFloatBuffer();
        //写入数组
        buffer.put(vertexs);
        //设置默认的读取位置
        buffer.position(0);
        return buffer;
    }

    public static int createProgram(Resources res, String vertexRes, String fragmentRes){
        return createProgram(loadFromAssetsFile(vertexRes,res),loadFromAssetsFile(fragmentRes,res));
    }


    public static int createProgram(String vertexSource, String fragmentSource){
        int vertex=loadShader(GLES20.GL_VERTEX_SHADER,vertexSource);
        if(vertex==0)return 0;
        int fragment=loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentSource);
        if(fragment==0)return 0;
        int program= GLES20.glCreateProgram();
        if(program!=0){
            GLES20.glAttachShader(program,vertex);
            GLES20.glAttachShader(program,fragment);
            GLES20.glLinkProgram(program);
            int[] linkStatus=new int[1];
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS,linkStatus,0);
            if(linkStatus[0]!= GLES20.GL_TRUE){
                GLES20.glDeleteProgram(program);
                program=0;
            }
        }
        return program;
    }

    public static String loadFromAssetsFile(String fname, Resources res){
        StringBuilder result=new StringBuilder();
        try{
            InputStream is=res.getAssets().open(fname);
            int ch;
            byte[] buffer=new byte[1024];
            while (-1!=(ch=is.read(buffer))){
                result.append(new String(buffer,0,ch));
            }
        }catch (Exception e){
            return null;
        }
        return result.toString().replaceAll("\\r\\n","\n");
    }

    public static int loadShader(int shaderType,String source){
        int shader= GLES20.glCreateShader(shaderType);
        if(0!=shader){
            GLES20.glShaderSource(shader,source);
            GLES20.glCompileShader(shader);
            int[] compiled=new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS,compiled,0);
            if(compiled[0]==0){
                GLES20.glDeleteShader(shader);
                shader=0;
            }
        }
        return shader;
    }
}
