package com.facemail.mobile.android.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.facemail.mobile.android.R;
import com.facemail.mobile.android.application.FaceMailApplication;
import com.facemail.mobile.android.db.bean.BaseBean;
import com.facemail.mobile.android.db.bean.FaceMailMessage;
import com.facemail.mobile.android.db.bean.UserAcount;
import com.facemail.mobile.android.db.conf.Config;
import com.facemail.mobile.android.db.dao.UserAccountDao;
import com.facemail.mobile.android.fragment.*;
import com.facemail.mobile.android.mail.FaceMailProccess;
import com.facemail.mobile.android.util.SystemUtil;
import com.facemail.mobile.android.view.SlidingMenu;

public class SlidingActivity extends FragmentActivity implements LeftFragment.LeftMenuOnClickListener, CenterViewListener {
    private SlidingMenu mSlidingMenu;    //滑动自定义控件本身
    private LeftFragment leftFragment;  //  左侧的菜单 即第一屏
    //    private RightFragment rightFragment;   //一共3屏,这是最右边的一屏
    private AccountsFragment viewPageFragment; //即默认的当前屏,显示fliper和左右2个按钮
    private BaseFragment mCurentFragment;
    private Context mContext;
    private ProgressDialog pd;
    private Dialog dialog;
    private int selectedMenu;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        init();
//        initListener();
        initCreateDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SystemUtil.log("facemail113 destroy...");
    }

    private void initCreateDialog() {
        pd = new ProgressDialog(mContext);
        pd.setIndeterminate(false);
        pd.setCancelable(true);
        pd.setMessage(mContext.getResources().getString(R.string.loading));
        if (!FaceMailApplication.getFaceMailContext().getSetupStart()) {//决定是否出现只显示start的左侧界面
            //此时弹出添加用户帐号的view ,如果用户没有添加任何帐号，如果用户同意输入则进入添加帐号界面
            showSetup(null);
        } else {
            //已经设置帐号
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //检查 selectedMenu的合法性
        checkSelectedMenu(selectedMenu);
        leftFragment.setSelectedGuy(selectedMenu);
    }

    public ProgressDialog getPd() {
        return pd;
    }

    private void init() {
        mContext = SlidingActivity.this;
        mSlidingMenu = (SlidingMenu) findViewById(R.id.slidingMenu);
        mSlidingMenu.setLeftView(getLayoutInflater().inflate(
                R.layout.left_frame, null));
        mSlidingMenu.setRightView(getLayoutInflater().inflate(
                R.layout.right_frame, null));
        mSlidingMenu.setCenterView(getLayoutInflater().inflate(
                R.layout.center_frame, null));
        mSlidingMenu.setCanSliding(false, false);
        FragmentTransaction t = this.getSupportFragmentManager()
                .beginTransaction();
        leftFragment = new LeftFragment();
        leftFragment.setLeftMenuOnClickListener(this);
        //此时需要处理菜单栏中哪项被标记为选中状态
        Bundle bundle = getIntent().getExtras();
        SystemUtil.log(bundle == null ? "null" : bundle + "----");
        if (bundle == null) {
            selectedMenu = Config.leftMenu[1];
        } else {
            selectedMenu = bundle.getInt("selectedMenu");
        }
        t.replace(R.id.left_frame, leftFragment);

/*
        rightFragment = new RightFragment();
        t.replace(R.id.right_frame, rightFragment);
*/

        //取默认显示的中间view,如果用户不做制定则默认选择左侧的收件箱菜单并显示邮件账户列表
        viewPageFragment = new AccountsFragment(); //默认的界面，即邮件帐号列表
        t.replace(R.id.center_frame, viewPageFragment);
        t.commit();

        mSlidingMenu.setCanSliding(true, false);
//        leftFragment.setSelectedGuy(selectedMenu);
    }

    private void checkSelectedMenu(int selectedMenuTemp) {
        boolean mInleftMenu = false;
        for (int item : Config.leftMenu) {
            if (item == selectedMenuTemp) {
                mInleftMenu = true;
                break;
            }
        }
        if (!mInleftMenu) {
            selectedMenu = Config.leftMenu[0];
        }

    }


    protected void showDialog(final ProgressDialog pd, boolean isShow) {
        if (isShow) {
            if (pd != null) {
                FaceMailApplication.getFaceMailContext().getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        pd.show();
                    }
                });

            }
        } else {
            if (pd != null) {
                try {
                    FaceMailApplication.getFaceMailContext().getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                        }
                    });

                } catch (Exception e) {
                }
            }
        }
    }

    //显示提示用户是否设置帐号的dialog
    public void showSetup(final UserAcount userAcount) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.setting_dialog_title_account);
