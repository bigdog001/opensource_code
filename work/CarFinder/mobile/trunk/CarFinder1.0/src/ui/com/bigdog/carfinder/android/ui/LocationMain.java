package com.bigdog.carfinder.android.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.bigdog.carfinder.android.application.CarFinderApplication;
import com.bigdog.carfinder.android.base.BaseLocationActivity;
import com.bigdog.carfinder.android.util.SystemUtil;
import com.bigdog.carfinder.android.util.json.JsonObject;
import com.bigdog.carfinder.android.util.lbs.ILocationListener;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 7/26/13
 * Time: 11:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class LocationMain extends BaseLocationActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final TextView tv = new TextView(this);
        getLocaiton(LocationMain.this,new ILocationListener() {
            @Override
            public void onLocateSuccess(final long lat, final long lon, final JsonObject locateObj) {
                CarFinderApplication.getAppContext().getmHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(lat+","+lon+","+locateObj.toJsonString());
                    }
                }) ;
                SystemUtil.log(lat+","+lon+","+locateObj.toJsonString());
            }

            @Override
            public void onLocateCancel() {

            }

            @Override
            public void onLocateFail() {
                SystemUtil.log("failed.....");
            }
        });
        tv.setText("2222");
        setContentView(tv);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SystemUtil.log("onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        SystemUtil.log("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        SystemUtil.log("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        SystemUtil.log("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SystemUtil.log("onDestroy");
    }

}