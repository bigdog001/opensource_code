/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bigdog.server.web.cfg;

import com.bigdog.server.web.cfg.init.EnvInitializer;
import com.bigdog.server.web.cfg.init.Initializer;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author bigdog
 */
public class SystemInitor {
    private static ApplicationContext springApplicationContext;
    private List<Initializer> lists;
    private static SystemInitor systemInitor;
    private SystemInitor(){
      lists = new ArrayList<Initializer>();
      lists.add(new EnvInitializer());
      callAll();
    }
    public static void init(ApplicationContext ApplicationContext){
        if(systemInitor == null){
         springApplicationContext = ApplicationContext;
         systemInitor = new SystemInitor();  
        }
    }
    private void callAll(){
        for(Initializer initializer: lists){
            if(initializer != null)initializer.init(springApplicationContext);
        }
    }
     
    
}
