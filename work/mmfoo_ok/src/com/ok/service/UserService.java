package com.ok.service;

import com.ok.bean.*;
import com.ok.dao.UserDao;

import java.util.List;

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

    public boolean checkExsit(String email) {
        return ud.checkExsit(email);
    }

    public void saveUser_reg(String email, String path_tpl, String password, String nickname) {
        ud.saveUser_reg(email, path_tpl, password, nickname);
    }

    public boolean is_active(String email, String active) {
        return ud.isActive(email, active);
    }

    public boolean login(String email, String passwor) {
        return ud.login(email, passwor);
    }

    public MmfooHd getMmfHd(int hdid) {
        return ud.getMmffhds(hdid);
    }

    public User getUser(String email) {
        return ud.getUser(email);
    }

    public void saveUserHd(UserHd uhd) {
        ud.saveUserHd(uhd);
    }

    public boolean joinOrnot(int user_id, int hdid) {
        return ud.joinOrnot(user_id, hdid);
    }

    public void saveFriends(int myid, int friendsid) {
        ud.saveMyfriend(myid, friendsid);
    }

    public void tiket(int user_id, int who_put_tick) {
        ud.tecikUser(user_id, who_put_tick);
    }

    public boolean is_Myfriends(int my_user_id, int friends_user_id) {
        return ud.is_Myfriends(my_user_id, friends_user_id);
    }

    public boolean tickTime(int get_tick_user_id, int who_put_tick) {
        return ud.tickTime(get_tick_user_id, who_put_tick);
    }

    public boolean is_userSee(int my_user_id, int friends_user_id) {
        return ud.is_userSee(my_user_id, friends_user_id);
    }

    public void saveUserSee(int my_user_id, int friends_user_id) {
        ud.saveUserSee(my_user_id, friends_user_id);
    }

    public void addShortMessage(ShortMessage sm) {
        ud.addShortMessage(sm);
    }

    public void deletMessage(int mesageid) {
        ud.deletMessage(mesageid);
    }

    public void archiveImg(int setid, List<String> images, String basepath) {
        ud.archiveImg(setid, images, basepath);
    }

    public void addImgSet(ImgSet is) {
        ud.addImgSet(is);
    }

    public void loveimgset(int setid) {
        ud.LoveImgSet(setid);
    }

    public void deleteImgSet(int setid) {
        ud.deleteImgSet(setid);
    }

    public void updateImgSet(ImgSet is) {
        ud.updateImgSet(is);
    }

    public void savepm(PrivateMessage pm) {
        ud.save_privateMessage(pm);
    }

    public int getUser_idByEamil(String email) {
        return ud.getUser_idByEamil(email);
    }

    public void delete_myprivateMessage(int user_id) {
        ud.delete_myprivateMessage(user_id);
    }

    public void delete_privateMessage(int pmid) {
        ud.delete_privateMessage(pmid);
    }

    public void unSee(final int my_user_id, final int beeSeeed_user_id) {
        ud.unSee(my_user_id, beeSeeed_user_id);
    }

    public void addBlacker(int my_user_id, int backer_user_id) {
        ud.addBlacker(my_user_id, backer_user_id);
    }

    public boolean is_my_backer(int my_user_id, int backer_user_id) {
        return ud.is_my_backer(my_user_id, backer_user_id);
    }

    public void savejichu(final int user_id, final String jichu) { //保存基础信息
        ud.savejichu(user_id, jichu);
    }

    public void saveJinLi(final int user_id, final String jichu) { //保存经历
        ud.saveJinLi(user_id, jichu);
    }

    public void savePeixun(final int user_id, final String jichu) {  //保存培训信息
        ud.savePeixun(user_id, jichu);
    }

    public void saveJLianxiFangshi(final int user_id, final String jichu) { //保存联系方式信息
        ud.saveJLianxiFangshi(user_id, jichu);
    }

    public void saveAihao(final int user_id, final String jichu) { //保存爱好信息
        ud.saveAihao(user_id, jichu);
    }

    public void interestForHd(int hdid) {
        ud.interestForHd(hdid);
    }

    public void pleasego(int user_id) {
        ud.pleasego(user_id);
    }
 public void unpleasego(int user_id) {
        ud.unpleasego(user_id);
    }

    public void deleteUser(int user_id) {
        ud.deleteUser(user_id);
    }

    public void deleteHd(int hdid) {
        ud.deleteHd(hdid);
    }

    public void addMmffhds(MmfooHd hd) {
        ud.addMmffhds(hd);
    }

    public void editeMmffhds(MmfooHd hd) {
        ud.editeMmffhds(hd);
    }

    public void updateImg(int user_id, String img) {
        ud.updateImg(user_id, img);
    }

    public void saveComment(Comment c) {
        ud.saveComment(c);
    }

    public List<User> getUserSearche(String truename) {
        return ud.getUserSearche(truename);
    }

    public void UpdateLianluoXinxi(User user) {
        ud.UpdateLianluoXinxi(user);
    }

    public void Updateaihao(User user) {
        ud.Updateaihao(user);
    }

    public void UpdateJingli(User user) {
        ud.UpdateJingli(user);
    }

    public void UpdateZuopin(User user) {  //作品描述
        ud.UpdateZuopin(user);
    }

    public void UpdateZhanlan(User user) { //  展览
        ud.UpdateZhanlan(user);
    }

    public void UpdateCaifang(User user) {   //   媒体采访
        ud.UpdateCaifang(user);
    }

    public void UpdateChuxihuodong(User user) {//  出席活动
        ud.UpdateChuxihuodong(user);
    }

    public void UpdatePeixun(User user) {   //  培训
        ud.UpdatePeixun(user);
    }

    public void UpdateHuojiang(User user) { //  获奖
        ud.UpdateHuojiang(user);
    }

    public void UpdateXueli(User user) {  //   学历
        ud.UpdateXueli(user);
    }

    public void SaveBaseInfor(User user) {  //   基础信息
        ud.SaveBaseInfor(user);
    }
}
