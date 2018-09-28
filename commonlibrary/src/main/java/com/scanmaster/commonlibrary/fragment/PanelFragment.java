package com.scanmaster.commonlibrary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.scanmaster.commonlibrary.panel.Panels;

import java.time.temporal.ValueRange;

/**
 * author:  zhouchaoxiang
 * date:    2018/9/26
 * explain:
 */
public abstract class PanelFragment extends InitFragment {
    protected Panels mPanels = new Panels();
    public Panels getPanels() {
        return mPanels;
    }
    @Override
    protected void initSome(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.initSome(inflater, container, savedInstanceState);
        initPanel(mPanels);
        mPanels.onCreate(savedInstanceState);
    }

    private void initPanel(Panels panels) {
    }

    @Override
    public void onStart() {
        super.onStart();
        mPanels.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPanels.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPanels.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPanels.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPanels.onDestroy();
    }
}
