package com.ryan.slideapp.recycler;

import android.view.View;

public class SlideItem {

    private String title;
    private int iconId;
    private int bgColorId;
    private View.OnClickListener listener;

    public SlideItem(String title, int iconId, int bgColorId, View.OnClickListener listener){
        this.title = title;
        this.iconId = iconId;
        this.bgColorId = bgColorId;
        this.listener = listener;
    }


    public String getTitle() {
        return title;
    }

    public int getIconId() {
        return iconId;
    }

    public int getBgColorId() {
        return bgColorId;
    }

    public View.OnClickListener getListener() {
        return listener;
    }
}
