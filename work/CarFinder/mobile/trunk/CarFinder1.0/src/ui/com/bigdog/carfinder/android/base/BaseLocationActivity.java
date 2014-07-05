package com.bigdog.carfinder.android.base;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
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
 * Time: 11:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class BaseLocationActivity extends Activity {
    protected ILocationListener mLocationListener;
    public final int LATLON_DEFAULT = 255 * 1000000;
    protected LocationThread locationThread;

    public void getLocaiton(Context context, ILocationListener locationListener) {
        mLocationListener = locationListener;
        locationThread = new LocationThread(context, locationandler);
        locationThread.chekPosition();

        //如果本地缓存中游数据，则直接调用mLocationListener.onLocateSuccess()方法。
    }

    protected Handler locationandler = new Handler() {
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
                    mLocationListener.onLocateSuccess(LATLON_DEFAULT, LATLON_DEFAULT, latlon);
                    break;
                case LocationThread.RESULT_FAIL:
                    mLocationListener.onLocateFail();
                    break;
                default:
                    break;
            }
        }

        ;
    };
}
