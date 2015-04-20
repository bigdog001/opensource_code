/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bigdog.server.web.lib.cfg;

import java.util.Properties;
import org.springframework.stereotype.Component;

/**
 *
 * @author jw362j
 */
@Component(value="envParametersHolder")
public class EnvParametersHolder {
    private Properties envValues;

    public Properties getEnvValues() {
        return envValues;
    }

    public void setEnvValues(Properties envValues) {
        this.envValues = envValues;
    }
    
    
}
