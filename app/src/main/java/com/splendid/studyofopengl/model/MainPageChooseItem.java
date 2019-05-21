package com.splendid.studyofopengl.model;

/**
 * Created by WeiHuiHuang on 2019/5/21.
 */
public class MainPageChooseItem {

    private String name;

    private Class targetActivity;

    public MainPageChooseItem(String name, Class targetActivity) {
        this.name = name;
        this.targetActivity = targetActivity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getTargetActivity() {
        return targetActivity;
    }

    public void setTargetActivity(Class targetActivity) {
        this.targetActivity = targetActivity;
    }
}
