package com.facemail.mobile.android.mail;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 4/25/13
 * Time: 3:36 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IMailProvider {

    /**
     * 添加一个请求.
     * @param req
     */
    void addRequest(IMailRequest req);

    /**
     * 停止网络线程.
     */
    void stop();
}
