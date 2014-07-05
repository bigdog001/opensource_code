/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bigdog.server.web.cfg.init;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author bigdog
 */
public class EnvInitializer implements Initializer{
    Logger logger = Logger.getLogger(this.getClass());

    @Override
    public void init(ApplicationContext springApplicationContext) {
        logger.info("EnvInitializer startup.......");
    }
    
}