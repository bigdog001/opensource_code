package com.facemail.mobile.android.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 4/16/13
 * Time: 2:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class testCase  {
    public static void main(String ss[]){
        String path = "/home/bigdog/Downloads/code/ide/google/294356967@qq.com/facemail/mobile/trunk/res/values/AuthorConf.ini";
        readTxtFile(path);

    }
    public static void readTxtFile(String filePath){
        try {
            String encoding="utf-8";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                String [] t = null;
                int x = 1;
                while((lineTxt = bufferedReader.readLine()) != null) {
                    t = lineTxt.split(",");
                    if (t.length == 4) {
                        if (t[3].length()>13) {
                            System.out.println(x++ + "," + lineTxt);
                        }

                    }

                }
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

    }
}
