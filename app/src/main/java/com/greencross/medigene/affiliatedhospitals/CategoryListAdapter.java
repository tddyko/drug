package com.greencross.medigene.affiliatedhospitals;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.greencross.medigene.R;

import java.util.List;
/**
 * 검강검진 제휴병원 - 검사항목 리스트 어댑터
 *
 */
public class CategoryListAdapter  extends ArrayAdapter<CategoryBean>{

	View rowView;
	List<CategoryBean> dataList = null;

	public CategoryListAdapter(Activity activity, List<CategoryBean> datas) {
		super(activity, 0, datas);
		dataList = datas;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Activity activity = (Activity) getContext();
		LayoutInflater inflater = activity.getLayoutInflater();	
		rowView = inflater.inflate(R.layout.categorylist_row, null);
		
		CategoryBean bean = dataList.get(position);
		
		TextView title = (TextView) rowView.findViewById(R.id.title);
		title.setText(bean.getName());
		return rowView;
	}
}
