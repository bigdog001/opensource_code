package com.bigdog.server.web.cfg;

import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.*;
import java.util.Properties;
import org.springframework.web.context.support.WebApplicationContextUtils;

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
        SystemInitor.init(WebApplicationContextUtils.getRequiredWebApplicationContext(servletContextEvent.getServletContext()));
        logger.info("init parameters finished....");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        logger.info("car finder application destroying ......");
    }
}
