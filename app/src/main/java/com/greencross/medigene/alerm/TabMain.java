package com.greencross.medigene.alerm;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.Toast;

import com.greencross.medigene.util.Utils;

import java.util.Iterator;
import java.util.List;

/**
 * Home 메인 화면<br/>
 *
 */
public class TabMain extends FragmentActivity   {

	private FragmentActivity ac;
	private Context con;
	private Handler handler = null;
	private String[] arAd;
	private int iii = 0;
	private TextSwitcher mSwitcher;
	/* 사용 안함
	private TextView buttonTxt1;
	private TextView buttonTxt2;
	private TextView buttonTxt3;
	 */
	private boolean firstOne = true;
	// 안쓰고 고정으로 변경
	//	private boolean firstgetData = true;

	LayoutInflater inflater;
	LinearLayout linear;
	Dialog dialog;
	EditText p1, p2, p3;
	CheckBox c1, c2, c3;
	Intent accelservie;

	private boolean toggle = false;
	ImageView togglebutton;
	ImageView new1, new2, new3;

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.tab_main);
//		if(Utils.TEST){
//			Log.i(Utils.TAG, "TabMain");
//		}
//
//		// 고정으로 변경
//		/*arAd = new String[] {
//				getString(R.string.messageStr001),
//				getString(R.string.messageStr002)
//		};*/
//
//		arAd = new String[] {
//				"여자의 일생을 더 아름답게, 더 건강하게",
//				"지켜드리는 약물관리 서비스에",
//				"오신걸 환영합니다."
//		};
//
//		if(Utils.FRAGMENTACTIVITY1 == null){Utils.FRAGMENTACTIVITY1= this;}
//		handler = new Handler();
//		con = this;
//		ac = this;
//		Utils.returnvalueClass = new ReturnvalueClass();
//
//		Utils.progressBar.setVisibility(View.INVISIBLE);
//
//		Utils.SVIEW = (ScrollView)findViewById(R.id.ScrollView01);
//		Utils.SVIEW.setVerticalScrollBarEnabled(false);
//		Utils.SVIEW.setVerticalScrollBarEnabled(false);
//
//		/**예약현황*/
//		ImageView btn1 = (ImageView)this.findViewById(R.id.button1);
//		new1 = (ImageView)this.findViewById(R.id.new_icon1);
//		//		buttonTxt1 = (TextView)this.findViewById(R.id.buttonTxt1);//사용안함
//		btn1.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if(Utils.getLogin.checkID(TabMain.this)){
//					if(Utils.TEST)
//						Log.d("hsh", "return 복약 알람 설정");
//					return;
//				}
////				Bundle arg = new Bundle();
////				arg.putInt("type", 0);
////				arg.putInt("type_homebar", 0);
////				AppManageHelper.addTabMenu(ac, TAB_MENU.MAIN, ServiceReqStatusFragment.class, getString(R.string.service_req), true, arg);
//
//				AppManageHelper.addTabMenu(ac, TAB_MENU.MAIN, DrugAlarmFragment.class, getString(R.string.drug_main), true, null);
//			}
//		});
//
//
//		/**알림센터*/
//		ImageView btn2 = (ImageView)this.findViewById(R.id.button2);
//		new2 = (ImageView)this.findViewById(R.id.new_icon2);
//		//		buttonTxt2 = (TextView)this.findViewById(R.id.buttonTxt2);//사용안함
//		btn2.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if(Utils.getLogin.checkID(TabMain.this)){
//					if(Utils.TEST)
//						Log.d("hsh", "return 알림센터");
//					return;
//				}
//				AppManageHelper.addTabMenu(ac, TAB_MENU.MAIN, ContentHelath.class, getString(R.string.healthContents), true, null);
//			}
//		});
//		/**공지사항*/
//		ImageView btn3 = (ImageView)this.findViewById(R.id.button3);
//		new3 = (ImageView)this.findViewById(R.id.new_icon3);
//		//		buttonTxt3 = (TextView)this.findViewById(R.id.buttonTxt3);//사용안함
//		btn3.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if(Utils.getLogin.checkID(TabMain.this)){
//					if(Utils.TEST)
//						Log.d("hsh", "return 공지사항");
//					return;
//				}
//				AppManageHelper.addTabMenu(ac, TAB_MENU.MAIN, NoticeFragment.class, getString(R.string.notice), true, null);
//			}
//		});
//		/**우먼싸이클*/
//		ImageView btn4 = (ImageView)this.findViewById(R.id.button4);
//		btn4.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				AppManageHelper.addTabMenu(ac, TAB_MENU.MAIN, CTCalendarView.class, getString(R.string.woman_cycle), true, null);
//			}
//		});
//		/**우먼너싱*/
//		ImageView btn5 = (ImageView)this.findViewById(R.id.button5);
//		btn5.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				AppManageHelper.addTabMenu(ac, TAB_MENU.MAIN, WomanNursingFragment.class, getString(R.string.req_woman), true, null);
//			}
//		});
//		/**우먼힐링*/
//		ImageView btn6 = (ImageView)this.findViewById(R.id.button6);
//		btn6.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				AppManageHelper.addTabMenu(ac, TAB_MENU.MAIN, WomanhilingFragment.class, getString(R.string.hil_woman), true, null);
//			}
//		});
//		/**안심귀가*/
//		ImageView btn7 = (ImageView)this.findViewById(R.id.button7);
//		btn7.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				AppManageHelper.addTabMenu(ac, TAB_MENU.MAIN, TabSafeComeHome.class, getString(R.string.input_password), true, null);
//			}
//		});
//		/**위급상황 SOS*/
//		ImageView btn8 = (ImageView)this.findViewById(R.id.button8);
//		btn8.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if(checkAccess()){
//					showLocationSettingsAlert();
//					return;
//				}
//
//				int result = GooglePlayServicesUtil.isGooglePlayServicesAvailable(TabMain.this);
////								result=1;
//				if (result != ConnectionResult.SUCCESS) {
//					showGPSSettingsAlert(result);
//					return;
//				}
//
//				//170215 - 삭제
//				/*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
//
//				}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
//					WifiManager mWifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
//					boolean b = mWifiManager.isScanAlwaysAvailable();
//					if(Utils.TEST){
//						Log.i(Utils.TAG, "always : "+b);
//					}
//
//					if(!b){
//						showWIFISettingsAlert();
//						return;
//					}
//				}*/
//
//				setSOS();
//			}
//		});
//		/**기타*/
//		ImageView btn9 = (ImageView)this.findViewById(R.id.button9);
//		btn9.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				/** 환경설정*/
//				AppManageHelper.addTabMenu(ac, TAB_MENU.MAIN, EtcFragment.class, getString(R.string.etc), true, null);
//			}
//		});
//
//		/*Button headRightBtn = (Button)this.findViewById(R.id.headRightBtn);
//		headRightBtn.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if(Utils.TEST){
//					Log.i("hsh", "headRightBtn");
//					String s = getString(R.string.drug_main_detail);
//					DrugAlarmInputFragment as = (alarmSet)getSupportFragmentManager().findFragmentByTag(s);
//					as.setAlarm();
//				}
//			}
//		});*/
//
//		/**메인화면 전광판*/
//		mSwitcher = (TextSwitcher) findViewById(R.id.switcher);
//		mSwitcher.setFactory(this);
//		AnimationSet ani = new AnimationSet(true);
//		ani.setInterpolator(new LinearInterpolator());
//		AnimationSet ani2 = new AnimationSet(true);
//		ani2.setInterpolator(new LinearInterpolator());
//
//		TranslateAnimation trans = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_PARENT, 0);
//		trans.setDuration(2000);
//		ani.addAnimation(trans);
//		TranslateAnimation trans2 = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, -1);
//		trans2.setDuration(2000);
//		ani2.addAnimation(trans2);
//
//		mSwitcher.setInAnimation(ani);
//		mSwitcher.setOutAnimation(ani2);
//		mSwitcher.startLayoutAnimation();
//
//		trans2.setAnimationListener(new AnimationListener() {
//			@Override
//			public void onAnimationStart(Animation arg0) {}
//			@Override
//			public void onAnimationRepeat(Animation arg0) {}
//			@Override
//			public void onAnimationEnd(Animation arg0) {
//				handler.postDelayed(new Runnable() {
//					public void run() {
//						if(iii == arAd.length){iii = 0;}
//						mSwitcher.setText(arAd[iii]);
//						iii++;
//					}
//				},2000);
//			}
//		});
//		mSwitcher.setText(arAd[iii++]);
//		Utils.GetMain = new GetMain();
//		getMainData();
//
//		setService();
	}

	protected boolean checkAccess() {
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (isNetworkEnabled == false || isGPSEnabled == false) {
			return true;
		} else
			return false;

	}

	private void showLocationSettingsAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
