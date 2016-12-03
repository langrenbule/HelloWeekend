package com.deity.helloweekend.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        return view;
    }

    public void initRecycleView() {
        mSquareItemAdapter = new SquareItemAdapter(getActivity());
        square_item_list.setLinearLayout();
        square_item_list.setAdapter(mSquareItemAdapter);
        square_item_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), NewsContentActivity.class);
//                intent.putExtra("url", data.getNewBornArticleUrl());
//                intent.putExtra("imageUrl", data.getNewBornImageUrl());
//                intent.putExtra("newsTitle", data.getNewBornTitle());
//                startActivity(intent);
            }
        });
        square_item_list.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
//                mSquareOperator.obtainSquareItemList(getActivity(),1);
                obtainSquareData(1);
            }

            @Override
            public void onLoadMore() {
                square_item_list.setFooterViewText("loading");
                obtainSquareData(currentNewsPage);
            }
        });

    }

    /**
     *  mPullLoadMoreRecyclerView.scrollToTop() 快速返回顶部
     * @param currentNewsPage
     */
    public void obtainSquareData(int currentNewsPage) {
        BmobQuery<Dynamic> query = new BmobQuery<>();
        query.order("-createdAt").setLimit(Parameters.REQUEST_PER_PAGE)//.setSkip(Parameters.REQUEST_PER_PAGE*(currentNewsPage))
                .include("author").findObjects(new FindListener<Dynamic>() {
            @Override
            public void done(List<Dynamic> list, BmobException e) {
                square_item_list.setPullLoadMoreCompleted();
                if (null != list && !list.isEmpty()) {
                    mSquareItemAdapter.setData(list);
                    mSquareItemAdapter.notifyDataSetChanged();
                }
                if (null != e) {
                    System.out.println("获取数据失败>>>>" + e.toString());
                }
            }
        });

    }
}
