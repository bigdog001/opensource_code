package com.ok.tags;

import com.ok.bean.User;
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
 * Date: 2011-6-12
 * Time: 22:27:22
 */
public class getAllUsersTotal extends TagSupport {
    private int page;
    private int size;
//根据给定的分页参数取出系统中的注册用户对象列表
    public void setPage(int page) {
        this.page = page;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int doStartTag() throws JspException {
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest) pageContext.getRequest()).getSession().getServletContext());
        UserDao nd = (UserImpl) ctx.getBean("userdao");
        List<User> sms = nd.getAllUsersTotal(page, size);
        int allrecord = nd.getAllUser_records();
        //每页显示20个
        int pagetotal=0;
         pagetotal=allrecord/20;
        if(allrecord%20==0){

        }else{
          pagetotal++;  
        }
        pageContext.getRequest().setAttribute("UsersTotal", sms);
        pageContext.getRequest().setAttribute("pagetotal", pagetotal+"");
//           取出系统中所有的用户个数
        return SKIP_PAGE; //忽略对标签体的处理
    }
}
