package com.scanmaster.commonlibrary.activity;

import android.os.Bundle;

import me.imid.swipebacklayout.lib.SwipeBackLayout;

/**
 * author:  zhouchaoxiang
 * date:    2018/9/19
 * explain:
 */
public abstract class InitActivity extends me.imid.swipebacklayout.lib.app.SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackLayout swipeBackLayout = getSwipeBackLayout();
        swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        swipeBackLayout.setEdgeSize(150);
        initSome(savedInstanceState);
        initData();
        initView();
        initEvent();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    protected void initSome(Bundle savedInstanceState) {
    }

    protected abstract void initData();

    protected abstract void initView();

    protected abstract void initEvent();


}
