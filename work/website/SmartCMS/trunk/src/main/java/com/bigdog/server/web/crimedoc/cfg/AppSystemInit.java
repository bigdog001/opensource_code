package com.bigdog.server.web.crimedoc.cfg;

import com.bigdog.server.web.crimedoc.cfg.impl.AdminInitor;
import com.bigdog.server.web.crimedoc.cfg.impl.ArticleInitor;
import com.bigdog.server.web.crimedoc.cfg.impl.ParameterInitor;
import com.bigdog.server.web.lib.cfg.EnvParameterInit;
import com.bigdog.server.web.lib.cfg.Initializer;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

/**
 * Created with IntelliJ IDEA. User: bigdog Date: 10/6/13 Time: 1:14 PM
 */
public class AppSystemInit {

    Logger logger = Logger.getLogger(this.getClass());
    private List<Initializer> initializerList = new ArrayList<Initializer>();

    public AppSystemInit() {
    }

    public AppSystemInit(ApplicationContext springApplicationContext) {
        initializerList.add(new AdminInitor());//初始化管理员
        initializerList.add(new ParameterInitor());//初始化系统参数
        initializerList.add(new ArticleInitor());//初始化网站基础新闻文章数据
        initializerList.add(new EnvParameterInit());
        initAll(springApplicationContext);
    }

    private void initAll(ApplicationContext springApplicationContext) {
        if (initializerList != null) {
            for (Initializer iz : initializerList) {                
                    iz.init(springApplicationContext);                
            }
            
        }
    }

    public void DestroyAll() {
        initializerList.clear();
    }
}
