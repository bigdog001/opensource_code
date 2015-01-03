package com.bigdog.server.web.crimedoc.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 5/27/13
 * Time: 7:04 PM
 */
@Aspect
@Component
public class MyInterceptor {
    @Pointcut("execution (* com.bigdog.server.web.crimedoc.dao.impl.*(..))")
    private void anyMethod(){};

    @Before("anyMethod()&&args(userName)")
    public void doAccessCheck(String userName){
      System.out.println("bigdog---前置通知...");
    }

    @AfterReturning
    public void doReturnCheck(){

    }

}
