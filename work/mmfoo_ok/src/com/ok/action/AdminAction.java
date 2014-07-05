package com.ok.action;

import com.ok.bean.IndexImg;
import com.ok.bean.MmfooHd;
import com.ok.bean.News;
import com.ok.bean.User;
import com.ok.service.AdminService;
import com.ok.service.NewsService;
import com.ok.service.UserService;
import com.ok.tool.Tool;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-6-12
 * Time: 21:46:44
 */
public class AdminAction {
    private AdminService as;
    private UserService us;
    private NewsService ns;
    private static Logger logger = Logger.getLogger(AdminAction.class);

    public void setUs(UserService us) {
        this.us = us;
    }

    public void setNs(NewsService ns) {
        this.ns = ns;
    }

    public void setAs(AdminService as) {
        this.as = as;
    }

    private String email_login;
    private String password;
    private int user_id;

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setEmail_login(String email_login) {
        this.email_login = email_login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        if ("admin".equals(email_login) && "123456".equals(password)) {
            ActionContext.getContext().getSession().put("isadminlogin", "admin_ok");
            out.print("1");
        } else {
            out.print("0");
        }
        return null;
    }

    public String pleasego() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        String status = (String) ActionContext.getContext().getSession().get("isadminlogin");

        if ("admin_ok".equals(status)) {
            us.pleasego(user_id);
            out.print("1");
        } else {
            out.print("0");
        }
        return null;
    }

    public String unpleasego() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        String status = (String) ActionContext.getContext().getSession().get("isadminlogin");

        if ("admin_ok".equals(status)) {
            us.unpleasego(user_id);
            out.print("1");
        } else {
            out.print("0");
        }
        return null;
    }

    public String deleteUser() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        String status = (String) ActionContext.getContext().getSession().get("isadminlogin");

        if ("admin_ok".equals(status)) {
            us.deleteUser(user_id);
            out.print("1");
        } else {
            out.print("0");
        }
        return null;
    }

    private int hdid;

    public void setHdid(int hdid) {
        this.hdid = hdid;
    }

    public String deleteHd() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        String status = (String) ActionContext.getContext().getSession().get("isadminlogin");

        if ("admin_ok".equals(status)) {
            us.deleteHd(hdid);
            out.print("1");
        } else {
            out.print("0");
        }
        return null;
    }

    private String hdname;
    private int orders;
    private String hdtitle;
    private String hdabstract;
    private String hdcontent;
    private int starter_user_id;
    private String payfeed;
    private String jiangxian;
    private Timestamp starttime;
    private Timestamp endtime;
    private String hdface;
    private String hd_thumb;

    public void setHdname(String hdname) {
        this.hdname = hdname;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public void setHdtitle(String hdtitle) {
        this.hdtitle = hdtitle;
    }

    public void setHdabstract(String hdabstract) {
        this.hdabstract = hdabstract;
    }

    public void setHdcontent(String hdcontent) {
        this.hdcontent = hdcontent;
    }

    public void setStarter_user_id(int starter_user_id) {
        this.starter_user_id = starter_user_id;
    }

    public void setPayfeed(String payfeed) {
        this.payfeed = payfeed;
    }

    public void setJiangxian(String jiangxian) {
        this.jiangxian = jiangxian;
    }

    public void setStarttime(Timestamp starttime) {
        this.starttime = starttime;
    }

    public void setEndtime(Timestamp endtime) {
        this.endtime = endtime;
    }

    public void setHdface(String hdface) {
        this.hdface = hdface;
    }

    public void setHd_thumb(String hd_thumb) {
        this.hd_thumb = hd_thumb;
    }

    public String editHd() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        String status = (String) ActionContext.getContext().getSession().get("isadminlogin");

        if ("admin_ok".equals(status)) {
//            构造活动对象
            MmfooHd hds = new MmfooHd();
            hds.setHdid(hdid);
            hds.setHdname(hdname);
            hds.setOrders(orders);
            hds.setHdtitle(hdtitle);
            hds.setHdabstract(hdabstract);
            hds.setHdcontent(hdcontent);
            hds.setStarter_user_id(starter_user_id);
            hds.setPayfeed(payfeed);
            hds.setJiangxian(jiangxian);
            hds.setStarttime(starttime);
            hds.setEndtime(endtime);
            hds.setHdface(hdface);
            hds.setHd_thumb(hd_thumb);
            us.editeMmffhds(hds);
            out.print("1");
        } else {
            out.print("0");
        }
        return null;
    }

    public String addHd() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        String status = (String) ActionContext.getContext().getSession().get("isadminlogin");

        if ("admin_ok".equals(status)) {
            MmfooHd hds = new MmfooHd();
            hds.setHdname(hdname);
            hds.setOrders(orders);
            hds.setHdtitle(hdtitle);
            hds.setHdabstract(hdabstract);
            hds.setHdcontent(hdcontent);
            hds.setStarter_user_id(starter_user_id);
            hds.setPayfeed(payfeed);
            hds.setJiangxian(jiangxian);
            hds.setStarttime(starttime);
            hds.setEndtime(endtime);
            hds.setHdface(hdface);
            hds.setHd_thumb(hd_thumb);
            us.addMmffhds(hds);
            out.print("1");
        } else {
            out.print("0");
        }
        return null;
    }

    private int imgid;

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }

    public String deleteSlide() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        String status = (String) ActionContext.getContext().getSession().get("isadminlogin");

        if ("admin_ok".equals(status)) {
            as.deleteIndexImg(imgid);
            out.print("1");
        } else {
            out.print("0");
        }
        return null;
    }

    private int oedds;
    private String img;
    private String thumb;
    private String truename;
    private String city;
    private String star;
    private String target_url;

    public void setOedds(int oedds) {
        this.oedds = oedds;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public void setTarget_url(String target_url) {
        this.target_url = target_url;
    }

    public String editeindeximg() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        String status = (String) ActionContext.getContext().getSession().get("isadminlogin");

        if ("admin_ok".equals(status)) {
            IndexImg ii = new IndexImg();
            ii.setImgid(imgid);
            ii.setOedds(oedds);
            ii.setImg(img);
            ii.setThumb(thumb);
            ii.setTruename(truename);
            ii.setCity(city);
            ii.setStar(star);
            ii.setTarget_url(target_url);
            as.editeImdeImg(ii);
            out.print("1");
        } else {
            out.print("0");
        }
        return null;
    }

    public String addindeximg() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        String status = (String) ActionContext.getContext().getSession().get("isadminlogin");

        if ("admin_ok".equals(status)) {
            IndexImg ii = new IndexImg();
            ii.setOedds(oedds);
            ii.setImg(img);
            ii.setThumb(thumb);
            ii.setTruename(truename);
            ii.setCity(city);
            ii.setStar(star);
            ii.setTarget_url(target_url);
            as.addIndexImg(ii);
            out.print("1");
        } else {
            out.print("0");
        }
        return null;
    }

    private int newsid;
    private String newstitle;
    private String newscontent;

    public void setNewsid(int newsid) {
        this.newsid = newsid;
    }

    public void setNewstitle(String newstitle) {
        this.newstitle = newstitle;
    }

    public void setNewscontent(String newscontent) {
        this.newscontent = newscontent;
    }

    public String updateNews() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        String status = (String) ActionContext.getContext().getSession().get("isadminlogin");

        if ("admin_ok".equals(status)) {
            News ii = new News();
            ii.setTitle(newstitle);
            ii.setNewsid(newsid);
            ii.setContent(newscontent);
            ns.updateNews(ii);
            out.print("1");
        } else {
            out.print("0");
        }
        return null;
    }

    private String email;

    public void setEmail(String email) {
        this.email = email;
    }

    public String forgetpwd() throws IOException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
