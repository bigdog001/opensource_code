package com.bigdog.server.web.testCase;

import com.bigdog.server.web.tool.TestParameter;
import com.bigdog.server.web.tool.TestTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/15/13
 * Time: 2:57 AM
 */
public class Location extends testBase {
    public Location() {
        String mac = "ec:55:f9:c0:03:32";
        long latitude = 27813054;
        long lonitude = -80425241;
        String tid = "ATVXDH3Q";
        String method = "location";
        Random rd = new Random(100);
        List<TestParameter> testParameters = new ArrayList<TestParameter>();
        for (int x = 0; x < 1; x++) {
            TestParameter tp_mac = new TestParameter("mac", mac);
            TestParameter tp_latitude = new TestParameter("latitude", (latitude + rd.nextInt()) + "");
            TestParameter tp_lonitude = new TestParameter("lonitude", (lonitude + rd.nextInt()) + "");
            TestParameter tp_detacte_time = new TestParameter("detacte_time", System.currentTimeMillis() + "");
            TestParameter tp_tid = new TestParameter("tid", tid);
            TestParameter tp_method = new TestParameter("method", method);
            testParameters.add(tp_mac);
            testParameters.add(tp_latitude);
            testParameters.add(tp_lonitude);
            testParameters.add(tp_detacte_time);
            testParameters.add(tp_tid);
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
