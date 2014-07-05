package com.itheima.mobilesafe.engine;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Debug.MemoryInfo;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.domain.TaskInfo;

public class TaskInfoProvider {

	/**
	 * 返回所有的进程列表信息
	 * @param context
	 * @return
	 */
	public static List<TaskInfo> getTaskInfos(Context context){
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> appProcessInfos = am.getRunningAppProcesses();
		List<TaskInfo> taskInfos = new ArrayList<TaskInfo>();
		PackageManager pm = context.getPackageManager();
		for(RunningAppProcessInfo appProcessInfo : appProcessInfos){
			String packname = appProcessInfo.processName;
			TaskInfo taskInfo = new TaskInfo();
			taskInfo.setPackname(packname);
			
			MemoryInfo[] memoryInfos = am.getProcessMemoryInfo(new int[]{appProcessInfo.pid});
			long memsize = memoryInfos[0].getTotalPrivateDirty() * 1024;
			taskInfo.setMemsize(memsize);
			try {
				PackageInfo packInfo = pm.getPackageInfo(packname, 0);
				Drawable icon = packInfo.applicationInfo.loadIcon(pm);
				taskInfo.setIcon(icon);
				String name = packInfo.applicationInfo.loadLabel(pm).toString();
				taskInfo.setName(name);
				if(AppInfoProvider.filterApp(packInfo.applicationInfo)){
					taskInfo.setUserTask(true);
				}else{
					taskInfo.setUserTask(false);
				}
			} catch (NameNotFoundException e) {
				taskInfo.setIcon(context.getResources().getDrawable(R.drawable.ic_launcher));
				taskInfo.setName(packname);
				e.printStackTrace();
			}
			
			taskInfos.add(taskInfo);
		}
		return taskInfos;
	}
}
