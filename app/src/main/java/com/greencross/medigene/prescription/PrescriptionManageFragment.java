package com.greencross.medigene.prescription;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.greencross.medigene.R;
import com.greencross.medigene.base.BaseFragment;
import com.greencross.medigene.base.CommonActionBar;
import com.greencross.medigene.base.IBaseFragment;
import com.greencross.medigene.component.CDialog;
import com.greencross.medigene.component.swipeListview.SwipeMenu;
import com.greencross.medigene.component.swipeListview.SwipeMenuCreator;
import com.greencross.medigene.component.swipeListview.SwipeMenuItem;
import com.greencross.medigene.component.swipeListview.SwipeMenuListView;
import com.greencross.medigene.database.DBHelper;
import com.greencross.medigene.database.DBHelperDataRecipe;
import com.greencross.medigene.database.DBHelperRecipeDrug;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrsohn on 2017. 3. 14..
 * 처방전 관리
 */

public class PrescriptionManageFragment extends BaseFragment implements IBaseFragment, NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mDrawerMenu;

    private final String TAG = PrescriptionManageFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private LinearLayout liNotfound;
    List<DBHelperDataRecipe.RecipeData> prescriptData = new ArrayList<>();
    private AppAdapter mAdapter;

    public static Fragment newInstance() {
        PrescriptionManageFragment fragment = new PrescriptionManageFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_prescription, container, false);
        return view;
    }

    /**
     * 액션바 세팅
     */
    @Override
    public void loadActionbar(CommonActionBar actionBar) {
        super.loadActionbar(actionBar);
        actionBar.setActionBarTitle("처방전 관리");
        actionBar.setActionBackBtnVisible(View.INVISIBLE);
        actionBar.setActionBarWriteBtn(PrescriptionFragment.class, new Bundle());     // 입력 화면
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

        SwipeMenuListView listView = (SwipeMenuListView) view.findViewById(R.id.manage_prescription_recycler_view);
        liNotfound = (LinearLayout) view.findViewById(R.id.li_notfound);


        mAdapter = new AppAdapter();
        listView.setAdapter(mAdapter);

        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                createMenu1(menu);
            }

            private void createMenu1(SwipeMenu menu) {
                SwipeMenuItem item1 = new SwipeMenuItem(getContext());
                item1.setBackground(new ColorDrawable(Color.BLACK));//new ColorDrawable(Color.rgb(0xE5, 0x18, 0x5E)));
                item1.setWidth(dp2px(60));
                item1.setIcon(R.drawable.btn_swipe_edit);
                menu.addMenuItem(item1);

                SwipeMenuItem item2 = new SwipeMenuItem(getContext());
                item2.setBackground((new ColorDrawable(Color.RED)));//(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
                item2.setWidth(dp2px(60));
                item2.setIcon(R.drawable.btn_swipe_del);
                menu.addMenuItem(item2);
            }

        };
        // set creator
        listView.setMenuCreator(creator);

        // step 2. listener item click event
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {

                DBHelperDataRecipe.RecipeData data = (DBHelperDataRecipe.RecipeData) mAdapter.getItem(position);

                switch (index) {
                    case 0:
                        Bundle bundle = new Bundle();
                        bundle.putString("IDX", data.idx);
                        bundle.putString(CommonActionBar.ACTION_BAR_TITLE, "처방전 수정");
                        movePage(PrescriptionFragment.newInstance(), bundle);

                        break;
                    case 1:

                        //처방전 테이블
                        DBHelper helper = new DBHelper(getContext());
                        DBHelperDataRecipe db = helper.getmDataRecipe();
                        db.DeleteDb(data.idx);

                        // 처방전 약 조인 테이블
                        DBHelperRecipeDrug RDdb = helper.getRecipeDrug();
                        RDdb.DeleteDb(data.idx);

                        prescriptData.remove(position);

                        mAdapter.notifyDataSetChanged();
                        CDialog.showDlg(getContext(), "삭제 되었습니다.");
                        break;
                }

                // false:Swipe 닫힘, true:Swipe안닫힘
                return false;
            }
        });


        listView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });


        mDrawerMenu = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        getData();
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getContext().getResources().getDisplayMetrics());
    }

    private void getData() {
        prescriptData.clear();

        DBHelper helper = new DBHelper(getContext());
        DBHelperDataRecipe db = helper.getmDataRecipe();
        List<DBHelperDataRecipe.RecipeData> listData = db.getResult();
        prescriptData.addAll(listData);
        mAdapter.notifyDataSetChanged();

        if (prescriptData.size()==0) {
            liNotfound.setVisibility(View.VISIBLE);
        }else{
            liNotfound.setVisibility(View.GONE);
        }
    }

    class AppAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return prescriptData.size();
        }

        @Override
        public Object getItem(int position) {
            return prescriptData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            // menu type count
            return 3;
        }

        @Override
        public int getItemViewType(int position) {
            // current menu type
            return position % 3;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.fragment_manage_prescription_item, null);
                new ViewHolder(convertView);
            }

            ViewHolder holder = (ViewHolder) convertView.getTag();
            final DBHelperDataRecipe.RecipeData data = (DBHelperDataRecipe.RecipeData) getItem(position);

            holder.nameTv.setText(data.re_name);
            int strindex = data.re_date.indexOf("요");
            holder.dateTv.setText(data.re_date.substring(0, strindex - 1));
            convertView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Bundle bundle = new Bundle();
                   bundle.putString("IDX", data.idx);
                   movePage(PrescriptionDetailFragment.newInstance(), bundle);
               }
            });

            return convertView;
        }

        class ViewHolder {
            TextView nameTv;
            TextView dateTv;

            public ViewHolder(View view) {
                nameTv = (TextView) view.findViewById(R.id.prescript_name_textview);
                dateTv = (TextView) view.findViewById(R.id.prescript_date_textview);

                view.setTag(this);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        super.navigationItemSelected(item);

        mDrawerMenu.closeDrawer(GravityCompat.START);
        return true;
    }

}
