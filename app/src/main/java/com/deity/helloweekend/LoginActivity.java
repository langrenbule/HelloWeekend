package com.deity.helloweekend;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;


/**
 * 登录界面
 * Created by Deity on 2016/11/23.
 */

public class LoginActivity extends BaseActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private UMShareAPI mShareAPI = null;
    @Bind(R.id.btn_qq)
    public Button btn_qq;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mShareAPI = UMShareAPI.get(this);
        BmobUser user = BmobUser.getCurrentUser();
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
            initGetUserInfo("");
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
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.HandleQQError(this,requestCode,listener);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }




    /**
     * @param authInfo
     * @return void
     * @throws
     * @method loginWithAuth
     */
    public void loginWithAuth(final BmobUser.BmobThirdUserAuth authInfo) {
        BmobUser.loginWithAuthData(authInfo, new LogInListener<JSONObject>() {

            @Override
            public void done(JSONObject jsonObject, BmobException e) {
                // TODO Auto-generated method stub
                Log.i("smile", authInfo.getSnsType() + "登陆成功返回:" + jsonObject.toString());
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("json", jsonObject.toString());
                intent.putExtra("from", authInfo.getSnsType());
                startActivity(intent);
            }
        });
    }

    private void initGetUserInfo(String snsType){
        SHARE_MEDIA platform = null;
        if (TextUtils.isEmpty(snsType)) platform = SHARE_MEDIA.QQ;
        mShareAPI.getPlatformInfo(LoginActivity.this, platform, umAuthListener);
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            for (String key : data.keySet()) {
                com.umeng.socialize.utils.Log.e("xxxxxx key = "+key+"    value= "+data.get(key));
            }
            if (data!=null){
                Toast.makeText(getApplicationContext(), data.toString(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( getApplicationContext(), "get fail"+t.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( getApplicationContext(), "get cancel", Toast.LENGTH_SHORT).show();
        }
    };


}