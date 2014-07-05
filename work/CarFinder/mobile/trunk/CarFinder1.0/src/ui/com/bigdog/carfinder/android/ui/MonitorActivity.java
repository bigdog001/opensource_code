package com.bigdog.carfinder.android.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.bigdog.carfinder.android.base.CarBase;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 7/27/13
 * Time: 9:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class MonitorActivity extends CarBase {
    @Override
    public void setUp() {

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(MonitorActivity.this);
        textView.setText("monitor.....");
        setContentView(textView);
    }
}