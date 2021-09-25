package com.greencross.medigene.alerm;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.greencross.medigene.R;
import com.greencross.medigene.base.BaseFragment;
import com.greencross.medigene.base.CommonActionBar;
import com.greencross.medigene.base.IBaseFragment;
import com.greencross.medigene.component.CDialog;
import com.greencross.medigene.util.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 예약현황, 서비스신청현황 메인 화면
 */
public class DrugAlarmFragment extends BaseFragment implements IBaseFragment, NavigationView.OnNavigationItemSelectedListener  {

    private DrawerLayout mDrawerMenu;
    private FragmentActivity fac;

//	private View footer;

    Button imagebutton;


    private MyCursorAdapter adapter;
    private ListView list;
    private LinearLayout noalarm;
    private DBAdapter db;
    private Cursor currentCursor;
    //
    private static int colID;
    private static int colONOFF;
    private static int colHOUR;
    private static int colMINUTE;
    private static int colDAY;
    private static int colRING;
    private static int colVIB;


    public static Fragment newInstance() {
        DrugAlarmFragment fragment = new DrugAlarmFragment();
        return fragment;
    }

    @Override
    public void loadActionbar(CommonActionBar actionBar) {
        super.loadActionbar(actionBar);
        actionBar.setActionBarTitle(getString(R.string.drug_main));
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alarm_list_fragment, container, false);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fac = getActivity();
        Utility.returnSetAlarm = new ReturnSetAlarm();

        initFrame(view);

        mDrawerMenu = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    private void initFrame(View view) {


        // 알람
        db = new DBAdapter(fac);
        db.open();

        currentCursor = db.fetchAllAlarm();
        noalarm = (LinearLayout) view.findViewById(R.id.noalarm_lin);
        list = (ListView) view.findViewById(R.id.alarmListView);
        list.setOnItemClickListener(itemClickListener);
        list.setOnTouchListener(TouchListener);
        String[] from = new String[]{DBAdapter.ALARM_HOUR, DBAdapter.ALARM_MINUTE};
        int[] to = new int[]{R.id.alarm_row_time, R.id.alarm_row_day, R.id.alarm_row_title};

        adapter = new MyCursorAdapter(list.getContext(), R.layout.alarm_row, currentCursor, from, to);
        list.setAdapter(adapter);
        resetAlarmList();
        // column index
        colID = currentCursor.getColumnIndex("_id");
        colONOFF = currentCursor.getColumnIndex(DBAdapter.ALARM_ON);
        colDAY = currentCursor.getColumnIndex(DBAdapter.ALARM_APDAY);
        colHOUR = currentCursor.getColumnIndex(DBAdapter.ALARM_HOUR);
        colMINUTE = currentCursor.getColumnIndex(DBAdapter.ALARM_MINUTE);
        colRING = currentCursor.getColumnIndex(DBAdapter.ALARM_RINGTONE);
        colVIB = currentCursor.getColumnIndex(DBAdapter.ALARM_VIBRATE);
        if (Utils.TEST) {

            Log.d("hsh", " count " + currentCursor.getCount());
            if (currentCursor.getCount() > 0) {
                currentCursor.moveToFirst();
                do {

                    Log.d("hsh", "-----------------------");
                    Log.d("hsh", "colID " + currentCursor.getInt(0));
                    Log.d("hsh", "colONOFF " + currentCursor.getInt(1));
                    Log.d("hsh", "colDAY " + currentCursor.getInt(2));
                    Log.d("hsh", "colTime " + getTimeString(currentCursor.getInt(3), currentCursor.getInt(4)));

                    Log.d("hsh", "colRING " + currentCursor.getString(6));

                } while (currentCursor.moveToNext());
            }
        }

        imagebutton = (Button) view.findViewById(R.id.btn_add);
        imagebutton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("hsh", " count " + currentCursor.getCount());
                if (currentCursor.getCount() >= 10) {
                    Toast.makeText(fac, "알람은 10개까지만 설정 할 수 있습니다.", Toast.LENGTH_SHORT).show();
                } else {
//					AppManageHelper.addTabMenu(fac, TAB_MENU.MAIN, alarmSet.class, "복약 알람 설정", true, null);

                    movePage(DrugAlarmInputFragment.newInstance());
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Utils.TEST) {
            Log.d("hsh", "DrugAlarmFragment onResume ");
        }
        resetAlarmList();
    }

    public class ReturnSetAlarm {
        public void resetList() {
            resetAlarmList();
        }
    }

