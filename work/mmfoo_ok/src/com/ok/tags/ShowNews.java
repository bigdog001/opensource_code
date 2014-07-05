package com.ok.tags;

//import javax.servlet.http.HttpServletRequest;
import com.ok.dao.NewsDao;
import com.ok.daoimpl.NewsDaoImpl;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Created by WangJiuNian.
 * User: Administrator
 * Date: 2011-3-24
 * Time: 19:37:50
 */
public class ShowNews extends TagSupport {
    private int newsid;
    private static Logger logger = Logger.getLogger(ShowNews.class);
//显示某新闻的内容
    public int getNewsid() {
        return newsid;
    }

    public void setNewsid(int newsid) {
        this.newsid = newsid;
    }

    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest)pageContext.getRequest()).getSession().getServletContext());
        NewsDao nd = (NewsDaoImpl) ctx.getBean("newsdao");
        try {
           // ((HttpServletResponse)pageContext.getResponse()).sendRedirect("/");
           out.print(nd.getNews(newsid).getContent());
        } catch (IOException e) {
            logger.error(e.getStackTrace());
            throw new JspException(e);
        }
        return SKIP_PAGE; //忽略对标签体的处理
    }
}
