package com.ok.tool;

import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ArrayList;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-5-18
 * Time: 21:35:48
 */
public class PictrueListener implements ServletContextListener {
     private  static Logger logger = Logger.getLogger(PictrueListener.class);

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.error("seesion create ,put  pictrues to it!!");
         servletContextEvent.getServletContext().setAttribute("pictrues", new ArrayList<String>()); 
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
         logger.error("seesion distroy ,remove pictrues in it!!");
        servletContextEvent.getServletContext().removeAttribute("pictrues");
    }
}
