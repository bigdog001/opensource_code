package com.itheima.mobilesafe.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;

public class AssetCopyUtils {

	/**
	 * �ʲ������Ĺ�����
	 * 
	 * @param contex
	 *            ������
	 * @param filename
	 *            �ļ�����
	 * @param destPath
	 *            ������Ŀ��·��
	 * @return �ļ�����.
	 */
	public static File copy(Context context, String fileName, String destPath) {
		try {
			InputStream is = context.getAssets().open(fileName);
			File file = new File(destPath);
			FileOutputStream fos = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int len = 0;

			while ((len = is.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}
			fos.flush();
			fos.close();
			is.close();
			return file;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
