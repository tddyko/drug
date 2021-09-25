package com.greencross.medigene.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.greencross.medigene.R;
import com.greencross.medigene.base.BaseFragment;
import com.greencross.medigene.base.CommonActionBar;
import com.greencross.medigene.base.IBaseFragment;
import com.greencross.medigene.component.CDialog;
import com.greencross.medigene.database.DBHelper;
import com.greencross.medigene.database.DBHelperMember;
import com.greencross.medigene.util.EditTextUtil;
import com.greencross.medigene.util.SharedPref;
import com.greencross.medigene.util.StringUtil;

/**
 * Created by insystemscompany on 2017. 2. 28..
 */

public class SettingMemberEditFragment extends BaseFragment implements IBaseFragment {

    private TextView setting_memid;
    private EditText memtxt_pass1;
    private EditText memtxt_pass2;
    private Button mem_save_button;

    public static Fragment newInstance() {
        SettingMemberEditFragment fragment = new SettingMemberEditFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_memedit, container, false);
        return view;
    }

    @Override
    public void loadActionbar(CommonActionBar actionBar) {
        super.loadActionbar(actionBar);
        actionBar.setActionBarTitle(getString(R.string.text_memberedit));
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setting_memid = (TextView) view.findViewById(R.id.setting_memid);
        memtxt_pass1 = (EditText) view.findViewById(R.id.memtxt_pass1);
        memtxt_pass2 = (EditText) view.findViewById(R.id.memtxt_pass2);
        mem_save_button = (Button) view.findViewById(R.id.mem_save_button);

        view.findViewById(R.id.mem_save_button).setOnClickListener(mOnClickListener);
        String email = SharedPref.getInstance().getPreferences(SharedPref.SAVED_LOGIN_ID);
        setting_memid.setText(email);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int vId = v.getId();
            if (R.id.mem_save_button == vId) {
                showProgress();
                if (isPasswordValidation()){
                    doEditMember();
                }
                hideProgress();
            }
        }
    };

    private boolean isPasswordValidation(){

        final String pass1 = memtxt_pass1.getText().toString();
        final String pass2 = memtxt_pass2.getText().toString();
        if (pass1.length() < 8 || pass2.length() < 8 || pass1.length() > 15) {
            CDialog.showDlg(getContext(), getString(R.string.join_step1_pwd_error_leng8));
            return false;
        } else if (!StringUtil.isValidPassword(pass1) || !StringUtil.isValidPassword(pass2)) {
            CDialog.showDlg(getContext(), getString(R.string.join_step1_pwd_error_leng8));
            return false;
        } else if (pass1.equals(pass2) == false) {
            CDialog.showDlg(getContext(), getString(R.string.join_step1_pwd_error));
            return false;
        }
        return true;
    }

    private void doEditMember() {

        final String pwd = memtxt_pass1.getText().toString();
        DBHelper helper = new DBHelper(getContext());
        DBHelperMember db = helper.getMemberDb();
        db.editMemberDb(pwd);

        Toast.makeText(getContext(), getString(R.string.message_finish_emember), Toast.LENGTH_SHORT).show();

        EditTextUtil.hideKeyboard(getContext(), memtxt_pass1);
        EditTextUtil.hideKeyboard(getContext(), memtxt_pass2);

        // 자동로그인 취소.
        SharedPref.getInstance().savePreferences(SharedPref.IS_AUTO_LOGIN, false);

        hideProgress();
        onBackPressed();
    }
}
