package com.scanmaster.commonlibrary.presenter;

import com.scanmaster.commonlibrary.manager.SubscriptionManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * author:  zhouchaoxiang
 * date:    2018/10/5
 * explain: 
 */
public    class InitPresenter implements IPresenter   {
    public List<Disposable> mDisposables = new ArrayList<Disposable>();

    public void addDisposable(Disposable disposable) {
        mDisposables.add(disposable);
    }

    public void onDestroy() {
        if (null != mDisposables && !mDisposables.isEmpty()) {
            for (Disposable disposable : mDisposables) {
                if (!disposable.isDisposed()) {
                    SubscriptionManager.getInstance().remove(disposable);
                }
            }
            mDisposables.clear();
        }
        mDisposables = null;
    }
}
