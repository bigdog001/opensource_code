package com.ok.action;

import com.ok.bean.MmfooHd;
import com.ok.bean.ShortMessage;
import com.ok.bean.User;
import com.ok.bean.UserHd;
import com.ok.service.UserService;
import com.ok.tool.ImgUtil;
import com.ok.tool.Tool;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-5-17
 * Time: 22:39:07
 */
public class UserAction extends ActionSupport {
    private UserService us;
    private static Logger logger = Logger.getLogger(UserAction.class);
//    String path_base = ServletActionContext.getServletContext().getRealPath("/");

    public void setUs(UserService us) {
        this.us = us;
    }

    private String email;

    public void setEmail(String email) {
        this.email = email;
    }

    public String exsits() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        if (us.checkExsit(email)) {
            out.print("0");
        } else {
            out.print("1");
        }
        return null;
    }

    private String checkcoe;

    public void setCheckcoe(String checkcoe) {
        this.checkcoe = checkcoe;
    }

    public String CheckCode() throws IOException {
        String code = "";// (String);

        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        if (ActionContext.getContext().getSession().get("code") == null) {
            out.print("0");
            return null;
        } else {
            code = (String) ActionContext.getContext().getSession().get("code");
        }
        if ("".equals(code) || code == null) {
            out.print("0");
            return null;
        }
        if ("".equals(checkcoe) || checkcoe == null) {
            out.print("0");
            return null;
        }
        if (code.equals(checkcoe)) {
            // ActionContext.getContext().getSession().remove("code");
            out.print("1");
            return null;
        } else {
            out.print("0");
            return null;
        }
    }

    public String SaveUser() throws IOException {
        String code = "";// (String);
        String path_base = ServletActionContext.getServletContext().getRealPath("/");
        String path_tpl = path_base + "WEB-INF" + File.separator + "tpl" + File.separator + "ConfirmMail.html";

        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        if (ActionContext.getContext().getSession().get("code") == null) {
            out.print("0");
            return null;
        } else {
            code = (String) ActionContext.getContext().getSession().get("code");
        }
        if ("".equals(code) || code == null) {
            out.print("0");
            return null;
        }
        if ("".equals(checkcoe) || checkcoe == null) {
            out.print("0");
            return null;
        }
        if ("".equals(email) || email == null) {
            out.print("0");
            return null;
        }
        email = email.trim();
        checkcoe = checkcoe.trim();
        if (!us.checkExsit(email)) {
            logger.error("注册用户:" + email + " email 地址被占用!!");
            out.print("3");
            return null;
        }
        if (!Tool.checkEmail(email)) {
            logger.error("注册用户:" + email + " email 格式错误!!");
            out.print("2");
            return null;
        }
        if (code.equals(checkcoe)) {
            ActionContext.getContext().getSession().remove("code");
            // 保存用户
            us.saveUser_reg(email, path_tpl);
            //  ActionContext.getContext().getSession().put("islogin", "Y");
            ActionContext.getContext().getSession().put("login_email", email);
            out.print("1");
            return null;
        } else {
            out.print("0");
            return null;
        }
    }

    private String activeid;

    public void setActiveid(String activeid) {
        this.activeid = activeid;
    }

    public String active() {
        //激活成功后自动登陆到主页 激活失败后也到主页但不登陆
        if (us.is_active(email, activeid)) {
            ActionContext.getContext().getSession().put("islogin", "Y");
            ActionContext.getContext().getSession().put("login_email", email);
            return "active";
        } else {
            return "active";
        }

    }

    public String Logout() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        logger.error("用戶" + ActionContext.getContext().getSession().get("login_email") + "註銷登陸");
        ActionContext.getContext().getSession().remove("login_email");
        ActionContext.getContext().getSession().remove("islogin");
        //ActionContext.getContext().getSession()

        out.print("1");
        return null;
    }

    private String password;

    public void setPassword(String password) {
        this.password = password;
    }

    public String Login() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        if (us.login(email, password)) {
            ActionContext.getContext().getSession().put("userbean", us.getUser(email));
            ActionContext.getContext().getSession().put("islogin", "Y");
            ActionContext.getContext().getSession().put("login_email", email);
            out.print("1");
            return null;
        } else {
            out.print("0");
            return null;
        }

    }

    private int pic_x;
    private int pic_y;
    private int pic_width;
    private int pic_heigth;

    public void setPic_x(int pic_x) {
        this.pic_x = pic_x;
    }

    public void setPic_y(int pic_y) {
        this.pic_y = pic_y;
    }

    public void setPic_width(int pic_width) {
        this.pic_width = pic_width;
    }

    public void setPic_heigth(int pic_heigth) {
        this.pic_heigth = pic_heigth;
    }

    public String DrawImg() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();

        String picName = (String) ActionContext.getContext().getSession().get("userimg");
        if ("".equals(picName) || picName == null) {
            logger.error("用戶没有上传用来裁剪的图片!!");
            out.print("0");
            return null;
        }
        String path_base = ServletActionContext.getServletContext().getRealPath("/");
        //   String path_base = ServletActionContext.getRequest().getRealPath("/") ;
        String inputpath = path_base + "images" + File.separator + "upload" + File.separator + picName;
        String outputfile = path_base + "images" + File.separator + "thumb" + File.separator + picName;
        logger.error("用戶已经上传的图片：" + inputpath + " 的裁剪参数为：(x,y)(" + pic_x + "," + pic_y + "),width=" + pic_width + ",heigth=" + pic_heigth);
        ImgUtil.readUsingImageReader(inputpath, outputfile, new Rectangle(pic_x, pic_y, pic_width, pic_heigth));
        logger.error("调用裁剪程序，裁剪后的图片输出为：" + outputfile);
        out.print(picName);
        return null;
    }

    //参加活动
    private int hdid;

    public void setHdid(int hdid) {
        this.hdid = hdid;
    }

    public String joinHd() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        //判断是否登录
        if (ActionContext.getContext().getSession().get("islogin") == null) {
            //未登录
            out.print("3");
            return null;
        }
        String loginstatus = (String) ActionContext.getContext().getSession().get("islogin");
        if ("".equals(loginstatus) || loginstatus == null) {
            //未登录
            out.print("2");
            return null;
        }
        if ("Y".equals(loginstatus)) {
            String email = (String) ActionContext.getContext().getSession().get("login_email");
            User user_join = (User) ActionContext.getContext().getSession().get("userbean");
            if (us.joinOrnot(user_join.getUser_id(), hdid)) {
                logger.error("用戶" + email + " 参加活动 : " + hdid);
                //将活动休息入库
                UserHd uhd = new UserHd();
                uhd.setHdid(hdid);
                uhd.setUser_id(user_join.getUser_id());
                us.saveUserHd(uhd);
                out.print("1");
                return null;
            } else {
                logger.error("用戶" + email + " 已经参与了活动 : " + hdid);
                out.print("4");
                return null;
            }


        } else {
            out.print("0");
            return null;
        }


    }

    private int friendis;

    public void setFriendis(int friendis) {
        this.friendis = friendis;
    }

    public String addfriends() throws IOException {

        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        if (ActionContext.getContext().getSession().get("islogin") == null) {
            //未登录
            out.print("3");
            return null;
        }
        String loginstatus = (String) ActionContext.getContext().getSession().get("islogin");
        if ("".equals(loginstatus) || loginstatus == null) {
            //未登录
            out.print("2");
            return null;
        }
        if ("Y".equals(loginstatus)) {
            //属于登录用户，将其好友邀请进行入库
            //取出自己的user_id
            int userid = ((User) ActionContext.getContext().getSession().get("userbean")).getUser_id();
            //看看之前是否有加此人为好友的动作发生
            if (us.is_Myfriends(userid, friendis)) {
                out.print("4");
                return null;
            }
            us.saveFriends(userid, friendis);
            out.print("1");
            return null;
        } else {
            out.print("0");
            return null;
        }
    }

    private int user_id;
    private int who_put_tick;

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setWho_put_tick(int who_put_tick) {
        this.who_put_tick = who_put_tick;
    }

    public String tiket() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        //user_id 是得票人的user_id
