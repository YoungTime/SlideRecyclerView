package com.ryan.slideapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ryan.slide_recyclerview.SlideViewAdapter;

import java.util.List;

public class ShareAdapter extends SlideViewAdapter<Object> {

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
    protected View bindContent(ViewGroup parent, final Object data, int pos) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slide_view,parent,false);
        TextView tv = view.findViewById(R.id.tv_slide);
        tv.setText((String) data);
        view .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, data.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


    @Override
    protected Context getContext() {
        return context;
    }

}
