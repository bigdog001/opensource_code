package com.itheima.mobilesafe;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Relation;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.mobilesafe.domain.TaskInfo;
import com.itheima.mobilesafe.engine.TaskInfoProvider;
import com.itheima.mobilesafe.utils.MyAsyncTask;
import com.itheima.mobilesafe.utils.ProcessStatusUtils;

public class TaskManagerActivity extends Activity {
	private TextView tv_taskmanger_count;
	private TextView tv_taskmanger_mem;
	private long availRam;
	private int processCount;
	private ListView lv_taskmanager;
	private ProgressBar pb_taskmanger;
	private List<TaskInfo> taskInfos;
	private List<TaskInfo> userTaskInfos;
	private List<TaskInfo> systemTaskInfos;

	private TaskAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_manager);
		tv_taskmanger_mem = (TextView) findViewById(R.id.tv_taskmanger_mem);
		tv_taskmanger_count = (TextView) findViewById(R.id.tv_taskmanger_count);

		availRam = ProcessStatusUtils.getAvailRAM(this);
		processCount = ProcessStatusUtils.getProcessCount(this);

		tv_taskmanger_mem.setText("可用/总内存:"
				+ Formatter.formatFileSize(this, availRam)
				+ "/"
				+ Formatter.formatFileSize(getApplicationContext(),
						ProcessStatusUtils.getTotalRAM()));
		tv_taskmanger_count.setText("运行中进程:" + processCount + "个");

		lv_taskmanager = (ListView) findViewById(R.id.lv_taskmanager);

		pb_taskmanger = (ProgressBar) findViewById(R.id.pb_taskmanger);

		lv_taskmanager.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 1.检查当前的条目是否是别选择了..
				Object obj = lv_taskmanager.getItemAtPosition(position);
				if (obj != null) {
					TaskInfo taskInfo = (TaskInfo) obj;
					if (taskInfo.getPackname().equals(getPackageName())) {
						return;
					}
					CheckBox cb = (CheckBox) view
							.findViewById(R.id.cb_taskmanager);
					if (taskInfo.isChecked()) {
						cb.setChecked(false);
						taskInfo.setChecked(false);
					} else {
						cb.setChecked(true);
						taskInfo.setChecked(true);
					}
				}

			}
		});

		fillData();
	}

	private void fillData() {
		new MyAsyncTask() {

			@Override
			public void onPreExecute() {
				pb_taskmanger.setVisibility(View.VISIBLE);
			}

			@Override
			public void onPostExecute() {
				pb_taskmanger.setVisibility(View.INVISIBLE);
				adapter = new TaskAdapter();
				lv_taskmanager.setAdapter(adapter);
			}

			@Override
			public void doInBackground() {
				taskInfos = TaskInfoProvider
						.getTaskInfos(getApplicationContext());
				userTaskInfos = new ArrayList<TaskInfo>();
				systemTaskInfos = new ArrayList<TaskInfo>();
				for (TaskInfo info : taskInfos) {
					if (info.isUserTask()) {
						userTaskInfos.add(info);
					} else {
						systemTaskInfos.add(info);
					}
				}
			}
		}.execute();
	}

	private class TaskAdapter extends BaseAdapter {

		public int getCount() {
			SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
			boolean showsystem = sp.getBoolean("showsystem", false);
			if (showsystem) {
				return userTaskInfos.size() + 1 + systemTaskInfos.size() + 1;
			}else{
				return userTaskInfos.size() + 1;
			}
		}

		public Object getItem(int position) {
			TaskInfo taskinfo;
			if (position == 0 || position == (userTaskInfos.size() + 1)) {
				return null;
			} else if (position <= userTaskInfos.size()) {
				taskinfo = userTaskInfos.get(position - 1);
			} else {// 系统进程
				int newposition = position - 1 - userTaskInfos.size() - 1;
				taskinfo = systemTaskInfos.get(newposition);
			}
			return taskinfo;

		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			TaskInfo taskinfo = null;
			if (position == 0) {
				TextView tv = new TextView(getApplicationContext());
				tv.setText("用户进程:" + userTaskInfos.size() + "个");
				tv.setBackgroundColor(Color.GRAY);
				tv.setTextColor(Color.BLACK);
				return tv;
			} else if (position == (userTaskInfos.size() + 1)) {
				TextView tv = new TextView(getApplicationContext());
				tv.setText("系统进程:" + systemTaskInfos.size() + "个");
				tv.setBackgroundColor(Color.GRAY);
				tv.setTextColor(Color.BLACK);
				return tv;
			} else if (position <= userTaskInfos.size()) {
				taskinfo = userTaskInfos.get(position - 1);
			} else {// 系统进程
				int newposition = position - 1 - userTaskInfos.size() - 1;
				taskinfo = systemTaskInfos.get(newposition);
			}
			View view;
			ViewHolder holder;
			if (convertView != null && convertView instanceof RelativeLayout) {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			} else {
				view = View.inflate(getApplicationContext(),
						R.layout.list_taskmanager_item, null);
				holder = new ViewHolder();
				holder.iv_icon = (ImageView) view
						.findViewById(R.id.iv_taskmanger_icon);
				holder.tv_mem = (TextView) view
						.findViewById(R.id.tv_taskmanager_mem);
				holder.tv_name = (TextView) view
						.findViewById(R.id.tv_taskmanager_name);
				holder.cb = (CheckBox) view.findViewById(R.id.cb_taskmanager);
				view.setTag(holder);
			}
			holder.iv_icon.setImageDrawable(taskinfo.getIcon());
			holder.tv_name.setText(taskinfo.getName());
			holder.tv_mem.setText("内存占用:"
					+ Formatter.formatFileSize(getApplicationContext(),
							taskinfo.getMemsize()));
			if (taskinfo.isChecked()) {
				holder.cb.setChecked(true);
			} else {
				holder.cb.setChecked(false);
			}

			if (taskinfo.getPackname().equals(getPackageName())) {// 当前自己的应用
																	// 黑马卫士
																	// //隐藏掉
																	// checkbox.
				holder.cb.setVisibility(View.INVISIBLE);
			} else {
				holder.cb.setVisibility(View.VISIBLE);
			}

			return view;
		}

	}

	static class ViewHolder {
		TextView tv_name;
		TextView tv_mem;
		ImageView iv_icon;
		CheckBox cb;
	}

	public void selectAll(View view) {
		for (TaskInfo info : userTaskInfos) {
			if (info.getPackname().equals(getPackageName())) {

			} else {
				info.setChecked(true);
			}
		}
		for (TaskInfo info : systemTaskInfos) {
			info.setChecked(true);
		}
		adapter.notifyDataSetChanged();
	}

	public void unSelectAll(View view) {
		for (TaskInfo info : userTaskInfos) {
			info.setChecked(false);
		}
		for (TaskInfo info : systemTaskInfos) {
			info.setChecked(false);
		}
		adapter.notifyDataSetChanged();
	}

	public void killAll(View view) {
		List<TaskInfo> killedProcess = new ArrayList<TaskInfo>();
		long mem = 0;
		ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		for (TaskInfo info : userTaskInfos) {
			if (info.isChecked()) {
				am.killBackgroundProcesses(info.getPackname());
				killedProcess.add(info);
				mem += info.getMemsize();
			}
		}
		for (TaskInfo info : systemTaskInfos) {
			if (info.isChecked()) {
				am.killBackgroundProcesses(info.getPackname());
				killedProcess.add(info);
				mem += info.getMemsize();
			}
		}
		// fillData();
		// 不能重新刷新数据 更新界面.
		// 把所有用户选择的条目从界面上给移除.
		for (TaskInfo info : killedProcess) {
			if (info.isUserTask()) {
				userTaskInfos.remove(info);
			} else {
				systemTaskInfos.remove(info);
			}
		}
		adapter.notifyDataSetChanged();
		Toast.makeText(
				this,
				"杀死了" + killedProcess.size() + "进程,释放了"
						+ Formatter.formatFileSize(this, mem) + "的内存", 1)
				.show();
		processCount -= killedProcess.size();// 计算出来新的正在运行的进程数.
		availRam += mem;// 计算出新的内存.
		tv_taskmanger_mem.setText("可用/总内存:"
				+ Formatter.formatFileSize(this, availRam)
				+ "/"
				+ Formatter.formatFileSize(getApplicationContext(),
						ProcessStatusUtils.getTotalRAM()));
		tv_taskmanger_count.setText("运行中进程:" + processCount + "个");
	}

	public void setting(View view) {
		Intent intent = new Intent(this, TaskSettingActivity.class);
		startActivityForResult(intent, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		adapter.notifyDataSetChanged();// 更界面返回的时候 刷新界面.
		super.onActivityResult(requestCode, resultCode, data);
	}
}
