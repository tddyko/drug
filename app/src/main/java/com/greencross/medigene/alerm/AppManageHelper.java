package com.greencross.medigene.alerm;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.greencross.medigene.R;
import com.greencross.medigene.util.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 네비게이션바, 뒤로가기 버튼 처리
 *
 */
public class AppManageHelper {

	public static List<String> tap1_title = null;
	public static List<String> tap3_title = null;
	public static List<String> tap5_title = null;
	public static List<String> tap6_title = null;

	public static Button headRightBtn = null; 
	public static ImageView headRightBtnTT = null; 

	public enum TAB_MENU {MAIN, HEALTH, HOME, CAL}

	/**
	 * 초기 설정
	 * @param ac
	 */
	public static void init(final Activity ac) {
		tap1_title = new ArrayList<String>();
		tap3_title = new ArrayList<String>();
		tap5_title = new ArrayList<String>();
		tap6_title = new ArrayList<String>();
		tap1_title.add(ac.getString(R.string.home));
		tap3_title.add(ac.getString(R.string.service_req));
		tap5_title.add(ac.getString(R.string.appointment));
		tap6_title.add(ac.getString(R.string.reservation_health_screening));
	}

	public static void removeTitleTag(final FragmentActivity ac) {
		if (ac instanceof TabMain) {
			if (tap1_title.size() > 1)
				tap1_title.remove(tap1_title.size() - 1);
		} else if (ac instanceof ServiceReqStatusActivity) {
			if (tap3_title.size() > 0)
				tap3_title.remove(tap3_title.size() - 1);
		} else if (ac instanceof Appointment) {
			if (tap5_title.size() > 0)
				tap5_title.remove(tap5_title.size() - 1);
		}
		else if (ac instanceof Healthscreening) {
			if (tap6_title.size() > 0)
				tap6_title.remove(tap6_title.size() - 1);
		}

	}

	public static String getName(final FragmentActivity ac) {
		String result = "";
		if (ac instanceof TabMain) {
			if (tap1_title.size() != 0)
				result = tap1_title.get(tap1_title.size()-1);
		} else if (ac instanceof ServiceReqStatusActivity) {
			if (tap3_title.size() != 0)
				result = tap3_title.get(tap3_title.size()-1);
		} else if (ac instanceof Appointment) {
			if (tap5_title.size() != 0)
				result = tap5_title.get(tap5_title.size()-1);
		}
		else if (ac instanceof Healthscreening) {
			if (tap6_title.size() != 0)
				result = tap6_title.get(tap6_title.size()-1);
		}
		return result;
	}

	public static String getTabName(final FragmentActivity ac, int idx) {
		String result = "";
		if (idx >= 0) {
			try {
				if (ac instanceof TabMain) {
					if (tap1_title.size() != 0)
						result = tap1_title.get(idx);
				} else if (ac instanceof ServiceReqStatusActivity) {
					if (tap3_title.size() != 0)
						result = tap3_title.get(idx);
				} else if (ac instanceof Appointment) {
					if (tap5_title.size() != 0)
						result = tap5_title.get(idx);
				}else if (ac instanceof Healthscreening) {
					if (tap6_title.size() != 0){
						try {
							result = tap6_title.get(idx);
						} catch (Exception e) {
						}

					}
				}
			} catch (Exception e) {
				tap1_title = new ArrayList<String>();
				tap3_title = new ArrayList<String>();
				tap5_title = new ArrayList<String>();
				tap6_title = new ArrayList<String>();
				tap1_title.add(ac.getString(R.string.home));
				tap3_title.add(ac.getString(R.string.service_req));
				tap5_title.add(ac.getString(R.string.appointment));
				tap6_title.add(ac.getString(R.string.reservation_health_screening));
				if (ac instanceof TabMain) {
					Utils.goTab.got(0);
				} else if (ac instanceof ServiceReqStatusActivity) {
					Utils.goTab.got(3);
				} else if (ac instanceof Appointment) {
					Utils.goTab.got(2);
				}else if (ac instanceof Healthscreening) {
					Utils.goTab.got(1);
				}
			}

		}
		return result;
	}

