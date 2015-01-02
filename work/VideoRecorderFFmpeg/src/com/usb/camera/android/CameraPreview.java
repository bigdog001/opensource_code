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
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback, Runnable {
	
    private CameraUtil cameraUtil;
	
	private static final boolean DEBUG=true;
	private static final String TAG="DoubleWebCam";
	protected Context context;
	private SurfaceHolder holder;
    Thread mainLoop = null;
    	
	private boolean shouldStop=false;
	
	// Size of each image. This definition also exists in jni/ImageProc.h
	public static final int IMG_WIDTH=640;
	public static final int IMG_HEIGHT=480;
    
	public int winWidth=0;
	public int winHeight=0;
	public Rect rect1, rect2;
	
    static {
    	System.loadLibrary("jpeg");
        System.loadLibrary("mycamera");
    }
	public CameraPreview(Context context) {
		super(context);
		this.context = context;
		if(DEBUG) Log.d(TAG,"CameraPreview constructed");
		setFocusable(true);


		holder = getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);	
		
		if(cameraUtil == null){
			cameraUtil = new CameraUtil();
		}
	}

	public CameraPreview(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		if(DEBUG) Log.d(TAG,"CameraPreview constructed");
		setFocusable(true);

		holder = getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);	
		
		if(cameraUtil == null){
			cameraUtil = new CameraUtil();
		}
	}
	
    @Override
    public void run() {
        while (cameraUtil.cameraExists[0]||cameraUtil.cameraExists[1]) {
    	//for(int i=0; i<10; i++){
        	cameraUtil.processCamera();
        	cameraUtil.pixeltobmp(cameraUtil.bmp[0],cameraUtil.bmp[1]);
            //Log.d("bigdog","cameraExists[0]:"+cameraExists[0]+",cameraExists[1]:"+cameraExists[1]);
        	Canvas canvas = getHolder().lockCanvas();
            if (canvas != null){
            	if(winWidth==0){
            		winWidth=this.getWidth();
            		winHeight=this.getHeight();
            		rect1 = new Rect(0, 0, winWidth/2-1, winWidth*3/4/2-1);
            		rect2 = new Rect(winWidth/2,0,winWidth-1, winWidth*3/4/2-1);
            	}

        		canvas.drawBitmap(cameraUtil.bmp[0],null,rect1,null);
        		canvas.drawBitmap(cameraUtil.bmp[1],null,rect2,null);
        		Log.d("bigdog","Width:"+cameraUtil.bmp[0].getWidth()+",Height:"+cameraUtil.bmp[0].getHeight());
            	getHolder().unlockCanvasAndPost(canvas);
            }
            if(shouldStop){
             	shouldStop = false;  
             	break;
            }
        }
    }

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if(DEBUG) Log.d(TAG, "surfaceCreated");
		for(int i=0 ; i<2 ; i++){
			if(cameraUtil.bmp[i]==null){
				cameraUtil.bmp[i] = Bitmap.createBitmap(IMG_WIDTH, IMG_HEIGHT, Bitmap.Config.ARGB_8888);
				
			}
		}		
		
        mainLoop = new Thread(this);
        mainLoop.start();		
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		if(DEBUG) Log.d(TAG, "surfaceChanged");
	}
	

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if(DEBUG) Log.d(TAG, "surfaceDestroyed");
		if(cameraUtil.cameraExists[0]||cameraUtil.cameraExists[1]){
			shouldStop = true;
			for(int i=0 ; i<10 ; i++){
				try{ 
					Thread.sleep(100); // wait for thread stopping
				}catch(Exception e){}
				if(!shouldStop){
					break;
				}
			}
			cameraUtil.stopCamera();
		}
	}
	
	
	    
}
