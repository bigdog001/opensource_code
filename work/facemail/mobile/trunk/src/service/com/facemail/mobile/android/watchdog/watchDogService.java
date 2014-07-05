package com.facemail.mobile.android.watchdog;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import com.facemail.mobile.android.db.bean.UserAcount;
import com.facemail.mobile.android.db.conf.Config;
import com.facemail.mobile.android.db.dao.UserAccountDao;
import com.facemail.mobile.android.fragment.AccountsFragment;
import com.facemail.mobile.android.mail.FaceMailProvider;
import com.facemail.mobile.android.mail.IMailRequest;
import com.facemail.mobile.android.mail.IMailResponse;
import com.facemail.mobile.android.mail.MailTaskStatus;
import com.facemail.mobile.android.util.SystemUtil;
import com.facemail.mobile.android.util.json.JsonValue;

import java.util.List;

/**
 * User: bigdog
 * Date: 5/11/13
 * Time: 6:03 AM
 */
public class watchDogService extends Service {
    private final IBinder faceBinder = new FaceBinder();
    private BroadcastReceiver receiver;
    private BroadcastReceiver reloadAccountReceiver;
    AlarmManager alarmManager;
    PendingIntent pi;
    private static final long INTERVAL = 1000 +500;
    private AccountsFragment mAccountsFragment;
    private boolean scanflag = true; //仅仅是为了在加载刷新用户数据的时候能起到暂停扫描操作的作用
    private List<UserAcount> userAcounts;//将要被收取的所有的邮件账户，其会不断的被扫描

    public class FaceBinder extends Binder {
        public watchDogService getService() {
            return watchDogService.this;
        }

        public void setObserveFragment(AccountsFragment accountsFragment) {
            mAccountsFragment = accountsFragment;
        }

        public void updateTaskStatus(int serverConnStatus, int currentPosition, int totalAmount, int workModule, String email) {
            updateTaskStatusInService(serverConnStatus, currentPosition, totalAmount, workModule, email);
        }

        public MailTaskStatus getTaskStatus(String email) {
            return getTaskStatusInService(email);
        }

        public void registeUICallBack(String email, BackgroundRefresh backgroundRefresh) {
            if ("".equals(email) || email == null) {
                return;
            }
            if (backgroundRefresh == null) {
                return;
            }
            email = email.trim();
//            int sss= userAcounts==null?0:userAcounts.size();
//            SystemUtil.log("UI UPDATE back registe =======,任务队列的长度是:"+sss);
            if (userAcounts != null) {
                for (UserAcount ua : userAcounts) {
                    if (ua.getUser_name().equals(email)) {
                        BackgroundRefresh backgroundRefresh_temp = backgroundRefresh;
                        ua.setBackgroundRefresh(backgroundRefresh_temp);
                        SystemUtil.log("callback registe 成功注册:" + email + "的回调!");
                        break;
                    }
                }
            }


        }

        public void clearCallBack() {

            if (userAcounts != null) {
                for (UserAcount ua : userAcounts) {
                    ua.setBackgroundRefresh(null);
                }

            }

        }

        public void clearTaskStatuses() {
            if (userAcounts != null) {
                for (UserAcount ua : userAcounts) {
                    ua.getMailTaskStatus().setServerConnStatus(0);
                    ua.getMailTaskStatus().setCurrentPosition(0);
                    ua.getMailTaskStatus().setTotalAmount(0);
                    ua.getMailTaskStatus().setWorkModule(0);
                }
                SystemUtil.log("所有的工作状态被销毁，进入完全的后台工作模式");
            }

        }

        public void update_last_rcv(String email) {
            if ("".equals(email)||email==null) {
                return;
            }
            email=email.trim();
            if (userAcounts!=null) {
                for (UserAcount ua:userAcounts) {
                    if (ua.getUser_name().equals(email)) {
                        ua.setLast_task_Rcv(System.currentTimeMillis());
                        break;
                    }
                }
            }


        }
    }

