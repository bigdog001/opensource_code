package com.bigdog.server.web.crimedoc.cfg.impl;

import com.bigdog.server.web.crimedoc.cfg.Initializer;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 10/15/13
 * Time: 9:52 AM
 */
public class Crasher  implements Initializer {
    Logger logger = Logger.getLogger(this.getClass());
    @Override
    public void init(ApplicationContext springApplicationContext) {
        
    }
}
