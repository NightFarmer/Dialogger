package com.nightfarmer.dialogger.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.nightfarmer.dialogger.Dialogger;
import com.nightfarmer.dialogger.base.IDialogger;
import com.nightfarmer.dialogger.alert.DAlert;

public class MainActivity extends AppCompatActivity {

    private DAlert dAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void show(View view) {

        dAlert = Dialogger.alert(this)
//                .title("我的标题")
                .msg("我是内容。。。。。")
                .cancelable(false)
                .button("hehe", new Dialogger.OnClickListener() {
                    @Override
                    public void onClick(IDialogger dialogger) {
                        dialogger.dismiss();
                    }
                })
                .button("haha", new Dialogger.OnClickListener() {
                    @Override
                    public void onClick(IDialogger dialogger) {
                        Dialogger.alert(MainActivity.this)
                                .title("提示")
                                .msg("是否继续进程操作？是否继续进程操作？是否继续进程操作？")
                                .button("是的", new Dialogger.OnClickListener() {
                                    @Override
                                    public void onClick(IDialogger dialogger) {
                                        dialogger.dismiss();
                                    }
                                })
                                .buttons(new CharSequence[]{"aaaaaaaaaaa", "bb"}, new Dialogger.OnClickWatcher() {
                                    @Override
                                    public void onClick(IDialogger dialogger, int buttonIndex) {
                                        Log.i("xx", "" + buttonIndex);
                                    }
                                })
                                .button("不要", null)
                                .show();
//                        dAlert.dismiss();
                    }
                })
                .button("yooo", new Dialogger.OnClickListener() {
                    @Override
                    public void onClick(IDialogger dialogger) {
                        Dialogger.alert(MainActivity.this)
                                .title("提示")
                                .msg("这是个悲伤的提示！")
                                .show();
                        dialogger.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        dAlert.dismiss();
    }
}
