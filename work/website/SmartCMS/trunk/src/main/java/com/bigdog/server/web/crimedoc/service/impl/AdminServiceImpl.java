package com.bigdog.server.web.crimedoc.service.impl;

import com.bigdog.server.web.crimedoc.bean.Admin;
import com.bigdog.server.web.crimedoc.bean.Wsbean;
import com.bigdog.server.web.crimedoc.dao.AdminDao;
import com.bigdog.server.web.crimedoc.service.AdminService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 10/6/13
 * Time: 9:05 AM
 */
@Service("adminService")
public class AdminServiceImpl implements AdminService {
    Logger logger = Logger.getLogger(this.getClass());
    private Wsbean wsbean;

    @Resource
    private AdminDao adminDao;

    @Override
    public void setWSbean(Wsbean wsbean) {
        this.wsbean = wsbean;
    }

    @Override
    public void add(Admin admin) {
        adminDao.add(admin);
    }

    @Override
    public void del(int id) {
        adminDao.del(id);
    }

    @Override
    public Admin get(int id) {
        return adminDao.get(id);
    }

    @Override
    public void update(Admin admin) {
        adminDao.update(admin);
    }


    @Override
    public boolean adminLogin() {
        boolean login_status = false;
        logger.info("wsbean---:" + wsbean);
        if ("".equals(wsbean.getUsername()) || "".equals(wsbean.getPasswd()) || wsbean.getUsername() == null || wsbean.getPasswd() == null) {
            login_status = false;
        } else {
            Admin admin = adminDao.adminLogin(wsbean.getUsername(), wsbean.getPasswd());
            if (admin == null) {
                login_status = false;
                logger.info("用户:" + wsbean.getUsername() + ",登录失败.");
            } else {
                logger.info("用户:" + admin.getUsername() + ",登录成功.");
                login_status = true;
            }
        }
        return login_status;
    }
}
