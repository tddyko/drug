package com.greencross.medigene.apply;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.greencross.medigene.R;
import com.greencross.medigene.base.BaseFragment;
import com.greencross.medigene.base.CommonActionBar;
import com.greencross.medigene.component.CDialog;
import com.greencross.medigene.database.DBHelper;
import com.greencross.medigene.database.DBHelperHospital;
import com.greencross.medigene.util.Logger;
import com.greencross.medigene.util.SharedPref;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by godaewon on 2017. 7. 4..
 */

public class CheckApplyListFragment extends BaseFragment{

    private RecyclerView mRecyclerView;
    private RecyclerAdapter mRecyclerAdapter;
    private LinearLayout mLiTop;
    private RelativeLayout mLiNotFound;
    private TextView mLiNotFoundTV;
    private Button mApplyBtn;

    public static Fragment newInstance() {
        CheckApplyListFragment fragment = new CheckApplyListFragment();
        return fragment;
    }


    @Override
    public void loadActionbar(CommonActionBar actionBar) {
        super.loadActionbar(actionBar);
        actionBar.setActionBarTitle("검사 신청");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check_apply_list, null);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.check_apply_recyclerview);
        mLiNotFound = (RelativeLayout) view.findViewById(R.id.li_notfound);
        mLiTop = (LinearLayout) view.findViewById(R.id.li_check_apply_list_top);
        mLiNotFoundTV = (TextView) view.findViewById(R.id.li_notfound_textview);
        mApplyBtn = (Button) view.findViewById(R.id.check_apply_btn);


        mRecyclerAdapter = new RecyclerAdapter(CheckApplyListFragment.this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mRecyclerAdapter);

        view.findViewById(R.id.check_apply_btn).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                CDialog.showDlg(getContext(), "   검사 신청을 위해\n     헬스콜센터로\n전화 연결하시겠습니까?", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPref.getInstance().savePreferences(SharedPref.IS_DRUG_REQUEST, true);
                        String tel = "tel:0220409100";
                        startActivity(new Intent("android.intent.action.DIAL", Uri.parse(tel)));
                    }
                }, null);
            }
        });

        getData(getArguments().getString("name"));

        return view;
    }

    private void getData(String area) {
        showProgress();

        DBHelper helper = new DBHelper(getContext());
        DBHelperHospital db = helper.getHospital();
        List<DBHelperHospital.HospitalData> listData = db.getAreaResult(area);
        mRecyclerAdapter.setData(listData);

        if(listData.size() > 0){
            mLiTop.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mApplyBtn.setVisibility(View.VISIBLE);
            mLiNotFound.setVisibility(View.GONE);
        }else{
            mLiTop.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
            mApplyBtn.setVisibility(View.GONE);
            mLiNotFound.setVisibility(View.VISIBLE);
            mLiNotFoundTV.setText(area + "에 조회된 데이터가 없습니다.");
        }

        hideProgress();
    }


    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
        BaseFragment mFragment;

        public RecyclerAdapter(BaseFragment fragment) {
            mFragment = fragment;
        }

        List<DBHelperHospital.HospitalData> hospitalDatas = new ArrayList<>();

        @Override
        public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_check_apply_row, parent, false);
            return new RecyclerAdapter.ViewHolder(itemView);
        }

        public void setData(List<DBHelperHospital.HospitalData> dataList) {
            hospitalDatas.clear();
            hospitalDatas.addAll(dataList);

            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(final RecyclerAdapter.ViewHolder holder, final int position) {

            DBHelperHospital.HospitalData data = hospitalDatas.get(position);
            if(position == 0){
                holder.li_Top.setVisibility(View.VISIBLE);
                holder.top_title.setText(data.area);
            }else{
                holder.li_Top.setVisibility(View.GONE);
            }

            holder.apply_title.setText(data.name);
            holder.apply_address.setText(data.address);
            holder.apply_call.setText(data.tel);
        }

        @Override
        public int getItemCount() {
            return hospitalDatas == null ? 0 : hospitalDatas.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            LinearLayout li_Top;
            TextView top_title;
            TextView apply_title;
            TextView apply_address;
            TextView apply_call;

            public ViewHolder(View itemView) {
                super(itemView);
                li_Top = (LinearLayout) itemView.findViewById(R.id.top1);
                top_title = (TextView) itemView.findViewById(R.id.local_title);
                apply_title = (TextView) itemView.findViewById(R.id.check_apply_title);
                apply_address = (TextView) itemView.findViewById(R.id.check_apply_address);
                apply_call = (TextView) itemView.findViewById(R.id.check_apply_call);
            }
        }
    }
}
