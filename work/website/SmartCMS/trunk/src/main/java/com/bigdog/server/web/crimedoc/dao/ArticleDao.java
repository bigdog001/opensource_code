package com.bigdog.server.web.crimedoc.dao;

import com.bigdog.server.web.crimedoc.bean.Article;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 9/1/13
 * Time: 1:08 AM
 */
public interface ArticleDao {
    public void add(Article article);
    public void delete(int aid);
    public Article query(int aid);
    public List<Article> query(int start,int size);
    public void update(Article article);
    public Article getByTitle(String title);//文章表中放置一张特殊的标题文章，以此判断系统中的基础文章是否被初始化
    public int pages(int pageSize);
}
