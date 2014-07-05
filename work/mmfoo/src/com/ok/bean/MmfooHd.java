package com.ok.bean;

import java.sql.Timestamp;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-5-22
 * Time: 13:57:10
 */
public class MmfooHd {
    private int hdid;
    private int orders;
    private String hdname;
    private String hdface;
    private String hdtitle;
    private String hdabstract;
    private String hdcontent;
    private Timestamp starttime;
    private Timestamp endtime;
    private Timestamp createtime;
    public String toString() {
        return "hdid is:" + hdid +
                "hdname is:" + hdname +
                "hdtitle is:" + hdtitle +
                "hdabstract is:" + hdabstract +
                "hdcontent is:" + hdcontent +
                "starttime is" + starttime +
                "endtime is:" + endtime;
    }
     public MmfooHd(){
        createtime=new Timestamp(System.currentTimeMillis());
    }
    public Timestamp getCreatetime() {
        return createtime;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public int getHdid() {
        return hdid;
    }

    public void setHdid(int hdid) {
        this.hdid = hdid;
    }

    public String getHdname() {
        return hdname;
    }

    public String getHdface() {
        return hdface;
    }

    public void setHdface(String hdface) {
        this.hdface = hdface;
    }

    public void setHdname(String hdname) {
        this.hdname = hdname;
    }

    public String getHdtitle() {
        return hdtitle;
    }

    public void setHdtitle(String hdtitle) {
        this.hdtitle = hdtitle;
    }

    public String getHdabstract() {
        return hdabstract;
    }

    public void setHdabstract(String hdabstract) {
        this.hdabstract = hdabstract;
    }

    public String getHdcontent() {
        return hdcontent;
    }

    public void setHdcontent(String hdcontent) {
        this.hdcontent = hdcontent;
    }

    public Timestamp getStarttime() {
        return starttime;
    }

    public void setStarttime(Timestamp starttime) {
        this.starttime = starttime;
    }

    public Timestamp getEndtime() {
        return endtime;
    }

    public void setEndtime(Timestamp endtime) {
        this.endtime = endtime;
    }
}
