/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycomm.dao.mydao;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author jw362j
 */


public class UserDaoTest {
    private static ApplicationContext applicationContext = null;
    
//    @BeforeClass
    public static void setup(){
        applicationContext= new ClassPathXmlApplicationContext("applicationContext.xml");
    }
//    @Test
    public void userInit() throws Exception {
        try {            
           IUserDao userDao = (IUserDao)applicationContext.getBean("userDaoImpl");
           if(userDao!=null){
               userDao.init();
           }
        } catch (BeansException e) {
            e.printStackTrace();
        }       
    }
    
//     @Test
    public void quesryData() {
         try {            
           IUserDao userDao = (IUserDao)applicationContext.getBean("userDaoImpl");
           if(userDao!=null){
             ResultHelp<User> user_result = userDao.getScrollData(1, 5);
             System.out.println("---------> "+user_result);
             Assert.assertEquals(5,user_result.getResultlist().size());
           }
        } catch (BeansException e) {
            e.printStackTrace();
        }  
    }
}
