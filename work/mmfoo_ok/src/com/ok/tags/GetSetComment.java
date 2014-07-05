package com.ok.tags;

import com.ok.bean.Comment;
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
 * Date: 2011-6-23
 * Time: 0:17:31
 */
public class GetSetComment  extends TagSupport {
    private int setid;
    private int page;
    private int size;
//根据分页参数取某相册获得的评价对象
    public void setSetid(int setid) {
        this.setid = setid;
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
         List<Comment> sms=nd.getSetComment(setid,page,size);
        pageContext.getRequest().setAttribute("Comments",sms);
        return SKIP_PAGE; //忽略对标签体的处理
    }
    
}
