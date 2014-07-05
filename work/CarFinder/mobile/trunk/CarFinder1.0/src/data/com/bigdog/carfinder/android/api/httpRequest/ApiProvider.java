package com.bigdog.carfinder.android.api.httpRequest;

import android.content.Context;
import android.os.Build;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import com.bigdog.carfinder.android.application.CarFinderApplication;
import com.bigdog.carfinder.android.bean.LocationBean;
import com.bigdog.carfinder.android.conf.Config;
import com.bigdog.carfinder.android.conf.Variable;
import com.bigdog.carfinder.android.util.SystemUtil;
import com.bigdog.carfinder.android.util.json.JsonObject;
import com.bigdog.carfinder.android.util.net.INetRequest;
import com.bigdog.carfinder.android.util.net.INetResponse;
import com.bigdog.carfinder.android.util.net.http.HttpProviderWrapper;
import com.bigdog.carfinder.android.util.net.http.HttpRequestWrapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 7/28/13
 * Time: 7:58 AM
 */
public class ApiProvider {
    public static String api_finder_Host;
    public static String api_finder_Url;

    static {
        api_finder_Host = Config.api_finder_Host;
        api_finder_Url = Config.api_finder_Url;
    }

    public static void getTrackid(final INetResponse response) {
        JsonObject bundle = m_buildRequestBundle(false);
        bundle.put("method", "trackid");
        INetRequest request = m_buildRequest(api_finder_Url, bundle, response, 0);
        HttpProviderWrapper.getInstance().addRequest(request);
    }

    public static void saveLocation(final INetResponse response, LocationBean locationBean) {
        JsonObject bundle = m_buildRequestBundle(false);
        bundle.put("method", "location");
        bundle.put("latitude", locationBean.getLatitude());
        bundle.put("lonitude", locationBean.getLontitude());
        bundle.put("detacte_time", locationBean.getDetacte_time());
        bundle.put("tid", locationBean.getTid());
        INetRequest request = m_buildRequest(api_finder_Url, bundle, response, 0);
        HttpProviderWrapper.getInstance().addRequest(request);
    }

    public static void saveLocationBatch(final INetResponse response, String tid, List<LocationBean> locationBeans) {
        JsonObject bundle = m_buildRequestBundle(false);
        bundle.put("method", "locationBatch");
        bundle.put("tid", tid);
        StringBuffer batch_location = new StringBuffer();
        for (LocationBean lb : locationBeans) {
            batch_location.append(Variable.getMacAddress()).append("#").append(lb.getLatitude()).append("#").append(lb.getLontitude()).append("#").append(tid).append("#").append(System.currentTimeMillis()).append(",");
        }
        String batch_location_ = batch_location.toString();
        batch_location_ = batch_location_.substring(0, batch_location_.length() - 1);
        bundle.put("batch_location", batch_location_);
        INetRequest request = m_buildRequest(api_finder_Url, bundle, response, 0);
        HttpProviderWrapper.getInstance().addRequest(request);
    }

    public static void getPhoneType(final INetResponse response) {
        JsonObject bundle = m_buildRequestBundle(false);
        bundle.put("method", "phoneclient.getRegexMsg");
        INetRequest request = m_buildRequest(api_finder_Url, bundle, response, 0);
        HttpProviderWrapper.getInstance().addRequest(request);
    }

    public static void userRegiste(final INetResponse response, String userName,String passwd) {
        JsonObject bundle = m_buildRequestBundle(false);
        bundle.put("method", "userRegiste");
        bundle.put("userName", userName);
        bundle.put("passwd", passwd);
        INetRequest request = m_buildRequest(api_finder_Url, bundle, response, 0);
        HttpProviderWrapper.getInstance().addRequest(request);
    }


    public static void userLogin(final INetResponse response, String passwd, String userName) {
        JsonObject bundle = m_buildRequestBundle(false);
        bundle.put("method", "userLogin");
        bundle.put("userName", userName);
        bundle.put("passwd", passwd);
        INetRequest request = m_buildRequest(api_finder_Url, bundle, response, 0);
        HttpProviderWrapper.getInstance().addRequest(request);
    }

    private static INetRequest m_buildRequest(String url, JsonObject sm, INetResponse response, int requestType) {
        INetRequest request = new HttpRequestWrapper();
        request.setType(requestType);
        request.setUrl(url);
        request.setData(sm);
        request.setResponse(response);
        return request;
    }

    /**
     * 构造一个网絡请求的基本数据bundle, 填入api_key, call_id, session_key.
     *
     * @param batchRun 是否使用批调用. 使用批调用的请求不包含session_key, session_key统一由batch.run方法提供.
     */
    private static JsonObject m_buildRequestBundle(boolean batchRun) {
        JsonObject bundle = new JsonObject();
        // 使用 3G 服务器的 api_key
        bundle.put("api_key", "api_key");
        bundle.put("mac",Variable.getMacAddress());
        bundle.put("call_id", System.currentTimeMillis());
        bundle.put("client_info", getClientInfo());
        bundle.put("v", "1.0");
        bundle.put("regex_code", "regex_code");
        return bundle;
    }

    /**
     * 统计信息
     *
     * @return
     */
    public static String getClientInfo() {

        int cellid = 0;
        TelephonyManager tm = (TelephonyManager) CarFinderApplication.getAppContext().getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null) {
            // GsmCellLocation location = (GsmCellLocation)
            // tm.getCellLocation();
            CellLocation location = tm.getCellLocation();
            if (location != null) {
                if (location instanceof GsmCellLocation) {
                    cellid = ((GsmCellLocation) location).getCid();
                }
            }
        }


        JsonObject clientInfo = new JsonObject();
        clientInfo.put("model", Build.MODEL);
        clientInfo.put("os", Build.VERSION.SDK + "_" + Build.VERSION.RELEASE);
        clientInfo.put("screen", "screen");
        clientInfo.put("font", "");
        clientInfo.put("ua", "ua");
        clientInfo.put("from", "from");
//		Log.v("kxy", "------from: " + Variables.from);
        clientInfo.put("cellId", cellid + "");
        clientInfo.put("version", "version");
        return clientInfo.toJsonString();
    }
}
