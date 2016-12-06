package com.deity.helloweekend;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.deity.helloweekend.adapter.CommentAdapter;
import com.deity.helloweekend.data.Parameters;
import com.deity.helloweekend.entity.Comment;
import com.deity.helloweekend.entity.Dynamic;
import com.deity.helloweekend.entity.User;
import com.deity.helloweekend.ui.BaseActivity;
import com.othershe.baseadapter.interfaces.OnLoadMoreListener;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 评论页面
 * Created by Deity on 2016/12/3.
 */

public class CommentActivity extends BaseActivity {//implements SwipeRefreshLayout.OnRefreshListener
    private int currentPage=0;
    @Bind(R.id.comment_list) public RecyclerView comment_list;
//    @Bind(R.id.widget_refresh) public SwipeRefreshLayout widget_refresh;
    @Bind(R.id.comment_content) public EditText comment_content;
    @Bind(R.id.content_text) public TextView content_text;
    @Bind(R.id.content_image) public ImageView content_image;
    @Bind(R.id.user_link) public PercentRelativeLayout user_link;
    public CommentAdapter mCommentDataAdapter;
    public Dynamic dynamic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                obtainComment(dynamic);
            }
        }, 1000);
        initRecycleView();
    }

    public void initViews(){
        dynamic = (Dynamic) getIntent().getSerializableExtra("dynamic");
        if (!TextUtils.isEmpty(dynamic.getContent())) content_text.setText(dynamic.getContent());
        Glide.with(CommentActivity.this).load(dynamic.getDynamicImage().getUrl()).thumbnail(1.0f).placeholder(R.drawable.ic_laucher).into(content_image);
    }

//    public void obtainCommentFirst(Dynamic dynamic){
//        BmobQuery<Comment> query = new BmobQuery<>();
//        query .addWhereRelatedTo("relation",new BmobPointer(dynamic))
//                .include("user")
//                .order("createdAt")
//                .setLimit(Parameters.REQUEST_PER_PAGE)
//                .setSkip(Parameters.REQUEST_PER_PAGE*currentPage);
//        query.findObjects(new FindListener<Comment>() {
//            @Override
//            public void done(List<Comment> list, BmobException e) {
//                widget_refresh.setRefreshing(false);
//                if (null!=list&&!list.isEmpty()){
//                    currentPage++;
//                    mCommentDataAdapter.setNewData(list);
//                }
//            }
//        });
//    }


    public void obtainComment(Dynamic dynamic){
        BmobQuery<Comment> query = new BmobQuery<>();
        query .addWhereRelatedTo("relation",new BmobPointer(dynamic))
                .include("user")
                .order("createdAt")
                .setLimit(Parameters.REQUEST_PER_PAGE)
                .setSkip(Parameters.REQUEST_PER_PAGE*currentPage);
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if (null!=list&&!list.isEmpty()){
                    currentPage++;
                    mCommentDataAdapter.setLoadMoreData(list);
                }
            }
        });

    }
    @OnClick(R.id.user_link)
    public void linkPersonFragment(){
        Intent intent = new Intent(CommentActivity.this,PersonalActivity.class);
        startActivity(intent);
    }

    public void initRecycleView() {
        mCommentDataAdapter = new CommentAdapter(CommentActivity.this, null, true);
        //初始化EmptyView
        View emptyView = LayoutInflater.from(CommentActivity.this).inflate(R.layout.empty_layout, (ViewGroup) comment_list.getParent(), false);
        mCommentDataAdapter.setEmptyView(emptyView);

        //初始化 开始加载更多的loading View
        mCommentDataAdapter.setLoadingView(R.layout.load_loading_layout);
        //设置加载更多触发的事件监听
        mCommentDataAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                obtainComment(dynamic);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(CommentActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        comment_list.setLayoutManager(layoutManager);
        comment_list.setAdapter(mCommentDataAdapter);
    }


    /**评论*/
    @SuppressWarnings("unused")
    @OnClick(R.id.comment_commit)
    public void publicComment(){
        String commentContent = comment_content.getText().toString().trim();
        if (!TextUtils.isEmpty(commentContent)){
            final Comment currentComment = new Comment(BmobUser.getCurrentUser(User.class),commentContent);
            currentComment.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (!TextUtils.isEmpty(s)){
                        Toast.makeText(CommentActivity.this,s,Toast.LENGTH_SHORT).show();
                    }
                    BmobRelation relation = new BmobRelation();
                    relation.add(currentComment);
                    dynamic.setRelation(relation);
                    dynamic.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            Toast.makeText(CommentActivity.this,"发表评论成功!",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    /**
     * Called when a swipe gesture triggers a refresh.
     */
//    @Override
//    public void onRefresh() {
//        obtainCommentFirst(dynamic);
//    }
}
