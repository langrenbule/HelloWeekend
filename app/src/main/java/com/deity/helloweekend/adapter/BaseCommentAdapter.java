package com.deity.helloweekend.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.deity.helloweekend.R;
import com.deity.helloweekend.entity.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * 评论，使用ListView，因为跟ScrollView配合使用的情况下，RecycleView会有一些问题
 * Created by Deity on 2016/12/9.
 */

public class BaseCommentAdapter extends BaseAdapter {

    /**评论数据*/
    private List<Comment> datas= new ArrayList<>();
    private Context context;

    public BaseCommentAdapter(Context context){
        this.context = context;
    }

    public void setDatas(List<Comment> datas) {
        this.datas = datas;
    }

    public void addDatas(List<Comment> datas){
        this.datas.addAll(datas);
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        if (null==datas||datas.isEmpty()) return 0;
        return datas.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null==convertView){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_comment,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.comment_index = (TextView) convertView.findViewById(R.id.comment_index);
            viewHolder.content_comment = (TextView) convertView.findViewById(R.id.content_comment);
            viewHolder.userName_comment = (TextView) convertView.findViewById(R.id.userName_comment);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Comment comment = datas.get(position);
        viewHolder.comment_index.setText((position+1)+"楼");
        viewHolder.content_comment.setText(comment.getCurrentComment());
        String nickName;
        if (TextUtils.isEmpty(comment.getUser().getNickName())){
            nickName = "匿名";
        }else {
            nickName = comment.getUser().getNickName();
        }
        viewHolder.userName_comment.setText(nickName);
        return convertView;
    }

    class ViewHolder{
        /**用户名*/
        public TextView userName_comment;
        /**楼层号*/
        public TextView comment_index;
        /**评论*/
        public TextView content_comment;

    }
}
