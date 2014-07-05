package com.sender.util;

import com.sender.taskbean.configBean;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 10/21/12
 * Time: 6:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class updateStartCount {
    private static Logger logger=Logger.getLogger(updateStartCount.class);
    public static void update(){
        Properties properties = new Properties();
        try {
            InputStream fis = new FileInputStream(configBean.dbinfPath);
            properties.load(fis);
            fis.close();// 关闭流
            properties.setProperty("mail_countstart", (Integer.valueOf(properties.getProperty("mail_countstart"))+configBean.sendLength)+"");
            FileOutputStream fos = new FileOutputStream(configBean.dbinfPath);
            // 将Properties集合保存到流中
            properties.store(fos, "Copyright (c) Boxcode Studio");
            fos.close();// 关闭流
        } catch (FileNotFoundException e) {
            logger.info("update startcount config failed:  " + e.getMessage());
        } catch (IOException e) {
            logger.info("update startcount config failed:  " + e.getMessage());
        }
    }
}
