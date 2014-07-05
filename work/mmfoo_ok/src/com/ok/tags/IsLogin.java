package com.ok.tags;

//import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Created by WangJiuNian.
 * User: Administrator
 * Date: 2011-3-24
 * Time: 19:37:50
 */
public class IsLogin extends TagSupport {
    private static Logger logger = Logger.getLogger(ShowNews.class);
//判断用户是否登录


    public int doStartTag() throws JspException {
         return EVAL_PAGE; //忽略对标签体的处理
    }
    public int doEndTag() throws javax.servlet.jsp.JspException {
         JspWriter out = pageContext.getOut();
         String islogin= (String) ((HttpServletRequest) pageContext.getRequest()).getSession().getAttribute("islogin");
        if("".equals(islogin)||islogin==null){
            try {
                out.println("<script>alert('您尚未登录！');window.location='/'</script>");
                return SKIP_PAGE;
            } catch (IOException e) {
                 logger.error(e.getStackTrace());
            }
    }if("Y".equals(islogin)){
          return EVAL_PAGE;
        }
        return SKIP_PAGE;  
    }

}
