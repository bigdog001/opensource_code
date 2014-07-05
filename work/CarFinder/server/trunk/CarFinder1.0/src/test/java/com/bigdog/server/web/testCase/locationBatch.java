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
 * Time: 2:58 AM
 */
public class locationBatch extends testBase{
    private static int cnt = 0;
    public locationBatch() {
        String mac = "ec:55:f9:c0:03:32";
        long latitude = 27813054;
        long lonitude = -80425241;
        String tid = "ATVXDH3Q";
        String method = "locationBatch";
        Random rd = new Random(100);
        List<TestParameter> testParameters = new ArrayList<TestParameter>();
//        int cnt= 0 ;
        //       mac        latitude lonitude   tid     detacte_time
        // ec:55:f9:c0:03:32#27813054#-80425241#2L3QR92I#13990095556772
        for (int x = 0; x < 10; x++) {
            StringBuffer batch_location = new StringBuffer();
            for (int i = 0; i < 2; i++) {
                batch_location.append(mac).append("#").append(latitude + rd.nextInt()).append("#").append(lonitude + rd.nextInt()).append("#").append(tid).append("#").append(System.currentTimeMillis()).append(",");
            }
            String batch_location_string = batch_location.toString();
            batch_location_string = batch_location_string.substring(0, batch_location_string.length() - 1);
            TestParameter tp_method = new TestParameter("method", method);
            TestParameter tp_tid = new TestParameter("tid", tid);
            TestParameter tp_batch_location = new TestParameter("batch_location", batch_location_string);
            testParameters.add(tp_batch_location);
            testParameters.add(tp_method);
            testParameters.add(tp_tid);
            try {
                new TestTool().postMethod(TestTool.TEST_URL, testParameters);
            } catch (Exception e) {
                logger.info("error:" + e.getMessage());
            }
            testParameters.clear();
            logger.info("upload cnt:" + cnt++);
        }
    }
}
