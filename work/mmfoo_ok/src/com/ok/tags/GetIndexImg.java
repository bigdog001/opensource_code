package com.ok.tags;

import com.ok.bean.IndexImg;
import com.ok.dao.AdminDao;
import com.ok.daoimpl.AdminDaoImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-6-13
 * Time: 20:59:25
 */
public class GetIndexImg extends TagSupport {
    private int page;
    private int size;
//根据分页参数取出首页需要显示的n张轮播图
    public void setSize(int size) {
        this.size = size;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int doStartTag() throws JspException {
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest) pageContext.getRequest()).getSession().getServletContext());
        AdminDao ad = (AdminDaoImpl) ctx.getBean("admindao");
        List<IndexImg> ingindex=ad.getIndexImg(page, size);
        pageContext.getRequest().setAttribute("ingindex", ingindex);
        return SKIP_PAGE; //忽略对标签体的处理
    }
}
