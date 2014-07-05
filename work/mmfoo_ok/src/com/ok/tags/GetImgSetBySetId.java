package com.ok.tags;

import com.ok.bean.ImgSet;
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
 * Time: 17:14:58
 */
public class GetImgSetBySetId   extends TagSupport {
    private int setid;
//根据相册id取此相册对象
    public void setSetid(int setid) {
        this.setid = setid;
    }
     public int doStartTag() throws JspException {
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest)pageContext.getRequest()).getSession().getServletContext());
        UserDao nd = (UserImpl) ctx.getBean("userdao");
         ImgSet sms=nd.getImgSetById(setid);
        pageContext.getRequest().setAttribute("imgsetsingle",sms);
        return SKIP_PAGE; //忽略对标签体的处理
    }
}
