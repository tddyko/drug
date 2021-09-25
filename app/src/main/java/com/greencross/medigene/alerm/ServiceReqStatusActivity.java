package com.greencross.medigene.alerm;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.greencross.medigene.R;
import com.greencross.medigene.alerm.AppManageHelper.TAB_MENU;
import com.greencross.medigene.util.Utils;

/**
 * 예약현황, 서비스신청현황 메인 화면
 *
 */
public class ServiceReqStatusActivity extends FragmentActivity{

	private FragmentActivity fac;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.servicereqstatus_list_activity);
		if(Utils.TEST){
			Log.i(Utils.TAG, "ServiceReqStatusFragment");
		}
		if(Utils.FRAGMENTACTIVITY4 == null){
			Utils.FRAGMENTACTIVITY4= this;
		}
		
		fac = ServiceReqStatusActivity.this;
		
		setFragment();
	}

	@Override
	public void onBackPressed(){
		AppManageHelper.footBack(fac);
	}
	
	public void setFragment(){
		Bundle arg = new Bundle();
		arg.putInt("type", 1);
		arg.putInt("type_homebar", 1);
		AppManageHelper.addTabMenu(ServiceReqStatusActivity.this, TAB_MENU.HEALTH, ServiceReqStatusFragment_tabbar.class, getString(R.string.service_req), true, arg);
	}

	@Override
	public void onResume() {
		super.onResume();   
	}

	@Override
	public void onDestroy() {
		super.onDestroy(); 		
	}
}