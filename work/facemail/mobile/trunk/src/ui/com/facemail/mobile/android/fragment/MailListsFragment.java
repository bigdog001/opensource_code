package com.facemail.mobile.android.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.facemail.mobile.android.R;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface;
import com.facemail.mobile.android.application.FaceMailApplication;
import com.facemail.mobile.android.db.bean.BaseBean;
import com.facemail.mobile.android.db.bean.FaceMailMessage;
import com.facemail.mobile.android.db.bean.UserAcount;
import com.facemail.mobile.android.db.conf.Config;
import com.facemail.mobile.android.db.dao.FaceMailMessageDao;
import com.facemail.mobile.android.db.dao.UserAccountDao;
import com.facemail.mobile.android.util.SystemUtil;
import com.facemail.mobile.android.view.FaceTitleBar;
import com.facemail.mobile.android.watchdog.watchDogService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 5/8/13
 * Time: 10:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class MailListsFragment extends BaseFragment {
    private View mMailListsView;
    private ListView mailsList;
    private UserAcount userAcount;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mMailListsView = inflater.inflate(R.layout.mail_message_lists, null);
        initView(mMailListsView);
        return mMailListsView;
    }


    private void initView(View mMailListView) {
        mailsList = (ListView) mMailListView.findViewById(R.id.email_message_lists);
        mailsList.setDivider(mContext.getResources().getDrawable(R.drawable.face_mail_divider));


    }

    public void initTitleBar() {
        faceTitleBar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回到账户列表的界面
                MailListsFragment.this.doReturn();
            }
        });
        faceTitleBar.setMidText(userAcount.getUser_name());
        faceTitleBar.setLeftText(mContext.getResources().getString(R.string.mail_title_bar_return));
        faceTitleBar.setRightText(mContext.getResources().getString(R.string.mail_title_bar_setting));
        faceTitleBar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示功能，如收邮件的频率
                showFrequency();
            }
        });
    }

    private void showFrequency() {
        Dialog dialog = null;
        AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.mail_message_rcv_frequency_label);
        final int now_index = SystemUtil.getIndex(Config.MAIL_RCV_FREQUENCY_VALUE, userAcount.getScanfrequency());
        builder.setSingleChoiceItems(R.array.rcv_frequency, now_index, new OnClickListener() {
            public void onClick(final DialogInterface dialog, final int which) {
                if (which != now_index) {

                    String rcv_frequency = getResources().getStringArray(R.array.rcv_frequency)[which];
                    Toast.makeText(getActivity(), "您选择了： " + rcv_frequency, 0).show();
                    userAcount.setScanfrequency(Config.MAIL_RCV_FREQUENCY_VALUE[which]);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            UserAccountDao.getInstance(mContext).updateRcvFrequency(Config.MAIL_RCV_FREQUENCY_VALUE[which], userAcount);

                            if (which==0) {
                                //选择了从不自动接收
                               List<UserAcount> userAcounts = UserAccountDao.getInstance(mContext).getUserAccountByScanFrequency();
                                if (userAcounts==null||userAcounts.size()<1) {
                                    //说明此时无任何帐号需要被扫描，写入服务不自动启动的标记，然后停止服务
                                    SharedPreferences.Editor editor = FaceMailApplication.getFaceMailContext().getSharedPreferences().edit();
                                    editor.putBoolean(Config.RCV_SERVICE_AUTORUN, false);
                                    editor.commit();
                                    if (SystemUtil.isServiceRunning(mContext, "com.facemail.mobile.android.watchdog.watchDogService")) {
                                        Intent watchDogIntent = new Intent(mContext, watchDogService.class);
                                        mContext.stopService(watchDogIntent);
                                    }
                                    SystemUtil.log("停止服务，此时无任何帐号需要被扫描....");
                                }

                            }else {
                                if (!SystemUtil.isServiceRunning(mContext, "com.facemail.mobile.android.watchdog.watchDogService")) {
                                    //如果服务未启动，则启动服务
                                    SystemUtil.log("服务未启动，自动运行邮件扫描服务....");
                                    Intent watchDogIntent = new Intent(mContext, watchDogService.class);
                                    mContext.startService(watchDogIntent);

                                    //写入启动标记
                                    SharedPreferences.Editor editor = FaceMailApplication.getFaceMailContext().getSharedPreferences().edit();
                                    editor.putBoolean(Config.RCV_SERVICE_AUTORUN, true);
                                    editor.commit();

                                }
                            }

                            //发广播通知任务监视狗的数据刷新
                            Intent reload_intent = new Intent(Config.FaceBroadcastType.RELOAD_ACCOUNT_FORSCAN);
                            mContext.sendBroadcast(reload_intent);

                            if (dialog != null) {
                                dialog.dismiss();
                            }

                        }
                    }).start();




                }
            }
        });
        builder.setNegativeButton("cancle", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        setmCanReturn(true);
        Bundle info = getArguments();
        userAcount = (UserAcount) info.getSerializable("user");
        super.onActivityCreated(savedInstanceState);
        if (pd == null) {
            pd = new ProgressDialog(getActivity());
            pd.setIndeterminate(false);
            pd.setCancelable(true);
            pd.setMessage(mContext.getResources().getString(R.string.loading));
        }
        pd.show();
        //设置邮件数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<FaceMailMessage> faceMailMessages = FaceMailMessageDao.getInstance(FaceMailApplication.getFaceMailContext().getApplicationContext()).getMails(userAcount.getUser_name(),0);
                FaceMailApplication.getFaceMailContext().getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                        if (faceMailMessages != null && faceMailMessages.size() > 0) {
                            MailListsAdapter adapter = new MailListsAdapter(mContext, faceMailMessages);
                            mailsList.setAdapter(adapter);
                        } else {
                            Toast.makeText(mContext, R.string.mail_message_detail_nomessage, 0).show();
                        }
                    }
                });


            }
        }).start();
    }

    @Override
    public void doReturn() {
        //响应返回键
        faceTitleBar.hideTitle(true);
        //返回到账户列表的界面
        mCenterViewListener.doProcess(5, null);
    }

    public class MailListsAdapter extends BaseAdapter {
        private Context mContext;
        private List<FaceMailMessage> faceMailMessages;

        public MailListsAdapter(Context mContext, List<FaceMailMessage> faceMailMessages) {
            this.mContext = mContext;
            this.faceMailMessages = faceMailMessages;
        }

        @Override
        public int getCount() {
            return faceMailMessages.size();
        }

        @Override
        public Object getItem(int position) {
            return faceMailMessages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MailListsHolder mailListsHolder;
            if (convertView == null) {
                mailListsHolder = new MailListsHolder();
            } else {
                mailListsHolder = (MailListsHolder) convertView.getTag();
            }
            FaceMailMessage faceMailMessage = faceMailMessages.get(position);
            mailListsHolder.init(faceMailMessage);
            convertView = mailListsHolder.root;
            convertView.setTag(mailListsHolder);
            return convertView;
        }
    }

    public class MailListsHolder {
        private View root;
        private TextView email_title;
        private TextView email_rcv_time;
        private FaceMailMessage faceMailMessage;

        public MailListsHolder() {
            this.root = View.inflate(mContext, R.layout.mail_message_lists_item, null);
            email_title = (TextView) root.findViewById(R.id.email_title);
            email_rcv_time = (TextView) root.findViewById(R.id.email_rcv_time);

        }

        public void init(final FaceMailMessage faceMailMessage) {
            this.faceMailMessage = faceMailMessage;
            email_title.setText(faceMailMessage.getMail_title().length() > 15 ? faceMailMessage.getMail_title().substring(0, 15) + "..." : faceMailMessage.getMail_title());
            email_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BaseBean[] baseBeans = new BaseBean[2];
                    baseBeans[0] = userAcount;
                    baseBeans[1] = faceMailMessage;
                    mCenterViewListener.doProcess(6, baseBeans);
                }
            });
            email_rcv_time.setText(SystemUtil.getDate(faceMailMessage.getMail_rcv_time(), 0));
        }
    }
}
