package com.greencross.medigene.alerm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.greencross.medigene.util.Utils;
/**
 * 안심귀가 체크 권유 알람 호출시 실행되는 Service<br/>
 * 현재 별다른 작업없이 졸요한다. 
 */
public class AlarmPopupServiceS4 extends Service  {

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		if(Utils.TEST){
			Log.i(Utils.TAG, "AlarmPopupServiceS4");
		}

		stopSelf();
	}



	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