//        String status = (String) ActionContext.getContext().getSession().get("isadminlogin");
        as.forgetPwd(email);
        out.print("1");

        return null;
    }

    private String email_invent_targets;

    public void setEmail_invent_targets(String email_invent_targets) {
        this.email_invent_targets = email_invent_targets;
    }

    public String sendEmailInvent() throws IOException, EmailException {
        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
//        做登录状态检测
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

//        对email的格式和数目进行处理
            //切出email地址元素
            if ("".equals(email_invent_targets) || email_invent_targets == null) {
                logger.error("输入的email地址列为空");
                out.print("0");
                return null;
            }
            String[] targets = email_invent_targets.split(",");
            if (targets == null || targets.length < 1) {
                logger.error("输入的email地址列为空");
                out.print("0");
                return null;
            }
            if ("".equals(email_invent_targets) || email_invent_targets == null) {
                logger.error("输入的email地址列为空");
                out.print("0");
                return null;
            }
//            从前台提交的email地址列表中取出地址做邮件传送
            User user_join = (User) ActionContext.getContext().getSession().get("userbean");
             String path_base = ServletActionContext.getServletContext().getRealPath("/");
            path_base=path_base+"WEB-INF"+File.separator+"tpl"+ File.separator+"invite_EMAIL.html";
            for(String email_to:targets){
                 //做地址格式分析
                if(!Tool.checkEmail(email_to)){
                    logger.error("输入的email地址:"+email_to+" 格式不合法");
                } else{
                    as.sendEmailInvent(email_to,path_base,user_join);
                     logger.error("向email:"+email_to+"发送邀请涵成功");
                }
            }

            out.print("1");

            return null;
        } else {
            out.print("0");
            logger.error("用户未登录");
            return null;
        }

    }
}
