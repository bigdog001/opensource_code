package com.ok.tags;

import com.ok.bean.MmfooHd;
import com.ok.dao.UserDao;
import com.ok.daoimpl.UserImpl;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-5-23
 * Time: 21:29:27
 */
public class getTopHd extends TagSupport {
    private static Logger logger = Logger.getLogger(getTopHd.class);
    private int page;
    private int size;
//根据分页参数取出前N个美分活动对象
    public void setSize(int size) {
        this.size = size;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest) pageContext.getRequest()).getSession().getServletContext());
        UserDao nd = (UserImpl) ctx.getBean("userdao");
        int total_hd_record = nd.mmfoohd_total();
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
        pageContext.getRequest().setAttribute("tophd", nd.getMmfhds(page, size));
        pageContext.getRequest().setAttribute("pagetotal",pagetotal);
        return SKIP_PAGE; //忽略对标签体的处理
    }
}
