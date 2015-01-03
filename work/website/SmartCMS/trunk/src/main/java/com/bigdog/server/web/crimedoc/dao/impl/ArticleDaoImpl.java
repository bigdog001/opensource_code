package com.bigdog.server.web.crimedoc.dao.impl;

import com.bigdog.server.web.crimedoc.dao.base.DaoSupport;
import com.bigdog.server.web.crimedoc.util.Tools;
import com.bigdog.server.web.crimedoc.bean.Article;
import com.bigdog.server.web.crimedoc.dao.ArticleDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 9/1/13
 * Time: 1:08 AM
 */
@Repository("articleDao")
@Transactional
public class ArticleDaoImpl  extends DaoSupport implements ArticleDao {
    Logger logger = Logger.getLogger(this.getClass());


    public void add(Article article) {
        entityManager.persist(article);
    }

    public void delete(int aid) {
        entityManager.remove(entityManager.getReference(Article.class, aid));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Article query(int aid) {
        return entityManager.find(Article.class, aid);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public List<Article> query(int start, int size) {
        Query query = entityManager.createQuery("from Article");
        List list = query.setMaxResults(size).
                setFirstResult(start).
                getResultList();
        return list;
    }

    public void update(Article article) {
        entityManager.merge(article);
    }

    @Override
    public Article getByTitle(String title) {
        Article article = null;
        Query query = entityManager.createNamedQuery("getArticleByTitle");
        query.setParameter(1, Tools.getMD5(title));
        List<Article> result = query.getResultList();
        if (result != null && result.size() > 0) {
            article = result.get(0);
        }
        return article;
    }

    @Override
    public int pages(int pageSize) {
        int totalPage = 0;
        Query query = (Query) entityManager.createQuery("select article.id from Article article");
        List<Article> result = query.getResultList();
        if (result != null && result.size() > 0) {
            int tmp = result.size() / pageSize;
            if (result.size() % pageSize == 0) {
                totalPage = tmp;
            } else {
                totalPage = tmp + 1;
            }
        }
        return totalPage;
    }

}
