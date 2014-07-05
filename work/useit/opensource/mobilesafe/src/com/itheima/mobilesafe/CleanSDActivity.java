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

		// 1.遍历sd卡.
		File file = Environment.getExternalStorageDirectory();

		File[] files = file.listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				String dirname = f.getName();
				// 查询数据库
				// if 文件名在清理数据库存在 ,添加当前目录到一个待清理的集合里面.
			}
		}
		// 2.展现列表 listview展示. checkbox 类似进程管理器 界面.

		// 3.相应条目的点击清理 删除这个目录.
	}

	public void deleteFile(File f) {
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			for (File file : files) {
				deleteFile(file); //递归调用删除 当前目录下的所有的文件.
			}
		} else {
			f.delete();
		}
		f.delete();//如果是文件夹 删除完毕后 把文件夹也清除掉.
	}
}
