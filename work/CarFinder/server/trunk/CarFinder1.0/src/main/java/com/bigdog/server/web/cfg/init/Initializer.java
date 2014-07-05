/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bigdog.server.web.cfg.init;

import org.springframework.context.ApplicationContext;

/**
 *
 * @author bigdog
 */
public interface Initializer {
    public void init(ApplicationContext springApplicationContext);
}
