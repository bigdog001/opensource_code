package com.ok.daoimpl;

import com.ok.bean.*;
import com.ok.dao.OkBase;
import com.ok.dao.UserDao;
import com.ok.tool.MailSender;
import com.ok.tool.Md5;
import com.ok.tool.ImgUtil;
import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapperResultReader;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-5-17
 * Time: 22:49:53
 */
public class UserImpl extends OkBase implements UserDao {
    private static Logger logger = Logger.getLogger(UserImpl.class);

    public void saveUser(User user) {
        hibernateTemplate.save(user);
    }

    public void saveUser_reg(final String email, String path_tpl, final String password, final String nickname) {
        //随机产生密码
//        String pwd_ = Md5.getMD5((System.currentTimeMillis() + "").getBytes()).substring(0, 5);
        final String pwd = Md5.getMD5((password + System.currentTimeMillis()).getBytes());
        //用户名 密码入库      默认没有激活 不能登陆
        jdbctemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "insert into user(email,password,reg_time,activetys,nickname) values(?,?,?,?,?)";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, email);
                ps.setString(2, Md5.getMD5(password.getBytes()));
                ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                ps.setString(4, pwd);
                ps.setString(5, nickname);
                return ps;
            }
        });
        //将密码发送到指定邮箱。。
        try {
//            pwd为激活码，同时也是用户提交的密码与当前时间的md5密文，pwd_是用户的密码明文状态值
            MailSender.sendMail_regActive(email, password, pwd, path_tpl);
        } catch (EmailException e) {
            logger.error("向注册用户邮件发送异常:" + e);
        }
    }

    public boolean checkExsit(String email) {
        int size_ = jdbctemplate.queryForInt("select count(*) from user where email='" + email + "'");
        if (size_ == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isActive(String emai, String active) {
        String is_ok = (String) jdbctemplate.queryForObject("select is_activety from user where email=?", new Object[]{emai}, java.lang.String.class);
        logger.error("用户" + emai + "的激活状态为：" + is_ok);
        if ("Y".equals(is_ok)) {
            return false;
        }
        int size_ = jdbctemplate.queryForInt("select count(*) from user where email='" + emai + "' and activetys ='" + active + "'");
        if (size_ == 0) {
            logger.error("用户" + emai + "的激失败");
            return false;
        }
        if (size_ == 1) {
            logger.error("用户" + emai + "的激活ok");
            jdbctemplate.update("update user set is_activety='Y' where  email='" + emai + "'");
            return true;
        } else {
            logger.error("用户" + emai + "的激失败");
            return false;
        }

    }

    public boolean login(String emai, String passord) {
        passord = Md5.getMD5(passord.getBytes());
        String login = (String) jdbctemplate.queryForObject("select count(*) from user where email=? and password=? and is_activety='Y'", new Object[]{emai, passord}, String.class);
        logger.error("用戶" + emai + "的登陸驗證結果為:" + login);
        if ("1".equals(login)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean joinOrnot(int user_id, int hdid) {
        int joins = jdbctemplate.queryForInt("select count(*) from userhd where user_id=" + user_id + " and hdid=" + hdid);
        if (joins > 0) {
            logger.error("用戶" + user_id + "已经参与了活动:" + hdid + " 避免重复参与!!");
            return false;
        } else {
            logger.error("用戶" + user_id + "可以参加活动:" + hdid);
            return true;
        }
    }

    public User getUser(int userid) {
        List<User> users = hibernateTemplate.find("from User where user_id=" + userid);
        if (users == null) {
            return null;
        }
        if (users.size() == 0) {
            return null;
        }
        return users.get(0);
    }

    public User getUser(String email) {
        List<User> list = hibernateTemplate.find("from User where email='" + email + "'");
        if (list == null) {
            return null;
        }
        if (list.size() < 1) {
            return null;
        }
        return list.get(0);
    }

    public List<User> getAllUser() {
        return hibernateTemplate.find("from User where hd_ok='Y'");
    }

    @Override
    public List<User> getAllUsersTotal(final int pages, final int size) {
        final int start = pages <= 0 ? 0 : (pages - 1) * size;
        final String hql = "from User ";
        List<User> user_tmp = hibernateTemplate.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                query.setFirstResult(start);
                query.setMaxResults(size);
                List list = query.list();
                return list;
            }
        });
        logger.error("取系统中所有的用户!");
        return user_tmp;

    }

    public int getAllUsers() {
        String sql = "select count(*) from user where hd_ok='Y'";
        int total = jdbctemplate.queryForInt(sql);
        return total;
    }

    @Override
    public int getAllUser_records() {
         String sql = "select count(*) from user ";
        int total = jdbctemplate.queryForInt(sql);
        return total;
    }

    public List<User> getByPage(final int pages, final int lenth) {
        final int start = pages <= 0 ? 0 : (pages - 1) * lenth;
        final String hql = "from User where is_activety='Y'  and hd_ok='Y'  order by visit_cnt ";
        List<User> user_tmp = hibernateTemplate.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                query.setFirstResult(start);
                query.setMaxResults(lenth);
                List list = query.list();
                return list;
            }
        });
        logger.error("取本月最美美女!");
        return user_tmp;
    }

    public User getUserIndex() {
        final String hql = "from User where is_activety='Y' and hd_ok='Y'  order by visit_cnt DESC ";
        List<User> user_tmp = hibernateTemplate.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                query.setFirstResult(0);
                query.setMaxResults(1);
                List list = query.list();
                return list;
            }
        });
        logger.error("取本月最美美女!");
        return user_tmp.get(0);
    }

    public User getUserNew() {
        final String hql = "from User where is_activety='Y'  and hd_ok='Y'  order by reg_time DESC";
        List<User> user_tmp = hibernateTemplate.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                query.setFirstResult(0);
                query.setMaxResults(1);
                List list = query.list();
                return list;
            }
        });
        logger.error("取最新的美女!");
        return user_tmp.get(0);
    }

    public String getHdName(int user_id) {
        String sql = "select hdname from mmfoohd where hdid=(select hdid from userhd where user_id=?)";
        String hdname = (String) jdbctemplate.queryForObject(sql, new Object[]{user_id}, String.class);
        return hdname;

    }

    public UserHd getUserHd(int user_id) {
        //取第一个
        List ll = hibernateTemplate.find("from UserHd where user_id=" + user_id + " and is_ok ='Y'");
        if (ll == null) {
            return null;
        } else {
            if (ll.size() == 0) {
                return null;
            }
            return (UserHd) ll.get(0);
        }

    }

    public List<User> getUserTop(final int page, final int size) {
        final int start = page <= 0 ? 0 : (page - 1) * size;
        final String hql = "from User where is_activety='Y'  and hd_ok='Y'  order by reg_time  DESC";
        List<User> user_tmp = hibernateTemplate.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                query.setFirstResult(start);
                query.setMaxResults(size);
                List list = query.list();
                return list;
            }
        });
        return user_tmp;
    }

    public List<UserHd> getUserHds(final int user_id, final int page, final int size) {
        final int start = page <= 0 ? 0 : (page - 1) * size;
        final String hql = "from UserHd where user_id=" + user_id + " and is_ok='Y' order by jointime DESC";
        List<UserHd> Hd_tmp = hibernateTemplate.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                query.setFirstResult(start);
                query.setMaxResults(size);
                List list = query.list();
                return list;
            }
        });
        return Hd_tmp;
