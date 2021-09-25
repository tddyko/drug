package com.greencross.medigene.drug;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.greencross.medigene.R;
import com.greencross.medigene.base.BaseFragment;
import com.greencross.medigene.base.CommonActionBar;
import com.greencross.medigene.base.IBaseFragment;
import com.greencross.medigene.component.CDialog;
import com.greencross.medigene.database.DBHelper;
import com.greencross.medigene.database.DBHelperDrugInfo;
import com.greencross.medigene.util.DividerItemDecoration;
import com.greencross.medigene.util.EditTextUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrsohn on 2017. 3. 14..
 * 처방전 관리
 */

public class DrugInfoHistoryListFragment extends BaseFragment  implements IBaseFragment, NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerMenu;

    private final String TAG = DrugInfoHistoryListFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mRecyclerAdapter;
    private LinearLayout rowitem;
    private LinearLayout liNotfound;
    private EditText drug_keyword;
    private Button search_button;
    private Button historysearch;
    private Button favoritesSearch;
    private TextView searchnull;
    private String TAB_TYPE;


    public static Fragment newInstance() {
        DrugInfoHistoryListFragment fragment = new DrugInfoHistoryListFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_list, container, false);


        return view;
    }

    /**
     * 액션바 세팅
     */
    @Override
    public void loadActionbar(CommonActionBar actionBar) {
        super.loadActionbar(actionBar);
        actionBar.setActionBarTitle("약품검색");
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

        TAB_TYPE = "HISTORY";
        mRecyclerView = (RecyclerView) view.findViewById(R.id.druglist_recycler_view);
        liNotfound = (LinearLayout) view.findViewById(R.id.li_notfound);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(0);
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        drug_keyword= (EditText) view.findViewById(R.id.drug_keyword_search);
        search_button = (Button) view.findViewById(R.id.search_drug_button);
        searchnull = (TextView) view.findViewById(R.id.search_null);
        historysearch = (Button) view.findViewById(R.id.bnt_history_search);
        favoritesSearch = (Button) view.findViewById(R.id.bnt_favorites_search);

        mRecyclerAdapter = new RecyclerAdapter(DrugInfoHistoryListFragment.this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mRecyclerAdapter);

        getHistoryData();

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
        historysearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TAB_TYPE = "HISTORY";
                historysearch.setBackgroundColor(getResources().getColor(R.color.colorMain));
                historysearch.setTextColor(getResources().getColor(R.color.colorWhite));
                favoritesSearch.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                favoritesSearch.setTextColor(getResources().getColor(R.color.colorMain));

                showProgress();
                getHistoryData();
            }
        });


        favoritesSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TAB_TYPE = "FAVORITES";
                historysearch.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                historysearch.setTextColor(getResources().getColor(R.color.colorMain));
                favoritesSearch.setBackgroundColor(getResources().getColor(R.color.colorMain));
                favoritesSearch.setTextColor(getResources().getColor(R.color.colorWhite));

                showProgress();
                getFavoritesData();
            }
        });

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress();

                Bundle bundle = new Bundle();
                bundle.putString(DRUGINFO_KEYWORD, drug_keyword.getText().toString());
                movePage(DrugInfoSearchListFragment.newInstance(), bundle);
            }
        });


        mDrawerMenu = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void getHistoryData() {
        DBHelper helper = new DBHelper(getContext());
        DBHelperDrugInfo db = helper.getDrugInfoDb();
        List<DBHelperDrugInfo.DrugData> listData = db.getHistoryResult();
        mRecyclerAdapter.setData(listData);

        if (listData.size()==0){
            liNotfound.setVisibility(View.VISIBLE);
        }else{
            liNotfound.setVisibility(View.GONE);
        }
        EditTextUtil.hideKeyboard(getContext(), drug_keyword);
        hideProgress();
    }

    private void getFavoritesData() {
        DBHelper helper = new DBHelper(getContext());
        DBHelperDrugInfo db = helper.getDrugInfoDb();
        List<DBHelperDrugInfo.DrugData> listData = db.getFavoritesResult();

        mRecyclerAdapter.setData(listData);

        if (listData.size()==0){
            liNotfound.setVisibility(View.VISIBLE);
        }else{
            liNotfound.setVisibility(View.GONE);
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
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_history_list_item, parent, false);
            int height = 35;
            itemView.setMinimumHeight(height);
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

            if (data.drug_value.equals("0")){
                holder.titleIma.setImageDrawable(getResources().getDrawable(R.drawable.oval_status_red));
            }else if (data.drug_value.equals("1")){
                holder.titleIma.setImageDrawable(getResources().getDrawable(R.drawable.oval_status_orange));
            }else if (data.drug_value.equals("2")){
                holder.titleIma.setImageDrawable(getResources().getDrawable(R.drawable.oval_status_green));
            }else if (data.drug_value.equals("3")){
                holder.titleIma.setImageDrawable(getResources().getDrawable(R.drawable.oval_status_gray));
            }

            holder.txtTitle.setText(data.drug_title);
            holder.rowitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(DRUGINFO_IDX, data.idx);
                    movePage(DrugInfoDetailFragment.newInstance(), bundle);

                }
            });
            holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String message = getContext().getString(R.string.text_alert_mesage_delete);
                    CDialog.showDlg(getContext(), message, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showProgress();
                            Bundle bundle = new Bundle();
                            bundle.putString(DRUGINFO_IDX, data.idx);

                            DBHelper helper = new DBHelper(getContext());
                            DBHelperDrugInfo db = helper.getDrugInfoDb();

                            if (TAB_TYPE.equals("HISTORY")) {
                                db.DeleteHistoryDb(data.idx);
                                getHistoryData();
                            }else {
                                db.DeleteFavoritesDb(Integer.getInteger(data.idx));
                                getFavoritesData();
                            }
                            hideProgress();
                        }
                    }, null);
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
            ImageView deleteBtn;
            ImageView titleIma;

            public ViewHolder(View itemView) {
                super(itemView);
                txtTitle = (TextView) itemView.findViewById(R.id.history_title_textview);
                rowitem= (LinearLayout) itemView.findViewById(R.id.history_rowitem);
                deleteBtn = (ImageView) itemView.findViewById(R.id.history_delete_btn);
                titleIma = (ImageView) itemView.findViewById(R.id.history_title_imageview);
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
