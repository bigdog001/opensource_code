package com.ok.tags;

import com.ok.bean.User;
import com.ok.bean.UserHd;
import com.ok.dao.UserDao;
import com.ok.daoimpl.UserImpl;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-5-24
 * Time: 22:33:31
 */
public class GetUserById  extends TagSupport {
     private static Logger logger = Logger.getLogger(GetUserById.class);
    private int user_id;

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int doStartTag() throws JspException {

        JspWriter out = pageContext.getOut();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest)pageContext.getRequest()).getSession().getServletContext());
        UserDao nd = (UserImpl) ctx.getBean("userdao");
        User user= nd.getUser(user_id);
         pageContext.getRequest().setAttribute("userbyid",user); 
        return SKIP_PAGE; //忽略对标签体的处理
    }
}