//        return hibernateTemplate.find("from UserHd where user_id=?");
    }

    public List<User> getUserhd(final int hdid, final int page, final int size) {
        final int start = page <= 0 ? 0 : (page - 1) * size;
        final String hql = "from UserHd where hdid=" + hdid + " and is_ok='Y' order by jointime DESC";
        List<UserHd> Hd_tmp = hibernateTemplate.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                query.setFirstResult(start);
                query.setMaxResults(size);
                List list = query.list();
                return list;
            }
        });
        List<User> user_tmp = new ArrayList<User>();

        for (UserHd uhd : Hd_tmp) {
            List lltmp = hibernateTemplate.find("from User where user_id=" + uhd.getUser_id() + " and is_activety ='Y'  and hd_ok='Y' ");
            if (lltmp != null) {
                if (lltmp.size() > 0) {
                    user_tmp.add((User) lltmp.get(0));
                }
            }
        }
        logger.error("参加活动：" + hdid + "的用户一共有：" + user_tmp.size() + " 个");
        return user_tmp;
    }

    @Override
    public List<User> getUserFans(final int page, final int size) {
        final int start = page <= 0 ? 0 : (page - 1) * size;
        final String hql = "from User where  hd_ok='Y' order by point_cnt DESC";
        List<User> Hd_tmp = hibernateTemplate.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                query.setFirstResult(start);
                query.setMaxResults(size);
                List list = query.list();
                return list;
            }
        });
        return Hd_tmp;
    }

    @Override
    public List<User> getUsercnt(final int page, final int size) {
        final int start = page <= 0 ? 0 : (page - 1) * size;
        final String hql = "from User where  is_activety='Y'  and hd_ok='Y'  order by visit_cnt DESC";
        List<User> Hd_tmp = hibernateTemplate.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                query.setFirstResult(start);
                query.setMaxResults(size);
                List list = query.list();
                return list;
            }
        });
        return Hd_tmp;
    }

    public List<MmfooHd> getMmffhds() {
        return hibernateTemplate.find("from MmfooHd");
    }

    @Override
    public List<MmfooHd> getMmffhdsbyuserid(int user_id, int page, final int size) {
//        取某用户参加的活动

//        从用户活动表中得出用户参加的活动id
        final int start = page <= 0 ? 0 : (page - 1) * size;
        final String hql = "from UserHd where user_id=" + user_id + " and is_ok='Y' order by jointime DESC";
        List<UserHd> Hd_tmp = hibernateTemplate.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                query.setFirstResult(start);
                query.setMaxResults(size);
                List list = query.list();
                return list;
            }
        });
        List<MmfooHd> user_tmp = new ArrayList<MmfooHd>();
        if (Hd_tmp == null) {
            return null;
        }
        if (Hd_tmp.size() < 1) {
            return null;
        }
        List<MmfooHd> mhd_tmp = null;
        for (UserHd uhd : Hd_tmp) {
//逐个加载美分活动对象
            mhd_tmp = hibernateTemplate.find("from MmfooHd where hdid=" + uhd.getHdid());
            if (mhd_tmp != null) {
                if (mhd_tmp.size() > 0) {
                    user_tmp.add(mhd_tmp.get(0));
                }
            }

        }
        return user_tmp;
    }

    @Override
    public int getMmffhds_user(int user_id) {
        int x = 0;
        List y = hibernateTemplate.find("from UserHd where user_id=" + user_id);
        if (y == null) {

        }
        if (y.size() < 1) {

        } else {
            x = y.size();
        }

        return x;
    }

    public List<MmfooHd> getMmfhds(final int page, final int size) {
        //取前top个活动
        final int start = page <= 0 ? 0 : (page - 1) * size;
        final String hql = "from MmfooHd order by orders ";
        List<MmfooHd> Hd_tmp = hibernateTemplate.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                query.setFirstResult(start);
                query.setMaxResults(size);
                List list = query.list();
                return list;
            }
        });
        return Hd_tmp;
    }

    @Override
    public void interestForHd(int hdid) {
        jdbctemplate.execute("update mmfoohd set intrest_cnt=intrest_cnt+1");
    }

    @Override
    public int mmfoohd_total() {
        return jdbctemplate.queryForInt("select count(*) from mmfoohd");
    }

    public int mmfoohd_user_total(int hdid) {
        return jdbctemplate.queryForInt("select count(*) from userhd where hdid=" + hdid);
    }

    public MmfooHd getMmffhds(int hdid) {
        if (hibernateTemplate.find("from MmfooHd where hdid=" + hdid) == null) {
            return null;
        }
        return (MmfooHd) hibernateTemplate.find("from MmfooHd where hdid=" + hdid).get(0);
    }

    @Override
    public void addMmffhds(MmfooHd hd) {
        hibernateTemplate.save(hd);
    }

    @Override
    public void editeMmffhds(MmfooHd hd) {
        hibernateTemplate.update(hd);
    }

    public void addVisit_cnt(int hdid) {
        jdbctemplate.execute("update mmfoohd set visit_cnt=visit_cnt+1 where hdid=" + hdid);
    }

    public void saveUserHd(UserHd uhd) {
        hibernateTemplate.save(uhd);
    }

    public List<User> getMytopFriends(int user_id, final int page, final int size) {
        List<User> users = new ArrayList<User>();
        List<Long> mailss = new ArrayList<Long>();
        List emails = jdbctemplate.queryForList("select friendsid from userfriends where user_id=" + user_id + " and friends_flag='Y' order by friendstime");
        Iterator it = emails.iterator();
        while (it.hasNext()) {
            Map m = (Map) it.next();
            mailss.add((Long) m.get("friendsid"));
        }
        // logger.error("用户"+user_id+"的第一个好友为："+mailss.get(0));
        for (Long friendsid : mailss) {
            // Long  friendid=friendsid;
            List tmp = hibernateTemplate.find("from User where user_id=" + friendsid + "");
            if (tmp != null) {
                if (tmp.size() > 0) {
                    users.add((User) tmp.get(0));
                }
            }
        }
        return users;
    }

    public void saveMyfriend(int myid, int friendsid) {
        UserFriends uf = new UserFriends();
        uf.setFriendsid(friendsid);
        uf.setUser_id(myid);
        hibernateTemplate.save(uf);
    }

    public List<ImgSet> getImgSetsByid(final int user_id, final int page, final int size) {
        final int start = page <= 0 ? 0 : (page - 1) * size;
        final String hql = "from ImgSet where user_id=" + user_id + " order by createtime DESC";
        List<ImgSet> Hd_tmp = hibernateTemplate.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                query.setFirstResult(start);
                query.setMaxResults(size);
                List list = query.list();
                return list;
            }
        });
        return Hd_tmp;
    }

    public void addImgSet(ImgSet is) {
        hibernateTemplate.save(is);
    }

    public int getImgTotalBysetid(int setid) {
        return jdbctemplate.queryForInt("select count(*) from userimg where setid=" + setid);
    }

    public ImgSet getImgSetById(int imgsetid) {
        List<ImgSet> ls = hibernateTemplate.find("from ImgSet where setid=" + imgsetid);
        if (ls == null) {
            return null;
        }
        if (ls.size() < 1) {
            return null;
        } else {
            return ls.get(0);
        }

    }

    public void AddImgSetViewCnt(int setid) {
        jdbctemplate.execute("update imgset set see_cnt=see_cnt+1 where setid=" + setid);
    }

    public void LoveImgSet(int setid) {
        jdbctemplate.execute("update imgset set favor_cnt=favor_cnt+1 where setid=" + setid);
    }

    public void deleteImgSet(int setid) {
        jdbctemplate.execute("delete from imgset  where setid=" + setid);
    }

    public void updateImgSet(ImgSet im) {
//        hibernateTemplate.update(im);
        jdbctemplate.execute("update imgset set setname='" + im.getSetname() + "' ,  setdiscribe='" + im.getSetdiscribe() + "' where setid=" + im.getSetid());
    }

    public List<UserImg> getImgsBysetId(final int setid, final int page, final int size) {
        final String hql = "from UserImg where setid=" + setid + " order by createtime DESC";

        final int start = page <= 0 ? 0 : (page - 1) * size;

        List<UserImg> Hd_tmp = hibernateTemplate.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                query.setFirstResult(start);
                query.setMaxResults(size);
                List list = query.list();
                return list;
            }
        });
        return Hd_tmp;


    }

    @Override
    public void archiveImg(int setid, List<String> images, String basepath) {
//         System.out.println("做缩略图:"+images+","+basepath+",setid"+setid);
        if (images == null) {
            return;
        }
        if (images.size() < 1) {
            return;
        }
        //为上传的照片做缩略图 将缩略图直接放到images目录下
        if (basepath == null) {
            return;
        }
        if (basepath.endsWith(File.separator)) {

        } else {
            basepath = basepath + File.separator;
        }
        String tmp_imgname_out = "";
        int x = 0;
        for (String name : images) {
            x++;
            tmp_imgname_out = Md5.getMD5((System.currentTimeMillis() + "" + x).getBytes()) + ".jpg";
//              System.out.println(tmp_imgname_out+"--"+name);
            try {
                ImgUtil.saveImageAsJpg(basepath + "images" + File.separator + "upload" + File.separator + "54zzb" + File.separator + name, basepath + "images" + File.separator + tmp_imgname_out, 223, 170);
                //做完一张缩略图就进行一次入库
                UserImg ui = new UserImg();
                ui.setSetid(setid);
                ui.setThumb_img(tmp_imgname_out);
                ui.setImgname(name);
                hibernateTemplate.save(ui);
            } catch (Exception e) {
                logger.error("做缩略图时发生故障：" + e.getMessage());
            }
        }
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void tecikUser(int user_id, int who_put_tick) {
        if (tickTime(user_id, who_put_tick)) {
            logger.error("得票人：" + user_id + " 已经在十分钟内得到了投票人：" + who_put_tick + " 的投票");
        } else {
            jdbctemplate.execute("update user set visit_cnt=visit_cnt+1 where user_id=" + user_id);
            //录入投票足迹
            TickFooter tf = new TickFooter();
            tf.setBe_tick_user_id(user_id);
            tf.setUser_id(who_put_tick);
            hibernateTemplate.save(tf);
        }

    }

    public ShortMessage getShortMessageByRecently(int user_id) {//取用户的最后一条微博
        final String hql = "from ShortMessage where user_id=" + user_id + " order by messagetime DESC";
        List<ShortMessage> list_message = new ArrayList<ShortMessage>();
        list_message = hibernateTemplate.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                query.setFirstResult(0);
                query.setMaxResults(1);
                List list = query.list();
                return list;
            }
        });
        if (list_message == null) {
            return null;
        }
        if (list_message.size() < 1) {
            return null;
        }
        return list_message.get(0);
    }

    public List<ShortMessage> getShortMessageByEmail(int user_id, final int page, final int size) {
        //取用户的短消息
        final String hql = "from ShortMessage where user_id=" + user_id + " order by messagetime DESC";

        final int start = page <= 0 ? 0 : (page - 1) * size;
        List<ShortMessage> list_message = new ArrayList<ShortMessage>();
        list_message = hibernateTemplate.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                query.setFirstResult(start);
                query.setMaxResults(size);
                List list = query.list();
                return list;
            }
        });
        return list_message;
    }

    public List<ShortMessage> getAllShortMessage(final int page, final int size) {
        //取用户的短消息
        final String hql = "from ShortMessage  order by messagetime DESC";

        final int start = page <= 0 ? 0 : (page - 1) * size;
        List<ShortMessage> list_message = new ArrayList<ShortMessage>();
        list_message = hibernateTemplate.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                query.setFirstResult(start);
                query.setMaxResults(size);
                List list = query.list();
                return list;
            }
        });
        return list_message;
    }

    public void addShortMessage(ShortMessage sm) {
        //发表心情
        hibernateTemplate.save(sm);
    }

    public void deletMessage(int messageid) {
        //删除心情
        jdbctemplate.execute("delete from shortmessage where mesageid=" + messageid);
    }

    public int totalMessage(String my_user_email) {//我发的所有微博数 即短消息数
        int message_total = jdbctemplate.queryForInt("select count(*) from shortmessage where email='" + my_user_email + "'");
        return message_total;
    }

    public int totalMessage(int user_id) {
        int message_total = jdbctemplate.queryForInt("select count(*) from shortmessage where user_id=" + user_id);
        return message_total;
    }

    public UserImg getSetFace(int setid) { //取相册封面 即取相册中最新的一张封面（如果没有设置封面的话），如果已经设置了封面 那么就取被设置的那张
//         UserImg  user_img=new UserImg();
        List<UserImg> user_img_tmp = hibernateTemplate.find("from UserImg where setid=" + setid);
        if (user_img_tmp == null) {
            return null;//相册中无相片 
        }
        List<UserImg> user_img = new ArrayList<UserImg>();
        for (UserImg u_img : user_img_tmp) {
            //将所有被设置为封面的bean都选取出来  然后只返回第一张
            if ("Y".equals(u_img.getIs_face())) {
                user_img.add(u_img);
            }
        }
        if (user_img.size() == 0) {
            //如果没有任何一张被设置成为封面 那么返回本相册下的第一张
            return null;
        }
        if (user_img.size() > 0) {
            return user_img.get(0);
        }
        return null;
    }

    public void makeFace(int imgid, int setid) {   //设置封面
        //将编号为imgid的照片设置成封面 首先将本相册下的其它的相片解除封面
        jdbctemplate.execute("update userimg set is_face='N' where setid=" + setid);
        //设置封面
        jdbctemplate.execute("update userimg set is_face='Y' where imgid=" + imgid);
    }

    public int getFans(int my_user_id) {//我有多少粉丝  你关注了我  你就是我的粉丝 即有多少人关注了我 我被关注了多少次
        int be_see_total = jdbctemplate.queryForInt("select count(*) from usersee where be_seeed_user_id=" + my_user_id);
        return be_see_total;
    }

    public List<User> whoSeeMe(int my_user_id, final int page, final int size) {//谁关注了我 即关注我的那帮人都是谁
        final int start = page <= 0 ? 0 : (page - 1) * size;
        final String hql = "from UserSee where be_seeed_user_id=" + my_user_id; //此时我扮演的是被关注者角色
        //查出那些和我有关的关注对象
//        List<UserSee>see_withme= hibernateTemplate.find("from UserSee where be_seeed_user_id="+my_user_id);
        List<UserSee> see_withme = hibernateTemplate.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                query.setFirstResult(start);
                query.setMaxResults(size);
                List list = query.list();
                return list;
            }
        });
        if (see_withme == null) {
            return null;
        }
        //取出那些关注我的所有的用户对象存入see_user_tmp中
        List<User> see_user_tmp = new ArrayList<User>();
        for (UserSee us : see_withme) {
            List<User> tmp = hibernateTemplate.find("from User where user_id=" + us.getUser_id());
            if (tmp != null) {
                if (tmp.size() > 0) {
                    see_user_tmp.add(tmp.get(0));
                }
            }
            //循环结束后所有关注我的那些用户对象都取出来了
        }
        //此时已经实现了分页操作
        return see_user_tmp;
    }

    public int fanstotal(int my_user_id) { // 我关注了多少人
        String sql = "select count(*) from usersee where user_id=" + my_user_id;
        int total = jdbctemplate.queryForInt(sql);
        return total;
    }

    @Override
    public int fanshx(int my_user_id) {//我和多少人互相关注
        //找出我所关注的所有人
        List<UserSee> t1 = hibernateTemplate.find("from UserSee where user_id=" + my_user_id);
        if (t1 == null) {
            return 0;
        }
        if (t1.size() < 1) {
            return 0;
        }

        //从中筛选出那些关注我的人

        int total = 0;
        for (UserSee user : t1) {
            if (is_userSee(user.getBe_seeed_user_id(), my_user_id)) {
                total++;
            }
        }
        return total;
    }

    @Override
    public List<User> getHxUserSee(int my_user_id) {//和我为互相关注的那帮人都是谁
        List<UserSee> t1 = hibernateTemplate.find("from UserSee where user_id=" + my_user_id);
        if (t1 == null) {
            return null;
        }
        if (t1.size() < 1) {
            return null;
        }
        List<User> t2 = new ArrayList<User>();
        for (UserSee user : t1) {
            if (is_userSee(user.getUser_id(), my_user_id)) {
                List l1 = hibernateTemplate.find("from User where user_id=" + user.getUser_id());
                if (l1 != null) {
                    if (l1.size() > 0) {
                        t2.add((User) l1.get(0));
                    }
                }

            }
        }

        return t2;
    }

    public List<User> iSeewho(int user_id, final int page, final int size) {
        ////我关注的所有人
        final int start = page <= 0 ? 0 : (page - 1) * size;
        final String hql = "from UserSee where user_id=" + user_id; //此时我扮演的是关注者的角色
        //查出那些和我有关的关注对象
//        List<UserSee>see_withme= hibernateTemplate.find("from UserSee where be_seeed_user_id="+my_user_id);
        List<UserSee> see_withme = hibernateTemplate.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                query.setFirstResult(start);
                query.setMaxResults(size);
                List list = query.list();
                return list;
            }
        });
        if (see_withme == null) {
            return null;
        }
        //取出那些关注我的所有的用户对象存入see_user_tmp中
        List<User> see_user_tmp = new ArrayList<User>();
        for (UserSee us : see_withme) {
            List<User> tmp = hibernateTemplate.find("from User where user_id=" + us.getBe_seeed_user_id());
            if (tmp != null) {
                if (tmp.size() > 0) {
                    see_user_tmp.add(tmp.get(0));
                }
            }
            //循环结束后所有关注我的那些用户对象都取出来了
        }
        //此时已经实现了分页操作
        return see_user_tmp;
    }

    public void saveUserSee(int my_user_id, int friends_user_id) {
        jdbctemplate.execute("update user set point_cnt=point_cnt+1 where user_id=" + friends_user_id);
        //关注数据入库
        UserSee us = new UserSee();
        us.setUser_id(my_user_id);
        us.setBe_seeed_user_id(friends_user_id);
        hibernateTemplate.save(us);
    }

    public boolean is_userSee(int my_user_id, int friends_user_id) {
        //我是否关注了某人
        int see_total = jdbctemplate.queryForInt("select count(*) from usersee where be_seeed_user_id=" + friends_user_id + " and user_id=" + my_user_id);
        logger.error("被关注着：" + friends_user_id + " 已经被：" + my_user_id + " 这个人关注的值为：" + see_total);
        if (see_total <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public void unSee(final int my_user_id, final int beeSeeed_user_id) {
        jdbctemplate.update("delete from usersee where user_id=? and be_seeed_user_id=?", new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1, my_user_id);
                preparedStatement.setInt(2, beeSeeed_user_id);
            }
        });
    }

    public void saveFoot(int get_tick_user_id, int who_put_tick) {//给人投票
        TickFooter tf = new TickFooter();
        tf.setUser_id(who_put_tick);
        tf.setBe_tick_user_id(get_tick_user_id);
        hibernateTemplate.save(tf);
    }

    public boolean tickTime(int get_tick_user_id, int who_put_tick) {  //我10分钟内有没有给某人投过票
        String sql = "select count(*) from tickfooter where tick_time between now()-interval 10 MINUTE and now() and user_id=" + who_put_tick + " and be_tick_user_id=" + get_tick_user_id;
        int total = jdbctemplate.queryForInt(sql);
        if (total > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean is_Join(int my_user_id, int hdid) { //我是否参加了某活动
        String sql = "select count(*) from userhd where user_id=" + my_user_id + " and hdid=" + hdid;
        int total = jdbctemplate.queryForInt(sql);
        if (total > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean is_Myfriends(int my_user_id, int friends_user_id) {//某人是否为我的好友
        String sql = "select count(*) from userfriends where user_id=" + my_user_id + " and friendsid=" + friends_user_id + " and friends_flag='Y'";
        int total = jdbctemplate.queryForInt(sql);
        if (total > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean quest_friend(int my_user_id, int friends_user_id) {//我是否向某人发送过好友请求  不管其是否同意过
        String sql = "select count(*) from userfriends where user_id=" + my_user_id + " and friendsid=" + friends_user_id;
        int total = jdbctemplate.queryForInt(sql);
        if (total > 0) {
            return true;
        } else {
            return false;
        }
    }

    public int getHdFriends(int my_user_id, int friends_user_id) {//我的朋友中又多少人也加我为好友了
        //先查出所有与我有关的好友对象
        List<UserFriends> friends_tmp = hibernateTemplate.find("from UserFriends where user_id=" + my_user_id);
        int total = 0;
        for (UserFriends uf : friends_tmp) {
            if (is_Myfriends(uf.getFriendsid(), my_user_id)) {
                total++;
            }
        }
        return total;
    }

    public int getFriends(int my_user_id) {//我的朋友总数
        String sql = "select count(*) from userfriends where user_id=" + my_user_id + " and friends_flag='Y'";
        int total = jdbctemplate.queryForInt(sql);
        return total;
    }

    public List<User> getMyfriends(int my_user_id, final int page, final int size) {//得到我的所有好友 包括我没同意的
//      List<UserFriends> friends_tmp = hibernateTemplate.find("from UserFriends where user_id=" + my_user_id);
        final String hql = "from UserFriends where user_id=" + my_user_id;
        final int start = page <= 0 ? 0 : (page - 1) * size;
        List<UserFriends> friends_tmp = hibernateTemplate.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                query.setFirstResult(start);
                query.setMaxResults(size);
                List list = query.list();
                return list;
            }
        });
        List<User> friends = new ArrayList<User>();
        for (UserFriends uf : friends_tmp) {
            List<User> tmp = hibernateTemplate.find("from User where user_id=" + uf.getFriendsid());
            if (tmp != null) {
                if (tmp.size() > 0) {
                    friends.add(tmp.get(0));
                }
            }
        }
        return friends;
    }

    public List<User> getMyfriends_y(int my_user_id, final int page, final int size) {//得到我的所有好友 不包括我没同意的
        final String hql = "from UserFriends where user_id=" + my_user_id + " and friends_flag ='Y'";
        final int start = page <= 0 ? 0 : (page - 1) * size;
        List<UserFriends> friends_tmp = hibernateTemplate.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                query.setFirstResult(start);
                query.setMaxResults(size);
                List list = query.list();
                return list;
            }
        });
        List<User> friends = new ArrayList<User>();
        for (UserFriends uf : friends_tmp) {
            List<User> tmp = hibernateTemplate.find("from User where user_id=" + uf.getFriendsid());
            if (tmp != null) {
                if (tmp.size() > 0) {
                    friends.add(tmp.get(0));
                }
            }
        }
        return friends;
    }

    public List<User> getMy_hotfriends(int my_user_id, final int page, final int size) {//我和这帮人互为好友 我加他们为好友了 他们各自也都加我为好友了
        final String hql = "from UserFriends where user_id=" + my_user_id + " and friends_flag ='Y'";
        final int start = page <= 0 ? 0 : (page - 1) * size;
        List<UserFriends> friends_tmp = hibernateTemplate.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                query.setFirstResult(start);
                query.setMaxResults(size);
                List list = query.list();
                return list;
            }
        });
        List<User> friends = new ArrayList<User>();
        for (UserFriends uf : friends_tmp) {
            if (is_Myfriends(uf.getFriendsid(), my_user_id)) {
                List<User> tmp = hibernateTemplate.find("from User where user_id=" + uf.getFriendsid());
                if (tmp != null) {
                    if (tmp.size() > 0) {
                        friends.add(tmp.get(0));
                    }
                }
            }
        }
        return friends;
    }

    public void who_visit_me(int my_user_id, int friends_user_id) {
        VisitFooter vf = new VisitFooter();
        vf.setMy_user_id(my_user_id);
        vf.setFriends_user_id(friends_user_id);
        hibernateTemplate.save(vf);

    }

    public List<User> getUserAccessMyspace(int my_user_id, final int page, final int size) {
        //一周内都有谁访问过我的空间
        //查出一周内的所有访问过我的空间的用户的id
        final int start = page <= 0 ? 0 : (page - 1) * size;
//        final String hql="from VisitFooter where my_user_id="+my_user_id+" and visit_time between  now()-interval 10080 MINUTE and now()";
        final String hql = "from VisitFooter where my_user_id=" + my_user_id + "  order by visit_time DESC";
        List<VisitFooter> VisitFooter_tmp = hibernateTemplate.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                query.setFirstResult(start);
                query.setMaxResults(size);
                List list = query.list();
                return list;
            }
        });
        List<User> user_tmp = new ArrayList<User>();
        for (VisitFooter vf : VisitFooter_tmp) {
            List<User> tmp_ = hibernateTemplate.find("from User where user_id=" + vf.getFriends_user_id());
            if (tmp_ != null) {
                if (tmp_.size() > 0) {
                    user_tmp.add(tmp_.get(0));
                }
            }
        }
        logger.error("近一周内访问：" + my_user_id + " 的空间的用户一共有：" + user_tmp.size() + "人!");
        return user_tmp;
    }

    @Override
    public int space_see_cnt(int user_id) {
        return jdbctemplate.queryForInt("select see_cnt from user where user_id=" + user_id);
    }

    @Override
    public void add_space_see_cnt(int user_id) {
        jdbctemplate.execute("update user set see_cnt=see_cnt+1 where user_id=" + user_id);
    }

    @Override
    public void save_privateMessage(PrivateMessage pm) {
        hibernateTemplate.save(pm);
    }

    @Override
    public void delete_privateMessage(int pmid) {
        jdbctemplate.execute("delete from  privatemessage   where pmid=" + pmid);
    }

    @Override
    public void delete_myprivateMessage(int user_id) {
        jdbctemplate.execute("delete from  privatemessage   where user_id_to=" + user_id);
    }

    @Override
    public List<PrivateMessage> getUserPrivateMessage(int user_id_to, int page, final int size) {
        final int start = page <= 0 ? 0 : (page - 1) * size;
        final String hql = "from PrivateMessage where user_id_to=" + user_id_to;
        List<PrivateMessage> see_withme = hibernateTemplate.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                query.setFirstResult(start);
                query.setMaxResults(size);
                List list = query.list();
                return list;
            }
        });
        return see_withme;
    }

    public List<PrivateMessage> getPrivateMessages(int user_id_to, int user_id_from, final int page, final int size) {
        final int start = page <= 0 ? 0 : (page - 1) * size;
        final String hql = "from PrivateMessage where user_id_to=" + user_id_to + " and user_id_from=" + user_id_from;
        List<PrivateMessage> see_withme = hibernateTemplate.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                query.setFirstResult(start);
                query.setMaxResults(size);
                List list = query.list();
                return list;
            }
        });
        return see_withme;
    }

    @Override
    public void update_privateMessage_status(int pmid) {
        jdbctemplate.execute("update privatemessage set is_read='Y' where pmid=" + pmid);
    }

    @Override
    public int getUser_idByEamil(String email) {
        return jdbctemplate.queryForInt("select user_id from user where email='" + email + "'");
    }

    @Override
    public PrivateMessage GetMyPrivateMessage(int pmid) {
        List tmp = hibernateTemplate.find("from PrivateMessage where pmid=" + pmid);
        if (tmp == null) {
            return null;
        }
        if (tmp.size() == 0) {
            return null;
        }
        return (PrivateMessage) tmp.get(0);
    }

    @Override
    public List<PrivateMessage> GetMyPrivateMessage_reDouble(int pmid, int user_id_to, int user_id_from, final int page, final int size) {
        final int start = page <= 0 ? 0 : (page - 1) * size;
        final String hql = "from PrivateMessage where user_id_to=" + user_id_to + " and user_id_from=" + user_id_from + " and pm_return=" + pmid;
        List<PrivateMessage> see_withme = hibernateTemplate.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                query.setFirstResult(start);
                query.setMaxResults(size);
                List list = query.list();
                return list;
            }
        });
        return see_withme;

    }

    @Override
    public void addBlacker(int my_user_id, int backer_user_id) {
        Blacker black = new Blacker();
        black.setMy_user_id(my_user_id);
        black.setBlacker_user_id(backer_user_id);
        hibernateTemplate.save(black);
        unSee(my_user_id, backer_user_id);
    }

    @Override
    public boolean is_my_backer(int my_user_id, int backer_user_id) {
        int result = jdbctemplate.queryForInt("select count(*) from blacker where my_user_id=? and blacker_user_id=?", new Object[]{my_user_id, backer_user_id});
        if (result > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void savejichu(final int user_id, final String jichu) {
        jdbctemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "update user set jichu=? where user_id=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, jichu);
                ps.setInt(2, user_id);
                return ps;
            }
        });
    }

    @Override
    public void saveJinLi(final int user_id, final String jichu) {
        jdbctemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "update user set jinli=? where user_id=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, jichu);
                ps.setInt(2, user_id);
                return ps;
            }
        });
    }

    @Override
    public void savePeixun(final int user_id, final String jichu) {
        jdbctemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "update user set education=? where user_id=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, jichu);
                ps.setInt(2, user_id);
                return ps;
            }
        });
    }

    @Override
    public void saveJLianxiFangshi(int user_id, String jichu) {

    }

    @Override
    public void saveAihao(final int user_id, final String jichu) {
        jdbctemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "update user set aihao=? where user_id=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, jichu);
                ps.setInt(2, user_id);
                return ps;
            }
        });
    }

    public void pleasego(int user_id) {
        jdbctemplate.execute("update user set hd_ok='Y' where user_id=" + user_id);
    }

    @Override
    public void unpleasego(int user_id) {
       jdbctemplate.execute("update user set hd_ok='N' where user_id=" + user_id);
    }

    @Override
    public void deleteUser(int user_id) {
        jdbctemplate.execute("delete from user  where user_id=" + user_id);
    }

    @Override
    public void deleteHd(int hdid) {
        jdbctemplate.execute("delete from mmfoohd  where hdid=" + hdid);
    }

    @Override
    public void updateImg(int user_id, String img) {
        jdbctemplate.execute("update user set userimg='" + img + "',userthumb='" + img + "' where user_id=" + user_id);
    }

    @Override
    public void saveComment(Comment c) {
        hibernateTemplate.save(c);
    }

    @Override
    public int CommentTotal(int setid) {
        return jdbctemplate.queryForInt("select count(*) from comment where setid=" + setid);
    }

    @Override
    public List<Comment> getSetComment(final int setid, final int page, final int size) {
        final int start = page <= 0 ? 0 : (page - 1) * size;
        final String hql = "from Comment where setid=" + setid + " order by createtime DESC";
        List<Comment> see_withme = hibernateTemplate.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                query.setFirstResult(start);
                query.setMaxResults(size);
                List list = query.list();
                return list;
            }
        });
        return see_withme;
    }

    @Override
    public List<User> getUserSearche(String truename) {
        String sql = "SELECT user_id FROM user where nickname like '%" + truename + "%'";

        List list = jdbctemplate.queryForList(sql);
        if (list == null) {
            return null;
        }
        if (list.size() < 1) {
            return null;
        }
        Iterator it = list.iterator();
        List<Integer> lists = new ArrayList<Integer>();
        while (it.hasNext()) {
            Map usermap = (Map) it.next();
            lists.add(Integer.valueOf("" + usermap.get("user_id")));
        }
        List<User> users = new ArrayList<User>();
        for (Integer ints : lists) {
            List<User> usertmp = hibernateTemplate.find("from User where user_id=" + ints);
            if (usertmp == null) {
                return null;
            }
            if (usertmp.size() < 1) {
                return null;
            }
            users.add(usertmp.get(0));
        }

        logger.error("----->" + users);
        return users;
    }

    @Override
    public void UpdateLianluoXinxi(final User user) { //联络信息
        jdbctemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "update user set email=?,user_msn=?,mobile=?,qqnumber=?,nickname=? where user_id=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, user.getEmail());
                ps.setString(2, user.getUser_msn());
                ps.setString(3, user.getMobile());
                ps.setString(4, user.getQqnumber());
                ps.setString(5, user.getNickname());
                ps.setInt(6, user.getUser_id());
                return ps;
            }
        });
    }

    @Override
    public void Updateaihao(final User user) {    //爱好
        jdbctemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "update user set lovemusic=?,lovestar=?,lovemovie=?,lovetv=?,lovesport=?,lovereading=? where user_id=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, user.getLovemusic());
                ps.setString(2, user.getLovestar());
                ps.setString(3, user.getLovemovie());
                ps.setString(4, user.getLovetv());
                ps.setString(5, user.getLovesport());
                ps.setString(6, user.getLovereading());
                ps.setInt(7, user.getUser_id());
                return ps;
            }
        });
    }

    @Override
    public void UpdateJingli(final User user) {
        jdbctemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "update user set jinli=? where user_id=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, user.getJinli());
                ps.setInt(2, user.getUser_id());
                return ps;
            }
        });
    }

    @Override
    public void UpdateZuopin(final User user) {
        jdbctemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "update user set zuopin=? where user_id=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, user.getZuopin());
                ps.setInt(2, user.getUser_id());
                return ps;
            }
        });
    }

    @Override
    public void UpdateZhanlan(final User user) {
        jdbctemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "update user set zhanlan=? where user_id=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, user.getZhanlan());
                ps.setInt(2, user.getUser_id());
                return ps;
            }
        });
    }

    @Override
    public void UpdateCaifang(final User user) {
        jdbctemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "update user set meiticaifang=? where user_id=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, user.getMeiticaifang());
                ps.setInt(2, user.getUser_id());
                return ps;
            }
        });
    }

    @Override
    public void UpdateChuxihuodong(final User user) {
        jdbctemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "update user set chuxihuodong=? where user_id=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, user.getChuxihuodong());
                ps.setInt(2, user.getUser_id());
                return ps;
            }
        });
    }

    @Override
    public void UpdatePeixun(final User user) {
        jdbctemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "update user set peixun=? where user_id=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, user.getPeixun());
                ps.setInt(2, user.getUser_id());
                return ps;
            }
        });
    }

    @Override
    public void UpdateHuojiang(final User user) {
        jdbctemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "update user set huojiang=? where user_id=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, user.getHuojiang());
                ps.setInt(2, user.getUser_id());
                return ps;
            }
        });
    }

    @Override
    public void UpdateXueli(final User user) {
        jdbctemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "update user set education=? where user_id=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, user.getEducation());
                ps.setInt(2, user.getUser_id());
                return ps;
            }
        });
    }

    @Override
    public void SaveBaseInfor(final User user) {
        jdbctemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "update user set birthday=?,user_star=?,worspace=?,sex=?,hometown=?,blood=?,height=?,heavy=? where user_id=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setTimestamp(1, user.getBirthday());
                ps.setString(2, user.getUser_star());
                ps.setString(3, user.getWorspace());
                ps.setString(4, user.getSex());
                ps.setString(5, user.getHometown());
                ps.setString(6, user.getBlood());
                ps.setString(7, user.getHeight());
                ps.setString(8, user.getHeavy());
                ps.setInt(9, user.getUser_id());
                return ps;
            }
        });
    }
}
