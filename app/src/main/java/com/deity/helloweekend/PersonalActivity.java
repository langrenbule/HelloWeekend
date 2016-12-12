package com.deity.helloweekend;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deity.helloweekend.adapter.SquareDataAdapter;
import com.deity.helloweekend.data.Parameters;
import com.deity.helloweekend.entity.Dynamic;
import com.deity.helloweekend.fragment.SquareFragment;
import com.deity.helloweekend.ui.BaseActivity;
import com.deity.helloweekend.utils.I18NData;
import com.othershe.baseadapter.ViewHolder;
import com.othershe.baseadapter.interfaces.OnItemClickListener;
import com.othershe.baseadapter.interfaces.OnLoadMoreListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 个人资料介绍界面
 * Created by Deity on 2016/12/5.
 */

public class PersonalActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.dynamic_list)
    public RecyclerView dynamic_list;
    @Bind(R.id.widget_refresh)
    public SwipeRefreshLayout mSwipeRefreshLayout;
    private SquareDataAdapter mSquareDataAdapter;
    private int currentNewsPage = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_personal);
        ButterKnife.bind(this);
        initRecycleView();
    }

    public void initRecycleView() {
        mSquareDataAdapter = new SquareDataAdapter(PersonalActivity.this, null, true);
        //初始化EmptyView
        View emptyView = LayoutInflater.from(PersonalActivity.this).inflate(R.layout.empty_layout, (ViewGroup) dynamic_list.getParent(), false);
        mSquareDataAdapter.setEmptyView(emptyView);

        //初始化 开始加载更多的loading View
        mSquareDataAdapter.setLoadingView(R.layout.load_loading_layout);
        //设置加载更多触发的事件监听
        mSquareDataAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                obtainSquareData(new SquareFragment.DataCallBackResult() {
                    @Override
                    public void callbackHandler(List<Dynamic> list, BmobException e) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        if (null != list && !list.isEmpty()) {
                            currentNewsPage++;//只有成功了，才递增
                            mSquareDataAdapter.setLoadMoreData(list);
                        }
                        if (null != e) {
                            System.out.println("获取数据失败>>>>" + e.toString());
                        }
                    }
                });
            }
        });

        //设置item点击事件监听
        mSquareDataAdapter.setOnItemClickListener(new OnItemClickListener<Dynamic>() {

            @Override
            public void onItemClick(ViewHolder viewHolder, Dynamic data, int position) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(PersonalActivity.this, CommentActivity.class);
                bundle.putSerializable("dynamic",data);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(PersonalActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dynamic_list.setLayoutManager(layoutManager);
        dynamic_list.setAdapter(mSquareDataAdapter);
        mSwipeRefreshLayout.setColorSchemeColors(I18NData.getColor(R.color.colorAccent),I18NData.getColor(R.color.colorPrimary),I18NData.getColor(R.color.colorPrimaryDark));
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }


    /**
     *  mPullLoadMoreRecyclerView.scrollToTop() 快速返回顶部
     *  square_item_list.setHasMore(false);//通知控件，已经没数据了
     */
    public void obtainSquareData(final SquareFragment.DataCallBackResult result) {
        BmobQuery<Dynamic> query = new BmobQuery<>();
        query.order("-createdAt").setLimit(Parameters.REQUEST_PER_PAGE).setSkip(Parameters.REQUEST_PER_PAGE*(currentNewsPage)).include("author");
        query.addWhereEqualTo("author", Parameters.currentDynamic.getAuthor());
        query.findObjects(new FindListener<Dynamic>() {
            @Override
            public void done(List<Dynamic> list, BmobException e) {
                result.callbackHandler(list,e);
            }
        });

    }

    public void obtainSquareDataFirst(){
        currentNewsPage=0;//重置页数
        obtainSquareData(new SquareFragment.DataCallBackResult() {
            @Override
            public void callbackHandler(List<Dynamic> list, BmobException e) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (null != list && !list.isEmpty()) {
                    currentNewsPage++;//只有成功了，才递增
                    mSquareDataAdapter.setNewData(list);
                }
                if (null != e) {
                    System.out.println("获取数据失败>>>>" + e.toString());
                }
            }
        });
    }

    /**
     * Called when a swipe gesture triggers a refresh.
     */
    @Override
    public void onRefresh() {
        obtainSquareDataFirst();
    }
}