    private void resetAlarmList() {
        currentCursor = db.fetchAllAlarm();
        adapter.notifyDataSetChanged();
        if (currentCursor.getCount() > 0) {
            list.setVisibility(View.VISIBLE);
            noalarm.setVisibility(View.GONE);
        } else {
            list.setVisibility(View.GONE);
            noalarm.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private static int QuickMenuEvent = 0;
    private static float CheckedColumn_x = 0;

    OnTouchListener TouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            // 여기서 view 는 ListItem 이 아닌  리스트 자체임
            CheckedColumn_x = event.getX();
            //CheckedColumn_y = event.getY();
            QuickMenuEvent = event.getAction();

            return false;
        }
    };

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> list, View view, int position, long id) {
            currentCursor.moveToPosition(position);
            ImageView icon_view = (ImageView) view.findViewById(R.id.toggleButton1);
            ImageView delete_view = (ImageView) view.findViewById(R.id.alarm_delete);

            if (QuickMenuEvent == MotionEvent.ACTION_UP) {
                long db_id = currentCursor.getLong(colID);

                Bundle bundle = new Bundle();
                bundle.putLong("id", db_id);
                bundle.putInt("day", currentCursor.getInt(colDAY));
                bundle.putInt("hour", currentCursor.getInt(colHOUR));
                bundle.putInt("min", currentCursor.getInt(colMINUTE));
                bundle.putString("ring", currentCursor.getString(colRING));
                bundle.putInt("vib", currentCursor.getInt(colVIB));

                movePage(DrugAlarmInputFragment.newInstance(), bundle);
//					AppManageHelper.addTabMenu(fac, TAB_MENU.MAIN, DrugAlarmInputFragment.class, getString(R.string.drug_main_detail), true, arg);
            }
        }
    };

    ////////////////////////////////////////////////////////////////////////////////////
    //list adapter
    ////////////////////////////////////////////////////////////////////////////////////
    private class MyCursorAdapter extends SimpleCursorAdapter {
        Context my_context;
        private int mRowLayout;

        MyCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
            super(context, layout, c, from, to);
            my_context = context;
            mRowLayout = layout;
        }

        @Override
        public int getCount() {
            return currentCursor.getCount();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            currentCursor.moveToPosition(position); ///////////////
            final ViewHolder viewHolder;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) my_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(mRowLayout, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.icon = (ImageView) convertView.findViewById(R.id.toggleButton1);
                viewHolder.delete = (ImageView) convertView.findViewById(R.id.alarm_delete);
                viewHolder.time = (TextView) convertView.findViewById(R.id.alarm_row_time);
                viewHolder.day = (TextView) convertView.findViewById(R.id.alarm_row_day);
                viewHolder.title = (TextView) convertView.findViewById(R.id.alarm_row_title);

                viewHolder.icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        long db_id = currentCursor.getLong(colID);
                        int on = currentCursor.getInt(colONOFF);

                        if (on == 0) on = 1;
                        else on = 0;

                        db.modifyAlarmOn(db_id, on);
                        currentCursor = db.fetchAllAlarm();
                        adapter.notifyDataSetChanged();

                        if (on == 1) {
                            viewHolder.icon.setImageResource(R.drawable.ic_alarm_black);
                            Toast.makeText(fac, "알람이설정되었습니다.", Toast.LENGTH_SHORT).show();
                            Utility.startAlarm(fac, db_id);
                        } else {
                            viewHolder.icon.setImageResource(R.drawable.ic_alarm_gray);
                            Toast.makeText(fac, "알람이해제됐습니다.", Toast.LENGTH_SHORT).show();
                            Utility.cancelAlarm(fac, db_id);
                        }

                    }
                });
                viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CDialog.showDlg(getContext(), getString(R.string.text_alert_mesage_delete), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                long db_id = currentCursor.getLong(colID);
                                db.delAlarm("" + db_id);
                                Utility.cancelAlarm(fac, db_id);
                                resetAlarmList();
                            }
                        }, null);
                    }
                });
                //
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            //
            viewHolder.time.setText(getTimeString(currentCursor.getInt(colHOUR), currentCursor.getInt(colMINUTE)));

            int day = currentCursor.getInt(colDAY);
            String strDay = "";
            if ((day & 0x01) == 0x01) {
                strDay = "일 ";
            }
            if ((day & 0x02) == 0x02) {
                strDay += "월 ";
            }
            if ((day & 0x04) == 0x04) {
                strDay += "화 ";
            }
            if ((day & 0x08) == 0x08) {
                strDay += "수 ";
            }
            if ((day & 0x10) == 0x10) {
                strDay += "목 ";
            }
            if ((day & 0x20) == 0x20) {
                strDay += "금 ";
            }
            if ((day & 0x40) == 0x40) {
                strDay += "토";
            }

            viewHolder.day.setText(strDay);
            int on = currentCursor.getInt(colONOFF);

            if (on == 1) viewHolder.icon.setImageResource(R.drawable.ic_alarm_black);
            else viewHolder.icon.setImageResource(R.drawable.ic_alarm_gray);

            String sTitle = currentCursor.getString(colRING);
            viewHolder.title.setText(sTitle);

            return convertView;
        }

        private class ViewHolder {
            ImageView icon;
            ImageView delete;
            TextView time;
            TextView day;
            TextView title;
        }
    }

    public String getTimeString(int h, int m) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, h);
        cal.set(Calendar.MINUTE, m);
        SimpleDateFormat dayformat = new SimpleDateFormat("HH:mm");
        dayformat.setCalendar(cal);
        Date date = cal.getTime();
        return dayformat.format(date);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        super.navigationItemSelected(item);

        mDrawerMenu.closeDrawer(GravityCompat.START);
        return true;
    }
}