package com.ryan.slideapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ryan.slide_recyclerview.SlideViewAdapter;

import java.util.List;

public class ShareAdapter extends SlideViewAdapter {

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
    protected void bindContent(LinearLayout linearLayout, final Object data) {
        View view = LayoutInflater.from(linearLayout.getContext()).inflate(R.layout.item_slide_view,linearLayout,false);
        linearLayout.addView(view);
        TextView tv = view.findViewById(R.id.tv_slide);
        tv.setText((String) data);
        view .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, (String) data,Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected Context getContext() {
        return context;
    }
}
