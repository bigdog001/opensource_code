package com.bigdog.carfinder.android.bean;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/4/13
 * Time: 11:02 AM
 */
public class LocationBean {

    private String latitude;
    private String lontitude;
    private String tid;
    private long detacte_time;
    private int upload_status = 0;
    private String _id;


    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLontitude() {
        return lontitude;
    }

    public void setLontitude(String lontitude) {
        this.lontitude = lontitude;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public long getDetacte_time() {
        return detacte_time;
    }

    public void setDetacte_time(long detacte_time) {
        this.detacte_time = detacte_time;
    }

    public int getUpload_status() {
        return upload_status;
    }

    public void setUpload_status(int upload_status) {
        this.upload_status = upload_status;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "LocationBean{" +
                "latitude='" + latitude + '\'' +
                ", lontitude='" + lontitude + '\'' +
                ", tid='" + tid + '\'' +
                ", detacte_time=" + detacte_time +
                ", upload_status=" + upload_status +
                ", _id='" + _id + '\'' +
                '}';
    }
}
