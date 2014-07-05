package com.sender.impl;

import com.sender.pool.AddressPool;
import com.sender.pool.AuthorPool;
import com.sender.taskbean.AuthorBean;
import com.sender.taskbean.mailSenderTaskBean;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 10/18/12          private static Logger logger = Logger.getLogger(mailSenderTask.class);
 * Time: 3:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class mailSenderTask  extends Task implements SenderTask {
    private static Logger logger = Logger.getLogger(mailSenderTask.class);
   private  mailSenderTaskBean taskbean;
   private AuthorBean authorBean;
    @Override
    public void initTask() {
        authorBean= AuthorPool.getAuthorPool().getAuthorBean();
        taskbean=new mailSenderTaskBean();
        taskbean.setMailTo(AddressPool.getAddressPool().getSendTarget());
        taskbean.setSmtpHost(authorBean.getSmtpHost());
        taskbean.setSmtpUsername(authorBean.getUsername());
        taskbean.setSmtppassword(authorBean.getPassword());
        taskbean.setSmtpPort(authorBean.getPort());
//        taskbean.setMailTo(AddressPool.getAddressPool().getSendTarget());
        taskbean.setMailFrom(authorBean.getUsername());
        logger.info("本次任务目标；"+getT(taskbean.getMailTo())+",本次任务目标通讯身份:"+taskbean.getSmtpUsername());
    }

    @Override
    public void send(Task task) {
    }

    @Override
    public void initData(Task task) {
        initTask();
        send(task);
    }

    public mailSenderTaskBean getTaskbean() {
        return taskbean;
    }
    public String getT(String[] o){
        String t="";
        if (o==null) {
            return "";
        }
        for (String f:o) {
            t=t+","+f;
        }

        return  t;
    }
}
