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
	private int startIndex = 0;// ��0����ʼ��ȡ����
	private int maxNumber = 20;// һ������ȡ20������
	private CallSmsAdapter adapter;// listview ������������
	
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
			 * ����״̬�����ı��ʱ����õķ���. ��ֹ - ���� ����״̬ - ��ֹ
			 * 
			 * @param view
			 * @param scrollState
			 */
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_IDLE: // ��ֹ״̬
					// �ж����һ����Ŀ ����Ǽ�����������һ����Ŀ ˵�����˽�������·�.
					int lastPosition = lv_callsms_safe.getLastVisiblePosition();// ��ȡ���һ���ɼ���Ŀ��λ��.
																				// ��0�������һ����Ŀλ��
																				// 19
					int total = blackNumberInfos.size();// ��������һ���ж��ٸ�����. 20
					if (lastPosition == (total - 1)) {// ˵�����һ���ɼ�����Ŀ
														// ���Ǽ��ϵ����һ��Ԫ��.
						// ���ظ��������...
						Logger.i(TAG, "������20��������.");
						startIndex += maxNumber;
						if(startIndex>totalCount){
							Toast.makeText(getApplicationContext(), "û�и����������...", 1).show();
							return;
						}
						
						fillData();
					}

					break;

				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL: // ��ָ�����Ĺ���.

					break;
				case OnScrollListener.SCROLL_STATE_FLING:// ����״̬.

					break;
				}

			}

			/**
			 * ������ʱ�� ���õķ���.
			 * 
			 * @param view
			 * @param firstVisibleItem
			 * @param visibleItemCount
			 * @param totalItemCount
			 */
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				//tv_status.setText("λ��:"+firstVisibleItem/10);
			}
		});

		fillData();

	}

	/**
	 * �������
	 */
	private void fillData() {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				// ����ǵ�һ����������.
				if (blackNumberInfos == null) {
					blackNumberInfos = dao.findByPage(startIndex, maxNumber);
				} else {// �Ѿ������������.
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
				if (adapter == null) {// ��һ�μ������� ������������������
					adapter = new CallSmsAdapter();
					lv_callsms_safe.setAdapter(adapter);
				}else{//���µ����ݱ���ӽ���.
					adapter.notifyDataSetChanged();//֪ͨ���������� ���ݱ仯��.
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

		// convertView ��ʷ����� ��ʷ���յ�view����
		// convertView ֻ�е���������һ����Ŀ���Ƴ���ʱ�� convertView�Ż�������
		public View getView(int position, View convertView, ViewGroup parent) {
			// ��������ᱻ���ö��ٴ���?
			View view;
			ViewHolder holder;
			if (convertView != null && convertView instanceof RelativeLayout) {
				view = convertView;
				holder = (ViewHolder) view.getTag(); // �ѸղŴ���view�������溢�ӵ����������µĻ�ȡ����.
				// Logger.i(TAG, "ʹ�û����view����" + position);
			} else {
				view = View.inflate(getApplicationContext(),
						R.layout.list_callsms_item, null);
				// Logger.i(TAG, "����view����" + position);
				holder = new ViewHolder();// ��������һ���Զ���Ķ���, ��ź��ӿؼ�������.
				holder.tv_mode = (TextView) view
						.findViewById(R.id.tv_black_mode);
				holder.tv_number = (TextView) view
						.findViewById(R.id.tv_black_number);
				view.setTag(holder); // �ѵ�ǰ�������溢��id������ �������� ���ڵ�ǰ��view��������.

			}
			// Ŀ��:���ٺ��ӿؼ����ò��ҵĴ���. ����: ������ʷ����view�����ʱ�� ��ʵû�б�Ҫ�����²��Һ���.
			BlackNumberInfo info = blackNumberInfos.get(position);
			String mode = info.getMode();
			String number = info.getNumber();
			holder.tv_number.setText(number);
			if ("1".equals(mode)) {
				holder.tv_mode.setText("ȫ������");
			} else if ("2".equals(mode)) {
				holder.tv_mode.setText("�绰����");
			} else if ("3".equals(mode)) {
				holder.tv_mode.setText("��������");
			}
			return view;
		}

	}

	static class ViewHolder {
		TextView tv_mode;
		TextView tv_number;
	}
}
