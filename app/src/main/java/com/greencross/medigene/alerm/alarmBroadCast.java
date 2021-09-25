package com.greencross.medigene.alerm;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.greencross.medigene.util.Utils;

import java.util.Calendar;

public class alarmBroadCast extends BroadcastReceiver {
	boolean[] week = { false, false, false, false, false, false, false };

	@Override
	public void onReceive(Context context, Intent intent) {
		if(Utils.TEST){
			Log.d("hsh", "alarmBroadCast onReceive()");
		}
		Bundle extra = intent.getExtras();
		if(extra !=null){
			int id = (int)intent.getLongExtra("id",0);
			int appDay = intent.getIntExtra("appday", 0);
			String sTitle = intent.getStringExtra("title");
			
			if(Utils.TEST){
				Log.d("hsh", "id "+id);
				Log.d("hsh", "appDay "+appDay);
				Log.d("hsh", "sTitle "+sTitle);
			}	
			
			if(appDay != 0){
				if((appDay & 0x01) == 0x01){ week[0] = true; } // 일
				if((appDay & 0x02) == 0x02){ week[1] = true; }
				if((appDay & 0x04) == 0x04){ week[2] = true; }
				if((appDay & 0x08) == 0x08){ week[3] = true; }
				if((appDay & 0x10) == 0x10){ week[4] = true; }
				if((appDay & 0x20) == 0x20){ week[5] = true; }
				if((appDay & 0x40) == 0x40){ week[6] = true; } // 토
				
				Calendar cal = Calendar.getInstance();
				int cur_day = cal.get(Calendar.DAY_OF_WEEK); //일 1 토 7
				if(Utils.TEST){
					Log.d("hsh", "week[cur_day-1] "+(week[cur_day-1]?"today":"no today"));			
				}
				if(week[cur_day-1]){
					if(Utils.TEST){
						Log.d("hsh", "cur_day is on");			
					}
					Intent i = new Intent(context, alarmReceiver.class);
					i.putExtra("title", sTitle);
					i.putExtra("id", id);
					
					PendingIntent pi = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_ONE_SHOT);
					try{
						pi.send();
					} catch( CanceledException e){
						
					}
				}else{
					if(Utils.TEST){
						Log.d("hsh", "cur_day is off");			
					}
				}
				
				
			}
			
			Utility.startAlarm(context, id);
		}
		
		
	}

}