//		alertDialog.setTitle("위치 서비스 설정");
//
//		//        alertDialog.setMessage("위급상황 SOS서비스를 사용하기 위해서는 위치 서비스 기능이 켜져 있어야 합니다. WI-FI 및 모바일 네트워크 위치를 켜주세요.\n설정으로 이동하시겠습니까?");
//		alertDialog.setMessage("위급상황 SOS서비스를 사용하기 위해서는 위치 서비스 기능이 켜져 있어야 합니다. GPS, WI-FI 및 모바일 네트워크 위치를 켜주세요.\n설정으로 이동하시겠습니까?");
//
//		alertDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog,int which) {
//				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//				startActivity(intent);
//			}
//		});
//
//		alertDialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.cancel();
//			}
//		});
//
//		alertDialog.show();
	}

	private void showGPSSettingsAlert(final int result) {
//		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
//		alertDialog.setTitle("위치 서비스 설정");
//
//		alertDialog.setMessage("위급상황 SOS서비스를 사용하기 위해서는 휴대전화에 Google Play 서비스가 설치되어야 합니다. 설치하시겠습니까?");
//
//		alertDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog,int which) {
//				GooglePlayServicesUtil.getErrorDialog(result, TabMain.this, 0, new DialogInterface.OnCancelListener()
//				{
//					@Override
//					public void onCancel(DialogInterface dialog)
//					{
//
//					}
//				}).show();
//			}
//		});
//
//		alertDialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.cancel();
//			}
//		});
//
//		alertDialog.show();
	}

	private void showWIFISettingsAlert() {
//		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
//		alertDialog.setTitle("위치 서비스 설정");
//
//		alertDialog.setMessage("Wi-Fi 고급설정의\n \"Google의 위치 서비스 및 다른 앱이 Wi-Fi가 꺼졌을때도 네트워크를 검색하도록 허용함\" 옵션을 활성화 해야합니다.");
//
//		alertDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog,int which) {
//				Intent intent = new Intent(android.provider.Settings.ACTION_WIFI_IP_SETTINGS);
//				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				startActivity(intent);
//			}
//		});
//
//		alertDialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.cancel();
//			}
//		});
//
//		alertDialog.show();
	}

	@Override
	protected void onResume() {
		if (Utils.FRAGMENTACTIVITY1 == null) {
			Utils.FRAGMENTACTIVITY1 = this;
		}
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		if (Utils.TEST) {
			Log.d("hsh", "TabMain onDestroy()");
		}
		super.onDestroy();
	}

	public class GetMain {
		public void getMain() {
			getMainData();
		}
	}

	public void getMainData() {
//		if(Utils.pref.getString("USER_NUMBER", "").equals("")){
//			return;
//		}
//		String param = getString(R.string.mainScreen_Request, Utils.INSURESCODE, Utils.pref.getString("USER_NUMBER", ""), Utils.pref.getString("CID", ""));
//		if(Utils.TEST){
//			Log.i(Utils.TAG, "TabMain param : "+param);
//		}
//		HttpAsyncTask rssTask = new HttpAsyncTask(this, Utils.pref.getString("apiURL", "")+Utils.URL_STR);
//		rssTask.setParam(param);
//		rssTask.execute();
	}

	@Override
	public void onBackPressed() {
		String a = AppManageHelper.getName(ac);
		if (Utils.TEST) {
			Log.d("hsh", "a: " + a);
		}
//		AppManageHelper.footBack(ac);
//		if(a.equals(getString(R.string.service_req))
//				|| a.equals(getString(R.string.healthContents))
//				|| a.equals(getString(R.string.notice))){
//			getMainData();
//		}else if(a.equals(getString(R.string.woman_cycle))){
//			if(Utils.TEST){
//				Log.d("hsh", "tab main onBackPressed");
//			}
//		}else if(a.equals(getString(R.string.safe_str03))){ //연락받을 전화 번호
//			if(Utils.TEST){
//				Log.d("hsh", "sms onBackPressed");
//			}
//		}else if(a.equals(getString(R.string.messageStr026))){ //SMS 입력내용
//			if(Utils.TEST){
//				Log.d("hsh", "sms onBackPressed");
//			}
//		}
	}

	private void getHealthContent() {
//		String param = getString(R.string.mainScreen_Request2, "104", Utils.pref.getString("USER_NUMBER", ""), Utils.pref.getString("CID", ""), "1");
//		if(Utils.TEST){
//			Log.i(Utils.TAG, "Logo param : "+param);
//		}
//		HttpAsyncTask rssTask = new HttpAsyncTask(this, Utils.pref.getString("apiURL", "")+Utils.URL_STR);
//		rssTask.setParam(param);
//		rssTask.execute();
	}



	public static float getDensity(Context con) {
		float density = 0.0f;
		density = con.getResources().getDisplayMetrics().density;

		if (Utils.TEST)
			Log.d(Utils.TAG, "density = " + density);
		return density;
	}

	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
