package com.scanmaster.commonlibrary.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.scanmaster.commonlibrary.R;

/**
 * author:  zhouchaoxiang
 * date:    2018/9/19
 * explain:
 */
public class SwipeBackLayout extends FrameLayout {

    private int mEdgeSize;
    private boolean mSwipeEnable;

    public SwipeBackLayout(@NonNull Context context) {
        this(context, null);
    }

    public SwipeBackLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeBackLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SwipeBackLayout);
        mEdgeSize = array.getDimensionPixelSize(R.styleable.SwipeBackLayout_edge_size, 0);
        int edgeFlag = array.getInteger(R.styleable.SwipeBackLayout_edge_flag, 0);
        array.recycle();

    }

    public void setSwipeEnable(boolean enable) {
        mSwipeEnable = enable;
    }

    public void onAttachActivity(Activity activity) {
        TypedArray array = activity.getTheme().obtainStyledAttributes(new int[android.R.attr.windowBackground]);
        int resourceId = array.getResourceId(0, 0);
        array.recycle();
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View decorChild = decorView.getChildAt(0);
        decorChild.setBackgroundResource(resourceId);
        decorView.removeView(decorView);
        addView(decorChild);
        decorView.addView(this);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!mSwipeEnable) {
            return super.onInterceptTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return false;
            case MotionEvent.ACTION_MOVE:
                return false;
            case MotionEvent.ACTION_CANCEL:
                return false;
            case MotionEvent.ACTION_UP:
                return false;
            default:
                return super.onInterceptTouchEvent(ev);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        return super.drawChild(canvas, child, drawingTime);
    }
}
