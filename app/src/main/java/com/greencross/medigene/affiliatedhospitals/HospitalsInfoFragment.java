package com.greencross.medigene.affiliatedhospitals;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.greencross.medigene.R;
import com.greencross.medigene.affiliatedhospitals.common.HttpAsyncTask;
import com.greencross.medigene.affiliatedhospitals.common.HttpAsyncTaskInterface;
import com.greencross.medigene.affiliatedhospitals.common.Utils;
import com.greencross.medigene.base.BaseFragment;
import com.greencross.medigene.base.CommonActionBar;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * 검강검진 제휴병원 - 병원정보
 *
 */
public class HospitalsInfoFragment  extends BaseFragment implements HttpAsyncTaskInterface,OnClickListener {

	private Context con;
	private FragmentActivity fac;
//	private TextView name;
	private String telNumber;
	private String introduction;
	private String workingTime;
	private String note;
	private String address;
	private String traffic;
	private String parking;
	private String homepage;
	private TextView table6;
	private TextView table7;
	private TextView table8;
	private ImageView table9;
	Activity ac;
	private String longitude="";
	private String latitude="";

	private View view;

	public static Fragment newInstance() {
		HospitalsInfoFragment fragment = new HospitalsInfoFragment();
		return fragment;
	}


	@Override
	public void loadActionbar(CommonActionBar actionBar) {
		super.loadActionbar(actionBar);
		actionBar.setActionBarTitle(getArguments().getString("name"));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.hospitals_info, null);	
		this.ac = getActivity();

		con = getActivity();
		fac = getActivity();
		String hospital_code = getArguments().getString("kind");
		
//		name = (TextView)view.findViewById(R.id.name);
		
		LinearLayout table1 = (LinearLayout)view.findViewById(R.id.table1);
		LinearLayout table2 = (LinearLayout)view.findViewById(R.id.table2);
		LinearLayout table3 = (LinearLayout)view.findViewById(R.id.table3);
		LinearLayout table4 = (LinearLayout)view.findViewById(R.id.table4);
		LinearLayout table5 = (LinearLayout)view.findViewById(R.id.table5);
		table6 = (TextView)view.findViewById(R.id.table6);
		table7 = (TextView)view.findViewById(R.id.table7);
		table8 = (TextView)view.findViewById(R.id.table8);
		table9 = (ImageView)view.findViewById(R.id.table9);
		
		table1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle arg = new Bundle();
//				arg.putString("name", name.getText().toString());
				arg.putString("title", getString(R.string.str_0022));
				arg.putString("content", introduction);
				arg.putString("hospitalname", getArguments().getString("name"));
				movePage(HospitalsInfoDetailFragment.newInstance(), arg);
			}
		});
		table2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle arg = new Bundle();
//				arg.putString("name", name.getText().toString());
				arg.putString("title", getString(R.string.str_0023));
				arg.putString("content", note);
				arg.putString("hospitalname", getArguments().getString("name"));
				movePage(HospitalsInfoDetailFragment.newInstance(), arg);
			}
		});
		table3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle arg = new Bundle();
//				arg.putString("name", name.getText().toString());
				arg.putString("title", getString(R.string.str_0024));
				arg.putString("content", traffic);
				arg.putString("hospitalname", getArguments().getString("name"));
				movePage(HospitalsInfoDetailFragment.newInstance(), arg);
			}
		});
		table4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle arg = new Bundle();
//				arg.putString("name", name.getText().toString());
				arg.putString("title", getString(R.string.str_0025));
				arg.putString("content", parking);
				arg.putString("hospitalname", getArguments().getString("name"));
				movePage(HospitalsInfoDetailFragment.newInstance(), arg);
			}
		});
		table5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(homepage != null){
					Intent i = new Intent(Intent.ACTION_VIEW);
					Uri u = Uri.parse(homepage);
					i.setData(u);
					startActivity(i);
				}
			}
		});
		table9.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle args = new Bundle();
//				args.putString("hospitalName", name.getText().toString());
				args.putString("gpxX", longitude);
				args.putString("gpxY", latitude);
				args.putString("title", getArguments().getString("name"));
				movePage(HospitalMapFragment.newInstance(), args);
			}
		});
		
		

		String param = getString(R.string.hospitalInformation_Request,"103", "10041", "9999999999999",hospital_code);
		if(Utils.TEST){
			Log.i(Utils.TAG, "Logo param : "+param);
		}
		HttpAsyncTask rssTask = new HttpAsyncTask(this, "http://www.amdoctor.com/webservice/mobile_call.asmx"+"/mobile_Call");
		rssTask.setParam(param);
		rssTask.execute();	

		return view;    	
	}



	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}	   

	@Override
	public void onClick(View v) {
	}



	@Override
	public void onPreExecute() {
	}



	@Override
	public void onPostExecute(String data) {

		try {
			String jsonData = Utils.getJosonDataFromXML(data);

			Object obj = JSONValue.parse(jsonData);
			JSONObject JSONONotice = (JSONObject)obj;
			JSONArray object = (JSONArray)JSONONotice.get("hospitalInformation");
			JSONObject oj = (JSONObject)object.get(0);

//			String hospital_codeP = (String)oj.get("hospital_code");
			String nameP = (String)oj.get("name");//병원이름
			String telNumberP = (String)oj.get("telNumber");//연락처
			String introductionP = (String)oj.get("introduction");//병원소개 
			String workingTimeP = (String)oj.get("workingTime");//업무시간
			String noteP = (String)oj.get("note");//검사 전  준수사항
			String addressP = (String)oj.get("address");//병원주소
			String trafficP = (String)oj.get("traffic");//교통편
			String parkingP = (String)oj.get("parking");//주차정보
			String longitudeP = (String)oj.get("longitude");//경도
			String latitudeP = (String)oj.get("latitude");//위도
			String homepageP = (String)oj.get("homepage");//홈페이지

//			name.setText(nameP+" "+getString(R.string.str_0021));
			telNumber = telNumberP;	
			introduction = introductionP;	//
			workingTime = workingTimeP;
			if (!TextUtils.isEmpty(workingTime)){

				workingTime = workingTime.replaceAll("&lt;br&gt;", "");
				workingTime = workingTime.replaceAll("&amp;", "&").replaceAll("&lt;", "").replaceAll("&gt;", ">");
			}
			note = noteP;	//
			address = addressP;	
			traffic = trafficP;	//
			parking = parkingP;	//
			homepage = homepageP;//	
			table6.setText(telNumber);
			table7.setText(workingTime);
			table8.setText(address);
			
			longitude = longitudeP;
			latitude = latitudeP;

		} catch (Exception e) {
			Utils.dialog(con, getString(R.string.notgetData));
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onError() {

	}
}