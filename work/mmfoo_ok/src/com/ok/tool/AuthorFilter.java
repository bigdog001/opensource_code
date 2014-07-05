package com.ok.tool;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by WangJiuNian.
 * User: Administrator
 * Date: 2011-3-28
 * Time: 12:27:41
 */
public class AuthorFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        ServletContext application = session.getServletContext();
         String is_login= (String) session.getAttribute("isadminlogin");
            if(is_login==null||(!"admin_ok".equals(is_login))){
                response.sendRedirect("/admin.html");
//                request.getRequestDispatcher("/admin.html").forward(request,response);
//              response.sendRedirect("/login_admin.jsp");
            return;
             } else {
            filterChain.doFilter(request, response);
        }
    }

    public void destroy() {
    }
}
