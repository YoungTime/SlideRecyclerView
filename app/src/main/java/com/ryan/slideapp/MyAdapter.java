package com.ryan.slideapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ryan.slide_recyclerview.SlideViewAdapter;

import java.util.List;

public class MyAdapter extends SlideViewAdapter<MsgEntity> {

    private List<MsgEntity> mDatas;
    private Context mContext;

    public MyAdapter(List<MsgEntity> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
    }

    /**
     * @return 你需要在 Item 中使用的数据列表
     */
    @Override
    protected List<MsgEntity> getDataList() {
        return mDatas;
    }

    /**
     * 绑定 Item 布局，也可根据 data 类型返回不同的 View
     * @param parent 父布局
     * @param data 单个布局的 Item 的数据
     * @return 返回单个 Item 的布局
     */
    @Override
    protected View bindContent(ViewGroup parent, MsgEntity data,int pos) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slide_view,parent,false);
        // 操作 Item 中的子 View
        TextView textView = view.findViewById(R.id.tv_slide);
        textView.setText(data.getName());
        return view;
    }

    /**
     * @return 需要返回使用 SlideRecyclerview 的上下文
     */
    @Override
    protected Context getContext() {
        return mContext;
    }
}
