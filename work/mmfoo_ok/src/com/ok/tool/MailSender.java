package com.ok.tool;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by WangJiuNian.
 * User: Administrator
 * Date: 2011-3-31
 * Time: 15:58:15
 */
public class MailSender {
    private static Logger logger = Logger.getLogger(MailSender.class);

     public static void sendMail_regActive(String to, String user_pwd, String active ,String path_tpl) throws EmailException {
         logger.error("开始向"+to+"发送邮件！！");
         String content="";
       String title="Hi，"+to+"，美分网消息";
       String usr="support@mmfoo.com";
//       String usr="webmaster@jac.com.cn";
       String pwd="guantao724";
//       String pwd="hello123";
	//	SimpleEmail email = new SimpleEmail();
         HtmlEmail  email=new HtmlEmail();
		email.setTLS(true);
		email.setHostName("119.147.74.45");
//		email.setHostName("smtp.exmail.qq.com");
		email.addTo(to);
		email.setFrom(usr);
		email.setAuthentication(usr, pwd);
		email.setSubject(title);
		email.setCharset("utf-8");
        content=Reader(path_tpl);
        content=content.replace("[email]",to) ;
        content=content.replace("[password]",user_pwd) ;
//        content=content.replace("[password]","") ;
        content=content.replace("[active]",active) ;
       // content="您好:"+ to +" ! \n\n"+"您的密码是:"+user_pwd+"\n\n 用户名是:"+to  ;
		email.setHtmlMsg(content);
		email.send();
         logger.error("向"+to+"发送邮件成功结束！！");
	 }
    //发送邮件邀请
    public static void sendMailInvent(String sendto, String tpl_path, String sendfrom) throws EmailException {
         logger.error("开始向"+sendto+"发送邮件！！");
         String content="";
       String title="Hi，"+sendto+"，美分网消息";
       String usr="support@mmfoo.com";
       String pwd="guantao724";
         HtmlEmail  email=new HtmlEmail();
		email.setTLS(true);
		email.setHostName("119.147.74.45");
		email.addTo(sendto);
		email.setFrom(usr);
		email.setAuthentication(usr, pwd);
		email.setSubject(title);
		email.setCharset("utf-8");
        content=Reader(tpl_path);
        content=content.replace("[mailfrom]",sendto) ;     
        content=content.replace("[catogry]",sendfrom) ;
		email.setHtmlMsg(content);
		email.send();
         logger.error("向"+sendto+"发送邮件成功结束！！");
	 }

    public static void sendMail_forgetpwd(String to, String user_pwd) throws EmailException {
         logger.error("开始向"+to+"发送邮件！！");
         String content="";
       String title="Hi，"+to+"，美分网消息";
       String usr="support@mmfoo.com";
       String pwd="guantao724";
         HtmlEmail  email=new HtmlEmail();
		email.setTLS(true);
		email.setHostName("smtp.exmail.qq.com");
		email.addTo(to);
		email.setFrom(usr);
		email.setAuthentication(usr, pwd);
		email.setSubject(title);
		email.setCharset("utf-8");
        content="尊敬的用户："+to+"<br><br>您的新密码是："+user_pwd;
		email.setHtmlMsg(content);
		email.send();
         logger.error("向"+to+"发送邮件成功结束！！");
	 }

    private static String Reader(String path){
        logger.error("从 "+path+" 中读取模板!");
        if("".equals(path)||path==null){
            return null;
        }
         String inf="";
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
              int x = fis.available();
      byte[] data = new byte[x];
      fis.read(data);
        inf = new String(data, "utf-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
     return inf;
    }

}
