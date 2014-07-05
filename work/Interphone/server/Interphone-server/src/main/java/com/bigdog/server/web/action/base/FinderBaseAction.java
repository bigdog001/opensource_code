package com.bigdog.server.web.action.base;

import com.bigdog.server.web.bean.base.FinderBaseBean;
import com.bigdog.server.web.service.TrackerService;
import com.bigdog.server.web.util.Tools;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import javax.annotation.Resource;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 7/29/13
 * Time: 1:13 PM
 */
@ParentPackage("myPackage")
//@Controller
//@Results({@Result(name = "success", location = "/WEB-INF/jsp/test/index.jsp", type = "dispatcher"),@Result(name = "fail",location = "/WEB-INF/jsp/test/fail.jsp",type = "dispatcher")})
@InterceptorRefs(value = { @InterceptorRef("defaultStack"),@InterceptorRef("bigdog") })
public class FinderBaseAction extends ActionSupport  implements ModelDriven<FinderBaseBean> {
    protected Logger logger = Logger.getLogger(this.getClass());
    protected FinderBaseBean finderBaseBean = new FinderBaseBean();
    private InputStream inputStream;

    @Override
    public FinderBaseBean getModel() {
        return getFinderBaseBean();
    }

    @Resource
    protected TrackerService trackerService;


    public InputStream getInputStream() {
        if (inputStream == null) {
            return Tools.getInputStream();
        }
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }


    public FinderBaseBean getFinderBaseBean() {
        return finderBaseBean;
    }

    public void setFinderBaseBean(FinderBaseBean finderBaseBean) {
        this.finderBaseBean = finderBaseBean;
    }

    public TrackerService getTrackerService() {
        return trackerService;
    }

    public void setTrackerService(TrackerService trackerService) {
        this.trackerService = trackerService;
    }
}
