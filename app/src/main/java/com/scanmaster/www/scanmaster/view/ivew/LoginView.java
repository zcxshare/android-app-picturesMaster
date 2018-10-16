package com.scanmaster.www.scanmaster.view.ivew;

import com.scanmaster.commonlibrary.net.HttpErrorBean;

/**
 * author:  zhouchaoxiang
 * date:    2018/10/4
 * explain:
 */
public interface LoginView extends IBaseView {
    void loginSuccess(String string);

    void loginFail(HttpErrorBean fail);

    void registerSuccess(String string);

    void registerFail(HttpErrorBean fail);
}
