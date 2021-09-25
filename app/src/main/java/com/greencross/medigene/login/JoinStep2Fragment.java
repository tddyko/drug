package com.greencross.medigene.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.greencross.medigene.R;
import com.greencross.medigene.base.BaseFragment;
import com.greencross.medigene.base.CommonActionBar;
import com.greencross.medigene.component.CDialog;
import com.greencross.medigene.database.DBHelper;
import com.greencross.medigene.database.DBHelperMember;
import com.greencross.medigene.util.EditTextUtil;
import com.greencross.medigene.util.StringUtil;

/**
 * Created by MrsWin on 2017-02-16.
 */

public class JoinStep2Fragment extends BaseFragment {
    public static String JOIN_DATA = "join_data";   // 회원가입시에 사용할 데이터들


    private String _PARAMS_MBER_NAME;
    private String _PARAMS_MBER_BIRTH;
    private String _PARAMS_MBER_SEX;
    private String _PARAMS_MBER_PHONE;
    private String _PARAMS_MBER_INTER;
    private EditText txtEmail;
    private EditText txtPass1;
    private EditText txtPass2;
    private Button buttonNext;


    public static Fragment newInstance() {
        JoinStep2Fragment fragment = new JoinStep2Fragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(this.getClass().getSimpleName(), "onCreate()");
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.join_step2_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtEmail = (EditText) view.findViewById(R.id.login_id_edittext);
        txtPass1 = (EditText) view.findViewById(R.id.join_step1_pwd_edittext1);
        txtPass2 = (EditText) view.findViewById(R.id.join_step1_pwd_edittext2);
        buttonNext = (Button) view.findViewById(R.id.next_button);

        view.findViewById(R.id.next_button).setOnClickListener(mClickListener);

        if (getArguments() != null) {

            _PARAMS_MBER_NAME       = getArguments().getString(PARAMS_MBER_NAME);
            _PARAMS_MBER_BIRTH      = getArguments().getString(PARAMS_MBER_BIRTH);
            _PARAMS_MBER_SEX        = getArguments().getString(PARAMS_MBER_SEX);
            _PARAMS_MBER_PHONE      = getArguments().getString(PARAMS_MBER_PHONE);
            _PARAMS_MBER_INTER      = getArguments().getString(PARAMS_MBER_INTER);
        }

    }

    @Override
    public void loadActionbar(CommonActionBar actionBar) {
        super.loadActionbar(actionBar);
        actionBar.setActionBarTitle(getString(R.string.join_inform_step1));       // 액션바 타이틀
    }
    @Override
    public void onResume() {
        Log.d(this.getClass().getSimpleName(), "onResume()");
        super.onResume();

    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int vId = v.getId();

            EditTextUtil.hideKeyboard(getContext(), txtEmail);
            EditTextUtil.hideKeyboard(getContext(), txtPass1);
            EditTextUtil.hideKeyboard(getContext(), txtPass2);

            if (vId == R.id.next_button) {
                showProgress();
                if (validIdCheck()){
                    doRegistMember();
                }
                hideProgress();
            }
        }
    };


    private boolean validIdCheck() {

        String email = txtEmail.getText().toString();
        String pass1 = txtPass1.getText().toString();
        String pass2 = txtPass2.getText().toString();
        if (TextUtils.isEmpty(email)) {
            CDialog.showDlg(getContext(), "이메일을 입력하여주세요.");
            return false;
        } else if (pass1.length() < 8 || pass2.length() < 8 || pass1.length() > 15) {
            CDialog.showDlg(getContext(), getString(R.string.join_step1_pwd_error_leng8));
            return false;
        } else if (StringUtil.isValidEmail(email) == false) {
            CDialog.showDlg(getContext(), getString(R.string.join_step1_id_error));
            return false;
        } else if (!StringUtil.isValidPassword(pass1) || !StringUtil.isValidPassword(pass2)) {

            CDialog.showDlg(getContext(), getString(R.string.join_step1_pwd_error_leng8));
            return false;
        } else if (pass1.equals(pass2) == false) {
            CDialog.showDlg(getContext(), getString(R.string.join_step1_pwd_error));
            return false;
        }
        // 이메일 중복체크
        DBHelper helper = new DBHelper(getContext());
        DBHelperMember db = helper.getMemberDb();
        if ( db.isRegistMember(email) == false) {
            CDialog.showDlg(getContext(), "중복된 이메일주소가 이미 존재합니다.\n다른 이메일주소를 입력하여주세요.");
            return false;
        }
        return true;
    }



    private void doRegistMember() {

        String id = txtEmail.getText().toString();
        String pwd = txtPass1.getText().toString();

        DBHelper helper = new DBHelper(getContext());
        DBHelperMember db = helper.getMemberDb();
        db.insertDb(id, pwd, _PARAMS_MBER_NAME, _PARAMS_MBER_BIRTH, _PARAMS_MBER_SEX, _PARAMS_MBER_PHONE, _PARAMS_MBER_INTER);

        movePage(LoginFragment.newInstance());
        Toast.makeText(getContext(), getString(R.string.message_finish_member), Toast.LENGTH_SHORT).show();

    }

}
