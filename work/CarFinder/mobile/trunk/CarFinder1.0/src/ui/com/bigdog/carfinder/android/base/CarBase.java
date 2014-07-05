package com.bigdog.carfinder.android.base;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import com.bigdog.carfinder.android.api.httpRequest.ApiProvider;
import com.bigdog.carfinder.android.application.CarFinderApplication;
import com.bigdog.carfinder.android.conf.Config;
import com.bigdog.carfinder.android.obverser.TrackerService;
import com.bigdog.carfinder.android.util.SystemUtil;
import com.bigdog.carfinder.android.util.json.JsonObject;
import com.bigdog.carfinder.android.util.json.JsonValue;
import com.bigdog.carfinder.android.util.net.INetRequest;
import com.bigdog.carfinder.android.util.net.INetResponse;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 7/25/13
 * Time: 9:58 AM
 */
public abstract class CarBase extends Activity {
    protected Context mContext;
    protected SharedPreferences sharedPreferences;
    private TrackerService.TrackerService_TrackerID trackerService_trackerID;

    public abstract void setUp();

    public TrackerService.TrackerService_TrackerID getTrackerService_trackerID() {
        return trackerService_trackerID;
    }

    public void setTrackerService_trackerID(TrackerService.TrackerService_TrackerID trackerService_trackerID) {
        this.trackerService_trackerID = trackerService_trackerID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        init();
        super.onCreate(savedInstanceState);
        mContext = CarFinderApplication.getAppContext().getApplicationContext();
        setUp();
    }

    private void init() {
        String tracker_ID = SystemUtil.getTid();

        if ("00000000".equals(tracker_ID)) {
            //去服务器请求tracker_id数据
            ApiProvider.getTrackid(new INetResponse() {
                @Override
                public void response(INetRequest req, JsonValue obj) {
                    if (obj instanceof JsonObject) {
                        final JsonObject obj_ = (JsonObject) obj;
                        SystemUtil.log(obj_.toJsonString());
                        if (SystemUtil.noError(req, obj_, true)) {
                            if (trackerService_trackerID != null) {
                                trackerService_trackerID.OnTrackerID_Success(obj_);
                            }
                        }
                    }
                }
            });
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


}
