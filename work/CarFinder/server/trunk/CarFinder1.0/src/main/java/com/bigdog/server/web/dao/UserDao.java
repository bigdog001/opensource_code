package com.bigdog.server.web.dao;

import com.bigdog.server.web.bean.UserBean;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/4/13
 * Time: 6:30 AM
 */
public interface UserDao {
    public void save(UserBean userBean);
    public void delete(int id);
    public UserBean getById(int id);
    public UserBean login(UserBean userBean);
    public List<UserBean> getUsers(int start,int size);
    public void update(UserBean userBean);
    public int getTotalPages(int pageSize);
}
