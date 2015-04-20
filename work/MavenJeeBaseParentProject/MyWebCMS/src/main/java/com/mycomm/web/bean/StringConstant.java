/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycomm.web.bean;
import com.opensymphony.xwork2.Action;

/**
 *
 * @author jw362j
 */
public class StringConstant {

    public static class JSP_LOCATION {
        public static final String SITE_INDEX_JSP = "/WEB-INF/jsp/index.jsp";
        public static final String MyWebCms_INDEX_JSP = "/WEB-INF/jsp/mywebcms/index.jsp";        
    }
     public static class ActionResult_NAME {
        public static final String JSP_SITE_INDEX_DIS = Action.SUCCESS;
        public static final String JSP_MyWebCms_INDEX_DIS = "index";
     }

}
