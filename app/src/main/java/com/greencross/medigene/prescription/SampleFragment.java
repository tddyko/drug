package com.greencross.medigene.prescription;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.greencross.medigene.R;
import com.greencross.medigene.WebViewFragment;
import com.greencross.medigene.base.BaseFragment;
import com.greencross.medigene.base.CommonActionBar;
import com.greencross.medigene.component.CDatePicker;
import com.greencross.medigene.component.CDialog;
import com.greencross.medigene.database.DBHelper;
import com.greencross.medigene.database.DBHelperDataRecipe;
import com.greencross.medigene.database.util.DBBackupManager;
import com.greencross.medigene.util.Logger;
import com.greencross.medigene.util.ViewUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by MrsWin on 2017-03-01.
 */

public class SampleFragment extends BaseFragment {
    public static String SAMPLE_BACK_DATA = "SAMPLE_BACK_DATA";

    public static Fragment newInstance() {
        SampleFragment fragment = new SampleFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sample_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.sample_time_picker).setOnClickListener(mClickListener);
        view.findViewById(R.id.sample_date_picker).setOnClickListener(mClickListener);
        view.findViewById(R.id.sample_default_webivew).setOnClickListener(mClickListener);
        view.findViewById(R.id.sample_draw_view2).setOnClickListener(mClickListener);

        view.findViewById(R.id.sample_dlg_prescription).setOnClickListener(mClickListener);
        view.findViewById(R.id.sample_camera_exam).setOnClickListener(mClickListener);
        view.findViewById(R.id.sample_add_prescription).setOnClickListener(mClickListener);
        view.findViewById(R.id.sample_manage_prescription).setOnClickListener(mClickListener);
        view.findViewById(R.id.sample_alert).setOnClickListener(mClickListener);
        view.findViewById(R.id.sample_test_connection).setOnClickListener(mClickListener);

        view.findViewById(R.id.db_export_button).setOnClickListener(mClickListener);
        view.findViewById(R.id.db_import_button).setOnClickListener(mClickListener);

        /** font Typeface 적용 */
        TextView typeTextView = (TextView)view.findViewById(R.id.sample_type_textview);
        EditText typeEditText = (EditText)view.findViewById(R.id.sample_type_edittext);
        ViewUtil.setTypefaceKelsonSansBold(getContext(), typeTextView);
        ViewUtil.setTypefaceKelsonSansBold(getContext(), typeEditText);
    }

    /**
     * 액션바 세팅
     */
    @Override
    public void loadActionbar(CommonActionBar actionBar) {
        super.loadActionbar(actionBar);
        actionBar.setActionBarTitle(getString(R.string.text_login));
    }


    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int vId = v.getId();

            if (vId == R.id.sample_time_picker) {
//                movePage(CircleProgressFragment.newInstance());
                showTimePicker();
            } else if (vId == R.id.sample_date_picker) {
                showDatePicker(v);
            } else if (vId == R.id.sample_alert) {
                // 클릭 리스너 연결
                CDialog.showDlg(getContext(), "메시지", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "makeText", Toast.LENGTH_SHORT).show();
                    }
                });

            } else if (vId == R.id.sample_camera_exam) {
                movePage(ImageFragment.newInstance());
            } else if (vId == R.id.sample_default_webivew) {
                movePage(WebViewFragment.newInstance());
            } else if (vId == R.id.sample_dlg_prescription) {
                // 처방전 추가 다이얼로그
                DBHelper helper = new DBHelper(getContext());
                DBHelperDataRecipe db = helper.getmDataRecipe();
                List<DBHelperDataRecipe.RecipeData> listData = db.getResult();
                PrescriptionDialog dlg = new PrescriptionDialog(SampleFragment.this, "");
                dlg.setData(listData);
            } else if (vId == R.id.db_import_button) {
                Toast.makeText(getContext(), "db_import_button", Toast.LENGTH_SHORT).show();
                new DBBackupManager().copyDb(getContext());
            } else if (vId == R.id.sample_add_prescription) {
                movePage(PrescriptionFragment.newInstance());
            } else if (vId == R.id.sample_manage_prescription) {
                movePage(PrescriptionManageFragment.newInstance());
            } else if (vId == R.id.db_export_button) {
                Toast.makeText(getContext(), "db_export_button", Toast.LENGTH_SHORT).show();
                new DBBackupManager().exportDB(getContext());
            }
        }
    };

    /**
     * 사진보기 코드
     */
//    private showPhotoView() {
//        ImageView iv = (ImageView) findViewById(R.id.imageView);
//        Drawable bitmap = getResources().getDrawable(R.drawable.wallpaper);
//        iv.setImageDrawable(bitmap);
//        PhotoViewAttacher mAttacher = new PhotoViewAttacher(mImageView);
//
//        <uk.co.senab.photoview.PhotoView
//        android:id="@+id/widget_photoview"
//        android:layout_width="match_parent"
//        android:layout_height="match_parent"
//        android:adjustViewBounds="true"
//        android:layout_gravity="center"/>
//
//    }


    /**
     * 시간 Picer 표시
     */
    private void showTimePicker() {
        Calendar cal = Calendar.getInstance(Locale.KOREA);
        Date now = new Date();
        cal.setTime(now);

        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        TimePickerDialog dialog = new TimePickerDialog(getContext(), listener, hour, minute, false);
        dialog.show();
    }

    /**
     * 시간 피커 완료
     */
    private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // 설정버튼 눌렀을 때
            Toast.makeText(getContext(), hourOfDay + "시 " + minute + "분", Toast.LENGTH_SHORT).show();
        }
    };

    private void showDatePicker(View v) {
        GregorianCalendar calendar = new GregorianCalendar();
        String birth = "2017";//mBrithEt.getText().toString().trim();
        String[] birthSpl = birth.split("\\.");

//        Toast.makeText(getContext(), birthSpl.length, Toast.LENGTH_SHORT).showDlg();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (birthSpl.length == 3) {
            year = Integer.parseInt("".equals(birthSpl[0]) ? "0" : birthSpl[0].trim());
            month = Integer.parseInt("".equals(birthSpl[1]) ? "0" : birthSpl[1].trim()) - 1;
            day = Integer.parseInt("".equals(birthSpl[2]) ? "0" : birthSpl[2].trim());
        }


//                int hour = calendar.get(Calendar.HOUR_OF_DAY);
//                int minute = calendar.get(Calendar.MINUTE);
//        EditTextUtil.hideKeyboard(getContext(), mBrithEt);
        new CDatePicker(getContext(), dateSetListener, year, month, day).show();
    }
    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String msg = String.format("%d. %d. %d", year, monthOfYear + 1, dayOfMonth);

            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
//            mBrithEt.setText(msg);
        }

    };


    @Override
    public void onResume() {
        super.onResume();
        // 이전 플래그먼트에서 데이터 받기
        Bundle bundle = getBackData();
        String backString = bundle.getString(SAMPLE_BACK_DATA);
        Logger.i("", "backString="+backString);
    }

    @Override
    public void onBackPressed() {
        super.finishStep();
    }
}
