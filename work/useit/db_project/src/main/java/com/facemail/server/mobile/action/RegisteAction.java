package com.facemail.server.mobile.action;

import com.facemail.server.mobile.action.base.FinderBaseAction;
import com.facemail.server.mobile.bean.base.FinderBaseBean;
import com.facemail.server.mobile.cfg.Config;
import com.facemail.server.mobile.util.Tools;
import com.opensymphony.xwork2.ModelDriven;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/3/13
 * Time: 3:55 AM
 */

@ParentPackage("myPackage")
@Action("userRegiste")
@Controller
//@Results({@Result(name = "success", location = "/WEB-INF/jsp/test/index.jsp", type = "dispatcher"),@Result(name = "fail",location = "/WEB-INF/jsp/test/fail.jsp",type = "dispatcher")})
@InterceptorRefs(value = { @InterceptorRef("defaultStack"),@InterceptorRef("bigdog") })
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RegisteAction extends FinderBaseAction implements ModelDriven<FinderBaseBean> {

    public String execute() throws Exception {
        trackerService.setBaseAction(RegisteAction.this);
        trackerService.Registe();
        return SUCCESS;
    }

    @Override
    public FinderBaseBean getModel() {
        return getFinderBaseBean();
    }

}
