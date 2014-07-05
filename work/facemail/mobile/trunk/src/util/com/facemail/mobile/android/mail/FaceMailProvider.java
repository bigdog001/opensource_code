package com.facemail.mobile.android.mail;

import com.facemail.mobile.android.db.bean.FaceMailMessage;
import com.facemail.mobile.android.db.bean.UserAcount;
import com.facemail.mobile.android.util.SystemUtil;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 4/25/13
 * Time: 5:26 PM
 */
public class FaceMailProvider {

    public synchronized static void doMailReceive(UserAcount ua,int TaskType,IMailResponse response){
        IMailRequest request=new MailRequestWrapper();
        request.setTaskType(TaskType);
        request.setUserAccount(ua);
        request.setIMailResponse(response);
        SystemUtil.log("----xxx:"+ua.getUser_name());
        FaceMailProccess.getInstance().addRequest(request);
    }

    public synchronized static void doMailReceiveTest(final UserAcount ua,final int TaskType,final IMailResponse response){
        final IMailRequest request=new MailRequestWrapper();
        request.setTaskType(TaskType);
        request.setUserAccount(ua);
        request.setIMailResponse(response);

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemUtil.log("rcv debug..user is:"+ua.getUser_name()+",taskTYpe is:"+TaskType+",response:"+response);

                ua.setMailTaskStatus(new MailTaskStatus(5,0,0,2));

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ua.setMailTaskStatus(new MailTaskStatus(1,0,0,2));

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ua.setMailTaskStatus(new MailTaskStatus(2,0,0,2));

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ua.setMailTaskStatus(new MailTaskStatus(3,0,0,2));

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ua.setMailTaskStatus(new MailTaskStatus(4,0,0,2));

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ua.setMailTaskStatus(new MailTaskStatus(0,0,0,0));

                response.onReceive(request,null);


            }
        }).start();

    }


    public static void doMailSend(UserAcount ua,int TaskType,IMailResponse response,FaceMailMessage faceMailMessage){
        IMailRequest request=new MailRequestWrapper();
        request.setTaskType(TaskType);
        request.setUserAccount(ua);
        request.setFaceMailMessage(faceMailMessage);
        request.setIMailResponse(response);
        FaceMailProccess.getInstance().addRequest(request);
    }
}
