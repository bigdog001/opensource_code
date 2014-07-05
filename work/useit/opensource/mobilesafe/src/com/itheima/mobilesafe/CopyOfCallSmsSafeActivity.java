package com.itheima.mobilesafe;

import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import com.itheima.mobilesafe.utils.Logger;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.mobilesafe.db.dao.BlackNumberDao;
import com.itheima.mobilesafe.domain.BlackNumberInfo;
import com.itheima.mobilesafe.utils.MyAsyncTask;

public class CopyOfCallSmsSafeActivity extends Activity {
	public static final String TAG = "CallSmsSafeActivity";
	private ListView lv_callsms_safe;
	private BlackNumberDao dao;
	private List<BlackNumberInfo> blackNumberInfos;
	private View loading;
	private int startIndex = 0;// 从0条开始获取数据
	private int maxNumber = 20;// 一次最多获取20条数据
	private CallSmsAdapter adapter;// listview 的数据适配器
	
	private int totalCount;
	//private TextView tv_status;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call_sms_safe);
		loading = findViewById(R.id.loading);
		lv_callsms_safe = (ListView) findViewById(R.id.lv_callsms_safe);
		//tv_status = (TextView) findViewById(R.id.tv_status);
		dao = new BlackNumberDao(this);
		totalCount = dao.getTotalCount();
		lv_callsms_safe.setOnScrollListener(new OnScrollListener() {

			/**
			 * 滚动状态发生改变的时候调用的方法. 静止 - 滚动 滑翔状态 - 静止
			 * 
			 * @param view
			 * @param scrollState
			 */
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_IDLE: // 静止状态
					// 判断最后一个条目 如果是集合里面最后的一个条目 说明到了界面的最下方.
					int lastPosition = lv_callsms_safe.getLastVisiblePosition();// 获取最后一个可见条目的位置.
																				// 从0开的最后一个条目位置
																				// 19
					int total = blackNumberInfos.size();// 集合里面一共有多少个内容. 20
					if (lastPosition == (total - 1)) {// 说明最后一个可见的条目
														// 就是集合的最后一个元素.
						// 加载更多的数据...
						Logger.i(TAG, "加载下20条的数据.");
						startIndex += maxNumber;
						if(startIndex>totalCount){
							Toast.makeText(getApplicationContext(), "没有更多的数据了...", 1).show();
							return;
						}
						
						fillData();
					}

					break;

				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL: // 手指触摸的滚动.

					break;
				case OnScrollListener.SCROLL_STATE_FLING:// 滑翔状态.

					break;
				}

			}

			/**
			 * 滚动的时候 调用的方法.
			 * 
			 * @param view
			 * @param firstVisibleItem
			 * @param visibleItemCount
			 * @param totalItemCount
			 */
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				//tv_status.setText("位置:"+firstVisibleItem/10);
			}
		});

		fillData();

	}

	/**
	 * 填充数据
	 */
	private void fillData() {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				// 如果是第一次请求数据.
				if (blackNumberInfos == null) {
					blackNumberInfos = dao.findByPage(startIndex, maxNumber);
				} else {// 已经请求过数据了.
					blackNumberInfos.addAll(dao.findByPage(startIndex,
							maxNumber));
				}
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
				}else{//有新的数据被添加进来.
					adapter.notifyDataSetChanged();//通知数据适配器 数据变化了.
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
				view.setTag(holder); // 把当前布局里面孩子id的引用 给存起来 存在当前的view对象里面.

			}
			// 目的:减少孩子控件引用查找的次数. 分析: 复用历史缓存view对象的时候 其实没有必要再重新查找孩子.
			BlackNumberInfo info = blackNumberInfos.get(position);
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
			return view;
		}

	}

	static class ViewHolder {
		TextView tv_mode;
		TextView tv_number;
	}
}
