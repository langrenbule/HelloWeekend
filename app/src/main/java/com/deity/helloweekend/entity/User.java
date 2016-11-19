package com.deity.helloweekend.entity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * 用户实体类
 * Created by Deity on 2016/11/19.
 */

public class User extends BmobUser {
    /**签名*/
    private String signature;
    /**头像*/
    private BmobFile avatar;
    /**点赞*/
    private BmobRelation favorite;
    /**性别*/
    private String sex;

    public User(){}

    public User(String userName,String password,String email){
        this.setUsername(userName);
        this.setPassword(password);
        this.setEmail(email);
    }

    /**更新性别及签名*/
    public User(String sex,String signature){
        this.setSex(sex);
        this.setSignature(signature);
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public BmobFile getAvatar() {
        return avatar;
    }

    public void setAvatar(BmobFile avatar) {
        this.avatar = avatar;
    }

    public BmobRelation getFavorite() {
        return favorite;
    }

    public void setFavorite(BmobRelation favorite) {
        this.favorite = favorite;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
