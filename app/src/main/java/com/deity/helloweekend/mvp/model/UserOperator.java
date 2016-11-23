package com.deity.helloweekend.mvp.model;

import android.content.Context;
import android.util.Log;

import com.deity.helloweekend.data.Parameters;
import com.deity.helloweekend.entity.User;

import cn.bmob.v3.BmobUser;
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

    public UserOperator(Context mContext){
        this.mContext = mContext;
    }

    public interface ISignUpListener{
        void signUpSuccess();
        void signUpFail(int errorCode,String errorDescription);
    }

    public interface ILoginListener{
        void loginSuccess();
        void loginFail(int errorCode,String errorDescription);
    }

    public interface IUpdateListener{
        void onUpdateSuccess();
        void onUpdateFailure(int errorCode,String errorDescription);
    }

    public interface IResetPasswordListener{
        void onResetSuccess();
        void onResetFailure(int errorCode,String errorDescription);
    }

    public void setiLoginListener(ILoginListener iLoginListener) {
        this.iLoginListener = iLoginListener;
    }

    public void setiSignUpListener(ISignUpListener iSignUpListener) {
        this.iSignUpListener = iSignUpListener;
    }

    /**新用户注册*/
    public void signUp(String userName,String password,String email){
        User user = new User(userName,password,email);
        user.setSex(Parameters.SexType.SEX_UNKNOW.getDescription());
        user.setSignature("这个家伙很懒，什么也不说。。。");
        user.signUp(mContext, new SaveListener() {
            @Override
            public void onSuccess() {
                if (null!=iSignUpListener) iSignUpListener.signUpSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                Log.i(TAG,"sign up Fail,cause by>>>"+s);
                if (null!=iSignUpListener) iSignUpListener.signUpFail(i,s);
            }
        });
    }

    /**获取当前用户*/
    public User obtainCurrentUser(){
        return BmobUser.getCurrentUser(mContext, User.class);
    }

    public void login(String userName,String password){
        BmobUser user = new BmobUser();
        user.setUsername(userName);
        user.setPassword(password);
        user.login(mContext, new SaveListener() {
            @Override
            public void onSuccess() {
                if(null!=iLoginListener) iLoginListener.loginSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                if (null!=iLoginListener) iLoginListener.loginFail(i,s);
            }
        });
    }

    public void loginOut(){
        BmobUser.logOut(mContext);
    }

    public void updateUser(String sex,String signature){
        User user = obtainCurrentUser();
        user.setSex(sex);
        user.setSignature(signature);
        user.update(mContext, new UpdateListener() {
            @Override
            public void onSuccess() {
                if (null!=iUpdateListener) iUpdateListener.onUpdateSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                if (null!=iUpdateListener) iUpdateListener.onUpdateFailure(i,s);
            }
        });
    }

//    public void resetPassword(String email){
//        BmobUser.resetPassword(mContext, email, new ResetPasswordListener() {
//            @Override
//            public void onSuccess() {
//                if (null!=iResetPasswordListener) iResetPasswordListener.onResetSuccess();
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//                if (null!=iResetPasswordListener) iResetPasswordListener.onResetFailure(i,s);
//            }
//        });
//    }


}
