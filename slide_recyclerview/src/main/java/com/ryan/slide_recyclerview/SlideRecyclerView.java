package com.ryan.slide_recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

public class SlideRecyclerView extends RecyclerView {


    private View curItem; // 当前滑动的 item
    private View lastItem; // 上次滑动的 item
    private int hideWidth; // 菜单栏宽度

    private int xDown,yDown;
    private int mLastX;
    private int mTouchSlop; // 最小滑动距离，防止误滑

    private boolean isMoving; // 是否在滑动
    private Scroller mScroller;

    private static final int MIN_SPEED = 500; // 最小速度


    private VelocityTracker tracker; // 滑动速度追踪器

    private TypedArray typedArray;

    // item 的属性
    private int itemMarLeft;
    private int itemMarRight;
    private int itemMarTop;
    private int itemMarBot;
    private int itemPadLeft;
    private int itemPadRight;
    private int itemPadTop;
    private int itemPadBot;
    private Drawable drawable;

    public SlideRecyclerView(Context context) {
        super(context);
        init();
    }

    public SlideRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setItemAttrs(attrs);
        init();
    }

    public SlideRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setItemAttrs(attrs);
        init();
    }

    private void init(){
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mScroller = new Scroller(getContext());
    }

    private void setItemAttrs(AttributeSet attrs){
        if (typedArray == null){
            typedArray = getContext().obtainStyledAttributes(attrs,R.styleable.SlideRecyclerView);
        }
        itemMarLeft = (int) typedArray.getDimension(R.styleable.SlideRecyclerView_item_margin_left,0);
        itemMarRight = (int) typedArray.getDimension(R.styleable.SlideRecyclerView_item_margin_right,0);
        itemMarTop = (int) typedArray.getDimension(R.styleable.SlideRecyclerView_item_margin_top,0);
        itemMarBot = (int) typedArray.getDimension(R.styleable.SlideRecyclerView_item_margin_bottom,0);
        itemPadLeft = (int) typedArray.getDimension(R.styleable.SlideRecyclerView_item_padding_left,0);
        itemPadRight = (int) typedArray.getDimension(R.styleable.SlideRecyclerView_item_padding_right,0);
        itemPadTop = (int) typedArray.getDimension(R.styleable.SlideRecyclerView_item_padding_top,0);
        itemPadBot = (int) typedArray.getDimension(R.styleable.SlideRecyclerView_item_padding_bottom,0);
        drawable = typedArray.getDrawable(R.styleable.SlideRecyclerView_item_background);
        typedArray.recycle();
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        super.setAdapter(adapter);
        if (getAdapter() != null){
            ((SlideViewAdapter)getAdapter()).setItemAttrs(itemMarLeft,itemMarRight,itemMarTop,itemMarBot,itemPadLeft,itemPadRight,itemPadTop,itemPadBot,drawable);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        addTracker(e);
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                xDown = x;
                yDown = y;
                mLastX = x;
                curItem =  findChildViewUnder(x, y);
                if (lastItem != null && lastItem != curItem && lastItem.getScrollX() != 0){
                    closeMenu();
                }
                if (curItem != null && getAdapter() != null){
                    hideWidth = ((SlideViewAdapter)getAdapter()).getW();
                }else {
                    hideWidth = -1;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                tracker.computeCurrentVelocity(1000);
                int velocityX = (int) Math.abs(tracker.getXVelocity());
                int velocityY = (int) Math.abs(tracker.getYVelocity());
                int moveX = Math.abs(x - xDown);
                int moveY = Math.abs(y - yDown);
                boolean isHorizontalMove = (Math.abs(velocityX) >= MIN_SPEED && velocityX > velocityY || moveX > moveY
                        && moveX > mTouchSlop) && hideWidth > 0 && getScrollState() == 0;
                if (isHorizontalMove){
                    isMoving = true;
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                removeTracker();
                closeMenuNow();
                break;
            default:break;
        }
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        addTracker(e);
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                if (isMoving){
                    int dx = mLastX - x;
                    if (curItem.getScrollX() + dx >= 0 && curItem.getScrollX() + dx <= hideWidth) {
                        curItem.scrollBy(dx, 0);
                    }
                    mLastX = x;
                    return true;
                }else {
                    tracker.computeCurrentVelocity(1000);
                    int velocityX = (int) Math.abs(tracker.getXVelocity());
                    int velocityY = (int) Math.abs(tracker.getYVelocity());
                    int moveX = Math.abs(x - xDown);
                    int moveY = Math.abs(y - yDown);
                    boolean isHorizontalMove = (Math.abs(velocityX) >= MIN_SPEED && velocityX > velocityY
                            || moveX > moveY && moveX > mTouchSlop) && hideWidth > 0 && getScrollState() == 0;
                    if (isHorizontalMove) {
                        int dx = mLastX - x;
                        if (curItem.getScrollX() + dx >= 0 && curItem.getScrollX() + dx <= hideWidth) {
                            curItem.scrollBy(dx, 0);
                        }
                        mLastX = x;
                        isMoving = true;
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (isMoving) {
                    if (!mScroller.isFinished()){
                        mScroller.abortAnimation();
                        lastItem.scrollTo(mScroller.getFinalX(),0);
                    }
                    isMoving = false;
                    lastItem = curItem;
                    tracker.computeCurrentVelocity(1000);
                    int scrollX = lastItem.getScrollX();
                    if (tracker.getXVelocity() >= MIN_SPEED){
                        mScroller.startScroll(scrollX, 0, -scrollX, 0, Math.abs(scrollX));
                    }else if (tracker.getXVelocity() <= -MIN_SPEED){
                        int dx = hideWidth - scrollX;
                        mScroller.startScroll(scrollX, 0, dx, 0, Math.abs(dx));
                    } else if (scrollX > hideWidth / 2) {
                        int dx = hideWidth - scrollX;
                        mScroller.startScroll(scrollX, 0, dx, 0, Math.abs(dx));
                    } else {
                        mScroller.startScroll(scrollX, 0, -scrollX, 0, Math.abs(scrollX));
                    }
                    invalidate();
                } else if (lastItem != null && lastItem.getScrollX() != 0){
                    closeMenu();
                }
                removeTracker();
                break;
            default:break;
        }
        return super.onTouchEvent(e);
    }


    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            if (isInWindow(lastItem)){
                lastItem.scrollTo(mScroller.getCurrX(), 0);
                invalidate();
            }else {
                mScroller.abortAnimation();
                lastItem.scrollTo(mScroller.getFinalX(),0);
            }
        }
    }

    /**
     * 使用Scroller关闭菜单栏
     */
    public void closeMenu(){
        mScroller.startScroll(lastItem.getScrollX(),0, -lastItem.getScrollX(), 0 ,500);
        invalidate();
    }

    /**
     * 即刻关闭菜单栏
     */
    public void closeMenuNow(){
        if (lastItem != null && lastItem.getScrollX() != 0) {
            lastItem.scrollTo(0, 0);
        }
    }

    /**
     * 获取VelocityTracker实例，并为其添加事件
     * @param e 触碰事件
     */
    private void addTracker(MotionEvent e){
        if (tracker == null){
            tracker = VelocityTracker.obtain();
        }
        tracker.addMovement(e);
    }

    /**
     * 释放VelocityTracker
     */
    private void removeTracker(){
        if (tracker != null){
            tracker.clear();
            tracker.recycle();
            tracker = null;
        }
    }

    /**
     * 判断该itemView是否显示在屏幕内
     * @param view itemView
     * @return isInWindow
     */
    private boolean isInWindow(View view){
        if (getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager manager = (LinearLayoutManager) getLayoutManager();
            int firstPosition = manager.findFirstVisibleItemPosition();
            int lastPosition = manager.findLastVisibleItemPosition();
            int currentPosition = manager.getPosition(view);
            return currentPosition >= firstPosition && currentPosition <= lastPosition;
        }
        return true;
    }

}