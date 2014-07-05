package com.bigdog.server.web.crimedoc.aop.base;

import com.bigdog.server.web.crimedoc.action.base.BaseAction;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.springframework.stereotype.Component;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/3/13
 * Time: 5:26 AM
 */
@Component("bigdogInterceptor")
public class BigdogInterceptor extends AbstractInterceptor{
    Logger logger = Logger.getLogger(this.getClass());

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        BaseAction baseAction = (BaseAction) actionInvocation.getAction();
        long start = System.currentTimeMillis();
        logger.info("interceptro startup ........."+start+","+this);
        String result = actionInvocation.invoke();
        logger.info("interceptro finish .........result:"+result+",time:"+(System.currentTimeMillis()-start));
        return result;
    }
}
