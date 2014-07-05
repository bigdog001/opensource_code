package com.des.tool;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class jiami
{
    public static void main(String[] args)
    {
        if (args.length != 1) {
            System.out.println("密码参数异常,请携带密码数据");
            System.exit(0);
        }
        try {
            System.out.println("开始进行数据加密");
            FileInputStream fis = new FileInputStream("./data/mingwen");
            int x = fis.available();
            byte[] data = new byte[x];
            fis.read(data);
            String inf = new String(data, "gbk");
            DESPlus des = new DESPlus(args[0]);
            String result = des.encrypt(inf);
            FileOutputStream fos = new FileOutputStream("./data/miwen");
            fos.write(result.getBytes("gbk"));
            fos.flush();
            fos.close();
            System.out.println("成功的完成数据加密");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String content,String path,String pwd)
    {
        String result="";
        try {
            System.out.println("开始进行数据加密");
            byte[] data=content.getBytes("gbk");
            String inf = new String(data, "gbk");
            DESPlus des = new DESPlus(pwd.trim());
            result = des.encrypt(inf);
            FileOutputStream fos = new FileOutputStream(path);
            fos.write(result.getBytes("gbk"));
            fos.flush();
            fos.close();
            System.out.println("成功的完成数据加密");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //  return result;
    }

}