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

	// ����һ����Ϣ������ �����߳����洴��
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case PARSE_SUCCESS:
				// �ȶԵ�ǰ�ͻ��˵İ汾�� ���������İ汾���Ƿ�һ��.
				if (getAppVersion().equals(updateInfo.getVersion())) {
					loadMainUI();
				} else {
					// �汾��ͬ,��ʾ�û�����.
					Logger.i(TAG, "�汾��ͬ,��ʾ�û�����");
					showUpdateDialog();

				}

				break;
			case PARSE_ERROR:
				Toast.makeText(getApplicationContext(), "����ʧ��", 0).show();
				loadMainUI();
				break;
			case SERVER_ERROR:
				Toast.makeText(getApplicationContext(), "�������쳣", 0).show();
				loadMainUI();
				break;
			case URL_ERROR:
				Toast.makeText(getApplicationContext(), "����·������", 0).show();
				loadMainUI();
				break;
			case NETWORK_ERROR:
				Toast.makeText(getApplicationContext(), "���������쳣", 0).show();
				loadMainUI();
				break;
			case DOWNLOAD_ERROR:
				Toast.makeText(getApplicationContext(), "����ʧ��.", 0).show();
				loadMainUI();
				break;
			case DOWNLOAD_SUCCESS:
				Toast.makeText(getApplicationContext(), "���سɹ�,��װapk", 0).show();
				File file = (File) msg.obj;
				installApk(file);

				break;
			case COPY_DB_ERROR:
				Toast.makeText(getApplicationContext(), "�������ݿ�ʧ��", 0).show();
				break;
			case COPY_DB_SUCCESS:
				Toast.makeText(getApplicationContext(), "�������ݿ�ɹ�", 0).show();
				break;
			}

		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// ����û��Ƿ��ǵ�һ�δ�Ӧ�ó���.
		super.onCreate(savedInstanceState);

		SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);

		boolean firstuse = sp.getBoolean("firstuse", true);
		if (firstuse) {//��һ��ʹ��Ӧ�ó���
			//�������ͼ��
			Intent intent = new Intent();
	    	intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
	    	intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "������");
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
		tv_splash_version.setText("�汾:" + getAppVersion());
		rl_splash_bg = (RelativeLayout) findViewById(R.id.rl_splash_bg);

		AlphaAnimation aa = new AlphaAnimation(0.2f, 1.0f);
		aa.setDuration(3000);
		rl_splash_bg.startAnimation(aa);

		// ���ӷ����� ��ȡ������Ϣ,�����̲߳���.
		new Thread(new CheckVersionTask()).start();

		// copyAddressDB();�������������ݿ�
		copyAddressDB();
		
		//�������ú������ݿ�
		copyCommonNumDB();
		
		
		//�����������ݿ�
		copyVirsuDB();

	}

	private void copyVirsuDB() {
		new Thread() {
			public void run() {
				// data/data/����/filesĿ¼
				File destFile = new File(getFilesDir(), "antivirus.db");
				if (destFile.exists() && destFile.length() > 0) {
					Logger.i(TAG, "�ļ��Ѿ�����,����Ҫ����");
				} else {
					File file = AssetCopyUtils.copy(getApplicationContext(),
							"antivirus.db", destFile.getAbsolutePath());
					Message msg = Message.obtain();
					if (file == null) {// ����ʧ��.
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
				// data/data/����/filesĿ¼
				File destFile = new File(getFilesDir(), "commonnum.db");
				if (destFile.exists() && destFile.length() > 0) {
					Logger.i(TAG, "�ļ��Ѿ�����,����Ҫ����");
				} else {
					File file = AssetCopyUtils.copy(getApplicationContext(),
							"commonnum.db", destFile.getAbsolutePath());
					Message msg = Message.obtain();
					if (file == null) {// ����ʧ��.
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
	 * ������������ص����ݿ�
	 */
	private void copyAddressDB() {

		new Thread() {
			public void run() {
				// data/data/����/filesĿ¼
				File destFile = new File(getFilesDir(), "address.db");
				if (destFile.exists() && destFile.length() > 0) {
					Logger.i(TAG, "�ļ��Ѿ�����,����Ҫ����");
				} else {
					File file = AssetCopyUtils.copy(getApplicationContext(),
							"address.jpg", destFile.getAbsolutePath());
					Message msg = Message.obtain();
					if (file == null) {// ����ʧ��.
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
	 * ��װһ���µ�apk
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
	 * ��ʾ�Զ����µĶԻ���
	 */
	protected void showUpdateDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("��������");
		builder.setIcon(R.drawable.notification);
		builder.setMessage(updateInfo.getDescription());
		builder.setPositiveButton("����", new OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				Logger.i(TAG, "����:" + updateInfo.getApkurl());
				// ���SD����״̬ ,�Ƿ�ɶ���д.
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {

					pd = new ProgressDialog(SplashActivity.this);
					pd.setTitle("����:");
					pd.setMessage("���ڸ���...");
					// ָ����ʾ����Ľ���
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
								// ���سɹ�..
								msg.what = DOWNLOAD_SUCCESS;
								msg.obj = file;
							} else {
								// ����ʧ��.
								msg.what = DOWNLOAD_ERROR;
							}
							handler.sendMessage(msg);
							pd.dismiss();
						};
					}.start();

				} else {
					Toast.makeText(getApplicationContext(), "sd��������...", 0)
							.show();
					loadMainUI();
				}
			}
		});
		builder.setNegativeButton("ȡ��", new OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				loadMainUI();
			}
		});
		// builder.create().show();
		builder.show(); // ��ʾ�Ի���
	}

	public class CheckVersionTask implements Runnable {
		long startTime;// ��¼������֮ǰ��ʱ��

		public void run() {
			SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
			boolean update = sp.getBoolean("update", true);
			if (!update) {
				// ����������.
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
			// ��ȡ�������ϵ�������Ϣ.
			try {
				URL url = new URL(getResources().getString(R.string.server_url));
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(2000);
				int code = conn.getResponseCode();
				if (code == 200) {
					// ����ɹ�.
					InputStream is = conn.getInputStream();
					updateInfo = UpdateInfoParser.getUpdateInfo(is);
					if (updateInfo != null) {
						// �����ɹ�
						msg.what = PARSE_SUCCESS;

					} else {
						// ����ʧ��..
						msg.what = PARSE_ERROR;
					}

				} else {
					// TODO:���ӷ���������ʧ��.
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
				long dTime = endTime - startTime;// ��������ļ��ʱ��.
				if (dTime < 2000) {
					try {
						Thread.sleep(2000 - dTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				// ������Ϣ�����߳�.
				handler.sendMessage(msg);
			}
		}

	}

	// ��ȡ��ǰӦ�ó���İ汾��
	public String getAppVersion() {
		// ��ȡ�ֻ��İ�������
		PackageManager pm = getPackageManager();
		try {
			PackageInfo packInfo = pm.getPackageInfo(getPackageName(), 0);
			return packInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			// �����ܷ���.
			// can't reach
			return "";

		}
	}

	/**
	 * ����������.
	 */
	public void loadMainUI() {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		// ��splash������رյ�
		this.finish();
	}

}