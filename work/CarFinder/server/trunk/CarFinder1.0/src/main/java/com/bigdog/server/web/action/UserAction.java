package com.bigdog.server.web.action;

import com.bigdog.server.web.action.base.FinderBaseAction;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 9/21/13
 * Time: 5:45 AM
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserAction extends FinderBaseAction {
    @Action(value = "/userRegiste")
    public String Registe() {
        logger.info("registe in new ....");
        trackerService.setBaseAction(UserAction.this);
        trackerService.Registe();
        return SUCCESS;
    }

    @Action(value = "/userLogin")
    public String Login() {
        logger.info("Login in new ....");
        trackerService.setBaseAction(UserAction.this);
        trackerService.Login();
        return SUCCESS;
    }


}
