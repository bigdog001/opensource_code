package com.facemail.server.mobile.action.mum;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 5/29/13
 * Time: 5:39 AM
 * To change this template use File | Settings | File Templates.
 */
public interface UserDao {
    public void save(User user);
    public void delete(int user_id);
    public User getByid(int id);
    public User getByPwd(String username,String pwd);
    public List<User> getUsers();
    public void upddate(User user);
}
