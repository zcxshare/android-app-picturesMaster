package com.scanmaster.www.scanmaster.view.activity;

import com.scanmaster.www.scanmaster.presenter.LoginPreserter;
import com.scanmaster.www.scanmaster.view.activity.base.BaseActivity;
import com.scanmaster.www.scanmaster.view.ivew.LoginView;

/**
 * author:  zhouchaoxiang
 * date:    2018/10/4
 * explain: 
 */
public    class LoginActivity extends BaseActivity<LoginPreserter> implements LoginView{
    @Override
    protected LoginPreserter initPresenter() {
        return new LoginPreserter(this);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }
}
