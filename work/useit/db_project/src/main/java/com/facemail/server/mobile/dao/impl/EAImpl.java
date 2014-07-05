package com.facemail.server.mobile.dao.impl;

import com.facemail.server.mobile.bean.ea.Addressww;
import com.facemail.server.mobile.bean.ea.Customer;
import com.facemail.server.mobile.dao.EA;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 9/4/13
 * Time: 11:04 AM
 */
@Repository("eadaos")
@Transactional
public class EAImpl implements EA{
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
    public void save(Customer customer) {
        entityManager.persist(customer);
    }
}
