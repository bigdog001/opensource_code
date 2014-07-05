package com.facemail.mobile.android.mail;

import com.facemail.mobile.android.R;
import com.facemail.mobile.android.application.FaceMailApplication;
import com.facemail.mobile.android.db.bean.FaceAddress;
import com.facemail.mobile.android.db.bean.FaceMailMessage;
import com.facemail.mobile.android.db.bean.UserAcount;
import com.facemail.mobile.android.db.conf.Config;
import com.facemail.mobile.android.db.dao.FaceMailMessageDao;
import com.facemail.mobile.android.mail.pop3.FaceMailReceiver;
import com.facemail.mobile.android.mail.smtp.FaceMailSender;
import com.facemail.mobile.android.util.SystemUtil;
import com.facemail.mobile.android.util.json.JsonObject;
import com.facemail.mobile.android.util.json.JsonValue;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 4/25/13
 * Time: 3:33 PM
 */
public class FaceMailProccess implements IMailProvider {
    private static IMailProvider instance = new FaceMailProccess();
    private static Object lock = new Object();

    private FaceMailProccess() {
        System.setProperty("http.keepAlive", "true");
    }

    public static IMailProvider getInstance() {
        return instance;
    }

    private Vector<IMailRequest> mailVec = new Vector<IMailRequest>();

    private MailThread[] mailThreads = null;


    public void addRequest(IMailRequest req) {
        if (req == null) {
            SystemUtil.log("facemailprocess.addRequest1请求对象为空!");
            return;
        }
        if (req.getUserAccount() == null) {
            SystemUtil.log("facemailprocess.addRequest2账户对象为空!");
            return;
        }
        if (req.getUserAccount().getUser_name() == null || "".equals(req.getUserAccount().getUser_name())) {
            SystemUtil.log("facemailprocess.addRequest3账户对象为空!");
            return;
        }
        if (!SystemUtil.isEmail(req.getUserAccount().getUser_name().trim())) {
            SystemUtil.log("facemailprocess.addRequest4 Email格式错误!");
            return;
        }

        synchronized (FaceMailProccess.this) {
            checkThreads();
            if (Config.MAIL_TASK_RCV == req.getTaskType()) {
                synchronized (mailVec) {
                    mailVec.addElement(req);
                    mailVec.notify();
                }
            } else {
                SystemUtil.log("wrong mail task,task type:" + req.getTaskType() + ",and user account is:" + req.getUserAccount().getUser_name());
            }
        }
    }


    private void checkThreads() {
        if (mailThreads == null) {
            mailThreads = new MailThread[Config.MAIL_THREADS];
            for (int i = 0; i < mailThreads.length; i++) {
                mailThreads[i] = new MailThread(mailVec);
                mailThreads[i].start();
            }
        }
    }

    public void stop() {
        synchronized (FaceMailProccess.this) {
            if (null != mailThreads) {
                synchronized (mailVec) {
                    for (int i = 0; i < mailThreads.length; i++) {
                        if (null != mailThreads[i]) {
                            mailThreads[i].running = false;
                            mailThreads[i].burnning = false;
                        }
                    }
                    mailVec.clear();
                    mailVec.notifyAll();
                    mailThreads = null;
                }
                SystemUtil.log("mailSendThreads stop work...");
            }

            if (null != mailThreads) {
                synchronized (mailVec) {
                    for (int i = 0; i < mailThreads.length; i++) {
                        if (null != mailThreads[i]) {
                            mailThreads[i].running = false;
                            mailThreads[i].burnning = false;
                        }
                    }
                    mailVec.clear();
                    mailVec.notifyAll();
                    mailThreads = null;
                }
                SystemUtil.log("mailRcvThreads stop work...");
            }
        }
    }

    static class MailThread extends Thread {
        private Vector<IMailRequest> reqVec = null;
        private JsonObject error = new JsonObject();

        public MailThread(Vector<IMailRequest> reqVec) {
            this.reqVec = reqVec;
            setPriority(Thread.NORM_PRIORITY);
            error.put("error_code", -99);
            error.put("error_msg", "连接服务器错误，请稍后再试！");
        }

        protected boolean running = true;

        /**
         * 等所有请求都发送完成后关闭
         */
        protected boolean burnning = false;

        @Override
        public void destroy() {
            super.destroy();
            SystemUtil.log("Thread gone..." + super.getId());
        }

