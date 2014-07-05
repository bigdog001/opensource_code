package com.bigdog.carfinder.android.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import com.bigdog.carfinder.android.R;
import com.bigdog.carfinder.android.application.CarFinderApplication;
import com.bigdog.carfinder.android.base.CarBase;
import com.bigdog.carfinder.android.conf.Config;
import com.bigdog.carfinder.android.service.LocationService;
import com.bigdog.carfinder.android.util.SystemUtil;

public class Welcome extends CarBase {
    /**
     * Called when the activity is first created.
     */
    private LinearLayout welcome_main;
    private Dialog dialog;

    @Override
    public void setUp() {


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = Welcome.this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welcome);
        welcome_main = (LinearLayout) findViewById(R.id.welcome_main);

        AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
        aa.setDuration(500);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //判断用户身份，如果没有登录过则出现登录界面，如果登录过且身份没有过期，则直接进入主界面，如果身份已经失效则提示重新登录
                final SharedPreferences sp = CarFinderApplication.getAppContext().getSharedPreferences();
                int work_declare = sp.getInt(Config.work_declare, 0);
                SystemUtil.log("work_declare:" + work_declare);
                switch (work_declare) {
                    case 0:
                        //第一次運行,需要選擇座標採集模式或者目標監視模式
                        AlertDialog.Builder builder = new AlertDialog.Builder(Welcome.this);
                        builder.setCancelable(false);
                        builder.setTitle(R.string.work_declare_title);
                        View declare_content = View.inflate(mContext, R.layout.declare_content, null);
                        final CheckBox checkBox = (CheckBox) declare_content.findViewById(R.id.check_declare);
                        builder.setView(declare_content);
                        builder.setPositiveButton(R.string.agree, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if (checkBox.isChecked()) {
                                    SystemUtil.log("checked...");
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putInt(Config.work_declare, 1);
                                    editor.commit();
                                }
                                next();
                            }
                        });
                        builder.setNegativeButton(R.string.refuse, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Welcome.this.finish();
                                System.exit(0);
                            }
                        });
                        dialog = builder.create();
                        dialog.show();
                        break;
                    case 1:
                        next();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        welcome_main.startAnimation(aa);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void next() {

        /*Intent intent = new Intent(Welcome.this, MonitorActivity.class);
                        startActivity(intent);
                        finish();*/
        final SharedPreferences sp = CarFinderApplication.getAppContext().getSharedPreferences();
        int work_model = sp.getInt(Config.work_model, 0);
        if (work_model == 1) {
            //1 探测模式
            detect();
        } else if (work_model == 2) {
            //2 观测模式
            monitor();
        } else {
            //弹出选择框让用户指定工作模式
            AlertDialog.Builder builder = new AlertDialog.Builder(Welcome.this);
            builder.setCancelable(false);
            builder.setMessage(R.string.work_model_content);
            builder.setPositiveButton(R.string.work_detect_model, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt(Config.work_model, 1);
                    editor.commit();
                    SystemUtil.log("---------detect model.......");
                    detect();

                }
            });
            builder.setNegativeButton(R.string.work_monitor_motel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt(Config.work_model, 2);
                    editor.commit();
                    SystemUtil.log("---------monitor model.......");
                    monitor();
                }
            });

            dialog = builder.create();
            dialog.show();

        }

    }

    private void detect() {
        CarFinderApplication.getAppContext().startService();
        Intent intent = new Intent(Welcome.this, DetectActivity.class);
        intent.putExtra(Config.come_from, Config.come_from_welcome);
        startActivity(intent);
        finish();
    }

    private void monitor() {
        Intent intent = new Intent(Welcome.this, MonitorActivity.class);
        intent.putExtra(Config.come_from, Config.come_from_welcome);
        startActivity(intent);
        finish();
    }


}
