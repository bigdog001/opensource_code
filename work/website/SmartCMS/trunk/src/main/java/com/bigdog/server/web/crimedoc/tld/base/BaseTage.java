package com.bigdog.server.web.crimedoc.tld.base;

import com.bigdog.server.web.crimedoc.service.TitleTagService;
import com.bigdog.server.web.crimedoc.service.impl.TitleTagServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 9/2/13
 * Time: 7:11 AM
 */
public class BaseTage extends SimpleTagSupport {
    protected Logger logger = Logger.getLogger(this.getClass());
    private int id;//需要调取的数据id
    private int showit;//页面显示控制变量
    private int showSize;//控制变量的显示字数
    protected TitleTagService titleTagService;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShowit() {
        return showit;
    }

    public void setShowit(int showit) {
        this.showit = showit;
    }

    public int getShowSize() {
        return showSize;
    }

    public void setShowSize(int showSize) {
        this.showSize = showSize;
    }

    private void initService() {
//        logger.info("注入titleTagService");
        PageContext pageContext = (PageContext) this.getJspContext();
        ServletContext servletContext = pageContext.getServletContext();
        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        titleTagService = (TitleTagServiceImpl) wac.getBean("titleTagService");
//        logger.info("titleTagService is:"+titleTagService);
    }

    protected TitleTagService getTitleTagService() {
        if (titleTagService == null) {
            initService();
            return titleTagService;
        } else {
            return titleTagService;
        }
    }

}
