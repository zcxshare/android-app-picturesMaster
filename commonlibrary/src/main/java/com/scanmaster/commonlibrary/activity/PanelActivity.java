package com.scanmaster.commonlibrary.activity;

import android.os.Bundle;

import com.scanmaster.commonlibrary.panel.Panels;

/**
 * author:  zhouchaoxiang
 * date:    2018/9/20
 * explain:
 */
public abstract class PanelActivity extends InitActivity {
    protected Panels mPanels = new Panels();

    public Panels getPanels() {
        return mPanels;
    }

    @Override
    protected void initSome(Bundle savedInstanceState) {
        initPanels(mPanels);
        mPanels.onCreate(savedInstanceState);
    }

    private void initPanels(Panels panels) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        mPanels.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mPanels.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPanels.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPanels.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPanels.onDestroy();
    }
}
