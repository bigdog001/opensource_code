package com.ok.dao;

import com.ok.bean.*;

import java.util.List;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-5-17
 * Time: 22:40:01
 */
public interface UserDao {
    public void saveUser(User user);

    public void saveUser_reg(String email, String path_tpl, String password, String nickname); //注册时 首先将其email作为用户名入库

    public boolean checkExsit(String emai);

    public boolean isActive(String emai, String active);

    public boolean login(String emai, String passord);

    public boolean joinOrnot(int user_id, int hdid);

    public User getUser(int userid);

    public User getUser(String email);

    public List<User> getAllUser();

    public List<User> getAllUsersTotal(final int page, final int size);  //取系统中所有的用户


    public int getAllUsers();//取审核通过了的用户总数

    public int getAllUser_records();//取所有的用户总数

    public List<User> getByPage(int start, int lenth);

    public User getUserIndex();//取本月最美美女

    public User getUserNew();//取最新参加活动的美女

    public String getHdName(int user_id);//取本月最美美女

    public UserHd getUserHd(int user_id);//取用户参加的第一个活动

    public List<User> getUserTop(final int page, final int size);//取参加报名活动的用户     首页的用户动态可以用这个数据 按进来的时间先后排序

    public List<UserHd> getUserHds(final int user_id, final int page, final int size);//取用户参加的活动

    public List<User> getUserhd(final int hdid, final int page, final int size);//取一个活动的参与者

    public List<User> getUserFans(final int page, final int size);//按point_cnt的多少 取用户对象

    public List<User> getUsercnt(final int page, final int size);//按投票数取参与者

    public List<MmfooHd> getMmffhds();//取所有的mmfoo活动对象

    public List<MmfooHd> getMmffhdsbyuserid(final int user_id, final int page, final int size);//取某用户参与的前n个mmfoo活动对象

    public int getMmffhds_user(int user_id);//取某用户参加的所有活动个数

    public List<MmfooHd> getMmfhds(final int page, final int size);//取某页的mmfoo活动对象

    public void interestForHd(int hdid);//对某活动感兴趣

    public int mmfoohd_total();//一共有多少个活动

    public int mmfoohd_user_total(int hdid);//有多少人参加了此活动

    public MmfooHd getMmffhds(int hdid);//取mmfoo活动对象

    public void addMmffhds(MmfooHd hd);//增加活动对象

    public void editeMmffhds(MmfooHd hd);//编辑活动对象

    public void addVisit_cnt(int hdid);//给活动的浏览次数加一

    public void saveUserHd(UserHd uhd);//用户参加活动

    public List<User> getMytopFriends(int user_id, final int page, final int size);

    public void saveMyfriend(int myid, int friendsid);

    public List<ImgSet> getImgSetsByid(final int user_id, final int page, final int size);//找某用户前top个相册

    public void addImgSet(ImgSet is);//增加相册

    public ImgSet getImgSetById(int imgsetid);//根据相册id取相册对象

    public void AddImgSetViewCnt(int setid);//给相册的浏览次数加一

    public void LoveImgSet(int setid);//给相册的浏览次数加一

    public void deleteImgSet(int setid);//给相册的浏览次数加一

    public void updateImgSet(ImgSet im);//给相册的浏览次数加一

    public List<UserImg> getImgsBysetId(final int setid, final int page, final int size);//找某相册下的所有相片

    public void archiveImg(int setid, List<String> images, String basepath);//将用户所传的照片进行归档

    public int getImgTotalBysetid(int setid);//取某相册下的相片数目

    public void tecikUser(int user_id, int who_put_tick);//给某人投票

    //-----用户心情  心情其实就是微博

    public List<ShortMessage> getShortMessageByEmail(int user_id, final int page, final int size);//取用户的短消息  即微博

    public ShortMessage getShortMessageByRecently(int user_id);//取用户的最后一条微博

    public void addShortMessage(ShortMessage sm);

    public List<ShortMessage> getAllShortMessage(final int page, final int size);//按时间顺序取用户的所有微波

    public void deletMessage(int messageid);

    public int totalMessage(String my_user_email);//我发的所有微博数 即短消息数

    public int totalMessage(int user_id);//我发的所有微博数 即短消息数

    public UserImg getSetFace(int setid); //取相册封面 即取相册中最新的一张封面（如果没有设置封面的话），如果已经设置了封面 那么就取被设置的那张

    public void makeFace(int imgid, int setid);   //设置封面

    //用户关注功能---模块    UserSee  bean    你关注了我  你就是我的粉丝  ----------

    public int getFans(int my_user_id);//我有多少粉丝  你关注了我  你就是我的粉丝 即有多少人关注了我

    public List<User> whoSeeMe(int my_user_id, final int page, final int size);//谁关注了我 即关注我的那帮人都是谁

    public List<User> iSeewho(int user_id, final int page, final int size);//我关注的所有人

    public int fanstotal(int my_user_id);//我关注了多少人

    public int fanshx(int my_user_id);//我和多少人互相关注

