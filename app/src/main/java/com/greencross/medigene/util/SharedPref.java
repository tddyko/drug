package com.greencross.medigene.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    private final String TAG = SharedPref.class.getSimpleName();
	
	private static SharedPref instance;
	private static Context mContext;
	
	public final String PREF_NAME = "greencross";

    public static String SAVED_LOGIN_ID = "saved_login_id";         // 저장된 아이디
    public static String IS_SAVED_LOGIN_ID = "is_saved_login_id";   // 아이디 저장 여부
    public static String SAVED_LOGIN_PWD = "saved_login_pwd";       // 저장된 아이디
    public static String IS_LOGIN_SUCEESS = "is_login_suceess";     // 로그인 성공 여부
    public static String IS_AUTO_LOGIN = "is_auto_login";           // 자동 로그인 여부

    public static String IS_DRUG_REQUEST = "is_drug_request";     // 검사 신청 여부

	public static SharedPref getInstance() {
		if (instance == null) {
			instance = new SharedPref();
		}
		return instance;
	}

	public void initContext(Context context) {
        mContext = context;
    }
	
	// 값 불러오기
    public String getPreferences(String key){
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.getString(key, "");
    }
    
 // 값 불러오기
    public int getPreferences(String key, int defValue){
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.getInt(key, defValue);
    }

    // 값 불러오기
    public float getPreferences(String key, float defValue){
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.getFloat(key, defValue);
    }
 // 값 불러오기
    public boolean getPreferences(String key, boolean defValue){
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.getBoolean(key, defValue);
    }
    
    // 값 저장하기
    public void savePreferences(String key, String val){
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, val);
        editor.commit();
    }
    
 // 값 저장하기
    public void savePreferences(String key, int val){
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, val);
        editor.commit();
    }

    // 값 저장하기
    public void savePreferences(String key, float val){
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat(key, val);
        editor.commit();
    }

    // 값 저장하기
    public void savePreferences(String key, boolean val){
        if (mContext == null) {
            Logger.e(TAG, "mContext is null");
        } else {
            SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean(key, val);
            editor.commit();
        }
    }
     
    // 값(Key Data) 삭제하기
    public void removePreferences(String key){
        if (mContext == null) {
            Logger.e(TAG, "mContext is null");
        } else {
            SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.remove(key);
            editor.commit();
        }
    }
     
    // 값(ALL Data) 삭제하기
    public void removeAllPreferences(){
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }
}
