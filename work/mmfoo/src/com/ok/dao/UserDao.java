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

    public void saveUser_reg(String email, String path_tpl); //注册时 首先将其email作为用户名入库

    public boolean checkExsit(String emai);

    public boolean isActive(String emai, String active);

    public boolean login(String emai, String passord);

    public boolean joinOrnot(int user_id, int hdid);

    public User getUser(int userid);

    public User getUser(String email);

    public List<User> getAllUser();

    public List<User> getByPage(int start, int lenth);

    public User getUserIndex();//取本月最美美女

    public User getUserNew();//取最新参加活动的美女

    public String getHdName(int user_id);//取本月最美美女

    public UserHd getUserHd(int user_id);//取用户参加的第一个活动

    public List<User> getUserTop(final int page, final int size);//取成参加报名活动的用户     首页的用户动态

    public List<UserHd> getUserHds(final int user_id, final int page, final int size);//取用户参加的活动

    public List<User> getUserhd(final int hdid, final int page, final int size);//取一个活动的参与者

    public List<MmfooHd> getMmffhds();//取所有的mmfoo活动对象

    public List<MmfooHd> getMmfhds(final int page, final int size);//取前top个mmfoo活动对象

    public MmfooHd getMmffhds(int hdid);//取mmfoo活动对象

    public void saveUserHd(UserHd uhd);//用户参加活动

    public List<User> getMytopFriends(int user_id, final int page, final int size);

    public void saveMyfriend(int myid, int friendsid);

    public List<ImgSet> getImgSetsByid(final int user_id, final int page, final int size);//找某用户前top个相册

    public List<UserImg> getImgsBysetId(final int setid, final int page, final int size);//找用户某相册下的所有相片

    public void tecikUser(int user_id ,int who_put_tick);//给某人投票

    //-----用户心情
    public List<ShortMessage> getShortMessageByEmail(int user_id, final int page, final int size);//取用户的短消息

    public void addShortMessage(ShortMessage sm);

    public void deletMessage(int messageid);

    public int totalMessage(String my_user_email);//我发的所有微博数 即短消息数

    public UserImg getSetFace(int setid); //取相册封面 即取相册中最新的一张封面（如果没有设置封面的话），如果已经设置了封面 那么就取被设置的那张

    public void makeFace(int imgid, int setid);   //设置封面

    //用户关注功能---模块    UserSee  bean    你关注了我  你就是我的粉丝

    public int getFans(int my_user_id);//我有多少粉丝  你关注了我  你就是我的粉丝 即有多少人关注了我

    public List<User> whoSeeMe(int my_user_id, final int page, final int size);//谁关注了我 即关注我的那帮人都是谁

    public List<User> myFriendsSee(int user_id, final int page, final int size);//我关注的所有人

    public void saveUserSee(int my_user_id, int friends_user_id);//关注数据入库

    public boolean is_userSee(int my_user_id, int friends_user_id); //我是否关注了某人

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

    public void who_visit_me(int my_user_id,int friends_user_id);//谁访问过我的空间

    public List<User> getUserAccessMyspace(int my_user_id,final int page,final int size );//一周内都有谁访问过我的空间


}
