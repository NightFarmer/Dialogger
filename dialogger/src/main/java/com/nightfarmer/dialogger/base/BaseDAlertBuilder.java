package com.nightfarmer.dialogger.base;

import android.app.Activity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by zhangfan on 2016/10/24 0024.
 */

public abstract class BaseDAlertBuilder {
    protected Activity activity;

    public BaseDAlertBuilder(Activity activity) {
        this.activity = activity;
    }

    public DialogMaskView getDialogRootView() {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        DialogMaskView dialog_root = (DialogMaskView) decorView.findViewWithTag("dialog_root");
        if (dialog_root != null) return dialog_root;
        dialog_root = new DialogMaskView(activity);
        decorView.addView(dialog_root);
        dialog_root.setTag("dialog_root");
//        dialog_root.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        dialog_root.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        ));
        Log.i("xx", dialog_root.toString());
        return dialog_root;
    }
}
