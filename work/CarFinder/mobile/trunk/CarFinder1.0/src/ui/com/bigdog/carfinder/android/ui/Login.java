package com.bigdog.carfinder.android.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.bigdog.carfinder.android.util.SystemUtil;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 7/25/13
 * Time: 9:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class Login extends Activity {

    /**
     * 只有在处于观测模式的时候才会用到登录界面
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("2222");
        setContentView(tv);
    }


}
