package com.nightfarmer.dialogger;

import android.app.Activity;

import com.nightfarmer.dialogger.progress.DProgress;
import com.nightfarmer.dialogger.alert.DAlertBuilder;
import com.nightfarmer.dialogger.base.IDialogger;
import com.nightfarmer.dialogger.picker.DPicker;

/**
 * Created by zhangfan on 2016/10/24 0024.
 */

public class Dialogger {

    public static DAlertBuilder alert(Activity activity) {
        return new DAlertBuilder(activity);
    }

    public DPicker picker() {
        return new DPicker();
    }

    public DProgress progress() {
        return new DProgress();
    }

    public interface OnClickListener {
        void onClick(IDialogger dialogger);
    }

    public interface OnClickWatcher {
        void onClick(IDialogger dialogger, int buttonIndex);
    }
}
