package com.sender.send;

import com.sender.taskbean.MessageBean;
import com.sender.taskbean.configBean;
import com.sender.taskbean.mailSenderTaskBean;
import com.sender.util.ContentUtil;
import com.sender.util.MailAddressValide;
import com.sender.util.updateStartCount;
import org.apache.log4j.Logger;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 10/18/12
 * Time: 6:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class Sender {
    private static Logger logger = Logger.getLogger(Sender.class);
    private Long send_start;
    public static int send_count=1;

    public void send(mailSenderTaskBean mailsenderTaskBean) {
        if (mailsenderTaskBean.getMailTo() == null || mailsenderTaskBean.getMailTo().length < 1) {
            logger.info("在准备发送前检查发现目标地址为空,本次放弃.." + new Date());
            return;
        }


        send_start = System.currentTimeMillis();
        try {
//建立邮件会话
            Properties props = new Properties(); //用来在一个文件中存储键-值对的，其中键和值是用等号分隔的，
//存储发送邮件服务器的信息
            props.put("mail.smtp.host", mailsenderTaskBean.getSmtpHost());
//同时通过验证
            props.put("mail.smtp.auth", "true");
//根据属性新建一个邮件会话
            Session s = Session.getInstance(props);
            s.setDebug(false); //有他会打印一些调试信息。

//由邮件会话新建一个消息对象
            MimeMessage message = new MimeMessage(s);

//设置邮件
            String nick = "";
            try {
                nick = javax.mail.internet.MimeUtility.encodeText(MessageBean.sender_Nickname);
            } catch (UnsupportedEncodingException e) {
                logger.info(e.getMessage());
            }
//            InternetAddress from = new InternetAddress(nick + " <" + mailsenderTaskBean.getMailFrom() + ">");
            InternetAddress from = new InternetAddress( mailsenderTaskBean.getSmtpUsername() );

            message.setFrom(from); //设置发件人的地址
            Address[] addrs = {new InternetAddress(MessageBean.mailReplyTo)};
            message.setReplyTo(addrs);
// //设置收件人,并设置其接收类型为TO
            String[] str_to = mailsenderTaskBean.getMailTo();
            InternetAddress to = new InternetAddress(str_to[0]); //pukeyouxintest3@163.com
            message.addRecipient(Message.RecipientType.TO, to);
//            message.setRecipient(Message.RecipientType.TO, to);
            Address[] bcc_addrs;
            if (str_to.length > 1) {
                bcc_addrs = new InternetAddress[str_to.length - 1];
                for (int x = 0; x < str_to.length - 1; x++) {
                    bcc_addrs[x] = new InternetAddress(str_to[x + 1]);
                }
                message.addRecipients(Message.RecipientType.CC, bcc_addrs);
            }

//            Message.RecipientType.BCC
//设置标题
            message.setSubject(MessageBean.title+" "+ContentUtil.getRandomString(8));

//设置信件内容
// message.setText(str_content); //发送文本邮件 //你好吗？
            message.setContent("<div style='display:none'>"+System.currentTimeMillis()+ ContentUtil.getRandomContent(configBean.contentSize)+"</div><br>"+MessageBean.content+" <br><div style='display:none'>"+System.currentTimeMillis()+ ContentUtil.getRandomContent(configBean.contentSize)+"</div><br>", "text/html;charset=utf8"); //HTML邮件
//设置发信时间
            message.setSentDate(new Date());

//存储邮件信息
            message.saveChanges();

//发送邮件
            Transport transport = s.getTransport("smtp");
//以smtp方式登录邮箱,第一个参数是发送邮件用的邮件服务器SMTP地址,第二个参数为用户名,第三个参数为密码
            transport.connect(mailsenderTaskBean.getSmtpHost(), mailsenderTaskBean.getSmtpUsername(), mailsenderTaskBean.getSmtppassword());
//发送邮件,其中第二个参数是所有已设好的收件人地址
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            updateStartCount.update();  //更新发送起点
            logger.info("本次数据是第" + (send_count++) + "次发送,本次耗时；" + (System.currentTimeMillis() - send_start) + "毫秒!");
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
}
