package com.facemail.server.mobile.service;

import com.facemail.server.mobile.action.base.FinderBaseAction;
import com.facemail.server.mobile.bean.TAddress;
import com.facemail.server.mobile.dao.TrackerDao;

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
