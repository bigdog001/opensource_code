package com.ok.tags;

import com.ok.bean.User;
import com.ok.dao.UserDao;
import com.ok.daoimpl.UserImpl;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-5-24
 * Time: 1:51:21
 */
public class getUserNew  extends TagSupport {
     private static Logger logger = Logger.getLogger(getUserNew.class);
    ;//取最新参加活动的美女
      public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest)pageContext.getRequest()).getSession().getServletContext());
       UserDao nd = (UserImpl) ctx.getBean("userdao");
          User user=nd.getUserNew();
        pageContext.getRequest().setAttribute("usernew",user);
//        pageContext.getRequest().setAttribute("user_id",user.getUser_id());
        return SKIP_PAGE; //忽略对标签体的处理
    }
}
