package com.ok.tags;

import com.ok.dao.UserDao;
import com.ok.daoimpl.UserImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-6-3
 * Time: 0:37:46
 */
public class GetUserByCnt   extends TagSupport {
     private int page;
    private int size;
////按投票数取参与者 此功能现已经被废弃
    public void setPage(int page) {
        this.page = page;
    }

    public void setSize(int size) {
        this.size = size;
    }
     public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest)pageContext.getRequest()).getSession().getServletContext());
        UserDao nd = (UserImpl) ctx.getBean("userdao");
        pageContext.getRequest().setAttribute("topuser",nd.getUsercnt(page,size));
        return SKIP_PAGE; //忽略对标签体的处理
    }
}
