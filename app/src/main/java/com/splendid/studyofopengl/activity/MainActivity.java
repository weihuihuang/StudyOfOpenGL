package com.splendid.studyofopengl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.splendid.studyofopengl.R;
import com.splendid.studyofopengl.adapter.MainPageChooseAdapter;
import com.splendid.studyofopengl.model.MainPageChooseItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.choose_list)
    RecyclerView recyclerView;

    private MainPageChooseAdapter adapter;

    private List<MainPageChooseItem> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initEvent();
    }


    private void initData() {
        adapter = new MainPageChooseAdapter(R.layout.main_choose_item, getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<MainPageChooseItem> getData() {
        data = new ArrayList<>();
        data.add(new MainPageChooseItem("三角形绘制", TriangleActivity.class));
        data.add(new MainPageChooseItem("纹理加载图片", TextureActivity.class));
        return data;
    }

    private void initEvent() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(MainActivity.this,data.get(position).getTargetActivity()));
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
}
