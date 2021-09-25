package com.greencross.medigene.prescription;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.greencross.medigene.R;
import com.greencross.medigene.base.BaseFragment;
import com.greencross.medigene.base.CommonActionBar;
import com.greencross.medigene.base.value.Define;
import com.greencross.medigene.database.DBHelper;
import com.greencross.medigene.database.DBHelperDataRecipe;
import com.greencross.medigene.database.DBHelperDrug;
import com.greencross.medigene.database.DBHelperDrugInfo;
import com.greencross.medigene.database.DBHelperRecipeDrug;
import com.greencross.medigene.util.ViewUtil;

import java.io.File;
import java.util.List;

/**
 * Created by godaewon on 2017. 6. 29..
 */

public class PrescriptionDetailFragment extends BaseFragment {

    private final String TAG = PrescriptionDetailFragment.class.getSimpleName();

    DBHelperDataRecipe.RecipeData mData;
    private LinearLayout mMainLinearVIew;
    private ImageView mImgView;
    private ImageView photoView;

    public static PrescriptionDetailFragment newInstance() {
        PrescriptionDetailFragment fragment = new PrescriptionDetailFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_precription_detail, container, false);
        return view;
    }

    /**
     * 액션바 세팅
     */
    @Override
    public void loadActionbar(CommonActionBar actionBar) {
        super.loadActionbar(actionBar);
        actionBar.setActionBarTitle("처방전 추가");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String idx = "";
        DBHelper helper = new DBHelper(getContext());
        if(getArguments() != null){
            idx = getArguments().getString("IDX");
            DBHelperDataRecipe db = helper.getmDataRecipe();
            mData = db.getDataSelect(idx);
        }

        mMainLinearVIew = (LinearLayout) view.findViewById(R.id.preciption_detail_mainview);
        TextView mDate = (TextView) view.findViewById(R.id.preciption_detail_date);
        TextView mEffect = (TextView) view.findViewById(R.id.preciption_detail_effect);
        TextView mHospital = (TextView) view.findViewById(R.id.preciption_detail_hospital);
        TextView mHow = (TextView) view.findViewById(R.id.preciption_detail_how);
        mImgView = (ImageView) view.findViewById(R.id.preciption_detail_image);
//        photoView = (ImageView) view.findViewById(R.id.imageView);

        mDate.setText(mData.re_date);
        mEffect.setText(mData.re_symp);
        mHospital.setText(mData.re_hospital);
        mHow.setText(mData.re_howtake);
        ViewUtil.getIndexToImageData(mData.idx, mImgView);

        mImgView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                File file = new File("/sdcard/sun.jpg");
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setDataAndType(Uri.parse("file://" + Define.getFoodPhotoPath(mData.idx)), "image/*");
                startActivity(i);
            }
        });

        DBHelperRecipeDrug RDdb = helper.getRecipeDrug();
        List<DBHelperDrug.DrugData> RDdatas = RDdb.getDrugValues(idx);
        if(RDdatas.size() > 0){
            getDrugDraw(RDdatas);
        }
    }

    private void getDrugDraw(List<DBHelperDrug.DrugData> datas){
        DBHelper helper = new DBHelper(getContext());
        DBHelperDrugInfo DIdb = helper.getDrugInfoDb();
        for(int i = 0 ; i < datas.size() ; i++){
            DBHelperDrugInfo.DrugData DDdata = DIdb.getDrugDataValue(datas.get(i).drug_idx);

            LinearLayout mDrugLayout = new LinearLayout(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(45, 15, 0 ,0);
            mDrugLayout.setLayoutParams(params);
            mDrugLayout.setOrientation(LinearLayout.HORIZONTAL);
            mDrugLayout.setGravity(Gravity.CENTER_VERTICAL);

            ImageView emtpyImg = new ImageView(getContext());
            emtpyImg.setLayoutParams(new DrawerLayout.LayoutParams(45, 45));
            mDrugLayout.addView(emtpyImg, 0);

            TextView nameText = new TextView(getContext());
            LinearLayout.LayoutParams Drparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            Drparams.setMargins(45, 0 , 0, 0);
            nameText.setLayoutParams(Drparams);
            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), getContext().getString(R.string.KelsonSansLight));//"Kelson-Sans-Light.otf");
            nameText.setTypeface(typeface);
            nameText.setTextColor(getResources().getColor(R.color.colorBlack));
            nameText.setTextSize(13.f);
            nameText.setText(DDdata.drug_title);
            mDrugLayout.addView(nameText, 1);

            ImageView iconText = new ImageView(getContext());
            LinearLayout.LayoutParams LLparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LLparams.setMargins(15, 0 , 0, 0);
            iconText.setLayoutParams(LLparams);
            if (DDdata.drug_value.equals("0")){
                iconText.setImageDrawable(getResources().getDrawable(R.drawable.oval_status_red));
            }else if (DDdata.drug_value.equals("1")){
                iconText.setImageDrawable(getResources().getDrawable(R.drawable.oval_status_orange));
            }else if (DDdata.drug_value.equals("2")){
                iconText.setImageDrawable(getResources().getDrawable(R.drawable.oval_status_green));
            }else if (DDdata.drug_value.equals("3")){
                iconText.setImageDrawable(getResources().getDrawable(R.drawable.oval_status_gray));
            }

            mDrugLayout.addView(iconText, 2);

            mMainLinearVIew.addView(mDrugLayout, 7);
        }
    }


}
