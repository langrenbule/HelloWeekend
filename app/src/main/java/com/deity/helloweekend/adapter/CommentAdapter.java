package com.deity.helloweekend.adapter;

import android.content.Context;

import com.deity.helloweekend.R;
import com.deity.helloweekend.entity.Comment;
import com.othershe.baseadapter.ViewHolder;
import com.othershe.baseadapter.base.CommonBaseAdapter;

import java.util.List;

/**
 * Created by Deity on 2016/12/4.
 */

public class CommentAdapter extends CommonBaseAdapter<Comment> {

    public CommentAdapter(Context context, List<Comment> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, Comment data) {
        holder.setText(R.id.content_comment,data.getCurrentComment());
        holder.setText(R.id.userName_comment,data.getUser().getNickName());
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_comment;
    }
}
