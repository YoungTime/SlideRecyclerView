package com.ryan.slide_recyclerview;

import android.content.Context;
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

    private int mTouchSlop; // 最小滑动距离，防止误滑

    private boolean isMoving; // 是否在滑动
    private Scroller mScroller;

    private static final int MIN_SPEED = 500; // 最小速度


    private VelocityTracker tracker; // 滑动速度追踪器

    /**触碰末次的横坐标*/
    private int mLastX;



    public SlideRecyclerView(Context context) {
        super(context);
        init();
    }

    public SlideRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlideRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mScroller = new Scroller(getContext());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        addTracker(e);
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                //若Scroller处于动画中，则终止动画
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                xDown = x;
                yDown = y;
                mLastX = x;
                //获取点击区域所在的itemView
                curItem =  findChildViewUnder(x, y);
                //在点击区域以外的itemView开着菜单，则关闭菜单
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
                //满足如下条件其一则判定为水平滑动：
                //1、水平速度大于竖直速度,且水平速度大于最小速度
                //2、水平位移大于竖直位移,且大于最小移动距离
                //必需条件：itemView菜单栏宽度大于0，且recyclerView处于静止状态（即并不在竖直滑动和拖拽）
                boolean isHorizontalMove = (Math.abs(velocityX) >= MIN_SPEED && velocityX > velocityY || moveX > moveY
                        && moveX > mTouchSlop) && hideWidth > 0 && getScrollState() == 0;
                if (isHorizontalMove){
                    //设置其已处于水平滑动状态，并拦截事件
                    isMoving = true;
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                removeTracker();
                //itemView以及其子view触发触碰事件(点击、长按等)，菜单未关闭则直接关闭
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
                //若已处于水平滑动状态，则随手指滑动，否则进行条件判断
                if (isMoving){
                    int dx = mLastX - x;
                    //让itemView在规定区域随手指移动
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
                    //根据水平滑动条件判断，是否让itemView跟随手指滑动
                    //这里重新判断是避免itemView中不拦截ACTION_DOWN事件，则后续ACTION_MOVE并不会走onInterceptTouchEvent()方法
                    boolean isHorizontalMove = (Math.abs(velocityX) >= MIN_SPEED && velocityX > velocityY
                            || moveX > moveY && moveX > mTouchSlop) && hideWidth > 0 && getScrollState() == 0;
                    if (isHorizontalMove) {
                        int dx = mLastX - x;
                        //让itemView在规定区域随手指移动
                        if (curItem.getScrollX() + dx >= 0 && curItem.getScrollX() + dx <= hideWidth) {
                            curItem.scrollBy(dx, 0);
                        }
                        mLastX = x;
                        //设置正处于水平滑动状态
                        isMoving = true;
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (isMoving) {
                    //先前没结束的动画终止，并直接到终点
                    if (!mScroller.isFinished()){
                        mScroller.abortAnimation();
                        lastItem.scrollTo(mScroller.getFinalX(),0);
                    }
                    isMoving = false;
                    //已放手，即现滑动的itemView成了末次滑动的itemView
                    lastItem = curItem;
                    tracker.computeCurrentVelocity(1000);
                    int scrollX = lastItem.getScrollX();
                    //若速度大于正方向最小速度，则关闭菜单栏；若速度小于反方向最小速度，则打开菜单栏
                    //若速度没到判断条件，则对菜单显示的宽度进行判断打开/关闭菜单
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
                    //若不是水平滑动状态，菜单栏开着则关闭
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
                //若处于动画的itemView滑出屏幕，则终止动画，并让其到达结束点位置
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