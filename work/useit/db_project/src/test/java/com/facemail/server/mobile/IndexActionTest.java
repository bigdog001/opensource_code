/*
 * Copyright 2006 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.facemail.server.mobile;


import com.facemail.server.mobile.bean.ea.Addressww;
import com.facemail.server.mobile.bean.ea.Customer;
import com.facemail.server.mobile.dao.EA;
import com.facemail.server.mobile.dao.impl.EAImpl;
import com.facemail.server.mobile.rmi.SessionServerService;
import com.facemail.server.mobile.dao.TrackerDao;
import com.facemail.server.mobile.tool.TestParameter;
import com.facemail.server.mobile.tool.TestTool;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class IndexActionTest {
    Logger logger = Logger.getLogger(this.getClass());


    private static EA ea;

    @BeforeClass
    public static void set() throws Exception {
        try {
            ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
             ea= (EA) applicationContext.getBean("eadaos");
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testea() {
        Customer customer = new Customer();
        Addressww address = new Addressww();
        address.setCustomer(customer);
        customer.setAddress(address);
           ea.save(customer);
    }

}
