package com.ok.action;

import com.ok.bean.*;
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
import java.awt.List;
import java.io.*;
import java.sql.Timestamp;
import java.util.*;

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

    private String nickname;

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String SaveUser() throws IOException {

        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        String code = "";// (String);
        String path_base = ServletActionContext.getServletContext().getRealPath("/");
        String path_tpl = path_base + "WEB-INF" + File.separator + "tpl" + File.separator + "ConfirmMail.html";

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
            us.saveUser_reg(email, path_tpl, password, nickname);
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
            logger.error("---0000000");
            ActionContext.getContext().getSession().put("islogin", "Y");
            ActionContext.getContext().getSession().put("login_email", email);
            return "active";
        } else {
            logger.error("---1111111");
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
        ActionContext.getContext().getSession().remove("userbean");
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
            out.print(us.getUser(email).getUser_id());
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

        String picName = (String) ActionContext.getContext().getSession().get("userimg_upload");
        if ("".equals(picName) || picName == null) {
            logger.error("用戶没有上传用来裁剪的图片!!" + picName);
            out.print("0");
            return null;
        }
//        判断用户是否登 是登陆用户的话讲起所传的图片更新到库记录中
        String loginstatus = (String) ActionContext.getContext().getSession().get("islogin");
        if (loginstatus == null) {
            //未登录
            out.print("3");
            return null;
        }
        if ("Y".equals(loginstatus)) {
            String path_base = ServletActionContext.getServletContext().getRealPath("/");
            //   String path_base = ServletActionContext.getRequest().getRealPath("/") ;
            String inputpath = path_base + "images" + File.separator + "upload" + File.separator + picName;
            String bigimgpath = path_base + "images" + File.separator + picName;
            String outputfile = path_base + "images" + File.separator + "thumb" + File.separator + picName;
            logger.error("用戶已经上传的图片：" + inputpath + " 的裁剪参数为：(x,y)(" + pic_x + "," + pic_y + "),width=" + pic_width + ",heigth=" + pic_heigth);
            //根据前台提交的坐标做的制作不准确
            // ImgUtil.readUsingImageReader(inputpath, outputfile, new Rectangle(pic_x, pic_y, pic_width, pic_heigth));
            //制作用户的bigimg
//            ImgUtil.readUsingImageReader(inputpath, outputfile, new Rectangle(pic_x, pic_y, pic_width, pic_heigth));
            try {
//                ImgUtil.saveImageAsJpg(inputpath,bigimgpath,160,170);
                ImgUtil.readUsingImageReader(inputpath, bigimgpath, new Rectangle(pic_x, pic_y, pic_width, pic_heigth));
            } catch (Exception e) {
                logger.error("制作用户的bigimg时发生异常:" + e.getMessage());
            }
            try {
                //制作用户的thumb
                ImgUtil.saveImageAsJpg(inputpath, outputfile, 45, 52);
            } catch (Exception e) {
                logger.error("制作用户的thumb时发生异常:" + e.getMessage());
            }
            User user = (User) ActionContext.getContext().getSession().get("userbean");
            us.updateImg(user.getUser_id(), picName);
//        ImgUtil.readUsingImageReader(inputpath, outputfile, new Rectangle(pic_x, pic_y, pic_width, pic_heigth));
            logger.error("调用裁剪程序，裁剪后的图片输出为：" + outputfile);
            out.print("1");
            return null;
        } else {
            out.print("0");
            return null;
        }


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
//                判断是否被对方拉黑
                if (us.is_my_backer(who_be_see, who_see)) {
                    out.print("4");
                    return null;
                } else {
                    //关注数据入库
                    us.saveUserSee(who_see, who_be_see);
                    out.print("1");
                    return null;
                }
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
            ShortMessage sm = new ShortMessage();
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

    public String uploadset() throws IOException {
        return "upload";
    }

    private int setid;

    public void setSetid(int setid) {
        this.setid = setid;
    }

    java.util.List<String> pic_tmp;

    public String archiveimg() throws IOException {
        //            从内存中找出已经上传的队列

//           System.out.println(ActionContext.getContext().getSession().get("pictruesupload"));
//           System.out.println(ActionContext.getContext().getSession().get("islogin"));
        // pic_tmp = (java.util.List<String>)  ActionContext.getContext().getSession().get("pictruesupload");
        if (pic_tmp == null) {
            pic_tmp = new ArrayList<String>();
        }

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


            if (pic_tmp == null) {
                out.print("4");
                return null;
            }
            if (pic_tmp.size() < 1) {
                out.print("4");
                return null;
            }
            //将用户上传的图片进行归档 并为每张图生成缩略图
            String path_base = ServletActionContext.getServletContext().getRealPath("/");
//            System.out.println("做缩略图:"+pic_tmp+","+path_base+",setid"+setid);
            us.archiveImg(setid, pic_tmp, path_base);
            out.print("1");
            return null;
        } else {
            out.print("3");
            return null;
        }
    }

    //文件上传----------------------

    private File file;
    private String fileFileName;
    private String fileContentType;


    public void setFile(File file) {
        this.file = file;
    }

    public String getFileFileName() {
        return this.fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public String getFileContentType() {
        return this.fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public String FileUpload() throws Exception {
        System.out.println("----进入新的文件上传程序--");

        try {
            String myFileDir = File.separator + "54zzb";

            File dir = new File(ServletActionContext.getRequest().getRealPath("/images/upload") + myFileDir);
            if (!dir.mkdir())
                logger.error(myFileDir + "目录存在");
            else {
                dir.mkdir();
            }
            InputStream is = new FileInputStream(this.file);

            String root = ServletActionContext.getRequest().getRealPath("/images/upload") + myFileDir;

            String newFileName = String.valueOf(System.currentTimeMillis() + getFileFileName());
            Object ttt = ActionContext.getContext().getSession().get("pictruesupload");

            if (ttt == null) {
                ttt = new ArrayList<String>();
            }
            pic_tmp = (java.util.List<String>) ttt;
//                ActionContext.getContext().getSession().put("userbean", us.getUser(email));
            pic_tmp.add(newFileName);
            ActionContext.getContext().getSession().put("pictruesupload", pic_tmp);
            System.out.println(((java.util.List<String>) ActionContext.getContext().getSession().get("pictruesupload")).size() + "------");
            File deskFile = new File(root, newFileName);
            OutputStream os = new FileOutputStream(deskFile);
            byte[] bytefer = new byte[400];
            int length = 0;
            while ((length = is.read(bytefer)) > 0) {
                os.write(bytefer, 0, length);
            }
            logger.error(newFileName + "文件的大小为：" + deskFile.length());
            os.close();
            is.close();
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "input";
    }

    //----------------------文件上传

    private String setname;
    private String setdiscribe;
//   private int user_id;

    public void setSetname(String setname) {
        this.setname = setname;
    }

    public void setSetdiscribe(String setdiscribe) {
        this.setdiscribe = setdiscribe;
    }

    public String addImgSet() throws IOException {
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
            ImgSet uimg = new ImgSet();
//            uimg.setSetid(setid);
            uimg.setUser_id(user_id);
            uimg.setSetname(setname);
            uimg.setSetdiscribe(setdiscribe);
            us.addImgSet(uimg);
//            us.archiveImg(setid, pic_tmp, path_base);
            out.print("1");
            return null;
        } else {
            out.print("3");
            return null;
        }
    }

    public String loveit() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        String loginstatus = (String) ActionContext.getContext().getSession().get("islogin");
        us.loveimgset(setid);
        out.print("1");
        return null;

    }

    public String deleteImgSet() throws IOException {
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
            us.deleteImgSet(setid);
            out.print("1");
            return null;
        } else {
            out.print("3");
            return null;
        }
    }

    public String saveImgSetedite() throws IOException {
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
            ImgSet is = new ImgSet();
            is.setSetid(setid);
            is.setSetname(setname);
            is.setSetdiscribe(setdiscribe);
            us.updateImgSet(is);
            out.print("1");
            return null;
        } else {
            out.print("3");
            return null;
        }
    }

    private String user_email_to;
    private int user_email_to_re;
    private String user_msg;
    private int user_id_from;

    public void setUser_email_to_re(int user_email_to_re) {
        this.user_email_to_re = user_email_to_re;
    }

    public void setUser_id_from(int user_id_from) {
        this.user_id_from = user_id_from;
    }

    public void setUser_msg(String user_msg) {
        this.user_msg = user_msg;
    }

    public void setUser_email_to(String user_email_to) {
        this.user_email_to = user_email_to;
    }

    //    根据email查询此用户的id，判断用户是否存在

    public String savepm() throws IOException {
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
            PrivateMessage is = new PrivateMessage();
            //根据email查询用户id
            is.setUser_id_to(us.getUser_idByEamil(user_email_to));
            is.setUser_id_from(user_id_from);
            is.setPm_content(user_msg);
            us.savepm(is);
            out.print("1");
            return null;
        } else {
            out.print("3");
            return null;
        }
    }
    //回复用户的私信

    public String saverepmmsg() throws IOException {
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
            PrivateMessage is = new PrivateMessage();
            //根据email查询用户id
            is.setUser_id_to(user_email_to_re);
            is.setUser_id_from(user_id_from);
            is.setPm_content(user_msg);
            is.setPm_return(pmid);
            us.savepm(is);
            out.print("1");
            return null;
        } else {
            out.print("3");
            return null;
        }
    }
    //删除我所有的私信

    public String deletmypm() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        User user = (User) ActionContext.getContext().getSession().get("userbean");
        String loginstatus = (String) ActionContext.getContext().getSession().get("islogin");
        if ("".equals(loginstatus) || loginstatus == null) {
            //未登录
            out.print("3");
            return null;
        }
        if ("Y".equals(loginstatus)) {
            //判断登录用户是否是空间主人
            if (user.getUser_id() == user_id) {
                us.delete_myprivateMessage(user_id);
                out.print("1");
                return null;
            } else {
                out.print("4");
                return null;
            }

        } else {
            out.print("3");
            return null;
        }
    }

    private int pmid;

    public void setPmid(int pmid) {
        this.pmid = pmid;
    }

    public String deletpmsingle() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        User user = (User) ActionContext.getContext().getSession().get("userbean");
        String loginstatus = (String) ActionContext.getContext().getSession().get("islogin");
        if ("".equals(loginstatus) || loginstatus == null) {
            //未登录
            out.print("3");
            return null;
        }
        if ("Y".equals(loginstatus)) {
            //判断登录用户是否是空间主人
            if (user.getUser_id() == user_id) {
                us.delete_privateMessage(pmid);
                out.print("1");
                return null;
            } else {
                out.print("4");
                return null;
            }

        } else {
            out.print("3");
            return null;
        }
    }

    private int my_user_id;
    private int duifang_user_id;

    public void setMy_user_id(int my_user_id) {
        this.my_user_id = my_user_id;
    }

    public void setDuifang_user_id(int duifang_user_id) {
        this.duifang_user_id = duifang_user_id;
    }

    public String unsee() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        User user = (User) ActionContext.getContext().getSession().get("userbean");
        String loginstatus = (String) ActionContext.getContext().getSession().get("islogin");
        if ("".equals(loginstatus) || loginstatus == null) {
            //未登录
            out.print("3");
            return null;
        }
        if ("Y".equals(loginstatus)) {
            //判断登录用户是否是空间主人
            if (user.getUser_id() == my_user_id) {
                us.unSee(my_user_id, duifang_user_id);
                out.print("1");
                return null;
            } else {
                out.print("4");
                return null;
            }

        } else {
            out.print("3");
            return null;
        }
    }

    private int blacker_user_id;

    public void setBlacker_user_id(int blacker_user_id) {
        this.blacker_user_id = blacker_user_id;
    }

    public String addBlacker() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        User user = (User) ActionContext.getContext().getSession().get("userbean");
        String loginstatus = (String) ActionContext.getContext().getSession().get("islogin");
        if ("".equals(loginstatus) || loginstatus == null) {
            //未登录
            out.print("3");
            return null;
        }
        if ("Y".equals(loginstatus)) {
            //判断登录用户是否是空间主人
            if (user.getUser_id() == my_user_id) {
                us.addBlacker(my_user_id, blacker_user_id);
                out.print("1");
                return null;
            } else {
                out.print("4");
                return null;
            }

        } else {
            out.print("3");
            return null;
        }
    }

    //保存我的基础信息
    private String jichu;
    private String jinli;
    private String jiaoyu;
    private String lianxi;
    private String aihao;

    public void setJinli(String jinli) {
        this.jinli = jinli;
    }

    public void setJiaoyu(String jiaoyu) {
        this.jiaoyu = jiaoyu;
    }

    public void setLianxi(String lianxi) {
        this.lianxi = lianxi;
    }

    public void setAihao(String aihao) {
        this.aihao = aihao;
    }

    public void setJichu(String jichu) {
        this.jichu = jichu;
    }

    public String savejichu() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        User user = (User) ActionContext.getContext().getSession().get("userbean");
        String loginstatus = (String) ActionContext.getContext().getSession().get("islogin");
        if ("".equals(loginstatus) || loginstatus == null) {
            //未登录
            out.print("3");
            return null;
        }
        if ("Y".equals(loginstatus)) {
            //判断登录用户是否是空间主人
            if (user.getUser_id() == my_user_id) {
                us.savejichu(my_user_id, jichu);
                out.print("1");
                return null;
            } else {
                out.print("4");
                return null;
            }

        } else {
            out.print("3");
            return null;
        }
    }


    public String jinli() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        User user = (User) ActionContext.getContext().getSession().get("userbean");
        String loginstatus = (String) ActionContext.getContext().getSession().get("islogin");
        if ("".equals(loginstatus) || loginstatus == null) {
            //未登录
            out.print("3");
            return null;
        }
        if ("Y".equals(loginstatus)) {
            //判断登录用户是否是空间主人
            if (user.getUser_id() == my_user_id) {
                us.saveJinLi(my_user_id, jinli);
                out.print("1");
                return null;
            } else {
                out.print("4");
                return null;
            }

        } else {
            out.print("3");
            return null;
        }
    }

    public String peixun() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        User user = (User) ActionContext.getContext().getSession().get("userbean");
        String loginstatus = (String) ActionContext.getContext().getSession().get("islogin");
        if ("".equals(loginstatus) || loginstatus == null) {
            //未登录
            out.print("3");
            return null;
        }
        if ("Y".equals(loginstatus)) {
            //判断登录用户是否是空间主人
            if (user.getUser_id() == my_user_id) {
                us.savePeixun(my_user_id, jiaoyu);
                out.print("1");
                return null;
            } else {
                out.print("4");
                return null;
            }

        } else {
            out.print("3");
            return null;
        }
    }

    public String lianxi() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        User user = (User) ActionContext.getContext().getSession().get("userbean");
        String loginstatus = (String) ActionContext.getContext().getSession().get("islogin");
        if ("".equals(loginstatus) || loginstatus == null) {
            //未登录
            out.print("3");
            return null;
        }
        if ("Y".equals(loginstatus)) {
            //判断登录用户是否是空间主人
            if (user.getUser_id() == my_user_id) {
                us.saveJLianxiFangshi(my_user_id, lianxi);
                out.print("1");
                return null;
            } else {
                out.print("4");
                return null;
            }

        } else {
            out.print("3");
            return null;
        }
    }

    public String aihao() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        User user = (User) ActionContext.getContext().getSession().get("userbean");
        String loginstatus = (String) ActionContext.getContext().getSession().get("islogin");
        if ("".equals(loginstatus) || loginstatus == null) {
            //未登录
            out.print("3");
            return null;
        }
        if ("Y".equals(loginstatus)) {
            //判断登录用户是否是空间主人
            if (user.getUser_id() == my_user_id) {
                us.saveAihao(my_user_id, aihao);
                out.print("1");
                return null;
            } else {
                out.print("4");
                return null;
            }

        } else {
            out.print("3");
            return null;
        }
    }

    public String interest() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        User user = (User) ActionContext.getContext().getSession().get("userbean");
        String loginstatus = (String) ActionContext.getContext().getSession().get("islogin");
        if ("".equals(loginstatus) || loginstatus == null) {
            //未登录
            out.print("3");
            return null;
        }
        if ("Y".equals(loginstatus)) {
            //判断登录用户是否是空间主人
            us.interestForHd(hdid);
            out.print("1");
            return null;
        } else {
            out.print("3");
            return null;
        }
    }

    private int user_becomment;
    private String content;

    public void setContent(String content) {
        this.content = content;
    }

    public void setUser_becomment(int user_becomment) {
        this.user_becomment = user_becomment;
    }

    public String comment() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        User user = (User) ActionContext.getContext().getSession().get("userbean");
        String loginstatus = (String) ActionContext.getContext().getSession().get("islogin");
        if ("".equals(loginstatus) || loginstatus == null) {
            //未登录
            out.print("0");
            return null;
        }
        if ("Y".equals(loginstatus)) {
            Comment c = new Comment();
            c.setUserid(user_id);
            c.setSetid(setid);
            c.setContent(content);
            c.setUser_set_onwer(user_becomment);
            us.saveComment(c);
            out.print("1");
            return null;
        } else {
            out.print("3");
            return null;
        }
    }

    private String truename;

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String UserSearche() throws IOException {
        ActionContext.getContext().getSession().put("searchs", us.getUserSearche(truename));
        return "search";
    }


    //编辑个人档案-------------------------------------
    private String msn;
    private String phone;
    private String qqnumber;

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setQqnumber(String qqnumber) {
        this.qqnumber = qqnumber;
    }

    public String UpdateLianluoXinxi() throws IOException {  //联络信息
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        User user = (User) ActionContext.getContext().getSession().get("userbean");
        String loginstatus = (String) ActionContext.getContext().getSession().get("islogin");
        if ("".equals(loginstatus) || loginstatus == null) {
            //未登录
            out.print("0");
            return null;
        }
        if ("Y".equals(loginstatus)) {
            //从session中取当前的登录用户id
            User users = new User();
            users.setUser_id(user.getUser_id());
            users.setEmail(email);
            users.setQqnumber(qqnumber);
            users.setMobile(phone);
            users.setUser_msn(msn);
             users.setNickname(nickname);
            us.UpdateLianluoXinxi(users);
            out.print("1");
            return null;
        } else {
            out.print("3");
            return null;
        }
    }


    private String lovemusic;//    喜欢的音乐
    private String lovestar;//    喜欢的明星
    private String lovemovie;//    喜欢的电影
    private String lovetv;//    喜欢的电视
    private String lovesport;//    喜欢的运动
    private String lovereading;//    喜欢看的书

    public void setLovereading(String lovereading) {
        this.lovereading = lovereading;
    }

    public void setLovesport(String lovesport) {
        this.lovesport = lovesport;
    }

    public void setLovetv(String lovetv) {
        this.lovetv = lovetv;
    }

    public void setLovemovie(String lovemovie) {
        this.lovemovie = lovemovie;
    }

    public void setLovestar(String lovestar) {
        this.lovestar = lovestar;
    }

    public void setLovemusic(String lovemusic) {
        this.lovemusic = lovemusic;
    }

    public String Updateaihao() throws IOException {   //爱好
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        User user = (User) ActionContext.getContext().getSession().get("userbean");
        String loginstatus = (String) ActionContext.getContext().getSession().get("islogin");
        if ("".equals(loginstatus) || loginstatus == null) {
            //未登录
            out.print("0");
            return null;
        }
        if ("Y".equals(loginstatus)) {
            //从session中取当前的登录用户id
            User users = new User();
            users.setLovemovie(lovemovie);
            users.setLovemusic(lovemusic);
            users.setLovestar(lovestar);
            users.setLovetv(lovetv);
            users.setLovesport(lovesport);
            users.setLovereading(lovereading);
            users.setUser_id(user.getUser_id());
            us.Updateaihao(users);
            out.print("1");
            return null;
        } else {
            out.print("3");
            return null;
        }
    }

    public String UpdateJingli() throws IOException {     //经历描述
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        User user = (User) ActionContext.getContext().getSession().get("userbean");
        String loginstatus = (String) ActionContext.getContext().getSession().get("islogin");
        if ("".equals(loginstatus) || loginstatus == null) {
            //未登录
            out.print("0");
            return null;
        }
        if ("Y".equals(loginstatus)) {
            //从session中取当前的登录用户id
            User users = new User();
            users.setJinli(jinli);
            users.setUser_id(user.getUser_id());
            us.UpdateJingli(users);
            out.print("1");
            return null;
        } else {
            out.print("3");
            return null;
        }
    }

    private String zuopin;

    public void setZuopin(String zuopin) {
        this.zuopin = zuopin;
    }
