package com.itheima.mobilesafe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Text;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import com.itheima.mobilesafe.utils.Logger;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.mobilesafe.domain.AppInfo;
import com.itheima.mobilesafe.engine.AppInfoProvider;
import com.itheima.mobilesafe.utils.DensityUtil;
import com.itheima.mobilesafe.utils.Logger;
import com.itheima.mobilesafe.utils.MyAsyncTask;

public class AppManagerActivity extends Activity implements OnClickListener {
	protected static final String TAG = "AppManagerActivity";
	private TextView tv_appmanger_rom, tv_appmanger_sd;
	private ListView lv_appmanager;
	private List<AppInfo> appInfos;

	// ���ֶ���������ֲ�ͬ����Ӧ�ó���ļ���
	private List<AppInfo> userAppInfos;
	private List<AppInfo> systemAppInfos;
	private TextView tv_app_status;
	private ProgressBar pb_appmanger;
	private boolean loadfinish;

	private PopupWindow popwindow;

	// ���������ؼ�
	private View ll_share;
	private View ll_start;
	private View ll_uninstall;

	private AppInfo selectedAppInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_manager);
		tv_appmanger_rom = (TextView) findViewById(R.id.tv_appmanger_rom);
		tv_appmanger_sd = (TextView) findViewById(R.id.tv_appmanger_sd);
		tv_appmanger_rom.setText("�����ڴ�:" + getAvailRom());
		tv_appmanger_sd.setText("����SD��:" + getAvailSd());
		tv_app_status = (TextView) findViewById(R.id.tv_app_status);
		lv_appmanager = (ListView) findViewById(R.id.lv_appmanager);
		pb_appmanger = (ProgressBar) findViewById(R.id.pb_appmanger);
	

		fillData();

		lv_appmanager.setOnScrollListener(new OnScrollListener() {

			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			/**
			 * �����������ʱ����õķ���.
			 * 
			 * @param view
			 * @param firstVisibleItem
			 * @param visibleItemCount
			 * @param totalItemCount
			 */
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (loadfinish) {
					if (firstVisibleItem >= (userAppInfos.size() + 1)) {
						tv_app_status.setText("ϵͳӦ��(" + systemAppInfos.size()
								+ ")��");
					} else {
						tv_app_status.setText("�û�Ӧ��(" + userAppInfos.size()
								+ ")��");
					}
				}

				dismissPopupWindow();

			}
		});

		lv_appmanager.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 1.֪�����������Ŀ����һ��.

				Object obj = lv_appmanager.getItemAtPosition(position);
				if (obj != null) {

					dismissPopupWindow();
					selectedAppInfo = (AppInfo) obj;
					View contentView = View.inflate(getApplicationContext(),
							R.layout.popup_appmanger, null);

					ll_share = contentView.findViewById(R.id.ll_share);
					ll_start = contentView.findViewById(R.id.ll_start);
					ll_uninstall = contentView.findViewById(R.id.ll_uninstall);
					ll_share.setOnClickListener(AppManagerActivity.this);
					ll_start.setOnClickListener(AppManagerActivity.this);
					ll_uninstall.setOnClickListener(AppManagerActivity.this);

					popwindow = new PopupWindow(contentView, -2, -2);
					// PopupWindow popwindow = new PopupWindow(tv,
					// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
					// true);
					popwindow.setBackgroundDrawable(new ColorDrawable(
							Color.TRANSPARENT));
					int[] location = new int[2];
					view.getLocationInWindow(location);
					popwindow.showAtLocation(parent,
							Gravity.LEFT | Gravity.TOP, DensityUtil.dip2px(getApplicationContext(), location[0] + 70),
							location[1]);

					ScaleAnimation sa = new ScaleAnimation(0.2f, 1.0f, 0.4f,
							1.0f);
					sa.setDuration(400);
					// contentView.startAnimation(sa);

					TranslateAnimation ta = new TranslateAnimation(
							Animation.RELATIVE_TO_SELF, 0,
							Animation.RELATIVE_TO_SELF, 0.1f,
							Animation.RELATIVE_TO_SELF, 0,
							Animation.RELATIVE_TO_SELF, 0);
					ta.setDuration(300);

					AnimationSet set = new AnimationSet(false);
					set.addAnimation(sa);
					set.addAnimation(ta);
					contentView.startAnimation(set); // ����һ�鶯��.
				}

			}
		});

	}

	private void fillData() {
		new MyAsyncTask() {

			@Override
			public void onPreExecute() {
				pb_appmanger.setVisibility(View.VISIBLE);
				loadfinish = false;

			}

			@Override
			public void onPostExecute() {
				lv_appmanager.setAdapter(new AppManagerAdapter());
				pb_appmanger.setVisibility(View.INVISIBLE);
				loadfinish = true;
			}

			@Override
			public void doInBackground() {
				appInfos = AppInfoProvider.getAppinfos(AppManagerActivity.this);
				userAppInfos = new ArrayList<AppInfo>();
				systemAppInfos = new ArrayList<AppInfo>();
				for (AppInfo info : appInfos) {
					if (info.isUserApp()) {
						userAppInfos.add(info);
					} else {
						systemAppInfos.add(info);
					}
				}

			}
		}.execute();
	}

	private class AppManagerAdapter extends BaseAdapter {

		/**
		 * ����ÿһ��λ�õ���Ŀ�Ƿ����(���Ա����)
		 */
		@Override
		public boolean isEnabled(int position) {
			if (position == 0 || position == (userAppInfos.size() + 1)) {
				return false;
			} else {
				return true;
			}
		}

		public int getCount() {
			return userAppInfos.size() + 1 + systemAppInfos.size() + 1; // ��Ϊ����������textview�ı�ǩ
																		// ����listview������Ҫ��������Ŀ
		}

		public Object getItem(int position) {
			AppInfo appinfo;
			if (position == 0 || position == (userAppInfos.size() + 1)) {
				return null;
			} else if (position <= userAppInfos.size()) {
				int newposition = position - 1;
				appinfo = userAppInfos.get(newposition);
			} else {
				int newposition = position - 1 - userAppInfos.size() - 1;
				appinfo = systemAppInfos.get(newposition);
			}
			return appinfo;
		}

		public long getItemId(int position) {
			return 0;
		}

		/**
		 * ����ÿһ��λ�õ�view����.
		 */
		public View getView(int position, View convertView, ViewGroup parent) {
			AppInfo appinfo = null;

			if (position == 0) {// ��һ��λ����ʾһ��textview��С��ǩ.
				TextView tv = new TextView(getApplicationContext());
				tv.setTextColor(Color.BLACK);
				tv.setBackgroundColor(Color.GRAY);
				tv.setText("�û�����(" + userAppInfos.size() + ")��");
				return tv;
			} else if (position == (userAppInfos.size() + 1)) {
				TextView tv = new TextView(getApplicationContext());
				tv.setTextColor(Color.BLACK);
				tv.setBackgroundColor(Color.GRAY);
				tv.setText("ϵͳ����(" + systemAppInfos.size() + ")��");
				return tv;
			} else if (position <= userAppInfos.size()) {
				int newposition = position - 1;
				appinfo = userAppInfos.get(newposition);
			} else {
				int newposition = position - 1 - userAppInfos.size() - 1;
				appinfo = systemAppInfos.get(newposition);
			}

			View view;
			ViewHolder holder;
			if (convertView != null && convertView instanceof RelativeLayout) {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			} else {
				view = View.inflate(getApplicationContext(),
						R.layout.list_appmanager_item, null);
				holder = new ViewHolder();
				holder.iv_icon = (ImageView) view
						.findViewById(R.id.iv_appmanger_icon);
				holder.tv_name = (TextView) view
						.findViewById(R.id.tv_appmanager_name);
				holder.tv_location = (TextView) view
						.findViewById(R.id.tv_appmanager_location);
				holder.tv_version = (TextView) view
						.findViewById(R.id.tv_appmanager_version);
				holder.ll_info = (LinearLayout) view.findViewById(R.id.ll_info);
				view.setTag(holder);
			}
			holder.iv_icon.setImageDrawable(appinfo.getAppIcon());
			holder.tv_name.setText(appinfo.getAppName());
			holder.tv_version.setText(appinfo.getVersion());
			String tag = "";
			if (appinfo.isUserApp()) {
				tag = "(�û�)";
			} else {
				tag = "(ϵͳ)";
			}

			if (appinfo.isInRom()) {
				holder.tv_location.setText("�ֻ��ڴ�" + tag);
			} else {
				holder.tv_location.setText("SD��" + tag);
			}
			holder.ll_info.removeAllViews();//�����view���󶼸������. ���³�ʼ���������������
			int size = DensityUtil.dip2px(getApplicationContext(), 20);
			if(appinfo.isUseGPS()){
				ImageView iv = new ImageView(getApplicationContext());
				iv.setImageResource(R.drawable.gps);
				holder.ll_info.addView(iv, size, size);
			}
			if(appinfo.isUseContact()){
				ImageView iv = new ImageView(getApplicationContext());
				iv.setImageResource(R.drawable.contact);
				holder.ll_info.addView(iv, size, size);
			}
			if(appinfo.isUseNetwork()){
				ImageView iv = new ImageView(getApplicationContext());
				iv.setImageResource(R.drawable.net);
				holder.ll_info.addView(iv, size, size);
			}
			
			return view;
		}

	}

	static class ViewHolder {
		ImageView iv_icon;
		TextView tv_name;
		TextView tv_location;
		TextView tv_version;
		LinearLayout ll_info;
	}

	private String getAvailRom() {
		File file = Environment.getDataDirectory();
		StatFs statf = new StatFs(file.getAbsolutePath());
		long count = statf.getAvailableBlocks();
		long size = statf.getBlockSize();
		return Formatter.formatFileSize(this, count * size);
	}

	private String getAvailSd() {
		File file = Environment.getExternalStorageDirectory();
		StatFs statf = new StatFs(file.getAbsolutePath());
		long count = statf.getAvailableBlocks();
		long size = statf.getBlockSize();
		return Formatter.formatFileSize(this, count * size);
	}

	private void dismissPopupWindow() {
		if (popwindow != null && popwindow.isShowing()) {
			popwindow.dismiss();
			popwindow = null;

		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		dismissPopupWindow();
	}

	public void onClick(View v) {
		dismissPopupWindow();
		switch (v.getId()) {
		case R.id.ll_share:
			Logger.i(TAG, "����:" + selectedAppInfo.getPackname());
			shareApk();
			break;

		case R.id.ll_start:
			Logger.i(TAG, "����:" + selectedAppInfo.getPackname());
			startApk();
			break;
		case R.id.ll_uninstall:
			Logger.i(TAG, "ж��:" + selectedAppInfo.getPackname());
			uninstallApk();
			break;
		}

	}

	/**
	 * ����һ��Ӧ�ó���
	 */
	private void shareApk() {
		Intent intent = new Intent();
		// <intent-filter>
		// <action android:name="android.intent.action.SEND" />
		// <category android:name="android.intent.category.DEFAULT" />
		// <data android:mimeType="text/plain" />
		// </intent-filter>
		intent.setAction(Intent.ACTION_SEND);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.setType("text/plain");
		intent.putExtra(
				Intent.EXTRA_TEXT,
				"�Ƽ���ʹ��һ�����,����Ϊ" + selectedAppInfo.getAppName()
						+ "���ص�ַ:google play xxx,�汾:"
						+ selectedAppInfo.getVersion());
		startActivity(intent);
	}

	/**
	 * ����һ��Ӧ�ó���
	 */
	private void startApk() {
		// 1.��ȡ���������Ӧ�õ�intent
		// 2. ��ȡ�������Ӧ�ó����һ��activity����ͼ
		PackageManager pm = getPackageManager();
		try {
			PackageInfo info = pm.getPackageInfo(selectedAppInfo.getPackname(),
					PackageManager.GET_ACTIVITIES);
			ActivityInfo[] activityInfos = info.activities;
			if (activityInfos != null && activityInfos.length > 0) {
				ActivityInfo activitInfo = activityInfos[0];
				Intent intent = new Intent();
				intent.setClassName(selectedAppInfo.getPackname(),
						activitInfo.name);
				startActivity(intent);
			} else {
				Toast.makeText(this, "�޷�����Ӧ�ó���", 0).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "�޷�����Ӧ�ó���", 0).show();
		}

	}

	private void uninstallApk() {
		// <intent-filter>
		// <action android:name="android.intent.action.VIEW" />
		// <action android:name="android.intent.action.DELETE" />
		// <category android:name="android.intent.category.DEFAULT" />
		// <data android:scheme="package" />
		// </intent-filter>

		if (selectedAppInfo.isUserApp()) {
			Intent intent = new Intent();
			intent.setAction("android.intent.action.VIEW");
			intent.setAction("android.intent.action.DELETE");
			intent.addCategory("android.intent.category.DEFAULT");
			intent.setData(Uri.parse("package:" + selectedAppInfo.getPackname()));
			startActivityForResult(intent, 200);
		} else {
			Toast.makeText(this, "ϵͳӦ����ҪrootȨ�޲���ж��", 1).show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 200) {
			// �����������.
			fillData();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
