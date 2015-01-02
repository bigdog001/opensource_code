package com.bigdog.server.web.crimedoc.cfg.impl;

import com.bigdog.server.web.crimedoc.util.Tools;
import com.bigdog.server.web.crimedoc.bean.Article;
import com.bigdog.server.web.crimedoc.cfg.Initializer;
import com.bigdog.server.web.crimedoc.dao.ArticleDao;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 10/8/13
 * Time: 5:32 AM
 */
public class ArticleInitor implements Initializer {
    Logger logger = Logger.getLogger(this.getClass());
    private ArticleDao articleDao;

    @Override
    public void init(ApplicationContext springApplicationContext) {
        articleDao = (ArticleDao) springApplicationContext.getBean("articleDao");

        if (articleDao.getByTitle("test title") == null) {
            logger.info("article is null,start to load data into database");
            //开始初始化系统内所有基础文章数据
            initDefaultArticle();
            logger.info("article init is ready");
        }
    }

    private void initDefaultArticle() {
        for (int x = 0; x < 20; x++) {
            Article article = new Article();
            if (x == 0) {
                article.setTitle(Tools.getMD5("test title"));
                article.setDescription("basic data desc" + x);
                article.setContent("basic data content" + x);
                article.setCatagory("from system init...");
                article.setCatagory_order(x);
                article.setCreateTime(new Date());
                article.setAuthor("system");
            } else {
                article.setTitle("basic data" + x);
                article.setDescription("basic data desc" + x);
                article.setContent("basic data content" + x);
                article.setCatagory("from system init...");
                article.setCatagory_order(x);
                article.setCreateTime(new Date());
                article.setAuthor("system");
            }
            articleDao.add(article);
        }
    }
}
