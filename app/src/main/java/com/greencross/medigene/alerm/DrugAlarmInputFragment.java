package com.greencross.medigene.alerm;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

import com.greencross.medigene.R;
import com.greencross.medigene.base.BaseFragment;
import com.greencross.medigene.base.CommonActionBar;
import com.greencross.medigene.component.CDialog;
import com.greencross.medigene.util.Utils;

import java.util.Calendar;

public class DrugAlarmInputFragment extends BaseFragment {

	private String strTitle = "";

	public Calendar calendar;

	private int mAlarmHour = 12;
	private int mAlarmMinute = 0;

	private long db_id = -1;

	TimePicker time;

	private FragmentActivity fac;

	EditText txt_input_drug;

	CheckBox sun, mon, tue, wed, thur, fri, sat;
	LinearLayout layout_sun, layout_mon, layout_tue, layout_wed, layout_thur, layout_fri, layout_sat;



	public static Fragment newInstance() {
		DrugAlarmInputFragment fragment = new DrugAlarmInputFragment();
		return fragment;
	}
  
	@Override
	public void loadActionbar(CommonActionBar actionBar) {
		super.loadActionbar(actionBar);
		actionBar.setActionBarTitle( getString(R.string.drug_main));
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		fac = getActivity();
		final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.appTheme_alarm);

		LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
		View view = localInflater.inflate(R.layout.alarmset, null);	

		initFrame(view);
		return view;    	
	}

	private void initFrame(final View view) {
		calendar = Calendar.getInstance();

		sun = (CheckBox)view.findViewById(R.id.sun);
		mon = (CheckBox)view.findViewById(R.id.mon);
		tue = (CheckBox)view.findViewById(R.id.tue);
		wed = (CheckBox)view.findViewById(R.id.wed);
		thur = (CheckBox)view.findViewById(R.id.thur);
		fri = (CheckBox)view.findViewById(R.id.fri);
		sat = (CheckBox)view.findViewById(R.id.sat);

		layout_set(view);

		txt_input_drug = (EditText)view.findViewById(R.id.txt_input_drug);

		time = (TimePicker)view.findViewById(R.id.timePicker1);

		time.setOnTimeChangedListener(new OnTimeChangedListener() {

			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

//				Toast.makeText(fac,"Time is "+hourOfDay+ " : "+minute, Toast.LENGTH_SHORT).show();
			}
		});


		Button button = (Button)view.findViewById(R.id.set);
		button.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				strTitle = txt_input_drug.getText().toString();
				setAlarm();
			}
		});

		int curHours = calendar.get(Calendar.HOUR_OF_DAY);
		int curMinutes = calendar.get(Calendar.MINUTE);
		time.setCurrentHour(curHours);
		time.setCurrentMinute(curMinutes);

		if(getArguments()!=null){
			db_id = getArguments().getLong("id", -1);
		}
		if(db_id != -1){

			mAlarmHour = getArguments().getInt("hour", 12);
			mAlarmMinute = getArguments().getInt("min", 30);

			strTitle = getArguments().getString("ring");
			int day = getArguments().getInt("day", 0);

			time.setCurrentHour(mAlarmHour);
			time.setCurrentMinute(mAlarmMinute);

			// title
			if (strTitle != null) {
				showTitle(strTitle);
			}

			if ((day & 0x0001) == 0x0001) sun.setChecked(true);
			if ((day & 0x0002) == 0x0002) mon.setChecked(true);
			if ((day & 0x0004) == 0x0004) tue.setChecked(true);
			if ((day & 0x0008) == 0x0008) wed.setChecked(true);
			if ((day & 0x0010) == 0x0010) thur.setChecked(true);
			if ((day & 0x0020) == 0x0020) fri.setChecked(true);
			if ((day & 0x0040) == 0x0040) sat.setChecked(true);
		}
	}

	private void layout_set(View view) {
		layout_sun = (LinearLayout)view.findViewById(R.id.layout_sun);
		layout_sun.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(sun.isChecked()){
					sun.setChecked(false);
				}else{
					sun.setChecked(true);
				}
			}
		});
		layout_mon = (LinearLayout)view.findViewById(R.id.layout_mon);
		layout_mon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mon.isChecked()){
					mon.setChecked(false);
				}else{
					mon.setChecked(true);
				}
			}
		});
		layout_tue = (LinearLayout)view.findViewById(R.id.layout_tue);
		layout_tue.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(tue.isChecked()){
					tue.setChecked(false);
				}else{
					tue.setChecked(true);
				}
			}
		});
		layout_wed = (LinearLayout)view.findViewById(R.id.layout_wed);
		layout_wed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(wed.isChecked()){
					wed.setChecked(false);
				}else{
					wed.setChecked(true);
				}
			}
		});
		layout_thur = (LinearLayout)view.findViewById(R.id.layout_thur);
		layout_thur.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(thur.isChecked()){
					thur.setChecked(false);
				}else{
					thur.setChecked(true);
				}
			}
		});
		layout_fri = (LinearLayout)view.findViewById(R.id.layout_fri);
		layout_fri.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(fri.isChecked()){
					fri.setChecked(false);
				}else{
					fri.setChecked(true);
				}
			}
		});
		layout_sat = (LinearLayout)view.findViewById(R.id.layout_sat);
		layout_sat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(sat.isChecked()){
					sat.setChecked(false);
				}else{
					sat.setChecked(true);
				}
			}
		});

	}

	private void showTitle(String strTitle) {
		txt_input_drug.setText(strTitle);
	}

	@Override
	public void onDestroyView() {
		Utility.returnSetAlarm.resetList();
		super.onDestroyView();
	}

	public void setAlarm() {
		int apday = 0;

		if (sun.isChecked())  { apday |= 0x0001;}
		if (mon.isChecked())  { apday |= 0x0002;}
		if (tue.isChecked())  { apday |= 0x0004;}
		if (wed.isChecked())  { apday |= 0x0008;}
		if (thur.isChecked()) { apday |= 0x0010;}
		if (fri.isChecked())  { apday |= 0x0020;}
		if (sat.isChecked())  { apday |= 0x0040;}

		if (apday == 0) 
		{	   
			Toast.makeText(fac, "요일을 선택하세요", Toast.LENGTH_LONG).show();
			return;
		}

		mAlarmHour = time.getCurrentHour();
		mAlarmMinute = time.getCurrentMinute();

		if(Utils.TEST){
			Log.d("hsh","DrugAlarmInputFragment mAarmHour "+ mAlarmHour+" mAlarmMinute "+mAlarmMinute );
		}
		DBAdapter db = new DBAdapter(fac);
		db.open();

		if (db_id == -1) {
			db_id = db.addAlarm(1, apday, mAlarmHour, mAlarmMinute, 0, strTitle);
			
		} else {
			Utility.cancelAlarm(fac, db_id);
			db.modifyAlarm(db_id, 1, apday, mAlarmHour, mAlarmMinute, 0, strTitle);
		}

		db.close();

		Utility.startAlarm(fac,db_id);
		
//		new AlertDialog.Builder(fac)
//		.setTitle(getString(R.string.drug_main_detail))
//		.setMessage("저장되었습니다.")
//		.setCancelable(false)
//		.setPositiveButton("예",
//				new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				fac.onBackPressed();
//			}
//		})
//		.show();


		CDialog.showDlg(getContext(), "저장 되었습니다.", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				fac.onBackPressed();
			}
		});



	}
}



