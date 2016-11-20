package com.deity.helloweekend.data;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * 全局Application
 * Created by Deity on 2016/11/19.
 */

public class HWApplication extends Application {
    private HWApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = this;
        Bmob.initialize(this, Parameters.BMOB_APP_ID);
    }
}
