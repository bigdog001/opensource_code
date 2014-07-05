package com.facemail.server.mobile.rmi.impl;

import com.facemail.server.mobile.rmi.SessionServerService;
import com.facemail.server.mobile.rmi.sessionPool.SessionPoolService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/6/13
 * Time: 6:18 AM
 */
public class SessionServerServiceImpl implements SessionServerService {
    private Logger logger = Logger.getLogger(this.getClass());
    @Resource(name = "sessionPoolService")
    private SessionPoolService sessionPoolService;

    @Override
    public String getSessionStatus(String sid) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sid",sid);
        jsonObject.put("s_status",1);

        String result = jsonObject.toString();
        logger.info("rmi t:"+result);
        return result;
    }
}
