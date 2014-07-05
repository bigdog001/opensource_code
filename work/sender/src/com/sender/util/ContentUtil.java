package com.sender.util;

import com.sender.taskbean.configBean;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 10/20/12
 * Time: 7:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContentUtil {

    static String result="";
    static int p=0;
    static int c_length=0;
    static int splite_flag=0;

    public static String getRandomContent(int size) {
        result="";
        if (size==0) {
            size=50;
        }
        if (size>200) {
            size=200;
        }
        c_length= configBean.all_china_charactor.length;
        Random random= new Random();//nextInt(max);
        for (int i=0;i< size;i++) {
            p++;
            if (p%(random.nextInt(20)+1)==0) {
                result=result+configBean.all_china_charactor[random.nextInt(c_length)]+getCentanceSplite();
            }else {
                result=result+configBean.all_china_charactor[random.nextInt(c_length)];
            }
        }
        result=result+" ! ";
        return result;

    }

    public static String getCentanceSplite() {
        splite_flag++;
        if (splite_flag%2==0) {
            return ",";
        } else {
            return ".";
        }
    }
/*    public static void main(String []fff) {
        for (int d=0;d<20;d++) {
            System.out.println(getRandomContent(60));
            System.out.println("----------------------------------------------------------------");
        }

    }*/

    public static void main(String[] args) {
        String str = getRandomString(9);
        System.out.println(str);
    }

    public static String getRandomString(int length) {
        double i = 1.0;
        String send_id = "";

        while (send_id.length() <= 8)
        {
            i = Math.random();
            String str = i + "";
            str = str.substring(2);
            send_id += str;
        }
        send_id = send_id.substring(0, 8);
        send_id="13"+new Random().nextInt(9)+send_id ;
        return send_id;
    }

}
