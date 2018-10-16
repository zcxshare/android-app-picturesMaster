package com.scanmaster.commonlibrary.manager;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.scanmaster.commonlibrary.InitApplication;
import com.scanmaster.commonlibrary.R;
import com.scanmaster.commonlibrary.utils.PxUtils;

/**
 * author:  zhouchaoxiang
 * date:    2018/10/5
 * explain: 
 */
public    class DialogManager   {
    private static Dialog loadingDialog;
    public static Dialog showLoadingDialog(CharSequence message) {
        if (null == loadingDialog) {
            loadingDialog = getDialog(InitApplication.getTopActivity(), true);
            loadingDialog.setCanceledOnTouchOutside(false);
            loadingDialog.setContentView(R.layout.dialog_progress);
            TextView tv_message = loadingDialog.findViewById(R.id.tv_message);
            if (TextUtils.isEmpty(message)) {
                tv_message.setText("正在加载。。。");
            } else {
                tv_message.setText(message);
            }
            showDialog(InitApplication.getTopActivity(), loadingDialog, 20);
        } else {
            loadingDialog.dismiss();
            loadingDialog = null;
            showLoadingDialog(message);
        }

        return loadingDialog;
    }
    public static void hideLoadingDialog() {
        if (null != loadingDialog) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }
    @NonNull
    public static Dialog getDialog(Context context, boolean cancel) {
        Dialog dialog = new Dialog(context);
        dialog.setCanceledOnTouchOutside(cancel);
        dialog.setCancelable(cancel);
        return dialog;
    }

    public static void showDialog(Context context, Dialog dialog, int marginDp) {
        dialog.show();
        Window window = dialog.getWindow();
        if (null != window) {
            WindowManager.LayoutParams params = window.getAttributes();
            Resources resources = context.getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            params.width = dm.widthPixels - PxUtils.dp2px(context, marginDp * 2);
            window.setAttributes(params);
            window.closeAllPanels();
        }
    }
}
