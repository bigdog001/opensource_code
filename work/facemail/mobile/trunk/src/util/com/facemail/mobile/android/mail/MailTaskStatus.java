package com.facemail.mobile.android.mail;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 5/11/13
 * Time: 7:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class MailTaskStatus {
    private int serverConnStatus=0;//0代表停滞或任务完成 5：开始连接服务器 1： 连接成功 2：连接成功后接收数据中 3：获取完毕所有数据，分析数据中 4：邮件接收存储中
    private int currentPosition;//当前处理到什么位置
    private  int totalAmount;//总量（等待接收的总量，活着等待分析的总量）
    private  int workModule=0;//任务工作模式0代表停滞，1手动，2后台自动收取（任何一种工作模式完毕后立即将其置为停滞模式）

    public MailTaskStatus(int serverConnStatus, int currentPosition, int totalAmount, int workModule) {
        this.serverConnStatus = serverConnStatus;
        this.currentPosition = currentPosition;
        this.totalAmount = totalAmount;
        this.workModule = workModule;
    }

    public int getServerConnStatus() {
        return serverConnStatus;
    }

    public void setServerConnStatus(int serverConnStatus) {
        this.serverConnStatus = serverConnStatus;
    }

    public int getWorkModule() {
        return workModule;
    }

    public void setWorkModule(int workModule) {
        this.workModule = workModule;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "{"
                 + serverConnStatus +
                "" + currentPosition +
                "" + totalAmount +
                "" + workModule +
                '}';
    }
}
