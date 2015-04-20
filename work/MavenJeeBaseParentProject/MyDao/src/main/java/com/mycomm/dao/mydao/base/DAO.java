/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycomm.dao.mydao.base;
import com.mycomm.dao.mydao.ResultHelp;
import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 *
 * @author jw362j
 */
public interface DAO<T> {
    /**
	 * 获取记录总数
	 * @param entityClass 实体类
	 * @return
	 */
	public long getCount();
	/**
	 * 清除一级缓存的数据
	 */
	public void clear();
	/**
	 * 保存实体
	 * @param entity 实体id
	 */
	public void save(T entity);
	/**
	 * 更新实体
	 * @param entity 实体id
	 */
	public void update(T entity);
	/**
	 * 删除实体
	 * @param entityClass 实体类
	 * @param entityids 实体id数组
	 */
	public void delete(Serializable ... entityids);
	/**
	 * 获取实体
	 * @param <T>
	 * @param entityClass 实体类
	 * @param entityId 实体id
	 * @return
	 */
	public T find(Serializable entityId);
	/**
	 * 获取分页数据
	 * @param <T>
	 * @param entityClass 实体类
	 * @param firstindex 开始索引
	 * @param maxresult 需要获取的记录数
	 * @return
	 */
        public ResultHelp<T> getScrollData(int firstindex, int maxresult, String wherejpql, Object[] queryParams,LinkedHashMap<String, String> orderby);
        public ResultHelp<T> getScrollData(int firstindex, int maxresult, String wherejpql, Object[] queryParams);	
	public ResultHelp<T> getScrollData(int firstindex, int maxresult, LinkedHashMap<String, String> orderby);
	public ResultHelp<T> getScrollData(int firstindex, int maxresult);	
	public ResultHelp<T> getScrollData();
	
}
