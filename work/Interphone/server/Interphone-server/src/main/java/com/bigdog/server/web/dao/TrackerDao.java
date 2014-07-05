package com.bigdog.server.web.dao;

import com.bigdog.server.web.bean.TrackerBean;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/1/13
 * Time: 12:04 PM
 */
public interface TrackerDao {
    public void save(TrackerBean trackerBean);
    public void delete(String id);
    public List<TrackerBean> getTrackerBean();
    public List<TrackerBean> getTrackerBeanByRang(int start,int size);
    public void upddate(TrackerBean trackerBean);
    public TrackerBean getByMac(String mac);
    public TrackerBean getByTid(String tid);
}
