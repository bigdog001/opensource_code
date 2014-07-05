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
 * Date: 2011-5-23
 * Time: 23:46:16
 */
public class getHdUser  extends TagSupport {
    private static Logger logger = Logger.getLogger(getHdUser.class);
    private int hdid;
    private int page;
    private int size;

    public void setHdid(int hdid) {
        this.hdid = hdid;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int doStartTag() throws JspException {
        logger.error("取参与活动："+hdid+" 的第 "+page+" 页用户 ，每页"+size+"个");
        JspWriter out = pageContext.getOut();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest)pageContext.getRequest()).getSession().getServletContext());
        UserDao nd = (UserImpl) ctx.getBean("userdao");
        pageContext.getRequest().setAttribute("topuser",nd.getUserhd(hdid,page,size));
        return SKIP_PAGE; //忽略对标签体的处理
    }
}
