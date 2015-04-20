package com.bigdog.server.web.lib.cfg;

import org.springframework.context.ApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 10/7/13
 * Time: 12:59 PM
 */
public interface Initializer {
    public void init(ApplicationContext springApplicationContext) ;
}
