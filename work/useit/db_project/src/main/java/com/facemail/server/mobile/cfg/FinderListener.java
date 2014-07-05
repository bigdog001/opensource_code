package com.facemail.server.mobile.cfg;

import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.*;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/1/13
 * Time: 10:16 AM
 */
public class FinderListener implements ServletContextListener {
    Logger logger = Logger.getLogger(this.getClass());

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("car finder application starting ......");
        InputStream fis = null;
        Properties properties = new Properties();
        String path  = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        if (!path.endsWith(File.separator)) {
            path = path+File.separator;
        }
        path = path+"carfinder.properties";
        logger.info("init file is:"+path);

         try {
            fis = new FileInputStream(new File(path));
            properties.load(fis); //从输入流中读取属性文件的内容
            fis.close();
        }catch (FileNotFoundException e) {
            logger.info(e.getMessage());
        } catch (IOException e) {
            logger.info(e.getMessage());
        }

        Config.setAppParams(properties);
        logger.info("init parameter:"+Config.getAppParams().getProperty("emptyMethod"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        logger.info("car finder application destroying ......");
        Config.getAppParams().clear();
        Config.setAppParams(null);
    }
}
