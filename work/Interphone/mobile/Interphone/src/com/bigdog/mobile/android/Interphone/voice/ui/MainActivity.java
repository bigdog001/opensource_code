package com.bigdog.mobile.android.Interphone.voice.ui;

import android.util.Log;
import android.widget.Toast;
import com.bigdog.mobile.android.Interphone.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.bigdog.mobile.android.Interphone.utils.IpMessageConst;
import com.bigdog.mobile.android.Interphone.voice.AudioWrapper;
import org.apache.http.conn.util.InetAddressUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class MainActivity extends Activity {
	String LOG = "MainActivity";
	private AudioWrapper audioWrapper;

	// View
	private Button btnStartRecord;
	private Button btnStopRecord;
	private Button btnStartListen;
	private Button btnStopListen;
	private Button btnExit;
	private EditText ipEditText;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_voice);
		audioWrapper = AudioWrapper.getInstance();

		initView();
	}

	private void initView() {
		btnStartRecord = (Button) findViewById(R.id.startRecord);
		btnStartListen = (Button) findViewById(R.id.startListen);
		btnStopRecord = (Button) findViewById(R.id.stopRecord);
		btnStopListen = (Button) findViewById(R.id.stopListen);
		ipEditText = (EditText) findViewById(R.id.edittext_ip);

		//
		btnStopRecord.setEnabled(false);
		btnStopListen.setEnabled(false);

		btnExit = (Button) findViewById(R.id.btnExit);
		btnStartRecord.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				String ipString = ipEditText.getText().toString().trim();
                IpMessageConst.VOICERCV_HOST = ipString;
				btnStartRecord.setEnabled(false);
				btnStopRecord.setEnabled(true);
				audioWrapper.startRecord();
			}
		});

		btnStopRecord.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				btnStartRecord.setEnabled(true);
				btnStopRecord.setEnabled(false);
				audioWrapper.stopRecord();
			}
		});
		btnStartListen.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				btnStartListen.setEnabled(false);
				btnStopListen.setEnabled(true);
				audioWrapper.startListen();
			}
		});
		btnStopListen.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				btnStartListen.setEnabled(true);
				btnStopListen.setEnabled(false);
				audioWrapper.stopListen();
			}
		});
		btnExit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				audioWrapper.stopListen();
				audioWrapper.stopRecord();
				System.exit(0);
			}
		});
        Toast.makeText(getApplicationContext(),getLocalIpAddress(),0).show();
	}

    //得到本机IP地址
    public String getLocalIpAddress(){
        try{
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while(en.hasMoreElements()){
                NetworkInterface nif = en.nextElement();
                Enumeration<InetAddress> enumIpAddr = nif.getInetAddresses();
                while(enumIpAddr.hasMoreElements()){
                    InetAddress mInetAddress = enumIpAddr.nextElement();
                    if(!mInetAddress.isLoopbackAddress() && InetAddressUtils.isIPv4Address(mInetAddress.getHostAddress())){
                        return mInetAddress.getHostAddress().toString();
                    }
                }
            }
        }catch(SocketException ex){
            Log.e("MyFeiGeActivity", "获取本地IP地址失败");
        }

        return null;
    }
}