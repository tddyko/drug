package com.greencross.medigene.alerm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.view.WindowManager;

import com.greencross.medigene.R;

import java.util.Calendar;

public class alarmReceiver extends Activity {

	String mTitle = "";
	int id = 0;

	PowerManager.WakeLock wl = null;
	PowerManager pm;
	Calendar calendar;

//	private NotificationManager nm = null;

	boolean startFlag = false;

//	private AlarmManager mManager = null;

//	View targetView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.alarm_receiver2);

		pm = (PowerManager)this.getSystemService(Context.POWER_SERVICE);
		if (!pm.isScreenOn()) { 
			wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "drugAlarm");
			wl.acquire();
			this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
					//										WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
					WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
					WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

		}


		mTitle = getIntent().getStringExtra("title");
		if(mTitle == null)
			mTitle = "약";
		id = getIntent().getIntExtra("id",0);

		/*TextView tv_title = (TextView)findViewById(R.id.mv_target);
		tv_title.setText(getString(R.string.str_drug_alarm_title, mTitle));

		Button btn_confirm = (Button)findViewById(R.id.btn_confirm);
		btn_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});*/

		playSound();

		new AlertDialog.Builder(this)
		.setTitle(getString(R.string.drug_main))
		.setMessage(getString(R.string.str_drug_alarm_title, mTitle))
		.setCancelable(false)
		.setPositiveButton("확인",
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		})
		.show();

		//		showNotification(R.drawable.ic_launcher, "�ῡ�� �Ͼ �ð��̾�!!", mRingTone, vibrate);

		//		clearAlarm();
	}


	private void playSound() {
		AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

		switch(audioManager.getRingerMode()){
		case AudioManager.RINGER_MODE_VIBRATE:
			// 진동
			Vibrator vide = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			long[] pattern = { 0, 500, 200, 400, 100 };
			vide.vibrate(pattern, -1);
			break;

		case AudioManager.RINGER_MODE_NORMAL:

			// 소리
			Uri ringtoneUri = RingtoneManager.getActualDefaultRingtoneUri(this ,RingtoneManager.TYPE_NOTIFICATION);

			Ringtone ringtone = RingtoneManager.getRingtone(this, ringtoneUri);

			ringtone.play();


			break;

		case AudioManager.RINGER_MODE_SILENT:

			// 무음

			break;

		}

	}


	@Override
	protected void onResume() {
		super.onResume();
	}
	//
	@Override
	protected void onStart() {
		super.onStart();

	}

	@Override
	protected void onPause() {
		super.onPause();   	
	}

	@Override
	protected void onStop() {
		super.onStop();
	}
	@Override
	protected void onRestart() {
		super.onRestart();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//		NotificationManager nm = (NotificationManager) getSystemService (Context.NOTIFICATION_SERVICE);
		//		nm.cancelAll();
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////


	/*private void showNotification(int statusBarIconID, 
			String statusBatTextID, String ringtone, int vibrate){
		// Notification ��ü ����/����
		nm = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notifi = new Notification(R.drawable.nabak_alarm_title, null, System.currentTimeMillis());
		//����ڰ� ���� �� ���� ��� �︮���ϴ� FLAG��
		//		notifi.flags |= Notification.FLAG_INSISTENT;

		playSound(Uri.parse(mRingTone));

		if (vibrate == 1) {	// ������ �����Ǿ� ������ ?
			vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE); 
			long[] pattern = {200, 2000, 100, 1700, 200, 2000, 100, 1700, 200, 2000, 100, 1700};          //  ������, ���� ���̴�.
			vibe.vibrate(pattern, 2);                                 // ������ �����ϰ� �ݺ�Ƚ���� ����  ���� 2�� ��� �ݺ��̴�.
		}

		Intent intent = new Intent(this, alarmCancel.class);
		PendingIntent theappIntent = PendingIntent.getActivity(this, 12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);		

		CharSequence from = "�˶��� �����Ϸ��� ������ �ٽ� Ŭ�� �� ��縦 ��ƾ� �������� �մϴ�. HomeKey�� ������ ������!";
		CharSequence message = "NabakAlarm";

		notifi.setLatestEventInfo(this, from, message, theappIntent);

		nm.notify(12345, notifi);	


	}*/

	/*private void playSound(Uri alert) {
		if (mMediaPlayer != null) return;

		mMediaPlayer = new MediaPlayer();
		try {
			mMediaPlayer.setDataSource(this, alert);
			final AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
			if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
				mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
				mMediaPlayer.prepare();
				mMediaPlayer.setLooping(true);
				mMediaPlayer.start();
			}
		} catch (IOException e) {

		}
	}*/
}
