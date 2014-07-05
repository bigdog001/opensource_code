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
public class GetMyPrivateMessage_reDouble extends TagSupport {
    private int user_id_to;
    private int user_id_from;
    private int page;
    private int size;
    private int pmid;
    //根据分页参数取我收到的某人发给我的私信 我所有的回复 即我（user_id_to）对某人（user_id_from）的某封私信（pmid）的所有回复数据对象
    public void setPage(int page) {
        this.page = page;
    }

    public void setPmid(int pmid) {
        this.pmid = pmid;
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
        pageContext.getRequest().setAttribute("repmdouble",nd.GetMyPrivateMessage_reDouble(pmid,user_id_to,user_id_from,page,size));
        return SKIP_PAGE; //忽略对标签体的处理
    }
}
