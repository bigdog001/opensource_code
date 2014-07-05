package com.facemail.mobile.android.mail;

import com.facemail.mobile.android.db.bean.FaceAddress;
import com.facemail.mobile.android.db.bean.FaceMailMessage;
import com.facemail.mobile.android.db.bean.UserAcount;
import com.facemail.mobile.android.db.conf.Config;
import com.facemail.mobile.android.util.SystemUtil;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.pop3.POP3Folder;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 4/28/13
 * Time: 11:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseReceiver {
    protected UserAcount userAcount;
    private String attachDir;
    protected javax.mail.Message message =null;
    protected Properties props = new Properties();
    protected Session session = Session.getDefaultInstance(props); // 取得pop3协议的邮件服务器
    protected Store store=null;
    protected Folder folder =null;
    protected Message messages[] =null;
//    private String currentEmailFileName;

    public String getAttachDir() {
        return attachDir;
    }

    public void setAttachDir(String attachDir) {
        this.attachDir = attachDir;
    }

    public UserAcount getUserAcount() {
        return userAcount;
    }

    public void setUserAcount(UserAcount userAcount) {
        this.userAcount = userAcount;
    }

    /**
     * 文件拷贝，在用户进行附件下载的时候，可以把附件的InputStream传给用户进行下载
     * @param is
     * @param os
     * @throws IOException
     */
    protected  void copy(InputStream is, OutputStream os) throws IOException {
        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len=is.read(bytes)) != -1 ) {
            os.write(bytes, 0, len);
        }
        if (os != null)
            os.close();
        if (is != null)
            is.close();
    }

    /***************************************************************************
     * 判断邮件是否是包含附件
     *
     * @param part
     * @return
     * @throws Exception
     */
    protected boolean isContainAttch(Part part)  {
        boolean attachflag = false;
        try {
            if (part.isMimeType("multipart/*")) {
                // 如果邮件体包含多部分
                Multipart mp = (Multipart) part.getContent();

                for (int i = 0; i < mp.getCount(); i++) {
                    BodyPart bodyPart = mp.getBodyPart(i);
                    String disposition = bodyPart.getDisposition();
                    if ((disposition != null)
                            && ((disposition.equals(Part.ATTACHMENT) || (disposition
                            .equals(Part.INLINE))))) {
                        attachflag = true;
                    } else if (bodyPart.isMimeType("Multipart/*")) {
                        attachflag = isContainAttch((Part) bodyPart);
                    } else {
                        String contype = bodyPart.getContentType();
                        if (contype.toLowerCase().indexOf("applcation") != -1) {
                            attachflag = true;
                        }
                        if (contype.toLowerCase().indexOf("name") != -1) {
                            attachflag = true;
                        }
                    }
                }
            } else if (part.isMimeType("message/rfc822")) {
                attachflag = isContainAttch((Part) part.getContent());
            }

        } catch (MessagingException e) {
            SystemUtil.log("BaseReceiver.isContainAttch1 error：" + e.getMessage());
        } catch (IOException e) {
            SystemUtil.log("BaseReceiver.isContainAttch2 error：" + e.getMessage());
        }
        return attachflag;
    }

    /***************************************************************************
     * 获得邮件是否是已经读过了
     *
     * @param mimeMessage
     * @return
     * @throws Exception
     */
    protected boolean isNew(Message mimeMessage)  {
        // TODO Auto-generated method stub
        try {
            boolean isnew = false;
            Flags flags = mimeMessage.getFlags();
            Flags.Flag[] flag = flags.getSystemFlags();
            for (int i = 0; i < flag.length; i++) {
                if (flag[i] == Flags.Flag.SEEN) {
                    isnew = true;
                    break;
                }
            }
            return false;
        } catch (MessagingException e) {
            SystemUtil.log("FACE ERROR,BaseReceiver.isNew:" + e.getMessage());
            return false;
        }

    }


    /***************************************************************************
     * 判断邮件是否需要回执。
     *
     * @param mimeMessage
     * @return
     * @throws Exception
     */
    protected boolean getReplySign(Message mimeMessage) throws Exception {
        // TODO Auto-generated method stub
        boolean replaysign = false;
        String[] needreply = (String[]) mimeMessage.getHeader("Disposition-Notification-To");
        if (needreply != null) {
            replaysign = true;
        }
        return replaysign;
    }

    //new way to do---------------------------------------



      int level = 0;
     private String messageUID="";
    protected String doMessageUID(Part p) {
        try {
            if(folder instanceof POP3Folder) {
                POP3Folder inbox = (POP3Folder) folder;
                messageUID = inbox.getUID((MimeMessage)p);
            }else if (folder instanceof IMAPFolder) {
                IMAPFolder inbox = (IMAPFolder) folder;
                messageUID = Long.toString(inbox.getUID((MimeMessage) p));
            } else {
                SystemUtil.log("no have this folder {}" + folder.getFullName());
            }
        } catch (MessagingException e) {
            SystemUtil.log("facemail error domessageUID:"+e.getMessage());
        }

        return messageUID;
    }

    protected void doMessage(Part p,FaceMailMessage faceMailMessage) throws Exception{
        faceMailMessage.setMessageNumber(doMessageUID(p));
        dumpPart2(p, faceMailMessage);
    }


    protected   void dumpPart2(Part p,FaceMailMessage faceMailMessage) throws Exception {

        try {
            if (p instanceof Message)
                dumpEnvelope((Message) p,faceMailMessage);

            String ct = p.getContentType();
            pr("CONTENT-TYPE: " + (new ContentType(ct)).toString());
            String filename = p.getFileName();
            if (filename != null) {
                pr("FILENAME: " + filename);
            }

            //打印正文
            if (p.isMimeType("text/plain")) {
                pr("This is plain text");
                pr("---------------------------");
                faceMailMessage.setMail_content(faceMailMessage.getMail_content() + p.getContent());
            }else if(p.isMimeType("text/html")){
                pr("This is plain html>:");
                faceMailMessage.setMail_content(faceMailMessage.getMail_content()+p.getContent());
            }
            else if (p.isMimeType("multipart/*")) {
                pr("This is a Multipart");
                pr("---------------------------");
                Multipart mp = (Multipart) p.getContent();
                level++;
                int count = mp.getCount();
                for (int i = 0; i < count; i++)
                    dumpPart2(mp.getBodyPart(i),faceMailMessage);
                level--;
            } else if (p.isMimeType("message/rfc822")) {
                pr("This is a Nested Message");
                pr("---------------------------");
                level++;
                dumpPart2((Part) p.getContent(),faceMailMessage);
                level--;
            } else {
                pr("---------------------------");
            }
            //处理附件
            if (level != 0 && !p.isMimeType("multipart/*")) {
                String disp = p.getDisposition();
                if (filename != null && (disp == null || disp.equalsIgnoreCase(Part.ATTACHMENT))) {
                    pr("Saving attachment to file " + filename);
                  //  try {
                      //  File f = new File("/mnt/sdcard/"+filename);
                       /* if (f.exists())
                            throw new IOException("file exists");*/
                      //  ((MimeBodyPart) p).saveFile(f);
                    faceMailMessage.setMail_is_attach("1");
                faceMailMessage.setMail_attach_filename("".equals(faceMailMessage.getMail_attach_filename())?filename:faceMailMessage.getMail_attach_filename()+","+filename);
                     /*}catch (IOException ex) {
                        pr("Failed to save attachment: " + ex);
                    }*/
                }
            }
        }catch (IOException e){
           SystemUtil.log("ACE ERROR FBaseReceiver.dumpPart2:"+e.getMessage());
            throw e;
        }
    }

    protected   void dumpEnvelope(Message m,FaceMailMessage faceMailMessage)   {
        try {
            InternetAddress[] FROM= (InternetAddress[])m.getFrom();
            if (FROM != null) {
                List<FaceAddress> faceAddresses_from=getFaceAddress(faceMailMessage);
                for (InternetAddress address_from:FROM) {
                    FaceAddress faceAddress=new FaceAddress();
                    faceAddress.setAddress_source_email(userAcount.getUser_name());
                    faceAddress.setEmailAddress(address_from.getAddress());
                    faceAddress.setEmailDesc(address_from.getPersonal());
                    faceAddress.setMessagenumber(messageUID);
                    faceAddress.setType(Config.ADRESS_TYPE.FROM);
                    faceAddresses_from.add(faceAddress);
                }
            }

            // REPLY TO
            InternetAddress[] REPLY_TO= (InternetAddress[])m.getReplyTo();
            if (REPLY_TO != null) {
                List<FaceAddress> faceAddresses_reply_to=getFaceAddress(faceMailMessage);
                for (InternetAddress address_reply_to:REPLY_TO) {
                    FaceAddress faceAddress=new FaceAddress();
                    faceAddress.setAddress_source_email(userAcount.getUser_name());
                    faceAddress.setEmailAddress(address_reply_to.getAddress());
                    faceAddress.setEmailDesc(address_reply_to.getPersonal());
                    faceAddress.setMessagenumber(messageUID);
                    faceAddress.setType(Config.ADRESS_TYPE.REPLY_TO);
                    faceAddresses_reply_to.add(faceAddress);
                }
            }

           /* //代替下面的3个步骤的操作
            InternetAddress[] to_all= (InternetAddress[])m.getAllRecipients();
            if (to_all!=null) {
                List<FaceAddress> faceAddresses_to_all=getFaceAddress(faceMailMessage);
                for (InternetAddress address_to:to_all) {
                    FaceAddress faceAddress=new FaceAddress();
                    faceAddress.setAddress_source_email(userAcount.getUser_name());
                    faceAddress.setEmailAddress(address_to.getAddress());
                    faceAddress.setEmailDesc(address_to.getPersonal());
                    faceAddress.setMessagenumber(messageUID);
                    faceAddress.setType(address_to.getType());
                    faceAddresses_to_all.add(faceAddress);
                }
            }*/


            InternetAddress[] to;
            to = (InternetAddress[])m.getRecipients(Message.RecipientType.TO);
            if (to!=null) {
                List<FaceAddress> faceAddresses_to=getFaceAddress(faceMailMessage);
                for (InternetAddress address_to:to) {
                    FaceAddress faceAddress=new FaceAddress();
                    faceAddress.setAddress_source_email(userAcount.getUser_name());
                    faceAddress.setEmailAddress(address_to.getAddress());
                    faceAddress.setEmailDesc(address_to.getPersonal());
                    faceAddress.setMessagenumber(messageUID);
                    faceAddress.setType(Config.ADRESS_TYPE.TO);
                    faceAddresses_to.add(faceAddress);
                }
            }


            InternetAddress[] to_cc;
            to_cc = (InternetAddress[])m.getRecipients(Message.RecipientType.CC);
            if (to_cc!=null) {
                List<FaceAddress> faceAddresses_to_cc=getFaceAddress(faceMailMessage);
                for (InternetAddress address_to_cc:to_cc) {
                    FaceAddress faceAddress=new FaceAddress();
                    faceAddress.setAddress_source_email(userAcount.getUser_name());
                    faceAddress.setEmailAddress(address_to_cc.getAddress());
                    faceAddress.setEmailDesc(address_to_cc.getPersonal());
                    faceAddress.setMessagenumber(messageUID);
                    faceAddress.setType(Config.ADRESS_TYPE.CC);
                    faceAddresses_to_cc.add(faceAddress);
                }
            }


            InternetAddress[] to_bcc;
            to_bcc = (InternetAddress[])m.getRecipients(Message.RecipientType.BCC);
            if (to_bcc!=null) {
                List<FaceAddress> faceAddresses_to_bcc=getFaceAddress(faceMailMessage);
                for (InternetAddress address_to_bcc:to_bcc) {
                    FaceAddress faceAddress=new FaceAddress();
                    faceAddress.setAddress_source_email(userAcount.getUser_name());
                    faceAddress.setEmailAddress(address_to_bcc.getAddress());
                    faceAddress.setEmailDesc(address_to_bcc.getPersonal());
                    faceAddress.setMessagenumber(messageUID);
                    faceAddress.setType(Config.ADRESS_TYPE.BCC);
                    faceAddresses_to_bcc.add(faceAddress);
                }
            }


            faceMailMessage.setMail_title(m.getSubject());
            faceMailMessage.setMail_rcv_time(m.getReceivedDate());
            faceMailMessage.setMail_send_time(m.getSentDate());
            faceMailMessage.setStarMail(getReplySign(m)?"1":"0");
            Flags flags = m.getFlags();
            StringBuffer sb = new StringBuffer();
            Flags.Flag[] sf = flags.getSystemFlags(); // get the system flags

            boolean first = true;
            for (int i = 0; i < sf.length; i++) {
                String s;
                Flags.Flag f = sf[i];
                if (f == Flags.Flag.ANSWERED)
                    s = "\\Answered";
                else if (f == Flags.Flag.DELETED)
                    s = "\\Deleted";
                else if (f == Flags.Flag.DRAFT)
                    s = "\\Draft";
                else if (f == Flags.Flag.FLAGGED)
                    s = "\\Flagged";
                else if (f == Flags.Flag.RECENT)
                    s = "\\Recent";
                else if (f == Flags.Flag.SEEN)
                    s = "\\Seen";
                else
                    continue; // skip it
                if (first)
                    first = false;
                else
                    sb.append(' ');
                sb.append(s);
            }

            String[] uf = flags.getUserFlags(); // get the user flag strings
            for (int i = 0; i < uf.length; i++) {
                if (first)
                    first = false;
                else
                    sb.append(' ');
                sb.append(uf[i]);
            }
            pr("FLAGS: " + sb.toString());
            // X-MAILER
            String[] hdrs = m.getHeader("X-Mailer");
            if (hdrs != null)
                pr("X-Mailer: " + hdrs[0]);
            else
                pr("X-Mailer NOT available");

        }   catch (Exception e){
            SystemUtil.log("FACE ERROR BASERECEIVER.dumpEnvelope"+e.getMessage());
        }
    }

    protected   void pr(String s) {
        SystemUtil.log(s);
    }

    protected List<FaceAddress> getFaceAddress(FaceMailMessage faceMailMessage) {
        if (faceMailMessage.getFaceAddresses()!=null) {
            return faceMailMessage.getFaceAddresses();
        }else {
            faceMailMessage.setFaceAddresses(new ArrayList<FaceAddress>());
            return faceMailMessage.getFaceAddresses();
        }
    }

    //-----------------------------------------------new way to do


}
