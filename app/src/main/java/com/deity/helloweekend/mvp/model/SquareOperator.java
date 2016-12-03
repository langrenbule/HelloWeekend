package com.deity.helloweekend.mvp.model;

import android.content.Context;

import com.deity.helloweekend.data.Parameters;
import com.deity.helloweekend.entity.SquareItem;
import com.deity.helloweekend.entity.User;
import com.deity.helloweekend.utils.SmallUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
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
        void fail(Exception errorDescription);
    }

    public IFindSquareItemsListener getiFindSquareItemsListener() {
        return iFindSquareItemsListener;
    }

    public void setiFindSquareItemsListener(IFindSquareItemsListener iFindSquareItemsListener) {
        this.iFindSquareItemsListener = iFindSquareItemsListener;
    }

    public void obtainSquareItemList(Context context, int page){
        BmobQuery<SquareItem> query = new BmobQuery<>();
        query.order("-createdAt").addWhereLessThan("createdAt", SmallUtils.getCurrentTime()).setLimit(Parameters.REQUEST_PER_PAGE);
        query.setSkip(page*Parameters.REQUEST_PER_PAGE);
        query.include("author");
        query.findObjects(new FindListener<SquareItem>() {
            @Override
            public void done(List<SquareItem> list, BmobException e) {

                if (null!=iFindSquareItemsListener){
                    if (null!=list) iFindSquareItemsListener.success(list);
                    if (null!=e)  iFindSquareItemsListener.fail(e);
                }

            }
//
//            @Override
//            public void onSuccess(List<SquareItem> list) {
//                if (null!=iFindSquareItemsListener) iFindSquareItemsListener.success(list);
//            }
//
//            @Override
//            public void onError(int i, String s) {
//                Log.i(SquareOperator.class.getSimpleName(),"obtainSquareItemList fail>>>"+i+"|"+s);
//                if (null!=iFindSquareItemsListener) iFindSquareItemsListener.fail(i,s);
//            }
        });
    }

    public List<SquareItem> monitorList(){
        List<SquareItem> list = new ArrayList<>();
        for (int i=0;i<30;i++){
            SquareItem item = new SquareItem();
            item.setAuthor(new User("天神","123456","546024423@qq.com"));
            item.setComment(10);
            item.setHateNum(1);
            item.setLoveNum(1);
            item.setWeibo("测试用的微博");
            list.add(item);
        }
        return list;
    }
}
