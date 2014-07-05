package com.facemail.server.mobile.action.mum;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 5/29/13
 * Time: 5:40 AM
 * To change this template use File | Settings | File Templates.
 */
@Transactional
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public void delete(int user_id) {
        entityManager.remove(entityManager.getReference(User.class,user_id));
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
    public User getByid(int id) {
        return entityManager.find(User.class,id);
    }

    @Override
    public User getByPwd(String username, String pwd) {
        TypedQuery query = entityManager.createQuery("select o from User o where o.username=? and  o.password=?", User.class);
        query.setParameter(1,username);
        query.setParameter(2,pwd);
        List<User> users = query.getResultList();
        if (users!=null||users.size()>0) {
            return users.get(0);
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
    public List<User> getUsers() {
        return entityManager.createQuery("select o from User o").getResultList();
    }

    @Override
    public void upddate(User user) {
        entityManager.merge(user);
    }
}
