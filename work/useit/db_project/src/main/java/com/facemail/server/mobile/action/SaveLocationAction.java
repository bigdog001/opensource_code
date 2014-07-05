package com.facemail.server.mobile.action;

import com.facemail.server.mobile.action.base.FinderBaseAction;
import com.facemail.server.mobile.bean.base.FinderBaseBean;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/2/13
 * Time: 9:51 AM
 */
@ParentPackage("myPackage")
@Action("location")
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SaveLocationAction extends FinderBaseAction implements ModelDriven<FinderBaseBean> {

    public String execute() throws Exception {
        trackerService.setBaseAction(SaveLocationAction.this);
        trackerService.saveTrackerPosition();
        return SUCCESS;
    }


    @Override
    public FinderBaseBean getModel() {
        return getFinderBaseBean();
    }



}
