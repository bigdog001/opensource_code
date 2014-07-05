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
 * Time: 16:42:04
 */
public class GetMmfoohd_user_total  extends TagSupport {
      private int hdid;
     //有多少人参加了此活动
    public void setHdid(int hdid) {
        this.hdid = hdid;
    }
     public int doStartTag() throws JspException {
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest)pageContext.getRequest()).getSession().getServletContext());
        UserDao nd = (UserImpl) ctx.getBean("userdao");
         int mmfoohd_user_total=  nd.mmfoohd_user_total(hdid);
          pageContext.getRequest().setAttribute("mmfoohd_user_total",mmfoohd_user_total);
        return SKIP_PAGE; //忽略对标签体的处理
    }
}
