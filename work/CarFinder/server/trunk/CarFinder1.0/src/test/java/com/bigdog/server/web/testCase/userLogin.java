package com.bigdog.server.web.testCase;

import com.bigdog.server.web.tool.TestParameter;
import com.bigdog.server.web.tool.TestTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/15/13
 * Time: 3:00 AM
 */
public class userLogin extends testBase {
    public userLogin() {
        String method = "userLogin";
        String username = "294356967@qq.com";
        String passwd = "ddskkl";
        List<TestParameter> testParameters = new ArrayList<TestParameter>();
        for (int x = 0; x < 1; x++) {
            TestParameter tp_method = new TestParameter("method", method);
            TestParameter tp_username = new TestParameter("userName", username);
            TestParameter tp_passwd = new TestParameter("passwd", passwd);
            testParameters.add(tp_method);
            testParameters.add(tp_username);
            testParameters.add(tp_passwd);
            try {
                new TestTool().postMethod(TestTool.TEST_URL, testParameters);
            } catch (Exception e) {
                logger.info("error:" + e.getMessage());
            }
            testParameters.clear();
        }

    }
}
