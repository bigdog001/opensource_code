package com.lee.tuliao.tools;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;

import com.lee.tuliao.activity.R;

public class TuliaoHelper {

	private static Context context;
	private static TuliaoHelper helper;

	private TuliaoHelper(Context context) {
		TuliaoHelper.context = context;
	}

	public static void init(Context context) {
		if (helper == null)
			helper = new TuliaoHelper(context);
	}

	public static int getVersion() {
		int version = -1;
		try {
			version = context.getPackageManager().getPackageInfo("com.chat", 0).versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return version;
	}

	public static String getVersionName() {
		String version = null;
		try {
			version = context.getPackageManager().getPackageInfo("com.chat", 0).versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return version;
	}

	public static String getAPKName() {
		String name = null;
		name = context.getResources().getString(R.string.app_name);
		return name;
	}

	public static String getMODEL() {
		return android.os.Build.MODEL;
	}

	public static boolean hasSDCard() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			return true;
		return false;
	}

	public static boolean checkNetwork() {
		boolean flag = false;
		ConnectivityManager cwjManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cwjManager.getActiveNetworkInfo() != null)
			flag = cwjManager.getActiveNetworkInfo().isAvailable();
		return flag;
	}

}
