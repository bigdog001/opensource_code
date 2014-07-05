package com.bigdog.server.web.crimedoc.aop;

import com.bigdog.server.web.crimedoc.cfg.WebConfig;
import com.bigdog.server.web.crimedoc.action.admin.base.adminBase;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 9/16/13
 * Time: 1:33 AM
 */
@Component("adminInterceptor")
public class AdminInterceptor extends MethodFilterInterceptor {
    Logger logger = Logger.getLogger(this.getClass());
    adminBase baseAction;

    @Override
    protected String doIntercept(ActionInvocation actionInvocation) throws Exception {
        baseAction = (adminBase) actionInvocation.getAction();
        long start = System.currentTimeMillis();
        logger.info("adminInterceptor startup ........." + start + "," + this);
        String result = "";
        if (baseAction.isLogin()) {
            baseAction.getArticleService().setAdminBase(baseAction);
            result = actionInvocation.invoke();
        } else {
            result = WebConfig.admin_login_page_name;
        }

        logger.info("adminInterceptor finish .........result:" + result + ",time:" + (System.currentTimeMillis() - start));
        return result;
    }
}
