package com.greencross.medigene.alerm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.util.Log;
import com.greencross.medigene.alerm.DrugAlarmFragment.ReturnSetAlarm;

import com.greencross.medigene.util.Utils;

import java.util.Calendar;
import java.util.Locale;

public class Utility {
//	static final int alarmSetId = 123;

	public static ReturnSetAlarm returnSetAlarm;


	public static boolean useKoreanLanguage(Context context) {
		Locale lc = context.getResources().getConfiguration().locale;
		String language = lc.getLanguage();

		if (language.equals("ko")) { 
			return true;
		} else {
			return false;
		}
	}

	public static void startAllAlarm(Context context){
		if(Utils.TEST){
			Log.d("hsh", "startAllAlarm");			
		}

		long m_id =1;
		int day;
		int onoff;
		int hour;
		int min;

		String title;

		DBAdapter db = new DBAdapter(context);
		if (db == null) return;

		db.open();
		Cursor c = db.fetchAllAlarm();
		if(c.getCount()>0){
			c.moveToFirst();

			do{
				m_id = c.getLong(c.getColumnIndex("_id"));

				cancelAlarm(context, m_id);
				if(Utils.TEST){
					Log.d("hsh", "startAllAlarm m_id "+m_id);			
				}
				onoff =  c.getInt(c.getColumnIndex(DBAdapter.ALARM_ON));
				if (onoff == 1) {
					day =  c.getInt(c.getColumnIndex(DBAdapter.ALARM_APDAY));
					hour = c.getInt(c.getColumnIndex(DBAdapter.ALARM_HOUR));
					min = c.getInt(c.getColumnIndex(DBAdapter.ALARM_MINUTE));
					title = c.getString(c.getColumnIndex(DBAdapter.ALARM_RINGTONE));

					Intent intent = new Intent(context, alarmBroadCast.class);
					intent.putExtra("title", title);
					intent.putExtra("id", m_id);
					intent.putExtra("appday", day);
					PendingIntent sender = PendingIntent.getBroadcast(context, (int)m_id, intent, PendingIntent.FLAG_CANCEL_CURRENT);


					AlarmManager mManager = null;
					mManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
						mManager.setExact(AlarmManager.RTC_WAKEUP, setTriggerTime(hour, min), sender);
					} else {
						mManager.set(AlarmManager.RTC_WAKEUP, setTriggerTime(hour, min), sender);
					}
					if(Utils.TEST){
						Log.d("hsh", "startAllAlarm do while");			
					}
				}		
			}while(c.moveToNext());
		}
		db.close();
	}


	private static long setTriggerTime(int hour, int minute)
	{
		long atime = System.currentTimeMillis();

		Calendar curTime = Calendar.getInstance();
		curTime.set(Calendar.HOUR_OF_DAY, hour);
		curTime.set(Calendar.MINUTE, minute);
		curTime.set(Calendar.SECOND, 0);
		curTime.set(Calendar.MILLISECOND, 0);
		long btime = curTime.getTimeInMillis();
		long triggerTime = btime;
		if (atime > btime){
			if(Utils.TEST){
				Log.d("hsh", "setTriggerTime is tommorow ");			
			}	
			triggerTime += 1000 * 60 * 60 * 24;
		}
		return triggerTime;
	}


	public static void startAlarm(Context context, long id) {
		if(Utils.TEST){
			Log.d("hsh", "startAlarm id "+id);			
		}
		// DB
		long m_id =1;
		int day;
		int onoff;
		int hour;
		int min;

		String title;

		DBAdapter db = new DBAdapter(context);
		if (db == null) return;

		db.open();
		Cursor c = db.fetchAllAlarm();
		c.moveToFirst();

		boolean bExist = false;

		for(int i=0;i<c.getCount();i++){
			m_id = c.getLong(c.getColumnIndex("_id"));
			if(m_id == id){
				bExist = true;
				break;
			}
			c.moveToNext();
		}

		if(Utils.TEST){
			Log.d("hsh", "startAlarm bExist "+bExist);			
		}
		if(bExist || c.getCount()==0){
			onoff =  c.getInt(c.getColumnIndex(DBAdapter.ALARM_ON));
			if (onoff == 1) {
				day =  c.getInt(c.getColumnIndex(DBAdapter.ALARM_APDAY));
				hour = c.getInt(c.getColumnIndex(DBAdapter.ALARM_HOUR));
				min = c.getInt(c.getColumnIndex(DBAdapter.ALARM_MINUTE));
				//				vib = c.getInt(c.getColumnIndex(DBAdapter.ALARM_VIBRATE));
				title = c.getString(c.getColumnIndex(DBAdapter.ALARM_RINGTONE));

				//				Intent intent = new Intent(context, alarmReceiver.class);
				Intent intent = new Intent(context, alarmBroadCast.class);
				intent.putExtra("title", title);
				intent.putExtra("id", m_id);
				intent.putExtra("appday", day);
				//				intent.putExtra("vibrate", m_vib);				        


				//				PendingIntent sender = PendingIntent.getActivity(context, (int)m_id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
				PendingIntent sender = PendingIntent.getBroadcast(context, (int)m_id, intent, PendingIntent.FLAG_CANCEL_CURRENT);


				AlarmManager mManager = null;
				mManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
				//				mManager.setRepeating(AlarmManager.RTC_WAKEUP, setTriggerTime(hour, min), 0, sender);

				/*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
					mManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, setTriggerTime(hour, min), sender);
		        } else*/ 
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
					mManager.setExact(AlarmManager.RTC_WAKEUP, setTriggerTime(hour, min), sender);
				} else {
					mManager.set(AlarmManager.RTC_WAKEUP, setTriggerTime(hour, min), sender);
				}

			}
		}else{

		}
		db.close();
	} 

	public static void cancelAlarm(Context context, long id) {
		if(Utils.TEST){
			Log.d("hsh", "startAlarm cancelAlarm");			
		}
		Intent intent = new Intent(context, alarmBroadCast.class);
		PendingIntent sender = PendingIntent.getBroadcast(context, (int)id, intent, PendingIntent.FLAG_CANCEL_CURRENT );

		AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		am.cancel(sender);
		sender.cancel();

		if(Utils.TEST){
			sender = PendingIntent.getBroadcast(context, (int)id, intent, PendingIntent.FLAG_NO_CREATE );
			boolean b = (sender==null);
			Log.d("hsh", "b "+(b?"정상취소":"취소안됨"));			
		}


	}
}
