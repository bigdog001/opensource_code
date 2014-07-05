package com.ok.tags;

import com.ok.bean.MmfooHd;
import com.ok.bean.User;
import com.ok.bean.UserHd;
import com.ok.dao.UserDao;
import com.ok.daoimpl.UserImpl;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-5-23
 * Time: 0:19:52
 */
public class GetHdName extends TagSupport {
    private static Logger logger = Logger.getLogger(GetHdName.class);
    private int user_id;

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
//根据用户的id取出此用户参加的第一个活动名称并且显示在页面上
    public int doStartTag() throws JspException {
        logger.error("要获取活动名称的用户的id是：" + user_id);

        JspWriter out = pageContext.getOut();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest) pageContext.getRequest()).getSession().getServletContext());
        UserDao nd = (UserImpl) ctx.getBean("userdao");
        UserHd uhd = nd.getUserHd(user_id);
        MmfooHd mmfoohd = null;
        if (uhd != null) {
            mmfoohd = nd.getMmffhds(uhd.getHdid());
        }
        try {
            if (mmfoohd == null) {
                out.print("未参加活动");
            } else {
                out.print("<a href='/mfhd-" + mmfoohd.getHdid() + ".html" + "'>" + mmfoohd.getHdname() + "</a>");
            }
        } catch (IOException e) {
            logger.error(e.getStackTrace());
        }
        return SKIP_PAGE; //忽略对标签体的处理
    }
}
