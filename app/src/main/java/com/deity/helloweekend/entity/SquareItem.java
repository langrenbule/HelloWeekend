package com.deity.helloweekend.entity;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * 广场数据
 * Created by Deity on 2016/11/20.
 */

public class SquareItem extends BmobObject implements Serializable {
    /**发布该条数据的用户*/
    private User author;
    /**说说内容*/
    private String weibo;
    /**说说附带的图片*/
    private BmobFile weiboImageUrl;
    /**喜欢数量*/
    private int loveNum;
    /**讨厌数量*/
    private int hateNum;
    /**转载次数*/
    private int share;
    /**评论次数*/
    private int comment;
    /***/
    private boolean isPass;
    /**是否已经收藏*/
    private boolean myFav;
    /**是否已经赞过*/
    private boolean myLove;
    /***/
    private BmobRelation relation;

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public BmobFile getWeiboImageUrl() {
        return weiboImageUrl;
    }

    public void setWeiboImageUrl(BmobFile weiboImageUrl) {
        this.weiboImageUrl = weiboImageUrl;
    }

    public int getLoveNum() {
        return loveNum;
    }

    public void setLoveNum(int loveNum) {
        this.loveNum = loveNum;
    }

    public int getHateNum() {
        return hateNum;
    }

    public void setHateNum(int hateNum) {
        this.hateNum = hateNum;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public boolean isPass() {
        return isPass;
    }

    public void setPass(boolean pass) {
        isPass = pass;
    }

    public boolean isMyFav() {
        return myFav;
    }

    public void setMyFav(boolean myFav) {
        this.myFav = myFav;
    }

    public boolean isMyLove() {
        return myLove;
    }

    public void setMyLove(boolean myLove) {
        this.myLove = myLove;
    }

    public BmobRelation getRelation() {
        return relation;
    }

    public void setRelation(BmobRelation relation) {
        this.relation = relation;
    }
}
