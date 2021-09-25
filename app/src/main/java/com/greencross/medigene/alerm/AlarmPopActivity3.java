package com.greencross.medigene.alerm;

import android.R.style;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.greencross.medigene.MainActivity;
import com.greencross.medigene.util.Utils;
import com.greencross.medigene.R;

/**
 * 팝업을 담당하는 Activity <br/>
 * 사용처 : Push 메시지를 화면에 보여준다.<br/>
 * 		알람메시지를 화면에 보여준다.(가임기, 배란일, 안심귀가 체크 권유 알람)
 * 
 */
@SuppressLint({ "Wakelock", "HandlerLeak" })
public class AlarmPopActivity3 extends Activity{

	private NotificationManager mManager;
	private String notifiContent = "";
	private String popupContent = "";
	private Activity ac;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(Utils.TEST){
			Log.i(Utils.TAG, "AlarmPopActivity2");
		}
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.alarmpopactivity2);
		ac = this;

		ac.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED

				| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD

				| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

		mManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		Utils.pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);

		notifiContent = getIntent().getStringExtra("notifiContent");
		popupContent = getIntent().getStringExtra("popupContent");

		TextView textView1 = (TextView) findViewById(R.id.textView1);
		textView1.setText(popupContent);

		ImageView btn01 = (ImageView) findViewById(R.id.btn01);
		ImageView btn02 = (ImageView) findViewById(R.id.btn02);


		btn01.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mManager.cancel("gch", Utils.NOTI_ID);
				if(Utils.AINTENT != null){
					Utils.AINTENT = new Intent(AlarmPopActivity3.this, MainActivity.class);
					startActivity(Utils.AINTENT);
					finish();
				}else{
					finish();
				}


			}
		});

		btn02.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		if(Utils.AINTENT == null){
			setNoti(notifiContent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

	protected void onApplyThemeResource(Resources.Theme theme, int resid, boolean first) {
		super.onApplyThemeResource(theme, resid, first);
		theme.applyStyle(style.Theme_Panel, true);
	}

	/**
	 * @param text 알림창에 보여줄 텍스트
	 */
	protected void setNoti(String text) {
/*		Notification notification = new Notification();
		notification.icon = R.drawable.ic_launcher;
		notification.tickerText = getString(R.string.str_0004);
		notification.when = System.currentTimeMillis();
		notification.number = 0;
		notification.vibrate = new long[] { 500, 100, 500, 100 };
		notification.sound = Uri.parse("/system/media/audio/notifications/20_Cloud.ogg");
		notification.defaults = Notification.DEFAULT_VIBRATE;
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		final String contentTitle = getString(R.string.app_names);
		final String contentText = text;
		
		PendingIntent pIntent = PendingIntent.getActivity(AlarmPopActivity3.this, 0, 
				new Intent(AlarmPopActivity3.this, Logo.class), PendingIntent.FLAG_UPDATE_CURRENT);
		notification.setLatestEventInfo( getApplicationContext(), contentTitle, contentText, pIntent);
		mManager.notify("gch", Utils.NOTI_ID++, notification);*/
		
		PendingIntent pIntent = PendingIntent.getActivity(AlarmPopActivity3.this, 0, 
				new Intent(AlarmPopActivity3.this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
		
		Notification.Builder builder = new Notification.Builder(getApplicationContext())
        .setContentIntent(pIntent)
        .setSmallIcon(R.drawable.ic_launcher)
        .setContentTitle(getString(R.string.app_name))
        .setContentText(text)
        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
        .setTicker(getString(R.string.str_0004));
		Notification notification = builder.build();
		mManager.notify("gch", Utils.NOTI_ID++, notification);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
