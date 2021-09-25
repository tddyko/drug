package com.greencross.medigene.prescription;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.greencross.medigene.R;
import com.greencross.medigene.base.BaseFragment;
import com.greencross.medigene.base.DummyActivity;
import com.greencross.medigene.component.CDialog;
import com.greencross.medigene.database.DBHelper;
import com.greencross.medigene.database.DBHelperDataRecipe;
import com.greencross.medigene.database.DBHelperRecipeDrug;

import java.util.ArrayList;
import java.util.List;

/**
 * 처방전 추가 다이얼로그
 */
public class PrescriptionDialog {

    private RecyclerAdapter mRecyclerAdapter;
    private BaseFragment mBaseFragment;
    private CDialog mdlg;
    private String mDrugIdx;
    private LinearLayout liNotfound;

    public PrescriptionDialog(BaseFragment fragment, String DrugIdx) {
        mBaseFragment = fragment;
        mDrugIdx = DrugIdx;
        showPrescriptionDialog(fragment);
    }
    /**
     * 처방전 추가 다이얼로그
     */
    private void showPrescriptionDialog(final BaseFragment fragment) {
        final View view = LayoutInflater.from(fragment.getContext()).inflate(R.layout.dialog_prescription_view, null);
        mdlg = CDialog.showDlg(fragment.getContext(), view);
        mdlg.setTitle("처방전에 추가");

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.dlalog_prescription_recycler_view);


        mRecyclerAdapter = new RecyclerAdapter(fragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(fragment.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mRecyclerAdapter);
        liNotfound = (LinearLayout) view.findViewById(R.id.li_notfound);


        View.OnClickListener dlgClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vId = v.getId();

                if (R.id.dialog_add_prescription_btn == vId) {
                    // 처방전에 추가 버튼
                    Bundle bundle = new Bundle();
                    BaseFragment visibleFragment = (BaseFragment) fragment.getVisibleFragment();
                    DummyActivity.startActivityForResult(visibleFragment, 1111, PrescriptionFragment.class, bundle);
                }else if(R.id.dialog_close_prescription == vId){
                    mdlg.dismiss();
                }
            }
        };

        view.findViewById(R.id.dialog_add_prescription_btn).setOnClickListener(dlgClickListener);
        view.findViewById(R.id.dialog_close_prescription).setOnClickListener(dlgClickListener);
    }

    public void setData(List<DBHelperDataRecipe.RecipeData> dataList) {
        if(dataList.size() > 0){
            mRecyclerAdapter.setData(dataList);
        }
        if (dataList.size()==0) {
            liNotfound.setVisibility(View.VISIBLE);
        }else{
            liNotfound.setVisibility(View.GONE);
        }
    }

    public void getSetData(){
        // 처방전 추가 다이얼로그
        DBHelper helper = new DBHelper(mBaseFragment.getContext());
        DBHelperDataRecipe db = helper.getmDataRecipe();
        List<DBHelperDataRecipe.RecipeData> listData = db.getResult();
        mRecyclerAdapter.setData(listData);

        if (listData.size()==0) {
            liNotfound.setVisibility(View.VISIBLE);
        }else{
            liNotfound.setVisibility(View.GONE);
        }
    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
        BaseFragment mFragment;
        List<DBHelperDataRecipe.RecipeData> prescriptData = new ArrayList<>();

        public RecyclerAdapter(BaseFragment fragment) {
            mFragment = fragment;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_prescription_item, parent, false);
            return new ViewHolder(itemView);
        }

        public void setData(List<DBHelperDataRecipe.RecipeData> dataList) {
            prescriptData.clear();
            prescriptData.addAll(dataList);

            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final DBHelperDataRecipe.RecipeData msg = prescriptData.get(position);
            holder.nameTextView.setText(msg.re_name);
            holder.nameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DBHelper helper = new DBHelper(mFragment.getContext());
                    DBHelperRecipeDrug db = helper.getRecipeDrug();
                    DBHelperRecipeDrug.RecipeDrugData data = new DBHelperRecipeDrug.RecipeDrugData();
                    data.recipe_idx = msg.idx;
                    data.drug_idx = mDrugIdx;
                    String dbDataCount = db.getDataWriteCheck(data);

                    if(dbDataCount.equals("0")){
                        db.insertDb(data.recipe_idx, data.drug_idx);
                        Toast.makeText(mFragment.getContext(), "등록 되었습니다.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(mFragment.getContext(), "이미 처방전에 등록 되어있습니다.", Toast.LENGTH_SHORT).show();
                    }
                    mdlg.dismiss();
                }
            });
        }

        @Override
        public int getItemCount() {
            if(prescriptData == null || prescriptData.size() < 1){
                return  0;
            }else{
                return prescriptData.size();
            }
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView nameTextView;
            public ViewHolder(View itemView) {
                super(itemView);
                nameTextView = (TextView) itemView.findViewById(R.id.dialog_add_prescription_textview);

            }
        }
    }
}
