package com.bigdog.server.web.crimedoc.dao.impl;

import com.bigdog.server.web.lib.dao.base.DaoSupport;
import com.bigdog.server.web.crimedoc.util.Tools;
import com.bigdog.server.web.crimedoc.bean.Admin;
import com.bigdog.server.web.crimedoc.dao.AdminDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 10/6/13
 * Time: 9:00 AM
 */

@Repository("adminDao")
@Transactional
public class adminDaoImpl  extends DaoSupport implements AdminDao {

    Logger logger = Logger.getLogger(this.getClass());


    @Override
    public void add(Admin admin) {
        entityManager.persist(admin);
    }

    @Override
    public void del(int id) {
        entityManager.remove(entityManager.getReference(Admin.class, id));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public Admin get(int id) {
        return entityManager.find(Admin.class, id);
    }

    @Override
    public void update(Admin admin) {
        entityManager.merge(admin);
    }

    @Override
    public void detactAdmin() {
        Query query = entityManager.createNamedQuery("detectAdmin");
        query.setParameter(1, "admin");
        List<Admin> result = query.getResultList();
        if (result != null && result.size() > 0) {
        } else {
            logger.info("admin不存在,初始化admin用户.");
            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setPasswd(Tools.getMD5("admin"));
            add(admin);
            logger.info("用户admin初始化ok.");
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public Admin adminLogin(String username, String passwd) {
        Admin admin = null;
        passwd = Tools.getMD5(passwd);
        Query query = entityManager.createNamedQuery("getadmin");
        query.setParameter(1, username);
        query.setParameter(2, passwd);
        List<Admin> result = query.getResultList();
        if (result != null && result.size() > 0) {
            admin = result.get(0);
        }
        return admin;
    }
}
