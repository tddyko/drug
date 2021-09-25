package com.greencross.medigene.serviceinfo;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.Button;

import com.greencross.medigene.R;
import com.greencross.medigene.affiliatedhospitals.AffiliatedHospitalsFragment;
import com.greencross.medigene.affiliatedhospitals.AppointmentHospitalsList;
import com.greencross.medigene.base.BaseFragment;
import com.greencross.medigene.base.CommonActionBar;
import com.greencross.medigene.base.IBaseFragment;
import com.greencross.medigene.component.CDialog;

/**
 * Created by insystemscompany on 2017. 2. 28..
 */

public class ServiceInfoFragment extends BaseFragment implements IBaseFragment, NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerMenu;

    String appVersion;
    private Button btnReshospital;
    private Button btnHealthhospital;
    private Button btnServiceRequest;

    public static Fragment newInstance() {
        ServiceInfoFragment fragment = new ServiceInfoFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_serviceinfo, container, false);
        return view;
    }


    @Override
    public void loadActionbar(CommonActionBar actionBar) {
        super.loadActionbar(actionBar);
        actionBar.setActionBarTitle(getString(R.string.text_serviceinfo));
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
        btnReshospital = (Button) view.findViewById(R.id.btn_reservation_hospital);
        btnHealthhospital = (Button) view.findViewById(R.id.btn_health_hospital);
        btnServiceRequest = (Button) view.findViewById(R.id.btn_service_request);

        view.findViewById(R.id.btn_reservation_hospital).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.btn_health_hospital).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.btn_service_request).setOnClickListener(mOnClickListener);


        mDrawerMenu = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int vId = v.getId();
            if (R.id.btn_reservation_hospital == vId) {
                movePage(AppointmentHospitalsList.newInstance());

            } else if (R.id.btn_health_hospital == vId) {
                movePage(AffiliatedHospitalsFragment.newInstance());
            }else if (R.id.btn_service_request == vId) {
                CDialog.showDlg(getContext(), getString(R.string.text_alert_message_service_content), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tel = "tel:0220409100";
                        startActivity(new Intent("android.intent.action.DIAL", Uri.parse(tel)));
                    }
                }, null);
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

