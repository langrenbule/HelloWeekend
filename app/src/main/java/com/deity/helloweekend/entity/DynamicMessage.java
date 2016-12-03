package com.deity.helloweekend.entity;

/**
 * Created by Deity on 2016/11/30.
 */

public class DynamicMessage {
    /**说说内容*/
    private String content;
    /**图片地址*/
    private String imageUrl;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
