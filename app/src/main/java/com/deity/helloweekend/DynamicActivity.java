package com.deity.helloweekend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.deity.helloweekend.entity.Dynamic;
import com.deity.helloweekend.entity.DynamicMessage;
import com.deity.helloweekend.entity.User;
import com.deity.helloweekend.ui.BaseActivity;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;

/**
 * Created by Deity on 2016/11/30.
 */

public class DynamicActivity extends BaseActivity {
    @Bind(R.id.dynamic_img) public ImageView dynamic_img;
    @Bind(R.id.dynamic_text) public TextView dynamic_text;
    public DynamicMessage dynamicMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dynamic);
        ButterKnife.bind(this);
        initParams();
    }

    private void initParams(){
        dynamicMessage = new DynamicMessage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dynamic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_public) {
            publicDynamicWithPic();
//            public4Message(dynamicMessage.getContent(),null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("unused")
    @OnClick(R.id.dynamic_img)
    public void selectImage(){
        Toast.makeText(DynamicActivity.this,"点击事件",Toast.LENGTH_LONG).show();
        RxGalleryFinal
                .with(DynamicActivity.this)
                .image()
                .radio()
                .crop()
                .imageLoader(ImageLoaderType.GLIDE)
                .subscribe(new RxBusResultSubscriber<ImageRadioResultEvent>() {
                    @Override
                    protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
                        String result=" 大缩略图:"+imageRadioResultEvent.getResult().getThumbnailBigPath();
                        dynamicMessage.setImageUrl(imageRadioResultEvent.getResult().getThumbnailBigPath());
                        Toast.makeText(getBaseContext(), result, Toast.LENGTH_SHORT).show();
                        Glide.with(DynamicActivity.this).load(imageRadioResultEvent.getResult().getOriginalPath()).thumbnail(1.0f).into(dynamic_img);

                    }
                })
                .openGallery();
    }


    public void publicDynamicWithPic(){
        String commitContent = dynamic_text.getText().toString().trim();
        if (!TextUtils.isEmpty(commitContent)){
            dynamicMessage.setContent(commitContent);
        }
        final BmobFile figureFile = new BmobFile(new File(dynamicMessage.getImageUrl()));
        figureFile.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                public4Message(dynamicMessage.getContent(),figureFile);
                Toast.makeText(getBaseContext(), "上传动态 upload=", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void public4Message(String commitContent,BmobFile figureFile){
        User user = BmobUser.getCurrentUser(User.class);
        Dynamic dynamic = new Dynamic();
        dynamic.setAuthor(user);
        dynamic.setContent(commitContent);
        dynamic.setCommentNum(0);
        dynamic.setDynamicImage(figureFile);
        dynamic.setLove(0);
        dynamic.setHate(0);
        dynamic.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                Toast.makeText(getBaseContext(), "恭喜,发表动态成功! desc="+s+"Exception=", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
