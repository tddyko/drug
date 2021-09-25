package com.greencross.medigene.affiliatedhospitals;

import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.greencross.medigene.R;
import com.greencross.medigene.affiliatedhospitals.common.Utils;
/**
 * 검강검진 제휴병원 - 검사항목 상세 리스트 어댑터
 *
 */
public class CategoryViewListAdapter  extends ArrayAdapter<CategoryViewBean>{

	View rowView;
	List<CategoryViewBean> dataList = null;

	public CategoryViewListAdapter(Activity activity, List<CategoryViewBean> datas) {
		super(activity, 0, datas);
		dataList = datas;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Activity activity = (Activity) getContext();
		LayoutInflater inflater = activity.getLayoutInflater();	
		rowView = inflater.inflate(R.layout.categoryviewdetaillist_row, null);
		
		CategoryViewBean bean = dataList.get(position);
		
		TextView title = (TextView) rowView.findViewById(R.id.title);
		TextView contents = (TextView) rowView.findViewById(R.id.contents);
		title.setText(bean.getCheckName());
		contents.setText(bean.getCheckContents());
		return rowView;
	}
}
