package com.ok.bean;

import java.sql.Timestamp;

/**
 * Created by WangJiuNian.
 * User: Administrator
 * Date: 2011-3-30
 * Time: 18:31:57
 */
public class User {
    private int user_id;
    private String user_star; //星座
    private int visit_cnt; //得票數
    private int see_cnt; //页面访问量
    private int user_order; //用户在前台显示的排序
    private int point_cnt; //被關注次數   即我有多少粉丝
    private int discuss; //评论數
    private String email;   //邮件
    private String user_name;   //用户名 其实没什么意义 保留站位字段
    private String truename;    //真是姓名
    private String sex;            //性别
    private String nickname;     //昵称
    private String address;      //个人地址
    private String mobile;       //手机号码
    private String password;     //登陆密码
    private String is_activety; //是否未激活用戶
    private Timestamp reg_time;   //注册时间
    private String activetys;// 激活碼
    private String user_msn;// 用户的msn
    private String worspace;// 工作地

    private Timestamp birthday;// 
    private String height;//身高
    private String school;//学校

    private String hometown;//出生地
    private String blood;//血型
    private String qqnumber;//qq
    private String declaration;//美女宣言
    private String userimg;//大图名称
    private String userthumb;//缩略图名称
    private String heavy;//体重
    private String province;//体重
    private String city;//体重
    private int age;//年龄
    private String sanwei;//三围
    //个人隐私设置
    private String selfsite;//个人主页     1: 全站可见  2： 仅好友可见 3： 仅自己可见
    private String friendlist;//好友列表     1： 全站可见  2：仅好友可见 3： 仅自己可见
    private String selfdynamic;//个人动态    1： 全站可见  2：仅好友可见 3： 仅自己可见
    private String piclist;//相册       1： 全站可见  2：仅好友可见 3： 仅自己可见
    private String hd_ok;//是否有参与的活动被审核通过  以此决定在前台显示与否
    private String jichu;//    基础信息
    private String aihao;//    爱好
    private String jinli;//    经历




    
    private String lovemusic;//    喜欢的音乐
    private String lovestar;//    喜欢的明星
    private String lovemovie;//    喜欢的电影
    private String lovetv;//    喜欢的电视
    private String lovesport;//    喜欢的运动
    private String lovereading;//    喜欢看的书

    
    private String education;//学历
    private String huojiang;//获奖
    private String peixun;//培训
    private String zuopin;//作品
    private String zhanlan;//展览
    private String meiticaifang;//没体采访
    private String chuxihuodong;//出席活动

    public String getWorspace() {
        return worspace;
    }

    public void setWorspace(String worspace) {
        this.worspace = worspace;
    }

    public String getPeixun() {
        return peixun;
    }

    public void setPeixun(String peixun) {
        this.peixun = peixun;
    }

    public String getZuopin() {
        return zuopin;
    }

    public void setZuopin(String zuopin) {
        this.zuopin = zuopin;
    }

    public String getZhanlan() {
        return zhanlan;
    }

    public void setZhanlan(String zhanlan) {
        this.zhanlan = zhanlan;
    }

    public String getMeiticaifang() {
        return meiticaifang;
    }

    public void setMeiticaifang(String meiticaifang) {
        this.meiticaifang = meiticaifang;
    }

    public String getChuxihuodong() {
        return chuxihuodong;
    }

    public void setChuxihuodong(String chuxihuodong) {
        this.chuxihuodong = chuxihuodong;
    }

    public String getLovemusic() {
        return lovemusic;
    }

    public void setLovemusic(String lovemusic) {
        this.lovemusic = lovemusic;
    }

    public String getLovestar() {
        return lovestar;
    }

    public void setLovestar(String lovestar) {
        this.lovestar = lovestar;
    }

    public String getLovemovie() {
        return lovemovie;
    }

    public void setLovemovie(String lovemovie) {
        this.lovemovie = lovemovie;
    }

    public String getLovetv() {
        return lovetv;
    }

    public void setLovetv(String lovetv) {
        this.lovetv = lovetv;
    }

    public String getLovesport() {
        return lovesport;
    }

    public void setLovesport(String lovesport) {
        this.lovesport = lovesport;
    }

    public String getLovereading() {
        return lovereading;
    }

    public void setLovereading(String lovereading) {
        this.lovereading = lovereading;
    }

