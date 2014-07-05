package com.bigdog.carfinder.android.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import com.bigdog.carfinder.android.application.CarFinderApplication;
import com.bigdog.carfinder.android.conf.Config;
import com.bigdog.carfinder.android.util.json.JsonObject;
import com.bigdog.carfinder.android.util.net.INetRequest;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created with with jiunian.wang
 * User: bigdog
 * Date: 12/16/12
 * Time: 2:11 PM
 */
public class SystemUtil {
    private static boolean log_flag = true;

    public static void log(String logBody) {
        if (log_flag) {
            Log.d("wjn", logBody + ",in " + new Date() + ", " + System.currentTimeMillis());
        }
    }

    /**
     * @return if api level >= level
     */
    public static boolean fitApiLevel(int level) {
        try {
            int sdkVersion = Integer.parseInt(Build.VERSION.SDK);
            if (sdkVersion >= level) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isServiceRunning(Context mContext, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager)
                mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList
                = activityManager.getRunningServices(30);
        if (!(serviceList.size() > 0)) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    //是否自动去服务器检查更新
    public static boolean AutoUpdate(Context context) {
        boolean updateResult = false;
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        updateResult = sp.getBoolean("isAutoUpdate", false);
        return updateResult;
    }

    /**
     * 将input流转为byte数组，自动关闭
     *
     * @param input
     * @return
     */
    public static byte[] toByteArray(InputStream input) throws Exception {
        if (input == null) {
            return null;
        }
        ByteArrayOutputStream output = null;
        byte[] result = null;
        try {
            output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024 * 100];
            int n = 0;
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
            }
            result = output.toByteArray();
        } finally {
            closeQuietly(input);
            closeQuietly(output);
        }
        return result;
    }

    /**
     * 关闭InputStream
     */
    public static void closeQuietly(InputStream is) {
        try {
            if (is != null) {
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭InputStream
     */
    public static void closeQuietly(OutputStream os) {
        try {
            if (os != null) {
                os.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            int chr = str.charAt(i);
            if (chr < 48 || chr > 57)
                return true;
        }
        return false;
    }


    //判断email格式是否正确
    public static boolean isEmail(String email) {
        if ("".equals(email) || email == null) {
            return false;
        }
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    public static String getLocalMacAddress(Context context) {

        String mac = "000000";
        if (context != null) {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (wifi != null) {
                WifiInfo info = wifi.getConnectionInfo();
                if (info != null) {
                    mac = info.getMacAddress();
                }
            }
        }
        log("mac is:" + mac);
        return mac;
    }

    /* 增加一个show参数用来标示是否弹toast */
    public static boolean noError(final INetRequest request, JsonObject rsp, boolean show) {
        int error_code = (int) rsp.getNum("error_code");
        String error_msg = rsp.getString("error_msg");
        switch (error_code) {
            case -9000:// 临时做语音的错误码
                return false;
            case -99:
                showToast("无法连接网络，请检查您的手机网络设置...", show);
                return false;
            case -97:
                // showToast("无法连接网络，请检查您的手机网络设置...", false, show);
                return false;
            case -90: // 获取图片失败
                return false;
            case 4:
                showToast("您的操作过于频繁，请歇一会儿再试(4)", show);
                return false;
        }
        if (error_code != 0) {
            showToast(error_msg, show);
            return false;
        }
        return true;
    }


    public static void showToast(final CharSequence text,  boolean show) {
        if (show == false) {
            return;
        } else {
            CarFinderApplication.getAppContext().getmHandler().post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(CarFinderApplication.getAppContext(), text, 0).show();
                }
            });
        }
    }

    public static String toMD5(String s) {
        if (s != null) {
            try {
                byte[] bs = s.getBytes("UTF-8");
                return encrypt(bs);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private synchronized static String encrypt(byte[] obj) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(obj);
            byte[] bs = md5.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bs.length; i++) {
                sb.append(Integer.toHexString((0x000000ff & bs[i]) | 0xffffff00).substring(6));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDate(Date date, int withYear) {
        String result = "";
        if (date == null) {
            return result;
        }
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf;
        if (withYear == 1) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else {
            sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
        }

        result = sdf.format(date);
        return result;
    }

    public static Date getDate(String date) {
        Date result = null;
        if ("".equals(date) || date == null) {
            result = new Date();
            return result;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
//            log(date);
            result = sdf.parse(date);
        } catch (ParseException e) {
            SystemUtil.log("时间数据转型失败:" + e.getMessage());
            result = new Date();
        }
        return result;
    }

    public static String getTid() {
        String tid = null;

        SharedPreferences sharedPreferences = CarFinderApplication.getAppContext().getSharedPreferences();
        tid = sharedPreferences.getString(Config.Config_tracker_id_name, "00000000");
        return tid;
    }


}
