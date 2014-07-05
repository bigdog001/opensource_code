package com.ok.tags;

import com.ok.bean.ImgSet;
import com.ok.dao.UserDao;
import com.ok.daoimpl.UserImpl;
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
 * Date: 2011-5-25
 * Time: 0:09:21
 */
public class GetImgSetByid  extends TagSupport {
    private int user_id;
    private int page;
    private int size;

    public void setPage(int page) {
        this.page = page;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest)pageContext.getRequest()).getSession().getServletContext());
       UserDao nd = (UserImpl) ctx.getBean("userdao");
        List<ImgSet> sets=nd.getImgSetsByid(user_id,page,size) ;
        pageContext.getRequest().setAttribute("userimgset",sets);
        return SKIP_PAGE; //忽略对标签体的处理
    }
}
