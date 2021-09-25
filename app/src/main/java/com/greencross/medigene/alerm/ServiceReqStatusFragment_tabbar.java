package com.greencross.medigene.alerm;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.greencross.medigene.R;
import com.greencross.medigene.base.BaseFragment;
import com.greencross.medigene.base.CommonActionBar;

/**
 * 예약현황, 서비스신청현황 메인 화면
 *
 */
public class ServiceReqStatusFragment_tabbar extends BaseFragment implements OnClickListener,  OnScrollListener {

	private FragmentActivity fac;
	private Activity ac;
	private Context con;

	private ListView reqListView;
	private ListView resListView;
	private int nowPage = 1;
	private int maxPage = 0;
	private boolean mLockListView;
	private int currentFirstVisibleItem;
	private int currentVisibleItemCount;
	private int currentTotalItemCount;
	private ImageView tabFirst;
	private ImageView tabSecond;
	private int type = 0;//page type flag
	private View footer;
	private boolean max = true;



	public static Fragment newInstance() {
		ServiceReqStatusFragment_tabbar fragment = new ServiceReqStatusFragment_tabbar();
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.login_fragment, container, false);
		return view;
	}

	@Override
	public void loadActionbar(CommonActionBar actionBar) {
		super.loadActionbar(actionBar);
		actionBar.setActionBackBtnVisible(View.INVISIBLE);
		actionBar.showActionBar(false);
		actionBar.setActionBarTitle( getString(R.string.text_login));
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

	}



	/** 서비스 신청 현황 상담*/
	public void reqRoadData() {

	}


	/** 서비스 신청 현황 예약*/
	public void resRoadData() {

	}	

	@Override
	public void onResume() {
		super.onResume();   
	}

	@Override
	public void onDestroy() {
		super.onDestroy(); 		
	}    
	/** 서비스 신청 현황 상담 화면 설정*/
	private void showReqRoad() {
		nowPage = 1;
		type = 1;
		max = true;
		footer.setVisibility(View.VISIBLE);
		reqListView.setVisibility(View.VISIBLE);
		resListView.setVisibility(View.GONE);


		reqRoadData();
	}
	/** 서비스 신청 현황 예약 화면 설정*/
	private void showResRoad() {
		nowPage = 1;
		type = 0;
		max = true;
		footer.setVisibility(View.VISIBLE);

		resRoadData();
	}    

	private AdapterView.OnItemClickListener reqClickListener = new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position,long l_position) {  	

		}
	}; 

	/** 서비스 신청 현황 상담 리스트 표시*/
	private void setReqAdapter(String data) {

	}
	/** 서비스 신청 현황 예약 리스트 표시*/
	private void setResAdapter(String data) {


	}    

	public void onPreExecute() {

	}

	public void onPostExecute(String data) {

	}

	public void onError() {
		mLockListView = false;
	}

	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		this.currentFirstVisibleItem = firstVisibleItem;
		this.currentVisibleItemCount = visibleItemCount;
		this.currentTotalItemCount = totalItemCount;
	}

	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollState == SCROLL_STATE_IDLE) {
			isScrollCompleted();
		}
	}

	private void isScrollCompleted() {
		int lastVisibleElement = currentFirstVisibleItem + currentVisibleItemCount;
		if(lastVisibleElement == currentTotalItemCount && mLockListView == false && max) {
			if (nowPage <= maxPage) {  	
				mLockListView = true;
				if (type == 1) {
					reqRoadData();
				} else if (type == 0) {
					resRoadData();
				} 	        	
			}else{
				if (type == 1) {
					reqListView.removeFooterView(footer);
				} else if (type == 0) {
					resListView.removeFooterView(footer);
				} 
			}
		}
	}

	@Override
	public void onClick(View v) {


	}	

}