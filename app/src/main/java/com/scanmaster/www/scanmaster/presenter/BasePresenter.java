package com.scanmaster.www.scanmaster.presenter;

import com.scanmaster.commonlibrary.presenter.IPresenter;
import com.scanmaster.www.scanmaster.view.ivew.IBaseView;

/**
 * author:  zhouchaoxiang
 * date:    2018/9/26
 * explain:
 */
public class BasePresenter<V extends IBaseView> implements IPresenter {
    protected V mView;

    public BasePresenter(V view) {
        mView = view;
    }

    public void onDestroy() {
        mView = null;
    }
}
