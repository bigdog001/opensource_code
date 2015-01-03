package com.bigdog.server.web.crimedoc.service;

import com.bigdog.server.web.crimedoc.action.admin.base.adminBase;
import com.bigdog.server.web.crimedoc.bean.Article;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 9/18/13
 * Time: 5:10 AM
 */
public interface ArticleService {
    public void setAdminBase(adminBase base);
    public void add();
    public void del();
    public Article get();
    public List<Article> gets();
    public void update();
    public String getFileContent();
    public void saveFile();
    public void rcvFile();
    public int pageCounte();
}
