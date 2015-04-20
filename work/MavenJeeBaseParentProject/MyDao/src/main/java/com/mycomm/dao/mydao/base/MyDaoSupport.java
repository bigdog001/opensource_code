/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycomm.dao.mydao.base;

import com.mycomm.dao.mydao.MycommBean;
import com.mycomm.dao.mydao.ResultHelp;
import java.io.Serializable;
import java.beans.PropertyDescriptor;
import java.lang.reflect.ParameterizedType;
import javax.persistence.EntityManager;
import javax.persistence.Entity;

import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.lang.reflect.Method;
import javax.persistence.EmbeddedId;
import java.beans.Introspector;
import java.util.LinkedHashMap;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jw362j
 */
@SuppressWarnings("unchecked")
@Transactional
public abstract class MyDaoSupport<T > implements DAO<T> {
   private static final Logger log = LoggerFactory.getLogger(MyDaoSupport.class);
    private Class<T> entityClass;
    @PersistenceContext
    private EntityManager entityManager;

    public MyDaoSupport() {
        // 使用反射技术得到T的真实类型
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass(); // 获取当前new的对象的 泛型的父类 类型
        this.entityClass = (Class<T>) pt.getActualTypeArguments()[0]; // 获取第一个类型参数的真实类型
        System.out.println("clazz ---> " + entityClass);
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    } 

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public long getCount() {
        String jpq_getcount = "select count(" + getCountField(this.entityClass) + ") from " + getEntityName(this.entityClass) + " o";
        return (Long) entityManager.createQuery(jpq_getcount).getSingleResult();
    }

    public void clear() {
        entityManager.clear();
    }

    public void save(T entity) {
        System.out.println("my entity class :"+entity.getClass()); 
        entityManager.persist(entity);
    }

    public void update(T entity) {
        entityManager.merge(entity);
    }

    public void delete(Serializable... entityids) {
        for (Object id : entityids) {
            entityManager.remove(entityManager.getReference(this.entityClass, id));
        }
    }

    public T find(Serializable entityId) {
        if (entityId == null) {
            throw new RuntimeException(this.entityClass.getName() + ":传入的实体id不能为空");
        }
        return entityManager.find(this.entityClass, entityId);
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public ResultHelp<T> getScrollData(int firstindex, int maxresult, String wherejpql, Object[] queryParams, LinkedHashMap<String, String> orderby) {
        ResultHelp qr = new ResultHelp<T>();
        String entityname = getEntityName(this.entityClass);
        Query query = entityManager.createQuery("select o from " + entityname + " o " + (wherejpql == null || "".equals(wherejpql.trim()) ? "" : "where " + wherejpql) + buildOrderby(orderby));
        setQueryParams(query, queryParams);
        if (firstindex != -1 && maxresult != -1) {
            query.setFirstResult(firstindex).setMaxResults(maxresult);
        }
        qr.setResultlist(query.getResultList());
        query = entityManager.createQuery("select count(" + getCountField(this.entityClass) + ") from " + entityname + " o " + (wherejpql == null || "".equals(wherejpql.trim()) ? "" : "where " + wherejpql));
        setQueryParams(query, queryParams);
        qr.setTotalrecord((Long) query.getSingleResult());
        return qr;
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public ResultHelp<T> getScrollData(int firstindex, int maxresult, String wherejpql, Object[] queryParams) {
        return getScrollData(firstindex, maxresult, wherejpql, queryParams, null);
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public ResultHelp<T> getScrollData(int firstindex, int maxresult, LinkedHashMap<String, String> orderby) {
        return getScrollData(firstindex, maxresult, null, null, orderby);
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public ResultHelp<T> getScrollData(int firstindex, int maxresult) {
        return getScrollData(firstindex, maxresult, null, null, null);
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public ResultHelp<T> getScrollData() {
        return getScrollData(-1, -1);
    }

    /**
     * 组装order by语句
     *
     * @param orderby
     * @return
     */
    protected static String buildOrderby(LinkedHashMap<String, String> orderby) {
        StringBuffer orderbyql = new StringBuffer("");
        if (orderby != null && orderby.size() > 0) {
            orderbyql.append(" order by ");
            for (String key : orderby.keySet()) {
                orderbyql.append("o.").append(key).append(" ").append(orderby.get(key)).append(",");
            }
            orderbyql.deleteCharAt(orderbyql.length() - 1);
        }
        return orderbyql.toString();
    }

    protected static void setQueryParams(Query query, Object[] queryParams) {
        if (queryParams != null && queryParams.length > 0) {
            for (int i = 0; i < queryParams.length; i++) {
                query.setParameter(i + 1, queryParams[i]);
            }
        }
    }

    /**
     * 获取统计属性,该方法是为了解决hibernate解析联合主键select count(o) from Xxx
     * o语句BUG而增加,hibernate对此jpql解析后的sql为select
     * count(field1,field2,...),显示使用count()统计多个字段是错误的
     *
     * @param <E>
     * @param clazz
     * @return
     */
    protected static <E> String getCountField(Class<E> clazz) {
        String out = "o";
        try {
            PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
            for (PropertyDescriptor propertydesc : propertyDescriptors) {
                Method method = propertydesc.getReadMethod();
                if (method != null && method.isAnnotationPresent(EmbeddedId.class)) {
                    PropertyDescriptor[] ps = Introspector.getBeanInfo(propertydesc.getPropertyType()).getPropertyDescriptors();
                    out = "o." + propertydesc.getName() + "." + (!ps[1].getName().equals("class") ? ps[1].getName() : ps[0].getName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    /**
     * 获取实体的名称
     *
     * @param <E>
     * @param clazz 实体类
     * @return
     */
    protected static <E> String getEntityName(Class<E> clazz) {
        String entityname = clazz.getSimpleName();
        Entity entity = clazz.getAnnotation(Entity.class);
        if (entity.name() != null && !"".equals(entity.name())) {
            entityname = entity.name();
        }
        return entityname;
    }

}
