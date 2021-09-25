package com.greencross.medigene.healthinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.greencross.medigene.R;
import com.greencross.medigene.WebViewFragment;
import com.greencross.medigene.base.BaseFragment;
import com.greencross.medigene.base.CommonActionBar;
import com.greencross.medigene.base.IBaseFragment;
import com.greencross.medigene.database.DBHelper;
import com.greencross.medigene.database.DBHelperHealth;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrsohn on 2017. 3. 14..
 * 처방전 관리
 */

public class HealthInfoFragment extends BaseFragment implements IBaseFragment, NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerMenu;
    private final String TAG = HealthInfoFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mRecyclerAdapter;
    private LinearLayout rowitem;

    public static Fragment newInstance() {
        HealthInfoFragment fragment = new HealthInfoFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_healthinfo_list, container, false);
        return view;
    }

    /**
     * 액션바 세팅
     */
    @Override
    public void loadActionbar(CommonActionBar actionBar) {
        super.loadActionbar(actionBar);
        actionBar.setActionBarTitle("건강정보");
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

        mRecyclerView = (RecyclerView) view.findViewById(R.id.healthinfo_recycler_view);

        mRecyclerAdapter = new RecyclerAdapter(HealthInfoFragment.this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mRecyclerAdapter);

        mDrawerMenu = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        getData();
    }

    private void getData() {
        DBHelper helper = new DBHelper(getContext());
        DBHelperHealth db = helper.getHealthInfo();
        List<DBHelperHealth.HealthData> listData = db.getResult();
        mRecyclerAdapter.setData(listData);
    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
        BaseFragment mFragment;

        public RecyclerAdapter(BaseFragment fragment) {
            mFragment = fragment;
        }

        List<DBHelperHealth.HealthData> healthData = new ArrayList<>();

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_healthinfo_list_item, parent, false);
            return new ViewHolder(itemView);
        }

        public void setData(List<DBHelperHealth.HealthData> dataList) {
            healthData.clear();
            healthData.addAll(dataList);

            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final DBHelperHealth.HealthData data = healthData.get(position);
            holder.txtTitle.setText(data.he_title);
            holder.txtDate .setText(data.regdate);
            holder.rowitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(PARAMS_ACTION_URL, data.he_url);
                    bundle.putString(CommonActionBar.ACTION_BAR_TITLE, "건강정보");
                    movePage(WebViewFragment.newInstance(), bundle);
//                    DBHelper helper = new DBHelper(getContext());
//                    DBHelperDataRecipe db = helper.getmDataRecipe();
//                    db.DeleteDb(data.idx);
//                    getData();
                }
            });
        }

        @Override
        public int getItemCount() {
            return healthData == null ? 0 : healthData.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView txtTitle;
            TextView txtDate;
            LinearLayout rowitem;

            public ViewHolder(View itemView) {
                super(itemView);
                txtTitle = (TextView) itemView.findViewById(R.id.health_title_textview);
                txtDate = (TextView) itemView.findViewById(R.id.health_date_textview);
                rowitem= (LinearLayout) itemView.findViewById(R.id.health_rowitem);
//                deleteBtn = (Button) itemView.findViewById(R.id.prescript_delete_btn);
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
