package com.greencross.medigene.affiliatedhospitals;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.greencross.medigene.R;
import com.greencross.medigene.affiliatedhospitals.common.HttpAsyncTask;
import com.greencross.medigene.affiliatedhospitals.common.HttpAsyncTaskInterface;
import com.greencross.medigene.affiliatedhospitals.common.Utils;
import com.greencross.medigene.base.BaseFragment;
import com.greencross.medigene.base.CommonActionBar;

/**
 * 검강검진 제휴병원 - 병원 리스트
 *
 */
public class AppointmentHospitalsList extends BaseFragment implements HttpAsyncTaskInterface {

	private ListView appointmentHospitalsListView;
	private AppointmentHospitalsListAdapter appointmentHospitalsListAdapter;
	private List<AppointmentHospitalsBean> dataList;


	public static Fragment newInstance() {
		AppointmentHospitalsList fragment = new AppointmentHospitalsList();
		return fragment;
	}


	@Override
	public void loadActionbar(CommonActionBar actionBar) {
		super.loadActionbar(actionBar);
		actionBar.setActionBarTitle("진료예약 제휴병원");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.appointmenthospitals_list, null);

		initFrame(view);

		return view;
	}

	private void initFrame(View view) {
		showProgress();


		appointmentHospitalsListView = (ListView) view.findViewById(R.id.ListView);

		String param = getString(R.string.hospital_list, "107");

		HttpAsyncTask rssTask = new HttpAsyncTask(this, "http://m.shealthcare.co.kr/Webservice/KB_Mobile_Call.asmx/KB_mobile_Call");
		rssTask.setParam(param);
		rssTask.execute();

		hideProgress();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	public void onPreExecute() {

	}

	public void onPostExecute(String data) {

		try {

			dataList = new ArrayList<>();

			String jsonData = Utils.getJosonDataFromXML(data);
			Object obj = JSONValue.parse(jsonData);
			JSONObject JSONONotice = (JSONObject)obj;

			JSONArray object = (JSONArray)JSONONotice.get("hospitalList_with");
			for (int i = 0; i < object.size(); i++) {
				JSONObject oj = (JSONObject) object.get(i);
				AppointmentHospitalsBean temp = new AppointmentHospitalsBean();
				temp.setAreaCode((String) oj.get("hospital_hos_area_num"));
				temp.setAreaCode_name((String) oj.get("hospital_hos_area_nm"));
				temp.setName((String) oj.get("hospital_hos_nm"));
				temp.setAddress((String) oj.get("hospital_hos_add"));
				temp.setX((String) oj.get("hospital_x"));
				temp.setY((String) oj.get("hospital_y"));
				dataList.add(temp);
			}

			appointmentHospitalsListAdapter = new AppointmentHospitalsListAdapter(getActivity(), dataList, AppointmentHospitalsList.this);
			appointmentHospitalsListView.setAdapter(appointmentHospitalsListAdapter);

			appointmentHospitalsListAdapter.notifyDataSetChanged();
		} catch (Exception e) {
		}

	}

	public void onError() {

	}


}