	public static void footBack(final FragmentActivity ac) {
		if(Utils.TEST)
			Log.d("hsh", "footBack: "+ac);
		FragmentManager fm = ac.getSupportFragmentManager();
		if ((ac instanceof TabMain)) {
			Utils.SVIEW.setVisibility(View.VISIBLE);
			final LinearLayout headNavLayout = (LinearLayout)ac.findViewById(R.id.headNav);
			final Button backBtn = (Button) ac.findViewById(R.id.headBackBtn);
			final TextView topTitle = (TextView) ac.findViewById(R.id.headTitle);

			int cnt = fm.getBackStackEntryCount();

			if (cnt != 0) {
				topTitle.setText(getTabName(ac, fm.getBackStackEntryCount() - 1));
			} else {
				killApp(ac);
			}

			if (cnt - 1 <= 0) {
				headNavLayout.setVisibility(View.GONE);
				backBtn.setVisibility(View.INVISIBLE);
			} else {
				headNavLayout.setVisibility(View.VISIBLE);
				backBtn.setVisibility(View.VISIBLE);
			}

			removeTitleTag(ac);
		}else if ((ac instanceof Appointment)) {

			final TextView topTitle = (TextView) ac.findViewById(R.id.headTitle);

			int cnt = fm.getBackStackEntryCount();

			if (cnt != 0) {
				topTitle.setText(getTabName(ac, fm.getBackStackEntryCount() - 1));
			} else {
				killApp(ac);
			}

			removeTitleTag(ac);
		}
		else if ((ac instanceof ServiceReqStatusActivity)) {
			final LinearLayout headNavLayout = (LinearLayout)ac.findViewById(R.id.headNav);
			final Button backBtn = (Button) ac.findViewById(R.id.headBackBtn);
			final TextView topTitle = (TextView) ac.findViewById(R.id.headTitle);

			int cnt = fm.getBackStackEntryCount();
			Log.d("hsh", "cnt: "+cnt);
			if (cnt > 1) {
				topTitle.setText(getTabName(ac, fm.getBackStackEntryCount() - 1));
			} else {
				killApp(ac);
			}

			removeTitleTag(ac);
		}else if ((ac instanceof Healthscreening)) {
			if(Utils.TEST)
				Log.d("hsh", "testtest");
			final LinearLayout headNavLayout = (LinearLayout)ac.findViewById(R.id.headNav);
			final TextView topTitle = (TextView) ac.findViewById(R.id.headTitle);
			final Button backBtn = (Button) ac.findViewById(R.id.headBackBtn);
			int cnt = fm.getBackStackEntryCount();

			if (cnt != 0) {
				topTitle.setText(getTabName(ac, fm.getBackStackEntryCount() - 1));
			} else {
				killApp(ac);
			}

			if (cnt - 1 <= 0) {
				headNavLayout.setVisibility(View.VISIBLE);
				backBtn.setVisibility(View.GONE);
			} else {
				headNavLayout.setVisibility(View.VISIBLE);
				backBtn.setVisibility(View.VISIBLE);
			}

			removeTitleTag(ac);
		}

		fm.popBackStack();

	}

