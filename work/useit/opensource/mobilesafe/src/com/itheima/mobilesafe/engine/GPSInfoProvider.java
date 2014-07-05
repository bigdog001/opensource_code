package com.itheima.mobilesafe.engine;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class GPSInfoProvider {

	// ��֤λ���ṩ��ֻ��ע��һ�μ����� ֻ���ʼ��һ��.

	private GPSInfoProvider() {
	} // ˽�л����췽��

	private static GPSInfoProvider mGpsInfoProvider;
	private static LocationManager lm;
	private static SharedPreferences sp;
	private static MyListener listener;
	public static GPSInfoProvider getInstance(Context context) {
		if (mGpsInfoProvider == null) {
			mGpsInfoProvider = new GPSInfoProvider();
			lm = (LocationManager) context
					.getSystemService(Context.LOCATION_SERVICE);
			Criteria criteria = new Criteria();
			sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
			criteria.setAccuracy(Criteria.ACCURACY_FINE);
			criteria.setCostAllowed(true);
			criteria.setPowerRequirement(Criteria.POWER_HIGH);
			criteria.setSpeedRequired(true);
			String provider = lm.getBestProvider(criteria, true);
			// ��һ������ λ���ṩ�� �ڶ������� ��̸���ʱ�� �������� ��̵ĸ��µľ���
			listener = mGpsInfoProvider.new MyListener();
			lm.requestLocationUpdates(provider, 0, 0, listener);
		}

		return mGpsInfoProvider;
	}

	private class MyListener implements LocationListener {

		/**
		 * ��λ�øı��ʱ��
		 */
		public void onLocationChanged(Location location) {
			float accuracy = location.getAccuracy();
			double wlong = location.getLatitude(); // γ��
			double jlong = location.getLongitude(); // ����
			//���Ⱥ�γ����Ϣ������.
			Editor editor = sp.edit();
			editor.putString("lastlocation", wlong+"-"+jlong+"-"+accuracy);
			editor.commit();
		}

		/**
		 * ĳһ��λ���ṩ�ߵ�״̬�����ı��ʱ����õķ���
		 */
		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

		public void onProviderEnabled(String provider) {

		}

		public void onProviderDisabled(String provider) {

		}

	}

	/**
	 * �������һ���ֻ���λ��.
	 * @return
	 */
	public String getPhoneLocation() {
		return	sp.getString("lastlocation", "");
	}
}
