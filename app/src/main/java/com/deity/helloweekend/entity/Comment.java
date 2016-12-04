package com.deity.helloweekend.entity;

import cn.bmob.v3.BmobObject;

/**
 * 评论
 * Created by Deity on 2016/11/19.
 */

public class Comment extends BmobObject {
    /**当前评论对应的用户*/
    private User user;
    /**当前对应的评论*/
    private String currentComment;

    public Comment(User user,String currentComment){
        this.user = user;
        this.currentComment = currentComment;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCurrentComment() {
        return currentComment;
    }

    public void setCurrentComment(String currentComment) {
        this.currentComment = currentComment;
    }
}
