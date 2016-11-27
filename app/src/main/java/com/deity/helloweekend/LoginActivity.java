package com.deity.helloweekend;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.deity.helloweekend.ui.BaseActivity;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONObject;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.OtherLoginListener;


/**
 * 登录界面
 * Created by Deity on 2016/11/23.
 */

public class LoginActivity extends BaseActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
//    public Tencent mTencent;
    @Bind(R.id.btn_qq)
    public Button btn_qq;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        BmobUser user = BmobUser.getCurrentUser(this);
        if(user!=null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            this.finish();
        }
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btn_weixin)
    public void Authorize4WX(){
        UMShareAPI mShareAPI = UMShareAPI.get( LoginActivity.this );
        mShareAPI.doOauthVerify(LoginActivity.this, SHARE_MEDIA.WEIXIN, listener);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btn_qq)
    public void Authorize4QQ(){
        UMShareAPI mShareAPI = UMShareAPI.get( LoginActivity.this );
        mShareAPI.doOauthVerify(LoginActivity.this, SHARE_MEDIA.QQ, listener);
    }

    private UMAuthListener listener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "Authorize succeed,Platform>>>"+platform.toString()+" token>>"+data.get("access_token")+" expires_in>>>"+data.get("expires_in")+" openId>>"+data.get("openid"), Toast.LENGTH_SHORT).show();
            BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth(platform.toString().toLowerCase(),data.get("access_token"), data.get("expires_in"),data.get("openid"));
            loginWithAuth(authInfo);
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        com.umeng.socialize.utils.Log.i("result", "requestCode="+requestCode);
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).HandleQQError(this,requestCode,listener);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


//    public void qqAuthorize() {
//
////        mTencent = Tencent.createInstance(AppId, this.getApplicationContext());
////        if (!mTencent.isSessionValid())
////        {
////            mTencent.login(this, Scope, listener);
////        }
//        if (mTencent == null) {
//            mTencent = Tencent.createInstance(Parameters.QQ_APP_ID, getApplicationContext());
//        }
//        mTencent.logout(this);
//        mTencent.login(this, "all", new IUiListener() {
//            @Override
//            public void onComplete(Object result) {
//                if (result != null) {
//                    JSONObject jsonObject = (JSONObject) result;
//                    try {
//                        String token = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_ACCESS_TOKEN);
//                        String expires = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_EXPIRES_IN);
//                        String openId = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_OPEN_ID);
//                        BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth(BmobUser.BmobThirdUserAuth.SNS_TYPE_QQ, token, expires, openId);
//                        loginWithAuth(authInfo);
//                    } catch (JSONException e) {
//                    }
//                }
//            }
//
//            @Override
//            public void onError(UiError arg0) {
//                // TODO Auto-generated method stub
//                Log.i(TAG, "QQ授权出错：" + arg0.errorCode + "--" + arg0.errorDetail);
//            }
//
//            @Override
//            public void onCancel() {
//                // TODO Auto-generated method stub
//                Log.i(TAG, "取消qq授权");
//            }
//        });
//    }


    /**
     * @param authInfo
     * @return void
     * @throws
     * @method loginWithAuth
     */
    public void loginWithAuth(final BmobUser.BmobThirdUserAuth authInfo) {
        BmobUser.loginWithAuthData(LoginActivity.this, authInfo, new OtherLoginListener() {

            @Override
            public void onSuccess(JSONObject userAuth) {
                // TODO Auto-generated method stub
                Log.i("smile", authInfo.getSnsType() + "登陆成功返回:" + userAuth);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("json", userAuth.toString());
                intent.putExtra("from", authInfo.getSnsType());
                startActivity(intent);
            }

            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                Log.i(TAG, "第三方登陆失败：" + msg);
            }

        });
    }


    /**
     * 获取QQ的信息:具体可查看QQ的官方Demo，最新的QQ登录sdk已提供获取用户资料的接口并提供相应示例，可忽略此方式
     * @Title: getQQInfo
     * @Description: TODO
     * @param
     * @return void
     * @throws
     */
//    public void getQQInfo(final String access_token, final String openId, final String oauth_consumer_key) {
//        // 若更换为自己的APPID后，仍然获取不到自己的用户信息，则需要
//        // 根据(http://wiki.open.qq.com/wiki/website/get_user_info)提供的API文档，想要获取QQ用户的信息，则需要自己调用接口，传入对应的参数
//        new Thread() {
//            @Override
//            public void run() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("access_token", access_token);// 此为QQ登陆成功之后返回access_token
//                params.put("openid", openId);
//                params.put("oauth_consumer_key", oauth_consumer_key);// oauth_consumer_key为申请QQ登录成功后，分配给应用的appid
//                params.put("format", "json");// 格式--非必填项
//                String result = NetUtils.getRequest("https://graph.qq.com/user/get_user_info", params);
//                Log.d("login", "QQ的个人信息：" + result);
//
//            }
//
//        }.start();
//    }


}