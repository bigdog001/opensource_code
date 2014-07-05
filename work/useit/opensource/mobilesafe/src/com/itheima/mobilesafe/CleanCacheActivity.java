package com.itheima.mobilesafe;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PackageStats;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.format.Formatter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CleanCacheActivity extends Activity {
	private ProgressBar pb;
	private TextView tv_status;
	private LinearLayout ll_container;
	private PackageManager pm;
	private Map<String, Long> cacheInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clean_cache);
		pb = (ProgressBar) findViewById(R.id.progressBar1);
		tv_status = (TextView) findViewById(R.id.tv_scan_status);
		ll_container = (LinearLayout) findViewById(R.id.ll_container);
		cacheInfo = new HashMap<String, Long>();
		pm = getPackageManager();
		new AsyncTask<Void, String, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				try {
					Thread.sleep(800);
					List<PackageInfo> infos = pm.getInstalledPackages(0);
					//pm.getInstalledApplications(flags);
					pb.setMax(infos.size());
					int total = 0;
					for (PackageInfo info : infos) {
						String packname = info.packageName;

						Method method = PackageManager.class.getDeclaredMethod(
								"getPackageSizeInfo", new Class[] {
										String.class,
										IPackageStatsObserver.class });
						method.invoke(pm, new Object[] { packname,
								new MyObserver(packname) });

						publishProgress("正在扫描:" + packname);
						total++;
						pb.setProgress(total);
						Thread.sleep(80);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPreExecute() {
				tv_status.setText("正在初始化超级无敌小豹缓存清理器...");
				super.onPreExecute();
			}

			@Override
			protected void onPostExecute(Void result) {
				if (cacheInfo.size() > 0) {
					tv_status.setText("扫描完毕发现缓存信息:" + cacheInfo.size() + "个 ");
					Set<Entry<String, Long>> sets = cacheInfo.entrySet();
					try {
						for (Entry<String, Long> entry : sets) {
							final String packname = entry.getKey();
							long cachesize = entry.getValue();
							View view = View.inflate(getApplicationContext(),
									R.layout.list_cache_item, null);
							ImageView iv = (ImageView) view
									.findViewById(R.id.iv_cache_icon);
							TextView tv_name = (TextView) view
									.findViewById(R.id.tv_cache_name);
							TextView tv_size = (TextView) view
									.findViewById(R.id.tv_cache_size);
							ApplicationInfo appinfo = pm.getApplicationInfo(
									packname, 0);
							iv.setImageDrawable(appinfo.loadIcon(pm));
							tv_name.setText(appinfo.loadLabel(pm));
							tv_size.setText(Formatter.formatFileSize(
									getApplicationContext(), cachesize));
							ll_container.addView(view);
							view.setOnClickListener(new OnClickListener() {

								public void onClick(View v) {

									if (Build.VERSION.SDK_INT >= 9) {
										// 适合2.3 以及以上系统
										Intent intent = new Intent();
										intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
										intent.addCategory("android.intent.category.DEFAULT");
										intent.setData(Uri.parse("package:"
												+ packname));
										startActivity(intent);
									} else {
										// 适合2.2 以及以下系统
										Intent intent = new Intent();
										intent.setAction("android.intent.action.VIEW");
										intent.addCategory("android.intent.category.DEFAULT");
										intent.addCategory("android.intent.category.VOICE_LAUNCH");
										intent.putExtra("pkg", packname);
										startActivity(intent);
									}
								}
							});
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
					tv_status.setText("扫描完毕,您的手机木有缓存...");
				}

				super.onPostExecute(result);
			}

			@Override
			protected void onProgressUpdate(String... values) {
				String msg = values[0];
				tv_status.setText(msg);
				super.onProgressUpdate(values);
			}

		}.execute();

	}

	private class MyObserver extends IPackageStatsObserver.Stub {
		private String packname;

		public MyObserver(String packname) {
			this.packname = packname;
		}

		public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
				throws RemoteException {
			long cache = pStats.cacheSize;
			long code = pStats.codeSize;
			long data = pStats.dataSize;
			if (cache > 0) {
				cacheInfo.put(packname, cache);
			}
		}

	}

	public void cleanAll(View view) {
		try {
			Method[] ms = PackageManager.class.getDeclaredMethods();
			for (Method m : ms) {
				if ("freeStorageAndNotify".equals(m.getName())) {
					m.invoke(pm, new Object[] { Long.MAX_VALUE,
							new MyDataObersver() });
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class MyDataObersver extends IPackageDataObserver.Stub {

		public void onRemoveCompleted(String packageName, boolean succeeded)
				throws RemoteException {

		}

	}
}
