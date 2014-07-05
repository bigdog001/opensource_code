package com.facemail.mobile.android.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import com.facemail.mobile.android.db.bean.UserAcount;
import com.facemail.mobile.android.db.conf.Config;
import com.facemail.mobile.android.db.dao.UserAccountDao;
import com.facemail.mobile.android.fragment.AccountsFragment;
import com.facemail.mobile.android.util.SystemUtil;
import com.facemail.mobile.android.watchdog.watchDogService;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
//import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 4/16/13
 * Time: 8:07 AM
 */
public class FaceMailApplication extends Application {
   private static FaceMailApplication mContext;
   private static Map<String ,Object>  mCacheParam;
   private static SharedPreferences mSp;
   private static boolean mIsSetup=false;
   private Handler mHandler;
   private List<UserAcount> userAcounts;
    @Override
    public void onCreate() {
        super.onCreate();
        SystemUtil.log("facemail003,App start up" + new Date());
        mContext=this;
        mHandler=new Handler() ;
        init();
        loadUsers(null);
    }
    private void loadUsers(final AccountsFragment.UserLoadListener userLoadListener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (userAcounts!=null) {
                    userAcounts.clear();
                }
                userAcounts =UserAccountDao.getInstance(mContext).getUserAccount();
                if (userLoadListener!=null) {
                    userLoadListener.success(userAcounts);
                }
            }
        }).start();
    }

    public void flushUser(){
        loadUsers(null);
    }

    public void getUserAcounts(AccountsFragment.UserLoadListener userLoadListener) {
        if (userAcounts==null) {
            loadUsers(userLoadListener);
        }
        userLoadListener.success(userAcounts);
    }

    private static void init() {
        mSp=mContext.getSharedPreferences(Config.faceConfigFileSp, Context.MODE_PRIVATE) ;
        mIsSetup= mSp.getBoolean(Config.accountSetup,false);//此处以此来决定是否主动提示用户设置帐号 ,当帐号设置ok后将值置为true

        boolean rcv_service_autorun=mSp.getBoolean(Config.RCV_SERVICE_AUTORUN,false);
        if (rcv_service_autorun) { //只有在满足启动条件是开启
            SystemUtil.log("开始启动扫描服务....");
            Intent watchDogIntent=new Intent(mContext,watchDogService.class);
            mContext.startService(watchDogIntent);
        }

    }
    public SharedPreferences getSharedPreferences(){
        return mSp;

    }
    public  boolean getSetupStart(){
        return mIsSetup;
    }
    public  void setSetupStart(boolean IsSetup){
          mIsSetup=IsSetup;
    }
    public  Handler getHandler() {
        if (mHandler==null) {
            mHandler=new Handler() ;
        }
        return mHandler;
    }
    public static FaceMailApplication getFaceMailContext() {
        return mContext;
    }

    //对象级参数传递工具-----
    public static void setmCacheParam(String key,Object value){
          if ("".equals(key)||key==null){
            return;
          }
        key=key.trim();
        if (mCacheParam==null) {
            mCacheParam=new Hashtable<String, Object>();
        }
        mCacheParam.put(key,value);
    }
    public static Object getmCacheParam(String key) {
        if (mCacheParam==null||"".equals(key)||key==null) {
            return null;
        }
        key=key.trim();
        Object value=  mCacheParam.get(key);
        if (value!=null) {
            mCacheParam.remove(key); //保证一个参数使用一次后在参数缓存中失效,这样清除垃圾数据
        }
        return value;
    }
    //-----对象级参数传递工具



}
