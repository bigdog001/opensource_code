/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigdog.server.cishan.exception;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.apache.log4j.Logger;

/**
 *
 * @author bigdog
 */
public class CishanHandlerExceptionResolver extends
        SimpleMappingExceptionResolver {

    protected Logger logger = Logger.getLogger(this.getClass());

    protected ModelAndView doResolveException(
            javax.servlet.http.HttpServletRequest httpServletRequest,
            javax.servlet.http.HttpServletResponse httpServletResponse,
            java.lang.Object o, java.lang.Exception e) {
        httpServletRequest.setAttribute("ex", e);
        logger.info("cishan app exception happen:" + e.getMessage());
        return super.doResolveException(httpServletRequest,
                httpServletResponse, o, e);
    }
}
