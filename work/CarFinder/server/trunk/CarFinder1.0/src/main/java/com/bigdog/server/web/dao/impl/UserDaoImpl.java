package com.bigdog.server.web.dao.impl;

import com.bigdog.server.web.bean.UserBean;
import com.bigdog.server.web.dao.UserDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/4/13
 * Time: 6:35 AM
 */

@Repository("userDao")
@Transactional
public class UserDaoImpl implements UserDao {

    Logger logger = Logger.getLogger(this.getClass());
    @PersistenceContext
    private javax.persistence.EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(UserBean userBean) {
        entityManager.persist(userBean);
    }

    @Override
    public void delete(int id) {
        entityManager.remove(entityManager.getReference(UserBean.class, id));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public UserBean getById(int id) {
        return entityManager.find(UserBean.class, id);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public UserBean login(UserBean userBean) {
        String query = "SELECT u FROM UserBean u WHERE u.username = :username and u.passwd = :passwd";
        List<UserBean> userBeans = entityManager.createQuery(query, UserBean.class).setParameter("username", userBean.getUsername())
                .setParameter("passwd", userBean.getPasswd()).getResultList();
        if (userBeans == null || userBeans.size() == 0) {
            return null;
        }
        return userBeans.get(0);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public List<UserBean> getUsers(int start, int size) {
        Query query = entityManager.createQuery("from UserBean");
        List list = query.setMaxResults(size).
                setFirstResult(start).
                getResultList();
        return list;
    }

    @Override
    public void update(UserBean userBean) {
        entityManager.merge(userBean);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public int getTotalPages(int pageSize) {
        return 0;
    }
}
