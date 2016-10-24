package com.nightfarmer.dialogger.progress;

import com.nightfarmer.dialogger.base.IDialogger;

/**
 * Created by zhangfan on 2016/10/24 0024.
 */

public class DProgress implements IDialogger{
    @Override
    public boolean isCancelable() {
        return false;
    }

    @Override
    public IDialogger show() {
        return null;
    }

    @Override
    public void dismiss() {

    }
}
