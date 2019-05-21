package com.splendid.studyofopengl.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.splendid.studyofopengl.R;
import com.splendid.studyofopengl.model.MainPageChooseItem;

import java.util.List;

/**
 * Created by WeiHuiHuang on 2019/5/21.
 */
public class MainPageChooseAdapter extends BaseQuickAdapter<MainPageChooseItem, BaseViewHolder> {

    public MainPageChooseAdapter(int layoutResId, List<MainPageChooseItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainPageChooseItem item) {
        helper.setText(R.id.item_name,item.getName());
    }

}
