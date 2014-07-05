package com.facemail.server.mobile.action;

import com.facemail.server.mobile.action.base.TrackerBaseAction;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/4/13
 * Time: 3:24 AM
 */

@Action("fileUpload")
@Controller
@Results({@Result(name = "success", location = "/WEB-INF/jsp/test/index.jsp", type = "dispatcher"),
          @Result(name = "fail",location = "/WEB-INF/jsp/test/fail.jsp",type = "dispatcher")})
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//@InterceptorRefs(value = { @InterceptorRef("fileUploadStack") })
public class TestAction extends TrackerBaseAction {

    public String execute() {
        return "success";
    }

//    @Action(value = "/welcome", results = {@Result(location = "/WEB-INF/jsp/Poc_test.jsp", type = "dispatcher", name = "success")})
    public String welcome() {
        return "fail";
    }
}
