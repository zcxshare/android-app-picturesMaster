package com.scanmaster.commonlibrary.net;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.scanmaster.commonlibrary.InitApplication;
import com.scanmaster.commonlibrary.manager.DialogManager;
import com.scanmaster.commonlibrary.manager.SubscriptionManager;
import com.scanmaster.commonlibrary.presenter.InitPresenter;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;


/**
 * author:  zhouchaoxiang
 * date:    2018/4/19
 * explain:
 */
public abstract class NetObserver<RESPONCE> implements Observer<RESPONCE> {

    private String text;
    private boolean isShowDialog;
    private long mStartTime;
    private InitPresenter mPresenter;

    public NetObserver(InitPresenter presenter, String text) {
        this(presenter, true, text);
    }

    public NetObserver(InitPresenter presenter, boolean isShowDialog, String text) {
        this.isShowDialog = isShowDialog;
        mPresenter = presenter;
        this.text = text;
    }

    @Override
    public void onSubscribe(final Disposable d) {
        mStartTime = System.currentTimeMillis();
        if (isShowDialog) {
            Dialog dialog = DialogManager.showLoadingDialog(text);
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    d.dispose();
                }
            });
        }
        SubscriptionManager.getInstance().add(d);
        if (null != mPresenter) {
            mPresenter.addDisposable(d);
        }
        onDisposable(d);
    }

    @Override
    public void onNext(RESPONCE value) {
        onCompleted();
        if (null == value) {
            onFail(new HttpErrorBean("结果为空", 0, 0));
            return;
        }
        onSuccess(value);
    }

    public abstract void onSuccess(RESPONCE value);

    @Override
    public void onError(Throwable e) {
        onCompleted();
        String fail;
        HttpErrorBean bean = null;
        if (e instanceof SocketTimeoutException) {
            String message = e.getMessage();
            if (TextUtils.isEmpty(message)) {
                fail = "响应超时";
            } else if (message.contains("failed to connect")) {
                fail = "连接服务器超时";
            } else {
                fail = "未知超时";
            }
        } else if (e instanceof ConnectException) {
            ConnectivityManager manager = (ConnectivityManager) InitApplication.getContext().getSystemService(
                    Context.CONNECTIVITY_SERVICE);
            if (null == manager) {
                fail = "连接不上服务器";
            } else {
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo == null || !networkInfo.isAvailable() || !networkInfo.isConnectedOrConnecting()) {
                    fail = "您当前没有网络，请检查网络状态";
                } else {
                    fail = "连接不上服务器";
                }
            }
        } else if (e instanceof HttpException) {
            ResponseBody responseBody = ((HttpException) e).response().errorBody();
            if (null == responseBody) {
                fail = "服务器拒绝连接";
            } else {
                try {
                    fail = responseBody.string();
                } catch (IOException e1) {
                    e1.printStackTrace();
                    fail = "服务器拒绝连接";
                }
            }
//                bean = JsonUtils.jsonToBean(string, HttpErrorBean.class);
        } else if (e instanceof IOException) {
            String message = e.getMessage();
            if (!TextUtils.isEmpty(message) && message.equals("Canceled")) {
                fail = "请求太频繁,已取消";
            } else {
                fail = message;
            }
        } else {
            fail = e.toString();
        }
        bean = new HttpErrorBean(fail, 0, 0);
        onFail(bean);
    }

    public abstract void onFail(HttpErrorBean bean);

    @Override
    public void onComplete() {
//        if (isShowDialog)
//            DialogManager.hideLoadingDialog();
//        long endTime = System.currentTimeMillis();
//        MyToast.showESTToastShort("用时：" + (endTime - mStartTime) + " ms");
//        onCompleted();
    }

    public void onCompleted() {
        if (isShowDialog)
            DialogManager.hideLoadingDialog();
    }

    public void onDisposable(Disposable d) {
    }
}
