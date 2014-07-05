package com.ok.tags;

import com.ok.bean.User;
import com.ok.dao.UserDao;
import com.ok.daoimpl.UserImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-6-12
 * Time: 11:39:43
 */
public class GetFansDisplayByFansFlag extends TagSupport {
    private int fansflag;
    private int page;
    private int size;
    private int my_user_id;
//   取出某用户的粉丝对象，互相关注的用户对象 此功能的实现较为复杂
    public void setFansflag(int fansflag) {
        this.fansflag = fansflag;
    }

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
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest) pageContext.getRequest()).getSession().getServletContext());
        UserDao nd = (UserImpl) ctx.getBean("userdao");

        List<User> users = new ArrayList<User>();

        switch (fansflag) {
            case 1:
                users = addList(nd.whoSeeMe(my_user_id, page, size), nd.iSeewho(my_user_id, page, size));
                ;
                break;
            case 2:
              users=nd.getHxUserSee(my_user_id);
                ;
                break;
            case 3:
             users=nd.whoSeeMe(my_user_id, page, size);
                ;
                break;
            default:
                ;
        }
        pageContext.getRequest().setAttribute("usersbyfans", users);
        return SKIP_PAGE; //忽略对标签体的处理
    }

    public List<User> addList(List<User> u1, List<User> u2) {
        if (u1 == null || u1.size() == 0) {
            return u2;
        }
        if (u2 == null || u2.size() == 0) {
            return u1;
        }
        for (User u : u1) {
            u2.add(u);
        }
        return u2;

    }

}
