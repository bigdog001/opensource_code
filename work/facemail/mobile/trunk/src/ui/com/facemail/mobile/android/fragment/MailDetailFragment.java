package com.facemail.mobile.android.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.*;
import com.facemail.mobile.android.R;
import com.facemail.mobile.android.application.FaceMailApplication;
import com.facemail.mobile.android.db.bean.BaseBean;
import com.facemail.mobile.android.db.bean.FaceAddress;
import com.facemail.mobile.android.db.bean.FaceMailMessage;
import com.facemail.mobile.android.db.bean.UserAcount;
import com.facemail.mobile.android.db.conf.Config;
import com.facemail.mobile.android.db.dao.FaceAddressDao;
import com.facemail.mobile.android.db.dao.FaceMailMessageDao;
import com.facemail.mobile.android.util.SystemUtil;
import com.facemail.mobile.android.watchdog.watchDogService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: bigdog
 * Date: 5/9/13
 * Time: 1:50 PM
 */
public class MailDetailFragment extends BaseFragment {
    private View mMailDetailView;
    //    private boolean editeAbleModule;
    private FaceMailMessage faceMailMessage;
    private UserAcount userAcount;
    private PopupWindow popupWindow;
    private View popupView;
    private ListView lv_group;
    private List<String> reply_items;
    private TextView mail_message_from;
    private TextView mail_message_to;
    private TextView mail_message_cc;
    private LinearLayout mail_message_cc_layout;
    private WebView mail_message_content_detail;
    private static int minimumFontSize = 8;
    private static int minimumLogicalFontSize = 8;
    private static int defaultFontSize = 16;
    private static int defaultFixedFontSize = 13;
    private StringBuilder buf;
    private List<FaceAddress> faceAddresses_from;
    private List<FaceAddress> faceAddresses_to;
    private List<FaceAddress> faceAddresses_cc;
    private List<FaceAddress> faceAddresses_reply;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mMailDetailView = inflater.inflate(R.layout.mail_message_detail, null);
        initView(mMailDetailView);
        return mMailDetailView;
    }

    private void initView(View mMailDetailView) {
        mail_message_from = (TextView) mMailDetailView.findViewById(R.id.mail_message_from);
        mail_message_to = (TextView) mMailDetailView.findViewById(R.id.mail_message_to);
        mail_message_cc = (TextView) mMailDetailView.findViewById(R.id.mail_message_cc);
        mail_message_cc_layout = (LinearLayout) mMailDetailView.findViewById(R.id.mail_message_cc_layout);
        mail_message_content_detail = (WebView) mMailDetailView.findViewById(R.id.mail_message_content_detail);
        setWebView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        setmCanReturn(true);
        Bundle info = getArguments();
        faceMailMessage = (FaceMailMessage) info.getSerializable(Config.FORWARD_PARAM2);
        userAcount = (UserAcount) info.getSerializable(Config.FORWARD_PARAM1);
        buf = new StringBuilder(faceMailMessage.getMail_content().length());
        for (char c : faceMailMessage.getMail_content().toCharArray()) {
            switch (c) {
                case '#':
                    buf.append("#");
                    break;
                case '%':
                    buf.append("%");
                    break;
                case '\'':
                    buf.append("'");
                    break;
                case '?':
                    buf.append("?");
                    break;
                default:
                    buf.append(c);
                    break;
            }
        }
        initData();
        super.onActivityCreated(savedInstanceState);
    }

    private void initData() {
        mail_message_content_detail.loadDataWithBaseURL("", buf.toString(), "text/html", "UTF-8", "");
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Map<String, List<FaceAddress>> faceAddresses = FaceAddressDao.getFaceAddressDao(FaceMailApplication.getFaceMailContext().getApplicationContext()).getFaceAddress(faceMailMessage);

                FaceMailApplication.getFaceMailContext().getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        faceAddresses_from = faceAddresses.get(Config.ADRESS_TYPE.FROM);
                        faceAddresses_to = faceAddresses.get(Config.ADRESS_TYPE.TO);
                        faceAddresses_cc = faceAddresses.get(Config.ADRESS_TYPE.CC);
                        faceAddresses_reply = faceAddresses.get(Config.ADRESS_TYPE.REPLY_TO);
                        int max_amount = 0;
                        if (faceAddresses_from != null && faceAddresses_from.size() > 0) {
                            String faceAddresses_from_result = "";
//                            SystemUtil.log("faceAddresses_from size:"+faceAddresses_from.size());
                            for (FaceAddress faceAddress_from : faceAddresses_from) {
                                faceAddresses_from_result = ("".equals(faceAddresses_from_result) ? "" : faceAddresses_from_result + ",") + ("".equals(faceAddress_from.getEmailDesc()) || faceAddress_from.getEmailDesc() == null ? faceAddress_from.getEmailAddress() : faceAddress_from.getEmailDesc());
                                max_amount++;
                                if (max_amount > 5) {
                                    break;
                                }
                            }
                            max_amount = 0;

                            mail_message_from.setText(faceAddresses_from_result);
                            mail_message_from.setVisibility(View.VISIBLE);
                        }
                        if (faceAddresses_to != null && faceAddresses_to.size() > 0) {
                            String faceAddresses_to_result = "";
//                            SystemUtil.log("faceAddresses_to size:"+faceAddresses_to.size());
                            for (FaceAddress faceAddress_to : faceAddresses_to) {
                                faceAddresses_to_result = ("".equals(faceAddresses_to_result) ? "" : faceAddresses_to_result + ",") + ("".equals(faceAddress_to.getEmailDesc()) || faceAddress_to.getEmailDesc() == null ? faceAddress_to.getEmailAddress() : faceAddress_to.getEmailDesc());
                                max_amount++;
                                if (max_amount > 5) {
                                    break;
                                }
                            }
                            max_amount = 0;
                            mail_message_to.setText(faceAddresses_to_result);
                            mail_message_to.setVisibility(View.VISIBLE);
                        }
                        if (faceAddresses_cc != null && faceAddresses_cc.size() > 0) {
                            String faceAddresses_cc_result = "";
//                            SystemUtil.log("faceAddresses_cc size:"+faceAddresses_cc.size());
                            for (FaceAddress faceAddress_cc : faceAddresses_cc) {
                                faceAddresses_cc_result = ("".equals(faceAddresses_cc_result) ? "" : faceAddresses_cc_result + ",") + ("".equals(faceAddress_cc.getEmailDesc()) || faceAddress_cc.getEmailDesc() == null ? faceAddress_cc.getEmailAddress() : faceAddress_cc.getEmailDesc());
                                max_amount++;
                                if (max_amount > 5) {
                                    break;
                                }
                            }

                            mail_message_cc.setText(faceAddresses_cc_result);
                            mail_message_cc_layout.setVisibility(View.VISIBLE);
                        }

                    }
                });
            }
        }).start();


    }

    @Override
    public void doReturn() {

        mCenterViewListener.doProcess(4, userAcount);

    }

    @Override
    public void initTitleBar() {
        faceTitleBar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MailDetailFragment.this.doReturn();
            }
        });
        faceTitleBar.setMidText(faceMailMessage.getMail_title().length() > 13 ? faceMailMessage.getMail_title().substring(0, 13) + "..." : faceMailMessage.getMail_title());
        faceTitleBar.setRightText(mContext.getResources().getString(R.string.mail_message_detail_reply_button));
        faceTitleBar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //回复
                showWindow(v);
            }
        });

    }

    private void showWindow(View parent) {
        if (popupWindow == null) {
            popupView = View.inflate(mContext, R.layout.mail_reply_list, null);
            lv_group = (ListView) popupView.findViewById(R.id.lvGroup);
            // 加载数据
            reply_items = new ArrayList<String>();
            reply_items.add("回复");
            reply_items.add("回复全部");

            GroupAdapter groupAdapter = new GroupAdapter(mContext, reply_items);
            lv_group.setAdapter(groupAdapter);
            // 创建一个PopuWidow对象
            popupWindow = new PopupWindow(popupView, 100, 80);
        }
        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        // 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
        int xPos = windowManager.getDefaultDisplay().getWidth() / 2
                - popupWindow.getWidth() / 2;

        /*SystemUtil.log("coder:windowManager.getDefaultDisplay().getWidth()/2:"
                + windowManager.getDefaultDisplay().getWidth() / 2);
        SystemUtil.log("coder:popupWindow.getWidth()/2:" + popupWindow.getWidth() / 2);
        SystemUtil.log("coder:xPos:" + xPos);*/

        popupWindow.showAsDropDown(parent, xPos, 0);

        lv_group.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long id) {

                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                switch (position) {
                    case 0:
                        /*final Animation rotateAnimation = new
                                RotateAnimation(0f,180f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                        rotateAnimation.setDuration(1500);
                        mMailDetailView.setAnimation(rotateAnimation);
                        rotateAnimation.startNow();*/
                        //回复
                        reply(1);
                        break;
                    case 1:
                        //回复全部
                        reply(2);
                        break;
                    default:
                        break;
                }

            }
        });
    }

    private void reply(int flag) {
        if (flag == 1) {
            //回复
            Object[] baseBeans = new Object[3];
            baseBeans[0] = userAcount;
            baseBeans[1] = faceMailMessage;
            baseBeans[2] = Config.MAIL_RPL_SINGLE;
            mCenterViewListener.doProcess(7, baseBeans);
        }
        if (flag == 2) {
            //回复全部
            Object[] baseBeans = new Object[3];
            baseBeans[0] = userAcount;
            baseBeans[1] = faceMailMessage;
            baseBeans[2] = Config.MAIL_RPL_ALL;
            mCenterViewListener.doProcess(7, baseBeans);
        }

    }

    public class GroupAdapter extends BaseAdapter {
        private Context context;

        private List<String> list;

        public GroupAdapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {

            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.group_item_view, null);
                holder = new ViewHolder();
                convertView.setTag(holder);
                holder.groupItem = (TextView) convertView.findViewById(R.id.groupItem);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.groupItem.setText(list.get(position));
            return convertView;
        }
    }

    static class ViewHolder {
        TextView groupItem;
    }

    public void setWebView() {

        WebSettings settings = mail_message_content_detail.getSettings();
        mail_message_content_detail.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        settings.setLoadsImagesAutomatically(true);
        settings.setPluginsEnabled(true);
        settings.setUseWideViewPort(false);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(false);
        settings.setCacheMode(WebSettings.LOAD_NORMAL);
//        settings.setLightTouchEnabled(true);
        settings.setNavDump(true);
        settings.setMinimumFontSize(minimumFontSize);
        settings.setMinimumLogicalFontSize(minimumLogicalFontSize);
        settings.setDefaultFontSize(defaultFontSize);
        settings.setDefaultFixedFontSize(defaultFixedFontSize);
        settings.setTextSize(WebSettings.TextSize.NORMAL);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mail_message_content_detail.setHorizontalScrollBarEnabled(false);

        mail_message_content_detail.setWebViewClient(new WebViewClient() {
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel:")) {
                    Uri uri = Uri.parse(url);
                    Intent it = new Intent(Intent.ACTION_DIAL, uri);
                    startActivity(it);
                } else if (url.startsWith("http:")) {
                    Uri uri = Uri.parse(url);
                    Intent it = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(it);
                } else {

                }
                return true;
            }
        });
        mail_message_content_detail.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
                if (icon != null) {
                }
            }

            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

            }
        });
    }

    @Override
    public void onLowMemory() {
        mail_message_content_detail.clearCache(true);
        super.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mail_message_content_detail.removeAllViews();
        mail_message_content_detail.destroy();
    }
}
