package com.ryan.slide_recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import static com.ryan.slide_recyclerview.ScreenUtil.dp2px;


public abstract class SlideViewAdapter extends RecyclerView.Adapter<SlideViewAdapter.JRSlideViewHolder> {


    private List<SlideItem> slideItems;
    private SlideItemAdapter itemAdapter;
    private LinearLayoutManager manager;

    protected abstract List<Object> getDataList();

    protected abstract void bindContent(LinearLayout linearLayout, Object data);

    protected abstract Context getContext();

    public SlideViewAdapter addSlideItem(SlideItem item) {
        if (slideItems == null) {
            slideItems = new ArrayList<SlideItem>();
        }
        slideItems.add(item);
        return this;
    }

    @Override
    public JRSlideViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_slide_view, parent, false);
        if (slideItems == null) {
            slideItems = new ArrayList<SlideItem>();
        }
        itemAdapter = new SlideItemAdapter(slideItems, parent.getContext());

        return new JRSlideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final JRSlideViewHolder holder, int position) {

        bindContent(holder.llContent, getDataList().get(position));
        manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.rvHide.setLayoutManager(manager);
        itemAdapter.setPos(position);
        holder.rvHide.setAdapter(itemAdapter);
    }

    @Override
    public int getItemCount() {
        return getDataList() == null ? 0 : getDataList().size();
    }


    class JRSlideViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llContent;
        RecyclerView rvHide;

        public JRSlideViewHolder(View itemView) {
            super(itemView);
            llContent = itemView.findViewById(R.id.ll_slide_context);
            rvHide = itemView.findViewById(R.id.rv_slide_hide);
        }
    }

    int getW(){
        return slideItems.size() * dp2px(60,getContext());
    }
}
