package com.deity.helloweekend;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.deity.helloweekend.data.Parameters;
import com.deity.helloweekend.entity.User;
import com.deity.helloweekend.fragment.PersonalFragment;
import com.deity.helloweekend.fragment.SettingFragment;
import com.deity.helloweekend.fragment.SquareFragment;
import com.deity.helloweekend.ui.BaseActivity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;


public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int INDEX_HOME=0;
    private static final int INDEX_SETTING=1;
    private static final int INDEX_PERSONAL=2;

    private SquareFragment squareFragment;
    private SettingFragment mSettingFragment;
    private PersonalFragment mPersonalFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initFragment();
        initUser();
    }

    /**初始化用户数据*/
    public void initUser(){
        User user = BmobUser.getCurrentUser(User.class);
        if (TextUtils.isEmpty(user.getNickName())){
            user.setNickName(Parameters.currentMapUserData.get("screen_name"));
            user.setSex(Parameters.currentMapUserData.get("gender"));
//            BmobFile file = new BmobFile(new File(Parameters.currentMapUserData.get("profile_image_url")));
//            user.setAvatar(file);
            user.update(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (null==e){
                        Toast.makeText(MainActivity.this,"更新个人信息成功",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void initFragment(){
        squareFragment = new SquareFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().replace(R.id.main_content,squareFragment);
        transaction.commit();
    }

    public void selectFragment(int index){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (index){
            case INDEX_HOME:
                if (null==squareFragment){
                    squareFragment = new SquareFragment();
                }
                transaction.replace(R.id.main_content,squareFragment);
                break;
            case INDEX_SETTING:
                if (null==mSettingFragment){
                    mSettingFragment = new SettingFragment();
                }
                transaction.replace(R.id.main_content,mSettingFragment);
                break;
            case INDEX_PERSONAL:
                if (null==mPersonalFragment){
                    mPersonalFragment = new PersonalFragment();
                }
                transaction.replace(R.id.main_content,mPersonalFragment);
                break;
        }
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent dynamicIntent = new Intent(MainActivity.this,DynamicActivity.class);
            startActivity(dynamicIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            selectFragment(INDEX_HOME);
        } else if (id == R.id.nav_gallery) {
            selectFragment(INDEX_SETTING);
        } else if (id == R.id.nav_slideshow) {
            selectFragment(INDEX_PERSONAL);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
