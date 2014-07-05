package com.ok.tags;

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
 * Date: 2011-6-11
 * Time: 23:55:05
 */
public class GetLeftNumber  extends TagSupport {
    private int user_id;
    //取出有多少人关注了我    我关注了多少人   互相关注的有几多人 这一系列的数据
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int doStartTag() throws JspException {
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest)pageContext.getRequest()).getSession().getServletContext());
        UserDao nd = (UserImpl) ctx.getBean("userdao");
//         List<ShortMessage> sms=nd.getAllShortMessage(page,size);
        int fans=nd.getFans(user_id); //有多少人关注了我
        int isee=nd.fanstotal(user_id); //我关注了多少人
        int hxn=nd.fanshx(user_id); //互相关注的有几多人
        int total=fans+isee; //总数

        pageContext.getRequest().setAttribute("total",total);
        pageContext.getRequest().setAttribute("hxn",hxn);
        pageContext.getRequest().setAttribute("fanss",fans);
        return SKIP_PAGE; //忽略对标签体的处理
    }
}
