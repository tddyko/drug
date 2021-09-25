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
import android.widget.TextView;

import com.greencross.medigene.R;
import com.greencross.medigene.base.BaseFragment;

/**
 * 검강검진 제휴병원 - 병원 리스트 표시 어댑터
 */
public class AffiliatedHospitalsListAdapter extends ArrayAdapter<AffiliatedHospitalsBean> {

    View rowView;
    List<AffiliatedHospitalsBean> dataList = null;
    Activity ac;
    private BaseFragment mBaseFramgent;

    public AffiliatedHospitalsListAdapter(Activity activity, List<AffiliatedHospitalsBean> datas, BaseFragment baseFragment) {
        super(activity, 0, datas);
        this.ac         = activity;
        dataList        = datas;
        mBaseFramgent   = baseFragment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Activity activity               = (Activity) getContext();
        final FragmentActivity ac       = (FragmentActivity) activity;
        LayoutInflater inflater         = activity.getLayoutInflater();
        rowView                         = inflater.inflate(R.layout.affiliatedhospitals_row, null);
        TextView title                  = (TextView) rowView.findViewById(R.id.title);
        TextView contents               = (TextView) rowView.findViewById(R.id.contents);
        AffiliatedHospitalsBean bean    = dataList.get(position);
        title.setText(bean.getName());
        contents.setText(bean.getSpecial());
        Button btn1                     = (Button) rowView.findViewById(R.id.btn1);
        Button btn2                     = (Button) rowView.findViewById(R.id.btn2);
        btn1.setTag(position);
        btn2.setTag(position);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Bundle args = new Bundle();
                AffiliatedHospitalsBean bean = dataList.get(Integer.valueOf(v.getTag().toString()));
                args.putString("cate", bean.getCode());
                args.putString("kind", bean.getHptCode());
                args.putString("name", bean.getName());
                mBaseFramgent.movePage(CareItemsFragment.newInstance(), args);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Bundle args               = new Bundle();
                AffiliatedHospitalsBean bean    = dataList.get(Integer.valueOf(v.getTag().toString()));
                args.putString("kind", bean.getCode());
                args.putString("name", bean.getName());
                mBaseFramgent.movePage(HospitalsInfoFragment.newInstance(), args);
            }
        });
        return rowView;
    }
}
