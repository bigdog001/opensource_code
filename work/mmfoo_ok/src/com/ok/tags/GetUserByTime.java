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
 * Date: 2011-6-3
 * Time: 0:37:46
 */
public class GetUserByTime   extends TagSupport {
     private int page;
    private int size;
    //取成参加报名活动的用户     首页的用户动态可以用这个数据 按进来的时间先后排序
    public void setPage(int page) {
        this.page = page;
    }

    public void setSize(int size) {
        this.size = size;
    }
     public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest)pageContext.getRequest()).getSession().getServletContext());
        UserDao nd = (UserImpl) ctx.getBean("userdao");
         int total_hd_record = nd.getAllUsers();
        int pagetotal = 0;
        if (total_hd_record == 0) {
            pagetotal = 0;
        } else {
            pagetotal = total_hd_record / size;
            if (total_hd_record % size == 0) {
            } else {
                pagetotal++;
            }
        }
        pageContext.getRequest().setAttribute("topuser",nd.getUserTop(page,size));
         pageContext.getRequest().setAttribute("pagetotal",pagetotal);
        return SKIP_PAGE; //忽略对标签体的处理
    }
}
