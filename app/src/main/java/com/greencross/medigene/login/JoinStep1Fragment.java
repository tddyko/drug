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
import android.widget.CheckBox;
import android.widget.EditText;

import com.greencross.medigene.R;
import com.greencross.medigene.base.BaseFragment;
import com.greencross.medigene.base.CommonActionBar;
import com.greencross.medigene.component.CDialog;
import com.greencross.medigene.util.EditTextUtil;

/**
 * Created by MrsWin on 2017-02-16.
 */

public class JoinStep1Fragment extends BaseFragment {
    public static String JOIN_DATA = "join_data";   // 회원가입시에 사용할 데이터들

    private EditText txtName;
    private EditText txtBirth;
    private EditText txtPhone;
    private CheckBox checkSex1;
    private CheckBox checkSex2;
    private CheckBox checkInter1;
    private CheckBox checkInter2;

    private CheckBox checkAgree1;
    private CheckBox checkAgree2;
    private Button btnAgree1;
    private Button btnAgree2;
    private Button nextbutton;

    public static Fragment newInstance() {
        JoinStep1Fragment fragment = new JoinStep1Fragment();
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
        View view = inflater.inflate(R.layout.join_step1_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtName         = (EditText) view.findViewById(R.id.login_name_edittext);
        txtBirth        = (EditText) view.findViewById(R.id.join_step1_birth_edittext1);
        txtPhone        = (EditText) view.findViewById(R.id.join_step1_phone_edittext1);

        checkSex1           = (CheckBox) view.findViewById(R.id.join_step1_sex1);
        checkSex2           = (CheckBox) view.findViewById(R.id.join_step1_sex2);
        checkInter1         = (CheckBox) view.findViewById(R.id.join_step1_inter1);
        checkInter2         = (CheckBox) view.findViewById(R.id.join_step1_inter2);
        checkAgree1         = (CheckBox) view.findViewById(R.id.join_step1_checkagree1);
        checkAgree2         = (CheckBox) view.findViewById(R.id.join_step1_checkagree2);

        btnAgree1       = (Button) view.findViewById(R.id.btn_agree1_detail);
        btnAgree2       = (Button) view.findViewById(R.id.btn_agree2_detail);
        nextbutton      = (Button) view.findViewById(R.id.next_button);

        view.findViewById(R.id.join_step1_sex1).setOnClickListener(mClickListener);
        view.findViewById(R.id.join_step1_sex2).setOnClickListener(mClickListener);
        view.findViewById(R.id.join_step1_inter1).setOnClickListener(mClickListener);
        view.findViewById(R.id.join_step1_inter2).setOnClickListener(mClickListener);
        view.findViewById(R.id.join_step1_checkagree1).setOnClickListener(mClickListener);
        view.findViewById(R.id.join_step1_checkagree2).setOnClickListener(mClickListener);
        view.findViewById(R.id.btn_agree1_detail).setOnClickListener(mClickListener);
        view.findViewById(R.id.btn_agree2_detail).setOnClickListener(mClickListener);
        view.findViewById(R.id.next_button).setOnClickListener(mClickListener);

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

            EditTextUtil.hideKeyboard(getContext(), txtName);
            EditTextUtil.hideKeyboard(getContext(), txtBirth);
            EditTextUtil.hideKeyboard(getContext(), txtPhone);

            if (vId == R.id.join_step1_sex1) {
                checkSex2.setChecked(!checkSex1.isChecked());
            } else if (vId == R.id.join_step1_sex2) {
                checkSex1.setChecked(!checkSex2.isChecked());
            }else if (vId == R.id.join_step1_inter1) {
                checkInter2.setChecked(!checkInter1.isChecked());
            }else if (vId == R.id.join_step1_inter2) {
                checkInter1.setChecked(!checkInter2.isChecked());
            }else if (vId == R.id.btn_agree1_detail) {
                CDialog.showDlg(getContext(), getString(R.string.join_step1_contract1_title), getString(R.string.join_step1_contract1_msg));
            }else if (vId == R.id.btn_agree2_detail) {
                CDialog.showDlg(getContext(), getString(R.string.join_step1_contract2_title), getString(R.string.join_step1_contract2_msg));
            }else if (vId == R.id.next_button) {

                if (validIdCheck()){

                    showProgress();
                    Bundle bundle = new Bundle();
                    bundle.putString(PARAMS_MBER_NAME, txtName.getText().toString());
                    bundle.putString(PARAMS_MBER_BIRTH, txtBirth.getText().toString());
                    bundle.putString(PARAMS_MBER_SEX, checkSex1.isChecked()?"M":"W");
                    bundle.putString(PARAMS_MBER_PHONE, txtPhone.getText().toString());
                    bundle.putString(PARAMS_MBER_INTER, checkInter1.isChecked()?"0":"1");
                    movePage(JoinStep2Fragment.newInstance(), bundle);
                    hideProgress();
                }
            }
        }
    };


    /**
     * 아이디체크
     *
     * @return
     */
    private boolean validIdCheck() {

        String name = txtName.getText().toString();
        String bir = txtBirth.getText().toString();
        String phone = txtPhone.getText().toString();
        if (TextUtils.isEmpty(name)) {
            CDialog.showDlg(getContext(), "이름을 입력하여주세요.");
            return false;
        } else if (bir.length() < 8) {
            CDialog.showDlg(getContext(), "생년월일을 정확히 입력하여주세요.\n\n예) 19830519");
            return false;
        } else if (phone.length() < 10) {
            CDialog.showDlg(getContext(), "휴대폰번호를 정확히 입력하여주세요.");
            return false;
        }else if (!checkAgree1.isChecked() || !checkAgree2.isChecked()) {
            CDialog.showDlg(getContext(), "개인정보 제공동의 및 개인민감 정보제공 동의하여야 합니다.");
            return false;
        }
        return true;
    }


}
