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
 * Time: 21:19:15
 */
public class GetAllFooterNews extends TagSupport {
    private int page;
    private int size;
//按照给定的记录起点和长度取出页脚的新闻记录 startpoint=  (page-1)*size,endpoint=  startpoint+ size,其实就是分页显示的机制
    public void setPage(int page) {
        this.page = page;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest) pageContext.getRequest()).getSession().getServletContext());
        NewsDao nd = (NewsDaoImpl) ctx.getBean("newsdao");
        pageContext.getRequest().setAttribute("allnews", nd.getAllnews(page,size));
        return SKIP_PAGE; //忽略对标签体的处理
    }
}
