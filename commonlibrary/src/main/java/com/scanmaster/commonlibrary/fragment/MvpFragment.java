package com.scanmaster.commonlibrary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.scanmaster.commonlibrary.presenter.IPresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author:  zhouchaoxiang
 * date:    2018/9/26
 * explain:
 */
public abstract class MvpFragment<P extends IPresenter> extends PanelFragment {
    protected P presenter;
    private Unbinder mBind;


    @Override
    protected void initSome(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        presenter = initPresenter();
        super.initSome(inflater, container, savedInstanceState);
    }

    protected abstract int getLayoutId();

    protected abstract P initPresenter();

    @Override
    protected void initView() {
        mVRoot = mInflater.inflate(getLayoutId(), null, false);
        mBind = ButterKnife.bind(this, mVRoot);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mBind) {
            mBind.unbind();
            mBind = null;
        }
    }
}
