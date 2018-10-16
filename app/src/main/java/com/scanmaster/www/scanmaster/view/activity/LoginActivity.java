package com.scanmaster.www.scanmaster.view.activity;

import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.scanmaster.commonlibrary.net.HttpErrorBean;
import com.scanmaster.commonlibrary.utils.MyToast;
import com.scanmaster.www.scanmaster.R;
import com.scanmaster.www.scanmaster.presenter.LoginPreserter;
import com.scanmaster.www.scanmaster.view.activity.base.BaseActivity;
import com.scanmaster.www.scanmaster.view.ivew.LoginView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author:  zhouchaoxiang
 * date:    2018/10/4
 * explain:
 */
public class LoginActivity extends BaseActivity<LoginPreserter> implements LoginView {
    @BindView(R.id.et_username)
    AppCompatEditText mEtUsername;
    @BindView(R.id.et_password)
    AppCompatEditText mEtPassword;
    @BindView(R.id.bt_register)
    Button mBtRegister;

    @Override
    protected LoginPreserter initPresenter() {
        return new LoginPreserter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @OnClick(R.id.bt_register)
    public void onViewClicked() {
        String username = getEditText(mEtUsername, "请输入用户名");
        String password = getEditText(mEtPassword, "请输入密码");
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            return;
        }
        getPresenter().register(username, password);
    }

    private String getEditText(EditText editText, CharSequence errorHind) {
        Editable mEtText = editText.getText();
        String text = null;
        if (null != mEtText) {
            text = mEtText.toString().trim();
        }
        if (TextUtils.isEmpty(text)) {
          MyToast.showToastShort(errorHind);
        }
        return text;
    }

    @Override
    public void loginSuccess(String string) {
        MyToast.showToastShort(string);
    }

    @Override
    public void loginFail(HttpErrorBean fail) {
        MyToast.showToastShort(fail.message);
    }

    @Override
    public void registerSuccess(String string) {
        MyToast.showToastShort(string);
    }

    @Override
    public void registerFail(HttpErrorBean fail) {
        MyToast.showToastShort(fail.message);
    }

}
