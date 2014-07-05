package com.facemail.mobile.android.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.*;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;

import com.facemail.mobile.android.R;
import com.facemail.mobile.android.application.FaceMailApplication;
import com.facemail.mobile.android.db.bean.FaceMailMessage;
import com.facemail.mobile.android.db.bean.UserAcount;
import com.facemail.mobile.android.db.conf.Config;
import com.facemail.mobile.android.db.dao.UserAccountDao;
import com.facemail.mobile.android.mail.FaceMailProvider;
import com.facemail.mobile.android.mail.IMailRequest;
import com.facemail.mobile.android.mail.IMailResponse;
import com.facemail.mobile.android.mail.MailTaskStatus;
import com.facemail.mobile.android.ui.SlidingActivity;
import com.facemail.mobile.android.util.SystemUtil;
import com.facemail.mobile.android.util.json.JsonObject;
import com.facemail.mobile.android.util.json.JsonValue;
import com.facemail.mobile.android.view.ActionItem;
import com.facemail.mobile.android.view.QuickAction;
import com.facemail.mobile.android.watchdog.watchDogService;

public class AccountsFragment extends BaseFragment implements OnClickListener {

    private View mAccountUnAvailable;
    private View mAccountAvailable;
    private Button btn_myemail_accountlists_UnAvailable;
    private ListView email_account_list;
    private ProgressDialog mprogressDialog;
    private watchDogService mWatchDogService;
    private watchDogService.FaceBinder faceBinder;
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className,IBinder localBinder) {
            SystemUtil.log("服务链接成功....");
            faceBinder=  (watchDogService.FaceBinder) localBinder;
            mWatchDogService = faceBinder.getService();
            faceBinder.setObserveFragment(AccountsFragment.this);

            //服务链接成功后刷新界面
            FaceMailApplication.getFaceMailContext().getUserAcounts(new UserLoadListener() {
                @Override
                public void success(final List<UserAcount> userAcount) {
                    FaceMailApplication.getFaceMailContext().getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                            if (userAcount != null && userAcount.size()>0) {
                                email_account_list.setAdapter(new AccountsAdaptor(userAcount));
                            }

                        }
                    });
                }
            });
        }
        public void onServiceDisconnected(ComponentName arg0) {
            mWatchDogService = null;
        }
    };



    /**
     * 此界面有2种情况
     * 1 ,用户没有设置任何邮件帐号则在界面上显示提示其设置邮件账户，点击设置后直接弹出添加账户的对话框
     * 2 ,用户已经添加些许帐号，则显示账户列表
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState) ;
        if (!FaceMailApplication.getFaceMailContext().getSetupStart()) {
            //无账户设置,提示其设置邮件账户
            mAccountUnAvailable=  inflater.inflate(R.layout.myemail_accountlists_unavailable, null);
            btn_myemail_accountlists_UnAvailable= (Button) mAccountUnAvailable.findViewById(R.id.btn_myemail_accountlists_UnAvailable);
            btn_myemail_accountlists_UnAvailable.setOnClickListener(this);
            return  mAccountUnAvailable;
        }else {
            Intent intent = new Intent(mContext, watchDogService.class);
            mContext.bindService(intent,mConnection, Context.BIND_AUTO_CREATE);

            //显示账户列表
            mAccountAvailable= inflater.inflate(R.layout.myemail_accountlists_available, null);
            email_account_list= (ListView) mAccountAvailable.findViewById(R.id.email_account_list);
            email_account_list.setItemsCanFocus(true);
            email_account_list.setFocusable(false);
            email_account_list.setAddStatesFromChildren(true);
            email_account_list.setFocusableInTouchMode(false);
            email_account_list.setVerticalFadingEdgeEnabled(false);
            email_account_list.setDivider(mContext.getResources().getDrawable(
                    R.drawable.face_mail_divider));
            if (pd==null) {
                pd = new ProgressDialog(getActivity());
                pd.setIndeterminate(false);
                pd.setCancelable(true);
                pd.setMessage(mContext.getResources().getString(R.string.loading));
            }
                pd.show();

           /* FaceMailApplication.getFaceMailContext().getUserAcounts(new UserLoadListener() {
                @Override
                public void success(final List<UserAcount> userAcount) {
                    FaceMailApplication.getFaceMailContext().getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                            email_account_list.setAdapter(new AccountsAdaptor(userAcount));
                        }
                    });
                }
            });*/
            return  mAccountAvailable;
        }
    }

	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
        setCenterViewListener(((SlidingActivity)getActivity()));
        setmCanReturn(false);
	}

    @Override
    public void doReturn() {
    }

    @Override
    public void initTitleBar() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_myemail_accountlists_UnAvailable:
                mCenterViewListener.doProcess(Config.ListenerType.SHOW_ACCOUNT_SETUP,null);
                break;

            default: break;
        }

    }


    class AccountsAdaptor extends BaseAdapter{
        private ActionItem action_email_account_receive ;
        private ActionItem action_email_account_edit ;
        private ActionItem action_email_account_delete ;
//        private ActionItem action_email_account_setting ;
        private QuickAction qab;
        private  List<UserAcount> accounts;
       public AccountsAdaptor(List<UserAcount> accounts) {
            this.accounts = accounts;
        }

        @Override
        public int getCount() {
            return accounts.size();  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Object getItem(int position) {
            return accounts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            AccountListsHolder holder;
            if (convertView==null) {
             holder=new AccountListsHolder();
            }else {
             holder= (AccountListsHolder) convertView.getTag();
            }
            UserAcount ua=accounts.get(position);
            holder.init(ua);
            convertView=holder.root;
            convertView.setTag(holder);
            return convertView;
        }
        public void updateViewData(List<UserAcount>users){
            this.accounts=users;
            notifyDataSetChanged();
        }

         class AccountListsHolder implements OnClickListener{
              private ImageView iv_email_account_image_vender;
              private TextView email_account_email_address;
              private TextView email_account_email_last_rcv;
              private ImageView ib_operation;
              private TextView mail_total;   //库中一共有多少封邮件
              private TextView mail_number;  //当前接收的是本次的第几封邮件
              private TextView mail_total_one_receive;//本次一共接收多少封邮件
              private LinearLayout mail_reveice_count;
              private LinearLayout mail_reveice_loading_container;
              private ImageView rcv_loading;
              private Animation anim_rcv_loading ;

              private  View root;
              private  UserAcount mUa;
              AccountListsHolder() {
                  root=View.inflate(mContext,R.layout.mail_account_listview_item,null);
                  iv_email_account_image_vender= (ImageView) root.findViewById(R.id.iv_email_account_image_vender);
                  email_account_email_address= (TextView) root.findViewById(R.id.email_account_email_address);
                  mail_total= (TextView) root.findViewById(R.id.mail_total);
                  mail_number= (TextView) root.findViewById(R.id.mail_number);
                  mail_total_one_receive= (TextView) root.findViewById(R.id.mail_total_one_receive);
                  mail_reveice_count= (LinearLayout) root.findViewById(R.id.mail_reveice_count);
                  mail_reveice_loading_container= (LinearLayout) root.findViewById(R.id.mail_reveice_loading_container);
                  email_account_email_last_rcv= (TextView) root.findViewById(R.id.email_account_email_last_rcv);
                  rcv_loading= (ImageView) root.findViewById(R.id.mail_reveice_loading);
                  ib_operation= (ImageView) root.findViewById(R.id.ib_operation);
                  anim_rcv_loading= AnimationUtils.loadAnimation(mContext, R.anim.mail_reveice_loading_anim);
              }

            public void init(UserAcount ua){
                mUa=ua;
                email_account_email_address.setText(mUa.getUser_name());
                email_account_email_address.setTag(4);
                email_account_email_address.setOnClickListener(AccountListsHolder.this);
                iv_email_account_image_vender.setImageResource(R.drawable.sohu);
                ib_operation.setImageResource(R.drawable.email_account_do);
                email_account_email_last_rcv.setText(SystemUtil.getDate(mUa.getLastRcv(), 0));
                mail_total.setText(ua.getTotal_mail_number()+"");
                SystemUtil.log("start to registe callback to binder....username is:"+mUa.getUser_name()+",binder is:"+faceBinder);
                faceBinder.registeUICallBack(mUa.getUser_name().trim(),new watchDogService.BackgroundRefresh() {
                    @Override
                    public void Bg_fresh(Object... objects) {
                        if (objects.length>0) {
                            SystemUtil.log("callback param-->"+objects[0].toString());
                            char command=  objects[0].toString().charAt(0);
                            switch (command) {
                                case '0':
                                  SystemUtil.log("开始收取。。。");
                                    FaceMailApplication.getFaceMailContext().getHandler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            ib_operation.setClickable(false);
                                            mail_reveice_loading_container.setVisibility(View.VISIBLE); //开始只显示动画标识在链接邮件服务器
                                            if (anim_rcv_loading==null||!anim_rcv_loading.isInitialized()) {
                                                anim_rcv_loading=AnimationUtils.loadAnimation(mContext, R.anim.mail_reveice_loading_anim);
                                            }
                                            rcv_loading.setAnimation(anim_rcv_loading);
                                            rcv_loading.startAnimation(anim_rcv_loading);
                                        }
                                    });


                                    break;
                                case '1':
                                    SystemUtil.log("收取完毕。。。");

                                    FaceMailApplication.getFaceMailContext().getHandler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            rcv_loading.clearAnimation();
                                            mail_reveice_loading_container.setVisibility(View.GONE);
                                            ib_operation.setClickable(true);
                                            Toast.makeText(mContext,mContext.getResources().getString(R.string.mail_rcv_email_done),0).show();

                                        }
                                    });

                                    break;
                                case '2':
                                    SystemUtil.log("收取出错。。。");

                                    FaceMailApplication.getFaceMailContext().getHandler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            rcv_loading.clearAnimation();
                                            mail_reveice_loading_container.setVisibility(View.GONE);
                                            ib_operation.setClickable(true);
                                            Toast.makeText(mContext,mContext.getResources().getString(R.string.mail_rcv_email_done),0).show();
                                        }
                                    });

                                    break;
                                default:break;
                            }

                        }

                    }
                });
                ib_operation.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                          action_email_account_receive = new ActionItem(getResources().getDrawable(R.drawable.mail_rcv_refresh_unselected),getResources().getString(R.string.mail_rcv_refresh),AccountListsHolder.this,Config.QickActionID.REFRESH);
                          action_email_account_edit = new ActionItem(getResources().getDrawable(R.drawable.mail_account_edit),getResources().getString(R.string.mail_account_edit),AccountListsHolder.this,Config.QickActionID.EDIT);
                          action_email_account_delete = new ActionItem(getResources().getDrawable(R.drawable.mail_account_delete),getResources().getString(R.string.mail_account_delete),AccountListsHolder.this,Config.QickActionID.DELETE);
