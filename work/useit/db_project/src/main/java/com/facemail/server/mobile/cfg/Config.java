package com.facemail.server.mobile.cfg;


import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/1/13
 * Time: 10:03 AM
 */
public class Config {
    public static final int tid_length = 8;//Tid长度

    public static final String error_codeName = "error_code";
    public static final String error_MsgName = "error_msg";

    public static final String msg_codeName = "msg_code";
    public static final String MsgName = "msg";

    public static final int error_code_emptyMac = 0;//请求中不含mac地址
    public static final String error_code_emptyMacMsg = "mac地址为空";//请求中不含mac地址

    public static final int error_code_wrrong_position = 1;//请求中不含mac地址
    public static final String error_code_wrrong_positionMsg = "地理坐标错误";//请求中不含mac地址

    public static final int error_code_emptyRequestParameter = 2;//请求参数异常
    public static final String error_code_emptyRequestParameterMsg = "请求参数异常";//请求参数异常


    private static Properties appParams;

    public static Properties getAppParams() {
        return appParams;
    }

    public static void setAppParams(Properties appParams) {
        Config.appParams = appParams;
    }

}
