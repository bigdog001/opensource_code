package com.itheima.mobilesafe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;

import android.os.Build;
import android.os.Environment;
import com.itheima.mobilesafe.utils.Logger;

public class MyExceptionHandler implements UncaughtExceptionHandler {

	private static final String TAG = "MyExceptionHandler";

	@Override
	public void uncaughtException(Thread arg0, Throwable arg1) {
		Logger.i(TAG, "发生了异常,但是被哥捕获了...");
		try {
		Field[] fields = Build.class.getDeclaredFields();
		StringBuffer sb = new StringBuffer();
		for(Field field: fields){
			String info = field.getName()+ ":"+field.get(null)+"\n";
			sb.append(info);
		}
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		arg1.printStackTrace(pw);
		String errorlog = sw.toString();
		
			File file = new File(Environment.getExternalStorageDirectory(),
					"error.log");
			FileOutputStream fos = new FileOutputStream(file);
			sb.append(errorlog);
			fos.write(sb.toString().getBytes());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		android.os.Process.killProcess(android.os.Process.myPid());

	}

}
