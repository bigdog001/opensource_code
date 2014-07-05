package com.ok.tool;

import java.util.Iterator;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by WangJiuNian.
 * User: Administrator
 * Date: 2011-4-1
 * Time: 11:56:22
 */
public class Tool {
    public static boolean checkEmail(String email) {
        String reg = "^\\w+([-.]\\w+)*@\\w+([-]\\w+)*\\.(\\w+([-]\\w+)*\\.)*[a-z]{2,3}$";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

}