// 作品

    public String UpdateZuopin() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        User user = (User) ActionContext.getContext().getSession().get("userbean");
        String loginstatus = (String) ActionContext.getContext().getSession().get("islogin");
        if ("".equals(loginstatus) || loginstatus == null) {
            //未登录
            out.print("0");
            return null;
        }
        if ("Y".equals(loginstatus)) {
            //从session中取当前的登录用户id
            User users = new User();
            users.setZuopin(zuopin);
            users.setUser_id(user.getUser_id());
            us.UpdateZuopin(users);
            out.print("1");
            return null;
        } else {
            out.print("3");
            return null;
        }
    }

    private String zhanlan;//展览

    public void setZhanlan(String zhanlan) {
        this.zhanlan = zhanlan;
    }
    //  展览

    public String UpdateZhanlan() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        User user = (User) ActionContext.getContext().getSession().get("userbean");
        String loginstatus = (String) ActionContext.getContext().getSession().get("islogin");
        if ("".equals(loginstatus) || loginstatus == null) {
            //未登录
            out.print("0");
            return null;
        }
        if ("Y".equals(loginstatus)) {
            //从session中取当前的登录用户id
            User users = new User();
            users.setZhanlan(zhanlan);
            users.setUser_id(user.getUser_id());
            us.UpdateZhanlan(users);
            out.print("1");
            return null;
        } else {
            out.print("3");
            return null;
        }
    }

    private String meiticaifang;//没体采访

    public void setMeiticaifang(String meiticaifang) {
        this.meiticaifang = meiticaifang;
    }
    //   媒体采访

    public String UpdateMeiticaifang() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        User user = (User) ActionContext.getContext().getSession().get("userbean");
        String loginstatus = (String) ActionContext.getContext().getSession().get("islogin");
        if ("".equals(loginstatus) || loginstatus == null) {
            //未登录
            out.print("0");
            return null;
        }
        if ("Y".equals(loginstatus)) {
            //从session中取当前的登录用户id
            User users = new User();
            users.setMeiticaifang(meiticaifang);
            users.setUser_id(user.getUser_id());
            us.UpdateCaifang(users);
            out.print("1");
            return null;
        } else {
            out.print("3");
            return null;
        }
    }
