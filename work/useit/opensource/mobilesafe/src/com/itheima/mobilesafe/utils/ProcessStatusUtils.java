package com.itheima.mobilesafe.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;

public class ProcessStatusUtils {
	/**
	 * ��ȡ�ж��ٸ���������������״̬.
	 * @param context
	 * @return
	 */
	public static int getProcessCount(Context context){
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		return am.getRunningAppProcesses().size();
	}
	
	/**
	 * ��ȡ�ֻ�������õ��ڴ�ռ�
	 * @param context
	 * @return long���͵�byte��ֵ
	 */
	public static long getAvailRAM(Context context){
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo outInfo = new MemoryInfo();
		am.getMemoryInfo(outInfo);
		return outInfo.availMem;
	}
	
	public static long getTotalRAM(){
		try {
			File file = new File("/proc/meminfo");
			FileInputStream fis = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String str = br.readLine();
			//MemTotal:         513248 kB
			char[] chars = str.toCharArray();
			StringBuffer sb = new StringBuffer();
			for(char c : chars){
				if(c>='0'&&c<='9'){
					sb.append(c);
				}
			}
			return Integer.parseInt(sb.toString())*1024;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
}
