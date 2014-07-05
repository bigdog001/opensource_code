package com.sender.send;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 10/18/12
 * Time: 7:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class Launcher {
    private static Logger logger = Logger.getLogger(Launcher.class);

    public static void main(String[] args) {
        logger.info("系统开始启动");
        ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
        logger.info("系统启动完毕");
    }
}
