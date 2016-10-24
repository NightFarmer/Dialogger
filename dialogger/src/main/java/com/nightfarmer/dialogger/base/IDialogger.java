package com.nightfarmer.dialogger.base;

/**
 * Created by zhangfan on 2016/10/24 0024.
 */

public interface IDialogger {
    boolean isCancelable();

    IDialogger show();

    void dismiss();
}
