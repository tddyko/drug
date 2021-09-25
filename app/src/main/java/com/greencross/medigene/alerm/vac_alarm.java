package com.greencross.medigene.alerm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.greencross.medigene.util.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class vac_alarm {
	private String INTENT_ACTION_CERVICAL1 = "com.greencross.medigene.alerm.cervical1";
	private String INTENT_ACTION_CERVICAL2 = "com.greencross.medigene.alerm.cervical2";
	private String INTENT_ACTION_CERVICAL3 = "com.greencross.medigene.alerm.cervical3";

	private String INTENT_ACTION_IN1 = "com.greencross.medigene.alerm.in1";
	private String INTENT_ACTION_IN2 = "com.greencross.medigene.alerm.in2";

	private String INTENT_ACTION_A1 = "com.greencross.medigene.alerm.a1";
	private String INTENT_ACTION_A2 = "com.greencross.medigene.alerm.a2";

	private String INTENT_ACTION_B1 = "com.greencross.medigene.alerm.b1";
	private String INTENT_ACTION_B2 = "com.greencross.medigene.alerm.b2";

	Context context;
	boolean pbToogle_cervical, pbToogle_a, pbToogle_b, pbToogle_influenza;
	String cerFirst, cerSecond, cerThird;
	String aFirst, aSecond;
	String bFirst, bSecond, bThird;
	String inFirst, inSecond;
	
	public vac_alarm(Context context){
		this.context = context;
		getPref();
	}

	public void startAlarm(int vac){
		switch(vac){
		case 0:
			if(pbToogle_cervical == false)
				break;
			cervical(0, cerFirst);
			cervical(1, cerSecond);
			cervical(2, cerThird);

			//			cervical(0, "201310121159");
			//			cervical(1, cerSecond);
			//			cervical(2, cerThird);

			break;
		case 1:
			if(pbToogle_influenza == false)
				break;
			influenza(0,inFirst);
			influenza(1,inSecond);
			break;
		case 2:
			if(pbToogle_a == false)
				break;
			a(0, aFirst);
			a(1, aSecond);
			break;
		case 3:
			if(pbToogle_b == false)
				break;
			b(0, bFirst);
			b(1, bSecond);
			break;
		}

	}

	private void setAlarm(Intent intent, Calendar caltime){
		AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

		PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
		alarmManager.set(AlarmManager.RTC_WAKEUP, caltime.getTimeInMillis(), pIntent);
	}
	
	private void a(int count, String day){
		Date datefirst = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm",Locale.KOREA);
		try {
			datefirst = dateFormat.parse(day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar caltemp = Calendar.getInstance();
		caltemp.setTime(datefirst);
		
		Calendar calNow = Calendar.getInstance();
		calNow.setTimeInMillis(System.currentTimeMillis());
		
		if(System.currentTimeMillis()>caltemp.getTimeInMillis()){
			return;
		}
		
		Intent intent = null;
		if(count == 0)
			intent = new Intent(INTENT_ACTION_A1);
		else if(count == 1)
			intent = new Intent(INTENT_ACTION_A2);
		
		setAlarm(intent, caltemp);
	}
	
	private void b(int count, String day){
		Date datefirst = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm",Locale.KOREA);
		try {
			datefirst = dateFormat.parse(day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar caltemp = Calendar.getInstance();
		caltemp.setTime(datefirst);
		
		Calendar calNow = Calendar.getInstance();
		calNow.setTimeInMillis(System.currentTimeMillis());
		
		if(System.currentTimeMillis()>caltemp.getTimeInMillis()){
			return;
		}
		
		Intent intent = null;
		if(count == 0)
			intent = new Intent(INTENT_ACTION_B1);
		else if(count == 1)
			intent = new Intent(INTENT_ACTION_B2);
		
		setAlarm(intent, caltemp);
	}
	
	private void cervical(int count, String day) {
		Date datefirst = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm",Locale.KOREA);
		try {
			datefirst = dateFormat.parse(day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar caltemp = Calendar.getInstance();
		caltemp.setTime(datefirst);

		Calendar calNow = Calendar.getInstance();
		calNow.setTimeInMillis(System.currentTimeMillis());
		if(Utils.TEST){
			Log.d("hsh", "set: "+caltemp.get(Calendar.YEAR)+" "+ caltemp.get(Calendar.MONTH)+" "+  caltemp.get(Calendar.DAY_OF_MONTH)+" "+  caltemp.get(Calendar.HOUR)+" "+  caltemp.get(Calendar.MINUTE));
			Log.d("hsh", "cur: "+calNow.get(Calendar.YEAR)+" "+ calNow.get(Calendar.MONTH)+" "+  calNow.get(Calendar.DAY_OF_MONTH)+" "+  calNow.get(Calendar.HOUR)+" "+  calNow.get(Calendar.MINUTE));

			Log.d("hsh", "set: "+caltemp.getTimeInMillis());
			Log.d("hsh", "now: "+calNow.getTimeInMillis());

			Log.d("hsh", "settime: "+caltemp);
			Log.d("hsh", "nowtime: "+calNow);
		}

		if(System.currentTimeMillis()>caltemp.getTimeInMillis()){
			if(Utils.TEST){	
				Log.d("hsh","true");
			}
			return;
		}else{
			if(Utils.TEST){
				Log.d("hsh","false");
			}
		}
		
		Intent intent = null;
		if(count == 0)
			intent = new Intent(INTENT_ACTION_CERVICAL1);
		else if(count == 1)
			intent = new Intent(INTENT_ACTION_CERVICAL2);
		else if(count == 2)
			intent = new Intent(INTENT_ACTION_CERVICAL3);
		
		setAlarm(intent, caltemp);
	}
	
	private void influenza(int count, String day) {
		Date datefirst = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm",Locale.KOREA);
		try {
			datefirst = dateFormat.parse(day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar caltemp = Calendar.getInstance();
		caltemp.setTime(datefirst);
		
		Calendar calNow = Calendar.getInstance();
		calNow.setTimeInMillis(System.currentTimeMillis());
		
		if(System.currentTimeMillis()>caltemp.getTimeInMillis()){
			return;
		}
		
		Intent intent = null;
		if(count == 0)
			intent = new Intent(INTENT_ACTION_IN1);
		else if(count == 1)
			intent = new Intent(INTENT_ACTION_IN2);
		
		setAlarm(intent, caltemp);
	}
	
	private void getPref() {
		SharedPreferences preferences = context.getSharedPreferences(preferencesVacInfo.preferencesName_vac, Activity.MODE_PRIVATE);
		pbToogle_cervical = preferences.getBoolean(preferencesVacInfo.pbToogle_cervical, false);
		if(pbToogle_cervical){
			int y = Integer.valueOf(preferences.getString(preferencesVacInfo.pCervical_firstday_y, "0"));
			int m = Integer.valueOf(preferences.getString(preferencesVacInfo.pCervical_firstday_m, "0"));
			int d = Integer.valueOf(preferences.getString(preferencesVacInfo.pCervical_firstday_d, "0"));
			cerFirst = Utils.zeroTwo(y)+Utils.zeroTwo(m)+Utils.zeroTwo(d)+"0900";
			
			int y2 = Integer.valueOf(preferences.getString(preferencesVacInfo.pCervical_secondday_y, "0"));
			int m2 = Integer.valueOf(preferences.getString(preferencesVacInfo.pCervical_secondday_m, "0"));
			int d2 = Integer.valueOf(preferences.getString(preferencesVacInfo.pCervical_secondday_d, "0"));
			cerSecond = Utils.zeroTwo(y2)+Utils.zeroTwo(m2)+Utils.zeroTwo(d2)+"0900";
			
			int y3 = Integer.valueOf(preferences.getString(preferencesVacInfo.pCervical_thirdday_y, "0"));
			int m3 = Integer.valueOf(preferences.getString(preferencesVacInfo.pCervical_thirdday_m, "0"));
			int d3 = Integer.valueOf(preferences.getString(preferencesVacInfo.pCervical_thirdday_d, "0"));
			cerThird = Utils.zeroTwo(y3)+Utils.zeroTwo(m3)+Utils.zeroTwo(d3)+"0900";
		}

		pbToogle_a = preferences.getBoolean(preferencesVacInfo.pbToogle_a, false);
		if(pbToogle_a){
			int y = Integer.valueOf(preferences.getString(preferencesVacInfo.pA_firstday_y, "0"));
			int m = Integer.valueOf(preferences.getString(preferencesVacInfo.pA_firstday_m, "0"));
			int d = Integer.valueOf(preferences.getString(preferencesVacInfo.pA_firstday_d, "0"));
			aFirst = Utils.zeroTwo(y)+Utils.zeroTwo(m)+Utils.zeroTwo(d)+"1000";
			
			int y2 = Integer.valueOf(preferences.getString(preferencesVacInfo.pA_secondday_y, "0"));
			int m2 = Integer.valueOf(preferences.getString(preferencesVacInfo.pA_secondday_m, "0"));
			int d2 = Integer.valueOf(preferences.getString(preferencesVacInfo.pA_secondday_d, "0"));
			aSecond = Utils.zeroTwo(y2)+Utils.zeroTwo(m2)+Utils.zeroTwo(d2)+"1000";
		}

		pbToogle_b = preferences.getBoolean(preferencesVacInfo.pbToogle_b, false);
		if(pbToogle_b){
			int y = Integer.valueOf(preferences.getString(preferencesVacInfo.pB_firstday_y, "0"));
			int m = Integer.valueOf(preferences.getString(preferencesVacInfo.pB_firstday_m, "0"));
			int d = Integer.valueOf(preferences.getString(preferencesVacInfo.pB_firstday_d, "0"));
			bFirst = Utils.zeroTwo(y)+Utils.zeroTwo(m)+Utils.zeroTwo(d)+"1100";
			
			int y2 = Integer.valueOf(preferences.getString(preferencesVacInfo.pB_secondday_y, "0"));
			int m2 = Integer.valueOf(preferences.getString(preferencesVacInfo.pB_secondday_m, "0"));
			int d2 = Integer.valueOf(preferences.getString(preferencesVacInfo.pB_secondday_d, "0"));
			bSecond = Utils.zeroTwo(y2)+Utils.zeroTwo(m2)+Utils.zeroTwo(d2)+"1100";
			
			int y3 = Integer.valueOf(preferences.getString(preferencesVacInfo.pB_thirdday_y, "0"));
			int m3 = Integer.valueOf(preferences.getString(preferencesVacInfo.pB_thirdday_m, "0"));
			int d3 = Integer.valueOf(preferences.getString(preferencesVacInfo.pB_thirdday_d, "0"));
			bThird = Utils.zeroTwo(y3)+Utils.zeroTwo(m3)+Utils.zeroTwo(d3)+"1100";
		}

		pbToogle_influenza = preferences.getBoolean(preferencesVacInfo.pbToogle_influenza, false);
		if(pbToogle_influenza){
			String y = preferences.getString(preferencesVacInfo.pInfluenza_firstday, "0");
			inFirst = y+"09011200";
			String y2 = preferences.getString(preferencesVacInfo.pInfluenza_secondday, "0");
			inSecond = y2+"10011200";
		}
	}
}
