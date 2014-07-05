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
 * Date: 2011-6-8
 * Time: 17:25:23
 */
public class AddImgSetViewCnt  extends TagSupport {
      private int setid;
// 此标签将用户相册访问次数字段加一
    public void setSetid(int setid) {
        this.setid = setid;
    }
     public int doStartTag() throws JspException {
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest)pageContext.getRequest()).getSession().getServletContext());
        UserDao nd = (UserImpl) ctx.getBean("userdao");
           nd.AddImgSetViewCnt(setid);
        return SKIP_PAGE; //忽略对标签体的处理
    }
}
