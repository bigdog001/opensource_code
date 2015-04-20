/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigdog.server.web.lib.cfg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author jw362j
 */
public class EnvParameterInit implements Initializer {

    Logger logger = Logger.getLogger(this.getClass());

    @Override
    public void init(ApplicationContext springApplicationContext) {
        InputStream fis = null;

        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        logger.info("our app env properties is :" + path);
        EnvParametersHolder envParametersHolder = (EnvParametersHolder) springApplicationContext.getBean("envParametersHolder");
        logger.info("envParametersHolder is :" + envParametersHolder);
        if (!path.endsWith(File.separator)) {
            path = path + File.separator;
        }
        path = path + "env.properties";
        logger.info("init file is:" + path);
        Properties properties = new Properties();
        try {
            fis = new FileInputStream(new File(path));
            properties.load(fis); //从输入流中读取属性文件的内容
            envParametersHolder.setEnvValues(properties);
            fis.close();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(EnvParameterInit.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }

}
