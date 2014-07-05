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
 * Time: 22:37:58
 */
public class GetImdeImg   extends TagSupport {
   private int imgid;
//取出首页焦点图区域中某一张焦点图对象
    public void setImgid(int imgid) {
        this.imgid = imgid;
    }

    public int doStartTag() throws JspException {
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest) pageContext.getRequest()).getSession().getServletContext());
        AdminDao ad = (AdminDaoImpl) ctx.getBean("admindao");
         IndexImg  ingindexss=ad.getImdeImg(imgid);
        pageContext.getRequest().setAttribute("ingindexss", ingindexss);
        return SKIP_PAGE; //忽略对标签体的处理
    }
}
