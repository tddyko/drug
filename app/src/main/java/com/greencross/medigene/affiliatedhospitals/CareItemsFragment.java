package com.greencross.medigene.affiliatedhospitals;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.List;

/**
 * 검강검진 제휴병원 - 검사항목
 *
 */
public class CareItemsFragment extends BaseFragment implements HttpAsyncTaskInterface,OnClickListener {
	private Context con;
	private Activity ac;
	private FragmentActivity fac;
	private CategoryListAdapter categoryListAdapter;
	private List<CategoryBean> dataList;
	private ListView listview;
	private String hospital_codeP;
	private String hospital_name;

	public static Fragment newInstance() {
		CareItemsFragment fragment = new CareItemsFragment();
		return fragment;
	}


	@Override
	public void loadActionbar(CommonActionBar actionBar) {
		super.loadActionbar(actionBar);
		actionBar.setActionBarTitle(getArguments().getString("name"));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.care_items, null);	

		con = getActivity();
		ac = getActivity();
		fac = getActivity();

		hospital_codeP = getArguments().getString("kind");
		hospital_name = getArguments().getString("name");

		TextView name = (TextView) view.findViewById(R.id.name);
		name.setText(getString(R.string.str_0020));
		listview = (ListView) view.findViewById(R.id.ListView);
		listview.setOnItemClickListener(mItemClickListener);


		String param = getString(R.string.hospitalCheckList_Request, "103", "10041", "9999999999999" ,hospital_codeP);
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
	public void onClick(View v) {
		//		switch (v.getId()) {
		//			case R.id.button:
		////		        AppManageHelper.addTabMenu(getActivity(), TAB_MENU.MAIN, Next1Fragment.class, AppConstruction.NEXT1_FRAGMENT, true);
		//				break;
		//		}
	}



	@Override
	public void onPreExecute() {

	}



	@Override
	public void onPostExecute(String data) {
		try {
			dataList = new ArrayList<>();

			String jsonData = Utils.getJosonDataFromXML(data);

			Object obj = JSONValue.parse(jsonData);
			JSONObject JSONONotice = (JSONObject)obj;
			JSONArray object = (JSONArray)JSONONotice.get("hospitalCheckList");
			for(int i =0 ; i < object.size(); i++) {
				JSONObject oj = (JSONObject)object.get(i);
				String code = (String)oj.get("lcode");
				String name = (String)oj.get("lname");
				CategoryBean bean = new CategoryBean();
				bean.setCode(code);
				bean.setName(name);
				dataList.add(bean);
			}

			categoryListAdapter = new CategoryListAdapter(ac, dataList);
			listview.setAdapter(categoryListAdapter);

			categoryListAdapter.notifyDataSetChanged();

		} catch (Exception e) {

		}
	}

	private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long l_position) {
			CategoryBean bean = dataList.get(position);
			Bundle args = new Bundle();

			args.putString("name", bean.getName());
			args.putString("code", bean.getCode());
			args.putString("hospital_code", hospital_codeP);
			args.putString("hospital_name", hospital_name);
			movePage(CategoryViewFragment.newInstance(), args);
		}
	};

	@Override
	public void onError() {

	}
}
