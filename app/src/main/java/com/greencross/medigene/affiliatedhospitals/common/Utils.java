package com.greencross.medigene.affiliatedhospitals.common;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TabHost;


/**
 * 공통 사용 메소드 모음
 *
 */
public class Utils {

	/** 구글계정 프로젝트 번호 **/
//	public static final String SENDER_ID = "604504064704";
	public static final String SENDER_ID = "177778920885";
	/** 보험사코드 **/
	public static final String INSURESCODE="103";
	/** API URL STR**/
	public static final String URL_STR="/mobile_Call";
	public static int NOTI_ID = 0;
	public static int NOTI_ID2 = 0;
	/** 
	 * 수정 사항
	 * 수정사항1 : LoginActivity 56라인 DeviceToken 수정
	 * 수정내용 : 현재 테스트를 위해 단말기의 디바이스 토큰(안드로이드는 RegID)대신 DeviceToken 이라는 문자 보냄
	 * 
	 * 수정사항2 : LoginActivity 116라인
	 * 로그인 협의 후 완료 URL 수정
	 */

	public static String TAG="GCHEALTHCARE";
	public static boolean TEST = true;
	public static List<View> Sview = null;
	public static FragmentActivity FRAGMENTACTIVITY1;
	public static FragmentActivity FRAGMENTACTIVITY2;
	public static FragmentActivity FRAGMENTACTIVITY3;
	public static FragmentActivity FRAGMENTACTIVITY4;
	public static int TABCNT=0;
	public static boolean VERSIONCHECK = true;		//버전이 같은지 틀린지
	public static ScrollView SVIEW;					//메인화면 스크롤뷰
	public static int displayWidth;					//화면 크기 가로
	public static int displayHeight;				//화면 크기 세로
	public static Intent AINTENT;					//메인탭 Intent
	public static SharedPreferences pref;
	public static SharedPreferences.Editor editor;
	public static ProgressBar progressBar;	//진행바
	public static TabHost tabHost;	//Tab 메뉴 관련
	public static EditText editText1; //글쓰기 EDIT BOX
	public static boolean checkOk = false; //고객의 소리 관련 flag
	public static String regId = "";
	public static String NOW_VERSION = "";
	public static String NEW_VERSION = "";


	public static List<ImageButton> imageButtonList;
	public static List<ImageView> imageViewList;
	public static int rectHeight;
	public static LinearLayout tablinear;
	public static int tablinearHeight;

	/**
	 *
	 * Utils.pref.getBoolean("pushcheck", true) //푸쉬알림 받을지 말지
	 * Utils.pref.getBoolean("magicalram" , true) //가임기 배란일 알림 받을지 말지
	 * Utils.pref.getBoolean("safecomehomeonoff" , true) //안심귀가 알림 받을지 말지
	 * 
	 * 

 	Utils.editor.putString("CMPNY_CODE", CMPNY_CODE);
	Utils.editor.putString("CMPNY_NM", CMPNY_NM);
	Utils.editor.putString("CMPNY_ARS", CMPNY_ARS);
	Utils.editor.putString("CMPNY_IMAGE", CMPNY_IMAGE);
	Utils.editor.putString("loginURL", loginURL);//로그인체크용 URL
	Utils.editor.putString("apiURL", apiURL);//api 통신용 URL
	Utils.editor.putString("appVersion", appVersion);
	Utils.editor.putString("updateURL", updateURL);//앱스토어 주소
	Utils.editor.putString("CMPNY_FILE_COURS", CMPNY_FILE_COURS);
	Utils.editor.putString("imageURL", imageURL);

	Utils.pref.getString("CID", "");
	Utils.pref.getString("USER_NUMBER", "");


 	Utils.pref.getString("REGID", "")
	Utils.pref.getString("imageURL", "");//고객의소리 통신용 URL
	Utils.pref.getString("introductURL", "");//헬스케어 소개 연결 URL

	//매직주기
	Utils.pref.getString("magictimeyear", today.year+"")
	Utils.pref.getString("magictimemonth", (today.month+1)+"")
	Utils.pref.getString("magictimedate", today.monthDay+"")

	Utils.editor.putString("magic_period" , checkMax.getPeriod());
	Utils.editor.putString("magic_childbearing" , checkMax.getCycle());
	Utils.editor.putString("magic_yyyy" , checkMax.getYmd().substring(0, 4));
	Utils.editor.putString("magic_mm" , checkMax.getYmd().substring(4, 6));
	Utils.editor.putString("magic_dd" , checkMax.getYmd().substring(6, 8));
	Utils.editor.putBoolean("magicalram" , togglebutton.isChecked());

	Utils.editor.putString("magic_averageCycle" , cycleslast+"");

	//안심귀가
	Utils.pref.getString("saftComeHomePassWord", "");
	Utils.editor.putBoolean("safecomehomeonoff", false);
	Utils.editor.putInt("safecomehometime", returnhometime);
	Utils.editor.putString("safecomehometimestr", returnhometimeStr);
	Utils.editor.putString("safecomehomephone", phonenumber);
	Utils.editor.putString("savecometimeyear", datePicker1.getYear()+"");
	Utils.editor.putString("savecometimemonth", (datePicker1.getMonth()+1)+"");
	Utils.editor.putString("savecometimedate", datePicker1.getDayOfMonth()+"");
	 **/
	
