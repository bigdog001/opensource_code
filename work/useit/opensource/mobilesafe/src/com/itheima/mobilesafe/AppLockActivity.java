package com.itheima.mobilesafe;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.itheima.mobilesafe.db.dao.AppLockDao;
import com.itheima.mobilesafe.domain.AppInfo;
import com.itheima.mobilesafe.engine.AppInfoProvider;
import com.itheima.mobilesafe.utils.DelayExecuter;
import com.itheima.mobilesafe.utils.MyAsyncTask;

public class AppLockActivity extends Activity implements OnClickListener {
	private TextView tv_locked, tv_unlock;
	private LinearLayout ll_locked, ll_unlock;

	private ListView lv_locked, lv_unlock;

	private TextView tv_locked_count, tv_unlock_count;

	private AppLockDao dao;

	private List<AppInfo> unlockAppInfos;
	private List<AppInfo> lockedAppInfos;

	private AppLockAdapter unlockAdapter;
	private AppLockAdapter lockedAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_lock);
		dao = new AppLockDao(this);
		tv_locked_count = (TextView) findViewById(R.id.tv_locked_count);
		tv_unlock_count = (TextView) findViewById(R.id.tv_unlock_count);

		tv_locked = (TextView) findViewById(R.id.tv_locked);
		tv_unlock = (TextView) findViewById(R.id.tv_unlock);
		ll_locked = (LinearLayout) findViewById(R.id.ll_locked);
		ll_unlock = (LinearLayout) findViewById(R.id.ll_unlock);
		tv_locked.setOnClickListener(this);
		tv_unlock.setOnClickListener(this);
		lv_locked = (ListView) findViewById(R.id.lv_locked);
		lv_unlock = (ListView) findViewById(R.id.lv_unlock);
		List<AppInfo> allAppinfos = AppInfoProvider.getAppinfos(this);
		unlockAppInfos = new ArrayList<AppInfo>();
		lockedAppInfos = new ArrayList<AppInfo>();
		for (AppInfo info : allAppinfos) {
			if (dao.find(info.getPackname())) {
				lockedAppInfos.add(info);
			} else {
				unlockAppInfos.add(info);
			}

		}

		unlockAdapter = new AppLockAdapter(unlockAppInfos, true);
		lv_unlock.setAdapter(unlockAdapter);

		lockedAdapter = new AppLockAdapter(lockedAppInfos, false);
		lv_locked.setAdapter(lockedAdapter);

		lv_unlock.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {


				TranslateAnimation ta = new TranslateAnimation(
						Animation.RELATIVE_TO_SELF, 0,
						Animation.RELATIVE_TO_SELF, 1.0f,
						Animation.RELATIVE_TO_SELF, 0,
						Animation.RELATIVE_TO_SELF, 0);
				ta.setDuration(50);
				view.startAnimation(ta);
				
				
				new MyAsyncTask() {
					@Override
					public void onPreExecute() {
						
					}
					@Override
					public void onPostExecute() {
						AppInfo appinfo = (AppInfo) lv_unlock
								.getItemAtPosition(position);
						dao.add(appinfo.getPackname());// 添加锁定的程序到数据库
						unlockAppInfos.remove(appinfo);
						lockedAppInfos.add(appinfo);
						unlockAdapter.notifyDataSetChanged();// 通知界面更新.
					}
					
					@Override
					public void doInBackground() {
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}.execute();

			}
		});

		lv_locked.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
			
				

				TranslateAnimation ta = new TranslateAnimation(
						Animation.RELATIVE_TO_SELF, 0,
						Animation.RELATIVE_TO_SELF, -1.0f,
						Animation.RELATIVE_TO_SELF, 0,
						Animation.RELATIVE_TO_SELF, 0);
				ta.setDuration(500);
				view.startAnimation(ta);
				
				new DelayExecuter() {
					@Override
					public void doInUiThread() {
						AppInfo appinfo = (AppInfo) lv_locked
								.getItemAtPosition(position);
						dao.delete(appinfo.getPackname()); // 把这个程序从数据库里面移除
						lockedAppInfos.remove(appinfo);
						// 把这个移除的条目 重新加入到 未加锁的集合里面.
						unlockAppInfos.add(appinfo);
						lockedAdapter.notifyDataSetChanged();
					}
				}.delayExectue(500);

			}
		});

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_unlock:
			tv_unlock.setBackgroundResource(R.drawable.tab_left_pressed);
			tv_locked.setBackgroundResource(R.drawable.tab_right_default);

			ll_locked.setVisibility(View.INVISIBLE);
			ll_unlock.setVisibility(View.VISIBLE);
			unlockAdapter.notifyDataSetChanged();

			break;
		case R.id.tv_locked:
			tv_unlock.setBackgroundResource(R.drawable.tab_left_default);
			tv_locked.setBackgroundResource(R.drawable.tab_right_pressed);
			ll_locked.setVisibility(View.VISIBLE);
			ll_unlock.setVisibility(View.INVISIBLE);
			lockedAdapter.notifyDataSetChanged();
			break;
		}

	}

	private class AppLockAdapter extends BaseAdapter {

		private List<AppInfo> appinfos;
		private boolean unlock;// true 代表未加锁 false代表已经加锁

		public AppLockAdapter(List<AppInfo> appinfos, boolean unlock) {
			this.appinfos = appinfos;
			this.unlock = unlock;
		}

		public int getCount() {
			if (unlock) {
				tv_unlock_count.setText("未加锁软件(" + appinfos.size() + ")个");
			} else {
				tv_locked_count.setText("已加锁软件(" + appinfos.size() + ")个");
			}
			return appinfos.size();
		}

		public Object getItem(int position) {
			return appinfos.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			ViewHolder holder;
			if (convertView != null && convertView instanceof LinearLayout) {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			} else {
				view = View.inflate(getApplicationContext(),
						R.layout.list_applock_item, null);
				holder = new ViewHolder();
				holder.iv_icon = (ImageView) view
						.findViewById(R.id.iv_applock_icon);
				holder.tv_name = (TextView) view
						.findViewById(R.id.tv_applock_name);
				holder.iv_status = (ImageView) view
						.findViewById(R.id.iv_applock_status);
				view.setTag(holder);
			}
			AppInfo info = appinfos.get(position);
			holder.iv_icon.setImageDrawable(info.getAppIcon());
			holder.tv_name.setText(info.getAppName());
			if (unlock) {
				holder.iv_status.setImageResource(R.drawable.lock);
			} else {
				holder.iv_status.setImageResource(R.drawable.unlock);
			}

			return view;
		}

	}

	static class ViewHolder {
		TextView tv_name;
		ImageView iv_icon;
		ImageView iv_status;
	}

}
