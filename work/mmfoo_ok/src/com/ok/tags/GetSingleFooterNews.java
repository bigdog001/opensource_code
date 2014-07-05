package com.ok.tags;

import com.ok.dao.NewsDao;
import com.ok.daoimpl.NewsDaoImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-6-14
 * Time: 21:19:35
 */
public class GetSingleFooterNews extends TagSupport {
    private int newid;
//取某条页脚新闻对象
    public void setNewid(int newid) {
        this.newid = newid;
    }

    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest) pageContext.getRequest()).getSession().getServletContext());
        NewsDao nd = (NewsDaoImpl) ctx.getBean("newsdao");
        pageContext.getRequest().setAttribute("newssingle", nd.getNews(newid));
        return SKIP_PAGE; //忽略对标签体的处理
    }
}
