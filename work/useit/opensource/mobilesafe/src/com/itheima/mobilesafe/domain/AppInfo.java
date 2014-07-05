package com.itheima.mobilesafe.domain;

import android.graphics.drawable.Drawable;

public class AppInfo {
	private Drawable appIcon;
	private String appName;
	private boolean inRom;
	private String packname;
	private String version;
	
	private boolean userApp;
	
	private boolean useNetwork;
	private boolean useGPS;
	private boolean useContact;
	
	
	
	
	public boolean isUseNetwork() {
		return useNetwork;
	}
	public void setUseNetwork(boolean useNetwork) {
		this.useNetwork = useNetwork;
	}
	public boolean isUseGPS() {
		return useGPS;
	}
	public void setUseGPS(boolean useGPS) {
		this.useGPS = useGPS;
	}
	public boolean isUseContact() {
		return useContact;
	}
	public void setUseContact(boolean useContact) {
		this.useContact = useContact;
	}
	public boolean isUserApp() {
		return userApp;
	}
	public void setUserApp(boolean userApp) {
		this.userApp = userApp;
	}
	public Drawable getAppIcon() {
		return appIcon;
	}
	public void setAppIcon(Drawable appIcon) {
		this.appIcon = appIcon;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public boolean isInRom() {
		return inRom;
	}
	public void setInRom(boolean inRom) {
		this.inRom = inRom;
	}
	public String getPackname() {
		return packname;
	}
	public void setPackname(String packname) {
		this.packname = packname;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	
}
