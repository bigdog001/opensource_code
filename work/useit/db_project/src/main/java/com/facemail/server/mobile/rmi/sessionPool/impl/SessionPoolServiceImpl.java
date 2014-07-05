package com.facemail.server.mobile.rmi.sessionPool.impl;

import com.facemail.server.mobile.rmi.sessionPool.SessionPoolService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/6/13
 * Time: 7:27 AM
 */
@Component("sessionPoolService")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SessionPoolServiceImpl implements SessionPoolService {

}
