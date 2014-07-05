package com.ok.service;

import com.ok.bean.News;
import com.ok.dao.NewsDao;

import java.util.List;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-6-14
 * Time: 21:07:02
 */
public class NewsService {
    private NewsDao    nd;

    public void setNd(NewsDao nd) {
        this.nd = nd;
    }



     public News getNews(int newsid){
        return nd.getNews(newsid); 
     }

    public List<News> getAllnews(final int page,final int size){
        return nd.getAllnews(page,size);  
    }

    public void updateNews(News news){
        nd.updateNews(news);
    }
    
}
