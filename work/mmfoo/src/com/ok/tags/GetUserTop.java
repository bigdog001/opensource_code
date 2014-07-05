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
import java.util.List;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-5-24
 * Time: 21:48:48
 */
public class GetUserTop   extends TagSupport {
    private static Logger logger = Logger.getLogger(GetUserTop.class);
    private int page;
    private int size;

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
         List<User> user=nd.getUserTop(page,size);
        pageContext.getRequest().setAttribute("usetop",user);
        return SKIP_PAGE; //忽略对标签体的处理
    }
}
