package com.itheima.mobilesafe;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;

public class CleanSDActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clean_sd);

		// 1.����sd��.
		File file = Environment.getExternalStorageDirectory();

		File[] files = file.listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				String dirname = f.getName();
				// ��ѯ���ݿ�
				// if �ļ������������ݿ���� ,��ӵ�ǰĿ¼��һ��������ļ�������.
			}
		}
		// 2.չ���б� listviewչʾ. checkbox ���ƽ��̹����� ����.

		// 3.��Ӧ��Ŀ�ĵ������ ɾ�����Ŀ¼.
	}

	public void deleteFile(File f) {
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			for (File file : files) {
				deleteFile(file); //�ݹ����ɾ�� ��ǰĿ¼�µ����е��ļ�.
			}
		} else {
			f.delete();
		}
		f.delete();//������ļ��� ɾ����Ϻ� ���ļ���Ҳ�����.
	}
}
