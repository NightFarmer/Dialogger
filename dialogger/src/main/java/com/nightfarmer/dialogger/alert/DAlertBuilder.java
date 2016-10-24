package com.nightfarmer.dialogger.alert;

import android.app.Activity;

import com.nightfarmer.dialogger.base.BaseDAlertBuilder;
import com.nightfarmer.dialogger.base.DialogMaskView;
import com.nightfarmer.dialogger.Dialogger;
import com.nightfarmer.dialogger.base.IDialogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangfan on 2016/10/24 0024.
 */

public class DAlertBuilder extends BaseDAlertBuilder {

    private CharSequence title;
    private List<ButtonBinder> buttons = new ArrayList<>();
    private DAlert dAlert;
    private CharSequence msg;
    private boolean cancelable = true;

    public DAlertBuilder(Activity activity) {
        super(activity);
    }

    public DAlertBuilder title(CharSequence title) {
        this.title = title;
        return this;
    }

    public DAlertBuilder msg(CharSequence msg) {
        this.msg = msg;
        return this;
    }

    public DAlertBuilder button(CharSequence text) {
        buttons.add(new ButtonBinder(text, null));
        return this;
    }

    public DAlertBuilder button(CharSequence text, Dialogger.OnClickListener listener) {
        buttons.add(new ButtonBinder(text, listener));
        return this;
    }

    public DAlertBuilder buttons(CharSequence[] buttonStrs, final Dialogger.OnClickWatcher watcher) {
        if (buttonStrs != null) {
            for (int i = 0; i < buttonStrs.length; i++) {
                final int finalI = i;
                buttons.add(new ButtonBinder(buttonStrs[i], new Dialogger.OnClickListener() {
                    @Override
                    public void onClick(IDialogger dialogger) {
                        if (watcher != null) {
                            watcher.onClick(dialogger, finalI);
                        }
                    }
                }));
            }
        }
        return this;
    }

    public DAlertBuilder cancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    public DAlert show() {
        dAlert = build().show();
        return dAlert;
    }

    public DAlert build() {
        DialogMaskView dialogRootView = getDialogRootView();
        List<ButtonBinder> dialogButtons = buttons.size() == 0 ? getDefaultButtons() : buttons;
        return new DAlert(dialogRootView, title, msg, dialogButtons, cancelable);
    }

    private List<ButtonBinder> getDefaultButtons() {
        ArrayList<ButtonBinder> defaultButtons = new ArrayList<>();
        ButtonBinder button = new ButtonBinder("确认", new Dialogger.OnClickListener() {
            @Override
            public void onClick(IDialogger dialog) {
                dialog.dismiss();
            }
        });
        defaultButtons.add(button);
        return defaultButtons;
    }

}
