package com.itheima.mobilesafe.adapter;

import com.itheima.mobilesafe.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeAdapter extends BaseAdapter{

	public static String[] names = {"手机防盗","通讯卫士","软件管理","进程管理","流量统计",
		"手机杀毒","缓存清理","高级工具","设置中心"};
	public static int[] icons ={R.drawable.safe,R.drawable.callmsgsafe,R.drawable.app,
		R.drawable.taskmanager,R.drawable.netmanager,R.drawable.trojan,R.drawable.sysoptimize,
		R.drawable.atools,R.drawable.settings};
	private Context context;
	private SharedPreferences sp;
	
	public HomeAdapter(Context context) {
		this.context = context;
		sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
	}

	/**
	 * 返回列表里面有多少个条目
	 */
	public int getCount() {
		return names.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	/**
	 * 返回每一个条目显示的view对象.
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = View.inflate(context, R.layout.grid_home_item, null);
		ImageView iv = (ImageView) view.findViewById(R.id.iv_home_item_icon);
		TextView tv = (TextView) view.findViewById(R.id.tv_home_item_name);
		tv.setText(names[position]);
		iv.setImageResource(icons[position]);
		
		if(position==0){
			String newname = sp.getString("newname", "");
			if(!TextUtils.isEmpty(newname)){
				tv.setText(newname);
			}
		}
		return view;
	}

}
