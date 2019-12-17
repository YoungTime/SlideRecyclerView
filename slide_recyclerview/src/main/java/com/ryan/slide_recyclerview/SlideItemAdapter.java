package com.ryan.slide_recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SlideItemAdapter extends RecyclerView.Adapter<SlideItemAdapter.JRSlideItemViewHolder> {


    private List<SlideItem> items;
    private Context context;
    private int pos;

    SlideItemAdapter(List<SlideItem> items, Context context){
        this.items = items;
        this.context = context;
    }

    void setPos(int pos){
        this.pos = pos;
    }

    @Override
    public JRSlideItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_slide_item_view,parent,false);
        return new JRSlideItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JRSlideItemViewHolder holder, final int position) {
        holder.title.setText(items.get(position).getTitle());
        if (items.get(position).getIconId() != 0){
            holder.imageView.setImageResource(items.get(position).getIconId());
        }
        if (items.get(position).getTitleColorId() != 0){
            holder.title.setTextColor(items.get(position).getIconId());
        }
        if (items.get(position).getTitleSize() != 0){
            holder.title.setTextSize(items.get(position).getTitleSize());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.get(position).getListener().onClick(v,pos);
            }
        });
        holder.itemView.setBackgroundResource(items.get(position).getBgId());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class JRSlideItemViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        ImageView imageView;

        JRSlideItemViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_slide_item_title);
            imageView = itemView.findViewById(R.id.iv_slide_item_icon);
        }
    }


    public interface OnItemClickListener{
        void onClick(View view, int pos);
    }
}
