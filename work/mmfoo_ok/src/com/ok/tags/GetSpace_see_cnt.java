package com.ok.tags;

import com.ok.dao.UserDao;
import com.ok.daoimpl.UserImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-6-4
 * Time: 10:23:34
 */
public class GetSpace_see_cnt  extends TagSupport {
     private int user_id;
//取某用户的空间被访问次数
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
      public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest)pageContext.getRequest()).getSession().getServletContext());
       UserDao nd = (UserImpl) ctx.getBean("userdao");
        pageContext.getRequest().setAttribute("space_see_cnt",nd.space_see_cnt(user_id));
        return SKIP_PAGE; //忽略对标签体的处理
    }
}
