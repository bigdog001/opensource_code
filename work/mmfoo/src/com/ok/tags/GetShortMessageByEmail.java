package com.ok.tags;

import com.ok.bean.ShortMessage;
import com.ok.dao.UserDao;
import com.ok.daoimpl.UserImpl;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-5-29
 * Time: 15:45:21
 */
public class GetShortMessageByEmail extends TagSupport {
    private static Logger logger = Logger.getLogger(GetShortMessageByEmail.class);
    private int  user_id;
    private int page;
    private int size;

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setSize(int size) {
        this.size = size;
    }
     public int doStartTag() throws JspException {
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest)pageContext.getRequest()).getSession().getServletContext());
        UserDao nd = (UserImpl) ctx.getBean("userdao");
         List<ShortMessage> sms=nd.getShortMessageByEmail(user_id,page,size);
        pageContext.getRequest().setAttribute("getshortmessagebyemail",sms);
        return SKIP_PAGE; //忽略对标签体的处理
    }
}
