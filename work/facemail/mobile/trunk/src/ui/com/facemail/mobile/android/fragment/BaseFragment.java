package com.facemail.mobile.android.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.facemail.mobile.android.application.FaceMailApplication;
import com.facemail.mobile.android.view.FaceTitleBar;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 4/22/13
 * Time: 8:01 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseFragment extends Fragment {
    protected Context mContext;
    protected CenterViewListener mCenterViewListener;
    protected FaceTitleBar faceTitleBar;
    protected  ProgressDialog pd;
    private boolean mCanReturn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext= FaceMailApplication.getFaceMailContext().getApplicationContext();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initTitleBar();
    }
    public  void setCenterViewListener(CenterViewListener centerViewListener){
        this.mCenterViewListener= centerViewListener;
    }

    public void setFaceTitleBar(FaceTitleBar faceTitleBar) {
        this.faceTitleBar = faceTitleBar;
    }

    public boolean ismCanReturn() {
        return mCanReturn;
    }

    public void setmCanReturn(boolean mCanReturn) {
        this.mCanReturn = mCanReturn;
    }

    @Override
    public void onDestroy() {


        if (mCenterViewListener!=null) {
            mCenterViewListener=null;
        }
        super.onDestroy();
    }

    public abstract void doReturn();
    public abstract void initTitleBar();
}
