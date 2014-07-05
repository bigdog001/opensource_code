package com.facemail.mobile.android.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.facemail.mobile.android.R;
import com.facemail.mobile.android.application.FaceMailApplication;
import com.facemail.mobile.android.db.bean.BaseBean;
import com.facemail.mobile.android.db.bean.FaceAddress;
import com.facemail.mobile.android.db.bean.FaceMailMessage;
import com.facemail.mobile.android.db.bean.UserAcount;
import com.facemail.mobile.android.db.conf.Config;
import com.facemail.mobile.android.db.dao.FaceAddressDao;
import com.facemail.mobile.android.util.Md5;
import com.facemail.mobile.android.util.SystemUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 6/2/13
 * Time: 5:33 AM
 */
public class MailWriteFragment extends BaseFragment {
    private FaceMailMessage faceMailMessage;//在邮件详情界面传递过来的邮件消息体对象
    private FaceMailMessage faceMailMessage_send_or_save;//用以接收保存草稿或被发送的邮件对象

    private UserAcount userAcount;    //在邮件详情界面传递过来的帐号身份对象
    private UserAcount mCurrentSenderAuthor; //用户选择的用来发送此邮件的帐号身份，默认和userccount对象值一样，除非用户新选择一个新的帐号
    private String reply_action;//标记是回复单个的发件人还是回复所以的邮件收件人标记位 ，如果是回复全部，那么 将to和cc全加入收件人列表中
    private String fragmentFrom;// 1:COMPOSE 直接从左边的fragment点击而来的新邮件，此时faceMailMessage应该为空，
                                // 2：DRAFTS 点击草稿箱中保存的邮件而来 ，此时有faceMailMessage的值，直接可以从中读出收件人，cc ，bcc等信息
                                // 3：REPLY 点击邮件回复而来
                                // 4:如果此值为空，则默认前往新邮件撰写机制即 COMPOSE
    //    private MenuItem menuItem;
    private View mMailWriteView;
    //ui component===========================
    private Button mail_message_select_author;
    private GridView mail_message_reply_to;
    private GridView mail_message_reply_cc;
    private GridView mail_message_reply_bcc;
    private TextView author_from;
    private EditText mail_message_reply_title;
    private EditText mail_message_content_reply_edittext;
    private LinearLayout mail_message_cc_layout;
    private LinearLayout mail_message_bcc_layout;

    //===========================ui component

    // reply=======================================
    private List<FaceAddress> faceAddresses_from; //取出这里面的值，置入faceAddresses_to
    private List<FaceAddress> faceAddresses_to;   //reply
    private List<FaceAddress> faceAddresses_cc;   //reply
    private List<FaceAddress> faceAddresses_bcc;  //reply
    private List<FaceAddress> faceAddresses_reply;
    // =======================================reply

    /**
     * 只有在用户点击保存草稿或发送邮件时才存储邮件体（包含所有的收件人对象）
     * 当已经保存过一次以后才在添加以及删除任何一个收件人时才
     */

    private Dialog dialog;

    @Override
    public void doReturn() {
        BaseBean[] params = new BaseBean[2];
        params[0] = userAcount;
        params[1] = faceMailMessage;
        mCenterViewListener.doProcess(8, params);
    }

    @Override
    public void initTitleBar() {
        faceTitleBar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MailWriteFragment.this.doReturn();
            }
        });
        faceTitleBar.setMidText(faceMailMessage.getMail_title().length() > 13 ? faceMailMessage.getMail_title().substring(0, 13) + "..." : faceMailMessage.getMail_title());
        faceTitleBar.removeRightButton();
/*

        faceTitleBar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOperatorMenus();//添加收件人,抄送，密送，标题
            }
        });
*/

    }
