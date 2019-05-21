package com.splendid.studyofopengl.activity;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;

import com.splendid.studyofopengl.R;
import com.splendid.studyofopengl.render.TextureLoadImgRender;

import butterknife.BindView;

/**
 * Created by WeiHuiHuang on 2019/5/21.  三角形绘制
 */
public class TriangleActivity extends BaseActivity{

    @BindView(R.id.glsurface_view)
    GLSurfaceView surfaceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        surfaceView.setEGLContextClientVersion(2);
        TextureLoadImgRender partOfFilterRender = new TextureLoadImgRender();
        partOfFilterRender.init(this);
        surfaceView.setRenderer(partOfFilterRender);
        surfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.triangle_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO
        switch (item.getItemId()){
            case R.id.default_triangle:

                break;
            case R.id.common_triangle:

                break;
            case R.id.isosceles_triangle:

                break;
            case R.id.colorful_triangle:

                break;

        }
        surfaceView.requestRender();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_triangle;
    }
}
