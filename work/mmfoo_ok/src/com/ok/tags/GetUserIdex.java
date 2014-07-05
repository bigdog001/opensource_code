package com.ok.tags;

import com.ok.bean.User;
import com.ok.dao.UserDao;
import com.ok.daoimpl.UserImpl;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-5-22
 * Time: 23:12:42
 */
public class GetUserIdex  extends TagSupport {
    private static Logger logger = Logger.getLogger(GetUserIdex.class);
    //取本月最美美女
      public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest)pageContext.getRequest()).getSession().getServletContext());
       UserDao nd = (UserImpl) ctx.getBean("userdao");
          User user=nd.getUserIndex();
        pageContext.getRequest().setAttribute("userindex",user); 
//        pageContext.getRequest().setAttribute("user_id",user.getUser_id());
        return SKIP_PAGE; //忽略对标签体的处理
    }
}
