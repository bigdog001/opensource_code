package com.itheima.mobilesafe.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.ProgressDialog;

public class DownLoadUtils {

	/**
	 * 下载的工具方法
	 * 
	 * @param filePath
	 *            服务器文件的路径
	 * @param savePath
	 *            本地保存的路径
	 * @param pd
	 *            进度条对话框
	 * @return
	 */
	public static File download(String filePath, String savePath,
			ProgressDialog pd) {
		try {
			URL url = new URL(filePath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			int code = conn.getResponseCode();
			if (code == 200) {
				File file = new File(savePath);
				FileOutputStream fos = new FileOutputStream(file);
				InputStream is = conn.getInputStream();

				int max = is.available();
				pd.setMax(max);
				int len = 0;
				byte[] buffer = new byte[1024];
				int total = 0;
				while ((len = is.read(buffer)) != -1) {

					fos.write(buffer, 0, len);
					total += len;
					pd.setProgress(total);// 更新进度条对话框的进度
					Thread.sleep(30);
				}
				fos.flush();
				fos.close();
				is.close();

				return file;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	/**
	 * 获取服务器上的文件名称
	 */
	
	public static String getFileName(String filePath){
		int index = filePath.lastIndexOf("/")+1;
		return filePath.substring(index);
	}
	
	
}
