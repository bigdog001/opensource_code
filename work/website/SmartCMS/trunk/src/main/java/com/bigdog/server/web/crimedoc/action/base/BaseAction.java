package com.bigdog.server.web.crimedoc.action.base;

import com.bigdog.server.web.crimedoc.bean.Wsbean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 9/1/13
 * Time: 1:21 AM
 */
@ParentPackage("myPackage")
@Results({@Result(name = "success", location = "/WEB-INF/jsp/web/index.jsp", type = "dispatcher"),
        @Result(name = "index", location = "/WEB-INF/jsp/web/index.jsp", type = "dispatcher"),
        @Result(name = "product", location = "/WEB-INF/jsp/web/product.jsp", type = "dispatcher"),
        @Result(name = "help", location = "/WEB-INF/jsp/web/help.jsp", type = "dispatcher"),
        @Result(name = "cooper", location = "/WEB-INF/jsp/web/cooper.jsp", type = "dispatcher"),
        @Result(name = "contact", location = "/WEB-INF/jsp/web/contact.jsp", type = "dispatcher"),
        @Result(name = "test", location = "/WEB-INF/jsp/web/site1/test.jsp", type = "dispatcher"),
        @Result(name = "fail", location = "/WEB-INF/jsp/test/fail.jsp", type = "dispatcher")
})
@InterceptorRefs(value = {@InterceptorRef("defaultStack"), @InterceptorRef("bigdog")})
public class BaseAction  extends ActionSupport  implements ModelDriven<Wsbean> {
    protected Logger logger = Logger.getLogger(this.getClass());
    protected Wsbean wsbean = new Wsbean();

    public Wsbean getWsbean() {
        return wsbean;
    }

    public void setWsbean(Wsbean wsbean) {
        this.wsbean = wsbean;
    }

    @Override
    public Wsbean getModel() {
        return getWsbean();
    }
}