//                          action_email_account_setting =new ActionItem(getResources().getDrawable(R.drawable.mail_account_setting),getResources().getString(R.string.mail_account_setting),AccountListsHolder.this,Config.QickActionID.SETTING);;
                          qab=new QuickAction(v);
                          qab.addActionItem(action_email_account_receive);
                          qab.addActionItem(action_email_account_edit);
                          qab.addActionItem(action_email_account_delete);
//                          qab.addActionItem(action_email_account_setting);
                          qab.show();
                    }
                });

              MailTaskStatus _mailTaskStatus = faceBinder.getTaskStatus(mUa.getUser_name());
                if (_mailTaskStatus!=null) {
                    if (_mailTaskStatus.getWorkModule()!=0) {
                        //进入界面后探测账户的工作状态，以此更新界面的提示状态
                        ib_operation.setClickable(false);
                        mail_reveice_loading_container.setVisibility(View.VISIBLE); //开始只显示动画标识在链接邮件服务器
                        if (anim_rcv_loading==null||!anim_rcv_loading.isInitialized()) {
                            anim_rcv_loading=AnimationUtils.loadAnimation(mContext, R.anim.mail_reveice_loading_anim);
                        }
                        rcv_loading.setAnimation(anim_rcv_loading);
                        rcv_loading.startAnimation(anim_rcv_loading);
                    }
                }


            }

            @Override
            public void onClick(View v) {
                int viewTag;
                if(v.getTag()!=null){
                    viewTag=Integer.valueOf(v.getTag().toString().trim())  ;
                }  else {
                    return;
                }
                switch (viewTag) {
                    case 1:
                        qab.dismiss();
                       // mail_reveice_count.setVisibility(View.VISIBLE);    显示数字
                        mail_reveice_loading_container.setVisibility(View.VISIBLE); //开始只显示动画标识在链接邮件服务器
                        if (anim_rcv_loading==null||!anim_rcv_loading.isInitialized()) {
                            anim_rcv_loading=AnimationUtils.loadAnimation(mContext, R.anim.mail_reveice_loading_anim);
                        }
                        rcv_loading.setAnimation(anim_rcv_loading);
                        rcv_loading.startAnimation(anim_rcv_loading);
                        ib_operation.setClickable(false);
                        //开始收邮件
                        faceBinder.updateTaskStatus(5,0,0,1,mUa.getUser_name());
                        faceBinder.update_last_rcv(mUa.getUser_name());
//                        faceBinder.setHandModule(true);
                        FaceMailProvider.doMailReceive(mUa,Config.MAIL_TASK_RCV,new IMailResponse() {
                            @Override
                            public void onReceive(IMailRequest iMailRequest, JsonValue obj) {
                                //弹出通知，告知某账户收取完毕
                                if (obj instanceof JsonObject) {
                                    faceBinder.update_last_rcv(mUa.getUser_name());
                                    JsonObject map = (JsonObject) obj;
                                    if (SystemUtil.noError(iMailRequest, map)) {
//                                       final String result=map.getString(IMailResponse.EMAIL);
                                            FaceMailApplication.getFaceMailContext().getHandler().post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    rcv_loading.clearAnimation();
                                                    mail_reveice_count.setVisibility(View.GONE);
                                                    mail_reveice_loading_container.setVisibility(View.GONE);
                                                    ib_operation.setClickable(true);
                                                    mail_total_one_receive.setText("0");   //本次一共接收多少封邮件
                                                    mail_number.setText(""+0);  //当前接收的是本次的第几封邮件
                                                    Toast.makeText(mContext,mContext.getResources().getString(R.string.mail_rcv_email_done),0).show();
                                                    faceBinder.updateTaskStatus(0,0,0,0,mUa.getUser_name());
                                                }
                                            });
                                    }
                                }
                            }

                            @Override
                            public void onReceiveOne(IMailRequest iMailRequest, JsonValue obj) {
                                 //每收到一封邮件触发一次此函数,收取完毕后触发 onReceive函数，在onReceiveOne函数中更新帐号列表中item对应的new mail count
                                 //每收到一封邮件将可以从obj中取出此邮件的标题
                                FaceMailApplication.getFaceMailContext().getHandler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                       //刷新当前收到的邮件数以及刷新收到的邮件标题
                                        mail_number.setText((Integer.valueOf(mail_number.getText() + "") + 1) + "");  //当前接收的是本次的第几封邮件
                                        mail_total.setText(Integer.valueOf(mail_total.getText()+"")+1+""); //库中一共有多少封邮件
                                    }
                                });
                                faceBinder.updateTaskStatus(2,Integer.valueOf(mail_number.getText()+""),Integer.valueOf(mail_total_one_receive.getText()+""),1,mUa.getUser_name());
                            }

                            @Override
                            public void onReceiveFirst(IMailRequest iMailRequest, final int total) {

                                FaceMailApplication.getFaceMailContext().getHandler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (total==0) {
                                            Toast.makeText(mContext,mUa.getUser_name()+mContext.getResources().getString(R.string.mail_rcv_nonew_email),0).show();
                                            rcv_loading.clearAnimation();
                                            mail_total_one_receive.setText("0"); //本次一共接收多少封邮件
                                            mail_number.setText(""+0);           //当前接收的是本次的第几封邮件
                                            mail_reveice_loading_container.setVisibility(View.GONE);
                                            mail_reveice_count.setVisibility(View.GONE);
                                            ib_operation.setClickable(true);
                                            faceBinder.updateTaskStatus(0,0,0,0,mUa.getUser_name());
                                        }else {
                                        mail_total_one_receive.setText(total + "");  //本次一共接收多少封邮件
                                        mail_number.setText(0 + "");                 //当前接收的是本次的第几封邮件
                                        mail_reveice_count.setVisibility(View.VISIBLE);
                                            faceBinder.updateTaskStatus(1,0,total,1,mUa.getUser_name());
                                        }
                                    }
                                });

                            }

                            @Override
                            public void onReceiveError(IMailRequest iMailRequest, final String errorMessage) {
                                faceBinder.update_last_rcv(mUa.getUser_name());
                                faceBinder.updateTaskStatus(0, 0, 0, 0, mUa.getUser_name());
                                FaceMailApplication.getFaceMailContext().getHandler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (errorMessage!=null&&!"".equals(errorMessage)) {
                                            rcv_loading.clearAnimation();
                                            mail_total_one_receive.setText("0");  //本次一共接收多少封邮件
                                            mail_number.setText(""+0);   //当前接收的是本次的第几封邮件
                                            mail_reveice_count.setVisibility(View.GONE);
                                            mail_reveice_loading_container.setVisibility(View.GONE);
                                            ib_operation.setClickable(true);

                                            Toast.makeText(mContext,mContext.getResources().getString(R.string.mail_rcv_on_error),0).show();
                                        }
                                    }
                                });

                            }
                        });
                        break;
                    case 2:
                        qab.dismiss();
                        //开始编辑帐号的策略
                        mCenterViewListener.doProcess(2,mUa);
