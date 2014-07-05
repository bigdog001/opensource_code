package com.bigdog.server.web.action.base;

import com.bigdog.server.web.bean.base.TrackerBaseBean;
import com.bigdog.server.web.util.Tools;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.ParentPackage;
import java.io.InputStream;


/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/1/13
 * Time: 8:15 AM
 */
@ParentPackage("myPackage")
//@Results({@Result(name = "success", location = "/WEB-INF/jsp/test/index.jsp", type = "dispatcher"),@Result(name = "fail",location = "/WEB-INF/jsp/test/fail.jsp",type = "dispatcher")})
//@InterceptorRefs(value = { @InterceptorRef("fileUploadStack") })
public class TrackerBaseAction extends ActionSupport implements ModelDriven<TrackerBaseBean> {
    private InputStream inputStream;
    protected Logger logger = Logger.getLogger(this.getClass());


    protected TrackerBaseBean trackerBaseBean = new TrackerBaseBean();


    public TrackerBaseBean getModel() {
        return getTrackerBaseBean();
    }
    public TrackerBaseBean getTrackerBaseBean() {
        return trackerBaseBean;
    }

    public void setTrackerBaseBean(TrackerBaseBean trackerBaseBean) {
        this.trackerBaseBean = trackerBaseBean;
    }

    public InputStream getInputStream() {
        if (inputStream == null){
            return Tools.getInputStream();
        }
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
