package com.scanmaster.www.scanmaster.presenter;

import com.scanmaster.commonlibrary.presenter.IPresenter;
import com.scanmaster.commonlibrary.presenter.InitPresenter;
import com.scanmaster.www.scanmaster.view.ivew.IBaseView;

/**
 * author:  zhouchaoxiang
 * date:    2018/9/26
 * explain:
 */
public class BasePresenter<V extends IBaseView> extends InitPresenter {
    protected V mView;

    public V getView() {
        return mView;
    }

    public BasePresenter(V view) {
        mView = view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mView = null;
    }
}
