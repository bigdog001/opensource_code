package com.facemail.mobile.android.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import com.facemail.mobile.android.application.FaceMailApplication;
import com.facemail.mobile.android.db.bean.FaceMailMessage;
import com.facemail.mobile.android.mail.IMailRequest;
import com.facemail.mobile.android.util.json.JsonObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
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
    private static  boolean log_flag=true;
    public static Toast gToast;
    public static void log(String logBody) {
        if (log_flag) {
            Log.d("wjn",logBody+",in "+new Date()+", "+System.currentTimeMillis());
        }
    }

    //是否自动去服务器检查更新
    public static boolean AutoUpdate(Context context){
        boolean updateResult=false;
      SharedPreferences sp= context.getSharedPreferences("config",Context.MODE_PRIVATE);
        updateResult=  sp.getBoolean("isAutoUpdate",false);
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
    public static boolean isNumeric(String str){
        for(int i=str.length();--i>=0;){
            int chr=str.charAt(i);
            if(chr<48 || chr>57)
                return true;
        }
        return false;
    }

    public static String getDate(Date date,int withYear){
       String result="";
        if (date==null) {
            return result;
        }
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf;
        if (withYear==1) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else {
            sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
        }

        result = sdf.format(date);
        return result;
    }
    public static Date getDate(String date){
        Date result=null;
        if ("".equals(date)||date==null) {
             result=new Date();
            return result;
        }

            SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
//            log(date);
            result = sdf.parse(date);
        } catch (ParseException e) {
            SystemUtil.log("时间数据转型失败:"+e.getMessage());
            result=new Date();
        }
        return result;
    }

    //判断email格式是否正确
    public static boolean isEmail(String email) {
        if ("".equals(email)||email==null) {
            return  false;
        }
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    public static boolean noError(final IMailRequest request, JsonObject rsp) {
        return noError(request, rsp, true);
    }


    /* 增加一个show参数用来标示是否弹toast */
    public static boolean noError(final IMailRequest request, JsonObject rsp, boolean show) {
        int error_code = (int) rsp.getNum("error_code");
        String error_msg = rsp.getString("error_msg");
        switch (error_code) {
            case 44:// network broken
                return false;
            case 43:
                // mail param wrong ,wrong emailAddress or empty content
                return false;
        }
        if (error_code != 0) {
            showToast(error_msg, false, show);
            return false;
        }
        return true;
    }

    public static void showToast(final CharSequence text, final boolean lengthLong, boolean show) {
        if (show == false) {
            return;
        }

        Runnable update = new Runnable() {
            public void run() {
                if (gToast != null) {
                    if (text == null) {
                        gToast.cancel();
                    } else {
                        gToast.setText(text);
                        gToast.setDuration(lengthLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
                        gToast.show();
                    }
                }
            }
        };
        FaceMailApplication.getFaceMailContext().getHandler().post(update);
    }

    public static File getMailDir(String email){
        File file=null;
        if (email==null) {
            return  file;
        }
        if ("".equals(email)) {
            return  file;
        }
        if (!isEmail(email)) {
         log("用户名非法，无法为该用户创建缓存目录");
            return  file;
        }

        String sdFilePath = Environment.getExternalStorageDirectory() + File.separator+"facemail"+ File.separator+email;
          file = new File(sdFilePath);
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

    /**
     * 用来判断服务是否运行.
     * @param className 判断的服务名字
     * @return true 在运行 false 不在运行
     */
    public static boolean isServiceRunning(Context mContext,String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager)
                mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList
                = activityManager.getRunningServices(30);
        if (!(serviceList.size()>0)) {
            return false;
        }
        for (int i=0; i<serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    public static int getIndex(int [] x,int value) {
        int index=0;
        for(int i=0;i<x.length;i++) {
            if (x[i]==value) {
                index=i;
                break;
            }
        }
        return  index;

    }

}
