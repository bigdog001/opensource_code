package com.ok.tags;

import com.ok.bean.UserImg;
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
 * Time: 12:26:50
 */
public class getSetFace extends TagSupport {
    private int setid;
//取某相册的封面对象 其实就是一张照片对象
    public void setSetid(int setid) {
        this.setid = setid;
    }
     public int doStartTag() throws JspException {
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest)pageContext.getRequest()).getSession().getServletContext());
        UserDao nd = (UserImpl) ctx.getBean("userdao");
         UserImg sms=nd.getSetFace(setid);
        pageContext.getRequest().setAttribute("setface",sms);
        return SKIP_PAGE; //忽略对标签体的处理
    }
    
}
