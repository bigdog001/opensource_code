/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycomm.dao.mydao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    public static void setup() {
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    }
//    @Test

    public void userInit() throws Exception {
        try {
            IUserDao userDao = (IUserDao) applicationContext.getBean("userDaoImpl");
            if (userDao != null) {
                userDao.init();
            }
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

//     @Test
    public void quesryData() {
        try {
            IUserDao userDao = (IUserDao) applicationContext.getBean("userDaoImpl");
            if (userDao != null) {
                ResultHelp<User> user_result = userDao.getScrollData(1, 5);
                System.out.println("---------> " + user_result);
                Assert.assertEquals(5, user_result.getResultlist().size());
            }
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void tttt() {
        File f = new File("C:\\Users\\jw362j\\work_jiunian.wang\\code\\att\\attsvn");
       showAllFiles(f);
    }
    
    private void showAllFiles(File dir){
         File[] fs = dir.listFiles();
        for (int i = 0; i < fs.length; i++) {
//            System.out.println(fs[i].getAbsolutePath());
            if (fs[i].isDirectory()) {
                try {
                    showAllFiles(fs[i]);
                } catch (Exception e) {
                }
            }else{
               String contents =  txt2String(fs[i]);
                for(int j=0;j<contents.length();j++){
                    String ddd= contents.charAt(j)+"";
                    if(test(ddd)){
                        System.out.println(ddd+","+j+",file:"+fs[i].getAbsolutePath());
                    }                    
                }
                
            }
        }
    }

    private boolean test(String str) {
        boolean temp = false;
        if ("".equals(str) || str == null) {
            return temp;
        }
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            temp = true;
        }
        return temp;
    }
    
    public static String txt2String(File file){
          String result = "";
          try{
              BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
              String s = null;
              while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                  result = result + "\n" +s;
              }
              br.close();    
          }catch(Exception e){
              e.printStackTrace();
          }
          return result;
      }
}
