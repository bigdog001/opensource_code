package com.ok.tags;

import com.ok.bean.ImgSet;
import com.ok.bean.User;
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
 * Date: 2011-5-29
 * Time: 23:54:50
 */
public class GetUserAccessMyspace  extends TagSupport {
    private int my_user_id;
    private int page;
    private int size;
// 根据分页参数取出访问了我的空间的用户对象
    public void setMy_user_id(int my_user_id) {
        this.my_user_id = my_user_id;
    }

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
        List<User> users=nd.getUserAccessMyspace(my_user_id,page,size) ;
        pageContext.getRequest().setAttribute("WhoVisitMe",users);
        return SKIP_PAGE; //忽略对标签体的处理
    }
}
