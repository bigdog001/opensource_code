package com.ok.daoimpl;

import com.ok.bean.News;
import com.ok.dao.NewsDao;
import com.ok.dao.OkBase;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

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
}
