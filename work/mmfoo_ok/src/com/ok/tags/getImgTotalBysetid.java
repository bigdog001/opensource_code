package com.ok.tags;

import com.ok.bean.ShortMessage;
import com.ok.dao.UserDao;
import com.ok.daoimpl.UserImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-6-4
 * Time: 12:15:30
 */
public class getImgTotalBysetid extends TagSupport {
    private int setid;
//取出此相册下共有多少张照片
    public void setSetid(int setid) {
        this.setid = setid;
    }
     public int doStartTag() throws JspException {
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest)pageContext.getRequest()).getSession().getServletContext());
        UserDao nd = (UserImpl) ctx.getBean("userdao");
         int sms=nd.getImgTotalBysetid(setid);
        pageContext.getRequest().setAttribute("picintheset",sms);
        return SKIP_PAGE; //忽略对标签体的处理
    }
}
