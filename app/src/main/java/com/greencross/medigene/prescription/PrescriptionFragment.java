package com.greencross.medigene.prescription;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.greencross.medigene.R;
import com.greencross.medigene.base.BaseFragment;
import com.greencross.medigene.base.CommonActionBar;
import com.greencross.medigene.base.IBaseFragment;
import com.greencross.medigene.base.value.Define;
import com.greencross.medigene.component.CDatePicker;
import com.greencross.medigene.component.CDialog;
import com.greencross.medigene.database.DBHelper;
import com.greencross.medigene.database.DBHelperDataRecipe;
import com.greencross.medigene.database.DBHelperDrug;
import com.greencross.medigene.database.DBHelperDrugInfo;
import com.greencross.medigene.database.DBHelperRecipeDrug;
import com.greencross.medigene.util.CDateUtil;
import com.greencross.medigene.util.EditTextUtil;
import com.greencross.medigene.util.Logger;
import com.greencross.medigene.util.StringUtil;
import com.greencross.medigene.util.ViewUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by mrsohn on 2017. 3. 14..
 * 처방전 추가
 */

public class PrescriptionFragment extends BaseFragment implements IBaseFragment, NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerMenu;

    private final String TAG = PrescriptionFragment.class.getSimpleName();
    private Uri contentUri;                                 // 앨범,카메라에서 받아온 경로 위치

    private String mIdx = CDateUtil.getForamtyyMMddHHmmssSS(new Date(System.currentTimeMillis()));

    private final int REQUEST_IMAGE_CAPTURE = 111;
    private final int REQUEST_IMAGE_ALBUM = 222;
    private final int REQUEST_IMAGE_CROP = 333;

    private LinearLayout mMainView;
    private Button mSaveBtn;
    private Button mModifyBtn;
    private EditText mHowEt;
    private EditText mHospitalNameEt;
    private EditText mEffectEt;
    private TextView mTimeTv;
    private TextView mDateTv;
    private EditText mNameEt;
    private ImageView mResultIv;
    private int textId = 31;

    private int cal_year;
    private int cal_month;
    private int cal_day;
    private int cal_hour;
    private int cal_min;

    List<DBHelperDrugInfo.DrugData> mDrugData = new ArrayList<>();

    public static Fragment newInstance() {
        PrescriptionFragment fragment = new PrescriptionFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_prescription, container, false);
        return view;
    }

    /**
     * 액션바 세팅
     */
    @Override
    public void loadActionbar(CommonActionBar actionBar) {
        super.loadActionbar(actionBar);
        actionBar.setActionBarTitle("처방전 추가");
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

        mMainView = (LinearLayout) view.findViewById(R.id.preciption_main_linearview);
        mSaveBtn = (Button) view.findViewById(R.id.prescp_save_button);
        mModifyBtn = (Button) view.findViewById(R.id.prescp_modify_button);
        mHowEt = (EditText) view.findViewById(R.id.prescp_how_edittext);
        mHospitalNameEt = (EditText) view.findViewById(R.id.prescp_hospital_edittext);
        mEffectEt = (EditText) view.findViewById(R.id.prescp_effect_edittext);
        mTimeTv = (TextView) view.findViewById(R.id.prescp_time_textview);
        mDateTv = (TextView) view.findViewById(R.id.prescp_date_textview);
        mNameEt = (EditText) view.findViewById(R.id.prescp_name_edittext);
        mResultIv = (ImageView) view.findViewById(R.id.prescp_imageview);

        mDateTv.setOnClickListener(mClickListener);
        mTimeTv.setOnClickListener(mClickListener);
        mResultIv.setOnClickListener(mClickListener);

        view.findViewById(R.id.prescp_save_button).setOnClickListener(mClickListener);
        view.findViewById(R.id.prescp_modify_button).setOnClickListener(mClickListener);
        view.findViewById(R.id.prescp_drug_add_button).setOnClickListener(mClickListener);

        String now_time = CDateUtil.getForamtyyyyMMddHHmm(new Date(System.currentTimeMillis()));
        java.util.Calendar cal = CDateUtil.getCalendar_yyyy_MM_dd_HH_mm(now_time);
        cal_year = cal.get(Calendar.YEAR);
        cal_month = cal.get(Calendar.MONTH);
        cal_day = cal.get(Calendar.DAY_OF_MONTH);
        cal_hour = cal.get(Calendar.HOUR_OF_DAY);
        cal_min = cal.get(Calendar.MINUTE);

        mDateTvSet(cal_year, cal_month, cal_day);
        mTimeTvSet(cal_hour, cal_min);

        // 액션바 타이틀이 있으면 환경설정에서 온것으로 판단
        if (getArguments() != null) {
            String bundleTitle = getArguments().getString(CommonActionBar.ACTION_BAR_TITLE);
            if (TextUtils.isEmpty(bundleTitle) == false) {
                mSaveBtn.setVisibility(View.GONE);
                mModifyBtn.setVisibility(View.VISIBLE);
                getData();
            }
        }



        mDrawerMenu = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void getData() {
        DBHelperDataRecipe.RecipeData data;
        DBHelper helper = new DBHelper(getContext());
        DBHelperDataRecipe db = helper.getmDataRecipe();
        String idx = getArguments().getString("IDX");
        data = db.getDataSelect(idx);
        int month = data.re_date.indexOf("일");
        mIdx = idx;

        mNameEt.setText(data.re_name);
        mDateTv.setText(data.re_date.substring(0, month + 1));
        mTimeTv.setText(data.re_date.substring(month + 1));
        mEffectEt.setText(data.re_symp);
        mHospitalNameEt.setText(data.re_hospital);
        mHowEt.setText(data.re_howtake);

        if(!data.re_img.isEmpty()){
            ViewUtil.getIndexToImageData(idx, mResultIv);
            mResultIv.setTag(data.re_img);
        }

        DBHelperRecipeDrug RDdb = helper.getRecipeDrug();
        List<DBHelperDrug.DrugData> RDdatas = RDdb.getDrugValues(idx);
        DBHelperDrugInfo DIdb = helper.getDrugInfoDb();
        if(RDdatas.size() > 0){
            for(int i = 0 ; i < RDdatas.size() ; i++){
                DBHelperDrugInfo.DrugData DDdata = DIdb.getDrugDataValue(RDdatas.get(i).drug_idx);

                LinearLayout medLayout = new LinearLayout(getContext());
                LinearLayout.LayoutParams VgLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                VgLayout.setMargins(9, 9, 9, 9);
                medLayout.setLayoutParams(VgLayout);
                medLayout.setBackground(getResources().getDrawable(R.drawable.edittext_box));
                medLayout.setPadding(24, 24, 44, 24);
                medLayout.setOrientation(LinearLayout.HORIZONTAL);
                medLayout.setGravity(Gravity.CENTER_VERTICAL);
                medLayout.setId(textId+1000);

                final TextView txt2 = new TextView(getContext());
                txt2.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
                txt2.setTextSize(15);
                txt2.setTextColor(getResources().getColor(R.color.colorBlack));
                Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), getContext().getString(R.string.KelsonSansLight));//"Kelson-Sans-Light.otf");
                txt2.setTypeface(typeface);
                txt2.setId(textId);
                txt2.setText(DDdata.drug_title);
                medLayout.addView(txt2);

                final ImageView imgView = new ImageView(getContext());
                DrawerLayout.LayoutParams Drparams = new DrawerLayout.LayoutParams(45,45);
                imgView.setLayoutParams(Drparams);
                imgView.setImageDrawable(getResources().getDrawable(R.drawable.ic_delete));
                imgView.setId(textId+2000);
                imgView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CDialog.showDlg(getContext(), getString(R.string.text_alert_mesage_delete), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                TextView textView2 = (TextView) getView().findViewById(txt2.getId());

                                if(mDrugData.size() > 0){
                                    for(int i = 0 ; i < mDrugData.size() ; i++){
                                        if(mDrugData.get(i).drug_title.equals(textView2.getText())){
                                            mDrugData.remove(i);
                                        }
                                    }
                                }

                                LinearLayout medLayout = (LinearLayout) getView().findViewById(txt2.getId()+1000);
                                mMainView.removeView(medLayout);


                                Logger.i(TAG, "삭제 완료" +txt2.getId());
                            }
                        }, null);
                    }
                });

                medLayout.addView(imgView);

                mMainView.addView(medLayout, 9);
                mDrugData.add(DDdata);

                textId ++;
            }
        }
    }

    /**
     * DB에 데이터 저장하기
     */
    private void saveData() {
        String name = mNameEt.getText().toString();
        String date = mDateTv.getText().toString();
        String time = mTimeTv.getText().toString();
        String hospital = mHospitalNameEt.getText().toString();
        String effect = mEffectEt.getText().toString();
        String how = mHowEt.getText().toString();

        if(name.length() < 1){
            CDialog.showDlg(getContext(), "처방전 이름을 입력해주세요");
            return;
        }

        if(date.length() < 1 || time.length() < 1){
            CDialog.showDlg(getContext(), "처방 일시를 입력해주세요");
            return;
        }


        DBHelperDataRecipe.RecipeData data = new DBHelperDataRecipe.RecipeData();
        data.idx = mIdx;
        data.re_name = name;
        data.re_date = date+" "+time;
        data.re_symp = effect;
        data.re_hospital = hospital;
        data.re_howtake = how;
        data.re_img = TextUtils.isEmpty(mResultIv.getTag().toString()) ? "" : mResultIv.getTag().toString();

        DBHelper helper = new DBHelper(getContext());
        DBHelperDataRecipe db = helper.getmDataRecipe();
        db.insertDb(data);

        DBHelperRecipeDrug RDdb = helper.getRecipeDrug();
        if(mDrugData.size() > 0){
            for(int i = 0 ; i < mDrugData.size() ; i ++)
                RDdb.insertDb(mIdx, mDrugData.get(i).idx);
        }

        CDialog.showDlg(getContext(), "저장 되었습니다.", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onBackPressed();
                movePage(PrescriptionManageFragment.newInstance(), null);
            }
        });
    }

    private void modifyData() {
        String name = mNameEt.getText().toString();
        String date = mDateTv.getText().toString();
        String time = mTimeTv.getText().toString();
        String hospital = mHospitalNameEt.getText().toString();
        String effect = mEffectEt.getText().toString();
        String how = mHowEt.getText().toString();

        if(name.length() < 1){
            CDialog.showDlg(getContext(), "처방전 이름을 입력해주세요");
            return;
        }

        if(date.length() < 1 || time.length() < 1){
            CDialog.showDlg(getContext(), "처방 일시를 입력해주세요");
            return;
        }

        DBHelperDataRecipe.RecipeData data = new DBHelperDataRecipe.RecipeData();
        data.idx = mIdx;
        data.re_name = name;
        data.re_date = date+" "+time;
        data.re_symp = effect;
        data.re_hospital = hospital;
        data.re_howtake = how;
        data.re_img = TextUtils.isEmpty(mResultIv.getTag().toString()) ? "" : mResultIv.getTag().toString();

        DBHelper helper = new DBHelper(getContext());
        DBHelperDataRecipe db = helper.getmDataRecipe();
        db.updateDb(data);

        DBHelperRecipeDrug RDdb = helper.getRecipeDrug();

        RDdb.DeleteDb(mIdx);

        if(mDrugData.size() > 0){
            for(int i = 0 ; i < mDrugData.size() ; i ++)
                RDdb.insertDb(mIdx, mDrugData.get(i).idx);
        }

        CDialog.showDlg(getContext(), "수정 되었습니다.", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private CDialog mPictureDlg;
    private void showSelectGalleryCamera() {
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_listview_gallery_camera, null);
        view.findViewById(R.id.select_gallery_btn).setOnClickListener(dlgClickListener);
        view.findViewById(R.id.select_camera_btn).setOnClickListener(dlgClickListener);
        mPictureDlg = CDialog.showDlg(getContext(), view);
        mPictureDlg.setTitle("사진 선택");
    }

    View.OnClickListener dlgClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int vId = v.getId();

            if (mPictureDlg != null) {
                mPictureDlg.dismiss();
            }
            if (R.id.select_gallery_btn == vId) {
                selectGallery();
            } else if (R.id.select_camera_btn == vId) {
                selectCamera();
            }
        }
    };

    private File createTempFile() {
        String fileName = mIdx + ".png";
        File file = null;
        try {
            file = new File(Define.IMAGE_SAVE_PATH);
            if (!file.exists()) {
                file.mkdirs();
                Logger.i(TAG, "createTempFile.mkdirs=" + file.getPath());
            }

            file = new File(file.getPath(), fileName);
            Logger.i(TAG, "createTempFile.getapth=" + file.getPath());
            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file;
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            int vId = v.getId();
            if (vId == R.id.sample_camera_btn) {
                selectCamera();
            } else if (vId == R.id.sample_gallery_btn) {
                selectGallery();
            } else if (vId == R.id.prescp_date_textview) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDateTv.requestFocus();

                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(mDateTv.getWindowToken(), 0);

                        showDatePicker(v);
                    }
                }, 100);

            } else if (vId == R.id.prescp_time_textview) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mTimeTv.requestFocus();

                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(mTimeTv.getWindowToken(), 0);

                        showTimePicker();
                    }
                }, 100);
            } else if (vId == R.id.prescp_imageview) {
                showSelectGalleryCamera();
            } else if (vId == R.id.prescp_save_button) {
                saveData();
            } else if(vId == R.id.prescp_modify_button) {
                modifyData();
            } else if (vId == R.id.prescp_drug_add_button){
                showDrugModifiDlg();
            }
        }
    };

    private void showTimePicker() {
        Calendar cal = Calendar.getInstance(Locale.KOREA);
        Date now = new Date();
        cal.setTime(now);

        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        String time = mTimeTv.getTag().toString();
        if (TextUtils.isEmpty(time) == false) {
            hour = StringUtil.getIntVal(time.substring(0, 2));
            minute = StringUtil.getIntVal(time.substring(2 , 4));

            Logger.i(TAG, "hour="+hour+", minute="+minute);
        }

        TimePickerDialog dialog = new TimePickerDialog(getContext(), listener, hour, minute, false);
        dialog.show();
    }

    /**
     * 시간 피커 완료
     */
    private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mTimeTvSet(hourOfDay, minute);
        }
    };

    private void mTimeTvSet(int hourOfDay, int minute){
        // 설정버튼 눌렀을 때
        String amPm = "오전";
        int hour = hourOfDay;
        if (hourOfDay > 11) {
            amPm = "오후";
            if (hourOfDay >= 13)
                hour -= 12;
        } else {
            hour = hour == 0 ? 12 : hour;
        }
        String tagMsg = String.format("%02d%02d", hourOfDay, minute);
        String timeStr = String.format("%02d:%02d", hour, minute);
        mTimeTv.setText(amPm + " " + timeStr);
        mTimeTv.setTag(tagMsg);
    }


    private void showDatePicker(View v) {
        GregorianCalendar calendar = new GregorianCalendar();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String date = mDateTv.getTag().toString();
        if (TextUtils.isEmpty(date) == false) {
            year = StringUtil.getIntVal(date.substring(0 , 4));
            month = StringUtil.getIntVal(date.substring(4 , 6))-1;
            day = StringUtil.getIntVal(date.substring(6 , 8));
        }

        new CDatePicker(getContext(), dateSetListener, year, month, day, false).show();
    }

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            mDateTvSet(year, monthOfYear, dayOfMonth);
        }

    };

    private void mDateTvSet(int year, int monthOfYear, int dayOfMonth){
        String msg = String.format("%d.%d.%d", year, monthOfYear + 1, dayOfMonth);
        String tagMsg = String.format("%d%02d%02d", year, monthOfYear + 1, dayOfMonth);
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear + 1);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        mDateTv.setText(msg+" "+ CDateUtil.getDateToWeek(tagMsg)+"요일");
        mDateTv.setTag(tagMsg);
    }

    /**
     * 갤러리 실행
     */
    private void selectGallery() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.CAMERA};
        reqPermissions(permissions, new IPermission() {
            @Override
            public void result(boolean isGranted) {
                if (isGranted)
                    selectImage();
            }
        });
    }

    private void selectCamera() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.CAMERA};
        reqPermissions(permissions, new IPermission() {
            @Override
            public void result(boolean isGranted) {
                if (isGranted) {
                    String idx = CDateUtil.getForamtyyMMddHHmmssSS(new Date(System.currentTimeMillis()));
                    try {
                        contentUri = Uri.fromFile(createTempFile());

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
                        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public File getTempFile(Context context, String url) {
        File file = null;
        try {
            String fileName = Uri.parse(url).getLastPathSegment();
            file = File.createTempFile(fileName, null, context.getCacheDir());
        } catch (IOException e) {
            // Error while creating file
        }
        return file;
    }

    /**
     * 갤러리 실행
     */
    public void selectImage() {
        //버튼 클릭시 처리로직
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_ALBUM);
    }

    /**
     * EXIF정보를 회전각도로 변환하는 메서드
     *
     * @param exifOrientation EXIF 회전각
     * @return 실제 각도
     */
    public int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    /**
     * 이미지를 회전시킵니다.
     *
     * @param bitmap 비트맵 이미지
     * @return 회전된 이미지
     */
    public Bitmap rotate(Bitmap bitmap, String path) {
        Logger.i(TAG, "rotate.path=" + path);
        // 이미지를 상황에 맞게 회전시킨다
        try {
            ExifInterface exif = new ExifInterface(path);
            int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int degrees = exifOrientationToDegrees(exifOrientation);

            Bitmap retBitmap = bitmap;

            if (degrees != 0 && bitmap != null) {
                Matrix m = new Matrix();
                m.setRotate(degrees, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);

                try {
                    Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
                    if (bitmap != converted) {
                        retBitmap = converted;
                        bitmap.recycle();
                        bitmap = null;
                    }
                } catch (OutOfMemoryError ex) {
                    // 메모리가 부족하여 회전을 시키지 못할 경우 그냥 원본을 반환합니다.
                }
            }
            return retBitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    /**
     * 사진을 저장 합니다.
     *
     * @param bitmap
     * @param path
     * @return
     */
    public String saveBitmapToFile(Bitmap bitmap, String path) {
        File tempFile = new File(path);
        try {
            FileOutputStream out = new FileOutputStream(tempFile);
            if (out != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // 넘거 받은 bitmap을 png로 저장해줌
                out.close(); // 마무리로 닫아줍니다.
            }
            Logger.i(TAG, "saveBitmapToFile.path=" + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger.i(TAG, "saveBitmapToFile=" + tempFile.getAbsolutePath());
        return tempFile.getAbsolutePath(); // 임시파일 저장경로를 리턴해주면 끝!
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_ALBUM) {
            if (data != null) {
                contentUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), contentUri);
                    contentUri = Uri.fromFile(createTempFile());
                    saveBitmapToFile(bitmap, contentUri.getPath());
                    rotate(bitmap, contentUri.getPath());
                    cropImage(contentUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            try {
                Logger.i(TAG, "contentUri=" + contentUri.toString());
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), contentUri);
                bitmap = rotate(bitmap, contentUri.getPath());
                saveBitmapToFile(bitmap, contentUri.getPath());
                cropImage(contentUri);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == REQUEST_IMAGE_CROP) {
            if (data != null) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap bitmap = (Bitmap) extras.get("data");
                    Logger.i(TAG, "REQUEST_IMAGE_CROP.contentUri");
                    mResultIv.setImageBitmap(bitmap);
//                    Toast.makeText(getContext(), "사진 저장:" + savePath(mIdx), Toast.LENGTH_SHORT).show();
                    saveBitmapToFile(bitmap, savePath(mIdx));
                }
            }
        }
    }

    public String savePath(String idx) {
        String path = Define.IMAGE_SAVE_PATH + File.separator + idx + ".png";
        mResultIv.setTag(path);
        return path;
    }

    private void cropImage(Uri contentUri) {
        if (contentUri != null) {
            File file = new File(contentUri.toString());
            Logger.i(TAG, "cropImage.exists=" + file.exists());
            Logger.i(TAG, "cropImage.isFile=" + file.isFile());
            Logger.i(TAG, "cropImage.length=" + file.length());
        }
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        // indicate image type and Uri of image
        cropIntent.setDataAndType(contentUri, "image/*");
        // set crop properties
        cropIntent.putExtra("crop", "true");
        // indicate aspect of desired crop
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        // indicate output X and Y
        cropIntent.putExtra("outputX", 250);
        cropIntent.putExtra("outputY", 250);
        // retrieve data on return
        cropIntent.putExtra("return-data", true);
        startActivityForResult(cropIntent, REQUEST_IMAGE_CROP);
    }


    private RecyclerView mRecyclerView;
    private RecyclerAdapter mRecyclerAdapter;
    private EditText drug_keyword;
    private Button search_button;
    private CDialog dlg;

    private void showDrugModifiDlg(){
        View modifiView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_drugsearch_list, null);

        RecyclerView mRecyclerView = (RecyclerView) modifiView.findViewById(R.id.druglist_recycler_view);
        drug_keyword= (EditText) modifiView.findViewById(R.id.drug_keyword_search);
        search_button = (Button) modifiView.findViewById(R.id.search_drug_button);
        TextView searchnull = (TextView) modifiView.findViewById(R.id.search_null);

        mRecyclerAdapter = new RecyclerAdapter(PrescriptionFragment.this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mRecyclerAdapter);

        if (getArguments() != null) {
            String keyword = getArguments().getString(DRUGINFO_KEYWORD);
            getData(keyword);
        }else{
            getData(drug_keyword.getText().toString());
        }

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData(drug_keyword.getText().toString());
            }
        });

        dlg = CDialog.showDlg(getContext(), modifiView);
        dlg.setTitle("약품");
    }

    private void getData(String word) {
        DBHelper helper = new DBHelper(getContext());
        DBHelperDrugInfo db = helper.getDrugInfoDb();
        List<DBHelperDrugInfo.DrugData> listData = db.getResult(word);
        mRecyclerAdapter.setData(listData);

        if (listData.size()==0){
//            CDialog.showDlg(getContext(), "일치하는 약품이 없습니다.");
        }
        EditTextUtil.hideKeyboard(getContext(), drug_keyword);
    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
        BaseFragment mFragment;

        public RecyclerAdapter(BaseFragment fragment) {
            mFragment = fragment;
        }

        List<DBHelperDrugInfo.DrugData> drugData = new ArrayList<>();

        @Override
        public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_drugsearch_list_item, parent, false);
            return new RecyclerAdapter.ViewHolder(itemView);
        }

        public void setData(List<DBHelperDrugInfo.DrugData> dataList) {
            drugData.clear();
            drugData.addAll(dataList);

            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(final RecyclerAdapter.ViewHolder holder, final int position) {
            final DBHelperDrugInfo.DrugData data = drugData.get(position);
            holder.txtTitle.setText(data.drug_title);
            holder.rowitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mDrugData.size() > 0){
                        for(int i = 0 ; i < mDrugData.size() ; i++){
                            if(mDrugData.get(i).idx.equals(data.idx)){

                                dlg.dismiss();
                                Toast.makeText(mFragment.getContext(), "이미 등록된 약품입니다", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }

                    LinearLayout medLayout = new LinearLayout(getContext());
                    LinearLayout.LayoutParams VgLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    VgLayout.setMargins(9, 9, 9, 9);
                    medLayout.setLayoutParams(VgLayout);
                    medLayout.setBackground(getResources().getDrawable(R.drawable.edittext_box));
                    medLayout.setPadding(24, 24, 44, 24);
                    medLayout.setOrientation(LinearLayout.HORIZONTAL);
                    medLayout.setGravity(Gravity.CENTER_VERTICAL);
                    medLayout.setId(textId+1000);

                    final TextView txt2 = new TextView(getContext());
                    txt2.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
                    txt2.setTextSize(15);
                    txt2.setTextColor(getResources().getColor(R.color.colorBlack));
                    Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), getContext().getString(R.string.KelsonSansLight));//"Kelson-Sans-Light.otf");
                    txt2.setTypeface(typeface);
                    txt2.setId(textId);
                    txt2.setText(data.drug_title);
                    medLayout.addView(txt2);

                    final ImageView imgView = new ImageView(getContext());
                    DrawerLayout.LayoutParams Drparams = new DrawerLayout.LayoutParams(45,45);
                    imgView.setLayoutParams(Drparams);
                    imgView.setImageDrawable(getResources().getDrawable(R.drawable.ic_delete));
                    imgView.setId(textId+2000);
                    imgView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CDialog.showDlg(getContext(), getString(R.string.text_alert_mesage_delete), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    TextView textView2 = (TextView) getView().findViewById(txt2.getId());

                                    if(mDrugData.size() > 0){
                                        for(int i = 0 ; i < mDrugData.size() ; i++){
                                            if(mDrugData.get(i).drug_title.equals(textView2.getText())){
                                                mDrugData.remove(i);
                                            }
                                        }
                                    }

                                    LinearLayout medLayout = (LinearLayout) getView().findViewById(txt2.getId()+1000);
                                    mMainView.removeView(medLayout);

                                    notifyDataSetChanged();

                                    Logger.i(TAG, "삭제 완료" +txt2.getId());
                                }
                            }, null);
                        }
                    });
                    medLayout.addView(imgView);

                    mMainView.addView(medLayout, 9);
                    mDrugData.add(data);

                    textId ++;

                    dlg.dismiss();
                    Toast.makeText(mFragment.getContext(), "등록이 되었습니다.", Toast.LENGTH_SHORT).show();

                }
            });
        }

        @Override
        public int getItemCount() {
            return drugData == null ? 0 : drugData.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView txtTitle;
            LinearLayout rowitem;

            public ViewHolder(View itemView) {
                super(itemView);
                txtTitle = (TextView) itemView.findViewById(R.id.drug_title_textview);
                rowitem= (LinearLayout) itemView.findViewById(R.id.drug_rowitem);
            }
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        super.navigationItemSelected(item);

        mDrawerMenu.closeDrawer(GravityCompat.START);
        return true;
    }
}
