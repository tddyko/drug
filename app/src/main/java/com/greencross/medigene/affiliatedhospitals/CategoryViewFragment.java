package com.greencross.medigene.affiliatedhospitals;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
 * 건강검진 제휴병원 - 검사항목 상세
 *
 */
public class CategoryViewFragment extends BaseFragment implements HttpAsyncTaskInterface,OnClickListener {
	private Context con;
	private Activity ac;
	private List<CategoryViewBean> dataList;
	private CategoryViewListAdapter categoryListAdapter;
	private String aaa = "";
	private ListView listview;

	public static Fragment newInstance() {
		CategoryViewFragment fragment = new CategoryViewFragment();
		return fragment;
	}

	@Override
	public void loadActionbar(CommonActionBar actionBar) {
		super.loadActionbar(actionBar);
		actionBar.setActionBarTitle(getArguments().getString("hospital_name"));
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.categoryview_items, null);	

		con = getActivity();
		ac = getActivity();
	
		dataList = new ArrayList<>();
		String hospital_code = getArguments().getString("hospital_code");
		String code = getArguments().getString("code");
		String names = getArguments().getString("name");
		listview = (ListView) view.findViewById(R.id.ListView);
		
		TextView name = (TextView) view.findViewById(R.id.name);
		name.setText(getArguments().getString("name"));

		String param = getString(R.string.hospitalCheckListCategory_Request, "103", "10041", "9999999999999" ,hospital_code,code);
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
	public void onClick(View v) {
	}



	@Override
	public void onPreExecute() {
		
	}



	@Override
	public void onPostExecute(String data) {
		try {

			aaa = "";
			String jsonData = Utils.getJosonDataFromXML(data);
			dataList = new ArrayList<>();
			Object obj = JSONValue.parse(jsonData);
			JSONObject JSONONotice = (JSONObject)obj;

				JSONArray object = (JSONArray)JSONONotice.get("hospitalCheckListCategory");
				for(int i =0 ; i < object.size(); i++) {
					JSONObject oj = (JSONObject)object.get(i);
					String a = (String)oj.get("SName");
					String b = (String)oj.get("DeS");
					CategoryViewBean bean = new CategoryViewBean();
					bean.setCheckName(a);
					bean.setCheckContents(b);
					dataList.add(bean);
					
					aaa	 = aaa +"\n\n"+ a+"\n";
					aaa	 = aaa + b+"\n\n";
				}

				categoryListAdapter = new CategoryViewListAdapter(ac, dataList);
				listview.setAdapter(categoryListAdapter);

				categoryListAdapter.notifyDataSetChanged();
		} catch (Exception e) {
		}
	}


	@Override
	public void onError() {
		
	}
}