/*

    private void showOperatorMenus() {
        Toast.makeText(mContext, "--->", 0).show();

    }
*/


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        setmCanReturn(true);
        Bundle info = getArguments();
        if (info.getSerializable(Config.FORWARD_PARAM2) != null) {
            faceMailMessage = (FaceMailMessage) info.getSerializable(Config.FORWARD_PARAM2);
        }
        if (info.getSerializable(Config.FORWARD_PARAM1) != null) {
            userAcount = (UserAcount) info.getSerializable(Config.FORWARD_PARAM1);
        }
        if (info.getSerializable(Config.FORWARD_PARAM3) != null) {
            reply_action = (String) info.getSerializable(Config.FORWARD_PARAM3);
        }
        if (info.getSerializable(Config.FRAGMENTFROM) !=null ) {
            fragmentFrom = (String) info.getSerializable(Config.FRAGMENTFROM);
            fragmentFrom = fragmentFrom.trim();
        }

        SystemUtil.log("facemail->" + faceMailMessage + ",userAcount-->" + userAcount + ",reply_action->" + reply_action);
        if (userAcount == null) {
            //此时说明未携带身份数据，则直接从数据库中加载身份然后选择第一个帐号身份显示在发件人的textView上
            FaceMailApplication.getFaceMailContext().getUserAcounts(new AccountsFragment.UserLoadListener() {
                @Override
                public void success(List<UserAcount> useracount) {
                    if (useracount != null) {
                        mCurrentSenderAuthor = useracount.get(0);  //承载新身份
                        FaceMailApplication.getFaceMailContext().getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                author_from.setText(mCurrentSenderAuthor.getUser_name());
                            }
                        });


                    } else {
                        //无身份，退出程序
                        FaceMailApplication.getFaceMailContext().getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, R.string.mail_message_reply_selectAuthor_none, 0).show();
                                getActivity().finish();
                            }
                        });

                    }
                }
            });


        } else {
            mCurrentSenderAuthor = userAcount;
            author_from.setText(userAcount.getUser_name());
        }

        if (faceMailMessage == null) {
            //此时无邮件体，说明是写一封新邮件 ，不做填充邮件内容的操作，即fragmentFrom的值为:COMPOSE
            //如果是被分享过来的文本数据或图片数据，那么在完成发送或保存后直接退出
            FaceMailApplication.getFaceMailContext().getHandler().post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mContext, "new mail", 0).show();
                }
            });
        } else {
            //此时有邮件体，说明是回复一封新邮件 ，也有可能是从草稿想中来的需要重新编辑的数据，需要做填充邮件内容的操作
            //来源有2种情况，1：从草稿箱中来 2：从邮件详情中来，回复邮件
            if (!"".equals(fragmentFrom)&&fragmentFrom !=null) {
                if (Config.FROM2_DRAFTS.equals(fragmentFrom)) {
                    // 从草稿箱中来


                }else if(Config.FROM3_REPLY.equals(fragmentFrom)){
                    // 从邮件详情界面而来，回复
                    final Map<String, List<FaceAddress>> faceAddresses = FaceAddressDao.getFaceAddressDao(FaceMailApplication.getFaceMailContext().getApplicationContext()).getFaceAddress(faceMailMessage);
                    FaceMailApplication.getFaceMailContext().getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            faceAddresses_from = faceAddresses.get(Config.ADRESS_TYPE.FROM);
                            faceAddresses_to = faceAddresses.get(Config.ADRESS_TYPE.TO);
                            faceAddresses_cc = faceAddresses.get(Config.ADRESS_TYPE.CC);
                            faceAddresses_reply = faceAddresses.get(Config.ADRESS_TYPE.REPLY_TO);
                            SystemUtil.log("faceAddresses_from:"+faceAddresses_from.get(0).getEmailAddress()+",faceAddresses_reply:"+faceAddresses_reply.get(0).getEmailAddress());

                            if (reply_action == null) {
                                //不初始化gridview
                            } else {

                                //初始化标题和正文
                                if (faceMailMessage != null) {
                                    if ("".equals(faceMailMessage.getMail_title()) || faceMailMessage.getMail_title() == null) {
                                    } else {
                                        mail_message_reply_title.setText(Config.MAIL_TITLE_PREFIX + faceMailMessage.getMail_title());
                                    }
                                    if (!"".equals(faceMailMessage.getMail_content()) && faceMailMessage.getMail_content() != null) {
                                        mail_message_content_reply_edittext.setText(Config.MAIL_CONTENT_PREFIX + faceMailMessage.getMail_content());
                                    }
                                }

                                if (Config.MAIL_RPL_SINGLE.equals(reply_action)) {
                                    if (faceAddresses_from != null && faceAddresses_from.size() > 0) {
                                        //有收件人，则将值初始化到界面，则将其值值入到此邮件的收件人
                                        faceAddresses_to = faceAddresses_from;//收件人只有一位，即此邮件的发件人
                                        //回复时的收件人可能和当时的发件人不一致

                                        if (faceAddresses_reply != null) {
                                            if (faceAddresses_reply.get(0) != null&&faceAddresses_to.get(0) != null) {
                                                if (faceAddresses_reply.get(0).getEmailAddress().equals(faceAddresses_to.get(0).getEmailAddress())) {
                                                }else {
                                                    faceAddresses_to = faceAddresses_reply;
                                                }
                                            }
                                        }
                                        mail_message_reply_to.setAdapter(new GridViewAdaptor(mContext, faceAddresses_to, mail_message_reply_to));
                                    }

                                }
                                if (Config.MAIL_RPL_ALL.equals(reply_action)) {
                                    //   如果reply_action为回复全部，则合并  faceAddresses_to和faceAddresses_cc以及from的值，将合并后的地址值塞入新的收件人地址列表
                                    if (faceAddresses_from != null) {
                                        for (FaceAddress fa : faceAddresses_from) {
                                            faceAddresses_to.add(fa);
                                        }
                                    }
                                    if (faceAddresses_cc != null) {
                                        for (FaceAddress fa : faceAddresses_cc) {
                                            faceAddresses_to.add(fa);
                                        }
                                    }
                                    //合并数据后移除cc中的元素
                                    faceAddresses_cc.clear();
                                    //将我本人从收件人列表中删除，回复全部邮件时不可能给自己也回复
                                    int removeFlag = 0;
                                    for (FaceAddress fa : faceAddresses_to) {
                                        if (fa.getEmailAddress().equals(userAcount.getUser_name())) {
                                            break;
                                        }
                                        removeFlag++;
                                    }
                                    faceAddresses_to.remove(removeFlag);

                                    //初始化ccgridview值
                                    mail_message_reply_to.setAdapter(new GridViewAdaptor(mContext, faceAddresses_to, mail_message_reply_to));
                                }
                            }
                        }
                    });
                }

            }else {
                //此时默认作为新邮件

            }


        }

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.mail_write_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        AlertDialog.Builder builder = null;

