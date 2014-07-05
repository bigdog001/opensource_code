package com.bigdog.mobile.android.Interphone.voice;

import com.bigdog.mobile.android.Interphone.utils.IpMessageConst;

import java.io.IOException;
import java.net.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 9/22/13
 * Time: 1:08 PM
 */
public class test implements Runnable {

    DatagramSocket socket;
    DatagramPacket packet;// 从客户端接收到的UDP包
    DatagramPacket sendPkt;// 转发给另一个客户端的UDP包

    byte[] pktBuffer = new byte[1024];
    int bufferSize = 1024;
    boolean isRunning = false;
    int myport = 5656;

    // ///////////
//    String clientIpStr = "10.10.52.76";
    String clientIpStr = "10.10.55.112";
    InetAddress clientIp;
    int clientPort = IpMessageConst.VOICE_PORT;



    public test() {
        try {
            clientIp = InetAddress.getByName(clientIpStr);
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        }
        try {
            socket = new DatagramSocket(myport);
//            socket2= new DatagramSocket(clientPort,clientIp) ;
            packet = new DatagramPacket(pktBuffer, bufferSize);
        }   catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("服务器初始化完成");
    }

    public void startServer() {
        this.isRunning = true;
        new Thread(this).start();
    }

    public void run() {
        try {
            while (isRunning) {
                socket.receive(packet);
                sendPkt = new DatagramPacket(packet.getData(),
                        packet.getLength(), packet.getAddress(), clientPort);
                System.out.println(new Date()+"收到数据...Length:"+packet.getLength()+",Address:"+packet.getAddress()+",client :"+packet.getAddress().getHostAddress()+":"+clientPort);
                socket.send(sendPkt);

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
        }
    }

    // main
    public static void main(String[] args) {
        new test().startServer();
    }
}