package com.scanmaster.www.scanmaster.view.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;

import com.scanmaster.commonlibrary.presenter.IPresenter;
import com.scanmaster.www.scanmaster.R;
import com.scanmaster.www.scanmaster.view.activity.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    protected List<Fragment> mFragments = new ArrayList<>();
    @BindView(R.id.vp_center)
    ViewPager mVpCenter;
    @BindView(R.id.rg_bottom)
    RadioGroup mRgBottom;
//    @BindView(R.id.drb_scan)
//    DotRadioButton mDrbScan;
//    @BindView(R.id.drb_crop)
//    DotRadioButton mDrbCrop;
//    @BindView(R.id.drb_discern)
//    DotRadioButton mDrbDiscern;
//    @BindView(R.id.drb_mine)
//    DotRadioButton mDrbMine;

    @Override
    protected void initData() {
//        mFragments.add(new HomeFragment());

    }

    @Override
    protected void initView() {
        super.initView();
        setSwipeBackEnable(false);
        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public Fragment getItem(int i) {
                return mFragments.get(i);
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

//        mDrbScan.setOnClickListener(this);
//        mDrbCrop.setOnClickListener(this);
//        mDrbDiscern.setOnClickListener(this);
//        mDrbMine.setOnClickListener(this);

    }

    @Override
    protected void initEvent() {
//        mDrbScan.setNumberDot(true, "123");
//        mDrbCrop.setNumberDot(true, "123");
//        mDrbDiscern.setShowSmallDot(true);
//        mDrbMine.setNumberDot(true, "3");

        mRgBottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
//                    case R.id.drb_scan:
//                        break;
//                    case R.id.drb_crop:
//                        break;
//                    case R.id.drb_discern:
//                        break;
//                    case R.id.drb_mine:
//                        break;
                }
            }
        });
    }

    @Override
    protected IPresenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onClick(View v) {

    }
}
