package com.deity.helloweekend.data;

import android.app.Application;

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
    }
}
