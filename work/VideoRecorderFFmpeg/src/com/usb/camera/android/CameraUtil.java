package com.usb.camera.android;

import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

@SuppressLint("Instantiatable")
public class CameraUtil {
	private static final boolean DEBUG=true;
	private static final String TAG="DoubleWebCam";
	
    
	public Bitmap[] bmp=new Bitmap[2];
	public boolean[] cameraExists=new boolean[2];
	
	
	// Size of each image. This definition also exists in jni/ImageProc.h
	public static final int IMG_WIDTH=640;
	public static final int IMG_HEIGHT=480;
    
	public int winWidth=0;
	public int winHeight=0;
	
    public native void pixeltobmp(Bitmap bitmap1, Bitmap bitmap2);
    // prepareCamera selects the device automatically. please set videoid=0
    public native int prepareCamera(int videoid);
    // prepareCameraWithBase is used if you want to specify the device manually.
    // e.g., for /dev/video[1,2], use prepareCameraWithBase(0,1).
    // please set videoid=0
    public native int prepareCameraWithBase(int videoid, int videobase);
    public native void processCamera();
    public native void processRBCamera(int lrmode);
    public native void stopCamera();

    static {
    	System.loadLibrary("jpeg");
        System.loadLibrary("mycamera");
    }
	public CameraUtil() {		
		
		for(int i=0 ; i<2 ; i++){
			if(bmp[i]==null){
				bmp[i] = Bitmap.createBitmap(IMG_WIDTH, IMG_HEIGHT, Bitmap.Config.ARGB_8888);
				
			}
		}
		
		bmp[0] = null;
		bmp[1] = null;

       int ret= prepareCamera(0);
		
		cameraExists[0] = ((ret>>0)&0x1) == 1;
		cameraExists[1] = ((ret>>1)&0x1) == 1;
		
	}

   
	    
}
