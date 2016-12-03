package com.deity.helloweekend.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deity.helloweekend.CommentActivity;
import com.deity.helloweekend.R;
import com.deity.helloweekend.adapter.SquareItemAdapter;
import com.deity.helloweekend.data.Parameters;
import com.deity.helloweekend.entity.Dynamic;
import com.deity.helloweekend.mvp.model.SquareOperator;
import com.deity.helloweekend.ui.BaseFragment;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


/**
 * 交友广场
 * Created by Deity on 2016/11/20.
 */

public class SquareFragment extends BaseFragment {

    @Bind(R.id.square_item_list)
    public PullLoadMoreRecyclerView square_item_list;

    private SquareItemAdapter mSquareItemAdapter;
    private SquareOperator mSquareOperator;
    private int currentNewsPage = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSquareOperator = new SquareOperator(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_square, container, false);
        ButterKnife.bind(this, view);
        initRecycleView();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                square_item_list.setRefreshing(true);
                obtainSquareDataFirst();
            }
        }, 1000);
        return view;
    }

    public void initRecycleView() {
        mSquareItemAdapter = new SquareItemAdapter(getActivity());
        square_item_list.setLinearLayout();
        square_item_list.setAdapter(mSquareItemAdapter);
        square_item_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CommentActivity.class);
//                intent.putExtra("url", data.getNewBornArticleUrl());
//                intent.putExtra("imageUrl", data.getNewBornImageUrl());
//                intent.putExtra("newsTitle", data.getNewBornTitle());
                startActivity(intent);
            }
        });
        square_item_list.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
//                mSquareOperator.obtainSquareItemList(getActivity(),1);
                obtainSquareDataFirst();
            }

            @Override
            public void onLoadMore() {
                square_item_list.setFooterViewText("loading");
                obtainSquareData(new DataCallBackResult() {
                    @Override
                    public void callbackHandler(List<Dynamic> list, BmobException e) {
                        square_item_list.setPullLoadMoreCompleted();
                        if (null != list && !list.isEmpty()) {
                            currentNewsPage++;//只有成功了，才递增
                            mSquareItemAdapter.addData(list);
                            mSquareItemAdapter.notifyDataSetChanged();
                        }
                        if (null != e) {
                            System.out.println("获取数据失败>>>>" + e.toString());
                        }
                    }
                });
            }
        });

    }

    public interface DataCallBackResult{
        void callbackHandler(List<Dynamic> list, BmobException e);
    }

    public void obtainSquareDataFirst(){
        currentNewsPage=0;//重置页数
        obtainSquareData(new DataCallBackResult() {
            @Override
            public void callbackHandler(List<Dynamic> list, BmobException e) {
                square_item_list.setPullLoadMoreCompleted();
                if (null != list && !list.isEmpty()) {
                    currentNewsPage++;//只有成功了，才递增
                    mSquareItemAdapter.setData(list);
                    mSquareItemAdapter.notifyDataSetChanged();
                }
                if (null != e) {
                    System.out.println("获取数据失败>>>>" + e.toString());
                }
            }
        });
    }

    /**
     *  mPullLoadMoreRecyclerView.scrollToTop() 快速返回顶部
     *  square_item_list.setHasMore(false);//通知控件，已经没数据了
     */
    public void obtainSquareData(final DataCallBackResult result) {
        BmobQuery<Dynamic> query = new BmobQuery<>();
        query.order("-createdAt").setLimit(Parameters.REQUEST_PER_PAGE).setSkip(Parameters.REQUEST_PER_PAGE*(currentNewsPage))
                .include("author").findObjects(new FindListener<Dynamic>() {
            @Override
            public void done(List<Dynamic> list, BmobException e) {
                result.callbackHandler(list,e);
            }
        });

    }
}
