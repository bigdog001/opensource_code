package com.itheima.mobilesafe;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import com.itheima.mobilesafe.utils.Logger;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.mobilesafe.db.dao.BlackNumberDao;
import com.itheima.mobilesafe.domain.BlackNumberInfo;

public class CallSmsSafeActivity extends Activity implements OnClickListener {
	public static final String TAG = "CallSmsSafeActivity";
	private ListView lv_callsms_safe;
	private BlackNumberDao dao;
	private List<BlackNumberInfo> blackNumberInfos;
	private View loading;
	private int startIndex = 0;// 从0条开始获取数据
	private int maxNumber = 20;// 一次最多获取20条数据
	private CallSmsAdapter adapter;// listview 的数据适配器

	private EditText et_callsms_pagenumber;
	private TextView tv_callsms_page_status;

	private int totalCount;
	// private TextView tv_status;
	private int totalPageNumber; // 总的页码数量
	private int pageNumber;// 当前页码

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call_sms_safe);
		et_callsms_pagenumber = (EditText) findViewById(R.id.et_callsms_pagenumber);
		loading = findViewById(R.id.loading);
		lv_callsms_safe = (ListView) findViewById(R.id.lv_callsms_safe);
		// tv_status = (TextView) findViewById(R.id.tv_status);
		tv_callsms_page_status = (TextView) findViewById(R.id.tv_callsms_page_status);
		dao = new BlackNumberDao(this);
		totalCount = dao.getTotalCount();
		initTotalPageNumber();
		tv_callsms_page_status.setText("当前/总页码:" + 1 + "/" + totalPageNumber);
		fillData();
		
		
		//给listview的条目注册一个长按的点击事件.
		lv_callsms_safe.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(CallSmsSafeActivity.this,EditBlackNumberDialog.class);
				intent.putExtra("number", blackNumberInfos.get(position).getNumber());
				intent.putExtra("mode", blackNumberInfos.get(position).getMode());
				
				intent.putExtra("obj", blackNumberInfos.get(position));
				MobliesafeApplication myapp = (MobliesafeApplication) getApplication();
				myapp.info =  blackNumberInfos.get(position);
				System.out.println("callsmsactivity:"+blackNumberInfos.get(position).hashCode());
				startActivityForResult(intent, 0);
				return false;
			}
		});
		
		
		
		Intent intent = getIntent();
		String number = intent.getStringExtra("number");
		if(!TextUtils.isEmpty(number)){
			showAddBlackNumberDialog(number);
		}

	}
	/**
	 * 如果一个activity配置了启动模式 为 singletop 
	 * 再去开启栈顶的同一个activity的时候 就不会创建新的了. 而是会复用旧的activity
	 * 并且调用onNewIntent()的方法.
	 */
	@Override
	protected void onNewIntent(Intent intent) {
		String number = intent.getStringExtra("number");
		if(!TextUtils.isEmpty(number)){
			showAddBlackNumberDialog(number);
		}
		super.onNewIntent(intent);
	}
	
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==200){
			fillData();
		}else if(resultCode==300){
		    adapter.notifyDataSetChanged();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	/**
	 * 填充数据
	 */
	private void fillData() {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {

				blackNumberInfos = dao.findByPage(startIndex, maxNumber);

				return null;
			}

			@Override
			protected void onPreExecute() {
				loading.setVisibility(View.VISIBLE);
				super.onPreExecute();
			}

			@Override
			protected void onPostExecute(Void result) {
				loading.setVisibility(View.INVISIBLE);
				if (adapter == null) {// 第一次加载数据 数据适配器还不存在
					adapter = new CallSmsAdapter();
					lv_callsms_safe.setAdapter(adapter);
				} else {// 有新的数据被添加进来.
					adapter.notifyDataSetChanged();// 通知数据适配器 数据变化了.
				}
				super.onPostExecute(result);
			}

		}.execute();
	}

	private class CallSmsAdapter extends BaseAdapter {

		public int getCount() {
			return blackNumberInfos.size();
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		// convertView 历史缓存的 历史回收的view对象
		// convertView 只有当界面上有一个条目被移除的时候 convertView才会有数据
		public View getView(int position, View convertView, ViewGroup parent) {
			// 这个方法会被调用多少次呢?
			View view;
			ViewHolder holder;
			if (convertView != null && convertView instanceof RelativeLayout) {
				view = convertView;
				holder = (ViewHolder) view.getTag(); // 把刚才存在view对象里面孩子的引用再重新的获取出来.
				// Logger.i(TAG, "使用缓存的view对象" + position);
			} else {
				view = View.inflate(getApplicationContext(),
						R.layout.list_callsms_item, null);
				// Logger.i(TAG, "创建view对象" + position);
				holder = new ViewHolder();// 创建出来一个自定义的对象, 存放孩子控件的引用.
				holder.tv_mode = (TextView) view
						.findViewById(R.id.tv_black_mode);
				holder.tv_number = (TextView) view
						.findViewById(R.id.tv_black_number);
				holder.iv_delete_blacknumber = (ImageView) view
						.findViewById(R.id.iv_delete_blacknumber);
				view.setTag(holder); // 把当前布局里面孩子id的引用 给存起来 存在当前的view对象里面.

			}
			// 目的:减少孩子控件引用查找的次数. 分析: 复用历史缓存view对象的时候 其实没有必要再重新查找孩子.
			final BlackNumberInfo info = blackNumberInfos.get(position);
			String mode = info.getMode();
			String number = info.getNumber();
			holder.tv_number.setText(number);
			if ("1".equals(mode)) {
				holder.tv_mode.setText("全部拦截");
			} else if ("2".equals(mode)) {
				holder.tv_mode.setText("电话拦截");
			} else if ("3".equals(mode)) {
				holder.tv_mode.setText("短信拦截");
			}
			holder.iv_delete_blacknumber
					.setOnClickListener(new OnClickListener() {

						public void onClick(View v) {
							// 1.移除数据库的条目.
							dao.delete(info.getNumber());
							// 2.移除当前界面里面的条目.
							blackNumberInfos.remove(info);
							adapter.notifyDataSetChanged();// 通知界面更新
							totalCount--;// 数据库里面的条目数量减少了.
							initTotalPageNumber();
						}
					});
			return view;
		}

	}

	static class ViewHolder {
		TextView tv_mode;
		TextView tv_number;
		ImageView iv_delete_blacknumber;
	}

	public void jump(View view) {
		String numberstr = et_callsms_pagenumber.getText().toString().trim();
		if (TextUtils.isEmpty(numberstr)) {
			Toast.makeText(getApplicationContext(), "号码不能为空", 1).show();
			return;
		}
		pageNumber = Integer.parseInt(numberstr);
		if (pageNumber == 0) {
			Toast.makeText(getApplicationContext(), "页码不合法", 1).show();
			return;
		}
		initTotalPageNumber();

		if (pageNumber > totalPageNumber) {
			Toast.makeText(getApplicationContext(), "没有这么多页码", 1).show();
			return;
		}

		// 1 0~19 (1-1)*maxNumber ~ (2-1)*maxNumber-1
		// 2 20~39
		startIndex = (pageNumber - 1) * maxNumber;
		fillData();

	}

	private void initTotalPageNumber() {
		int leaftCount = totalCount % maxNumber;
		if (leaftCount == 0) {
			totalPageNumber = totalCount / maxNumber;
		} else {
			totalPageNumber = totalCount / maxNumber + 1;
		}
		tv_callsms_page_status.setText("当前/总页码:" + pageNumber + "/"
				+ totalPageNumber);
	}

	/**
	 * 添加黑名单号码
	 */
	private EditText et_add_blacknumber;
	private Button bt_add_ok;
	private Button bt_add_cancle;
	private AlertDialog dialog;
	private RadioGroup rg;

	public void addBlackNumber(View view) {
		showAddBlackNumberDialog("");
	}

	private void showAddBlackNumberDialog(String number) {
		AlertDialog.Builder builder = new Builder(this);
		dialog = builder.create();
		View dialogView = View.inflate(getApplicationContext(),
				R.layout.dialog_add_blacknumber, null);
		et_add_blacknumber = (EditText) dialogView
				.findViewById(R.id.et_add_blacknumber);
		et_add_blacknumber.setText(number);
		bt_add_ok = (Button) dialogView
				.findViewById(R.id.bt_add_blacknumber_ok);
		bt_add_cancle = (Button) dialogView
				.findViewById(R.id.bt_add_blacknumber_cancle);
		rg = (RadioGroup) dialogView.findViewById(R.id.radioGroup1);
		bt_add_cancle.setOnClickListener(this);
		bt_add_ok.setOnClickListener(this);
		dialog.setView(dialogView, 0, 0, 0, 0);
		dialog.show();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_add_blacknumber_cancle:
			dialog.dismiss();
			break;
		case R.id.bt_add_blacknumber_ok:
			String number = et_add_blacknumber.getText().toString().trim();
			if (TextUtils.isEmpty(number)) {
				Toast.makeText(this, "号码不能为空", 1).show();
				return;
			}
			if(dao.find(number)){
				Toast.makeText(this, "数据已经存在在黑名单列表", 1).show();
				return;
			}
			
			int id = rg.getCheckedRadioButtonId();
			String mode ="1";
			switch (id) {
			case R.id.rb_all:
				mode = "1";
				break;

			case R.id.rb_phone:
				mode = "2";
				break;
			case R.id.rb_sms:
				mode ="3";
				break;
			}
			dao.add(number, mode);//黑名单号码就进到数据库了.
			BlackNumberInfo newInfo = new BlackNumberInfo();
			newInfo.setMode(mode);
			newInfo.setNumber(number);
			blackNumberInfos.add(0, newInfo);
			adapter.notifyDataSetChanged();
			dialog.dismiss();
			break;
		}

	}

}
