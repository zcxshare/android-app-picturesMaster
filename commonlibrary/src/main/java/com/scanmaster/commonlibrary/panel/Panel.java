package com.scanmaster.commonlibrary.panel;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.ButterKnife;

/**
 * author:  zhouchaoxiang
 * date:    2018/9/20
 * explain:
 */
public abstract class Panel {
    private Panels mPanels;
    private Context mContext;
    private View mView;

    public Panel(Context context, @LayoutRes int layoutId) {
        mPanels = new Panels();
        mContext = context;
        LayoutInflater from = LayoutInflater.from(mContext);
        mView = from.inflate(layoutId, null);
        ButterKnife.bind(this, mView);
    }

    public View getView() {
        return mView;
    }

    public void onAttach() {
    }

    public void onCreate(Bundle savedInstanceState) {
        initSome(savedInstanceState);
        initPanel(mPanels);
        mPanels.onCreate(savedInstanceState);
        initData();
        initView(mView);
        initEvent();
    }

    private void initPanel(Panels panels) {
    }

    protected void initSome(Bundle savedInstanceState) {
    }

    protected abstract void initData();

    protected abstract void initView(View view);

    protected abstract void initEvent();

    public void onCreateView() {
        mPanels.onCreateView();
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        mPanels.onActivityCreated(savedInstanceState);
    }

    public void onStart() {
        mPanels.onStart();
    }

    public void onRestart() {
        mPanels.onRestart();
    }

    public void onResume() {
        mPanels.onResume();
    }

    public void onPause() {
        mPanels.onPause();
    }

    public void onStop() {
        mPanels.onStop();
    }

    public void onDestroyView() {
        mPanels.onDestroyView();
    }

    public void onDestroy() {
        mContext = null;
        mView = null;
        mPanels.onDestroy();
        mPanels = null;
    }

}
