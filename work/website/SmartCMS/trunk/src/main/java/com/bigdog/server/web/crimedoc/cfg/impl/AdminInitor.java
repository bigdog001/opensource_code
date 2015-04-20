package com.bigdog.server.web.crimedoc.cfg.impl;

import com.bigdog.server.web.lib.cfg.Initializer;
import com.bigdog.server.web.crimedoc.dao.AdminDao;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 10/7/13
 * Time: 1:01 PM
 */
public class AdminInitor implements Initializer {
    Logger logger = Logger.getLogger(this.getClass());
    private AdminDao adminDao;
    @Override
    public void init(ApplicationContext springApplicationContext) {
        logger.info("开始初始化管理员." );
        adminDao = (AdminDao) springApplicationContext.getBean("adminDao");
        logger.info(" adminDao is:" + adminDao);
        adminDao.detactAdmin();
        logger.info("管理员初始化ok." );
    }
}
