package com.bigdog.server.web.crimedoc.action.admin;

import com.bigdog.server.web.crimedoc.cfg.WebConfig;
import com.bigdog.server.web.crimedoc.action.admin.base.adminBase;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 9/16/13
 * Time: 12:01 AM
 */
public class IndexAdminAction extends adminBase {
    @Action("/admin")
    public String admin() {  //负责真正的登录操作 此方法不受拦截器的约制
        if (isLogin()) {
            return WebConfig.admin_console_page_name;
        } else {
            getAdminService().setWSbean(getWsbean());
            boolean login_result = getAdminService().adminLogin();
            if (login_result) {
                getSession().put(WebConfig.admin_login_status, WebConfig.admin_login_ok);
                return WebConfig.admin_console_page_name;
            } else {
                return WebConfig.admin_login_page_name;
            }
        }

    }

    @Action("/adminLogin")
    public String adminLogin() { //负责返回登录页面或者后台控制台
        if (isLogin()) {
            return WebConfig.admin_console_page_name;
        } else {
            return WebConfig.admin_login_page_name;
        }
    }


    @Action("/adminLogOut")
    public String adminLogOut() {
        getSession().remove(WebConfig.admin_login_status);
        return WebConfig.admin_login_page_name;

    }

    @Action(value = "/ShowTab", results = {
            @Result(name = "tab1", location = "/WEB-INF/jsp/web/admin/include/tab1.jsp"),
            @Result(name = "tab2", location = "/WEB-INF/jsp/web/admin/include/tab2.jsp"),
            @Result(name = "tab3", location = "/WEB-INF/jsp/web/admin/include/tab3.jsp"),
            @Result(name = "tab4", location = "/WEB-INF/jsp/web/admin/include/tab4.jsp"),
            @Result(name = "tab5", location = "/WEB-INF/jsp/web/admin/include/tab5.jsp")
    })
    public String tab() {
        String result = "";
        switch (getWsbean().getTab()) {
            case 1:
                result = "tab1";
                break;
            case 2:
                result = "tab2";
                //计算出总页数
                getWsbean().setArticleCounter(getArticleService().pageCounte());
                break;
            case 3:
                result = "tab3";
                break;
            case 4:
                result = "tab4";
                break;
            case 5:
                result = "tab5";
                break;
            default:
                break;
        }
        return result;
    }


}
