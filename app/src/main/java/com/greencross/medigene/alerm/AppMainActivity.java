package com.greencross.medigene.alerm;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.greencross.medigene.R;
import com.greencross.medigene.util.Utils;

/**
 * 전체 탭을 관리하는 클래스
 *
 */
public class AppMainActivity extends TabActivity implements OnClickListener {
	private boolean mIsBackButtonTouched = false;

	private final int HOME_TAB = 0;
	private final int MAGIC_TAB = 1;
	private final int SECURITY_TAB = 2;
	private final int TWEET_TAB = 3;	
	private Activity ac;
	private boolean firstOne = true;
	private ImageView btnHome;
	private ImageView btnMagic;
	private ImageView btnSecurity;
	private ImageView btnTweet;

	int typePage = 1;

	LayoutInflater inflater; 
	LinearLayout linear;
	Dialog dialog;
	EditText p1;
	CheckBox c1;
	TextView selName;
	Intent accelservie;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		if(Utils.TEST){
			Log.i(Utils.TAG, "AppMainActivity");
		}

	}
	@Override
	protected void onResume() {
		super.onResume();
		ac = this;
	}
	
	@Override
	public void onClick(View v) {

	}	

	/**
	 * 선택된 Tab의 이미지를 변경하고 탭으로 이동시킨다.
	 * @param tabIndex
	 */
	private void setCurrenctTab(int tabIndex) {
		AppManageHelper.init(this);
	}    

	@SuppressLint("ShowToast")
	@Override
	public void onBackPressed() {
		if(Utils.TEST){
			Log.d("hsh", "onbackPressed");
		}

	}
	
	@Override
	protected void onDestroy() {
		if(Utils.TEST){
			Log.d("hsh","AppMainActivity onDestroy()");
		}
		super.onDestroy();
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if(firstOne){
			Rect rect = new Rect();
			Window win = getWindow();
			win.getDecorView().getWindowVisibleDisplayFrame(rect);
			Utils.rectHeight = rect.top;
			//Utils.tabHost.getLayoutParams().height = Utils.displayHeight-Utils.rectHeight;
			Utils.tablinearHeight = btnHome.getHeight();
			firstOne = false;
		}
	}

	public class goTab{
		public void got(int index){
			setCurrenctTab(index);
		}
	}

}