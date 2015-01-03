package com.bigdog.server.web.crimedoc.util;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 10/4/13
 * Time: 12:39 PM
 */
public class WsUtil {
    public static String readTxtFile(String filePath,String encoding){
        StringBuffer sb = new StringBuffer();
        File file=new File(filePath);

        if (!file.exists()) {
            sb.append("文件不存在:"+filePath);
        }else {
            Reader reader = null;
            try {
                // 一次读一个字符
                reader = new InputStreamReader(new FileInputStream(file),encoding);
                int tempchar;
                while ((tempchar = reader.read()) != -1) {
                    sb.append((char) tempchar);
                }
                reader.close();
            } catch (Exception e) {
                sb.append("file error:"+e.getMessage());
            }
        }
        return  sb.toString();

    }
}
