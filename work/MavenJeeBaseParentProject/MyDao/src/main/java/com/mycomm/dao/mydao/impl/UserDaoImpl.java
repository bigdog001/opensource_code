/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycomm.dao.mydao.impl;

import com.mycomm.dao.mydao.IUserDao;
import com.mycomm.dao.mydao.User;
import com.mycomm.dao.mydao.base.MyDaoSupport;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

/**
 *
 * @author jw362j
 */
@Service
public class UserDaoImpl extends MyDaoSupport<User> implements IUserDao {

    public void init() {
        for (int i = 0; i < 90; i++) {
            User go = new User();
//            go.setU_id(UUID.randomUUID().timestamp());
            go.setU_name("setU_name---");
            this.save(go);
        }
    }
    
    

  
}
