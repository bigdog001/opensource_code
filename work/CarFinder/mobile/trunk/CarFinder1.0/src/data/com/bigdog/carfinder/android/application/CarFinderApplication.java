package com.bigdog.carfinder.android.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Handler;
import com.bigdog.carfinder.android.conf.Config;
import com.bigdog.carfinder.android.conf.Variable;
import com.bigdog.carfinder.android.service.LocationService;
import com.bigdog.carfinder.android.util.SystemUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 7/25/13
 * Time: 8:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class CarFinderApplication extends Application {
    private static CarFinderApplication mContext;
    private Handler mHandler;
    private static SharedPreferences mSp;
    private long last_detecte_time;//上次坐标采集时间
    private long last_upload_time; //上次坐标上传时间



    @Override
    public void onCreate() {
        super.onCreate();
        SystemUtil.log("car finder,App start up ,in CarFinderApplication onCreate()....." + new Date());
        mContext=this;
        mHandler=new Handler() ;
        init();
        startService();
    }
    private  void init() {
        mSp=mContext.getSharedPreferences(Config.ConfigFileSp, Context.MODE_PRIVATE) ;
        Variable.setMacAddress(SystemUtil.getLocalMacAddress(mContext));
    }
    public SharedPreferences getSharedPreferences(){
        return mSp;

    }
    public Handler getmHandler(){
        return  mHandler;
    }

    public static CarFinderApplication getAppContext() {
        return mContext;
    }
    public void startService(){
        if ( mSp.getInt(Config.work_model,0) == 1) {
            //如果探测服务未启动，则启动探测服务
            if (!SystemUtil.isServiceRunning(mContext, Config.detect_rcv)) {
                //探测服务未被开启,立即开启探测服务程序
                SystemUtil.log("探测服务未被开启,立即开启探测服务程序,in CarFinderApplication Activity........");
                Intent locationServiceIntent = new Intent(mContext, LocationService.class);
                mContext.startService(locationServiceIntent);
            }
        }
    }

    public long getLast_detecte_time() {
        return last_detecte_time;
    }

    public void setLast_detecte_time(long last_detecte_time) {
        this.last_detecte_time = last_detecte_time;
    }

    public long getLast_upload_time() {
        return last_upload_time;
    }

    public void setLast_upload_time(long last_upload_time) {
        this.last_upload_time = last_upload_time;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

    }

    public CarFinderApplication() {
        super();
    }
}
