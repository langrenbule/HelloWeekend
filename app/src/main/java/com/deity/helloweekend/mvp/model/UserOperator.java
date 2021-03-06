package com.deity.helloweekend.mvp.model;

import android.content.Context;

import com.deity.helloweekend.data.Parameters;
import com.deity.helloweekend.entity.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 用户操作
 * Created by Deity on 2016/11/19.
 */

public class UserOperator {
    private final static String TAG = UserOperator.class.getSimpleName();
    private Context mContext;
    private ISignUpListener iSignUpListener;
    private ILoginListener iLoginListener;
    private IUpdateListener iUpdateListener;
    private IResetPasswordListener iResetPasswordListener;

    public UserOperator(Context mContext) {
        this.mContext = mContext;
    }

    public interface ISignUpListener {
        void signUpSuccess();

        void signUpFail(BmobException errorDescription);
    }

    public interface ILoginListener {
        void loginSuccess();

        void loginFail(BmobException errorDescription);
    }

    public interface IUpdateListener {
        void onUpdateSuccess();

        void onUpdateFailure(BmobException errorDescription);
    }

    public interface IResetPasswordListener {
        void onResetSuccess();

        void onResetFailure(int errorCode, String errorDescription);
    }

    public void setiLoginListener(ILoginListener iLoginListener) {
        this.iLoginListener = iLoginListener;
    }

    public void setiSignUpListener(ISignUpListener iSignUpListener) {
        this.iSignUpListener = iSignUpListener;
    }

    /**
     * 新用户注册
     */
    public void signUp(String userName, String password, String email) {
        User user = new User(userName, password, email);
        user.setSex(Parameters.SexType.SEX_UNKNOW.getDescription());
        user.setSignature("这个家伙很懒，什么也不说。。。");
        user.signUp(new SaveListener() {
            @Override
            public void done(Object o, BmobException e) {
                if (null != iSignUpListener) iSignUpListener.signUpSuccess();

                if (null != iSignUpListener&&null!=e) iSignUpListener.signUpFail(e);
            }
        });
    }

    /**
     * 获取当前用户
     */
    public User obtainCurrentUser() {
        return BmobUser.getCurrentUser(User.class);
    }

    public void login(String userName, String password) {
        BmobUser user = new BmobUser();
        user.setUsername(userName);
        user.setPassword(password);
        user.login(new SaveListener() {
            @Override
            public void done(Object o, BmobException e) {
                if (null != iLoginListener) iLoginListener.loginSuccess();

                if (null != iLoginListener&&null!=e) iLoginListener.loginFail(e);
            }
        });
    }

    public void loginOut() {
        BmobUser.logOut();
    }

    public void updateUser(String sex, String signature) {
        User user = obtainCurrentUser();
        user.setSex(sex);
        user.setSignature(signature);
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (null != iUpdateListener) iUpdateListener.onUpdateSuccess();

                if (null != iUpdateListener) iUpdateListener.onUpdateFailure(e);
            }
        });
    }
}
