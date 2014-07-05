package com.bigdog.carfinder.android.service;

import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import com.bigdog.carfinder.android.bean.LocationBean;
import com.bigdog.carfinder.android.dao.impl.LocationDaoImpl;
import com.bigdog.carfinder.android.util.SystemUtil;
import com.bigdog.carfinder.android.util.json.JsonObject;
import com.bigdog.carfinder.android.util.json.JsonParser;
import com.bigdog.carfinder.android.util.json.JsonValue;
import com.bigdog.carfinder.android.util.lbs.ILocationListener;
import com.bigdog.carfinder.android.util.lbs.LocationThread;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 7/26/13
 * Time: 11:35 AM
 */
public class LocationService extends MyService {
    public class FinderBinder extends Binder {
        public LocationService getService() {
            return LocationService.this;
        }

        public void setLocationListener(ILocationListener iLocationListener) {
            locationListener = iLocationListener;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (finderBinder == null) {
            finderBinder = new LocationService.FinderBinder();
        }
        return finderBinder;
    }


    @Override
    public void doLocationUpload() {
        //此处一定要新启动一个线程开始工作
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemUtil.log("坐标上报中......");


                last_location_upload_time = System.currentTimeMillis();
            }
        }).start();

    }

    @Override
    public void executeTasks() {
        if (taskUnits != null) {
            for (final TaskUnit taskUnit : taskUnits) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        taskUnit.doit(null);//参数暂时为空，此处应该传入系统的运行参数数据
                    }
                }).start();
            }
        }
    }

    @Override
    public void doLocation() {
//        SystemUtil.log("start to detect location......");
        new LocationThread(mContext, new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case LocationThread.RESULT_READY:
                    case LocationThread.RESULT_TIMEOUT:

                        String latlonString = ((JSONObject) msg.obj).toString();
                        JsonObject latlon = null;
                        if (latlonString != null) {
                            JsonValue value = JsonParser.parse(latlonString);
                            if (value instanceof JsonObject) {
                                latlon = (JsonObject) value;
                            }
                        }
                        //将坐标点入库...........
                        saveLocation(latlon);
                        last_location_time = System.currentTimeMillis();
                        if (locationListener != null) {
                            locationListener.onLocateSuccess(LATLON_DEFAULT, LATLON_DEFAULT, latlon);
                        }

                        break;
                    case LocationThread.RESULT_FAIL:
                        if (locationListener != null) {
                            locationListener.onLocateFail();
                        }
                        break;
                    default:
                        break;
                }
            }
        }).chekPosition();

    }


    @Override
    public void saveLocationToLocal(final LocationBean locationBean) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LocationDaoImpl.getFaceAddressDao(mContext).saveLocation(locationBean);
            }
        }).start();
    }


}