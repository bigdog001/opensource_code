package com.itheima.mobilesafe.ui;

import com.itheima.mobilesafe.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 自定义的组合控件.
 * 
 * @author Administrator
 * 
 */
public class SettingView extends RelativeLayout {
	private TextView tv_settingview_title;
	private TextView tv_settingview_status;
	private CheckBox cb_settingview_status;
	private String check_text; // 选中文本
	private String uncheck_text; // 未选中文本

	public SettingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * 布局文件创建view对象 会使用 有两个参数的构造方法.
	 * 
	 * @param context
	 * @param attrs
	 */
	public SettingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
		// 把自定义的属性 和 属性集attrs 建立一个对应关系.
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.SettingView);
		String title = a.getString(R.styleable.SettingView_title);
		check_text = a.getString(R.styleable.SettingView_checked_text);
		uncheck_text = a.getString(R.styleable.SettingView_unchecked_text);
		setTitle(title);
		if (isChecked()) {
			setContext(check_text);
		} else {
			setContext(uncheck_text);
		}
		a.recycle();
	}
	/**
	 * 初始化view对象
	 * 
	 * @param context
	 */
	private void initView(Context context) {
		View view = View.inflate(context, R.layout.ui_setting_view, this);
		cb_settingview_status = (CheckBox) view
				.findViewById(R.id.cb_settingview_status);
		tv_settingview_status = (TextView) view
				.findViewById(R.id.tv_settingview_status);
		tv_settingview_title = (TextView) view
				.findViewById(R.id.tv_settingview_title);
	}

	public SettingView(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * 设置自定义控件的标题
	 * 
	 * @param text
	 */
	public void setTitle(String text) {
		tv_settingview_title.setText(text);
	}

	/**
	 * 设置控件的内容
	 */
	public void setContext(String text) {
		tv_settingview_status.setText(text);
	}

	/**
	 * 设置checkbox的状态
	 */
	public void setChecked(boolean checked) {
		cb_settingview_status.setChecked(checked);
		if (isChecked()) {
			setContext(check_text);
		} else {
			setContext(uncheck_text);
		}
	}

	/**
	 * 获取当前checkbox的状态
	 */
	public boolean isChecked() {
		return cb_settingview_status.isChecked();
	}
}
