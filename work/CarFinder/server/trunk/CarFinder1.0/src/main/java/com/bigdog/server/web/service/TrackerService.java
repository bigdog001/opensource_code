package com.bigdog.server.web.service;

import com.bigdog.server.web.action.base.FinderBaseAction;
import com.bigdog.server.web.bean.TAddress;
import com.bigdog.server.web.dao.TrackerDao;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/1/13
 * Time: 12:17 PM
 */
public interface TrackerService {
    public void setBaseAction(FinderBaseAction finderBaseAction);
    public void getTrackerId();
    public void saveTrackerPosition();
    public void batchTrackerLocation();
    public void Registe();
    public void Login();
}
