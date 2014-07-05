package com.bigdog.mobile.android.Interphone.voice;

import com.bigdog.mobile.android.Interphone.utils.IpMessageConst;

import java.io.*;
import java.net.*;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 9/23/13
 * Time: 11:22 AM
 */
public class t {
    private static DatagramSocket ds = null;
//    static String  clientIpStr = "10.10.55.112";
    static String  clientIpStr = "255.255.255.255";

    static int clientPort = IpMessageConst.VOICE_PORT;
    public static void main(String[] args) throws IOException {

        String filepath = "/home/bigdog/Downloads/dbz.mp3";
        File file = new File(filepath);
        FileInputStream is = new FileInputStream(filepath);

        byte[] buf = new byte[200];
        int x= 0;
        ds = new DatagramSocket();
        while ((x = is.read(buf))!=-1){
            System.out.println(buf);
            DatagramPacket dp = new DatagramPacket(buf, buf.length, InetAddress
                    .getByName(clientIpStr), clientPort);
            ds.send(dp);
        }


    }
}
