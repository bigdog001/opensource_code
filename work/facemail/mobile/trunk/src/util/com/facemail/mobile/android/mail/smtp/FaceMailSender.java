package com.facemail.mobile.android.mail.smtp;

import com.facemail.mobile.android.db.bean.UserAcount;
import com.facemail.mobile.android.mail.IMailRequest;
import com.facemail.mobile.android.util.SystemUtil;

import java.util.Date;
import java.util.Properties;
import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 4/24/13
 * Time: 2:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class FaceMailSender extends javax.mail.Authenticator {

    private String _user;
    private String _pass;

    private String[] _to;
    private String _from;

    private String _port;
    private String _sport;

    private String _host_smtp;

    private String _subject;
    private String _body;

    private boolean _auth;

    private boolean _debuggable;

    private Multipart _multipart;


    public FaceMailSender() {
        _debuggable = false; // debug mode on or off - default off
        _multipart = new MimeMultipart();

        // There is something wrong with MailCap, javamail can not find a handler for the multipart/mixed part, so this bit needs to be added.
        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        CommandMap.setDefaultCommandMap(mc);
    }

    public void set_to(String[] _to) {
        this._to = _to;
    }


    public boolean send() throws Exception {
        Properties props = _setProperties();

        if(!_user.equals("") && !_pass.equals("") && _to.length > 0 && !_from.equals("") && !_subject.equals("") && !_body.equals("")) {
            Session session = Session.getInstance(props, this);

            MimeMessage msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(_from));

            InternetAddress[] addressTo = new InternetAddress[_to.length];
            for (int i = 0; i < _to.length; i++) {
                addressTo[i] = new InternetAddress(_to[i]);
            }
            msg.setRecipients(MimeMessage.RecipientType.TO, addressTo);

            msg.setSubject(_subject);
            msg.setSentDate(new Date());
            // setup message body
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(_body);
            _multipart.addBodyPart(messageBodyPart);

            // Put parts in message
            msg.setContent(_multipart);

            // send email
            Transport.send(msg);

            return true;
        } else {
            return false;
        }
    }

    public void addAttachment(String filename) throws Exception {
        BodyPart messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(filename);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);

        _multipart.addBodyPart(messageBodyPart);
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(_user, _pass);
    }

    private Properties _setProperties() {
        Properties props = new Properties();

        props.put("mail.smtp.host", _host_smtp);

        if(_debuggable) {
            props.put("mail.debug", "true");
        }

        if(_auth) {
            props.put("mail.smtp.auth", "true");
        }

        props.put("mail.smtp.port", _port);
        props.put("mail.smtp.socketFactory.port", _sport);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        return props;
    }

    // the getters and setters
    public String getBody() {
        return _body;
    }

    public void setBody(String _body) {
        this._body = _body;
    }

    public boolean checkMail(IMailRequest mailRequest) {
        boolean result=false;
        String [] toArr=mailRequest.getFaceMailMessage().getTo().split(",");
        if (toArr==null||toArr.length==0)return result;
        if (toArr!=null&&toArr.length>0) {
            for (String t:toArr) {
                if (!SystemUtil.isEmail(t)) {
                    SystemUtil.log("邮件地址: "+t+" 的值非法,非邮件格式!");
                    return result;
                }
            }
        }
        set_to(toArr);
        if ("".equals(mailRequest.getFaceMailMessage().getMail_from())||mailRequest.getFaceMailMessage().getMail_from()==null) {
            SystemUtil.log("from地址的值null!");
            return result;
        }
        if (!SystemUtil.isEmail(mailRequest.getFaceMailMessage().getMail_from().trim())) {
            SystemUtil.log("from地址的值非法!");
            return result;
        }
        /**
         *_user;_pass; [] _to;_from;_port;_sport;_host_smtp;_subject;  _body;
         */
        UserAcount ua_tmp=mailRequest.getUserAccount();
        _user=ua_tmp.getUser_name();
        _pass=ua_tmp.getUser_pwd() ;
        _to=mailRequest.getFaceMailMessage().getTo().split(",");
        _from=mailRequest.getFaceMailMessage().getMail_from();
        _port=ua_tmp.getUser_port_smtp()+"";
        _sport="465";
        _host_smtp=ua_tmp.getUser_smtp();
        _subject=mailRequest.getFaceMailMessage().getMail_title();
        _body=mailRequest.getFaceMailMessage().getMail_content();
        _auth = true;
        result=true;
        return  result;
    }

    // more of the getters and setters …..
    //send
/*
            Mail m = new Mail("gmailusername@gmail.com", "password");

            String[] toArr = {"bla@bla.com", "lala@lala.com"};
            m.setTo(toArr);
            m.setFrom("wooo@wooo.com");
            m.setSubject("This is an email sent using my Mail JavaMail wrapper from an Android device.");
            m.setBody("Email body.");
            try {
                m.addAttachment("/sdcard/filelocation");

                if(m.send()) {
                    Toast.makeText(MailApp.this, "Email was sent successfully.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MailApp.this, "Email was not sent.", Toast.LENGTH_LONG).show();
                }
            } catch(Exception e) {
                //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                SystemUtil.log(Could not send email"+e.getMessage());
            }



            receive:

            SharedPreferences pre = getSharedPreferences(SAVE_INFORMATION,MODE_WORLD_READABLE);
        String content = pre.getString("save", "");
        String[] Information = content.split(";");
        String username = Information[0];
        String password = Information[1];

        Intent intent = getIntent();//得到上一个文件传入的ID号
        Bundle i = intent.getExtras();

        int num = i.getInt("ID");//将得到的ID号传递给变量num

        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props);
        // 取得pop3协议的邮件服务器
        Store store = session.getStore("pop3");
        // 连接pop.qq.com邮件服务器
        store.connect("pop.sina.com", username, password);
        // 返回文件夹对象
        Folder folder = store.getFolder("INBOX");
        // 设置仅读
        folder.open(Folder.READ_ONLY);

        // 获取信息
        Message message[] = folder.getMessages();
        ResolveMail receivemail = new ResolveMail((MimeMessage) message[num]);
        text1.setText(receivemail.getSubject());//得到邮件解析后的标题内容并且在控件中显示出来
        text2.setText(receivemail.getFrom());//得到邮件解析后的发送者
        text3.setText(receivemail.getSentDate());//得到邮件解析后的发送时间
        text4.setText((CharSequence) message[num].getContent().toString());//得到邮件解析有的内容

        folder.close(true);
        store.close();



            */

}