	public static void addTabMenu(final FragmentActivity ac, TAB_MENU tabMenu,
			Class<?> a, final String fragmentId, boolean needBack, Bundle args) {
		final LinearLayout headNavLayout = (LinearLayout)ac.findViewById(R.id.headNav);
		final Button backBtn = (Button) ac.findViewById(R.id.headBackBtn);
		final TextView topTitle = (TextView) ac.findViewById(R.id.headTitle);

//		if(!(ac instanceof ServiceReqStatusActivity)){
//			headRightBtn = (Button) ac.findViewById(R.id.headRightBtn);
//		}else{
//			headRightBtnTT = (ImageView) ac.findViewById(R.id.headRightBtn);
//
//		}
		headRightBtn = (Button) ac.findViewById(R.id.headRightBtn);
		if(Utils.TEST)
			Log.d("hsh", "fragmentId "+fragmentId);
		if ((ac instanceof TabMain)) {
			Utils.SVIEW.setVisibility(View.INVISIBLE);
			backBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Utils.SVIEW.setVisibility(View.VISIBLE);
					FragmentManager fm = ac.getSupportFragmentManager();
					fm.popBackStack();
					topTitle.setText(getTabName(ac, fm.getBackStackEntryCount() - 1));
					if (fm.getBackStackEntryCount() - 1 == 0) {
						headNavLayout.setVisibility(View.GONE);
						backBtn.setVisibility(View.INVISIBLE);
						headRightBtn.setVisibility(View.INVISIBLE);
						if(Utils.GetMain != null){
							Utils.GetMain.getMain();
						}
					} else {
						headNavLayout.setVisibility(View.VISIBLE);
						backBtn.setVisibility(View.VISIBLE);
						headRightBtn.setVisibility(View.INVISIBLE);
					}
					removeTitleTag(ac);
				}
			});
		}

		else if((ac instanceof ServiceReqStatusActivity)){
			if(Utils.TEST)
				Log.d("hsh", "ServiceReqStatusActivity"+ac);
			backBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					FragmentManager fm = ac.getSupportFragmentManager();
					fm.popBackStack();
					topTitle.setText(getTabName(ac, fm.getBackStackEntryCount() - 1));
					if (fm.getBackStackEntryCount() - 1 > 1) {
						headNavLayout.setVisibility(View.VISIBLE);
						backBtn.setVisibility(View.VISIBLE);
//						headRightBtnTT.setVisibility(View.INVISIBLE);
					} else {
						headNavLayout.setVisibility(View.VISIBLE);
						backBtn.setVisibility(View.GONE);
//						headRightBtnTT.setVisibility(View.INVISIBLE);
					}


					removeTitleTag(ac);
				}
			});
		}

		else if((ac instanceof Healthscreening)){
			backBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					FragmentManager fm = ac.getSupportFragmentManager();
					fm.popBackStack();
					topTitle.setText(getTabName(ac, fm.getBackStackEntryCount() - 1));
					if (fm.getBackStackEntryCount() - 1 == 0) {
						headNavLayout.setVisibility(View.VISIBLE);
						backBtn.setVisibility(View.GONE);
//						headRightBtn.setVisibility(View.INVISIBLE);
					} else {
						headNavLayout.setVisibility(View.VISIBLE);
						backBtn.setVisibility(View.VISIBLE);
//						headRightBtn.setVisibility(View.INVISIBLE);
					}
					removeTitleTag(ac);
				}
			});
		}
		FragmentManager fm = ac.getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		try {
			Object ob = a.newInstance();
			if (args != null) {
				((Fragment) ob).setArguments(args);
			}
			if (tabMenu == TAB_MENU.MAIN) {
//				ft.add(R.id.main_tab, (Fragment) ob, fragmentId);
//				tap1_title.add(fragmentId);
			} else if (tabMenu == TAB_MENU.HEALTH) {
				if(Utils.TEST)
					Log.d("hsh", "tabMenu: "+tabMenu);
				ft.add(R.id.tab_service, (Fragment) ob, fragmentId);
				tap3_title.add(fragmentId);
			} else if (tabMenu == TAB_MENU.HOME) {
//				ft.add(R.id.main_tab, (Fragment) ob, fragmentId);
//				tap5_title.add(fragmentId);
			} else if (tabMenu == TAB_MENU.CAL) {
				if(Utils.TEST)
					Log.d("hsh", "tabMenu: "+tabMenu);
				ft.add(R.id.tab_cal_ori, (Fragment) ob, fragmentId);
				tap6_title.add(fragmentId);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}

		if (needBack) {
			ft.addToBackStack(null);
		}

		ft.commit();
		if ((ac instanceof TabMain)) {
			topTitle.setText(getTabName(ac, fm.getBackStackEntryCount() + 1));

			if (fm.getBackStackEntryCount() + 1 == 0) {
				headNavLayout.setVisibility(View.GONE);
				backBtn.setVisibility(View.INVISIBLE);
				headRightBtn.setVisibility(View.INVISIBLE);
			} else {
				headNavLayout.setVisibility(View.VISIBLE);
				backBtn.setVisibility(View.VISIBLE);
				headRightBtn.setVisibility(View.INVISIBLE);
				/*if(fragmentId.equals(ac.getString(R.string.drug_main_detail))){
					headRightBtn.setVisibility(View.VISIBLE);
					headRightBtn.setText("저장");
				}else
					headRightBtn.setVisibility(View.INVISIBLE);*/
			}
		}else if((ac instanceof Appointment)) {
			topTitle.setText(getTabName(ac, fm.getBackStackEntryCount() + 1));

			if (fm.getBackStackEntryCount() + 1 == 0) {
				headNavLayout.setVisibility(View.GONE);
				backBtn.setVisibility(View.INVISIBLE);
				headRightBtn.setVisibility(View.INVISIBLE);
			} else {
				headNavLayout.setVisibility(View.GONE);
				backBtn.setVisibility(View.VISIBLE);
				headRightBtn.setVisibility(View.INVISIBLE);
			}
		}else if((ac instanceof ServiceReqStatusActivity)) {
			topTitle.setText(getTabName(ac, fm.getBackStackEntryCount() + 1));
			if(Utils.TEST)
				Log.d("hsh", "fm.getBackStackEntryCount(): "+fm.getBackStackEntryCount());
			if (fm.getBackStackEntryCount() == 0) {
				headNavLayout.setVisibility(View.VISIBLE);
				backBtn.setVisibility(View.GONE);
//				headRightBtn.setVisibility(View.INVISIBLE);
			} else {
				headNavLayout.setVisibility(View.VISIBLE);
				backBtn.setVisibility(View.VISIBLE);
//				headRightBtn.setVisibility(View.INVISIBLE);
			}
		}else if((ac instanceof Healthscreening)) {
			topTitle.setText(getTabName(ac, fm.getBackStackEntryCount() + 1));

			if (fm.getBackStackEntryCount() + 1 == 0) {
				headNavLayout.setVisibility(View.VISIBLE);
				backBtn.setVisibility(View.GONE);
//				headRightBtn.setVisibility(View.INVISIBLE);
			} else {
				headNavLayout.setVisibility(View.VISIBLE);
				backBtn.setVisibility(View.VISIBLE);
//				headRightBtn.setVisibility(View.INVISIBLE);
			}
		}
	}


	private static boolean getServiceTaskName(final FragmentActivity ac) {
		boolean checked = false;
		ActivityManager am = (ActivityManager)ac.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> info;
		info = am.getRunningServices(Integer.MAX_VALUE);

		for(Iterator iterator = info.iterator(); iterator.hasNext();) {
			RunningServiceInfo runningTaskInfo = (RunningServiceInfo) iterator.next();
//			Log.i("hsh", "Service name :"+runningTaskInfo.service.getClassName());

//			if (runningTaskInfo.service.getClassName().equals("com.gchealthcare.app.sos.accelService")) {
//				checked = true;
//				if(Utils.TEST){
//					Log.i("hsh", "appmanager Service is.... : " + checked);
//				}
//			}
		}
		return checked;
	}

	public static void killApp(final FragmentActivity ac) {

		if(getServiceTaskName(ac)){
			//			Intent intent = new Intent(Intent.ACTION_MAIN);
			//			intent.addCategory(Intent.CATEGORY_HOME);
			//			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			//			ac.startActivity(intent);
			ac.finish();
		}else
		{

			String alertTitle = ac.getResources().getString(R.string.app_name);
			String buttonMessage = "xxxxxxxx";
			String buttonYes = "yes1";
			String buttonNo = "no2";
			new AlertDialog.Builder(ac)
			.setTitle(alertTitle)
			.setMessage(buttonMessage)
			.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					android.os.Process.killProcess(android.os.Process.myPid());
				}
			}).setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog,int which) {
					tap1_title = new ArrayList<String>();
					tap3_title = new ArrayList<String>();
					tap5_title = new ArrayList<String>();
					tap6_title = new ArrayList<String>();
					tap1_title.add(ac.getString(R.string.home));
					tap3_title.add(ac.getString(R.string.service_req));
					tap5_title.add(ac.getString(R.string.appointment));
					tap6_title.add(ac.getString(R.string.reservation_health_screening));
					if(ac instanceof ServiceReqStatusActivity){
						((ServiceReqStatusActivity) ac).setFragment();
					}
				}
			}).show();
		}
	}

	public static void getMenu(int tabIndex, Activity ac, TabHost tabHost) {
		FragmentActivity fac = null;
		if(tabIndex == 0){
			fac = Utils.FRAGMENTACTIVITY1;
		}else if(tabIndex == 1){
			fac = Utils.FRAGMENTACTIVITY2;
		}else if(tabIndex == 2){
			fac = Utils.FRAGMENTACTIVITY3;
		}else if(tabIndex == 3){
			fac = Utils.FRAGMENTACTIVITY4;
		}
		FragmentManager fm = fac.getSupportFragmentManager();
		if(Utils.TEST){
			Log.d("hsh", "fac: "+fac);
			Log.d("hsh", "fm: "+fm);
		}
		if(fm.toString().matches(".*null.*")){
			if(Utils.TEST){
				Log.d("hsh", "fm null");
			}
			return;
		}
		
		final LinearLayout headNavLayout = (LinearLayout)fac.findViewById(R.id.headNav);
		//		final Button backBtn = (Button) fac.findViewById(R.id.headBackBtn);
		final TextView topTitle = (TextView) ac.findViewById(R.id.headTitle);
		int cnt = fm.getBackStackEntryCount();
		if(Utils.TEST){
			Log.d("hsh", "cnt: "+cnt);
		}
		for(int i=0;i < cnt;i++){
			fm.popBackStack();
		}
		headNavLayout.setVisibility(View.GONE);
		if(Utils.TEST){
			Log.e("tttt", "aaa1");
		}
		if(tabIndex == 0){
			tap1_title = new ArrayList<String>();
			tap1_title.add(ac.getString(R.string.home));
			Utils.SVIEW.setVisibility(View.VISIBLE);
		}else if(tabIndex == 1){
			tap3_title = new ArrayList<String>();
			tap3_title.add(ac.getString(R.string.home));
		}else if(tabIndex == 2){
			tap5_title = new ArrayList<String>();
			tap5_title.add(ac.getString(R.string.home));
		}else if(tabIndex == 3){
			tap6_title = new ArrayList<String>();
			tap6_title.add(ac.getString(R.string.home));
		}
		if(topTitle != null){
			topTitle.setText(ac.getString(R.string.home));
		}
	}
}
