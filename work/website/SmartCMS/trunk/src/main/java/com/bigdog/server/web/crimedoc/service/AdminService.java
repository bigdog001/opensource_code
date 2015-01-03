package com.bigdog.server.web.crimedoc.service;

import com.bigdog.server.web.crimedoc.bean.Admin;
import com.bigdog.server.web.crimedoc.bean.Wsbean;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 10/6/13
 * Time: 9:04 AM
 */
public interface AdminService {
    public void setWSbean(Wsbean wsbean);
    public void add(Admin admin);
    public void del(int id);
    public Admin get(int id);
    public void update(Admin admin);
    public boolean adminLogin() ;
}
