package com.bigdog.server.web.crimedoc.dao;

import com.bigdog.server.web.crimedoc.bean.Admin;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 10/6/13
 * Time: 8:57 AM
 */
public interface AdminDao {
    public void add(Admin admin);
    public void del(int id);
    public Admin get(int id);
    public void update(Admin admin);
    public void detactAdmin();
    public Admin adminLogin(String username,String passwd);

}
