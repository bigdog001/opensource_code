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
 * Date: 2011-6-23
 * Time: 0:48:32
 */
public class GetCommentTotal  extends TagSupport {
     private int setid;
//取出对于某相册的评论对象
    public void setSetid(int setid) {
        this.setid = setid;
    }
       public int doStartTag() throws JspException {
      ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest) pageContext.getRequest()).getSession().getServletContext());
        UserDao nd = (UserImpl) ctx.getBean("userdao");
        pageContext.getRequest().setAttribute("CommentTotal", nd.CommentTotal(setid));
        return SKIP_PAGE; //忽略对标签体的处理
    }
}
