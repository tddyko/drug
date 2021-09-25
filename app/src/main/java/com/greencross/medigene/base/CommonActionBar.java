package com.greencross.medigene.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.greencross.medigene.BaseActivity;
import com.greencross.medigene.R;
import com.greencross.medigene.base.value.Define;
import com.greencross.medigene.prescription.SampleFragment;
import com.greencross.medigene.util.Logger;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.greencross.medigene.R.id.action_menu_btn;

/**
 * Created by MrsWin on 2017-02-16.
 */

public class CommonActionBar {
    private final String TAG = CommonActionBar.class.getSimpleName();

    public static String ACTION_BAR_TITLE = "action_bar_title";

    private BaseActivity mActivity;

    protected ImageButton mActionMenuBtn, mActionBackBtn, mActionSettingBtn, mActionDeviceBtn, mActionBluetoothBtn, mActionWriteBtn, mActionMediBtn, mActionWriteStepBtn, mActionBandStepBtn;
    protected Button mActionSaveBtn;

    private ActionBar mActionBar;
    private View mBlockView;

    public CommonActionBar(BaseActivity activity) {
        mActivity = activity;
        createActionBar();
    }

    private void createActionBar() {
        Log.i(TAG, "createActionBar");
        mActionBar = mActivity.getSupportActionBar();

        // Custom Actionbar를 사용하기 위해 CustomEnabled을 true 시키고 필요 없는 것은 false 시킨다
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(false);         //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        mActionBar.setDisplayShowTitleEnabled(false);        //액션바에 표시되는 제목의 표시유무를 설정합니다.
        mActionBar.setDisplayShowHomeEnabled(false);         //홈 아이콘을 숨김처리합니다.

        //layout을 가지고 와서 actionbar에 포팅을 시킵니다.
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(LAYOUT_INFLATER_SERVICE);
        View actionbar = inflater.inflate(R.layout.custom_action_bar, null);

        mBlockView = actionbar.findViewById(R.id.action_bar_block_layout);

        mActionMenuBtn = (ImageButton) actionbar.findViewById(action_menu_btn);
        mActionBackBtn = (ImageButton) actionbar.findViewById(R.id.action_bar_back_button);
        mActionDeviceBtn = (ImageButton) actionbar.findViewById(R.id.action_bar_device_btn);
        mActionBluetoothBtn = (ImageButton) actionbar.findViewById(R.id.action_bar_bluetooth_btn);
        mActionSettingBtn = (ImageButton) actionbar.findViewById(R.id.action_bar_setting_btn);
        mActionWriteBtn = (ImageButton) actionbar.findViewById(R.id.action_bar_write_btn);
        mActionWriteStepBtn = (ImageButton) actionbar.findViewById(R.id.action_bar_write_step_btn);
        mActionBandStepBtn = (ImageButton) actionbar.findViewById(R.id.action_bar_band_step_btn);
        mActionMediBtn = (ImageButton) actionbar.findViewById(R.id.action_bar_medi_btn);
        mActionSaveBtn = (Button) actionbar.findViewById(R.id.action_bar_save_btn);

        mActionBackBtn.setOnClickListener(actionBarClickListener);
        mActionSettingBtn.setOnClickListener(actionBarClickListener);
        mActionWriteBtn.setOnClickListener(actionBarClickListener);
        mActionMediBtn.setOnClickListener(actionBarClickListener);
        mActionSaveBtn.setOnClickListener(actionBarClickListener);
        mActionWriteStepBtn.setOnClickListener(actionBarClickListener);
        mActionBandStepBtn.setOnClickListener(actionBarClickListener);

        mActionBar.setCustomView(actionbar);
        mActionBar.setShowHideAnimationEnabled(false);

        //액션바 양쪽 공백 없애기
        Toolbar parent = (Toolbar) actionbar.getParent();
        parent.setContentInsetsAbsolute(0, 0);
        parent.setAnimation(null);
    }

