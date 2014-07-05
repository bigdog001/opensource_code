package com.facemail.server.mobile.action.base;

import com.facemail.server.mobile.bean.base.FinderBaseBean;
import com.facemail.server.mobile.service.TrackerService;
import com.facemail.server.mobile.util.Tools;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 7/29/13
 * Time: 1:13 PM
 */
public class FinderBaseAction extends ActionSupport {
    protected Logger logger = Logger.getLogger(this.getClass());
    protected FinderBaseBean finderBaseBean = new FinderBaseBean();
    private InputStream inputStream;

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
