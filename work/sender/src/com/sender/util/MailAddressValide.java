package com.sender.util;

import com.sender.taskbean.configBean;
import org.apache.log4j.Logger;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 10/19/12
 * Time: 1:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class MailAddressValide {
    private static Logger logger = Logger.getLogger(MailAddressValide.class);
    private static int invalidateTotal=0;
    public static boolean validate(String email) {
        if (email==null||"".equals(email)) {
            return false;
        }
        Boolean o=false;
        String EMAIL_REGEX = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher b = pattern.matcher(email);
        o=b.matches();
        if (o==false) {
            logger.info("00014:邮件地址非法:"+email+"目前非法地址总数为:"+invalidateTotal++);
        }

        return  o;
    }

    public static void main(String []fff) {
        for (int d=0;d<20;d++) {
//            System.out.println(getRandomContent(60));
            System.out.println("----------------------------------------------------------------");
        }

    }


}
