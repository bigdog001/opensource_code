package com.facemail.server.mobile.dao.impl;

import com.facemail.server.mobile.bean.TrackerBean;
import com.facemail.server.mobile.dao.TrackerDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/1/13
 * Time: 12:02 PM
 */
@Repository("trackerDao")
@Transactional
public class TrackerDaoImpl implements TrackerDao {
    Logger logger = Logger.getLogger(this.getClass());
    @PersistenceContext
    private javax.persistence.EntityManager entityManager;

    @Override
    public void save(TrackerBean trackerBean) {
        entityManager.persist(trackerBean);
    }

    @Override
    public void delete(String id) {
        entityManager.remove(entityManager.getReference(TrackerBean.class, id));
    }


    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public List<TrackerBean> getTrackerBean() {
        return entityManager.createQuery("select 0 from TrackerBean  o").getResultList();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public List<TrackerBean> getTrackerBeanByRang(int start, int size) {
        return null;
    }

    @Override
    public void upddate(TrackerBean trackerBean) {
        entityManager.merge(trackerBean);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public TrackerBean getByMac(String mac) {
        String query = "SELECT e FROM TrackerBean e WHERE e.mac = :mac";
        List<TrackerBean> trackerBeans = entityManager.createQuery(query, TrackerBean.class).setParameter("mac", mac).getResultList();
        if (trackerBeans == null || trackerBeans.size() == 0) {
            return null;
        }
        return trackerBeans.get(0);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public TrackerBean getByTid(String tid) {
        String query = "SELECT e FROM TrackerBean e WHERE e.id = :tid";
        List<TrackerBean> trackerBeans = entityManager.createQuery(query, TrackerBean.class).setParameter("tid", tid).getResultList();
        if (trackerBeans == null || trackerBeans.size() == 0) {
            return null;
        }
        return trackerBeans.get(0);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
