package com.facemail.mobile.android.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.facemail.mobile.android.R;
import com.facemail.mobile.android.db.conf.Config;
import com.facemail.mobile.android.util.SystemUtil;
import com.facemail.mobile.android.view.SlidingMenu;

public class LeftFragment extends BaseFragment implements View.OnClickListener {

    private TextView tv_myemail_left_menu;    //收件箱
    private TextView tv_myemail_send_left_menu; //已发件箱
    private TextView tv_email_important_star_left_menu;// 重要邮件,标星邮件
    private TextView tv_email_recyclebin_left_menu;    //垃圾箱
    private TextView tv_email_setting_left_menu; //设置界面
    private TextView v1_0_0_left_menu_addaccount_tv;   //增加邮件帐号
    private View view;
    private LeftMenuOnClickListener leftMenuOnClickListener;
    private int currentSelected;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.left, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        if (view != null) {
            tv_myemail_left_menu = (TextView) view.findViewById(R.id.tv_myemail_left_menu);
            tv_myemail_send_left_menu = (TextView) view.findViewById(R.id.tv_myemail_send_left_menu);
            tv_email_important_star_left_menu = (TextView) view.findViewById(R.id.tv_email_important_star_left_menu);
            tv_email_recyclebin_left_menu = (TextView) view.findViewById(R.id.tv_email_recyclebin_left_menu);
            tv_email_setting_left_menu = (TextView) view.findViewById(R.id.tv_email_setting_left_menu);
            v1_0_0_left_menu_addaccount_tv = (TextView) view.findViewById(R.id.v1_0_0_left_menu_addaccount_tv);
            tv_myemail_left_menu.setOnClickListener(this);
            tv_myemail_send_left_menu.setOnClickListener(this);
            tv_email_important_star_left_menu.setOnClickListener(this);
            tv_email_recyclebin_left_menu.setOnClickListener(this);
            tv_email_setting_left_menu.setOnClickListener(this);
            v1_0_0_left_menu_addaccount_tv.setOnClickListener(this);
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setmCanReturn(false);
        mContext = view.getContext();
    }

    @Override
    public void doReturn() {

    }

    @Override
    public void initTitleBar() {

    }

    @Override
    public void onPause() {
        super.onPause();
        SystemUtil.log("facemail020,guy onpause....");
    }

    @Override
    public void onResume() {
        super.onResume();
        SystemUtil.log("facemail021,guy onResume....");
    }

    public void setSelectedGuy(int guyid) {
        currentSelected = guyid;
        TextView tc_temp;
        for (int menu : Config.leftMenu) {
            if (menu == guyid) {
                tc_temp = (TextView) view.findViewById(menu);
                tc_temp.setTextColor(Color.WHITE);
                tc_temp.setBackgroundColor(view.getContext().getResources().getColor(R.color.midle_title_blue));
            } else {
                tc_temp = (TextView) view.findViewById(menu);
                tc_temp.setTextColor(Color.BLACK);
                tc_temp.setBackgroundColor(view.getContext().getResources().getColor(R.color.bg_color_deep));
            }
        }
    }

    @Override
    public void onClick(View v) {
        int current = v.getId();
        switch (v.getId()) {
            case R.id.tv_write_email_left_menu:
                if (SlidingMenu.getLeftMenuClickable() == 1 && !(currentSelected == R.id.tv_write_email_left_menu)) {
                Toast.makeText(mContext, "write new email...", 0).show();
                    setSelectedGuy(R.id.tv_write_email_left_menu);
//                    leftMenuOnClickListener.onclick(new AccountsFragment(),0);
                } else SystemUtil.log("facemail022,重复点击统一个按钮tv_write_email_left_menu" + R.id.tv_write_email_left_menu);
                break;
            case R.id.tv_myemail_left_menu:
                if (SlidingMenu.getLeftMenuClickable() == 1 && !(currentSelected == R.id.tv_myemail_left_menu)) {
//                Toast.makeText(mContext,"1",0).show();
                    setSelectedGuy(R.id.tv_myemail_left_menu);
                    leftMenuOnClickListener.onclick(new AccountsFragment(), 0);
                } else SystemUtil.log("facemail022,重复点击统一个按钮tv_myemail_left_menu" + R.id.tv_myemail_left_menu);
                break;
            case R.id.tv_myemail_send_left_menu:
                if (SlidingMenu.getLeftMenuClickable() == 1 && !(currentSelected == R.id.tv_myemail_send_left_menu)) {
//                Toast.makeText(mContext,"2",0).show();
                    setSelectedGuy(R.id.tv_myemail_send_left_menu);
                    leftMenuOnClickListener.onclick(new MailSendBox(), 0);
                } else
                    SystemUtil.log("facemail023,重复点击统一个按钮tv_myemail_send_left_menu" + R.id.tv_myemail_send_left_menu);
                break;
            case R.id.tv_email_draft_left_menu:

                break;
            case R.id.tv_email_important_star_left_menu:

                break;
            case R.id.tv_email_recyclebin_left_menu:

                break;
            case R.id.tv_email_setting_left_menu:

                break;
            case R.id.v1_0_0_left_menu_addaccount_tv:
                leftMenuOnClickListener.onclick(null, 1);
                break;

            default:
                break;
        }
    }

    public interface LeftMenuOnClickListener {
        public void onclick(BaseFragment replacedFragment, int flag);
    }

    public void setLeftMenuOnClickListener(LeftMenuOnClickListener leftMenuOnClickListener) {
        this.leftMenuOnClickListener = leftMenuOnClickListener;
    }
}
