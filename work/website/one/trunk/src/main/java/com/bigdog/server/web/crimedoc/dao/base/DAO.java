package com.bigdog.server.web.crimedoc.dao.base;

import java.io.Serializable;
import java.util.LinkedHashMap;
import com.bigdog.server.web.crimedoc.bean.QueryResult;

public interface DAO<T> {
	public long getCount();
	public void clear();
	public void save(T entity);
	public void update(T entity);
	public void delete(Serializable ... entityids);
	public T find(Serializable entityId);
	public QueryResult<T> getScrollData(int firstindex, int maxresult, String wherejpql, Object[] queryParams,LinkedHashMap<String, String> orderby);
	
	public QueryResult<T> getScrollData(int firstindex, int maxresult, String wherejpql, Object[] queryParams);
	
	public QueryResult<T> getScrollData(int firstindex, int maxresult, LinkedHashMap<String, String> orderby);
	
	public QueryResult<T> getScrollData(int firstindex, int maxresult);
	
	public QueryResult<T> getScrollData();
}
