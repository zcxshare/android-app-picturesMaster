package com.scanmaster.www.scanmaster.presenter;

import android.util.Log;

import com.scanmaster.commonlibrary.net.HttpErrorBean;
import com.scanmaster.commonlibrary.net.NetObserver;
import com.scanmaster.www.scanmaster.model.net.NetServer;
import com.scanmaster.www.scanmaster.view.ivew.IBaseView;
import com.scanmaster.www.scanmaster.view.ivew.LoginView;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * author:  zhouchaoxiang
 * date:    2018/10/4
 * explain:
 */
public class LoginPreserter extends BasePresenter<LoginView> {
    private static final String TAG = "LoginPreserter";
    public LoginPreserter(LoginView view) {
        super(view);
    }

    public void login(String username, String password) {
        NetServer.login(username, password, new NetObserver<ResponseBody>(this, "登录") {
            @Override
            public void onSuccess(ResponseBody value) {
                String string = null;
                try {
                    string = value.string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                getView().loginSuccess(string);
            }

            @Override
            public void onFail(HttpErrorBean bean) {
                getView().loginFail(bean);
                Log.i(TAG, "onFail: "+bean.message);
            }
        });
    }

    public void register(String username, String password) {
        NetServer.register(username, password, new NetObserver<ResponseBody>(this, "注册") {
            @Override
            public void onSuccess(ResponseBody value) {
                String string = null;
                try {
                    string = value.string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                getView().registerSuccess(string);
            }

            @Override
            public void onFail(HttpErrorBean bean) {
                Log.i(TAG, "onFail: "+bean.message);
                getView().registerFail(bean);

            }
        });
    }
}