        @Override
        public void run() {

            IMailRequest currentRequest = null;
            boolean reconnect = false;
            while (running || burnning) {
//				Thread.yield();
                if (!reconnect) {
                    synchronized (reqVec) {
                        if (reqVec.size() > 0) {
                            currentRequest = reqVec.firstElement();
                            reqVec.removeElementAt(0);
                        } else {
                            if (burnning) {
                                burnning = false;
                                return;
                            }
                            try {
                                reqVec.wait();
                                continue;
                            } catch (InterruptedException e) {
                                SystemUtil.log("Errors:" + e.getMessage());
                            }
                        }
                    }
                }


                if (currentRequest != null) {
                    JsonObject temp = new JsonObject();
                    temp.put("error_code", 44);
                    temp.put("error_msg", "无法连接网络，请检查您的手机网络设置...");

                    final UserAcount currentEmailAccount = currentRequest.getUserAccount();
                    final IMailResponse currentEmailResponse = currentRequest.getIMailResponse();
                    SystemUtil.log("mail account :"+currentEmailAccount.getUser_name()+"'s reponse callback is:"+currentEmailResponse+",thread is:"+getId());
                    // 执行发送或收取的操作
                    if (currentRequest.getTaskType() == Config.MAIL_TASK_SEND) {
                        // 执行发送
                        SystemUtil.log("sending thread working!");
                        FaceMailSender faceMailSender = new FaceMailSender();
                        if (faceMailSender.checkMail(currentRequest)) {
                            try {
                                faceMailSender.send();
                                JsonObject res = new JsonObject();
                                res.put("result", Config.MAIL_SEND_SUCESS);
                                currentEmailResponse.onReceive(currentRequest, res);
                            } catch (Exception e) {
                                JsonObject res = temp;
                                currentEmailResponse.onReceive(currentRequest, res);
                                SystemUtil.log("Errors when sending..." + e.getMessage());
                            }
                        } else {
                            JsonObject res = new JsonObject();
                            res.put("error_code", 43);
                            res.put("error_msg", "收件人错误或内容为空");
                            currentEmailResponse.onReceive(currentRequest, res);
                            SystemUtil.log("Errors when sending...invalide param,just invalide to and from or empty content");
                        }
//                        FaceMailMessage fmm=new FaceMailMessage();
//                        fmm.setMail_title("hi ,nice to meet u-sender");
//                        currentEmailResponse.onReceive(fmm);
                    } else if (currentRequest.getTaskType() == Config.MAIL_TASK_RCV) {
                        // 执行收取
                        SystemUtil.log("Rcving thread working!");
                        final FaceMailReceiver faceMailReceiver = new FaceMailReceiver(currentEmailAccount);
                        final IMailRequest finalCurrentRequest = currentRequest;
                        final FaceMailReceiver.ReceiveListener receiveListener = new FaceMailReceiver.ReceiveListener() {
                            @Override
                            public void doReceive(Message[] messages) {
                                //解析消息并通知界面
//                              currentEmailResponse.onReceiveFirst();
                                if (messages == null || messages.length == 0) {
                                    currentEmailResponse.onReceiveFirst(finalCurrentRequest, 0);
                                } else {
                                    List<Message> messages_new=new ArrayList<Message>();
                                    for (Message message : messages) {

                                        if (!FaceMailMessageDao.getInstance(FaceMailApplication.getFaceMailContext().getApplicationContext()).isExsit(faceMailReceiver.doMessageUID(message),currentEmailAccount.getUser_name())) {
                                            messages_new.add(message);
                                        }else {
                                            try {
                                                SystemUtil.log("old message:"+message.getSubject());
                                            } catch (MessagingException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    faceMailReceiver.setAttachDir(SystemUtil.getMailDir(currentEmailAccount.getUser_name()).getAbsolutePath());
                                    if (messages_new.size()==0) {
                                        currentEmailResponse.onReceiveFirst(finalCurrentRequest, 0);
                                    } else {
                                        try {
                                            currentEmailResponse.onReceiveFirst(finalCurrentRequest, messages_new.size());
                                            for (Message message_new : messages_new) {
                                                try {
                                                    //每收到一封邮件即开始入库
                                                    FaceMailMessage faceMailMessage = new FaceMailMessage();
                                                    faceMailReceiver.doMessage(message_new, faceMailMessage);
                                                    faceMailMessage.setMail_user_name(currentEmailAccount.getUser_name());//标识该邮件属于哪个帐号
                                                    faceMailMessage.setMail_READ_STATUS("0");

//                                                    faceMailMessage.setMessageNumber(message_new.getMessageNumber());
                                                    FaceMailMessageDao.getInstance(FaceMailApplication.getFaceMailContext()).insertMail(faceMailMessage);
                                                    SystemUtil.log("inserte mail..."+faceMailMessage.getMail_title());
                                                } catch (IOException e) {
                                                    currentEmailResponse.onReceiveError(finalCurrentRequest,FaceMailApplication.getFaceMailContext().getResources().getString(R.string.mail_rcv_on_error));
                                                    SystemUtil.log("----facemail00122x:" + e.getMessage());
                                                } catch (Exception e) {
                                                    SystemUtil.log("FACEMAIL ERROR:----facemail00122x:" + e.getMessage());
                                                }
                                                JsonObject obj = new JsonObject();
                                                obj.put(IMailResponse.EMAIL, message_new.getSubject());
                                                currentEmailResponse.onReceiveOne(finalCurrentRequest, obj);
                                            }
                                            //通知该账户收取完毕
                                            currentEmailResponse.onReceive(finalCurrentRequest, new JsonObject());

                                        } catch (MessagingException e) {
                                            currentEmailResponse.onReceiveError(finalCurrentRequest,FaceMailApplication.getFaceMailContext().getResources().getString(R.string.mail_rcv_on_error));
                                            SystemUtil.log("----facemail00122" + e.getMessage());
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onError(String[] ojb) {
                                currentEmailResponse.onReceiveError(finalCurrentRequest,FaceMailApplication.getFaceMailContext().getResources().getString(R.string.mail_rcv_on_error));
                            }
                        };
                        faceMailReceiver.setReceiveListener(receiveListener);
                        faceMailReceiver.doReceive();
                    }

                }
            }
        }
    }

}
