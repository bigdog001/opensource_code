package com.itheima.mobilesafe;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources.NotFoundException;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import com.itheima.mobilesafe.utils.Logger;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.itheima.mobilesafe.domain.UpdateInfo;
import com.itheima.mobilesafe.engine.UpdateInfoParser;
import com.itheima.mobilesafe.service.CallSmsFirewallService;
import com.itheima.mobilesafe.service.CallSmsSafeService;
import com.itheima.mobilesafe.utils.AssetCopyUtils;
import com.itheima.mobilesafe.utils.DownLoadUtils;

public class SplashActivity extends Activity {
	public static final int PARSE_SUCCESS = 10;
	public static final int PARSE_ERROR = 11;
	public static final int SERVER_ERROR = 12;
	public static final int URL_ERROR = 13;
	public static final int NETWORK_ERROR = 14;
	public static final int DOWNLOAD_SUCCESS = 15;
	public static final int DOWNLOAD_ERROR = 16;

	protected static final String TAG = "SplashActivity";
	protected static final int COPY_DB_ERROR = 17;
	protected static final int COPY_DB_SUCCESS = 18;
	private TextView tv_splash_version;
	private RelativeLayout rl_splash_bg;

	private ProgressDialog pd;

	private UpdateInfo updateInfo;

	// 定义一个消息处理器 在主线程里面创建
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case PARSE_SUCCESS:
				// 比对当前客户端的版本号 跟服务器的版本号是否一致.
				if (getAppVersion().equals(updateInfo.getVersion())) {
					loadMainUI();
				} else {
					// 版本不同,提示用户升级.
					Logger.i(TAG, "版本不同,提示用户升级");
					showUpdateDialog();

				}

				break;
			case PARSE_ERROR:
				Toast.makeText(getApplicationContext(), "解析失败", 0).show();
				loadMainUI();
				break;
			case SERVER_ERROR:
				Toast.makeText(getApplicationContext(), "服务器异常", 0).show();
				loadMainUI();
				break;
			case URL_ERROR:
				Toast.makeText(getApplicationContext(), "网络路径错误", 0).show();
				loadMainUI();
				break;
			case NETWORK_ERROR:
				Toast.makeText(getApplicationContext(), "网络连接异常", 0).show();
				loadMainUI();
				break;
			case DOWNLOAD_ERROR:
				Toast.makeText(getApplicationContext(), "下载失败.", 0).show();
				loadMainUI();
				break;
			case DOWNLOAD_SUCCESS:
				Toast.makeText(getApplicationContext(), "下载成功,安装apk", 0).show();
				File file = (File) msg.obj;
				installApk(file);

