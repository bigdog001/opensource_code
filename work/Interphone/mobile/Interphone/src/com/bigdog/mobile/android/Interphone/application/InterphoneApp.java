package com.bigdog.mobile.android.Interphone.application;

import android.app.Application;
import android.content.res.Configuration;
import android.os.Handler;
import com.bigdog.mobile.android.Interphone.utils.SystemUtil;
import com.bigdog.mobile.android.Interphone.voice.AudioWrapper;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 9/23/13
 * Time: 1:45 PM
 */
public class InterphoneApp  extends Application {
    private static InterphoneApp mContext;
    private Handler mHandler;
    private AudioWrapper audioWrapper;

    public AudioWrapper getAudioWrapper() {
        return audioWrapper;
    }

    public void setAudioWrapper(AudioWrapper audioWrapper) {
        this.audioWrapper = audioWrapper;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SystemUtil.log("car finder,App start up ,in CarFinderApplication onCreate()....." + new Date());
        mContext=this;
        mHandler=new Handler() ;
        init();
    }
    public Handler getmHandler(){
        return  mHandler;
    }
    public static InterphoneApp getAppContext() {
        return mContext;
    }


    private void init(){
        audioWrapper = AudioWrapper.getInstance();
        audioWrapper.startListen();
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
        audioWrapper.stopRecord();
        audioWrapper.stopListen();

    }
}
