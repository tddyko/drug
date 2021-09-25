package com.greencross.medigene.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.greencross.medigene.R;
import com.greencross.medigene.base.BaseFragment;
import com.greencross.medigene.base.CommonActionBar;
import com.greencross.medigene.component.CDialog;
import com.greencross.medigene.component.CFontEditText;
import com.greencross.medigene.database.DBHelper;
import com.greencross.medigene.database.DBHelperMember;
import com.greencross.medigene.main.MainFragment;
import com.greencross.medigene.prescription.SampleFragment;
import com.greencross.medigene.util.EditTextUtil;
import com.greencross.medigene.util.Logger;
import com.greencross.medigene.util.SharedPref;
import com.greencross.medigene.util.ViewUtil;

import static com.greencross.medigene.login.FindPwdFragment.FIND_PWD_EMAIL;

/**
 * Created by MrsWin on 2017-02-16.
 */

public class LoginFragment extends BaseFragment {
    private final String TAG = LoginFragment.class.getSimpleName();

    private CheckBox mSaveIdCheckBox;
    private CheckBox mAutoLoginCheckBox;
    private CFontEditText mPwdEditText;
    private CFontEditText mLoginIdEditText;

    public static Fragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        return view;
    }

    @Override
    public void loadActionbar(CommonActionBar actionBar) {
        super.loadActionbar(actionBar);
        actionBar.setActionBackBtnVisible(View.INVISIBLE);
        actionBar.showActionBar(false);
        actionBar.setActionBarTitle( getString(R.string.text_login));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 테스트
        if (Logger.mUseLogSetting) {
            view.findViewById(R.id.app_title).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movePage(SampleFragment.newInstance());
                }
            });
        }

        mSaveIdCheckBox = (CheckBox) view.findViewById(R.id.login_save_id_checkbox);
        mAutoLoginCheckBox = (CheckBox) view.findViewById(R.id.login_auto_login_checkbox);
        mPwdEditText = (CFontEditText) view.findViewById(R.id.login_pwd_edittext);
        mLoginIdEditText = (CFontEditText) view.findViewById(R.id.login_id_edittext);

        view.findViewById(R.id.login_find_id_textview).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.login_find_pwd_textview).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.login_join_button).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.login_login_button).setOnClickListener(mOnClickListener);

        TextView typeTextView = (TextView)view.findViewById(R.id.login_id_edittext);
        ViewUtil.setTypefaceKelsonSansRegular(getContext(), typeTextView);

        typeTextView = (TextView)view.findViewById(R.id.login_pwd_edittext);
        ViewUtil.setTypefaceKelsonSansRegular(getContext(), typeTextView);

        CheckBox typeCheckBox = (CheckBox)view.findViewById(R.id.login_auto_login_checkbox);
        ViewUtil.setTypefaceKelsonSansRegular(getContext(), typeCheckBox);

        Button typeButton = (Button)view.findViewById(R.id.login_login_button);
        ViewUtil.setTypefaceKelsonSansRegular(getContext(), typeButton);

        typeButton = (Button)view.findViewById(R.id.login_join_button);
        ViewUtil.setTypefaceKelsonSansRegular(getContext(), typeButton);

        Boolean isSavedId = SharedPref.getInstance().getPreferences(SharedPref.IS_SAVED_LOGIN_ID, false);
        if (isSavedId) {
            String savedId = SharedPref.getInstance().getPreferences(SharedPref.SAVED_LOGIN_ID);
            mLoginIdEditText.setText(savedId);
            mSaveIdCheckBox.setChecked(true);
            EditTextUtil.hideKeyboard(getContext(), mLoginIdEditText);
        }

        if (getArguments() != null) {
            String email = getArguments().getString(FIND_PWD_EMAIL);
            if (TextUtils.isEmpty(email) == false) {
                mLoginIdEditText.setText(email);
                EditTextUtil.hideKeyboard(getContext(), mLoginIdEditText);
            }
        }
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int vId = v.getId();
            if (R.id.login_find_pwd_textview == vId) {
                movePage(FindPwdFragment.newInstance());
            } else if (R.id.login_join_button == vId) {
                movePage(JoinStep1Fragment.newInstance());
            } else if (R.id.login_login_button == vId) {
                    doLogin();
            }
        }
    };

    private void doLogin() {
        showProgress();

        final String id = mLoginIdEditText.getText().toString();
        if (TextUtils.isEmpty(id)) {
            CDialog.showDlg(getContext(), getString(R.string.id_confirm));
            hideProgress();
            return;
        }
        final String pwd = mPwdEditText.getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            CDialog.showDlg(getContext(), getString(R.string.pw_confirm));
            hideProgress();
            return;
        }

        SharedPref.getInstance().savePreferences(SharedPref.SAVED_LOGIN_ID, id);
        SharedPref.getInstance().savePreferences(SharedPref.IS_SAVED_LOGIN_ID, mSaveIdCheckBox.isChecked());
        SharedPref.getInstance().savePreferences(SharedPref.IS_LOGIN_SUCEESS, true);
        SharedPref.getInstance().savePreferences(SharedPref.IS_AUTO_LOGIN, mAutoLoginCheckBox.isChecked());

        DBHelper helper = new DBHelper(getContext());
        DBHelperMember db = helper.getMemberDb();
        boolean isValid = db.isValidLoginInfo(id, pwd);

        if (isValid) {
            movePage(MainFragment.newInstance());
        } else {
            CDialog.showDlg(getContext(), "아이디 패스워드를 확인해 주세요.");
        }

        hideProgress();
    }

    @Override
    public void onBackPressed() {
        super.finishStep();
    }

    @Override
    protected void onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
