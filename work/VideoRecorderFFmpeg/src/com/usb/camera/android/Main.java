package com.usb.camera.android;


import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class Main extends Activity {
	
	private CameraPreview cp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
		/*setContentView(R.layout.main);

		cp = (CameraPreview) findViewById(R.id.cp);*/
	}
}
