package com.greencross.medigene.affiliatedhospitals;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.greencross.medigene.R;
import com.greencross.medigene.affiliatedhospitals.common.Utils;
import com.greencross.medigene.affiliatedhospitals.HospitalMapFragment;
import com.greencross.medigene.base.BaseFragment;

/**
 * 건강검진 제휴병원 - 병원 리스트 표시 어댑터
 */
public class AppointmentHospitalsListAdapter extends ArrayAdapter<AppointmentHospitalsBean> {

    View rowView;
    List<AppointmentHospitalsBean> dataList = null;
    Activity ac;
    BaseFragment mBaseFragment;

    public AppointmentHospitalsListAdapter(Activity activity, List<AppointmentHospitalsBean> datas, BaseFragment baseFragment) {
        super(activity, 0, datas);
        this.ac = activity;
        dataList = datas;
        mBaseFragment = baseFragment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Activity activity = (Activity) getContext();
        final FragmentActivity ac = (FragmentActivity) activity;
        LayoutInflater inflater = activity.getLayoutInflater();
        rowView = inflater.inflate(R.layout.appointmenthospitals_row, null);

        LinearLayout top1 = (LinearLayout) rowView.findViewById(R.id.top1);
        TextView local_title = (TextView) rowView.findViewById(R.id.local_title);

        TextView title = (TextView) rowView.findViewById(R.id.title);
        TextView contents = (TextView) rowView.findViewById(R.id.contents);
        AppointmentHospitalsBean bean = dataList.get(position);

        // 지역
        if (position == 0) {
            top1.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) top1.getLayoutParams();
            params.topMargin = 0;
            top1.setLayoutParams(params);
            local_title.setText(bean.getAreaCode_name());
        } else {
            if (bean.getAreaCode_name().equals(dataList.get(position - 1).getAreaCode_name())) {
                top1.setVisibility(View.GONE);
            } else {
                top1.setVisibility(View.VISIBLE);
                local_title.setText(bean.getAreaCode_name());
            }
        }

        title.setText(bean.getName()); // 이름
        contents.setText(bean.getAddress()); // 주소


        Button btn2 = (Button) rowView.findViewById(R.id.btn2);
        btn2.setTag(position);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Bundle args = new Bundle();

                AppointmentHospitalsBean bean = dataList.get(Integer.valueOf(v.getTag().toString()));
                args.putString("hospitalName", bean.getName());
                args.putString("gpxX", bean.getX());
                args.putString("gpxY", bean.getY());
                args.putString("title", bean.getName());
                mBaseFragment.movePage(HospitalMapFragment.newInstance(), args);

            }
        });

        return rowView;
    }
}
