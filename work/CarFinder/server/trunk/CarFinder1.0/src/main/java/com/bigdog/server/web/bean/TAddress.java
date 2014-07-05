package com.bigdog.server.web.bean;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 7/30/13
 * Time: 5:09 AM
 */
@Entity
@Table(name = "taddress")
public class TAddress implements Serializable {
    private long id;
    private long latitude;
    private long lonitude;
    private long detacte_time;
    private String city;
    private String stats;
    private String address;
    private TrackerBean trackerBean;

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    @Column(nullable = false)
    public long getLonitude() {
        return lonitude;
    }

    public void setLonitude(long lonitude) {
        this.lonitude = lonitude;
    }

    @Column
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column
    public String getStats() {
        return stats;
    }

    public void setStats(String stats) {
        this.stats = stats;
    }

    @Column
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @JoinColumn(name = "tid")
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH})
    public TrackerBean getTrackerBean() {
        return trackerBean;
    }

    public void setTrackerBean(TrackerBean trackerBean) {
        this.trackerBean = trackerBean;
    }

    @Column
    public long getDetacte_time() {
        return detacte_time;
    }

    public void setDetacte_time(long detacte_time) {
        this.detacte_time = detacte_time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TAddress tAddress = (TAddress) o;

        if (detacte_time != tAddress.detacte_time) return false;
        if (id != tAddress.id) return false;
        if (latitude != tAddress.latitude) return false;
        if (lonitude != tAddress.lonitude) return false;
        if (address != null ? !address.equals(tAddress.address) : tAddress.address != null) return false;
        if (city != null ? !city.equals(tAddress.city) : tAddress.city != null) return false;
        if (stats != null ? !stats.equals(tAddress.stats) : tAddress.stats != null) return false;
        if (trackerBean != null ? !trackerBean.equals(tAddress.trackerBean) : tAddress.trackerBean != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (latitude ^ (latitude >>> 32));
        result = 31 * result + (int) (lonitude ^ (lonitude >>> 32));
        result = 31 * result + (int) (detacte_time ^ (detacte_time >>> 32));
        return result;
    }
}
