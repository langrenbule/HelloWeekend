package com.deity.helloweekend.data;

/**
 * Created by Deity on 2016/11/19.
 */

public class Parameters {
    /**BMOB_APP_ID*/
    public static final String BMOB_APP_ID="a1d0e1cb293c0682c2a40e8e7a9c5152";
    /**
     * 此为腾讯官方提供给开发者用于测试的APP_ID，个人开发者需要去QQ互联官网为自己的应用申请对应的AppId
     */
    public static final int REQUEST_PER_PAGE=30;
    /**百度广告开屏Id*/
    public static final String adPlaceId="3078409";
    public enum SexType{

        SEX_FEMALE(0,"女"),
        SEX_MALE(1,"男"),
        SEX_UNKNOW(2,"未知");

        private int code;
        private String description;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        SexType(int code, String description){
            this.code = code;
            this.description = description;
        }
    }
}
