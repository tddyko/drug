package com.greencross.medigene.base;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.greencross.medigene.BaseActivity;
import com.greencross.medigene.MainActivity;
import com.greencross.medigene.R;
import com.greencross.medigene.alerm.DrugAlarmFragment;
import com.greencross.medigene.base.value.Define;
import com.greencross.medigene.checkresult.CheckresultFragment;
import com.greencross.medigene.component.CDialog;
import com.greencross.medigene.drug.DrugInfoHistoryListFragment;
import com.greencross.medigene.healthinfo.HealthInfoFragment;
import com.greencross.medigene.main.MainFragment;
import com.greencross.medigene.prescription.PrescriptionFragment;
import com.greencross.medigene.prescription.PrescriptionManageFragment;
import com.greencross.medigene.serviceinfo.ServiceInfoFragment;
import com.greencross.medigene.setting.SettingFragment;
import com.greencross.medigene.util.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrsWin on 2017-02-16.
 */

public class BaseFragment extends Fragment implements IBaseFragment {
    private final String TAG = BaseFragment.class.getSimpleName();

    public String DRUGINFO_IDX          =  "DRUGINFO_IDX";
    public String DRUGINFO_KEYWORD      =  "DRUGINFO_KEYWORD";
    public String PARAMS_ACTION_URL     =  "PARAMS_ACTION_URL";

    public String PARAMS_MBER_NAME      = "PARAMS_MBER_NAME";
    public String PARAMS_MBER_BIRTH     = "PARAMS_MBER_BIRTH";
    public String PARAMS_MBER_SEX       = "PARAMS_MBER_SEX";
    public String PARAMS_MBER_PHONE     = "PARAMS_MBER_PHONE";
    public String PARAMS_MBER_INTER     = "PARAMS_MBER_INTER";
    public String PARAMS_MBER_EMAIL     = "PARAMS_MBER_EMAIL";
    public String PARAMS_MBER_PASS      = "PARAMS_MBER_PASS";

    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 33;
    private int mRequestCode = 1111;

    private IPermission mIpermission = null;

    boolean mIsLogin = false;

    private static BaseActivity mBaseActivity;
    private CommonActionBar mActionBar;

    public static Fragment newInstance(BaseActivity activity) {
        BaseFragment fragment = new BaseFragment();
        mBaseActivity = activity;
        return fragment;
    }

    public void movePage(Fragment fragment) {
        movePage(fragment, null);
    }

    public void movePage(Fragment fragment, Bundle bundle) {
        mActionBar = mBaseActivity.getCommonActionBar();

        mBaseActivity.replaceFragment(fragment, bundle);
    }

    /**
     * 사용할 레이아웃 또는 View 지정
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        onViewCreated(view, savedInstanceState);
        return view;
    }

    /**
     * 뷰가 생성된 후 세팅
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadActionbar(getCommonActionBar());

    }

    @Override
    public void onBackPressed() {
        Logger.i(TAG, "BaseFragment.onBackPressed().getActivity()=" + getActivity());
        if (getActivity() instanceof MainActivity) {
            MainActivity activity = (MainActivity) getActivity();
            activity.superBackPressed();
        } else if (getActivity() instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) getActivity();
            activity.onBackPressed();
        } else {
            mBaseActivity.onBackPressed();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadActionbar(getCommonActionBar());
    }


    protected void onCreateOptionsMenu(Menu menu) {
        mBaseActivity.onCreateOptionsMenu(menu);
    }

    private ActionBar getActionBar() {
        return mBaseActivity.getSupportActionBar();
    }

    public CommonActionBar getCommonActionBar() {
        if (mActionBar == null)
            mActionBar = mBaseActivity.getCommonActionBar();

        return mActionBar;
    }

    /**
     * Back 이동시 데이터 전달
     */
    private static Bundle mBackDataBundle;

    protected static void setBackData(Bundle bundle) {
        mBackDataBundle = bundle;
    }

    public static Bundle getBackData() {
        Bundle bundle = mBackDataBundle;
        if (mBackDataBundle != null) {
            mBackDataBundle = new Bundle(); // 초기화
            return bundle;
        } else {
            return new Bundle();
        }
    }

    /**
     * 현재 보여지고 있는 Fragment
     *
     * @return
     */
    public Fragment getVisibleFragment() {
        Fragment fragment = mBaseActivity.getVisibleFragment();

        return fragment;
    }

    public void showProgress() {
        mBaseActivity.showProgress();
    }

