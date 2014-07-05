package com.bigdog.carfinder.android.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.*;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import com.bigdog.carfinder.android.R;
import com.bigdog.carfinder.android.application.CarFinderApplication;
import com.bigdog.carfinder.android.base.CarBase;
import com.bigdog.carfinder.android.bean.LocationBean;
import com.bigdog.carfinder.android.bean.TaskUnitBean;
import com.bigdog.carfinder.android.conf.Config;
import com.bigdog.carfinder.android.dao.impl.LocationDaoImpl;
import com.bigdog.carfinder.android.obverser.TrackerService;
import com.bigdog.carfinder.android.service.LocationService;
import com.bigdog.carfinder.android.service.TaskUnit;
import com.bigdog.carfinder.android.ui.widget.FinderBarTitle;
import com.bigdog.carfinder.android.util.SystemUtil;
import com.bigdog.carfinder.android.util.json.JsonObject;
import com.bigdog.carfinder.android.util.lbs.ILocationListener;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 7/27/13
 * Time: 9:40 AM
 */
public class DetectActivity extends CarBase implements ILocationListener, TrackerService.TrackerService_TrackerID ,TaskUnit {
    private TextView finder_trackid;
    private Dialog dialog;
    private FinderBarTitle finderBarTitle;
    private AnimationDrawable animationDrawable;
    private ImageView leida_anim;
    private TextView position_show_lat_lon;
    private TextView position_show_addr;
    private LocationService locationService;
    private LocationService.FinderBinder finderBinder;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            SystemUtil.log("定位服务链接成功....");
            finderBinder = (LocationService.FinderBinder) service;
            locationService = finderBinder.getService();
            locationService.addTaskUnit(DetectActivity.this);
            finderBinder.setLocationListener(DetectActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            locationService = null;
            SystemUtil.log("service connect failed.............." + name.describeContents());
        }
    };


    @Override
    public void setUp() {

    }

    public void onCreate(Bundle savedInstanceState) {
        setTrackerService_trackerID(DetectActivity.this);
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //连接到定位服务
        Intent intent = new Intent(mContext, LocationService.class);
        mContext.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

//        mContext = DetectActivity.this;
        setContentView(R.layout.detectactivity_content);
        LinearLayout detect_view_title = (LinearLayout) findViewById(R.id.detect_view_title);
        RelativeLayout.LayoutParams titleLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        titleLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        titleLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        finderBarTitle = new FinderBarTitle(mContext);
        detect_view_title.addView(finderBarTitle.getTitleBar(), titleLayoutParams);
        initView();
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void initView() {
        setUpTitleBar();
        position_show_addr = (TextView) findViewById(R.id.position_show_addr);
        position_show_lat_lon = (TextView) findViewById(R.id.position_show_lat_lon);
        finder_trackid = (TextView) findViewById(R.id.finder_trackid);

        //test===============================
        Button btn = (Button) findViewById(R.id.btn_test);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 new Thread(new Runnable() {
                     @Override
                     public void run() {
                         List <LocationBean> l = LocationDaoImpl.getFaceAddressDao(mContext).getLocationBeans(0,0);
                         if (l!=null) {
                             for(LocationBean lb:l){
                                 SystemUtil.log(lb.get_id() + "," + lb.getLatitude() + "," + lb.getLontitude()+","+lb.getUpload_status());
                             }
                         }

                     }
                 }).start();
            }
        });
        //===============================test
        String tid = SystemUtil.getTid();;
        if (!"00000000".equals(tid)) {
            intiTrackerID(tid);
        }
        finder_trackid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果没有设置密码，则提示密码设置
                final String tid = sharedPreferences.getString(Config.Config_tracker_id_name, "00000000");
                SystemUtil.log("tid:" + tid);
                if ("00000000".equals(tid)) {
                    Toast.makeText(mContext, R.string.no_tid_error, 0).show();
                } else {
                    final String passwd_tid = sharedPreferences.getString(Config.Config_tracker_id_passwd, "0");
                    if (!"0".equals(passwd_tid)) {
                        //已经有保护密码了,提示输入密码进行验证
//                        Toast.makeText(mContext,"-->"+passwd_tid,0).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(DetectActivity.this);
                        builder.setCancelable(false);
                        builder.setTitle(R.string.tracker_id_passwd_enter);
                        final View passwd_setting_view = View.inflate(mContext, R.layout.tracker_id_passwd_check, null);
                        builder.setView(passwd_setting_view);
                        Button button_ok = (Button) passwd_setting_view.findViewById(R.id.tracker_id_passwd_ok_c);
                        Button button_cancel = (Button) passwd_setting_view.findViewById(R.id.tracker_id_passwd_cancel_c);
                        button_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String tracker_id_passwd_check = ((EditText) passwd_setting_view.findViewById(R.id.tracker_id_passwd_check)).getText().toString();
                                if (passwd_tid.equals(SystemUtil.toMD5(tracker_id_passwd_check))) {
                                    dialog.dismiss();
                                    showTrackerID(tid);
                                } else {
                                    Toast.makeText(mContext, R.string.tracker_id_passwd_wrrong, 0).show();
                                }
                            }
                        });
                        button_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog = builder.create();
                        dialog.show();

                    } else {
                        //提示设置tid保护密码的对话框

                        AlertDialog.Builder builder = new AlertDialog.Builder(DetectActivity.this);
                        builder.setCancelable(false);
                        builder.setTitle(R.string.tracker_id_passwd_setting);
                        final View passwd_setting_view = View.inflate(mContext, R.layout.tracker_id_passwd_setting, null);
                        builder.setView(passwd_setting_view);
                        Button button_ok = (Button) passwd_setting_view.findViewById(R.id.tracker_id_passwd_ok);
                        Button button_cancel = (Button) passwd_setting_view.findViewById(R.id.tracker_id_passwd_cancel);
                        button_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String passwd_f = ((EditText) passwd_setting_view.findViewById(R.id.tracker_id_passwd_setting_f)).getText().toString();
                                String passwd_s = ((EditText) passwd_setting_view.findViewById(R.id.tracker_id_passwd_setting_s)).getText().toString();
                                if ("".equals(passwd_f) || "".equals(passwd_s) || passwd_f == null || passwd_s == null) {
                                    Toast.makeText(mContext, R.string.tracker_id_no_passwd_hint, 0).show();
                                } else {
                                    if (passwd_f.equals(passwd_s)) {
                                        passwd_f = passwd_f.trim();
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(Config.Config_tracker_id_passwd, SystemUtil.toMD5(passwd_f));
                                        editor.commit();
                                        CarFinderApplication.getAppContext().getmHandler().post(new Runnable() {
                                            @Override
                                            public void run() {
                                                dialog.dismiss();
                                            }
                                        });
                                        Toast.makeText(mContext, R.string.tracker_id_passwd_setting_ok, 0).show();
                                    } else {
                                        Toast.makeText(mContext, R.string.tracker_id_nosame_passwd_hint, 0).show();
                                    }
                                }
                            }
                        });
                        button_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CarFinderApplication.getAppContext().getmHandler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog.dismiss();
                                    }
                                });
                            }
                        });
                        dialog = builder.create();
                        dialog.show();
                    }
                }


            }
        });
        leida_anim = (ImageView) findViewById(R.id.leida_anim);
        leida_anim.setBackgroundResource(R.anim.frame);
        animationDrawable = (AnimationDrawable) leida_anim.getBackground();
        animationDrawable.setOneShot(false);

        //判断探测服务服务是否启动,如果未启动则启动，并启动雷达动画
        if (!SystemUtil.isServiceRunning(mContext, Config.detect_rcv)) {
            //探测服务未被开启,立即开启探测服务程序
            SystemUtil.log("探测服务未被开启,立即开启探测服务程序,in DetectActivity Activity........");
            Intent locationServiceIntent = new Intent(mContext, LocationService.class);
            mContext.startService(locationServiceIntent);
            startRadioAnim();
        } else {
            startRadioAnim();
        }
    }

    private void setUpTitleBar() {
        finderBarTitle.setLeftText("l");
        finderBarTitle.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "left", 0).show();
            }
        });
        finderBarTitle.setMidText(mContext.getString(R.string.title_show));
        finderBarTitle.setRightText("r");
        finderBarTitle.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "right", 0).show();
            }
        });
    }

    private void startRadioAnim() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                CarFinderApplication.getAppContext().getmHandler().post(new Runnable() {
                    @Override
                    public void run() {

                        if (!animationDrawable.isRunning()) {
                            animationDrawable.start();
                        }
                    }
                });


            }
        }).start();
    }

    @Override
    public void onLocateSuccess(long lat, long lon, JsonObject locateObj) {
        if (locateObj != null) {
            if (locateObj.getJsonArray("network_location") != null) {
                if (locateObj.getJsonArray("network_location").size() > 0) {
                    JsonObject network_location = (JsonObject) locateObj.getJsonArray("network_location").get(0);
                    final String latitude = network_location.getString("network_lat");
                    final String lontitude = network_location.getString("network_lon");
                    CarFinderApplication.getAppContext().getmHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            position_show_lat_lon.setText(" (" + latitude + "," + lontitude + ")");
                        }
                    });
                }
            } else if (locateObj.getJsonArray("gps_location") != null) {
                if (locateObj.getJsonArray("gps_location").size() > 0) {
                    JsonObject network_location = (JsonObject) locateObj.getJsonArray("gps_location").get(0);
                    final String latitude = network_location.getString("gps_lat");
                    final String lontitude = network_location.getString("gps_lon");
                    CarFinderApplication.getAppContext().getmHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            position_show_lat_lon.setText(" (" + latitude + "," + lontitude + ")");
                        }
                    });
                }
            } else {
                CarFinderApplication.getAppContext().getmHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        position_show_lat_lon.setText("location failed....");
                    }
                });
            }

        } else {
            CarFinderApplication.getAppContext().getmHandler().post(new Runnable() {
                @Override
                public void run() {
                    position_show_lat_lon.setText("location failed....");
                }
            });
        }
    }

    @Override
    public void onLocateCancel() {

    }

    @Override
    public void onLocateFail() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SystemUtil.log("DetectActivity被销毁....");
        if (finderBinder != null) {
            finderBinder.setLocationListener(null);
            mContext.unbindService(mConnection);
        }
    }

    @Override
    public void OnTrackerID_Success(final JsonObject jsonObject) {
        CarFinderApplication.getAppContext().getmHandler().post(new Runnable() {
            @Override
            public void run() {
                final String v = jsonObject.toJsonString();
                final String tracker_id = jsonObject.getString("tid");
                if (!"".equals(tracker_id) && tracker_id != null) {
                    SharedPreferences.Editor editor = CarFinderApplication.getAppContext().getSharedPreferences().edit();
                    editor.putString(Config.Config_tracker_id_name, tracker_id.toUpperCase());
                    editor.commit();

                    //update ui tracker_id
                    intiTrackerID(tracker_id);
                }
            }
        });
    }

    private void intiTrackerID(final String tid) {
        CarFinderApplication.getAppContext().getmHandler().post(new Runnable() {
            @Override
            public void run() {
                finder_trackid.setText(tid.substring(0, 2) + "******");
            }
        });
    }

    private void showTrackerID(final String tid) {
        //10秒种后隐藏tid
        CarFinderApplication.getAppContext().getmHandler().post(new Runnable() {
            @Override
            public void run() {
                finder_trackid.setText(tid);
            }
        });
        CarFinderApplication.getAppContext().getmHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finder_trackid.setText(tid.substring(0, 2) + "******");
            }
        }, 5000);
    }

    @Override
    public void doit(TaskUnitBean taskUnitBean) {
        SystemUtil.log(" 心跳机制运行中,in DetectActivity........");
    }
}