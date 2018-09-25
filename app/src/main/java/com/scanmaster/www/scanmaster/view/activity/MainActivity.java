package com.scanmaster.www.scanmaster.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.scanmaster.commonlibrary.presenter.IPresenter;
import com.scanmaster.commonlibrary.widget.DotImageView;
import com.scanmaster.www.scanmaster.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.vp_center)
    ViewPager mVpCenter;
    @BindView(R.id.ll_bottom)
    LinearLayout mLlBottom;
    @BindView(R.id.dotImageView)
    DotImageView mDotImageView;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        super.initView();
        setSwipeBackEnable(false);
        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return getSupportFragmentManager().getFragments().size();
            }

            @Override
            public Fragment getItem(int i) {
                return getSupportFragmentManager().getFragments().get(i);
            }
        };
        mVpCenter.setAdapter(mAdapter);
        mVpCenter.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected IPresenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

}
