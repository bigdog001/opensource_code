package com.ok.tags;

import com.ok.dao.UserDao;
import com.ok.daoimpl.UserImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-6-12
 * Time: 22:54:04
 */
public class getMmffhds_user  extends TagSupport {
    private int user_id;
     //取某用户参加的所有活动个数
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
     public int doStartTag() throws JspException {
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest)pageContext.getRequest()).getSession().getServletContext());
        UserDao nd = (UserImpl) ctx.getBean("userdao");
         int mmfoohd_total=  nd.getMmffhds_user(user_id);
          pageContext.getRequest().setAttribute("mmfoohd_total",mmfoohd_total);
        return SKIP_PAGE; //忽略对标签体的处理
    }
}
