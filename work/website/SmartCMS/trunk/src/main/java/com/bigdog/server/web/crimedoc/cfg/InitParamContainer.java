/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bigdog.server.web.crimedoc.cfg;

import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author bigdog
 */
public class InitParamContainer {
    protected Logger logger = Logger.getLogger(this.getClass());
    private Map<String,String> parameters ;

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        logger.info("all of the init parameters:"+parameters);
        this.parameters = parameters;
    }
    
    
}
