package com.facemail.server.mobile.action;

import com.facemail.server.mobile.action.base.FinderBaseAction;
import com.facemail.server.mobile.bean.base.FinderBaseBean;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/1/13
 * Time: 8:06 AM
 */
@ParentPackage("myPackage")
@Action("trackid")
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TrackerIdAction extends FinderBaseAction  implements ModelDriven<FinderBaseBean> {

    public String execute() throws Exception {
        trackerService.setBaseAction(TrackerIdAction.this);
        trackerService.getTrackerId();
        return SUCCESS;
    }


    @Override
    public FinderBaseBean getModel() {
        return getFinderBaseBean();
    }



}
