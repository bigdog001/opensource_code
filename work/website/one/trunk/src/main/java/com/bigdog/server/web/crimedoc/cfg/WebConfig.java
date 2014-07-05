package com.bigdog.server.web.crimedoc.cfg;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 9/16/13
 * Time: 2:49 AM
 */
public class WebConfig {
    public static final int tid_length = 8;//Tid长度
    public static final String error_codeName = "errorCode";
    public static final String error_MsgName = "errorMsg";

    public static final String msg_codeName = "msgCode";
    public static final String MsgName = "Msg";
    public static final int error_code_emptyRequestParameter = 2;//请求参数异常
    public static final String error_code_emptyRequestParameterMsg = "请求参数异常";//请求参数异常

    // admin unlogin=========================
    public static final String admin_login_ok = "admin_login_ok";
    public static final String admin_login_status = "admin_login_status";
    public static final String admin_login_page_name ="admin_login_page";
    public static final String admin_login_page_location ="/WEB-INF/jsp/web/admin/index.jsp";
    // =========================admin unlogin

    // admin login success=========================
    public static final String admin_console_page_name ="admin_console_page";
    public static final String admin_console_page_location ="/WEB-INF/jsp/web/admin/admin.jsp";
    // =========================admin login success

    public static final String upload_path = "upload";
}
