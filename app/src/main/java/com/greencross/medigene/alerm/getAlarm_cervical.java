package com.greencross.medigene.alerm;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.greencross.medigene.R;
import com.greencross.medigene.util.Utils;

public class getAlarm_cervical extends BroadcastReceiver {
	private String INTENT_ACTION_CERVICAL1 = "com.greencross.medigene.alerm.cervical1";
	private String INTENT_ACTION_CERVICAL2 = "com.greencross.medigene.alerm.cervical2";
	private String INTENT_ACTION_CERVICAL3 = "com.greencross.medigene.alerm.cervical3";

	private String INTENT_ACTION_IN1 = "com.greencross.medigene.alerm.in1";
	private String INTENT_ACTION_IN2 = "com.greencross.medigene.alerm.in2";

	private String INTENT_ACTION_A1 = "com.greencross.medigene.alerm.a1";
	private String INTENT_ACTION_A2 = "com.greencross.medigene.alerm.a2";

	private String INTENT_ACTION_B1 = "com.greencross.medigene.alerm.b1";
	private String INTENT_ACTION_B2 = "com.greencross.medigene.alerm.b2";
	private String INTENT_ACTION_B3 = "com.greencross.medigene.alerm.b3";

	private String notifiContent = "";
	private String popupContent = "";

	@Override
	public void onReceive(Context context, Intent intent) {
		if(Utils.TEST){
			Log.d("hsh", "onReceive: "+intent.getAction());
		}
		String action = intent.getAction();
		Utils.init(context);
		notifiContent = Utils.pref.getString("USER_NAME", "");
		popupContent = notifiContent;

		if(INTENT_ACTION_CERVICAL1.equals(action)){
			notifiContent += context.getString(R.string.str_vac_cer1);
			popupContent += context.getString(R.string.str_vac_cer1);
		}else if(INTENT_ACTION_CERVICAL2.equals(action)){
			notifiContent += context.getString(R.string.str_vac_cer2);
			popupContent += context.getString(R.string.str_vac_cer2);
		}else if(INTENT_ACTION_CERVICAL3.equals(action)){
			notifiContent += context.getString(R.string.str_vac_cer3);
			popupContent += context.getString(R.string.str_vac_cer3);
		}else if(INTENT_ACTION_IN1.equals(action)){
			notifiContent += context.getString(R.string.str_vac_in1);
			popupContent += context.getString(R.string.str_vac_in1);
		}else if(INTENT_ACTION_IN2.equals(action)){
			notifiContent += context.getString(R.string.str_vac_in2);
			popupContent += context.getString(R.string.str_vac_in2);
		}else if(INTENT_ACTION_A1.equals(action)){
			notifiContent += context.getString(R.string.str_vac_a1);
			popupContent += context.getString(R.string.str_vac_a1);
		}else if(INTENT_ACTION_A2.equals(action)){
			notifiContent += context.getString(R.string.str_vac_a2);
			popupContent += context.getString(R.string.str_vac_a2);
		}else if(INTENT_ACTION_B1.equals(action)){
			notifiContent += context.getString(R.string.str_vac_b1);
			popupContent += context.getString(R.string.str_vac_b1);
		}else if(INTENT_ACTION_B2.equals(action)){
			notifiContent += context.getString(R.string.str_vac_b2);
			popupContent += context.getString(R.string.str_vac_b2);
		}else if(INTENT_ACTION_B3.equals(action)){
			notifiContent += context.getString(R.string.str_vac_b3);
			popupContent += context.getString(R.string.str_vac_b3);
		}		
		getActivity(context);
	}

	private void getActivity(Context context) {
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, 
				new Intent(context, AlarmPopActivity3.class)
		.putExtra("notifiContent",notifiContent)
		.putExtra("popupContent",popupContent)
		.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK), PendingIntent.FLAG_UPDATE_CURRENT);
		try {
			pendingIntent.send();
		} catch (CanceledException e) {
			e.printStackTrace();
		}
	}

}
