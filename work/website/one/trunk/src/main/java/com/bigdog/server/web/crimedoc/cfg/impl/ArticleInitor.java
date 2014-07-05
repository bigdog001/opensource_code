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

        if (articleDao.getByTitle("测试标题") == null) {
            logger.info("article 为空,开始初始化文章表的基础数据....");
            //开始初始化系统内所有基础文章数据
            initDefaultArticle();
            logger.info("文章表的基础数据始初始化ok....");
        }
    }

    private void initDefaultArticle() {
        for (int x = 0; x < 20; x++) {
            Article article = new Article();
            if (x == 0) {
                article.setTitle(Tools.getMD5("测试标题"));
                article.setDescription("基础数据描述" + x);
                article.setContent("基础数据内容" + x);
                article.setCatagory("from system init...");
                article.setCatagory_order(x);
                article.setCreateTime(new Date());
                article.setAuthor("system");
            } else {
                article.setTitle("基础数据" + x);
                article.setDescription("基础数据描述" + x);
                article.setContent("基础数据内容" + x);
                article.setCatagory("from system init...");
                article.setCatagory_order(x);
                article.setCreateTime(new Date());
                article.setAuthor("system");
            }
            articleDao.add(article);
        }
    }
}