//        final EditText userenter = new EditText(getActivity());
        int itemid = item.getItemId();
        switch (itemid) {
            case R.id.mail_write_add_to:
                //弹出添加收件人的自定义对话框
                builder = new android.app.AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.mail_message_reply_to_enter_text_title);
                builder.setCancelable(false);
                View viewMainAddTo = View.inflate(mContext, R.layout.write_message_add_address, null);
                final EditText write_message_add_address_text_to = (EditText) viewMainAddTo.findViewById(R.id.write_message_add_address_text);
//                userenter.setHint(R.string.mail_message_reply_to_enter_text_hint);
                builder.setView(viewMainAddTo);
                Button add_ok_write_message = (Button) viewMainAddTo.findViewById(R.id.add_ok_write_message);
                Button add_cancel_write_message = (Button) viewMainAddTo.findViewById(R.id.add_cancel_write_message);
                add_ok_write_message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userenter_text = write_message_add_address_text_to.getText().toString();
                        if (!"".equals(userenter_text) && userenter_text != null) {
                            userenter_text = userenter_text.trim();
                            if (SystemUtil.isEmail(userenter_text)) {
                                faceAddresses_to.add(new FaceAddress(userenter_text));
                                if (mail_message_reply_to.getAdapter() != null) {
                                    final GridViewAdaptor gridViewAdaptor = (GridViewAdaptor) mail_message_reply_to.getAdapter();

                                    FaceMailApplication.getFaceMailContext().getHandler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            gridViewAdaptor.notifyDataSetChanged();
                                            dialog.dismiss();
                                        }
                                    });

                                } else {
                                    FaceMailApplication.getFaceMailContext().getHandler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            mail_message_reply_to.setAdapter(new GridViewAdaptor(mContext, faceAddresses_to, mail_message_reply_to));
                                            dialog.dismiss();
                                        }
                                    });

                                }

                            } else {
                                Toast.makeText(mContext, R.string.mail_message_reply_email_address_invalide, 0).show();
                            }

                        } else {
                            //提示输入为空。。。。
                            Toast.makeText(mContext, R.string.mail_message_reply_email_address_empty_tip, 0).show();
                        }
                    }
                });
                add_cancel_write_message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                break;
            case R.id.mail_write_add_cc:
                builder = new android.app.AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.mail_message_reply_cc_enter_text_title);
                builder.setCancelable(false);
                View viewMainAddCc = View.inflate(mContext, R.layout.write_message_add_address, null);
                final EditText write_message_add_address_text_cc = (EditText) viewMainAddCc.findViewById(R.id.write_message_add_address_text);
                builder.setView(viewMainAddCc);

                Button add_ok_write_message_cc_ok = (Button) viewMainAddCc.findViewById(R.id.add_ok_write_message);
                Button add_cancel_write_message_cc_cancel = (Button) viewMainAddCc.findViewById(R.id.add_cancel_write_message);


                add_ok_write_message_cc_ok.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        String userenter_email_text = write_message_add_address_text_cc.getText().toString();
                        if (!"".equals(userenter_email_text) && userenter_email_text != null) {
                            userenter_email_text = userenter_email_text.trim();
                            if (SystemUtil.isEmail(userenter_email_text)) {
                                //显示cclayout
                                if (mail_message_cc_layout.getVisibility() == View.GONE) {
                                    mail_message_cc_layout.setVisibility(View.VISIBLE);
                                }

                                faceAddresses_cc.add(new FaceAddress(userenter_email_text));
                                if (mail_message_reply_cc.getAdapter() != null) {
                                    final GridViewAdaptor gridViewAdaptor_cc = (GridViewAdaptor) mail_message_reply_cc.getAdapter();

                                    FaceMailApplication.getFaceMailContext().getHandler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            gridViewAdaptor_cc.notifyDataSetChanged();
                                            dialog.dismiss();
                                        }
                                    });
                                    dialog.dismiss();

                                } else {
                                    FaceMailApplication.getFaceMailContext().getHandler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            mail_message_reply_cc.setAdapter(new GridViewAdaptor(mContext, faceAddresses_cc, mail_message_reply_cc));
                                            dialog.dismiss();
                                        }
                                    });

                                }
                            } else {
                                Toast.makeText(mContext, R.string.mail_message_reply_email_address_invalide, 0).show();
                            }
                        } else {
                            //提示输入为空。。。。
                            Toast.makeText(mContext, R.string.mail_message_reply_email_address_empty_tip, 0).show();
                        }
                    }
                });
                add_cancel_write_message_cc_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                break;
            case R.id.mail_write_add_bcc:
                builder = new android.app.AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.mail_message_reply_bcc_enter_text_title);
                builder.setCancelable(false);
                View viewMainAddBcc = View.inflate(mContext, R.layout.write_message_add_address, null);
                final EditText write_message_add_address_text_bcc = (EditText) viewMainAddBcc.findViewById(R.id.write_message_add_address_text);
                builder.setView(viewMainAddBcc);

                Button add_ok_write_message_bcc_ok = (Button) viewMainAddBcc.findViewById(R.id.add_ok_write_message);
                Button add_cancel_write_message_bcc_cancel = (Button) viewMainAddBcc.findViewById(R.id.add_cancel_write_message);
                add_ok_write_message_bcc_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userenter_email_text = write_message_add_address_text_bcc.getText().toString();
                        if (!"".equals(userenter_email_text) && userenter_email_text != null) {
                            userenter_email_text = userenter_email_text.trim();
                            if (SystemUtil.isEmail(userenter_email_text)) {
                                //显示cclayout
                                if (mail_message_bcc_layout.getVisibility() == View.GONE) {
                                    mail_message_bcc_layout.setVisibility(View.VISIBLE);
                                }
                                if (faceAddresses_bcc == null) {
                                    faceAddresses_bcc = new ArrayList<FaceAddress>();
                                }
                                faceAddresses_bcc.add(new FaceAddress(userenter_email_text));
                                if (mail_message_reply_bcc.getAdapter() != null) {
                                    final GridViewAdaptor gridViewAdaptor_bcc = (GridViewAdaptor) mail_message_reply_bcc.getAdapter();

                                    FaceMailApplication.getFaceMailContext().getHandler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            gridViewAdaptor_bcc.notifyDataSetChanged();
                                            dialog.dismiss();
                                        }
                                    });
                                    dialog.dismiss();

                                } else {
                                    FaceMailApplication.getFaceMailContext().getHandler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            mail_message_reply_bcc.setAdapter(new GridViewAdaptor(mContext, faceAddresses_bcc, mail_message_reply_bcc));
                                            dialog.dismiss();
                                        }
                                    });

                                }
                            } else {
                                Toast.makeText(mContext, R.string.mail_message_reply_email_address_invalide, 0).show();
                            }
                        } else {
                            //提示输入为空。。。。
                            Toast.makeText(mContext, R.string.mail_message_reply_email_address_empty_tip, 0).show();
                        }
                    }
                });

                add_cancel_write_message_bcc_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                break;
            case R.id.mail_write_send:
                Toast.makeText(mContext, item.getTitle(), 0).show();


                break;
            case R.id.mail_write_save_draft:
                Toast.makeText(mContext, item.getTitle(), 0).show();
                String mail_out_title = mail_message_reply_title.getText().toString();
                String mail_out_content = mail_message_content_reply_edittext.getText().toString();
                if (faceMailMessage_send_or_save == null) {
                    faceMailMessage_send_or_save = new FaceMailMessage();
                    faceMailMessage_send_or_save.setMessageNumber(Md5.toMD5(System.currentTimeMillis()+""));
                }
                    faceMailMessage_send_or_save.setMail_title(mail_out_title);
                    faceMailMessage_send_or_save.setMail_content(mail_out_content);




                break;
            default:
                break;
        }
        if (builder != null) {
            dialog = builder.create();
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mMailWriteView = inflater.inflate(R.layout.mail_message_reply, null);
        initView(mMailWriteView);
        return mMailWriteView;

    }

    private void initView(View mMailWriteView) {
        mail_message_content_reply_edittext = (EditText) mMailWriteView.findViewById(R.id.mail_message_content_reply_edittext);
        mail_message_reply_title = (EditText) mMailWriteView.findViewById(R.id.mail_message_reply_title);
        mail_message_cc_layout = (LinearLayout) mMailWriteView.findViewById(R.id.mail_message_cc_layout);
        mail_message_bcc_layout = (LinearLayout) mMailWriteView.findViewById(R.id.mail_message_bcc_layout);
        mail_message_reply_to = (GridView) mMailWriteView.findViewById(R.id.mail_message_reply_to);
        mail_message_reply_cc = (GridView) mMailWriteView.findViewById(R.id.mail_message_reply_cc);
        mail_message_reply_bcc = (GridView) mMailWriteView.findViewById(R.id.mail_message_reply_bcc);
        author_from = (TextView) mMailWriteView.findViewById(R.id.author_from);
        mail_message_select_author = (Button) mMailWriteView.findViewById(R.id.mail_message_select_author);
        mail_message_select_author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAuthor();
            }
        });
