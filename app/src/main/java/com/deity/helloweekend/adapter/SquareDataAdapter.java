package com.deity.helloweekend.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.deity.helloweekend.R;
import com.deity.helloweekend.entity.Dynamic;
import com.othershe.baseadapter.ViewHolder;
import com.othershe.baseadapter.base.CommonBaseAdapter;

import java.util.List;

/**
 * 广场数据
 * Created by Deity on 2016/12/3.
 */

public class SquareDataAdapter extends CommonBaseAdapter<Dynamic> {
    private Context context;

    public SquareDataAdapter(Context context, List<Dynamic> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder holder, Dynamic data) {
        holder.setText(R.id.user_name,data.getAuthor().getUsername());
//        if (!TextUtils.isEmpty(data.getAuthor().getAvatar().getUrl())){
//            Glide.with(context).load(data.getAuthor().getAvatar().getUrl()).placeholder(R.drawable.ic_launcher).into((ImageView) holder.getView(R.id.user_logo));
//        }
        holder.setText(R.id.content_text,data.getContent());
        if (null!=data.getDynamicImage()) {
            Glide.with(context).load(data.getDynamicImage().getUrl()).placeholder(R.drawable.ic_laucher).into((ImageView) holder.getView(R.id.content_image));
        }
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_square;
    }
}
