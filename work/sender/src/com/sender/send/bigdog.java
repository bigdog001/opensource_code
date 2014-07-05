package com.sender.send;

import com.sender.impl.mailSenderTask;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 10/18/12
 * Time: 6:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class bigdog {
    private static Logger logger = Logger.getLogger(bigdog.class);

    public void work() {
        new Runnable() {
            @Override
            public void run() {
                new Sender().send(new mailSenderTask().getTaskbean());
//                new mailSenderTask().getTaskbean();
            }
        }.run();
    }


    public static void main(String[] ss) {
        new bigdog().work();
    }
}
