package com.nightfarmer.dialogger.alert;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nightfarmer.dialogger.base.DialogMaskView;
import com.nightfarmer.dialogger.Dialogger;
import com.nightfarmer.dialogger.base.IDialogger;
import com.nightfarmer.dialogger.R;

import java.util.ArrayList;
import java.util.List;

import static android.widget.RelativeLayout.CENTER_IN_PARENT;

/**
 * Created by zhangfan on 2016/10/24 0024.
 */

public class DAlert implements IDialogger {

    private final CharSequence msg;
    private final boolean cancelable;
    private CharSequence title;
    private List<ButtonBinder> buttons = new ArrayList<>();
    private DialogMaskView dialogRootView;
    private View alertView;

    DAlert(DialogMaskView dialogRootView, CharSequence title, CharSequence msg, List<ButtonBinder> buttons, boolean cancelable) {
        this.dialogRootView = dialogRootView;
        this.title = title;
        this.msg = msg;
        this.buttons = buttons;
        this.cancelable = cancelable;
    }

    @Override
    public boolean isCancelable() {
        return cancelable;
    }

    @Override
    public DAlert show() {
        ValueAnimator valueAnimator = null;
        if (!dialogRootView.active) {
            valueAnimator = ValueAnimator.ofInt(0, 100);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Integer value = (Integer) animation.getAnimatedValue();
                    int argb = Color.argb(value, 0, 0, 0);
                    dialogRootView.setBackgroundColor(argb);
                }
            });
        }
        alertView = LayoutInflater.from(dialogRootView.getContext()).inflate(R.layout.alert_layout, dialogRootView, false);
        TextView tv_title = (TextView) alertView.findViewById(R.id.tv_title);
        if (TextUtils.isEmpty(title)) {
            tv_title.setVisibility(View.GONE);
        } else {
            tv_title.setText(title);
        }
        TextView tv_msg = (TextView) alertView.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);

        buildButtons();
        dialogRootView.addView(alertView, this);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) alertView.getLayoutParams();
        layoutParams.addRule(CENTER_IN_PARENT);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(alertView, "alpha", 0, 1);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(alertView, "scaleX", 0.5f, 1);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(alertView, "scaleY", 0.5f, 1);
        scaleX.setInterpolator(new OvershootInterpolator());
        scaleY.setInterpolator(new OvershootInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        if (valueAnimator == null) {
            animatorSet.playTogether(alpha, scaleX, scaleY);
        } else {
            animatorSet.playTogether(valueAnimator, alpha, scaleX, scaleY);
        }
        animatorSet.setDuration(250);
        animatorSet.start();
        dialogRootView.active = true;
        return this;
    }

    private void buildButtons() {
        LinearLayout buttonContainer = (LinearLayout) alertView.findViewById(R.id.buttonsLayout);

        for (int i = 0; i < buttons.size(); i++) {
            TextView button = new TextView(dialogRootView.getContext());
            button.setClickable(true);
            final ButtonBinder buttonBinder = buttons.get(i);
            button.setText(buttonBinder.text);
            button.setGravity(Gravity.CENTER);
            if (buttons.size() == 1) {
                button.setBackgroundResource(R.drawable.alert_button_bgd_match);
            } else if (i == 0) {
                button.setBackgroundResource(R.drawable.alert_button_bgd_left);
            } else if (i == buttons.size() - 1) {
                button.setBackgroundResource(R.drawable.alert_button_bgd_right);
            } else {
                button.setBackgroundResource(R.drawable.alert_button_bgd_middle);
            }
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialogger.OnClickListener onClickListener = buttonBinder.onClickListener;
                    if (onClickListener != null) {
                        onClickListener.onClick(DAlert.this);
                    }
                }
            });
            buttonContainer.addView(button);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button.getLayoutParams();
            layoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT;
            layoutParams.weight = 1;
            if (buttons.size() > 1 && i < buttons.size() - 1) {
                View view = new View(dialogRootView.getContext());
                view.setBackgroundColor(ContextCompat.getColor(dialogRootView.getContext(), R.color.split_line));
                buttonContainer.addView(view);
                LinearLayout.LayoutParams layoutParamsSplit = (LinearLayout.LayoutParams) view.getLayoutParams();
                layoutParamsSplit.height = LinearLayout.LayoutParams.MATCH_PARENT;
                layoutParamsSplit.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, dialogRootView.getContext().getResources().getDisplayMetrics());
            }
        }
    }

    @Override
    public void dismiss() {
        if (alertView == null) {
            Log.i("DAlert", "dialog is already dismissed");
            return;
        }
        final View view = alertView;
        ValueAnimator valueAnimator = null;
        if (dialogRootView.getChildCount() == 1) {
            valueAnimator = ValueAnimator.ofInt(100, 0);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Integer value = (Integer) animation.getAnimatedValue();
                    int argb = Color.argb(value, 0, 0, 0);
                    dialogRootView.setBackgroundColor(argb);
                }
            });
        }
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 1, 0);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1, 0.8f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1, 0.8f);
//        scaleX.setInterpolator(new AnticipateInterpolator());
//        scaleY.setInterpolator(new AnticipateInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        if (valueAnimator == null) {
            animatorSet.playTogether(alpha, scaleX, scaleY);
        } else {
            animatorSet.playTogether(valueAnimator, alpha, scaleX, scaleY);
        }
        animatorSet.setDuration(250);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                dialogRootView.removeView(view);
                if (dialogRootView.getChildCount() == 0) {
                    dialogRootView.active = false;
                }
            }
        });
        animatorSet.start();
        alertView = null;
    }
}