    public String toString() {
        return "user_id      is:     " + user_id +
                "visit_cnt     is:     " + visit_cnt +
                "email        is:      " + email +
                "user_name    is:      " + user_name +
                "truename      is:     " + truename +
                "sex         is:       " + sex +
                "nickname     is:      " + nickname +
                "address       is:     " + address +
                "mobile          is:   " + mobile +
                "password        is:   " + password +
                "is_activety     is:   " + is_activety +
                "Timestamp reg_time  is:" + reg_time +
                "activetys         is: " + activetys +
                "birthday    is:" + birthday +
                "height      is:" + height +
                "school      is:" + school +
                "hometown    is:" + hometown +
                "blood       is:" + blood +
                "qqnumber    is:" + qqnumber +
                "declaration is:" + declaration +
                "userimg     is:" + userimg +
                "userthumb   is:" + userthumb +
                "heavy       is:" + heavy +
                "age         is:" + age +
                "sanwei      is:" + sanwei +
                "selfsite    is:" + selfsite +
                "friendlist  is:" + friendlist +
                "selfdynamic is:" + selfdynamic +
                "visit_cnt is:" + visit_cnt +
                "point_cnt is:" + point_cnt +
                "piclist     is:" + piclist;
    }
     public User(){
         reg_time=new Timestamp(System.currentTimeMillis());
         selfsite="1";
         friendlist="1";
         selfdynamic="1";
         piclist="1";
         point_cnt=0;
         visit_cnt=0;
         age=0;
     }

    public String getUser_star() {
        return user_star;
    }

    public String getAihao() {
        return aihao;
    }

    public void setAihao(String aihao) {
        this.aihao = aihao;
    }

    public void setUser_star(String user_star) {
        this.user_star = user_star;
    }

    public String getJinli() {
        return jinli;
    }

    public void setJinli(String jinli) {
        this.jinli = jinli;
    }

    public int getSee_cnt() {
        return see_cnt;
    }

    public String getJichu() {
        return jichu;
    }

    public void setJichu(String jichu) {
        this.jichu = jichu;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getHuojiang() {
        return huojiang;
    }

    public void setHuojiang(String huojiang) {
        this.huojiang = huojiang;
    }

    public String getUser_msn() {
        return user_msn;
    }

    public void setUser_msn(String user_msn) {
        this.user_msn = user_msn;
    }

    public void setSee_cnt(int see_cnt) {
        this.see_cnt = see_cnt;
    }

    public int getDiscuss() {
        return discuss;
    }

    public int getUser_order() {
        return user_order;
    }

    public void setUser_order(int user_order) {
        this.user_order = user_order;
    }

    public String getProvince() {
        return province;
    }

    public String getHd_ok() {
        return hd_ok;
    }

    public void setHd_ok(String hd_ok) {
        this.hd_ok = hd_ok;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDiscuss(int discuss) {
        this.discuss = discuss;
    }

    public int getPoint_cnt() {
        return point_cnt;
    }

    public void setPoint_cnt(int point_cnt) {
        this.point_cnt = point_cnt;
    }

    public String getActivetys() {
        return activetys;
    }

    public void setActivetys(String activetys) {
        this.activetys = activetys;
    }

    public String getIs_activety() {
        return is_activety;
    }

    public void setIs_activety(String is_activety) {
        this.is_activety = is_activety;
    }

    public int getVisit_cnt() {
        return visit_cnt;
    }

    public void setVisit_cnt(int visit_cnt) {
        this.visit_cnt = visit_cnt;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Timestamp getReg_time() {
        return reg_time;
    }

    public void setReg_time(Timestamp reg_time) {
        this.reg_time = reg_time;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getQqnumber() {
        return qqnumber;
    }

    public void setQqnumber(String qqnumber) {
        this.qqnumber = qqnumber;
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    public String getUserimg() {
        return userimg;
    }

    public void setUserimg(String userimg) {
        this.userimg = userimg;
    }

    public String getUserthumb() {
        return userthumb;
    }

    public void setUserthumb(String userthumb) {
        this.userthumb = userthumb;
    }

    public String getHeavy() {
        return heavy;
    }

    public void setHeavy(String heavy) {
        this.heavy = heavy;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSanwei() {
        return sanwei;
    }

    public void setSanwei(String sanwei) {
        this.sanwei = sanwei;
    }

    public String getSelfsite() {
        return selfsite;
    }

    public void setSelfsite(String selfsite) {
        this.selfsite = selfsite;
    }

    public String getFriendlist() {
        return friendlist;
    }

    public void setFriendlist(String friendlist) {
        this.friendlist = friendlist;
    }

    public String getSelfdynamic() {
        return selfdynamic;
    }

    public void setSelfdynamic(String selfdynamic) {
        this.selfdynamic = selfdynamic;
    }

    public String getPiclist() {
        return piclist;
    }

    public void setPiclist(String piclist) {
        this.piclist = piclist;
    }
}
