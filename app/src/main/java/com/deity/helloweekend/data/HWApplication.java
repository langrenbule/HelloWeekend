package com.deity.helloweekend.data;

import android.app.Application;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import cn.bmob.v3.Bmob;

/**
 * 全局Application
 * Created by Deity on 2016/11/19.
 */

public class HWApplication extends Application {
    public static HWApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = this;
        Bmob.initialize(this, Parameters.BMOB_APP_ID);
        UMShareAPI.get(this);
    }

    //各个平台的配置，建议放在全局Application或者程序入口
    {
        //微信 wx12342956d1cab4f9,a5ae111de7d9ea137e88a5e02c07c94d
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        PlatformConfig.setQQZone("101363589", "b51f507ce2fb587edd5a0294b0e776e0");
    }
}
