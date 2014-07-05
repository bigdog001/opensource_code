package com.facemail.mobile.android.db.dao.impl;

import com.facemail.mobile.android.db.bean.UserAcount;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 4/18/13
 * Time: 7:07 PM
 * To change this template use File | Settings | File Templates.
 */
public interface UserAccountImpl {
     public long insertUserAccount(UserAcount ua);
     public void deleteUserAccount(UserAcount ua);
     public UserAcount getUserAccount(String email);
     public List<UserAcount> getUserAccount();
     public List<UserAcount> getUserAccountByScanFrequency();
     public void updateUserAccount(UserAcount ua);
     public boolean isExsit(String email);
     public void updateRcvTime(Date date,UserAcount userAcount);
     public void updateRcvFrequency(int minute,UserAcount userAcount);
}
