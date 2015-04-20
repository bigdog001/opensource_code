/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycomm.web.action;

import com.mycomm.dao.mydao.User;
import com.mycomm.web.base.BaseAction;
import com.mycomm.web.bean.JiuNian;
import com.mycomm.web.bean.StringConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author jw362j
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserAction extends BaseAction<JiuNian> {

    private static final Logger log = LoggerFactory.getLogger(UserAction.class);

//  @org.apache.struts2.convention.annotation.Action(value = "/index", results = {@Result(name = "add_success", type = "stream", params = {"contentType", "text/html", "inputName", "inputStream", "contentCharSet", "UTF-8"})})
    @org.apache.struts2.convention.annotation.Action(value = "/index")
    public String index() {
        log.info("the request has arrived to index.jsp ...");
        User jn = new User();
        jn.setU_name("address");
        jn.setU_pwd("jiunian");        
        userDao.save( jn );
        return  StringConstant.ActionResult_NAME.JSP_SITE_INDEX_DIS;
    }

}
