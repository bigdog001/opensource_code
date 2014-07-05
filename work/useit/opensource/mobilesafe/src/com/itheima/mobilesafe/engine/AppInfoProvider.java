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
	 * ����Ӧ�ó������Ϣ
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
			//��ȡ��ĳ��Ӧ�ó����ȫ��Ȩ����Ϣ.
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
	 * ���� �û��Լ���װ����Ĺ�����.
	 * @param info
	 * @return true �û��Լ���װ�����
	 *         fasle  ϵͳ���.
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
