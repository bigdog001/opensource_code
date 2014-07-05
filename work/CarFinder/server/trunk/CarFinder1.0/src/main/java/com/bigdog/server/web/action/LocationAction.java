package com.bigdog.server.web.action;

import com.bigdog.server.web.action.base.FinderBaseAction;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 9/21/13
 * Time: 5:54 AM
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LocationAction  extends FinderBaseAction {

    @Action(value = "/location")
    public String Location() throws Exception {
        logger.info("Location in new ....");
        trackerService.setBaseAction(LocationAction.this);
        trackerService.saveTrackerPosition();
        return SUCCESS;
    }

    @Action(value = "/locationBatch")
    public String LocationBatch() throws Exception {
        logger.info("LocationBatch in new ....");
        trackerService.setBaseAction(LocationAction.this);
        trackerService.batchTrackerLocation();
        return SUCCESS;
    }

    @Action(value = "/trackid")
    public String Trackid() throws Exception {
        logger.info("trackid in new ....");
        trackerService.setBaseAction(LocationAction.this);
        trackerService.getTrackerId();
        return SUCCESS;
    }

}
