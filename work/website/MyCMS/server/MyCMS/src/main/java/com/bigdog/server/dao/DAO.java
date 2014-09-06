/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigdog.server.dao;

import java.io.Serializable;
import java.util.LinkedHashMap;
import com.bigdog.server.util.QueryResult;

/**
 *
 * @author jw362j
 */
public interface DAO<T> {

    public long getCount();

    public void clear();

    public void save(T entity);

    public void update(T entity);

    public void delete(Serializable... entityids);

    public T find(Serializable entityId);

    public QueryResult<T> getScrollData(int firstindex, int maxresult, String wherejpql, Object[] queryParams, LinkedHashMap<String, String> orderby);

    public QueryResult<T> getScrollData(int firstindex, int maxresult, String wherejpql, Object[] queryParams);

    public QueryResult<T> getScrollData(int firstindex, int maxresult, LinkedHashMap<String, String> orderby);

    public QueryResult<T> getScrollData(int firstindex, int maxresult);

    public QueryResult<T> getScrollData();
}