    View.OnClickListener actionBarClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int vId = v.getId();
            if (R.id.action_bar_back_button == vId) {
                mActivity.onBackPressed();
            }
        }
    };

    public void showActionBar(boolean isShow) {
        if (mActionBar != null) {
            if (isShow) {
                mActionBar.show();
            } else {
                mActionBar.hide();
            }
        }
    }

    /**
     * 액션바 타이틀 세팅
     *
     * @param title
     */
    public TextView setActionBarTitle(String title) {
        View v = mActivity.getSupportActionBar().getCustomView();
        TextView titleTxtView = (TextView) v.findViewById(R.id.action_bar_title_textview);

        Fragment fragment = mActivity.getVisibleFragment();
        if (fragment != null) {
            if (fragment.getArguments() != null) {
                // 플래그먼트 이동시 세팅해준 타이틀명
                String bundleTitle = fragment.getArguments().getString(CommonActionBar.ACTION_BAR_TITLE);
                titleTxtView.setText(bundleTitle == null ? title : bundleTitle);
            } else {
                titleTxtView.setText(title);
            }
        } else {
            titleTxtView.setText(title);
        }

//        // 테스트일때 샘플코드로 이동
        if (Logger.mUseLogSetting) {
            titleTxtView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.replaceFragment(SampleFragment.newInstance());
                }
            });
        }

        return titleTxtView;
    }

    public TextView getActionBarTitle() {
        View v = mActivity.getSupportActionBar().getCustomView();
        TextView titleTxtView = (TextView) v.findViewById(R.id.action_bar_title_textview);
        return titleTxtView;
    }

    public Button getActionBarOptionMenuBtn() {
        View v = mActivity.getSupportActionBar().getCustomView();
        Button optionButton = (Button) v.findViewById(R.id.action_bar_option_menu_button);

        return optionButton;
    }

    public void goneActionBarFunctionBtn() {
        mActionDeviceBtn.setVisibility(View.GONE);
        mActionSettingBtn.setVisibility(View.GONE);
        mActionWriteBtn.setVisibility(View.GONE);
        mActionMediBtn.setVisibility(View.GONE);
        mActionSaveBtn.setVisibility(View.GONE);
        mActionWriteStepBtn.setVisibility(View.GONE);
        mActionBandStepBtn.setVisibility(View.GONE);
    }

    public void setActionBarMenuBtn(View.OnClickListener clickListener) {
        mActionMenuBtn.setOnClickListener(clickListener);
    }

    public void setActionBackBtnVisible(int isVisible) {
        mActionBackBtn.setVisibility(isVisible);
        if (mActionBackBtn.getVisibility()==View.VISIBLE){
            mActionMenuBtn.setVisibility(View.GONE);
        }else{
            mActionBackBtn.setVisibility(View.GONE);
            mActionMenuBtn.setVisibility(View.VISIBLE);
        }
    }

    public void setActionBarSettingBtn(boolean isShow, View.OnClickListener clickListener) {
        mActionSettingBtn.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mActionSettingBtn.setOnClickListener(clickListener);
    }

    public void setActionBarMediBtn(boolean isShow, View.OnClickListener clickListener) {
        mActionMediBtn.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mActionMediBtn.setOnClickListener(clickListener);
    }

    public Button setActionBarSaveBtn(View.OnClickListener clickListener) {
        mActionSaveBtn.setVisibility(View.VISIBLE);
        mActionSaveBtn.setOnClickListener(clickListener);
        return mActionSaveBtn;
    }

    int mRequestCode = 1111;

    public void setActionBarWriteBtn(final Class<?> cls, final Bundle bundle) {
        if (mActivity != null) {

            setActionBarWriteBtn(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mActivity.getVisibleFragment() instanceof BaseFragment) {
                        BaseFragment visibleFragment = (BaseFragment) mActivity.getVisibleFragment();
                        DummyActivity.startActivityForResult(visibleFragment, mRequestCode, cls, bundle);
                    }
                }
            });
        }
    }

    public void setActionBarWriteBtn(boolean isShow, View.OnClickListener clickListener) {
        mActionWriteBtn.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mActionWriteBtn.setOnClickListener(clickListener);
    }

    public void setActionBar(Define.ACTION_BAR type, String title, View.OnClickListener clickListener1, View.OnClickListener clickListener2) {
        setActionBarTitle(title);
        if (type == Define.ACTION_BAR.NO_RIGHT_MENU) {
            // 오른쪽 버튼 모두 안나오게
            setActionBarSettingBtn(false, null);
            setActionBarWriteBtn(false, null);
        } else if (type == Define.ACTION_BAR.RIGHT_MENU1) {
            // 오른쪽 버튼 하나만 나오게
            setActionBarSettingBtn(true, clickListener1);
            setActionBarWriteBtn(false, null);
        } else if (type == Define.ACTION_BAR.RIGHT_MENU2) {
            // 오른쪽 버튼 두개 나오게
            setActionBarSettingBtn(true, clickListener1);
            setActionBarWriteBtn(true, clickListener2);
        }
    }

    /**
     * 액션바 Progress Visible 일때 block 처리
     *
     * @param isVisible
     */
    public void showBlockLayout(boolean isVisible) {
        mBlockView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }
}
