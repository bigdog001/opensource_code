package com.facemail.mobile.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.facemail.mobile.android.R;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 4/21/13
 * Time: 2:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class MailSendBox extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.mail_send_box, null);
        return view;
    }
    public void initTitleBar() {
        faceTitleBar.hideTitle(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setmCanReturn(false);
        initTitleBar();
    }

    @Override
    public void doReturn() {

    }

}
