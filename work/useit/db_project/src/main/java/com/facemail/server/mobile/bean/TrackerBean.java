package com.facemail.server.mobile.bean;

import com.facemail.server.mobile.bean.base.FinderBaseBean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 7/30/13
 * Time: 4:57 AM
 */

@Entity
@Table(name = "tracker", schema = "bigdog")
public class TrackerBean implements Serializable {
    private String id;
    private String passwd;
    private String mac;
    private int t_status;//1 表示此tracker在服务器上已经存在，根据mac地址来判断，此机制服务与匿名用户
    private List<TAddress> tAddresses;
    private UserBean userBean;


    @Id
    public String getId() {
        return id;
    }

    public void setId(String tid) {
        this.id = tid;
    }

    @Column
    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Column
    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Column
    public int getT_status() {
        return t_status;
    }

    public void setT_status(int t_status) {
        this.t_status = t_status;
    }

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "trackerBean")
    public List<TAddress> gettAddresses() {
        return tAddresses;
    }

    public void settAddresses(List<TAddress> tAddresses) {
        this.tAddresses = tAddresses;
    }

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }
}
