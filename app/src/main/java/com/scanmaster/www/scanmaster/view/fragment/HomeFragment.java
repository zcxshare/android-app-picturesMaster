package com.scanmaster.www.scanmaster.view.fragment;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scanmaster.commonlibrary.presenter.IPresenter;
import com.scanmaster.commonlibrary.utils.GlideImageLoader;
import com.scanmaster.commonlibrary.utils.ResourceUtils;
import com.scanmaster.commonlibrary.utils.StatusBarUtil;
import com.scanmaster.commonlibrary.widget.AceNestedScrollView;
import com.scanmaster.www.scanmaster.R;
import com.scanmaster.www.scanmaster.common.BaseApplication;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * author:  zhouchaoxiang
 * date:    2018/9/26
 * explain:
 */
public class HomeFragment extends BaseFragment {
    private static final String TAG = "HomeFragment";
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.rl_toolbar)
    RelativeLayout mRlContent;
    @BindView(R.id.srl_home)
    SmartRefreshLayout mSrlHome;
    @BindView(R.id.iv_header)
    ImageView mIvHeader;
    @BindView(R.id.ansv_scroll)
    AceNestedScrollView mAnsvScroll;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.toolbar_username)
    TextView mToolbarUsername;
    @BindView(R.id.buttonBarLayout)
    ButtonBarLayout mButtonBarLayout;
    @BindView(R.id.iv_menu)
    ImageView mIvMenu;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    Unbinder unbinder;
    private int mOffset;
    private int mScrollY;
    private double toolBarPositionY;
    private boolean isOpen = true;
    private ArrayList<String> mImageUrls;
    private ArrayList<String> mContents;
    private ArrayList<String> mTitles;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected IPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initData() {
        mImageUrls = new ArrayList<>();
        mImageUrls.add("https://up.enterdesk.com/edpic/53/39/d7/5339d743b3f38d7d1921ef34e2a3699f.jpg");
        mImageUrls.add("https://up.enterdesk.com/edpic_360_360/ec/7a/b6/ec7ab6374c4499b5ee2ecdf56c3fb354.jpg");
        mImageUrls.add("https://up.enterdesk.com/edpic_360_360/d8/13/30/d8133096fe2925f8ed683f6629d4a1c7.jpg");
        mImageUrls.add("https://up.enterdesk.com/edpic_360_360/d5/f8/5c/d5f85cc2d02d5559331aa0dfadf88b7d.jpg");
        mImageUrls.add("https://up.enterdesk.com/edpic_360_360/de/b0/e5/deb0e57dbb707d0ecaed312811cd700e.jpg");
        mImageUrls.add("https://up.enterdesk.com/edpic_360_360/54/87/2a/54872ae2822a379d363766b79abed1e3.jpg");
        mImageUrls.add("https://up.enterdesk.com/edpic_360_360/cb/5a/23/cb5a23309966a63e2e903043b46effbc.jpg");
        mTitles = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mTitles.add(i + "");
        }
        mContents = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mContents.add(i + "");
        }
    }

    @Override
    protected void initView() {
        super.initView();
        StatusBarUtil.initStatusBar(getActivity(), true, true, true);
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(getContext());
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mRlContent.getLayoutParams();
        params.setMargins(0, statusBarHeight, 0, 0);
        mRvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvContent.setAdapter(new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_content, mContents) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_content, item);
            }
        });
        mRlContent.setLayoutParams(params);
        mButtonBarLayout.setAlpha(0);
        mToolbar.setBackgroundColor(0);
        mToolbar.post(new Runnable() {
            @Override
            public void run() {
                toolBarPositionY = mToolbar.getY();
            }
        });
        mBanner.post(new Runnable() {
            @Override
            public void run() {
                mAnsvScroll.setScrollHeight(mBanner.getHeight());
            }
        });
        mBanner.setImageLoader(new GlideImageLoader())
                .setImages(mImageUrls)
//                .setBannerTitles(mTitles)
                .isAutoPlay(true)
                .setDelayTime(2000)
                .setBannerAnimation(Transformer.Accordion)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setIndicatorGravity(BannerConfig.CENTER)
                .start();
    }

    @Override
    protected void initEvent() {
        mSrlHome.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onHeaderPulling(RefreshHeader header, float percent, int offset, int headerHeight, int extendHeight) {
                mOffset = offset / 2;
                mIvHeader.setTranslationY(mOffset - mScrollY);
            }

            @Override
            public void onHeaderReleasing(RefreshHeader header, float percent, int offset, int footerHeight, int extendHeight) {
                mOffset = offset / 2;
                mIvHeader.setTranslationY(mOffset - mScrollY);
            }
        });
        mAnsvScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {

            int lastScrollY = 0;
            int h = DensityUtil.dp2px(170);
            int color = ResourceUtils.getColor(BaseApplication.getContext(), R.color.white) & 0x00ffffff;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int[] location = new int[2];
//                magicIndicator.getLocationOnScreen(location);
                int yPosition = location[1];
                Log.i(TAG, "onScrollChange: toolBarPositionY:"+toolBarPositionY+"---yPosition:"+yPosition);
                if (yPosition < toolBarPositionY) {
//                    magicIndicatorTitle.setVisibility(View.VISIBLE);
                    mAnsvScroll.setNeedScroll(false);
                } else {
//                    magicIndicatorTitle.setVisibility(View.GONE);
                    mAnsvScroll.setNeedScroll(true);

                }

                if (lastScrollY < h) {
                    scrollY = Math.min(h, scrollY);
                    mScrollY = scrollY > h ? h : scrollY;
                    mButtonBarLayout.setAlpha(1f * mScrollY / h);
//                    mToolbar.setBackgroundColor(((255 * mScrollY / h) << 24) | color);
                    mIvHeader.setTranslationY(mOffset - mScrollY);
                }
                if (scrollY == 0) {
                    mIvBack.setImageResource(R.drawable.back_white);
                    mIvMenu.setImageResource(R.drawable.icon_menu_white);
                } else {
                    mIvBack.setImageResource(R.drawable.back_black);
                    mIvMenu.setImageResource(R.drawable.icon_menu_black);
                }

                lastScrollY = scrollY;
            }
        });
    }

}
