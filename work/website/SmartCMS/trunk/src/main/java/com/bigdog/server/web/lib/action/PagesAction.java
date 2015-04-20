package com.bigdog.server.web.lib.action;

import com.bigdog.server.web.crimedoc.action.base.BaseAction;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 10/8/13
 * Time: 6:58 AM
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Controller("pagesAction")
public class PagesAction extends BaseAction {
    @Action("/index_website")
    public String index() {
        getWsbean().setW(1);
        return "index";
    }

    @Action("/product")
    public String product() {
        getWsbean().setW(2);
        return "product";
    }

    @Action("/help")
    public String help() {
        getWsbean().setW(3);
        return "help";
    }

    @Action("/cooper")
    public String cooper() {
        getWsbean().setW(4);
        return "cooper";
    }

    @Action("/contact")
    public String contact() {
        getWsbean().setW(5);
        return "contact";
    }

       
    public String test() {
        getWsbean().setW(5);
        return "success";
    }
}
