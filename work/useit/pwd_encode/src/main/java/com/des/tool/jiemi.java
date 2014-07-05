package com.des.tool;

import java.io.FileInputStream;
import java.io.PrintStream;

public class jiemi
{
    public static void main(String[] args)
    {
        if (args.length != 1) {
            System.out.println("密码参数异常,请携带密码数据");
            System.exit(0);
        }
        try {
            FileInputStream fis = new FileInputStream("./data/miwen");
            int x = fis.available();
            byte[] data = new byte[x];
            fis.read(data);
            String inf = new String(data, "gbk");
            DESPlus des = new DESPlus(args[0]);
            System.out.println("解密后的数据：\n" + des.decrypt(inf));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("密码错误或者是密文数据被损坏");
        }
    }

    public static String main(String path,String pwd)
    {
        String result="";

        try {
            FileInputStream fis = new FileInputStream(path);
            int x = fis.available();
            byte[] data = new byte[x];
            fis.read(data);
            String inf = new String(data, "gbk");
            DESPlus des = new DESPlus(pwd);
            result=des.decrypt(inf);
            // System.out.println("解密后的数据：\n" +result );
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("密码错误或者是密文数据被损坏");
        }
        return result;
    }


}