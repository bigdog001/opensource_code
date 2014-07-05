package com.itheima.mobilesafe.engine;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.itheima.mobilesafe.domain.AppInfo;

public class AppInfoProvider {

	/**
	 * 返回应用程序的信息
	 * @param context
	 * @return
	 */
	public static List<AppInfo> getAppinfos(Context context){
		PackageManager pm = context.getPackageManager();
		List<PackageInfo>  packinfos = pm.getInstalledPackages(PackageManager.GET_PERMISSIONS );
		List<AppInfo> appinfos = new ArrayList<AppInfo>();
		for(PackageInfo info : packinfos){
			AppInfo appInfo = new AppInfo();
			appInfo.setPackname(info.packageName);
			appInfo.setVersion(info.versionName);
			appInfo.setAppName(info.applicationInfo.loadLabel(pm).toString());
			appInfo.setAppIcon(info.applicationInfo.loadIcon(pm));
			if((info.applicationInfo.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE)==0){
				appInfo.setInRom(true);
			}else{
				appInfo.setInRom(false);
			}
			
			if(filterApp(info.applicationInfo)){
				appInfo.setUserApp(true);
			}else{
				appInfo.setUserApp(false);
			}
			//获取到某个应用程序的全部权限信息.
			String[] permissions = info.requestedPermissions;
			if(permissions!=null && permissions.length>0){
				for(String p: permissions){
					if("android.permission.INTERNET".equals(p)){
						appInfo.setUseNetwork(true);
					}else if("android.permission.ACCESS_FINE_LOCATION".equals(p)){
						appInfo.setUseGPS(true);
					}else if("android.permission.READ_CONTACTS".equals(p)){
						appInfo.setUseContact(true);
					}
				}
			}
			appinfos.add(appInfo);
			appInfo = null;
		}
		return appinfos;
	}
	
	/**
	 * 三方 用户自己安装软件的过滤器.
	 * @param info
	 * @return true 用户自己安装的软件
	 *         fasle  系统软件.
	 */
    public  static boolean filterApp(ApplicationInfo info) {
        if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
            return true;
        } else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            return true;
        }
        return false;
    }
	
}
