package com.bigdog.carfinder.android.obverser;

import com.bigdog.carfinder.android.util.json.JsonObject;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/7/13
 * Time: 7:50 AM
 */
public interface TrackerService {


    public interface TrackerService_TrackerID {
       public void OnTrackerID_Success(JsonObject jsonObject);
    }
}