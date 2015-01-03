package com.bigdog.server.web.crimedoc.dao.cache.impl;

import com.bigdog.server.web.crimedoc.bean.Article;
import com.bigdog.server.web.crimedoc.dao.cache.MyCache;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 9/2/13
 * Time: 9:34 AM
 */
@Component("myCache")
@Scope("singleton")
public class CacheUtil implements MyCache {
    private static Map<Integer, Object> itemCache = new HashMap<Integer, Object>();
    private Object obj = new Object();

    public Map<Integer, Object> getItemCache() {
        return itemCache;
    }

    public void setItemCache(Map<Integer, Object> itemCache) {
        this.itemCache = itemCache;
    }

    @Override
    public Object get(int id) {
        return itemCache.get(id);
    }

    @Override
    public void delItem(int id) {
        itemCache.remove(id);
    }

    @Override
    public void updateItem(Article article) {
        synchronized(obj){
            Article article_ = (Article) itemCache.get(article.getId());
            if (article_ == null) {
                itemCache.put(article.getId(), article);
            } else {
                itemCache.remove(article.getId());
                itemCache.put(article.getId(), article);
            }
        }

    }
}
