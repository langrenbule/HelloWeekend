package com.deity.helloweekend.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deity.helloweekend.R;
import com.deity.helloweekend.entity.Dynamic;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 广场数据适配器
 * Created by Deity on 2016/11/20.
 */

public class SquareItemAdapter extends RecyclerView.Adapter<SquareItemAdapter.ViewHolder> implements  View.OnClickListener{

    private RecycleViewOnClickListener recycleViewOnClickListener;
    private LayoutInflater inflater;
    private Context context;
    private List<Dynamic> mSquareItemList;

    public SquareItemAdapter(Context context){
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(List<Dynamic> mSquareItemList){
        this.mSquareItemList = mSquareItemList;
    }

    public interface RecycleViewOnClickListener{
        void onItemClick(View view,Dynamic data);
    }

    public RecycleViewOnClickListener getRecycleViewOnClickListener() {
        return recycleViewOnClickListener;
    }

    public void setRecycleViewOnClickListener(RecycleViewOnClickListener recycleViewOnClickListener) {
        this.recycleViewOnClickListener = recycleViewOnClickListener;
    }

    @Override
    public void onClick(View view) {
        if (null!=recycleViewOnClickListener){
            recycleViewOnClickListener.onItemClick(view, (Dynamic) view.getTag());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_square, parent, false);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return new SquareItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Dynamic data = mSquareItemList.get(position);
        holder.userName.setText(data.getAuthor().getUsername());
//        Glide.with(context).load(data.getAuthor().getAvatar().getUrl()).placeholder(R.drawable.ic_launcher).into(holder.userImageUrl);
        holder.content_text.setText(data.getContent());
        if (null!=data.getDynamicImage()) {
            Glide.with(context).load(data.getDynamicImage().getUrl()).placeholder(R.drawable.ic_launcher).into(holder.content_image);
        }
        holder.itemView.setTag(data);
    }

    @Override
    public int getItemCount() {
        if (null==mSquareItemList) return 0;
        return mSquareItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.user_logo) public ImageView userImageUrl;
        @Bind(R.id.user_name) public TextView userName;
        @Bind(R.id.item_action_fav) public ImageView action_fav;//喜好
        @Bind(R.id.content_text) public TextView content_text;
        @Bind(R.id.content_image) public ImageView content_image;
        @Bind(R.id.item_action_love) public TextView item_action_love;
        @Bind(R.id.item_action_hate) public TextView item_action_hate;
        @Bind(R.id.item_action_comment) public TextView item_action_comment;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