//        mail_message_reply_to
    }

    private void showAuthor() {
        //加载用户身份数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                FaceMailApplication.getFaceMailContext().getUserAcounts(new AccountsFragment.UserLoadListener() {
                    @Override
                    public void success(final List<UserAcount> userAcounts) {
                        //成功加载到以后身份数据
                        if (userAcounts != null && userAcounts.size() > 0) {
                            final CharSequence[] cs = new CharSequence[userAcounts.size()];
                            int i = 0;
                            int currentSelect = 0;
                            for (UserAcount ua_tmp : userAcounts) {
                                String accountName = ua_tmp.getUser_name();
                                if (accountName.equals(userAcount.getUser_name())) {

                                } else {
                                    currentSelect++;
                                }
                                cs[i++] = accountName.length() > 11 ? (accountName.substring(0, 11) + "...") : accountName;
                            }

                            FaceMailApplication.getFaceMailContext().getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    Dialog dialog;
                                    AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                                    //加载所有身份到一个集合中
                                    builder.setTitle(R.string.mail_message_reply_selectAuthor_text);
                                    builder.setSingleChoiceItems(cs, 1, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            author_from.setText(userAcounts.get(which).getUser_name());
                                        }
                                    });
                                    builder.setNegativeButton("cancle", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    dialog = builder.create();
                                    dialog.show();
                                }
                            });

                        } else {
                            Toast.makeText(mContext, R.string.mail_message_reply_selectAuthor_none, 0).show();

                        }
                    }
                });
            }
        }).start();


    }

    private class GridViewAdaptor extends BaseAdapter {
        private Context context;
        private List<FaceAddress> addrs;
        private GridView gridView;


        private class GridHolder {
            ImageView mViewImageIcion;
            TextView mEmailAdress;
        }


        private GridViewAdaptor(Context context, List<FaceAddress> addrs, GridView gridView) {
            this.context = context;
            this.addrs = addrs;
            this.gridView = gridView;
        }

        @Override
        public int getCount() {
            return addrs.size();  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Object getItem(int position) {
            return addrs.get(position);  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public long getItemId(int position) {
            return position;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            GridHolder gridHolder;

            if (convertView == null) {
                convertView = View.inflate(context, R.layout.mail_gridview, null);
                gridHolder = new GridHolder();
                gridHolder.mEmailAdress = (TextView) convertView.findViewById(R.id.item_textview_gridview);
                gridHolder.mViewImageIcion = (ImageView) convertView.findViewById(R.id.item_imageview_gridview);
                convertView.setTag(gridHolder);
            } else {
                gridHolder = (GridHolder) convertView.getTag();
            }

            String textShow = "";
            if ("".equals(addrs.get(position).getEmailDesc()) || addrs.get(position).getEmailDesc() == null) {
                if ("".equals(addrs.get(position).getEmailAddress()) || addrs.get(position).getEmailAddress() == null) {
                    SystemUtil.log("收件人地址例外");
                } else {
                    textShow = addrs.get(position).getEmailAddress().length() > 10 ? addrs.get(position).getEmailAddress().substring(0, 9) : addrs.get(position).getEmailAddress();
                }
            } else {
                textShow = addrs.get(position).getEmailDesc().length() > 10 ? addrs.get(position).getEmailDesc().substring(0, 9) : addrs.get(position).getEmailDesc();
            }

            gridHolder.mEmailAdress.setText(textShow);
            gridHolder.mEmailAdress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, addrs.get(position).getEmailAddress(), 0).show();
                }
            });
            gridHolder.mViewImageIcion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //删除此收件人，刷新gridiew界面
                    addrs.remove(position);
                    final GridViewAdaptor gridViewAdaptor = (GridViewAdaptor) gridView.getAdapter();
                    FaceMailApplication.getFaceMailContext().getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            gridViewAdaptor.notifyDataSetChanged();
                        }
                    });

                }
            });
            return convertView;
        }
    }
}
