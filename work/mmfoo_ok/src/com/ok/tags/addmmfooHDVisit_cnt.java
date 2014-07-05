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
 * Time: 16:04:50
 */
public class addmmfooHDVisit_cnt  extends TagSupport {
    private int hdid;
//此标签给美分网发布的活动记录的访问次数字段加一
    public void setHdid(int hdid) {
        this.hdid = hdid;
    }
    public int doStartTag() throws JspException {
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest)pageContext.getRequest()).getSession().getServletContext());
        UserDao nd = (UserImpl) ctx.getBean("userdao");
           nd.addVisit_cnt(hdid);
        return SKIP_PAGE; //忽略对标签体的处理
    }
}
