package com.greencross.medigene.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.greencross.medigene.MainActivity;
import com.greencross.medigene.R;
import com.greencross.medigene.apply.CheckApplyMapFragment;
import com.greencross.medigene.base.BaseFragment;
import com.greencross.medigene.base.CommonActionBar;
import com.greencross.medigene.base.IBaseFragment;
import com.greencross.medigene.checkresult.CheckresultFragment;
import com.greencross.medigene.drug.DrugInfoHistoryListFragment;
import com.greencross.medigene.util.SharedPref;


/**
 * Created by MrsWin on 2017-02-16.
 */

public class MainFragment extends BaseFragment implements IBaseFragment, NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerMenu;

    private final String TAG = MainActivity.class.getSimpleName();

    private LinearLayout liDrugSearch;
    private LinearLayout liDrugResult;
    private LinearLayout liRequestBefore;
    private LinearLayout liRequestAfter;
    private LinearLayout liRequestTitle;
    private Button  btnDrugRequest;

    public static BaseFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nav_main_fragment, container, false);

        liDrugSearch = (LinearLayout) view.findViewById(R.id.li_drug_search);
        liDrugResult = (LinearLayout) view.findViewById(R.id.li_drug_result);
        liRequestBefore = (LinearLayout) view.findViewById(R.id.li_request_before);
        liRequestAfter = (LinearLayout) view.findViewById(R.id.li_request_after);
        liRequestTitle = (LinearLayout) view.findViewById(R.id.li_request_title);
        btnDrugRequest = (Button) view.findViewById(R.id.btn_drug_request);

        view.findViewById(R.id.li_drug_search).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.li_drug_result).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.btn_drug_request).setOnClickListener(mOnClickListener);

        boolean isDrugRq = SharedPref.getInstance().getPreferences(SharedPref.IS_DRUG_REQUEST, false);
        // 선청하지 않았다면
        if (!isDrugRq){
            liRequestBefore.setVisibility(View.VISIBLE);
            liRequestAfter.setVisibility(View.GONE);
            liRequestTitle.setVisibility(View.GONE);
        }else{
            liRequestBefore.setVisibility(View.GONE);
            liRequestAfter.setVisibility(View.VISIBLE);
            liRequestTitle.setVisibility(View.VISIBLE);
        }

        return view;
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int vId = v.getId();
            if (R.id.li_drug_search == vId) {
                movePage(DrugInfoHistoryListFragment.newInstance());
            }else if (R.id.li_drug_result == vId) {
                movePage(CheckresultFragment.newInstance());
            }else if (R.id.btn_drug_request == vId) {
                movePage(CheckApplyMapFragment.newInstance());
            }

        }
    };
    @Override
    public void loadActionbar(CommonActionBar actionBar) {
        super.loadActionbar(actionBar);
        actionBar.setActionBackBtnVisible(View.INVISIBLE);
        actionBar.setActionBarTitle(getString(R.string.text_main));
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

        mDrawerMenu = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        view.findViewById(R.id.move_tab_page).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movePage(TabMenuFragment.newInstance());
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mDrawerMenu.isDrawerOpen(GravityCompat.START)) {
            mDrawerMenu.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
            finishStep();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        super.navigationItemSelected(item);

        mDrawerMenu.closeDrawer(GravityCompat.START);
        return true;
    }


}