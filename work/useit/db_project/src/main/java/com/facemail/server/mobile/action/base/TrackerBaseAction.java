package com.facemail.server.mobile.action.base;

import com.facemail.server.mobile.bean.base.TrackerBaseBean;
import com.facemail.server.mobile.util.Tools;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/1/13
 * Time: 8:15 AM
 */
public class TrackerBaseAction extends ActionSupport{
    private InputStream inputStream;
    protected Logger logger = Logger.getLogger(this.getClass());


    protected TrackerBaseBean trackerBaseBean = new TrackerBaseBean();

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
