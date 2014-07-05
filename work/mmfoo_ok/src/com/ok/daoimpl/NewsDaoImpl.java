package com.ok.daoimpl;

import com.ok.bean.News;
import com.ok.dao.NewsDao;
import com.ok.dao.OkBase;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-5-19
 * Time: 21:10:11
 */
public class NewsDaoImpl extends OkBase implements NewsDao {
    public News getNews(int newsid) {
        final News news = new News();
        jdbctemplate.query("select * from news where newsid=?", new Object[]{newsid}, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                news.setNewsid(resultSet.getInt("newsid"));
                news.setTitle(resultSet.getString("title"));
                news.setContent(resultSet.getString("content"));
                news.setCreateTime(resultSet.getTimestamp("createTime"));
            }
        });
        return news;
    }

    @Override
    public List<News> getAllnews(int pages,final int size) {

        final int start = pages <= 0 ? 0 : (pages - 1) * size;
        final String hql = "from News ";
        List<News> user_tmp = hibernateTemplate.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                query.setFirstResult(start);
                query.setMaxResults(size);
                List list = query.list();
                return list;
            }
        });
        return user_tmp;
    }

    @Override
    public void updateNews(News news) {
     hibernateTemplate.update(news);    
    }
}
