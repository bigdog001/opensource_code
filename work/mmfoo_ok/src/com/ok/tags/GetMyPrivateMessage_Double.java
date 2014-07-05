package com.ok.tags;

import com.ok.dao.UserDao;
import com.ok.daoimpl.UserImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-6-11
 * Time: 20:12:31
 */
public class GetMyPrivateMessage_Double   extends TagSupport {
    private int user_id_to;
    private int user_id_from;
    private int page;
    private int size;
      //根据分页参数取和对方来往的所有私信
    public void setPage(int page) {
        this.page = page;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setUser_id_to(int user_id_to) {
        this.user_id_to = user_id_to;
    }

    public void setUser_id_from(int user_id_from) {
        this.user_id_from = user_id_from;
    }
     public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest)pageContext.getRequest()).getSession().getServletContext());
        UserDao nd = (UserImpl) ctx.getBean("userdao");
        pageContext.getRequest().setAttribute("pmdouble",nd.getPrivateMessages(user_id_to,user_id_from,page,size));
        return SKIP_PAGE; //忽略对标签体的处理
    }
}