    public void hideProgress() {
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mBaseActivity.hideProgress();
                }
            }, 1 * 300);
        }catch (Exception e){
            if (mBaseActivity != null)
                mBaseActivity.hideProgress();
        }
    }

    /**
     * @param type           // 액션바 타입
     * @param title          // 타이틀
     * @param clickListener1 // 맨 오른쪽 버튼 처리
     * @param clickListener2 // 맨 오른쪽 옆 버튼 처리
     */
    public void setActionBar(Define.ACTION_BAR type, String title, View.OnClickListener clickListener1, View.OnClickListener clickListener2) {
        mActionBar.setActionBar(type, title, clickListener1, clickListener2);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent, null);
    }

    @Override
    public void startActivity(Intent intent, @Nullable Bundle options) {
        super.startActivity(intent, options);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode, null);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
    }

    public void startActivityForResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void loadActionbar(CommonActionBar actionBar) {

        if (mBaseActivity != null) {
            actionBar.setActionBackBtnVisible(View.VISIBLE);
            actionBar.goneActionBarFunctionBtn();
            actionBar.showActionBar(true);            // 액션바 띄울지 여부

            actionBar.setActionBarTitle("");
            Bundle bundle = getArguments();
            if (bundle != null) {
                String title = getArguments().getString(CommonActionBar.ACTION_BAR_TITLE);
                if (TextUtils.isEmpty(title) == false)
                    actionBar.setActionBarTitle(title);

                Logger.i(TAG, "loadActionbar.title=" + title);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.i(TAG, TAG + ".onActivityResult");
    }

    public void finishStep() {
        mBaseActivity.finishStep();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public BaseFragment getFragment(Class<?> cls) {
        BaseFragment fragment = null;
        try {
            Constructor<?> co = cls.getConstructor();
            fragment = (BaseFragment) co.newInstance();
        } catch (Exception e) {
            Log.e(TAG, "getFragment", e);
        }
        return fragment;
    }

    public void reqPermissions(String[] perms, IPermission iPermission) {
        mIpermission = iPermission;
        final String[] permissions = getGrandtedPermissions(perms);
        if (permissions.length > 0) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            CDialog.showDlg(getContext(), "권한 설정 후 이용 가능합니다.", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityCompat.requestPermissions(getActivity()
                            , permissions
                            , REQUEST_PERMISSIONS_REQUEST_CODE);
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        } else {
            if (iPermission != null) {
                iPermission.result(true);
                mIpermission = null;
            }

        }
    }

    /**
     * 설정이 되지 않은 권한들 가져옴
     * @return
     */
    private String[] getGrandtedPermissions(String... permissions) {
        List<String> list = new ArrayList<>();
        for (String perm : permissions) {
            int isGrandted = ActivityCompat.checkSelfPermission(getContext(), perm);

            if (isGrandted != PackageManager.PERMISSION_GRANTED)
                list.add(perm);
        }

        String[] permissionArr = new String[list.size()];
        permissionArr = list.toArray(permissionArr);
        return permissionArr;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions, @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                if (mIpermission != null) {
                    mIpermission.result(true);
                    mIpermission = null;
                }
            } else if (isPermissionGransteds(grantResults)) {
                if (mIpermission != null) {
                    mIpermission.result(true);
                    mIpermission = null;
                }
            } else {
                if (mIpermission != null) {
                    mIpermission.result(false);
                    mIpermission = null;
                } else {
                    CDialog.showDlg(getContext(), "권한 설정 후 이용 가능합니다.", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            reqPermissions(permissions, mIpermission);
                        }
                    }, null);
                }
            }
        }
    }

    /**
     * 앱 재시작
     */
    public void restartMainActivity() {
        getActivity().finish();

        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }

    /**
     * 권한이 정상적으로 설정 되었는지 확인
     *
     * @param grantResults
     * @return
     */
    private boolean isPermissionGransteds(int[] grantResults) {
        for (int isGranted : grantResults) {
            return isGranted == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    public interface IPermission {
        void result(boolean isGranted);
    }

    // 화면 회전시 초기화 방지
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public void navigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {  // 홈
            movePage(MainFragment.newInstance());
        } else if (id == R.id.nav_search) { // 약품검색
            movePage(DrugInfoHistoryListFragment.newInstance());
        } else if (id == R.id.nav_result) { // 검사결과보기
            movePage(CheckresultFragment.newInstance());
        } else if (id == R.id.nav_add) {    // 처방전추가
            movePage(PrescriptionFragment.newInstance());
        } else if (id == R.id.nav_manag) {  // 처방전관리
            movePage(PrescriptionManageFragment.newInstance());
        } else if (id == R.id.nav_healthinfo) { // 건강정보
            movePage(HealthInfoFragment.newInstance());
        } else if (id == R.id.nav_alarm) {  // 복약알람
            movePage(DrugAlarmFragment.newInstance());
        } else if (id == R.id.nav_serviceinfo) {    //서비스 안내
            movePage(ServiceInfoFragment.newInstance());
        } else if (id == R.id.nav_call) {   // 헬스콜센터 연결
            String tel = "tel:0220409100";
//            startActivity(new Intent("android.intent.action.CALL", Uri.parse(tel)));
            startActivity(new Intent("android.intent.action.DIAL", Uri.parse(tel)));
        } else if (id == R.id.nav_setting) {    // 설정
            movePage(SettingFragment.newInstance());
        }

    }
}
