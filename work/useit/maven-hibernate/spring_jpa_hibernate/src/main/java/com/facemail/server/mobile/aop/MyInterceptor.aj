package com.facemail.server.mobile.aop;

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
 * To change this template use File | Settings | File Templates.
 */
@Aspect
@Component
public class MyInterceptor {
    @Pointcut("execution (* com.facemail.server.mobile.serviceImpl.PersonService_jpaIMpl.*(..))")
    private void anyMethod(){};

    @Before("anyMethod()&&args(userName)")
    public void doAccessCheck(String userName){
      System.out.println("bigdog---前置通知...");
    }

    @AfterReturning
    public void doReturnCheck(){

    }

}
