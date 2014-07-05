package com.ok.bean;

import java.sql.Timestamp;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-5-22
 * Time: 13:40:37
 */
public class UserImg {
    private int imgid;
    private int setid;   //所属相册
    private String  email;//所属人email
    private String  thumb_img;//缩略图
    private String  is_face;//所属人email
    private String imgname;//相片全名 图片文件名
    private Timestamp createtime;
    private String img_name;//图片在相册中显示的名称
    private String img_discribe;//图片描述
    private String img_whosee;//图片能被谁看   1: 全站可见  2： 仅好友可见 3： 仅自己可见
    public String toStirng(){
        return "imgid is:"+imgid+
                "setid is:"+setid+
                "email is:"+
                "imgname is:"+imgname+
                "createtime is:"+createtime;
    }

    public String getIs_face() {
        return is_face;
    }

    public void setIs_face(String is_face) {
        this.is_face = is_face;
    }

    public String getThumb_img() {
        return thumb_img;
    }

    public void setThumb_img(String thumb_img) {
        this.thumb_img = thumb_img;
    }

    public UserImg(){
        createtime=new Timestamp(System.currentTimeMillis());
        img_whosee="1";
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public String getImg_name() {
        return img_name;
    }

    public void setImg_name(String img_name) {
        this.img_name = img_name;
    }

    public String getImg_discribe() {
        return img_discribe;
    }

    public void setImg_discribe(String img_discribe) {
        this.img_discribe = img_discribe;
    }

    public String getImg_whosee() {
        return img_whosee;
    }

    public void setImg_whosee(String img_whosee) {
        this.img_whosee = img_whosee;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }

    public int getSetid() {
        return setid;
    }

    public void setSetid(int setid) {
        this.setid = setid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgname() {
        return imgname;
    }

    public void setImgname(String imgname) {
        this.imgname = imgname;
    }
}