//  出席活动
    private String chuxihuodong;//出席活动

    public void setChuxihuodong(String chuxihuodong) {
        this.chuxihuodong = chuxihuodong;
    }

    public String UpdateChuxihuodong() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        User user = (User) ActionContext.getContext().getSession().get("userbean");
        String loginstatus = (String) ActionContext.getContext().getSession().get("islogin");
        if ("".equals(loginstatus) || loginstatus == null) {
            //未登录
            out.print("0");
            return null;
        }
        if ("Y".equals(loginstatus)) {
            //从session中取当前的登录用户id
            User users = new User();
            users.setChuxihuodong(chuxihuodong);
            users.setUser_id(user.getUser_id());
            us.UpdateChuxihuodong(users);
            out.print("1");
            return null;
        } else {
            out.print("3");
            return null;
        }
    }
//  培训
    private String peixun;//培训

    public void setPeixun(String peixun) {
        this.peixun = peixun;
    }

    public String UpdatePeixun() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        User user = (User) ActionContext.getContext().getSession().get("userbean");
        String loginstatus = (String) ActionContext.getContext().getSession().get("islogin");
        if ("".equals(loginstatus) || loginstatus == null) {
            //未登录
            out.print("0");
            return null;
        }
        if ("Y".equals(loginstatus)) {
            //从session中取当前的登录用户id
            User users = new User();
            users.setPeixun(peixun);
            users.setUser_id(user.getUser_id());
            us.UpdatePeixun(users);
            out.print("1");
            return null;
        } else {
            out.print("3");
            return null;
        }
    }
