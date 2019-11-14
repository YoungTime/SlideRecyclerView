package com.ryan.slide_recyclerview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
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

    private int itemMarLeft;
    private int itemMarRight;
    private int itemMarTop;
    private int itemMarBot;
    private int itemPadLeft;
    private int itemPadRight;
    private int itemPadTop;
    private int itemPadBot;
    private Drawable drawable;

    protected abstract List<Object> getDataList();

    protected abstract View bindContent(ViewGroup parent, Object data);

    protected abstract Context getContext();

    public SlideViewAdapter addSlideItem(SlideItem item) {
        if (slideItems == null) {
            slideItems = new ArrayList<SlideItem>();
        }
        slideItems.add(item);
        return this;
    }


    void setItemAttrs(float itemMarLeft, float itemMarRight, float itemMarTop, float itemMarBot, float itemPadLeft, float itemPadRight
            , float itemPadTop, float itemPadBot, Drawable drawable){
        this.itemMarLeft = (int) itemMarLeft;
        this.itemMarRight = (int) itemMarRight;
        this.itemMarTop = (int) itemMarTop;
        this.itemMarBot = (int) itemMarBot;
        this.itemPadLeft = (int) itemPadLeft;
        this.itemPadRight = (int) itemPadRight;
        this.itemPadTop = (int) itemPadTop;
        this.itemPadBot = (int) itemPadBot;
        this.drawable = drawable;
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
        setItemAttrs(holder.clItem);
        holder.llContent.addView(bindContent(holder.llContent, getDataList().get(position)));
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
        ConstraintLayout clItem;

        public JRSlideViewHolder(View itemView) {
            super(itemView);
            llContent = itemView.findViewById(R.id.ll_slide_context);
            rvHide = itemView.findViewById(R.id.rv_slide_hide);
            clItem = itemView.findViewById(R.id.cl_slide_item);
        }
    }

    int getW(){
        return slideItems.size() * dp2px(72,getContext());
    }

    private void setItemAttrs(View view){
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
        params.setMargins(itemMarLeft,itemMarTop,itemMarRight,itemMarBot);
        view.setPadding(itemPadLeft,itemPadTop,itemPadRight,itemPadBot);
        view.setBackground(drawable);
    }
}
