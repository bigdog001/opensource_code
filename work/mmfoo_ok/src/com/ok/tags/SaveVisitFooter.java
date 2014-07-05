package com.ok.tags;

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
 * Date: 2011-5-29
 * Time: 15:30:10
 */
public class SaveVisitFooter extends TagSupport {
     private static Logger logger = Logger.getLogger(SaveVisitFooter.class);
     private int my_user_id;
     private int friends_user_id;
//将访问过我空间的用户进行记录
    public void setMy_user_id(int my_user_id) {
        this.my_user_id = my_user_id;
    }

    public void setFriends_user_id(int friends_user_id) {
        this.friends_user_id = friends_user_id;
    }

    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest)pageContext.getRequest()).getSession().getServletContext());
        UserDao nd = (UserImpl) ctx.getBean("userdao");
        nd.who_visit_me(my_user_id,friends_user_id);
        return SKIP_PAGE; //忽略对标签体的处理
    }
}
