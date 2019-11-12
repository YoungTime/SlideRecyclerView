package com.ryan.slideapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ryan.slideapp.recycler.JRSlideViewAdapter;

import java.util.List;

public class ShareAdapter extends JRSlideViewAdapter {

    private Context context;
    private List<Object> strings;

    public ShareAdapter(Context context, List<Object> strings){
        this.context = context;
        this.strings = strings;
    }

    @Override
    protected List<Object> getDataList() {
        return strings;
    }

    @Override
    protected void bindContent(LinearLayout linearLayout, Object data) {
        View view = LayoutInflater.from(linearLayout.getContext()).inflate(R.layout.item_slide_view,linearLayout,false);
        linearLayout.addView(view);
        TextView tv = view.findViewById(R.id.tv_slide);
        tv.setText((String) data);

    }

    @Override
    protected Context getContext() {
        return context;
    }
}
