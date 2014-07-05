package com.bigdog.carfinder.android.conf;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 7/30/13
 * Time: 2:09 PM
 */
public class Variable {
    private static String macAddress ;//客户端网卡地址,全部集中在此处访问

    public static String getMacAddress() {
        if (macAddress == null) {
            return "ec:55:f9:c0:03:32";
        }
        return macAddress;
    }

    public static void setMacAddress(String macAddress) {
        Variable.macAddress = macAddress;
    }
}