//		if(firstOne){
//			ScrollView ScrollView01 =  (ScrollView) findViewById(R.id.ScrollView01);
//			LinearLayout   main_tab     =  (LinearLayout) findViewById(R.id.main_tab2);
//			if(ScrollView01.getLayoutParams().height > main_tab.getLayoutParams().height){
//				main_tab.getLayoutParams().height = ScrollView01.getLayoutParams().height;
//			}
//			firstOne  = false;
//		}
	}


	private boolean checkSelectTatget() {
		boolean b = false;
		if (c1.isChecked() && p1.length() != 0) {
			b = true;
		}
		if (c2.isChecked() && p2.length() != 0) {
			b = true;
		}
		if (c2.isChecked() && p2.length() != 0) {
			b = true;
		}
		return b;
	}

	private void setToggle() {
		Toast.makeText(TabMain.this, "대상자와 체크박스 선택후 저장 해주세요.", Toast.LENGTH_LONG).show();
//		togglebutton.setImageResource(R.drawable.switch_off);
//		toggle = false;
	}

	private void setService() {
		if (!getServiceTaskName()) {
//			accelservie = new Intent(this,accelService.class);
//			accelservie.setPackage(getPackageName());
		} else {
//			accelservie = new Intent(this,accelService.class);
			accelservie.setPackage(getPackageName());
			startService(accelservie);
			toggle = true;
		}
	}

	private boolean getServiceTaskName() {
		boolean checked = false;
		ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> info;
		info = am.getRunningServices(Integer.MAX_VALUE);

		for (Iterator iterator = info.iterator(); iterator.hasNext(); ) {
			RunningServiceInfo runningTaskInfo = (RunningServiceInfo) iterator.next();
			//			Log.i("hsh", "Service name :"+runningTaskInfo.service.getClassName());

//			if (runningTaskInfo.service.getClassName().equals("com.gchealthcare.app.sos.accelService")) {
//				checked = true;
//				if (Utils.TEST) {
//					Log.i("hsh", "Service is.... : " + checked);
//				}
//			}
		}
		return checked;
	}

	private void savePref(int id) {
//		SharedPreferences preferences = getSharedPreferences(preferencesINFO.preferencesName, Activity.MODE_PRIVATE);
//		SharedPreferences.Editor editorPreferences = preferences.edit();
//		switch(id){
//		case R.id.save1:
//			editorPreferences.putString(preferencesINFO.pPhoneNum1, p1.getText().toString());
//			editorPreferences.putBoolean(preferencesINFO.pPhoneCheck1, c1.isChecked());
//			break;
//		case R.id.save2:
//			editorPreferences.putString(preferencesINFO.pPhoneNum2, p2.getText().toString());
//			editorPreferences.putBoolean(preferencesINFO.pPhoneCheck2, c2.isChecked());
//			break;
//		case R.id.save3:
//			editorPreferences.putString(preferencesINFO.pPhoneNum3, p3.getText().toString());
//			editorPreferences.putBoolean(preferencesINFO.pPhoneCheck3, c3.isChecked());
//			break;
//		}
//		editorPreferences.commit();
	}

	private void getPref() {
//		SharedPreferences preferences = getSharedPreferences(preferencesINFO.preferencesName, Activity.MODE_PRIVATE);
//		String pNum1 = preferences.getString(preferencesINFO.pPhoneNum1, "");
//		boolean bCheckBox1 = preferences.getBoolean(preferencesINFO.pPhoneCheck1, false);
//		p1.setText(pNum1);
//		c1.setChecked(bCheckBox1);
//
//		String pNum2 = preferences.getString(preferencesINFO.pPhoneNum2, "");
//		boolean bCheckBox2 = preferences.getBoolean(preferencesINFO.pPhoneCheck2, false);
//		p2.setText(pNum2);
//		c2.setChecked(bCheckBox2);
//
//		String pNum3 = preferences.getString(preferencesINFO.pPhoneNum3, "");
//		boolean bCheckBox3 = preferences.getBoolean(preferencesINFO.pPhoneCheck3, false);
//		p3.setText(pNum3);
//		c3.setChecked(bCheckBox3);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		int id_search = requestCode;
		if (resultCode == RESULT_OK) {
			Cursor cursor = getContentResolver().query(data.getData(),
					new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
							ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
			cursor.moveToFirst();

			String str = cursor.getString(1).replace("-", "");
			switch (id_search) {
				case 0:
					p1.setText(str);
					break;
				case 1:
					p2.setText(str);
					break;
				case 2:
					p3.setText(str);
					break;
			}

			cursor.close();
		}


	}


}