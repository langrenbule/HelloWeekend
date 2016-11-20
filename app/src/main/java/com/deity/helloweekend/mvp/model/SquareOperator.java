package com.deity.helloweekend.mvp.model;

import android.content.Context;

import com.deity.helloweekend.data.Parameters;
import com.deity.helloweekend.entity.SquareItem;
import com.deity.helloweekend.utils.SmallUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * 广场用户数据获取
 * Created by Deity on 2016/11/20.
 */

public class SquareOperator {
    private IFindSquareItemsListener iFindSquareItemsListener;
    private Context context;

    public SquareOperator(Context context){
        this.context = context;
    }

    public interface IFindSquareItemsListener{
        void success(List<SquareItem> list);
        void fail(int errorCode,String errorDescription);
    }

    public IFindSquareItemsListener getiFindSquareItemsListener() {
        return iFindSquareItemsListener;
    }

    public void setiFindSquareItemsListener(IFindSquareItemsListener iFindSquareItemsListener) {
        this.iFindSquareItemsListener = iFindSquareItemsListener;
    }

    public void obtainSquareItemList(Context context, int page){
        BmobQuery<SquareItem> query = new BmobQuery<SquareItem>();
        query.order("-createdAt").addWhereLessThan("createdAt", SmallUtils.getCurrentTime()).setLimit(Parameters.REQUEST_PER_PAGE);
        query.setSkip(page*Parameters.REQUEST_PER_PAGE);
        query.include("author");
        query.findObjects(context, new FindListener<SquareItem>() {
            @Override
            public void onSuccess(List<SquareItem> list) {
                if (null!=iFindSquareItemsListener) iFindSquareItemsListener.success(list);
            }

            @Override
            public void onError(int i, String s) {
                if (null!=iFindSquareItemsListener) iFindSquareItemsListener.fail(i,s);
            }
        });
    }
}
