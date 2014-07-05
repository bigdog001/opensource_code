package com.bigdog.server.web.testCase;

import com.bigdog.server.web.tool.TestParameter;
import com.bigdog.server.web.tool.TestTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/15/13
 * Time: 2:55 AM
 */
public class Trackid extends testBase {
    public Trackid() {
        String mac = "ec:55:f9:c0:03:32";
        String method = "trackid";
        List<TestParameter> testParameters = new ArrayList<TestParameter>();
        for (int x = 0; x < 1; x++) {
            TestParameter tp_mac = new TestParameter("mac", mac);
            TestParameter tp_method = new TestParameter("method", method);
            testParameters.add(tp_mac);
            testParameters.add(tp_method);
            try {
                new TestTool().postMethod(TestTool.TEST_URL, testParameters);
            } catch (Exception e) {
                logger.info("error:" + e.getMessage());
            }
            testParameters.clear();
        }
    }
}
