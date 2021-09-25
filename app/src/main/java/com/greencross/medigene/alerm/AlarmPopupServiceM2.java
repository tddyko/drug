package com.greencross.medigene.alerm;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.greencross.medigene.util.Utils;
import com.greencross.medigene.R;

/**
 * 매직데이 배란일 알람 호출시 실행되는 Service<br/>
 * 메시지를 세팅하고 AlarmPopAcitivity를 호출한다.
 */
public class AlarmPopupServiceM2 extends Service {

	private String notifiContent = "";
	private String popupContent = "";

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		if(Utils.TEST){
			Log.i(Utils.TAG, "AlarmPopupServiceM2");
		}
		Utils.pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
		if(Utils.pref.getBoolean("magicalram" , true)){
			notifiContent = getString(R.string.str_0005);
			popupContent = getString(R.string.str_0005);


			PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, 
					new Intent(this, AlarmPopActivity.class)
			.putExtra("notifiContent",notifiContent)
			.putExtra("popupContent",popupContent)
			.putExtra("type","MAGIC_ACTION02")
			.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK), PendingIntent.FLAG_UPDATE_CURRENT);
			try {
				pendingIntent.send();
			} catch (CanceledException e) {
				e.printStackTrace();
			}
		}
		stopSelf();
	}


	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