    public List<User> getHxUserSee(int my_user_id);//和我为互相关注的那帮人都是谁

    public void saveUserSee(int my_user_id, int friends_user_id);//关注数据入库

    public boolean is_userSee(int my_user_id, int friends_user_id); //我是否关注了某人

    public void unSee(final int my_user_id, final int beeSeeed_user_id);//解除和某人的关注关系
    //--------------用户关注功能

    //投票足迹   TickFooter

    public void saveFoot(int get_tick_user_id, int who_put_tick);//给人投票

    public boolean tickTime(int get_tick_user_id, int who_put_tick);  //我10分钟内有没有给某人投过票

    public boolean is_Join(int my_user_id, int hdid); //我是否参加了某活动

    public boolean is_Myfriends(int my_user_id, int friends_user_id);//某人是否为我的好友

    public boolean quest_friend(int my_user_id, int friends_user_id);//我是否向某人发送过好友请求  不管其是否同意过

    public int getHdFriends(int my_user_id, int friends_user_id);//我的朋友中又多少人也加我为好友了

    public int getFriends(int my_user_id);//我的朋友总数

    public List<User> getMyfriends(int my_user_id, final int page, final int size);//得到我的所有好友 包括我没同意的

    public List<User> getMyfriends_y(int my_user_id, final int page, final int size);//得到我的所有好友 不包括我没同意的

    public List<User> getMy_hotfriends(int my_user_id, final int page, final int size);//我和这帮人互为好友 我加他们为好友了 他们各自也都加我为好友了

    public void who_visit_me(int my_user_id, int friends_user_id);//谁访问过我的空间

    public List<User> getUserAccessMyspace(int my_user_id, final int page, final int size);//一周内都有谁访问过我的空间

    public int space_see_cnt(int user_id); // 取页面的访问次数

    public void add_space_see_cnt(int user_id);//给访问次数加1


    //    私信接口       -------------------

    public void save_privateMessage(PrivateMessage pm);//发送私信

    public void delete_privateMessage(int pmid);//根据私信id删除私信

    public void delete_myprivateMessage(int user_id);//删除发给某用户的所有私信

    public List<PrivateMessage> getUserPrivateMessage(int user_id_to, final int page, final int size);//取发给某用户的所有私信

    public List<PrivateMessage> getPrivateMessages(int user_id_to, int user_id_from, final int page, final int size);//取来往的所有私信

    public void update_privateMessage_status(int pmid);//将私信的状态由为读转为已读状态

    public int getUser_idByEamil(String email);//根据用户email取用户id

    public PrivateMessage GetMyPrivateMessage(int pmid);//取私信对象

    public List<PrivateMessage> GetMyPrivateMessage_reDouble(int pmid, int user_id_to, int user_id_from, final int page, final int size);//取我收到的某人发给我的私信 我所有的回复
    //   ------------------- 私信接口


    // 黑名单接口--------------------------

    public void addBlacker(int my_user_id, int backer_user_id);//将某人拉入我的黑名单

    public boolean is_my_backer(int my_user_id, int backer_user_id);//判断某人是否在我的黑名单中
    // --------------------------黑名单接口


//    用户档案接口-------------------------

    public void savejichu(final int user_id, final String jichu);//保存基础信息

    public void saveJinLi(final int user_id, final String jichu);  //保存经历

    public void savePeixun(final int user_id, final String jichu);//保存培训信息

    public void saveJLianxiFangshi(final int user_id, final String jichu);//保存联系方式信息

    public void saveAihao(final int user_id, final String jichu);//保存爱好信息

    //    -------------------------用户档案接口


    // 后台接口----------------------------

    public void pleasego(int user_id);//用户通过审核
    public void unpleasego(int user_id);//屏蔽已经通过的用户

    public void deleteUser(int user_id);//删除用户

    public void deleteHd(int hdid);//删除活动

    // ----------------------------后台接口


    //处理用户所上传的图片------------------

    public void updateImg(int user_id, String img);
    //------------------处理用户所上传的图片


//    相册的评论模块----------------------

    public void saveComment(Comment c);//评论数据入库

    public int CommentTotal(int setid);//取评论数

    public List<Comment> getSetComment(final int setid, final int page, final int size);//取某相册的前n页评论

//    ----------------------相册的评论模块


    //搜索模块---------------------------------

    public List<User> getUserSearche(String truename);


    //---------------------------------搜索模块


    //编辑个人信息-----------------------------

    public void UpdateLianluoXinxi(User user);//联络信息

    public void Updateaihao(User user);//爱好

    public void UpdateJingli(User user);//经历描述

    public void UpdateZuopin(User user);//作品描述

    public void UpdateZhanlan(User user); //  展览

    public void UpdateCaifang(User user);//   媒体采访

    public void UpdateChuxihuodong(User user);//  出席活动

    public void UpdatePeixun(User user);//  培训

    public void UpdateHuojiang(User user);//  获奖

    public void UpdateXueli(User user);//   学历

    public void SaveBaseInfor(User user); //个人基础信息

    //-----------------------------编辑个人信息

}
