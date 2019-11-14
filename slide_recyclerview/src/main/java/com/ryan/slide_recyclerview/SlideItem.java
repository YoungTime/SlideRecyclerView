package com.ryan.slide_recyclerview;


public class SlideItem {

    private String title;
    private int iconId;
    private int bgId;
    private SlideItemAdapter.OnItemClickListener listener;
    private int titleColorId;
    private float titleSize;

    public SlideItem(String title, int iconId, SlideItemAdapter.OnItemClickListener listener){
        this.title = title;
        this.iconId = iconId;
        this.listener = listener;
    }

    public SlideItem(String title, int iconId, int bgId,int titleColorId,float titleSize, SlideItemAdapter.OnItemClickListener listener){
        this.title = title;
        this.iconId = iconId;
        this.bgId = bgId;
        this.titleColorId = titleColorId;
        this.titleSize = titleSize;
        this.listener = listener;
    }


    String getTitle() {
        return title;
    }

    int getIconId() {
        return iconId;
    }

    int getBgId() {
        return bgId;
    }

    SlideItemAdapter.OnItemClickListener getListener() {
        return listener;
    }

    int getTitleColorId() {
        return titleColorId;
    }

    float getTitleSize() {
        return titleSize;
    }
}
