package com.facemail.server.mobile.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 7/30/13
 * Time: 6:41 AM
 */
@Entity
@Table(name = "user",schema = "bigdog")
public class UserBean  implements Serializable {
    private int id;
    private String username;//用户名和email其实是一体的
    private String passwd;
    private String nickname;
    private List<TrackerBean> trackerBeans;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(unique = true,nullable = false,updatable = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column
    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Column
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @OneToMany(mappedBy = "userBean")
    public List<TrackerBean> getTrackerBeans() {
        return trackerBeans;
    }

    public void setTrackerBeans(List<TrackerBean> trackerBeans) {
        this.trackerBeans = trackerBeans;
    }
}