	public static void init(Activity activity){
		pref = activity.getSharedPreferences("pref", Activity.MODE_PRIVATE);
		editor = pref.edit(); 
	}

	/**
	 * 다이아로그 출력
	 * @param con 해당화면 Context
	 * @param msg 출력할 메세지
	 */
	public static void dialog(Context con, String msg) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);
		alertDialogBuilder.setTitle("");
		alertDialogBuilder.setMessage(msg);
		alertDialogBuilder.setPositiveButton("확인",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		alertDialogBuilder.setCancelable(true);
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}



	/**
	 * 날짜 형식 변환
	 * @param date 날짜 문자
	 * @return TimeStemp
	 */
	@SuppressLint("SimpleDateFormat")
	public long time_converter_long(String date){
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date datetime = null;
		try {
			datetime = dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();

		c.set(Calendar.DAY_OF_MONTH, datetime.getDay());
		c.set(Calendar.MONTH, datetime.getMonth());;
		c.set(Calendar.YEAR, datetime.getYear());
		c.set(Calendar.HOUR,9);
		c.set(Calendar.MINUTE,0);

		return c.getTime().getTime();
	}

	// 알람설정
	//	public void alarmSet(Context con, String servieName, long time) {
	//
	//		PendingIntent pendingIntent = PendingIntent.getService(con, 0,
	//				new Intent().setComponent(new ComponentName(
	//						"com.gchealthcare.app", "com.gchealthcare.app."
	//								+ servieName)),
	//								PendingIntent.FLAG_UPDATE_CURRENT);
	//		long strPlusTime = 0;
	//		if (strPlusTime < 0)
	//			strPlusTime = 0;
	//		int setTime = (int) (20);
	//		Calendar calendar = Calendar.getInstance();
	//		calendar.setTimeInMillis(time);
	//		calendar.add(Calendar.SECOND, setTime);
	//		AlarmManager alarmManager = (AlarmManager) con
	//				.getSystemService(Context.ALARM_SERVICE);
	//		alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
	//				pendingIntent);
	//	}


	/**
	 * Html 유니코드 변환
	 * @param data
	 * @return String
	 */
	public static String getHTML(String data) {
		String jsonData = data.replaceAll("&amp;", "&").replaceAll("&lt;", "<").replaceAll("&gt;", ">");
		return jsonData;
	}
	/**
	 * 서버로 부터 받은 Json 형식 중 필요없는 부분 제거
	 * @param data 서버로 부터 받은 문자
	 * @return json
	 */
	public static String getJosonDataFromXML(String data) {
		String jsonData = data.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?><string xmlns=\"http://tempuri.org/\">", "").replace("</string>", "");
		if(jsonData.equals("")){
			return "";
		}
		return jsonData;
//		try {
//			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//			DocumentBuilder builder = factory.newDocumentBuilder();
//			Document doc = builder.parse(new InputSource(new StringReader(data)));
//			NodeList headNodeList = doc.getChildNodes();
//			Element subItem = (Element) headNodeList.item(0);
//			String jsonData = subItem.getChildNodes().item(0).getNodeValue();
//			return jsonData;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}		
//		return "";
	}
	
	/**
	 * 게시물의 전체 수를 이용해 전체 페이지 수를 산출
	 * @param data
	 * @return int
	 */
	public static int getMaxPageNum(String data) {
		int result = 0;
		try {
			int param = Integer.parseInt(data);
			result = param / 10;
			Log.e(Utils.TAG, "result : "+result);
			if (param % 10 != 0) {
				result = result + 1;
			}
			Log.e(Utils.TAG, "result : "+result);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return result;
	}	

	/** 상담원 연결 시간 체크 */
	//	public static boolean checkTime(){
	//		boolean check = false;
	//		long now = System.currentTimeMillis();
	//
	//		Calendar scal =  Calendar.getInstance();
	//		scal.set(scal.get(Calendar.YEAR), scal.get(Calendar.MONTH), scal.get(Calendar.DAY_OF_MONTH), 9, 0);
	//		long smilliSecond = scal.getTimeInMillis();
	//
	//		Calendar ecal =  Calendar.getInstance();
	//		ecal.set(ecal.get(Calendar.YEAR), ecal.get(Calendar.MONTH), ecal.get(Calendar.DAY_OF_MONTH), 11, 0);
	//		long emilliSecond = ecal.getTimeInMillis();
	//
	//		if(smilliSecond < now && now < emilliSecond){
	//			check = true;
	//		}
	//
	//		return check;
	//	}
	/**
	 * 리스트뷰 이미지 표시 중 사용
	 * @param is
	 * @param os
	 */
	public static void CopyStream(InputStream is, OutputStream os){
		final int buffer_size=1024;
		try{
			byte[] bytes=new byte[buffer_size];
			for(;;){
				int count=is.read(bytes, 0, buffer_size);
				if(count==-1)
					break;
				os.write(bytes, 0, count);
			}
		}catch(Exception ex){
			if(Utils.TEST){
				Log.e("Exception", "Utils convertDipsToPixels Exception");
			}
		}
	}
	/**
	 * 한자리 숫자를 두자리 문자로 변환
	 * @param a
	 * @return String
	 */
	public static String zeroTwo(int a){
		String result = Integer.toString(a);
		if(a < 10){
			result = "0"+a;
		}
		return result;
	}
	/**
	 * 날짜 형식 변환(월 일)
	 * @param time
	 * @return String
	 */
	public static String dateFormatStr(String time){
		String timeform = "";
		timeform = time.substring(4,6)+"월 "+time.substring(6,8)+"일 ("+time.substring(0,4)+")";
		return timeform;
	}
	/**
	 * 날짜 형식 변환(..)
	 * @param time
	 * @return String
	 */
	public static String dateFormat(String time){
		String timeform = "";
		timeform = time.substring(0,4)+"."+time.substring(4,6)+"."+time.substring(6,8);
		return timeform;
	}
	/**
	 * 날짜 형식 변환(.. 오전 : )
	 * @param time
	 * @return String
	 */
	public static String timeFormat(String time){
		String timeform = "";
		try {
			timeform = time.substring(0,4)+"."+time.substring(4,6)+"."+time.substring(6,8);
			int hour = Integer.valueOf(time.substring(8,10));
			if(hour > 12){
				timeform = timeform + ", "+"오후 "+ (hour-12) +":"+ time.substring(10,12);
			}else{
				timeform = timeform + ", "+"오전 "+ (hour-12) +""+ time.substring(10,12);
			}
		} catch (Exception e) {
		}
		if(timeform.equals("")){
			timeform = time;
		}
		return timeform;
	}
	/**
	 * 날짜 형식 변환(년 월 일 오전 : )
	 * @param time
	 * @return String
	 */
	public static String timeFormatStra(String time){
		String timeform = "";
		try {
			timeform = time.substring(0,4)+"년 "+time.substring(4,6)+"월 "+time.substring(6,8)+"일";
			int hour = Integer.valueOf(time.substring(8,10));
			if(hour > 12){
				timeform = timeform + " "+"오후  "+ zeroTwo((hour-12)) +":"+ time.substring(10,12);
			}else{
				timeform = timeform + " "+"오전  "+ zeroTwo((hour)) +":"+ time.substring(10,12);
			}
		} catch (Exception e) {
		}
		if(timeform.equals("")){
			timeform = time;
		}
		return timeform;
	}
	/**
	 * 날짜 형식 변환(.. 오전 : )
	 * @param time
	 * @return String
	 */
	public static String timeFormatStr(String time){
		String timeform = "";
		try {
			timeform = time.substring(0,4)+". "+time.substring(4,6)+". "+time.substring(6,8)+".";
			int hour = Integer.valueOf(time.substring(8,10));
			if(hour > 12){
				timeform = timeform + " "+"오후  "+ zeroTwo((hour-12)) +":"+ time.substring(10,12);
			}else{
				timeform = timeform + " "+"오전  "+ zeroTwo((hour)) +":"+ time.substring(10,12);
			}
		} catch (Exception e) {
		}                    
		if(timeform.equals("")){
			timeform = time;
		}
		return timeform;
	}
	/**
	 * 시간 형식 변환(오전 :)
	 * @param time
	 * @return String
	 */
	public static String timeOnlyFormatStr(String time){
		String timeform = "";
		try {
			int hour = Integer.valueOf(time.substring(0,2));
			if(hour > 12){
				timeform = timeform + " "+"오후  "+ zeroTwo((hour-12)) +":"+ time.substring(2,4);
			}else{
				timeform = timeform + " "+"오전  "+ zeroTwo((hour)) +":"+ time.substring(2,4);
			}
		} catch (Exception e) {
		}
		if(timeform.equals("")){
			timeform = time;
		}
		return timeform;
	}
	
	/**
	 * 날짜 형식변환(년 월 일 요일)
	 * @param time
	 * @return String
	 */
	@SuppressLint("SimpleDateFormat")
	public static String timeFormatStrK(String time){
		String timeform = "";
		try {
			timeform = time.substring(0,4)+"년 "+time.substring(4,6)+"월 "+time.substring(6,8)+"일 ";

			Date dateTemp = null;
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String dates = time.substring(0,4)+time.substring(4,6)+time.substring(6,8);
			try {
				dateTemp = dateFormat.parse(dates);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			Calendar cals = Calendar.getInstance();
			cals.setTime(dateTemp);
			int week = cals.get(Calendar.DAY_OF_WEEK);
			String weekStr = "";
			switch (week){
			case 1:
				weekStr = "일요일";
				break;
			case 2:
				weekStr = "월요일";
				break;
			case 3:
				weekStr = "화요일";
				break;
			case 4:
				weekStr = "수요일";
				break;
			case 5:
				weekStr = "목요일";
				break;
			case 6:
				weekStr = "금요일";
				break;
			case 7:
				weekStr = "토요일";
				break;
			}
			timeform = timeform + " "+weekStr;

		} catch (Exception e) {
		}
		if(timeform.equals("")){
			timeform = time;
		}
		return timeform;
	}
	/** 안심귀가 알람 등록 */
	public static void setAlarm(Context ac, String name, Time time, int index){
		String classname = "AlarmPopupService";
		if(name.equals("SAFE_ACTION01")){
			classname = "AlarmPopupServiceS1";
		}else if(name.equals("SAFE_ACTION02")){
			classname = "AlarmPopupServiceS2";
		}else{
			classname = "AlarmPopupServiceS3";
		}
			
		Intent intent = new Intent().setComponent(new ComponentName("com.gchealthcare.app",	"com.gchealthcare.app."+ classname));
		intent.setAction(name);
		intent.putExtra("type", name);
		PendingIntent sender = PendingIntent.getService(ac, index, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager am = (AlarmManager) ac.getSystemService(Context.ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, time.toMillis(false), sender);
	}
	/** 안심귀가 알람 체크 */
	public static boolean checkAlarm(Context ac, String name, int index){
		String classname = "AlarmPopupService";
		if(name.equals("SAFE_ACTION01")){
			classname = "AlarmPopupServiceS1";
		}else if(name.equals("SAFE_ACTION02")){
			classname = "AlarmPopupServiceS2";
		}else{
			classname = "AlarmPopupServiceS3";
		}
		Intent intentToSend = new Intent().setComponent(new ComponentName("com.gchealthcare.app",	"com.gchealthcare.app."+ classname));
		intentToSend.setAction(name);
		PendingIntent pIntent = PendingIntent.getService(ac, index, intentToSend, PendingIntent.FLAG_UPDATE_CURRENT);
		return pIntent != null;
	}
	/** 안심귀가 알람 삭제 */
	public static void removeAlarm(Context ac, String name, int index){
		String classname = "AlarmPopupService";
		if(name.equals("SAFE_ACTION01")){
			classname = "AlarmPopupServiceS1";
		}else if(name.equals("SAFE_ACTION02")){
			classname = "AlarmPopupServiceS2";
		}else{
			classname = "AlarmPopupServiceS3";
		}
		Intent intentToSend = new Intent().setComponent(new ComponentName("com.gchealthcare.app",	"com.gchealthcare.app."+ classname));
		intentToSend.setAction(name);
		PendingIntent pIntent = PendingIntent.getService(ac, index, intentToSend, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager am = (AlarmManager) ac.getSystemService(Context.ALARM_SERVICE);
		boolean result = pIntent != null;
		if(result){
			pIntent.cancel();
			am.cancel(pIntent);
		}
	}
	/** 안심귀가 SMS 설정 알람 등록 */
	public static void setAlarmService(Context ac, String name, Time time, int index) {
		Intent intent = new Intent().setComponent(new ComponentName("com.gchealthcare.app",	"com.gchealthcare.app."+ "AlarmPopupServiceS4"));
		intent.setAction(name);
		intent.putExtra("type", name);
		PendingIntent sender = PendingIntent.getService(ac, index, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager am = (AlarmManager) ac.getSystemService(Context.ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, time.toMillis(false), sender);
	}
	/** 안심귀가 SMS 설정 알람 체크 */
	public static boolean checkAlarmService(Context ac, String name, int index){
		Intent intentToSend = new Intent().setComponent(new ComponentName("com.gchealthcare.app",	"com.gchealthcare.app."+ "AlarmPopupServiceS4"));
		intentToSend.setAction(name);
		PendingIntent pIntent = PendingIntent.getService(ac, index, intentToSend, PendingIntent.FLAG_UPDATE_CURRENT);
		return pIntent != null;
	}
	/** 안심귀가 SMS 설정 알람 삭제 */
	public static void removeAlarmService(Context ac, String name, int index){
		Intent intentToSend = new Intent().setComponent(new ComponentName("com.gchealthcare.app",	"com.gchealthcare.app."+ "AlarmPopupServiceS4"));
		intentToSend.setAction(name);
		PendingIntent pIntent = PendingIntent.getService(ac, index, intentToSend, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager am = (AlarmManager) ac.getSystemService(Context.ALARM_SERVICE);
		boolean result = pIntent != null;
		if(result){
			pIntent.cancel();
			am.cancel(pIntent);
		}
	}
	/** 매직주기 알림 등록 (예상가임기, 예상 배란일)*/
	public static void setMagicAlarm(Context ac, String name, Time time, int cycle, int index){
		if(name.equals("MAGIC_ACTION02")){
			Intent intent = new Intent().setComponent(new ComponentName("com.gchealthcare.app",	"com.gchealthcare.app."+ "AlarmPopupServiceM2"));
			intent.setAction(name);
			intent.putExtra("type", name);
			PendingIntent sender = PendingIntent.getService(ac, index, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			AlarmManager am = (AlarmManager) ac.getSystemService(Context.ALARM_SERVICE);
			am.setRepeating(AlarmManager.RTC_WAKEUP, time.toMillis(false),1000 * 60 * 60 * 24*cycle,sender);
		}else{
			Intent intent = new Intent().setComponent(new ComponentName("com.gchealthcare.app",	"com.gchealthcare.app."+ "AlarmPopupServiceM1"));
			intent.setAction(name);
			intent.putExtra("type", name);
			PendingIntent sender = PendingIntent.getService(ac, index, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			AlarmManager am = (AlarmManager) ac.getSystemService(Context.ALARM_SERVICE);
			am.setRepeating(AlarmManager.RTC_WAKEUP, time.toMillis(false),1000 * 60 * 60 * 24*cycle,sender);
		}
	}
	/** 매직주기 알림 체크 (예상가임기, 예상 배란일)*/
	public static boolean checkMagicAlarm(Context ac, String name, int index){
		if(name.equals("MAGIC_ACTION02")){
			Intent intentToSend = new Intent().setComponent(new ComponentName("com.gchealthcare.app",	"com.gchealthcare.app."+ "AlarmPopupServiceM2"));
			intentToSend.setAction(name);
			PendingIntent pIntent = PendingIntent.getService(ac, index, intentToSend, PendingIntent.FLAG_UPDATE_CURRENT);
			return pIntent != null;
			
		}else{
			Intent intentToSend = new Intent().setComponent(new ComponentName("com.gchealthcare.app",	"com.gchealthcare.app."+ "AlarmPopupServiceM1"));
			intentToSend.setAction(name);
			PendingIntent pIntent = PendingIntent.getService(ac, index, intentToSend, PendingIntent.FLAG_UPDATE_CURRENT);
			return pIntent != null;
			
		}
		
	}
	/** 매직주기 알림 삭제 (예상가임기, 예상 배란일)*/
	public static void removeMagicAlarm(Context ac, String name, int index){
		if(name.equals("MAGIC_ACTION02")){
			Intent intentToSend = new Intent().setComponent(new ComponentName("com.gchealthcare.app",	"com.gchealthcare.app."+ "AlarmPopupServiceM2"));
			intentToSend.setAction(name);
			PendingIntent pIntent = PendingIntent.getService(ac, index, intentToSend, PendingIntent.FLAG_UPDATE_CURRENT);
			AlarmManager am = (AlarmManager) ac.getSystemService(Context.ALARM_SERVICE);
			boolean result = pIntent != null;
			if(result){
				pIntent.cancel();
				am.cancel(pIntent);
			}
		}else{
			
			Intent intentToSend = new Intent().setComponent(new ComponentName("com.gchealthcare.app",	"com.gchealthcare.app."+ "AlarmPopupServiceM1"));
			intentToSend.setAction(name);
			PendingIntent pIntent = PendingIntent.getService(ac, index, intentToSend, PendingIntent.FLAG_UPDATE_CURRENT);
			AlarmManager am = (AlarmManager) ac.getSystemService(Context.ALARM_SERVICE);
			boolean result = pIntent != null;
			if(result){
				pIntent.cancel();
				am.cancel(pIntent);
			}
		}
	}

	public static String replaceEx(String string) {
		String result = string.replaceAll("\r\n","\r\n");
		return result;
	}

	public static void init(Context arg0) {
		pref = arg0.getSharedPreferences("pref", Activity.MODE_PRIVATE);
		editor = pref.edit(); 
		
	}

	/**
	 * 안심귀가 알람 설정
	 * @param ac
	 * @param returnhometime
	 * @param dd
	 * @param mm
	 * @param yy
	 */
	public static void setSafeA(Context ac, int returnhometime, int dd, int mm, int yy) {
		if(TEST){
			Log.e(Utils.TAG, "0 : "+yy);
			Log.e(Utils.TAG, "0 : "+mm);
			Log.e(Utils.TAG, "0 : "+dd);
			Log.e(Utils.TAG, "0 : "+returnhometime);
		}
		Time nowTime = new Time(Time.getCurrentTimezone());
		Time nowTime2 = new Time(Time.getCurrentTimezone());
		Time nowTime3 = new Time(Time.getCurrentTimezone());
		nowTime.set(System.currentTimeMillis());
		nowTime2.set(System.currentTimeMillis());
		nowTime3.set(System.currentTimeMillis());
		Time setTime = new Time(Time.getCurrentTimezone());
		int a = 0;
		int b = 10;
		int c = 20;
		int d = 30;
		setTime.set(0, a, returnhometime+1, dd, mm, yy);
		if(TEST){
			Log.e(Utils.TAG, "0 : 1");
		}
		if(nowTime.toMillis(true) < setTime.toMillis(true)){
			if(TEST){
				Log.e(Utils.TAG, "0 : 2");
			}
			nowTime.set(0, a, returnhometime+1, dd, mm, yy);
//			Log.i("tt3", nowTime.minute +" "+ nowTime.hour+" "+  nowTime.month+" "+nowTime.MONTH_DAY+" "+nowTime.monthDay);
			Utils.setAlarm(ac, "SAFE_ACTION01", nowTime,2);

			nowTime2.set(0, b, returnhometime+1, dd, mm, yy);
			Utils.setAlarm(ac, "SAFE_ACTION02", nowTime2,3);

			nowTime3.set(0, c, returnhometime+1, dd, mm, yy);
			Utils.setAlarm(ac, "SAFE_ACTION03", nowTime3,4);

			nowTime3.set(0, d, returnhometime+1, dd, mm, yy);
			Utils.setAlarmService(ac, "SAFE_ACTION04", nowTime3,5);
		}
	}
	/**
	 * 매직데이 알람설정
	 * @param ac
	 * @param cycleslast
	 * @param datefirstStr
	 */
	@SuppressLint("SimpleDateFormat")
	public static void setmagicA(Context ac, int cycleslast ,String datefirstStr) {
		Date datefirst = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		try {
			datefirst= dateFormat.parse(datefirstStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar caltemp = Calendar.getInstance();
		caltemp.setTime(datefirst);
		if(Utils.TEST){
			Log.e("최근생리날짜", caltemp.get(Calendar.DAY_OF_MONTH)+" "+ caltemp.get(Calendar.MONTH)+" "+  caltemp.get(Calendar.YEAR));
		}
		caltemp.add(Calendar.DATE, cycleslast - 1);
		caltemp.add(Calendar.DATE, -14);
		Time nowTime = new Time(Time.getCurrentTimezone());
		nowTime.set(System.currentTimeMillis());

		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		today.hour = 0;
		today.minute = 0;
		//							if(Utils.TEST){
		//								
		//								nowTime.set(today.second+10, today.minute+2, today.hour, today.monthDay, today.month, today.year);
		//								setMagicAlarm(ac, "MAGIC_ACTION01", nowTime, cycleslast);
		//							}else{
		caltemp.set(caltemp.get(Calendar.YEAR), caltemp.get(Calendar.MONTH), caltemp.get(Calendar.DAY_OF_MONTH), 9, 00);
		while(caltemp.getTimeInMillis() < today.toMillis(true)){
			caltemp.add(Calendar.DATE, cycleslast - 1);
		}
		if(Utils.TEST){
			Log.i("tt1", caltemp.get(Calendar.DAY_OF_MONTH)+" "+ caltemp.get(Calendar.MONTH)+" "+  caltemp.get(Calendar.YEAR)+" "+caltemp.get(Calendar.DAY_OF_MONTH)+" "+" "+caltemp.get(Calendar.HOUR_OF_DAY)+" "+" "+caltemp.get(Calendar.MINUTE)+" ");
		}
		nowTime.set(0, 00, 9, caltemp.get(Calendar.DAY_OF_MONTH), caltemp.get(Calendar.MONTH), caltemp.get(Calendar.YEAR));
		if(Utils.TEST){
			Log.i("tt2", today.minute +" "+ today.hour+" "+  today.month+" "+today.monthDay);
			Log.i("tt3", nowTime.minute +" "+ nowTime.hour+" "+  nowTime.month+" "+nowTime.monthDay);
		}
		setMagicAlarm(ac, "MAGIC_ACTION02", nowTime, cycleslast,1);
		//							}

		caltemp.setTime(datefirst);
		caltemp.add(Calendar.DATE, cycleslast - 1);
		caltemp.add(Calendar.DATE, -14);
		caltemp.add(Calendar.DATE, -5);
		caltemp.set(caltemp.get(Calendar.YEAR), caltemp.get(Calendar.MONTH), caltemp.get(Calendar.DAY_OF_MONTH), 9, 00);
		Time nowTime2 = new Time(Time.getCurrentTimezone());
		nowTime2.set(System.currentTimeMillis());

		while(caltemp.getTimeInMillis() < today.toMillis(false)){
			caltemp.add(Calendar.DATE, cycleslast - 1);
		}
		if(Utils.TEST){
			Log.i("tt4", caltemp.get(Calendar.DAY_OF_MONTH)+" "+ caltemp.get(Calendar.MONTH)+" "+  caltemp.get(Calendar.YEAR));
		}
		nowTime2.set(0, 00, 9, caltemp.get(Calendar.DAY_OF_MONTH), caltemp.get(Calendar.MONTH), caltemp.get(Calendar.YEAR));
		if(Utils.TEST){
			Log.i("tt2", today.minute +" "+ today.hour+" "+  today.month+" "+today.monthDay);
			Log.i("tt3", nowTime2.minute +" "+ nowTime2.hour+" "+  nowTime2.month+" "+nowTime2.monthDay);
		}
		setMagicAlarm(ac, "MAGIC_ACTION01", nowTime2, cycleslast,0);
		
		Utils.editor.putString("datefirstStr", datefirstStr);
		Utils.editor.putInt("cycleslast", cycleslast);
		Utils.editor.commit();

		if(Utils.TEST){
			if(checkMagicAlarm(ac, "MAGIC_ACTION01",0)){
				Log.i("tt5", "aaa");
			}
			if(checkMagicAlarm(ac, "MAGIC_ACTION02",1)){
				Log.i("tt6", "aaa");
			}
		}
	}
	
	public static final String[] LOCATION_PERMS={
		Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
		Manifest.permission.GET_ACCOUNTS, Manifest.permission.SEND_SMS
	};
	
	public static boolean canAccessStorage(Context context){
		boolean b1= hasPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
		boolean b2 = hasPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
		boolean b3 = hasPermission(context, Manifest.permission.GET_ACCOUNTS);
		boolean b4 = hasPermission(context, Manifest.permission.SEND_SMS);
		if(Utils.TEST){
			Log.e("hsh", "b1 "+b1+" b2 "+b2+" b3 "+b3+" b4 "+b4);
		}
        return( b1 && b2 && b3 && b4);
    }
	public static boolean hasPermission(Context context, String perm) {
        return(PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context, perm));
    }

}
