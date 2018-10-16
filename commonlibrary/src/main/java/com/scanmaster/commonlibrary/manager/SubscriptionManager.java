package com.scanmaster.commonlibrary.manager;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 订阅管理类 2018/4/25.
 */

public class SubscriptionManager  {
    private CompositeDisposable mDisposables;

    private static class Instance {
        private static final SubscriptionManager instance = new SubscriptionManager();
    }

    private SubscriptionManager() {
        if (null == mDisposables) {
            mDisposables = new CompositeDisposable();
        }
    }

    public static SubscriptionManager getInstance() {
        return Instance.instance;
    }

    public void add(Disposable disposable) {
        if (null == disposable) return;
        mDisposables.add(disposable);
    }

    public void remove(Disposable disposable) {
        if (null != mDisposables && null != disposable) {
            mDisposables.remove(disposable);
        }
    }

    public void clear() {
        if (mDisposables != null) {
            mDisposables.clear();
        }
    }
}
