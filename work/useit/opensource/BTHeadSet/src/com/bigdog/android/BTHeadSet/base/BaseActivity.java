package com.bigdog.android.BTHeadSet.base;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;
import com.bigdog.android.BTHeadSet.R;
import com.bigdog.android.BTHeadSet.Util.ExitUtils;
import com.bigdog.android.BTHeadSet.Util.Util;

/**
 * Created with IntelliJ IDEA.
 * User: jw362j
 * Date: 2/12/14
 * Time: 4:18 PM
 */
public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ExitUtils.getExitUtilsInstance().addActivity(this);
        Util.log(  "onCreate....");
        checkBlueTooth();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Util.log(  "onResume....");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Util.log(  "onPause....");
    }

    private void checkBlueTooth(){

        BluetoothAdapter adpter= BluetoothAdapter.getDefaultAdapter();

        if (adpter == null) {
            Toast.makeText(this, R.string.nobluetooth, Toast.LENGTH_SHORT).show();
            ExitUtils.getExitUtilsInstance().exit();
        }
        if (!adpter.isEnabled()) {
            Util.log(adpter.getState() + "..");
            Intent mIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(mIntent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Util.log(requestCode+","+resultCode);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, R.string.bluetoothOn, Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, R.string.bluetoothOff, Toast.LENGTH_SHORT).show();
                ExitUtils.getExitUtilsInstance().exit();
            }
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Util.log("=======KEYCODE_BACK");
            return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Util.log("=======onDestroy");
    }
}
