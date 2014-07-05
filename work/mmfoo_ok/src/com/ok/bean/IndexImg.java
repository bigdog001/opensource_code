package com.ok.bean;

import java.sql.Timestamp;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-6-13
 * Time: 1:00:13
 */
public class IndexImg {
    private int imgid;
    private int oedds;//排序
    private String img;
    private String thumb;
    private String truename;
    private String city;
    private String star;
    private String target_url;
    private Timestamp creattime;
    public IndexImg(){
     creattime=new Timestamp(System.currentTimeMillis());
     oedds=0;
    }
    public Timestamp getCreattime() {
        return creattime;
    }

    public int getOedds() {
        return oedds;
    }

    public void setOedds(int oedds) {
        this.oedds = oedds;
    }

    public void setCreattime(Timestamp creattime) {
        this.creattime = creattime;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getTarget_url() {
        return target_url;
    }

    public void setTarget_url(String target_url) {
        this.target_url = target_url;
    }
}
