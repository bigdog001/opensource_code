package com.facemail.server.mobile.bean.base;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 7/29/13
 * Time: 1:10 PM
 */
public class FinderBaseBean {
    private long call_id;//客户端发起请求时的手机时间
    private String method;
    private String v;
    private String client_info;
    private String regex_code;
    private String api_key;

    //=================================
    private String mac;
    private long latitude;
    private long lonitude;
    private long detacte_time;
    private String tid;
    private String language;


    private String userName;
    private String passwd;
    private String sid;//接收登录后的session id

    //       mac        latitude lonitude   tid     detacte_time
    // ec:55:f9:c0:03:32#27813054#-80425241#2L3QR92I#13990095556772
    private String batch_location;


    public long getCall_id() {
        return call_id;
    }

    public void setCall_id(long call_id) {
        this.call_id = call_id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getClient_info() {
        return client_info;
    }

    public void setClient_info(String client_info) {
        this.client_info = client_info;
    }

    public String getRegex_code() {
        return regex_code;
    }

    public void setRegex_code(String regex_code) {
        this.regex_code = regex_code;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public long getLonitude() {
        return lonitude;
    }

    public void setLonitude(long lonitude) {
        this.lonitude = lonitude;
    }

    public long getDetacte_time() {
        return detacte_time;
    }

    public void setDetacte_time(long detacte_time) {
        this.detacte_time = detacte_time;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getBatch_location() {
        return batch_location;
    }

    public void setBatch_location(String batch_location) {
        this.batch_location = batch_location;
    }

    @Override
    public String toString() {
        return "FinderBaseBean{" +
                "call_id=" + call_id +
                ", method='" + method + '\'' +
                ", v='" + v + '\'' +
                ", client_info='" + client_info + '\'' +
                ", regex_code='" + regex_code + '\'' +
                ", api_key='" + api_key + '\'' +
                ", mac='" + mac + '\'' +
                ", latitude=" + latitude +
                ", lonitude=" + lonitude +
                ", detacte_time=" + detacte_time +
                ", tid='" + tid + '\'' +
                ", language='" + language + '\'' +
                ", userName='" + userName + '\'' +
                ", passwd='" + passwd + '\'' +
                '}';
    }
}
