package com.greencross.medigene.affiliatedhospitals;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.greencross.medigene.R;
import com.greencross.medigene.affiliatedhospitals.common.HttpAsyncTask;
import com.greencross.medigene.affiliatedhospitals.common.HttpAsyncTaskInterface;
import com.greencross.medigene.affiliatedhospitals.common.Utils;
import com.greencross.medigene.base.BaseFragment;
import com.greencross.medigene.base.CommonActionBar;

/**
 * 건강검진 제휴병원 - 병원 리스트
 *
 */
public class AffiliatedHospitalsList extends BaseFragment implements HttpAsyncTaskInterface {

	private ListView affiliatedHospitalsListView;
	private AffiliatedHospitalsListAdapter affiliatedHospitalsListAdapter;
	private List<AffiliatedHospitalsBean> dataList;
	private Activity ac;
	private Context con;

	private TextView local_title;

	private String area_code = "";
	private String area_name = "";

	public static Fragment newInstance() {
		AffiliatedHospitalsList fragment = new AffiliatedHospitalsList();
		return fragment;
	}


	@Override
	public void loadActionbar(CommonActionBar actionBar) {
		super.loadActionbar(actionBar);
		actionBar.setActionBarTitle("건강검진 제휴병원");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.affiliatedhospitals_list, null);

		ac 			= getActivity();
		con 		= getActivity();
		area_code 	= getArguments().getString("kind");
		area_name 	= getArguments().getString("name");
		initFrame(view);
		return view;
	}

	private void initFrame(View view) {
		final LinearLayout linear1 = (LinearLayout) view.findViewById(R.id.top1);
		final LinearLayout linear2 = (LinearLayout) view.findViewById(R.id.top2);
		linear1.setVisibility(View.VISIBLE);
		linear2.setVisibility(View.INVISIBLE);
		local_title = (TextView) view.findViewById(R.id.local_title);
		local_title.setText(area_name);
		affiliatedHospitalsListView 	= (ListView) view.findViewById(R.id.ListView);
		affiliatedHospitalsListView.setOnItemClickListener(mItemClickListener);

		String param = getString(R.string.hospitalList_Request, "103", "10041", "9999999999999" ,area_code);
		if(Utils.TEST){
			Log.i(Utils.TAG, "Logo param : "+param);
		}
		HttpAsyncTask rssTask = new HttpAsyncTask(this, "http://www.amdoctor.com/webservice/mobile_call.asmx"+"/mobile_Call");
		rssTask.setParam(param);
		rssTask.execute();

		Button btn2 = (Button)view.findViewById(R.id.cancel_btn);
		btn2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				linear1.setVisibility(View.VISIBLE);
				linear2.setVisibility(View.INVISIBLE);
				affiliatedHospitalsListAdapter = new AffiliatedHospitalsListAdapter(ac, dataList, AffiliatedHospitalsList.this);
				affiliatedHospitalsListView.setAdapter(affiliatedHospitalsListAdapter);
				affiliatedHospitalsListAdapter.notifyDataSetChanged();
			}
		});

		
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long l_position) {
			AffiliatedHospitalsBean bean = dataList.get(position);
			Bundle args = new Bundle();
			args.putString("title", bean.getName());
			args.putString("content", bean.getSpecial());
			args.putString("key", bean.getCode());
		}
	};

	public void onPreExecute() {
	}

	public void onPostExecute(String data) {
		try {

			dataList = new ArrayList<>();

			String jsonData = Utils.getJosonDataFromXML(data);
			Object obj = JSONValue.parse(jsonData);
			JSONObject JSONONotice = (JSONObject)obj;
			JSONArray object = (JSONArray)JSONONotice.get("hospitalList");
			for (int i = 0; i < object.size(); i++) {
				JSONObject oj = (JSONObject) object.get(i);
				AffiliatedHospitalsBean temp = new AffiliatedHospitalsBean();
				temp.setCode((String) oj.get("hospital_code"));
				temp.setName((String) oj.get("hospital_name"));
				temp.setSpecial((String) oj.get("hospital_special"));
				temp.setHptCode((String) oj.get("hpt_code"));
				temp.setAreaCode((String) oj.get("hospital_areaCode"));
				dataList.add(temp);
			}

			affiliatedHospitalsListAdapter = new AffiliatedHospitalsListAdapter(ac, dataList, AffiliatedHospitalsList.this);
			affiliatedHospitalsListView.setAdapter(affiliatedHospitalsListAdapter);

			affiliatedHospitalsListAdapter.notifyDataSetChanged();
		} catch (Exception e) {

		}

	}

	public void onError() {
	}


}