//          先判断此人10分钟内是否有投票
        if (us.tickTime(user_id, who_put_tick)) {
            out.print("2");
            return null;
        }
        us.tiket(user_id, who_put_tick);
        out.print("1");
        return null;
    }

    private int who_see;       //主动发起关注的孙子
    private int who_be_see;  //被关注的孙子

    public void setWho_see(int who_see) {
        this.who_see = who_see;
    }

    public void setWho_be_see(int who_be_see) {
        this.who_be_see = who_be_see;
    }

    public String usersee() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        String loginstatus = (String) ActionContext.getContext().getSession().get("islogin");
        if ("".equals(loginstatus) || loginstatus == null) {
            //未登录
            out.print("3");
            return null;
        }
        if ("Y".equals(loginstatus)) {
            //        先判断以前是否关注过
            if (us.is_userSee(who_see, who_be_see)) {
                out.print("2");
                return null;
            } else {
                //关注数据入库
                us.saveUserSee(who_see,who_be_see);
                out.print("1");
                return null;
            }
        } else {
            out.print("3");
            return null;
        }
    }
   private String my_short_message_val;

    public void setMy_short_message_val(String my_short_message_val) {
        this.my_short_message_val = my_short_message_val;
    }

    public String publishmessage() throws IOException {
       ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        String loginstatus = (String) ActionContext.getContext().getSession().get("islogin");
        if ("".equals(loginstatus) || loginstatus == null) {
            //未登录
            out.print("3");
            return null;
        }
        if ("Y".equals(loginstatus)) {
            ShortMessage  sm=new ShortMessage();
            sm.setUser_id(user_id);
            sm.setMessagecontent(my_short_message_val);
            us.addShortMessage(sm);
           out.print("1");
            return null;
        } else {
            out.print("3");
            return null;
        }  
    }
    private int mesageid;

    public void setMesageid(int mesageid) {
        this.mesageid = mesageid;
    }

    public String deleteMessage() throws IOException {
     ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        String loginstatus = (String) ActionContext.getContext().getSession().get("islogin");
        if ("".equals(loginstatus) || loginstatus == null) {
            //未登录
            out.print("3");
            return null;
        }
        if ("Y".equals(loginstatus)) {
            us.deletMessage(mesageid);
           out.print("1");
            return null;
        } else {
            out.print("3");
            return null;
        }   
    }
}
