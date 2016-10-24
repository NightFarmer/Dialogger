package com.nightfarmer.dialogger.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.HashMap;

/**
 * Created by zhangfan on 2016/10/24 0024.
 */

public class DialogMaskView extends RelativeLayout {

    public boolean active = false;

    private HashMap<View, IDialogger> viewCancelMap = new HashMap<>();

    public DialogMaskView(Context context) {
        super(context);
    }

    public DialogMaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DialogMaskView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addView(View child, IDialogger dialog) {
        super.addView(child);
        viewCancelMap.put(child, dialog);
    }

    @Override
    public void removeView(View view) {
        super.removeView(view);
        viewCancelMap.remove(view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!active) {
            return super.onTouchEvent(event);
        }
        if (getChildCount() > 0) {
            View lastChild = getChildAt(getChildCount() - 1);
            IDialogger dialogger = viewCancelMap.get(lastChild);
            if (dialogger != null && dialogger.isCancelable()) {
                dialogger.dismiss();
            }
        }
        return true;
    }
}
