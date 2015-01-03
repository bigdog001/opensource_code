package com.bigdog.server.web.crimedoc.action.admin.base;

import com.bigdog.server.web.crimedoc.cfg.WebConfig;
import com.bigdog.server.web.crimedoc.bean.Wsbean;
import com.bigdog.server.web.crimedoc.service.AdminService;
import com.bigdog.server.web.crimedoc.service.ArticleService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.*;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 9/16/13
 * Time: 12:03 AM
 */
@ParentPackage("myPackage")
@Results({@Result(name = "success", location = "/WEB-INF/jsp/web/index.jsp", type = "dispatcher"),
        @Result(name = WebConfig.admin_login_page_name, location = WebConfig.admin_login_page_location, type = "dispatcher"),
        @Result(name = WebConfig.admin_console_page_name, location = WebConfig.admin_console_page_location, type = "dispatcher"),
        @Result(name = "input", location = "/WEB-INF/jsp/test/fail.jsp", type = "dispatcher")})
@InterceptorRefs(value = {@InterceptorRef("defaultStack"), @InterceptorRef("admin")})
public abstract class adminBase extends ActionSupport implements SessionAware, ModelDriven<Wsbean> ,ServletRequestAware {
    protected Logger logger = Logger.getLogger(this.getClass());
    protected Wsbean wsbean = new Wsbean();
    protected Map session;
    protected HttpServletRequest request;
    private InputStream inputStream;
    @Resource
    private ArticleService articleService;

    @Resource
    private AdminService adminService;

    public AdminService getAdminService() {
        return adminService;
    }

    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    public ArticleService getArticleService() {
        return articleService;
    }

    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Wsbean getWsbean() {
        return wsbean;
    }

    public void setWsbean(Wsbean wsbean) {
        this.wsbean = wsbean;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public Wsbean getModel() {
        return getWsbean();
    }

    @Override
    public void setSession(Map<String, Object> stringObjectMap) {
        this.session = stringObjectMap;
    }

    @Override
    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.request = httpServletRequest;
    }

    public Map getSession() {
        return session;
    }

    public boolean isLogin() {
        boolean result = false;
        Map session = getSession();
        String loginstatus = "";
        if (session.get(WebConfig.admin_login_status) != null) {
            loginstatus = (String) session.get(WebConfig.admin_login_status);
            loginstatus = loginstatus.trim();
        }
        if (WebConfig.admin_login_ok.equals(loginstatus)) {
            result = true;
        }
        logger.info("login status is :"+result);
        return result;
    }


}
