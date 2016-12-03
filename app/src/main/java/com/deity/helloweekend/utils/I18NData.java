package com.deity.helloweekend.utils;

import com.deity.helloweekend.data.HWApplication;

/**
 * Created by Deity on 2016/12/3.
 */

public class I18NData {
    /**获取颜色*/
    public static int getColor(int id){
        return HWApplication.instance.getResources().getColor(id);
    }
}
