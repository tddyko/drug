package com.greencross.medigene.affiliatedhospitals;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.greencross.medigene.R;
import com.greencross.medigene.base.BaseFragment;
import com.greencross.medigene.base.CommonActionBar;

/**
 * 건강검진 제휴병원 - 병원정보상세
 *
 */
public class HospitalsInfoDetailFragment  extends BaseFragment{

	private View view;
	private String content;

	public static Fragment newInstance() {
		HospitalsInfoDetailFragment fragment = new HospitalsInfoDetailFragment();
		return fragment;
	}


	@Override
	public void loadActionbar(CommonActionBar actionBar) {
		super.loadActionbar(actionBar);
		actionBar.setActionBarTitle(getArguments().getString("hospitalname"));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.hospitals_info_detail, null);	


		String name = getArguments().getString("name");
		String title = getArguments().getString("title");
		content = getArguments().getString("content");


//		TextView n = (TextView) view.findViewById(R.id.name);
//		n.setText(name);
		TextView t = (TextView) view.findViewById(R.id.title);
		t.setText(title);
		TextView c = (TextView) view.findViewById(R.id.content);

		if(title.equals("홈페이지") && !content.equals("")){
			c.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(Intent.ACTION_VIEW);
					Uri u = Uri.parse(content);
					i.setData(u);
					startActivity(i);
				}
			});
			c.setPaintFlags(c.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
//			Text1.setPaintFlags(mText4.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);//Bold
//			Text2.setPaintFlags(mText7.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG); // Text Middle Line
//			Text3.setPaintFlags(mText9.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG); // Text Under Line
		}
		if(content == null || content.equals("")){
			content ="정보가 없습니다.";
		}
		c.setText(content);

		return view;    	
	}
}