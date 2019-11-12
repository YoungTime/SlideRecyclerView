package com.ryan.slideapp.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ryan.slideapp.R;

import java.util.List;

public class JRSlideItemAdapter extends RecyclerView.Adapter<JRSlideItemAdapter.JRSlideItemViewHolder> {


    private List<SlideItem> items;
    private Context context;

    public JRSlideItemAdapter(List<SlideItem> items, Context context){
        this.items = items;
        this.context = context;
    }

    @Override
    public JRSlideItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_slide_item_view,parent,false);
        return new JRSlideItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JRSlideItemViewHolder holder, int position) {
        holder.title.setText(items.get(position).getTitle());
        holder.itemView.setOnClickListener(items.get(position).getListener());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class JRSlideItemViewHolder extends RecyclerView.ViewHolder{

        TextView title;

        public JRSlideItemViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_slide_item_text);
        }
    }
}
