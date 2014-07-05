package com.itheima.mobilesafe;

import java.util.ArrayList;
import java.util.List;

import com.itheima.mobilesafe.db.dao.VirusDao;
import com.itheima.mobilesafe.utils.MD5Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AntiVirusActivity extends Activity {
	private ImageView iv_scan;
	private PackageManager pm;
	private TextView scan_status;
	private ProgressBar pb;
	private LinearLayout ll_container;
	private List<PackageInfo> virusInfos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_anti_virus);
		iv_scan = (ImageView) findViewById(R.id.iv_scan);
		RotateAnimation ra = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF,
				1.0f);
		ra.setDuration(800);
		ra.setRepeatCount(Animation.INFINITE);
		ra.setRepeatMode(Animation.RESTART);
		iv_scan.startAnimation(ra);
		pb = (ProgressBar) findViewById(R.id.progressBar1);
		scan_status = (TextView) findViewById(R.id.scan_status);
		ll_container = (LinearLayout) findViewById(R.id.ll_container);
		pm = getPackageManager();
		virusInfos = new ArrayList<PackageInfo>();
		new AsyncTask<Void, Object, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				// ���Ӧ�ó����ǩ�� �Ƿ��ڲ������ݿ�����.

				try {
					Thread.sleep(500);
					List<PackageInfo> infos = pm
							.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES
									| PackageManager.GET_SIGNATURES);
					pb.setMax(infos.size());
					int total = 0;
					for (PackageInfo info : infos) {
						Signature[] signatures = info.signatures;
						String sign = signatures[0].toCharsString();// �õ�Ӧ�ó����ǩ����Ϣ
																	// ����ǩ����Ϣ
																	// ������ʶ�𿪷��ߵ�Ψһ��ʾ.
						String md5 = MD5Utils.encode(sign);
						String result = VirusDao.findVirsu(md5);
						if (result != null) {
							publishProgress(info, true);// ��������Ϣ ��һ������
														// ��Ӧ�ó���İ���Ϣ,�ڶ������������Ƿ��ǲ���
							virusInfos.add(info);
						} else {
							publishProgress(info, false);
						}
						total++;
						pb.setProgress(total);
						Thread.sleep(40);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPreExecute() {
				scan_status.setText("���ڳ�ʼ��˫��ɱ������...");
				super.onPreExecute();
			}

			@Override
			protected void onPostExecute(Void result) {
				scan_status.setText("ɨ�����!");
				iv_scan.clearAnimation();

				if (virusInfos.size() > 0) {
					AlertDialog.Builder builder = new Builder(
							AntiVirusActivity.this);
					builder.setTitle("���ֲ���");
					builder.setMessage("�Ƿ���������?");
					builder.setPositiveButton("ȷ��", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							for (PackageInfo info : virusInfos) {
								Intent intent = new Intent();
								intent.setAction(Intent.ACTION_DELETE);
								intent.setData(Uri.parse("package:"
										+ info.packageName));
								startActivity(intent);
							}
						}
					});
					builder.setNegativeButton("ȡ��", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

						}
					});
					builder.show();
				}

				super.onPostExecute(result);
			}

			@Override
			protected void onProgressUpdate(Object... values) {
				PackageInfo packinfo = (PackageInfo) values[0];
				Boolean result = (Boolean) values[1];
				scan_status.setText("����ɨ��:"
						+ packinfo.applicationInfo.loadLabel(pm));
				TextView tv = new TextView(getApplicationContext());
				tv.setTextSize(16);
				if (result) {// ���ֲ���
					tv.setTextColor(Color.RED);
					tv.setText("���ֲ�������:"
							+ packinfo.applicationInfo.loadLabel(pm));
				} else {
					tv.setTextColor(Color.BLACK);
					tv.setText("ɨ�谲ȫ:" + packinfo.applicationInfo.loadLabel(pm));
				}
				ll_container.addView(tv, 0);
				super.onProgressUpdate(values);
			}

		}.execute();
	}
}