//        builder.setMessage(R.string.setting_dialog_message_account);
        final View view = View.inflate(mContext, R.layout.add_account_view, null);
        if (userAcount != null) {
            setUserAccountData(userAcount, view);
        }
        Button saveAccount = (Button) view.findViewById(R.id.saveAccount);
        Button CancelAccount = (Button) view.findViewById(R.id.CancelAccount);
        saveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveAccount(view, userAcount);

            }
        });
        CancelAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }


    public void setUserAccountData(UserAcount userAccountData, View view) {
        if (userAccountData != null) {
            //如果 userAccountData不为空，则表明需要edite用户数据
            EditText user_email = ((EditText) view.findViewById(R.id.et_loginname_setting));
            user_email.setText(userAccountData.getUser_name());
            user_email.setEnabled(false);
            ((EditText) view.findViewById(R.id.et_loginpwd_setting)).setText(userAccountData.getUser_pwd());
            ((EditText) view.findViewById(R.id.et_loginsmtp_setting)).setText(userAccountData.getUser_smtp());
            ((EditText) view.findViewById(R.id.et_loginsmtpPort_setting)).setText(userAccountData.getUser_port_smtp());
            ((EditText) view.findViewById(R.id.et_loginpop3_setting)).setText(userAccountData.getUser_pop3());
            ((EditText) view.findViewById(R.id.et_loginpop3Port_setting)).setText(userAccountData.getUser_port_pop3());
            ((TextView) view.findViewById(R.id.user_account_id)).setText(userAccountData.get_id() + "");
        }


    }

    private void saveAccount(View view, final UserAcount userAcount_update) {
        final String userEmail = ((EditText) view.findViewById(R.id.et_loginname_setting)).getText().toString().trim();
        final String userPwd = ((EditText) view.findViewById(R.id.et_loginpwd_setting)).getText().toString().trim();
        final String userSmtp = ((EditText) view.findViewById(R.id.et_loginsmtp_setting)).getText().toString().trim();
        final String userSmtpport = ((EditText) view.findViewById(R.id.et_loginsmtpPort_setting)).getText().toString().trim();
        final String userPop3 = ((EditText) view.findViewById(R.id.et_loginpop3_setting)).getText().toString().trim();
        final String userPop3Port = ((EditText) view.findViewById(R.id.et_loginpop3Port_setting)).getText().toString().trim();
        final String user_account_id = ((TextView) view.findViewById(R.id.user_account_id)).getText().toString().trim();

        if ("".equals(userEmail) || userEmail == null) {
            Toast.makeText(mContext, R.string.emailError, 0).show();
            return;
        }
        if ("".equals(userPwd) || userPwd == null) {
            Toast.makeText(mContext, R.string.pwdError, 0).show();
            return;
        }
        if ("".equals(userSmtp) || userSmtp == null) {
            Toast.makeText(mContext, R.string.smtpError, 0).show();
            return;
        }
        if ("".equals(userSmtpport) || userSmtpport == null) {
            Toast.makeText(mContext, R.string.smtpPortError, 0).show();
            return;
        }
        if (SystemUtil.isNumeric(userSmtpport)) {
            Toast.makeText(mContext, R.string.smtpPortNUmberError, 0).show();
            return;
        }
        if ("".equals(userPop3) || userPop3 == null) {
            Toast.makeText(mContext, R.string.pop3Error, 0).show();
            return;
        }
        if ("".equals(userPop3Port) || userPop3Port == null) {
            Toast.makeText(mContext, R.string.pop3PortError, 0).show();
            return;
        }
        if (SystemUtil.isNumeric(userPop3Port)) {
            Toast.makeText(mContext, R.string.pop3PortnumberError, 0).show();
            return;
        }
        //查询用户是否存在
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (userAcount_update != null) {
                    //表示需要做update操作
                    final UserAcount ua = new UserAcount();
                    ua.setUser_name(userEmail);
                    ua.setUser_pwd(userPwd);
                    ua.setUser_smtp(userSmtp);
                    ua.setUser_port_smtp(userSmtpport);
                    ua.setUser_pop3(userPop3);
                    ua.setUser_port_pop3(userPop3Port);
                    ua.set_id(Integer.valueOf(user_account_id));
                    showDialog(pd, true);
                    UserAccountDao.getInstance(mContext).updateUserAccount(ua);
                    FaceMailApplication.getFaceMailContext().flushUser();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                            dialog.dismiss();
                            Toast.makeText(mContext, R.string.updateUserAccountSuccess, 0).show();
                            AccountsFragment emailAccountlistsFragment = new AccountsFragment();
                            onclick(emailAccountlistsFragment, 0);
                        }
                    });
                } else {
                    //表示需要做保存操作
                    if (!UserAccountDao.getInstance(mContext).isExsit(userEmail)) {
                        final UserAcount ua = new UserAcount();
                        ua.setUser_name(userEmail);
                        ua.setUser_pwd(userPwd);
                        ua.setUser_smtp(userSmtp);
                        ua.setUser_port_smtp(userSmtpport);
                        ua.setUser_pop3(userPop3);
                        ua.setUser_port_pop3(userPop3Port);
                        showDialog(pd, true);
                        UserAccountDao.getInstance(mContext).insertUserAccount(ua);
                        FaceMailApplication.getFaceMailContext().flushUser();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pd.dismiss();
                                dialog.dismiss();
                                Toast.makeText(mContext, R.string.addUserAccountSuccess, 0).show();
                                SharedPreferences.Editor editor = FaceMailApplication.getFaceMailContext().getSharedPreferences().edit();
                                editor.putBoolean(Config.accountSetup, true);
                                editor.commit();
                                FaceMailApplication.getFaceMailContext().setSetupStart(true);
                                AccountsFragment emailAccountlistsFragment = new AccountsFragment();
                                onclick(emailAccountlistsFragment, 0);
                            }
                        });
                    } else {
                        FaceMailApplication.getFaceMailContext().getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, R.string.userExistErrot, 0).show();
                            }
                        });
                    }
                }
            }
        }).start();
    }

    public static void showMe(Context context, int selectedMenu) {
        Intent intent = new Intent(context, SlidingActivity.class);
        intent.putExtra("selectedMenu", selectedMenu);
        context.startActivity(intent);
    }


    @Override
    public void onclick(BaseFragment replacedFragment, int flag) {
        if (flag == 0) {
            FragmentTransaction txc = this.getSupportFragmentManager()
                    .beginTransaction();
//        txc.remove(pre);
            mCurentFragment = replacedFragment;
            replacedFragment.setCenterViewListener(this);
            replacedFragment.setFaceTitleBar(mSlidingMenu.getFaceTitleBar());
            txc.replace(R.id.center_frame, replacedFragment);
//        pre=replacedFragment;
            txc.commit();
        } else if (flag == 1) {
            showSetup(null);
        }

    }

    private void dialogFinish() {

        AlertDialog.Builder builder = new AlertDialog.Builder(SlidingActivity.this);
        builder.setTitle(R.string.mail_exit_title);
        builder.setMessage(R.string.mail_exit_message);
        builder.setPositiveButton(R.string.mail_exit_yes, new android.content.DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                SlidingActivity.this.finish();
            }
        });

        builder.setNegativeButton(R.string.mail_exit_no, new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mCurentFragment != null && mCurentFragment.ismCanReturn()) {
                mCurentFragment.doReturn();
            } else {
                //提示是否退出
                dialogFinish();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void doProcess(int param, final Object data) {
        switch (param) {
            case 1:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showSetup(null);
                    }
                });
                break;
            case 2:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showSetup((UserAcount) data);
                    }
                });
                break;
            case 3:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AccountsFragment emailAccountlistsFragment = new AccountsFragment();
                        onclick(emailAccountlistsFragment, 0);
                    }
                });
                break;

            case 4:
                final MailListsFragment mailListsFragment = new MailListsFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Config.FORWARD_PARAM1, (UserAcount) data);
                mailListsFragment.setArguments(bundle);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onclick(mailListsFragment, 0);
                    }
                });

                break;
            case 5:
                //返回到账户列表界面
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AccountsFragment emailAccountlistsFragment = new AccountsFragment();
                        onclick(emailAccountlistsFragment, 0);
                    }
                });
                break;
            case 6:
                //跳转到邮件详情界面
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (data != null) {
                            MailDetailFragment mailDetailFragment = new MailDetailFragment();
                            BaseBean[] params = (BaseBean[]) data;
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(Config.FORWARD_PARAM1, (UserAcount) params[0]);
                            bundle.putSerializable(Config.FORWARD_PARAM2, (FaceMailMessage) params[1]);
                            mailDetailFragment.setArguments(bundle);
                            onclick(mailDetailFragment, 0);
                        } else {
                            Toast.makeText(mContext, R.string.mail_message_detail_error, 0).show();
                            SystemUtil.log("在进入邮件详情界面时发生异常");
                        }

                    }
                });
                break;
            case 7:
                //去回复邮件界面，回复界面与新邮件在同一个界面，依靠第三个参数来判断是该回复还是 完全撰写新邮件
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MailWriteFragment mailWriteFragment = new MailWriteFragment();
                        Object[] params = (Object[]) data;
                        if (params != null) {
                            if (params.length == 3) {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable(Config.FORWARD_PARAM1, (UserAcount) params[0]);
                                bundle.putSerializable(Config.FORWARD_PARAM2, (FaceMailMessage) params[1]);
                                bundle.putSerializable(Config.FORWARD_PARAM3, (String) params[2]);
                                bundle.putSerializable(Config.FRAGMENTFROM, Config.FROM3_REPLY);
                                mailWriteFragment.setArguments(bundle);
                                onclick(mailWriteFragment, 0);
                            }
                        }
                    }
                });

                break;
            case 8:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (data != null) {
                            MailDetailFragment mailDetailFragment = new MailDetailFragment();
                            BaseBean[] params = (BaseBean[]) data;
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(Config.FORWARD_PARAM1, (UserAcount) params[0]);
                            bundle.putSerializable(Config.FORWARD_PARAM2, (FaceMailMessage) params[1]);
                            mailDetailFragment.setArguments(bundle);
                            onclick(mailDetailFragment, 0);
                        } else {
                            Toast.makeText(mContext, R.string.mail_message_detail_error, 0).show();
                            SystemUtil.log("在进入邮件详情界面时发生异常");
                        }

                    }
                });
                break;

            default:
                break;
        }

    }


}
