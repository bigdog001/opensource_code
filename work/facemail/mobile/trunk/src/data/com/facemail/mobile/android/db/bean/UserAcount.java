package com.facemail.mobile.android.db.bean;

import com.facemail.mobile.android.mail.MailTaskStatus;
import com.facemail.mobile.android.watchdog.watchDogService;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 4/17/13
 * Time: 1:03 PM
 */
public class UserAcount extends BaseBean implements Comparable<UserAcount> ,Serializable {   //邮件账户
    private int _id;
    private String user_name;   //和FaceMailMessage中的MAIL_USER_NAME关联
    private String user_pwd;
    private String user_smtp;
    private String user_pop3;
    private String user_port_smtp="25";
    private String User_port_pop3="110";
    private Date lastRcv;
    private Long last_task_Rcv=System.currentTimeMillis();//不和数据库相关，只是又来标识其在任务池中的上次完成事件
    private int total_mail_number;
    private int scanfrequency=0;
    private MailTaskStatus mailTaskStatus=new MailTaskStatus(0,0,0,0);
    private watchDogService.BackgroundRefresh backgroundRefresh;

    public watchDogService.BackgroundRefresh getBackgroundRefresh() {
        return backgroundRefresh;
    }

    public void setBackgroundRefresh(watchDogService.BackgroundRefresh backgroundRefresh) {
        this.backgroundRefresh = backgroundRefresh;
    }

    public MailTaskStatus getMailTaskStatus() {
        return mailTaskStatus;
    }

    public void setMailTaskStatus(MailTaskStatus mailTaskStatus) {
        this.mailTaskStatus = mailTaskStatus;
    }

    public int getTotal_mail_number() {
        return total_mail_number;
    }

    public void setTotal_mail_number(int total_mail_number) {
        this.total_mail_number = total_mail_number;
    }

    public Long getLast_task_Rcv() {
        return last_task_Rcv;
    }

    public void setLast_task_Rcv(Long last_task_Rcv) {
        this.last_task_Rcv = last_task_Rcv;
    }

    public int getScanfrequency() {
        return scanfrequency;
    }

    public void setScanfrequency(int scanfrequency) {
        this.scanfrequency = scanfrequency;
    }

    public Date getLastRcv() {
        return lastRcv;
    }

    public void setLastRcv(Date lastRcv) {
        this.lastRcv = lastRcv;
    }

    public String getUser_port_smtp() {
        return user_port_smtp;
    }

    public void setUser_port_smtp(String user_port_smtp) {
        this.user_port_smtp = user_port_smtp;
    }

    public String getUser_port_pop3() {
        return User_port_pop3;
    }

    public void setUser_port_pop3(String user_port_pop3) {
        User_port_pop3 = user_port_pop3;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getUser_smtp() {
        return user_smtp;
    }

    public void setUser_smtp(String user_smtp) {
        this.user_smtp = user_smtp;
    }

    public String getUser_pop3() {
        return user_pop3;
    }

    public void setUser_pop3(String user_pop3) {
        this.user_pop3 = user_pop3;
    }



    @Override
    public int compareTo(UserAcount another) {
        return this.getUser_name().compareTo(another.getUser_name());
    }

    @Override
    public String toString() {
        return "UserAcount{" +
                "_id=" + _id +
                ", user_name='" + user_name + '\'' +
                ", user_pwd='" + user_pwd + '\'' +
                ", user_smtp='" + user_smtp + '\'' +
                ", user_pop3='" + user_pop3 + '\'' +
                ", user_port_smtp='" + user_port_smtp + '\'' +
                ", User_port_pop3='" + User_port_pop3 + '\'' +
                ", lastRcv=" + lastRcv +
                ", total_mail_number=" + total_mail_number +
                '}';
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
            user_name=null;
            user_pwd=null;
            user_smtp=null;
            user_pop3=null;
            user_port_smtp=null;
            User_port_pop3=null;
            lastRcv=null;
            last_task_Rcv=null;
            mailTaskStatus=null;
            backgroundRefresh=null;
    }
    public void close(){
        user_name=null;
        user_pwd=null;
        user_smtp=null;
        user_pop3=null;
        user_port_smtp=null;
        User_port_pop3=null;
        lastRcv=null;
        last_task_Rcv=null;
        mailTaskStatus=null;
        backgroundRefresh=null;
    }
}



