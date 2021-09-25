package com.greencross.medigene.drug;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.greencross.medigene.R;
import com.greencross.medigene.base.BaseFragment;
import com.greencross.medigene.base.CommonActionBar;
import com.greencross.medigene.component.CDialog;
import com.greencross.medigene.database.DBHelper;
import com.greencross.medigene.database.DBHelperDrugInfo;
import com.greencross.medigene.util.EditTextUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrsohn on 2017. 3. 14..
 * 처방전 관리
 */

public class DrugInfoSearchListFragment extends BaseFragment {
    private final String TAG = DrugInfoSearchListFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private LinearLayout noSearch;
    private RecyclerAdapter mRecyclerAdapter;
    private LinearLayout rowitem;
    private EditText drug_keyword;
    private Button search_button;
    private TextView searchnull;

    public static Fragment newInstance() {
        DrugInfoSearchListFragment fragment = new DrugInfoSearchListFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drugsearch_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.druglist_recycler_view);
        noSearch = (LinearLayout) view.findViewById(R.id.nosearch_lin);
        drug_keyword= (EditText) view.findViewById(R.id.drug_keyword_search);
        search_button = (Button) view.findViewById(R.id.search_drug_button);
        searchnull = (TextView) view.findViewById(R.id.search_null);

        mRecyclerAdapter = new RecyclerAdapter(DrugInfoSearchListFragment.this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mRecyclerAdapter);


        drug_keyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    showProgress();

                    Bundle bundle = new Bundle();
                    bundle.putString(DRUGINFO_KEYWORD, drug_keyword.getText().toString());
                    movePage(DrugInfoSearchListFragment.newInstance(), bundle);
                    return true;
                }
                return false;
            }
        });
        return view;
    }

    /**
     * 액션바 세팅
     */
    @Override
    public void loadActionbar(CommonActionBar actionBar) {
        super.loadActionbar(actionBar);
        actionBar.setActionBarTitle("검색결과");
    }


    @Override
    public void onResume() {
        super.onResume();

        if (!TextUtils.isEmpty(drug_keyword.getText())){
            getData(drug_keyword.getText().toString());
        }else{
            if (getArguments() != null) {
                String keyword = getArguments().getString(DRUGINFO_KEYWORD);
                drug_keyword.setText(keyword);
                getData(keyword);
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData(drug_keyword.getText().toString());
            }
        });
    }

    private void getData(String word) {
        showProgress();
        DBHelper helper = new DBHelper(getContext());
        DBHelperDrugInfo db = helper.getDrugInfoDb();
        List<DBHelperDrugInfo.DrugData> listData = db.getResult(word);
        mRecyclerAdapter.setData(listData);

        if (listData.size()==0){
            CDialog.showDlg(getContext(), "일치하는 약품이 없습니다.");
            noSearch.setVisibility(View.VISIBLE);
//            searchnull.setVisibility(View.GONE);
        } else {
            noSearch.setVisibility(View.GONE);
        }
        EditTextUtil.hideKeyboard(getContext(), drug_keyword);
        hideProgress();
    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
        BaseFragment mFragment;

        public RecyclerAdapter(BaseFragment fragment) {
            mFragment = fragment;
        }

        List<DBHelperDrugInfo.DrugData> drugData = new ArrayList<>();

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_drugsearch_list_item, parent, false);
            return new ViewHolder(itemView);
        }

        public void setData(List<DBHelperDrugInfo.DrugData> dataList) {
            drugData.clear();
            drugData.addAll(dataList);

            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final DBHelperDrugInfo.DrugData data = drugData.get(position);
            holder.txtTitle.setText(data.drug_title);
            holder.rowitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(DRUGINFO_IDX, data.idx);
//                    bundle.putString(CommonActionBar.ACTION_BAR_TITLE, "건강정보");
                    movePage(DrugInfoDetailFragment.newInstance(), bundle);


//                    DBHelper helper = new DBHelper(getContext());
//                    DBHelperDataRecipe db = helper.getmDataRecipe();
//                    db.DeleteDb(data.idx);
//                    getData();
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
//                deleteBtn = (Button) itemView.findViewById(R.id.prescript_delete_btn);
            }
        }
    }

}
