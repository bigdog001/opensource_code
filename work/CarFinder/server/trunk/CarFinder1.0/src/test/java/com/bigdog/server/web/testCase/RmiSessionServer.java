package com.bigdog.server.web.testCase;

import com.bigdog.server.web.rmi.SessionServerService;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/15/13
 * Time: 3:00 AM
 */
public class RmiSessionServer extends testBase {
    public RmiSessionServer(SessionServerService sessionServerService) {
        if (sessionServerService != null) {
            String sid = "f53699c4e22cf9f418d3fbf4036553b9";
            String result = sessionServerService.getSessionStatus(sid);
            logger.info("rmi result:" + result);
        }
    }
}
