package com.greencross.medigene.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.greencross.medigene.R;
import com.greencross.medigene.base.BaseFragment;
import com.greencross.medigene.base.CommonActionBar;
import com.greencross.medigene.base.IBaseFragment;
import com.greencross.medigene.component.CDialog;
import com.greencross.medigene.login.LoginFragment;
import com.greencross.medigene.util.SharedPref;

/**
 * Created by insystemscompany on 2017. 2. 28..
 */

public class SettingFragment extends BaseFragment implements IBaseFragment, NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerMenu;
    String appVersion;
    private TextView setting_row01;
    private TextView setting_row02;
    private TextView setting_row03;
    private TextView setting_row04;
    private CheckBox autologincheckbox;

    public static Fragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_setting, container, false);
        return view;
    }


    @Override
    public void loadActionbar(CommonActionBar actionBar) {
        super.loadActionbar(actionBar);
        actionBar.setActionBarTitle(getString(R.string.text_setting));
        actionBar.setActionBackBtnVisible(View.INVISIBLE);
        actionBar.setActionBarMenuBtn(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 좌측메뉴 펼치고 닫기
                if (mDrawerMenu.isDrawerOpen(GravityCompat.START)) {
                    mDrawerMenu.closeDrawer(GravityCompat.START);
                } else {
                    mDrawerMenu.openDrawer(GravityCompat.START);
                }
            }
        });
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setting_row01       = (TextView) view.findViewById(R.id.setting_row01);
        setting_row02       = (TextView) view.findViewById(R.id.setting_row02);
        setting_row03       = (TextView) view.findViewById(R.id.setting_row03);
        setting_row04       = (TextView) view.findViewById(R.id.setting_row04);
        autologincheckbox   = (CheckBox) view.findViewById(R.id.setting_active_autologin_checkbox);

        view.findViewById(R.id.setting_row01).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.setting_row02).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.setting_row03).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.setting_row04).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.setting_active_autologin_checkbox).setOnClickListener(mOnClickListener);


        // 자동 로그인 체크
        boolean isAutoLogin = SharedPref.getInstance().getPreferences(SharedPref.IS_AUTO_LOGIN, false);
        autologincheckbox.setChecked(isAutoLogin);


        mDrawerMenu = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int vId = v.getId();
            if (R.id.setting_row01 == vId) {
                movePage(SettingMemberEditFragment.newInstance());

            } else if (R.id.setting_row02 == vId) {
                movePage(SettingAgreeFragment.newInstance());

            } else if (R.id.setting_row03 == vId) {

                movePage(SettingAgreeSensitiveFragment.newInstance());
            } else if (R.id.setting_row04 == vId) {
                CDialog.showDlg(getContext(), getString(R.string.text_alert_mesage_logout), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPref.getInstance().savePreferences(SharedPref.IS_AUTO_LOGIN, false);
                        movePage(LoginFragment.newInstance());
                    }
                }, null);

            } else if (R.id.setting_active_autologin_checkbox == vId) {
                if (autologincheckbox.isChecked()) {
                    SharedPref.getInstance().savePreferences(SharedPref.IS_AUTO_LOGIN, true);
                } else {
                    SharedPref.getInstance().savePreferences(SharedPref.IS_AUTO_LOGIN, false);
                }
            }
        }
    };


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        super.navigationItemSelected(item);

        mDrawerMenu.closeDrawer(GravityCompat.START);
        return true;
    }
}
