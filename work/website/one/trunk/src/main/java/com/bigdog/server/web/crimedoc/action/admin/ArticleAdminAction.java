package com.bigdog.server.web.crimedoc.action.admin;

import com.bigdog.server.web.crimedoc.action.admin.base.adminBase;
import com.bigdog.server.web.crimedoc.bean.Article;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 9/18/13
 * Time: 5:26 AM
 */
public class ArticleAdminAction extends adminBase {
    @Action(value = "/articleadd", results = {@Result(name = "add_success", type = "stream", params = {"contentType", "text/html", "inputName", "inputStream", "contentCharSet", "UTF-8"})})
    public String add() {
        getArticleService().add();
        return "add_success";
    }

    @Action(value = "/openarticle", results = {
            @Result(name = "article", location = "/WEB-INF/jsp/web/admin/article.jsp")
    })
    public String openArticle() {
        if (getWsbean().getId() != 0) {
            Article article = getArticleService().get();
            getWsbean().setId(article.getId());
            getWsbean().setTitle(article.getTitle());
            getWsbean().setImgname(article.getImgname());
            getWsbean().setCatagory_order(article.getCatagory_order());
            getWsbean().setDescription(article.getDescription());
            getWsbean().setContent(article.getContent());
        }
        return "article";
    }

    @Action(value = "/articleAdd",
            results = {@Result(name = "add_success", type = "stream",
                    params = {"contentType", "text/html", "inputName", "inputStream", "contentCharSet", "UTF-8"})})
    public String articleAdd() {
        getArticleService().add();
        return "add_success";
    }

    @Action(value = "/articleUpdate",
            results = {@Result(name = "update_success", type = "stream",
                    params = {"contentType", "text/html", "inputName", "inputStream", "contentCharSet", "UTF-8"})})
    public String articleUpdate() {
        getArticleService().update();
        return "update_success";
    }

    @Action(value = "/articleLoad",
            results = {@Result(name = "load_success", type = "stream",
                    params = {"contentType", "text/html", "inputName", "inputStream", "contentCharSet", "UTF-8"})})
    public String articleLoad() {
        getArticleService().gets();
        return "load_success";
    }

    @Action(value = "/articleDel",
            results = {@Result(name = "del_success", type = "stream",
                    params = {"contentType", "text/html", "inputName", "inputStream", "contentCharSet", "UTF-8"})})
    public String articleDel() {
        getArticleService().del();
        return "del_success";
    }
}