//  获奖
    private String huojiang;//获奖

    public void setHuojiang(String huojiang) {
        this.huojiang = huojiang;
    }

    public String UpdateHuojiang() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        User user = (User) ActionContext.getContext().getSession().get("userbean");
        String loginstatus = (String) ActionContext.getContext().getSession().get("islogin");
        if ("".equals(loginstatus) || loginstatus == null) {
            //未登录
            out.print("0");
            return null;
        }
        if ("Y".equals(loginstatus)) {
            //从session中取当前的登录用户id
            User users = new User();
            users.setHuojiang(huojiang);
            users.setUser_id(user.getUser_id());
            us.UpdateHuojiang(users);
            out.print("1");
            return null;
        } else {
            out.print("3");
            return null;
        }
    }
//   学历
    private String education;//学历

    public void setEducation(String education) {
        this.education = education;
    }

    public String UpdateEducation() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        User user = (User) ActionContext.getContext().getSession().get("userbean");
        String loginstatus = (String) ActionContext.getContext().getSession().get("islogin");
        if ("".equals(loginstatus) || loginstatus == null) {
            //未登录
            out.print("0");
            return null;
        }
        if ("Y".equals(loginstatus)) {
            //从session中取当前的登录用户id
            User users = new User();
            users.setEducation(education);
            users.setUser_id(user.getUser_id());
            us.UpdateXueli(users);
            out.print("1");
            return null;
        } else {
            out.print("3");
            return null;
        }
    }

    private Timestamp birthday;
    private String user_star;
    private String worspace;
    private String sex;
    private String hometown;
    private String blood;
    private String height;
    private String heavy;

    public void setHeavy(String heavy) {
        this.heavy = heavy;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setWorspace(String worspace) {
        this.worspace = worspace;
    }

    public void setUser_star(String user_star) {
        this.user_star = user_star;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    public String SaveBaseInfor() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        User user = (User) ActionContext.getContext().getSession().get("userbean");
        String loginstatus = (String) ActionContext.getContext().getSession().get("islogin");
        if ("".equals(loginstatus) || loginstatus == null) {
            //未登录
            out.print("0");
            return null;
        }
        if ("Y".equals(loginstatus)) {
            //从session中取当前的登录用户id
            User users = new User();
            users.setBirthday(birthday);
            users.setUser_star(user_star);
            users.setWorspace(worspace);
            users.setSex(sex);
            users.setHometown(hometown);
            users.setBlood(blood);
            users.setHeight(height);
            users.setHeavy(heavy);
            users.setUser_id(user.getUser_id());
            us.SaveBaseInfor(users);
            out.print("1");
            return null;
        } else {
            out.print("3");
            return null;
        }
    }
    //-------------------------------------编辑个人档案

}
