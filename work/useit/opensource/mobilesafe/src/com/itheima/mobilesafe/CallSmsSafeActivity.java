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
	private int startIndex = 0;// ��0����ʼ��ȡ����
	private int maxNumber = 20;// һ������ȡ20������
	private CallSmsAdapter adapter;// listview ������������

	private EditText et_callsms_pagenumber;
	private TextView tv_callsms_page_status;

	private int totalCount;
	// private TextView tv_status;
	private int totalPageNumber; // �ܵ�ҳ������
	private int pageNumber;// ��ǰҳ��

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
		tv_callsms_page_status.setText("��ǰ/��ҳ��:" + 1 + "/" + totalPageNumber);
		fillData();
		
		
		//��listview����Ŀע��һ�������ĵ���¼�.
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
	 * ���һ��activity����������ģʽ Ϊ singletop 
	 * ��ȥ����ջ����ͬһ��activity��ʱ�� �Ͳ��ᴴ���µ���. ���ǻḴ�þɵ�activity
	 * ���ҵ���onNewIntent()�ķ���.
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
	 * �������
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
				if (adapter == null) {// ��һ�μ������� ������������������
					adapter = new CallSmsAdapter();
					lv_callsms_safe.setAdapter(adapter);
				} else {// ���µ����ݱ���ӽ���.
					adapter.notifyDataSetChanged();// ֪ͨ���������� ���ݱ仯��.
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
				holder.iv_delete_blacknumber = (ImageView) view
						.findViewById(R.id.iv_delete_blacknumber);
				view.setTag(holder); // �ѵ�ǰ�������溢��id������ �������� ���ڵ�ǰ��view��������.

			}
			// Ŀ��:���ٺ��ӿؼ����ò��ҵĴ���. ����: ������ʷ����view�����ʱ�� ��ʵû�б�Ҫ�����²��Һ���.
			final BlackNumberInfo info = blackNumberInfos.get(position);
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
			holder.iv_delete_blacknumber
					.setOnClickListener(new OnClickListener() {

						public void onClick(View v) {
							// 1.�Ƴ����ݿ����Ŀ.
							dao.delete(info.getNumber());
							// 2.�Ƴ���ǰ�����������Ŀ.
							blackNumberInfos.remove(info);
							adapter.notifyDataSetChanged();// ֪ͨ�������
							totalCount--;// ���ݿ��������Ŀ����������.
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
			Toast.makeText(getApplicationContext(), "���벻��Ϊ��", 1).show();
			return;
		}
		pageNumber = Integer.parseInt(numberstr);
		if (pageNumber == 0) {
			Toast.makeText(getApplicationContext(), "ҳ�벻�Ϸ�", 1).show();
			return;
		}
		initTotalPageNumber();

		if (pageNumber > totalPageNumber) {
			Toast.makeText(getApplicationContext(), "û����ô��ҳ��", 1).show();
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
		tv_callsms_page_status.setText("��ǰ/��ҳ��:" + pageNumber + "/"
				+ totalPageNumber);
	}

	/**
	 * ��Ӻ���������
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
				Toast.makeText(this, "���벻��Ϊ��", 1).show();
				return;
			}
			if(dao.find(number)){
				Toast.makeText(this, "�����Ѿ������ں������б�", 1).show();
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
			dao.add(number, mode);//����������ͽ������ݿ���.
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
