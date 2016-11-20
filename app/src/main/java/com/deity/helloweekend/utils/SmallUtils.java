package com.deity.helloweekend.utils;

import java.util.Date;

import cn.bmob.v3.datatype.BmobDate;

/**
 * 工具类
 * Created by Deity on 2016/11/20.
 */

public class SmallUtils {

    //TODO 需要改进

    /***
     * @return 获取当前时间
     */
    public static BmobDate getCurrentTime(){
        return new BmobDate(new Date(System.currentTimeMillis()));
    }


}
