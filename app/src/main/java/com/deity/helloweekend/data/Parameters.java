package com.deity.helloweekend.data;

/**
 * Created by Deity on 2016/11/19.
 */

public class Parameters {
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
