package com.bigdog.server.web.crimedoc.dao.cache;

import com.bigdog.server.web.crimedoc.bean.Article;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 9/2/13
 * Time: 9:32 AM
 */
public interface MyCache {
    public Object get(int id);
    public void delItem(int id);
    public void updateItem(Article article);
}
