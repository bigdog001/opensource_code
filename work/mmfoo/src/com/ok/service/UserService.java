package com.ok.service;

import com.ok.bean.MmfooHd;
import com.ok.bean.ShortMessage;
import com.ok.bean.User;
import com.ok.bean.UserHd;
import com.ok.dao.UserDao;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-5-17
 * Time: 22:50:31
 */
public class UserService {
   private UserDao ud;

    public void setUd(UserDao ud) {
        this.ud = ud;
    }
    public boolean checkExsit(String email){
     return ud.checkExsit(email);
    }

    public void saveUser_reg(String email,String path_tpl){
       ud.saveUser_reg(email,path_tpl);
    }
    public boolean is_active(String email,String active){
      return ud.isActive(email,active);
    }
    public boolean login(String email,String passwor){
        return ud.login(email,passwor);
    }
    public MmfooHd getMmfHd(int hdid){
     return  ud.getMmffhds(hdid);
    }
    public User getUser(String  email){
       return ud.getUser(email);
    }
    public void saveUserHd(UserHd uhd){
        ud.saveUserHd(uhd );
    }
    public boolean joinOrnot(int user_id,int hdid){
        return ud.joinOrnot(user_id,hdid)  ;
    }
   public void saveFriends(int myid,int friendsid){
        ud.saveMyfriend(myid,friendsid);
   }
   public void tiket(int user_id,int who_put_tick){
       ud.tecikUser(user_id,who_put_tick);
   }
    public boolean is_Myfriends(int my_user_id,int friends_user_id){
        return ud.is_Myfriends(my_user_id,friends_user_id);
    }
    public boolean tickTime(int get_tick_user_id, int who_put_tick){
      return ud.tickTime(get_tick_user_id,who_put_tick)  ;
    }
    public boolean is_userSee(int my_user_id, int friends_user_id){
        return ud.is_userSee(my_user_id,friends_user_id); 
    }
    public void saveUserSee(int my_user_id, int friends_user_id){
        ud.saveUserSee(my_user_id,friends_user_id);
    }
    public void addShortMessage(ShortMessage sm){
       ud.addShortMessage(sm);
    }
   public void deletMessage(int mesageid){
       ud.deletMessage(mesageid);
   }
    
}
