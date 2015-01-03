package com.bigdog.server.web.crimedoc.cfg;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/1/13
 * Time: 10:16 AM
 */
public class FinderListener implements ServletContextListener {
    Logger logger = Logger.getLogger(this.getClass());

    private ApplicationContext springApplicationContext;

    private AppSystemInit appSystemInit;


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("car finder application starting,began to init all ......");
        initSystem(servletContextEvent);
        logger.info("system initialized successfull...");
    }

    private void initSystem(ServletContextEvent servletContextEvent){
        springApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContextEvent.getServletContext());
        appSystemInit = new AppSystemInit(springApplicationContext);
    }


    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {                
        logger.info("car finder application destroying ......");        
    }


}
