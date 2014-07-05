package com.itheima.mobilesafe.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	/**
	 * md5���ܵĹ��߷���
	 * @param password
	 * @return
	 */
	public static String encode(String password){
		try {
			MessageDigest digest = MessageDigest.getInstance("md5");
			byte[] result = digest.digest(password.getBytes());
			StringBuilder sb = new StringBuilder();
			//16���Ƶķ�ʽ  �ѽ����byte���� ��ӡ����
			for(byte b :result){
				int number = (b&0xff);//����.
				String str =Integer.toHexString(number);
				if(str.length()==1){
					sb.append("0");
				}
				sb.append(str);
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}
}
