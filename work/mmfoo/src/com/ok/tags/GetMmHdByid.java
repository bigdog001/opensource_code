package com.ok.tags;

import com.ok.bean.MmfooHd;
import com.ok.dao.UserDao;
import com.ok.daoimpl.UserImpl;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-5-23
 * Time: 21:29:27
 */
public class GetMmHdByid  extends TagSupport {
     private static Logger logger = Logger.getLogger(GetMmHdByid.class);
    private int hdid;

    public void setHdid(int hdid) {
        this.hdid = hdid;
    }

    public int doStartTag() throws JspException {
        logger.error("要获取的活动的id是："+hdid);
        JspWriter out = pageContext.getOut();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest)pageContext.getRequest()).getSession().getServletContext());
        UserDao nd = (UserImpl) ctx.getBean("userdao");
        pageContext.getRequest().setAttribute("mmfoohd",nd.getMmffhds(hdid));
        return SKIP_PAGE; //忽略对标签体的处理
    }
}