//                        ((SlidingActivity)getActivity()).showSetup(mUa);
                        break;
                    case 3:
                        qab.dismiss();
                        //开始删除帐号，弹框询问
                        Dialog dialog=null;
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle(R.string.mail_account_delete_title);
                        builder.setMessage(R.string.mail_account_delete_message);
                        builder.setPositiveButton(R.string.mail_account_delete_positive,new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mprogressDialog=new ProgressDialog(getActivity());
                                mprogressDialog.setMessage(mContext.getResources().getString(R.string.loading));
                                mprogressDialog.setIndeterminate(false);
                                mprogressDialog.setCancelable(true);
                             //开始删除
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                         UserAccountDao.getInstance(mContext).deleteUserAccount(mUa);
                                         FaceMailApplication.getFaceMailContext().getHandler().post(new Runnable() {
                                             @Override
                                             public void run() {
                                                 mprogressDialog.dismiss();
                                                 Toast.makeText(mContext, R.string.mail_account_delete_suc,0).show();
                                                 mCenterViewListener.doProcess(3,null);
                                             }
                                         });
                                    }
                                }).start();
                            }
                        });
                        builder.setNegativeButton(R.string.mail_account_delete_nagtive,new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        dialog = builder.create();
                        dialog.show();
                        break;
                    case 4:
                        //点击item后转入邮件信息列表界面
//                        Toast.makeText(mContext,"popwindow..setting",0).show();

                        mCenterViewListener.doProcess(4,mUa);
                        break;
                    default:break;
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SystemUtil.log("MailDetailFragment被销毁....");
        if (faceBinder!=null) {
            faceBinder.setObserveFragment(null);
            faceBinder.clearCallBack();
//            faceBinder.clearTaskStatuses();
            mContext.unbindService(mConnection);
        }
    }

    public interface UserLoadListener{
        public void success(List<UserAcount> userAcount);
    }



}