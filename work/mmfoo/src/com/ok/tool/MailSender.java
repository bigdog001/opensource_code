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
       String usr="jiunian.wang@bj.phluency.com";
       String pwd="jiunian.wang";
	//	SimpleEmail email = new SimpleEmail();
         HtmlEmail  email=new HtmlEmail();
		email.setTLS(true);
		email.setHostName("mail.bj.phluency.com");
		email.addTo(to);
		email.setFrom(usr);
		email.setAuthentication(usr, pwd);
		email.setSubject(title);
		email.setCharset("utf-8");
        content=Reader(path_tpl);
        content=content.replace("[email]",to) ;
        content=content.replace("[password]",user_pwd) ;
        content=content.replace("[active]",active) ;
       // content="您好:"+ to +" ! \n\n"+"您的密码是:"+user_pwd+"\n\n 用户名是:"+to  ;
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
