package com.bigdog.carfinder.android.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import com.bigdog.carfinder.android.application.CarFinderApplication;
import com.bigdog.carfinder.android.bean.LocationBean;
import com.bigdog.carfinder.android.conf.Config;
import com.bigdog.carfinder.android.util.SystemUtil;
import com.bigdog.carfinder.android.util.json.JsonObject;
import com.bigdog.carfinder.android.util.lbs.ILocationListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 9/9/13
 * Time: 1:09 PM
 */
public abstract class MyService extends Service {
    protected BroadcastReceiver receiver;
    protected AlarmManager alarmManager;
    protected PendingIntent pi;
    protected List<TaskUnit> taskUnits;
    protected final int LATLON_DEFAULT = 255 * 1000000;  //默认的经纬度
    protected Context mContext;
    protected static final long INTERVAL = 20000; //接收系统定时事件的频率
    protected static long last_location_time = 0; //上次定位成功完成的时间
    protected static long last_location_upload_time = 0; //上次功完成上传的时间
    protected static long location_frequency;//位置探测时间差
    protected static long location_upload_frequency_default;//位置上报时间差
    protected IBinder finderBinder;
    protected ILocationListener locationListener; //定位成功后通过此接口通知界面

    public abstract void doLocation();

    public abstract void doLocationUpload();

    public abstract void executeTasks();

    public abstract void saveLocationToLocal(LocationBean locationBean);

    @Override
    public void onCreate() {
        taskUnits = new ArrayList<TaskUnit>();
        mContext = CarFinderApplication.getAppContext().getApplicationContext();
        location_frequency = CarFinderApplication.getAppContext().getSharedPreferences().getLong(Config.location_frequency_name, Config.location_frequency_default);
        location_upload_frequency_default = CarFinderApplication.getAppContext().getSharedPreferences().getLong(Config.location_upload_frequency_name, Config.location_upload_frequency_default);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent();
        intent.setAction(Config.detect_rcv);
        pi = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                0, INTERVAL, pi);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if ((System.currentTimeMillis() - last_location_time) > location_frequency) {
                    //该重新定位了.....
                    doLocation();
                } else {
                    SystemUtil.log("定位时间未到，本次放弃定位......");
                }
                if ((System.currentTimeMillis() - last_location_upload_time) > location_upload_frequency_default) {
                    doLocationUpload();
                } else {
                    SystemUtil.log("坐标上报时间未到，本次放弃上报......");
                }

                executeTasks();//每次都隔一段时间重复循环执行被注册进来的任务
            }
        };
        registerReceiver(receiver, new IntentFilter(Config.detect_rcv));

    }

    protected void saveLocation(JsonObject locateObj) {
        if (locateObj != null) {
            if (locateObj.getJsonArray("network_location") != null) {
                if (locateObj.getJsonArray("network_location").size() > 0) {
                    JsonObject network_location = (JsonObject) locateObj.getJsonArray("network_location").get(0);
                    String latitude = network_location.getString("network_lat");
                    String lontitude = network_location.getString("network_lon");
                    LocationBean locationBean = new LocationBean();
                    locationBean.setLatitude(latitude);
                    locationBean.setLontitude(lontitude);
                    locationBean.setDetacte_time(System.currentTimeMillis());
                    saveLocationToLocal(locationBean);
                }
            } else if (locateObj.getJsonArray("gps_location") != null) {
                if (locateObj.getJsonArray("gps_location").size() > 0) {
                    JsonObject network_location = (JsonObject) locateObj.getJsonArray("gps_location").get(0);
                    String latitude = network_location.getString("gps_lat");
                    String lontitude = network_location.getString("gps_lon");
                    LocationBean locationBean = new LocationBean();
                    locationBean.setLatitude(latitude);
                    locationBean.setLontitude(lontitude);
                    locationBean.setDetacte_time(System.currentTimeMillis());
                    saveLocationToLocal(locationBean);
                }
            } else {
                SystemUtil.log("location failed....");
            }

        } else {
            SystemUtil.log("location failed....");
        }
    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public void addTaskUnit(TaskUnit taskUnit) {
        if (taskUnits != null) {
            taskUnits.add(taskUnit);
        }
    }
}
