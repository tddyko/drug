package com.greencross.medigene.alerm;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.greencross.medigene.MainActivity;
import com.greencross.medigene.util.Utils;
/**
 * 스마트폰의 상단 알림창에서 알림을 클릭했을 경우 실행되는 Service<br/>
 * 앱이 실행 중인지 판단하여 비실행일 경우 실행시킨다.
 */
public class AlarmService extends Service {

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		if(Utils.TEST){
			Log.i(Utils.TAG, "AlarmGCMService");
		}
		String type = intent.getStringExtra("type");
		if(Utils.AINTENT != null){
			if(type.equals("MAGIC_ACTION01") || type.equals("MAGIC_ACTION02")){
				PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, 
						new Intent(this, MainActivity.class)
				.putExtra("type", "MAGIC")
				.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_UPDATE_CURRENT);
				try {
					pendingIntent.send();
				} catch (CanceledException e) {
					e.printStackTrace();
				}
			}else if(type.equals("SAFE_ACTION01") || type.equals("SAFE_ACTION02") || type.equals("SAFE_ACTION03")){

				PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, 
						new Intent(this, MainActivity.class)
				.putExtra("type", "SAFE")
				.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_UPDATE_CURRENT);
				try {
					pendingIntent.send();
				} catch (CanceledException e) {
					e.printStackTrace();
				}
			}else{
//				PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, 
//						new Intent(this, Logo.class)
//				.putExtra("aaa","1")
//				.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_UPDATE_CURRENT);
//				try {
//					pendingIntent.send();
//				} catch (CanceledException e) {
//					e.printStackTrace();
//				}
			}
		}else{
			if(type.equals("MAGIC_ACTION01") || type.equals("MAGIC_ACTION02")){
				PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, 
						new Intent(this, MainActivity.class)
				.putExtra("aaa","1")
				.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_UPDATE_CURRENT);
				try {
					pendingIntent.send();
				} catch (CanceledException e) {
					e.printStackTrace();
				}
			}else if(type.equals("SAFE_ACTION01") || type.equals("SAFE_ACTION02") || type.equals("SAFE_ACTION03")){

				PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, 
						new Intent(this, MainActivity.class)
				.putExtra("aaa","1")
				.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_UPDATE_CURRENT);
				try {
					pendingIntent.send();
				} catch (CanceledException e) {
					e.printStackTrace();
				}
			}else{
				PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, 
						new Intent(this, MainActivity.class)
				.putExtra("aaa","1")
				.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_UPDATE_CURRENT);
				try {
					pendingIntent.send();
				} catch (CanceledException e) {
					e.printStackTrace();
				}
			}
		}
		stopSelf();
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}

	@Override
	public void onCreate() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
