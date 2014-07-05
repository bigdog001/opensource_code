package com.ok.dao;

import com.ok.bean.News;

import java.util.List;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-5-19
 * Time: 21:09:18
 */
public interface NewsDao {
    public News getNews(int newsid);

    public List<News> getAllnews(final int page,final int size);//取所有的news对象

    public void updateNews(News news);//更新news
}
