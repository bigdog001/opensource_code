package com.bigdog.server.web.crimedoc.service.impl;

import com.bigdog.server.web.crimedoc.bean.Article;
import com.bigdog.server.web.crimedoc.dao.ArticleDao;
import com.bigdog.server.web.crimedoc.dao.cache.MyCache;
import com.bigdog.server.web.crimedoc.service.TitleTagService;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 9/2/13
 * Time: 7:17 AM
 */
@Service("titleTagService")
@Scope("singleton")
public class TitleTagServiceImpl implements TitleTagService {

    protected Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private ArticleDao articleDao;

    @Resource
    private MyCache myCache;

    @Override
    public Article getArticle(int id) {
        Article article = (Article) myCache.get(id);
        if (article == null) {
            article = articleDao.query(id);
            if (article!=null)myCache.updateItem(article);
            return article;
        }
        return article;
    }
}