				break;
			case COPY_DB_ERROR:
				Toast.makeText(getApplicationContext(), "拷贝数据库失败", 0).show();
				break;
			case COPY_DB_SUCCESS:
				Toast.makeText(getApplicationContext(), "拷贝数据库成功", 0).show();
				break;
			}

		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// 检测用户是否是第一次打开应用程序.
		super.onCreate(savedInstanceState);

		SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);

		boolean firstuse = sp.getBoolean("firstuse", true);
		if (firstuse) {//第一次使用应用程序
			//创建快捷图标
			Intent intent = new Intent();
	    	intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
	    	intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "神马护卫");
	    	//Intent callIntent = new Intent(this,HomeActivity.class);
	    	Intent homeIntent = new Intent();
	    	homeIntent.setAction("com.itheima.home");
	    	homeIntent.addCategory(Intent.CATEGORY_DEFAULT);
	    	intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, homeIntent);
	    	intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, BitmapFactory.decodeResource(getResources(), R.drawable.shenmabg));
	    	sendBroadcast(intent);
		} 
			
		Editor editor = sp.edit();
		editor.putBoolean("firstuse", false);
		editor.commit();
		
		boolean showaddress = sp.getBoolean("showaddress", false);
		boolean firewall = sp.getBoolean("firewall", false);
		if(showaddress){
			startService(new Intent(this,CallSmsSafeService.class));
		}
		if(firewall){
			startService(new Intent(this,CallSmsFirewallService.class));
		}
		
		
		setContentView(R.layout.activity_splash);
		tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);
		tv_splash_version.setText("版本:" + getAppVersion());
		rl_splash_bg = (RelativeLayout) findViewById(R.id.rl_splash_bg);

		AlphaAnimation aa = new AlphaAnimation(0.2f, 1.0f);
		aa.setDuration(3000);
		rl_splash_bg.startAnimation(aa);

		// 连接服务器 获取更新信息,在子线程操作.
		new Thread(new CheckVersionTask()).start();

		// copyAddressDB();拷贝归属地数据库
		copyAddressDB();
		
		//拷贝常用号码数据库
		copyCommonNumDB();
		
		
		//拷贝病毒数据库
		copyVirsuDB();

	}

	private void copyVirsuDB() {
		new Thread() {
			public void run() {
				// data/data/包名/files目录
				File destFile = new File(getFilesDir(), "antivirus.db");
				if (destFile.exists() && destFile.length() > 0) {
					Logger.i(TAG, "文件已经存在,不需要拷贝");
				} else {
					File file = AssetCopyUtils.copy(getApplicationContext(),
							"antivirus.db", destFile.getAbsolutePath());
					Message msg = Message.obtain();
					if (file == null) {// 拷贝失败.
						msg.what = COPY_DB_ERROR;
					} else {
						msg.what = COPY_DB_SUCCESS;
					}
					handler.sendMessage(msg);
				}
				;
			}
		}.start();
		
	}

	private void copyCommonNumDB() {
		new Thread() {
			public void run() {
				// data/data/包名/files目录
				File destFile = new File(getFilesDir(), "commonnum.db");
				if (destFile.exists() && destFile.length() > 0) {
					Logger.i(TAG, "文件已经存在,不需要拷贝");
				} else {
					File file = AssetCopyUtils.copy(getApplicationContext(),
							"commonnum.db", destFile.getAbsolutePath());
					Message msg = Message.obtain();
					if (file == null) {// 拷贝失败.
						msg.what = COPY_DB_ERROR;
					} else {
						msg.what = COPY_DB_SUCCESS;
					}
					handler.sendMessage(msg);
				}
				;
			}
		}.start();
	}

	/**
	 * 拷贝号码归属地的数据库
	 */
	private void copyAddressDB() {

		new Thread() {
			public void run() {
				// data/data/包名/files目录
				File destFile = new File(getFilesDir(), "address.db");
				if (destFile.exists() && destFile.length() > 0) {
					Logger.i(TAG, "文件已经存在,不需要拷贝");
				} else {
					File file = AssetCopyUtils.copy(getApplicationContext(),
							"address.jpg", destFile.getAbsolutePath());
					Message msg = Message.obtain();
					if (file == null) {// 拷贝失败.
						msg.what = COPY_DB_ERROR;
					} else {
						msg.what = COPY_DB_SUCCESS;
					}
					handler.sendMessage(msg);
				}
				;
			}
		}.start();

	}

	/**
	 * 安装一个新的apk
	 * 
	 * @param file
	 */
	protected void installApk(File file) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		// intent.setType("application/vnd.android.package-archive");
		// intent.setData(Uri.fromFile(file));
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		startActivity(intent);
	}

	/**
	 * 显示自动更新的对话框
	 */
	protected void showUpdateDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("升级提醒");
		builder.setIcon(R.drawable.notification);
		builder.setMessage(updateInfo.getDescription());
		builder.setPositiveButton("升级", new OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				Logger.i(TAG, "下载:" + updateInfo.getApkurl());
				// 检查SD卡的状态 ,是否可读可写.
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {

					pd = new ProgressDialog(SplashActivity.this);
					pd.setTitle("提醒:");
					pd.setMessage("正在更新...");
					// 指定显示具体的进度
					pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
					pd.show();
					final File f = new File(Environment
							.getExternalStorageDirectory(), DownLoadUtils
							.getFileName(updateInfo.getApkurl()));
					new Thread() {
						public void run() {
							File file = DownLoadUtils.download(
									updateInfo.getApkurl(),
									f.getAbsolutePath(), pd);
							Message msg = Message.obtain();
							if (file != null) {
								// 下载成功..
								msg.what = DOWNLOAD_SUCCESS;
								msg.obj = file;
							} else {
								// 下载失败.
								msg.what = DOWNLOAD_ERROR;
							}
							handler.sendMessage(msg);
							pd.dismiss();
						};
					}.start();

				} else {
					Toast.makeText(getApplicationContext(), "sd卡不可用...", 0)
							.show();
					loadMainUI();
				}
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				loadMainUI();
			}
		});
		// builder.create().show();
		builder.show(); // 显示对话框
	}

	public class CheckVersionTask implements Runnable {
		long startTime;// 记录任务开启之前的时间

		public void run() {
			SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
			boolean update = sp.getBoolean("update", true);
			if (!update) {
				// 进入主界面.
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				loadMainUI();
				return;
			}

			startTime = System.currentTimeMillis();
			Message msg = Message.obtain();
			// 获取服务器上的配置信息.
			try {
				URL url = new URL(getResources().getString(R.string.server_url));
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(2000);
				int code = conn.getResponseCode();
				if (code == 200) {
					// 请求成功.
					InputStream is = conn.getInputStream();
					updateInfo = UpdateInfoParser.getUpdateInfo(is);
					if (updateInfo != null) {
						// 解析成功
						msg.what = PARSE_SUCCESS;

					} else {
						// 解析失败..
						msg.what = PARSE_ERROR;
					}

				} else {
					// TODO:连接服务器请求失败.
					msg.what = SERVER_ERROR;
				}

			} catch (MalformedURLException e) {
				e.printStackTrace();
				msg.what = URL_ERROR;
			} catch (NotFoundException e) {
				e.printStackTrace();
				msg.what = URL_ERROR;
			} catch (IOException e) {
				e.printStackTrace();
				msg.what = NETWORK_ERROR;
			} finally {
				long endTime = System.currentTimeMillis();
				long dTime = endTime - startTime;// 访问网络的间隔时间.
				if (dTime < 2000) {
					try {
						Thread.sleep(2000 - dTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				// 发送消息给主线程.
				handler.sendMessage(msg);
			}
		}

	}

	// 获取当前应用程序的版本号
	public String getAppVersion() {
		// 获取手机的包管理者
		PackageManager pm = getPackageManager();
		try {
			PackageInfo packInfo = pm.getPackageInfo(getPackageName(), 0);
			return packInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			// 不可能发生.
			// can't reach
			return "";

		}
	}

	/**
	 * 进入主界面.
	 */
	public void loadMainUI() {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		// 把splash界面给关闭掉
		this.finish();
	}

}