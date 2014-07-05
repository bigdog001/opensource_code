package com.ok.dao;

import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;

import javax.sql.DataSource;

/**
 * Created by WangJiuNian.
 * User: Administrator
 * Date: 2011-3-18
 * Time: 14:18:52
 */
public class OkBase {
    public HibernateTemplate hibernateTemplate;
    public JdbcTemplate jdbctemplate;
      public void setSessionfactory(SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    public void setDataSource(DataSource ds) {
        jdbctemplate = new JdbcTemplate(ds);
    }
}
