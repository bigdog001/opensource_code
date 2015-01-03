package com.bigdog.server.web.crimedoc.util;

import com.bigdog.server.web.crimedoc.cfg.WebConfig;
import net.sf.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/1/13
 * Time: 1:40 PM
 */

public class Tools {
    public static InputStream getInputStream() {
        InputStream inputStream = null;
        JSONObject obj = new JSONObject();
        obj.put(WebConfig.error_codeName, WebConfig.error_code_emptyRequestParameter);
        obj.put(WebConfig.error_MsgName, WebConfig.error_code_emptyRequestParameterMsg);

        try {
            inputStream = new ByteArrayInputStream(obj.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {

        }
        return inputStream;

    }

    public static InputStream getInputStream(String msg) {
        InputStream inputStream = null;
        try {
            inputStream = new ByteArrayInputStream(msg.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {

        }
        return inputStream;

    }

    public static String GenerateTid() {
        int length = WebConfig.tid_length;
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static String getValueByKey(String language1, String language2, String key) {
        String value = "";
        if (!"".equals(language1) && !"".equals(language2) && language1 != null & language2 != null && !"".equals(key) && key != null) {
            Locale locale = new Locale(language1.trim(), language2.trim());
            ResourceBundle messages = ResourceBundle.getBundle("finder", locale);
            value = messages.getString(key);
        } else {
            return value;
        }

        return value;
    }

    public static String getSessionId()  {
       long now = System.currentTimeMillis();
       long _pre = new Random().nextLong();
        try {
            return encrypt((now+""+_pre).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getMD5(String pa)  {
        try {
            return encrypt((pa).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
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

    public static String getSubFix(String fName){
        String sub = "";
        if (!"".equals(fName)&&fName != null) {
            sub = "."+fName.substring(fName.indexOf(".")+1,fName.length());
        }

        return sub;
    }



}
