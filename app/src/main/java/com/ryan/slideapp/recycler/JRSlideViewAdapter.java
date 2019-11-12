package com.ryan.slideapp.recycler;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.ryan.slideapp.R;

import java.util.ArrayList;
import java.util.List;

public abstract class JRSlideViewAdapter extends RecyclerView.Adapter<JRSlideViewAdapter.JRSlideViewHolder> {


    private List<SlideItem> slideItems;
    private JRSlideItemAdapter itemAdapter;
    private LinearLayoutManager manager;
    protected abstract List<Object> getDataList();

    protected abstract void bindContent(LinearLayout linearLayout, Object data);
    protected abstract Context getContext();

    public JRSlideViewAdapter addSlideItem(SlideItem item){
        if (slideItems == null){
            slideItems = new ArrayList<SlideItem>();
        }
        slideItems.add(item);
        return this;
    }

    @Override
    public JRSlideViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_slide_view,parent,false);
        if (slideItems == null){
            slideItems = new ArrayList<SlideItem>();
        }
        itemAdapter = new JRSlideItemAdapter(slideItems,parent.getContext());
        manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        return new JRSlideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JRSlideViewHolder holder, int position) {
        bindContent(holder.llContent,getDataList().get(position));
        holder.rvHide.setLayoutManager(manager);
        holder.rvHide.setAdapter(itemAdapter);
    }

    @Override
    public int getItemCount() {
        return getDataList() == null ? 0:getDataList().size();
    }

    class JRSlideViewHolder extends RecyclerView.ViewHolder{

        LinearLayout llContent;
        RecyclerView rvHide;

        public JRSlideViewHolder(View itemView) {
            super(itemView);
            llContent = itemView.findViewById(R.id.ll_slide_context);
            rvHide = itemView.findViewById(R.id.rv_slide_hide);
        }
    }
}
