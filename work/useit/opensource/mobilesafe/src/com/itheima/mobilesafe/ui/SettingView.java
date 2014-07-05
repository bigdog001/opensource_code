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
 * �Զ������Ͽؼ�.
 * 
 * @author Administrator
 * 
 */
public class SettingView extends RelativeLayout {
	private TextView tv_settingview_title;
	private TextView tv_settingview_status;
	private CheckBox cb_settingview_status;
	private String check_text; // ѡ���ı�
	private String uncheck_text; // δѡ���ı�

	public SettingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * �����ļ�����view���� ��ʹ�� �����������Ĺ��췽��.
	 * 
	 * @param context
	 * @param attrs
	 */
	public SettingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
		// ���Զ�������� �� ���Լ�attrs ����һ����Ӧ��ϵ.
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
	 * ��ʼ��view����
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
	 * �����Զ���ؼ��ı���
	 * 
	 * @param text
	 */
	public void setTitle(String text) {
		tv_settingview_title.setText(text);
	}

	/**
	 * ���ÿؼ�������
	 */
	public void setContext(String text) {
		tv_settingview_status.setText(text);
	}

	/**
	 * ����checkbox��״̬
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
	 * ��ȡ��ǰcheckbox��״̬
	 */
	public boolean isChecked() {
		return cb_settingview_status.isChecked();
	}
}
