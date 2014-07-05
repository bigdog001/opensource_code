package com.sender.impl;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 10/18/12
 * Time: 3:03 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SenderTask {
    public void initTask();//初始化身份,获取任务目标地址
    public void send(Task task);//向目标发起数据
}
