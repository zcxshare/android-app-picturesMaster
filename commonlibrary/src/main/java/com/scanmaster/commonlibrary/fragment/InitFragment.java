package com.scanmaster.commonlibrary.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * author:  zhouchaoxiang
 * date:    2018/9/19
 * explain:
 */
public abstract class InitFragment extends Fragment {

    protected Context mContext;
    protected LayoutInflater mInflater;
    public View mVRoot;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflater = inflater;
        if (null == mVRoot) {
            initSome(inflater, container, savedInstanceState);
            initData();
            initView();
            initEvent();
        }
        return mVRoot;
    }

    protected void initSome(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    }
    protected abstract void initData();

    protected abstract void initView() ;

    protected abstract void initEvent();

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

}
