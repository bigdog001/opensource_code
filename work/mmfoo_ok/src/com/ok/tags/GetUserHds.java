package com.ok.tags;

import com.ok.bean.MmfooHd;
import com.ok.bean.UserHd;
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
 * Date: 2011-6-22
 * Time: 18:13:39
 */
public class GetUserHds extends TagSupport {
    private int page;
    private int size;
    private int user_id;
  //根据分页参数取某用户参与的前n个mmfoo活动对象
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
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest) pageContext.getRequest()).getSession().getServletContext());
        UserDao nd = (UserImpl) ctx.getBean("userdao");
          List<MmfooHd> userhds=nd.getMmffhdsbyuserid(user_id,page,size);
        pageContext.getRequest().setAttribute("userhds", userhds);
//        pageContext.getRequest().setAttribute("user_id",user.getUser_id());
        return SKIP_PAGE; //忽略对标签体的处理
    }
}
