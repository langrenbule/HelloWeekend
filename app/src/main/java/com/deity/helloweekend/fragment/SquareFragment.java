package com.deity.helloweekend.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deity.helloweekend.R;
import com.deity.helloweekend.adapter.EndlessRecyclerOnScrollListener;
import com.deity.helloweekend.adapter.HeaderViewRecyclerAdapter;
import com.deity.helloweekend.adapter.SquareItemAdapter;
import com.deity.helloweekend.entity.SquareItem;
import com.deity.helloweekend.mvp.model.SquareOperator;
import com.deity.helloweekend.ui.BaseFragment;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.deity.helloweekend.R.id.widget_refresh;

/**
 * 交友广场
 * Created by Deity on 2016/11/20.
 */

public class SquareFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    @Bind(widget_refresh) public SwipeRefreshLayout widgetRefresh;
    @Bind(R.id.square_item_list) public RecyclerView square_item_list;

    private SquareItemAdapter mSquareItemAdapter;
    private HeaderViewRecyclerAdapter headerViewRecyclerAdapter;
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
        ButterKnife.bind(this,view);
        widgetRefresh.setOnRefreshListener(this);
        initRecycleView();
        return view;
    }

    private void createLoadMoreView() {
        View loadMoreView = LayoutInflater
                .from(getActivity())
                .inflate(R.layout.load_more_view, square_item_list, false);
        headerViewRecyclerAdapter.addFooterView(loadMoreView);
    }

    public void initRecycleView(){
        mSquareItemAdapter = new SquareItemAdapter(getActivity());
        headerViewRecyclerAdapter = new HeaderViewRecyclerAdapter(mSquareItemAdapter);
        square_item_list.setAdapter(headerViewRecyclerAdapter);
        mSquareItemAdapter.setRecycleViewOnClickListener(new SquareItemAdapter.RecycleViewOnClickListener() {
            @Override
            public void onItemClick(View view, SquareItem data) {
//                Intent intent = new Intent(getActivity(), NewsContentActivity.class);
//                intent.putExtra("url", data.getNewBornArticleUrl());
//                intent.putExtra("imageUrl", data.getNewBornImageUrl());
//                intent.putExtra("newsTitle", data.getNewBornTitle());
//                startActivity(intent);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        square_item_list.setHasFixedSize(true);
        square_item_list.setLayoutManager(linearLayoutManager);
        createLoadMoreView();
        square_item_list.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                headerViewRecyclerAdapter.setFooterViewsVisable();
                currentNewsPage = currentPage;
                mSquareOperator.obtainSquareItemList(getActivity(),currentNewsPage);
            }
        });
    }

    /**
     * Called when a swipe gesture triggers a refresh.
     */
    @Override
    public void onRefresh() {
//        mSquareOperator.obtainSquareItemList(getActivity(),1);
        List<SquareItem> itemList = mSquareOperator.monitorList();
        mSquareItemAdapter.setData(itemList);
        mSquareItemAdapter.notifyDataSetChanged();
        widgetRefresh.setRefreshing(false);

    }
}