    private synchronized void updateTaskStatusInService(int serverConnStatus, int currentPosition, int totalAmount, int workModule, String email) {
        if ("".equals(email) || email == null) {
            return;
        }
        email = email.trim();

        if (userAcounts != null) {
            for (UserAcount ua : userAcounts) {
                if (ua.getUser_name().equals(email)) {
                    ua.getMailTaskStatus().setServerConnStatus(serverConnStatus);
                    ua.getMailTaskStatus().setCurrentPosition(currentPosition);
                    ua.getMailTaskStatus().setTotalAmount(totalAmount);
                    ua.getMailTaskStatus().setWorkModule(workModule);
                    SystemUtil.log("成功update:" + email + "的工作状态为:" + ua.getMailTaskStatus());
                    break;
                }
            }
        }

    }

    private MailTaskStatus getTaskStatusInService(String email) {
        if ("".equals(email) || email == null) {
            return null;
        }
        MailTaskStatus mailTaskStatus_temp = null;
        if (userAcounts != null) {
            for (UserAcount ua : userAcounts) {
                if (ua.getUser_name().equals(email)) {
                    mailTaskStatus_temp = ua.getMailTaskStatus();
                    break;
                }
            }
        }
        return mailTaskStatus_temp;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent();
        intent.setAction(Config.FaceBroadcastType.WATCHDOG);
        pi = PendingIntent.getBroadcast(watchDogService.this, 0, intent, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                0, INTERVAL, pi);
//        mailTaskStatuses = new HashMap<String, MailTaskStatus>();
//        mailTaskCallBacks = new HashMap<String, BackgroundRefresh>();
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //此广播不会携带任何数据，仅仅起到定数触发的功能
                //不管当前界面是邮件账户列表界面，都启动任务，只要当前处于非刷新模式
                if (scanflag) {
                    if (userAcounts == null || userAcounts.size() == 0) {
                        SystemUtil.log("当前邮件账户列表为空，启动帐号数据加载策略");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                userAcounts = UserAccountDao.getInstance(watchDogService.this).getUserAccountByScanFrequency();
                                if (userAcounts == null || userAcounts.size() == 0) {
                                    scanflag = false;//无数据，暂定扫描
                                    SystemUtil.log("成功加载帐号数据，但是帐号列表为空，停止任务扫描....");
                                } else {
                                    SystemUtil.log("成功加载帐号数据，开始任务扫描....");
                                    doMailTask();
                                }
                            }
                        }).start();
                    } else {
                        doMailTask();
                    }

                } else {
                    SystemUtil.log("当前在刷新帐号数据或者帐号数据列表为空，无任务，放弃界面扫描，等待...");
                }
            }
        };
        registerReceiver(receiver, new IntentFilter(Config.FaceBroadcastType.WATCHDOG));

        reloadAccountReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                scanflag = false;//收到刷新广播，锁定扫描程序，令其暂停 ，开始刷新数据
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        userAcounts = UserAccountDao.getInstance(watchDogService.this).getUserAccountByScanFrequency();
                        SystemUtil.log("重新加载完毕用户帐号数据....");
                        if (userAcounts == null || userAcounts.size() == 0) {
                            scanflag = false;//加载完毕，但是无任务 ，锁定扫描程序，令其over
                        } else {
                            scanflag = true;//加载完毕，解锁扫描程序，令其恢复扫描
                        }

                    }
                }).start();
            }
        };
        registerReceiver(reloadAccountReceiver, new IntentFilter(Config.FaceBroadcastType.RELOAD_ACCOUNT_FORSCAN));

    }

    private void doMailTask() {
        SystemUtil.log("当前任务启动,被扫描的账户数目是:" + userAcounts.size());
        //根据userAcounts中的账户接收频率情况来决定本次是否处理某邮件账户的收信操作
        for (final UserAcount userAcount : userAcounts) {
            SystemUtil.log("user callback object:"+userAcount.getBackgroundRefresh());
            if (userAcount.getScanfrequency() != 0) {
                Long last_task_rcv = userAcount.getLast_task_Rcv();
                Long timeDiff = System.currentTimeMillis() - last_task_rcv;
                if (timeDiff > userAcount.getScanfrequency() * 60 * 1000 && userAcount.getMailTaskStatus().getWorkModule() == 0) {
                    //开始接收 ，首先找出此账户对应的接秒回调函数。
                    if (mAccountsFragment == null) {
                        SystemUtil.log("当前界面不是邮件账户列表界面，放弃界面扫描式刷新，启动后台刷新。。");
                        //后台刷新很简单，只是收取后入库，不做前台提示和任务限制

                    } else {
                        //首先锁定模式，一旦后台接收邮件则不允许前台点击操作按钮
                        updateTaskStatusInService(5, 0, 0, 2, userAcount.getUser_name().trim());
                        userAcount.setLast_task_Rcv(System.currentTimeMillis());
                        SystemUtil.log("当前界面是邮件账户列表界面，启动界面扫描式刷新");
                         BackgroundRefresh _backgroundRefresh = userAcount.getBackgroundRefresh();
                        if (_backgroundRefresh != null) {
                            _backgroundRefresh.Bg_fresh('0');//标识后台开始收取，前台作出刷新动作
                            SystemUtil.log("start up "+userAcount.getUser_name()+"'s callback ,param is '0'");
                        }

                        FaceMailProvider.doMailReceiveTest(userAcount, Config.MAIL_TASK_RCV, new IMailResponse() {

                            @Override
                            public void onReceive(IMailRequest iMailRequest, JsonValue obj) {
                                //标识任务完成
                                SystemUtil.log("rcv done....." + iMailRequest.getUserAccount().getUser_name());
                                updateTaskStatusInService(0, 0, 0, 0, userAcount.getUser_name().trim());
                                //接收完毕，更新最后接收事件
                                userAcount.setLast_task_Rcv(System.currentTimeMillis());
                                BackgroundRefresh _backgroundRefresh = iMailRequest.getUserAccount().getBackgroundRefresh();
                                if (_backgroundRefresh != null) {
                                    _backgroundRefresh.Bg_fresh('1');//标识后台收取ok，前台作出清理动作
                                    SystemUtil.log("start up " + userAcount.getUser_name() + "'s callback ,param is '1'");
                                }
                            }

                            @Override
                            public void onReceiveOne(IMailRequest iMailRequest, JsonValue obj) {

                            }

                            @Override
                            public void onReceiveFirst(IMailRequest iMailRequest, int total) {

                            }

                            @Override
                            public void onReceiveError(IMailRequest iMailRequest, String errorMessage) {
                                //发生错误
                                SystemUtil.log("rcv done error....." + iMailRequest.getUserAccount().getUser_name());
                                updateTaskStatusInService(0, 0, 0, 0, userAcount.getUser_name().trim());
                                userAcount.setLast_task_Rcv(System.currentTimeMillis());
                                BackgroundRefresh _backgroundRefresh = iMailRequest.getUserAccount().getBackgroundRefresh();
                                if (_backgroundRefresh != null) {
                                    _backgroundRefresh.Bg_fresh('2'); //标识后台收取error，前台作出清理动作
                                    SystemUtil.log("start up " + userAcount.getUser_name() + "'s callback ,param is '2'");
                                }
                            }
                        });

                    }

                } else {
                    //此账户在约定时间内已经被接收过一次了，本次放弃
                    SystemUtil.log(userAcount.getUser_name() + "预订时间内已收过一次,时差" + timeDiff + "=" + (timeDiff / 60 / 1000) + "分,放弃,时间差必须为:" + userAcount.getScanfrequency() + " 分钟,此账户的工作模式为：" + userAcount.getMailTaskStatus());
                }


            } else {
                SystemUtil.log(userAcount.getUser_name() + "-->该邮件账户的扫描频率是0，放弃..");
            }
        }
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return faceBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        unregisterReceiver(reloadAccountReceiver);
        if (alarmManager != null) {
            alarmManager.cancel(pi);
        }
        if (userAcounts != null) {
            for (UserAcount ua : userAcounts) {
                ua.close();
            }
        }
        SystemUtil.log("watchdog gone......");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public interface BackgroundRefresh {
        public void Bg_fresh(Object... objects);
    }
}
