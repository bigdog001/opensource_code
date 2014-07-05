/*
 * Copyright 2006 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bigdog.server.web;


import com.bigdog.server.web.rmi.SessionServerService;
import com.bigdog.server.web.testCase.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.MalformedURLException;

public class IndexActionTest {

    private static SessionServerService sessionServerService;

    @BeforeClass
    public static void set() throws Exception {
        try {
            ApplicationContext applicationContext = new ClassPathXmlApplicationContext("test/test.xml");
            sessionServerService = (SessionServerService) applicationContext.getBean("sessionServerServiceProx");
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void trackid() {
        new Trackid();
    }

    @Test
    public void location() {
        new Location();
    }


    @Test
    public void locationBatch() {
        new locationBatch();

    }


    @Test
    public void userRegiste() {
        new userRegiste();
    }

    @Test
    public void userLogin() {
        new userLogin();
    }

    @Test
    public void RmiSessionServer() {
        new RmiSessionServer(sessionServerService);
    }

    @Test
    public void wsdlTest() throws MalformedURLException, Exception {
        new wsdlTest();
    }

}
