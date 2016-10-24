package com.nightfarmer.dialogger.alert;

import com.nightfarmer.dialogger.Dialogger;

/**
 * Created by zhangfan on 2016/10/24 0024.
 */

class ButtonBinder {
    CharSequence text;
    Dialogger.OnClickListener onClickListener;

    public ButtonBinder(CharSequence text, Dialogger.OnClickListener onClickListener) {
        this.text = text;
        this.onClickListener = onClickListener;
    }
}
