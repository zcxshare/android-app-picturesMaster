package com.scanmaster.commonlibrary.widget;

import android.content.Context;
import android.nfc.Tag;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.scanmaster.commonlibrary.R;
import com.scanmaster.commonlibrary.utils.PxUtils;
import com.scanmaster.commonlibrary.utils.StatusBarUtil;

import java.lang.reflect.Field;


/**
 * Created by yyj on 2018/07/16. email: 2209011667@qq.com
 * Ace 艾斯，四皇之一，月光岛领主。
 */

public class AceNestedScrollView extends NestedScrollView {
    private static final String TAG = "AceNestedScrollView";
    private boolean isNeedScroll = true;
    private int mScrollHeight;
    private boolean isOpen = false;//轮播图起状态

    public AceNestedScrollView(@NonNull Context context) {
        this(context, null, 0);
    }

    public AceNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AceNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                return isNeedScroll;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                int scrollY = getScrollY();
                Log.i(TAG, "onTouchEvent: up -- mScrollHeight:" + mScrollHeight + "--scrollY:" + scrollY);
                if (scrollY >= mScrollHeight) break;
                if (isOpen) {
                    if (scrollY > mScrollHeight / 5) {
                        scrollA2B(0, mScrollHeight);
//                        myRunnable.setAB(scrollY, mScrollHeight);
//                        this.scrollTo(0, mUpHeight);
                    } else {
//                        myRunnable.setAB(scrollY, 0);
                        scrollA2B(0, 0);
                    }
                } else {
                    if (scrollY < mScrollHeight * 4 / 5) {
//                        myRunnable.setAB(scrollY, 0);
                        scrollA2B(0, 0);
                    } else {
//                        myRunnable.setAB(scrollY, mBgHeight);
                        scrollA2B(0, mScrollHeight);
                    }
                }
//                handler.postDelayed(myRunnable, 1);
                return true;
            case MotionEvent.ACTION_DOWN:
                if (getScrollY() > mScrollHeight) break;
//                handler.removeCallbacks(myRunnable);
                break;


        }
        return super.onTouchEvent(ev);
    }

    private void scrollA2B(int a, int b) {
        Log.i(TAG, "scrollA2B: a:" + a + "---b:" + b);
        smoothScrollTo(a, b);
        isOpen = b == 0;
    }

    public void setNeedScroll(boolean isNeedScroll) {
        this.isNeedScroll = isNeedScroll;
    }

    /**
     * 设置滑动的高度
     *
     * @param height 滑动的高度
     */
    public void setScrollHeight(int height) {
        mScrollHeight = height;
    }

    public int getScrollHeight() {
        return mScrollHeight;
    }
}
