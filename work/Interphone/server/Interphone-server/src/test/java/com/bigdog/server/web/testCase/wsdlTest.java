package com.bigdog.server.web.testCase;


import com.bigdog.server.web.tool.TestTool;
import org.codehaus.xfire.client.Client;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/16/13
 * Time: 6:29 AM
 */
public class wsdlTest  extends testBase {
    public wsdlTest()  throws MalformedURLException, Exception {
        Client client = new Client(new URL(TestTool.TEST_URL_WEBSERVICE));
        Object[] results = client.invoke("getBook", new Object[] {});
        logger.info("come from web service--->"+results[0]);
    }
}
