package com.sender.send;

import com.sender.impl.IwebRun;
import com.sender.impl.mailSenderTask;
import com.sender.taskbean.configBean;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 10/26/12
 * Time: 7:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class webRun implements IwebRun {
    private static Logger logger= Logger.getLogger(webRun.class);
    public static int isRun=0;
    private static webRun webrun;
    private static Timer timer;

    private webRun() {}

    public static webRun getInstance() {
        if (webrun==null) {
            webrun=new webRun();
        }
        return webrun;
    }

    public void start() {
        logger.info("开始开启数据发送...");
            if (isRun==1) {
                return;
            }
            timer=new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    new Sender().send(new mailSenderTask().getTaskbean());
                }
            }, configBean.web_timedis);
            isRun = 1;
        logger.info("成功开启数据发送...");
    }

    public void stop() {
        isRun=0;
        timer.cancel();
        logger.info("停止数据发送...");
    }

    public void changeWork() {
        if (isRun==1) {
            stop();
        } else{
            start();
        }

    }

}
