package com.facemail.server.mobile.dao.impl;

import com.facemail.server.mobile.bean.TAddress;
import com.facemail.server.mobile.bean.TrackerBean;
import com.facemail.server.mobile.dao.TAddressDao;
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
 * Date: 8/2/13
 * Time: 7:28 AM
 */
@Repository("taddressDao")
@Transactional
public class TAddressDaoImpl implements TAddressDao {
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
    public void save(TAddress tAddress, String tid) {
        TrackerBean trackerBean = entityManager.getReference(TrackerBean.class, tid);
        tAddress.setTrackerBean(trackerBean);
        entityManager.persist(tAddress);
    }

    @Override
    public void saveBatch(List<TAddress> tAddresses,String tid) {
        if (tAddresses!=null) {
            for (TAddress ta:tAddresses) {
                TrackerBean trackerBean = entityManager.getReference(TrackerBean.class, tid);
                ta.setTrackerBean(trackerBean);
                entityManager.persist(ta);
            }
        }
    }

    @Override
    public void delete(int id) {
        entityManager.remove(entityManager.getReference(TAddress.class, id));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
    @Override
    public TAddress getById(int id) {
        return entityManager.find(TAddress.class, id);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
    @Override
    public List<TAddress> getByRange(int start, int size) {
        Query query = entityManager.createQuery("from TAddress");
        List list = query.setMaxResults(size).
                setFirstResult(start).
                getResultList();
        return list;
    }
}
