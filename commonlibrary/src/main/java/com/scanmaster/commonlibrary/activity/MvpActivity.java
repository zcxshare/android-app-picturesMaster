package com.scanmaster.commonlibrary.activity;

import android.os.Bundle;

import com.scanmaster.commonlibrary.presenter.IPresenter;

import butterknife.ButterKnife;

/**
 * author:  zhouchaoxiang
 * date:    2018/9/20
 * explain:
 */
public abstract class MvpActivity<P extends IPresenter> extends PanelActivity {

    private P mPresenter;

    @Override
    protected void initSome(Bundle savedInstanceState) {
        mPresenter = initPresenter();
        super.initSome(savedInstanceState);
    }


    protected abstract P initPresenter();

    protected abstract int getLayoutId();
    @Override
    protected void initView() {
        setContentView(getLayoutId());
        ButterKnife.bind(this);
    }

}
