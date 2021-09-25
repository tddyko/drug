package com.greencross.medigene.apply;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.greencross.medigene.R;
import com.greencross.medigene.base.BaseFragment;
import com.greencross.medigene.base.CommonActionBar;


/**
 * Created by godaewon on 2017. 7. 4..
 */

public class CheckApplyMapFragment extends BaseFragment implements View.OnClickListener {
    public static Fragment newInstance() {
        CheckApplyMapFragment fragment = new CheckApplyMapFragment();
        return fragment;
    }


    @Override
    public void loadActionbar(CommonActionBar actionBar) {
        super.loadActionbar(actionBar);
        actionBar.setActionBarTitle("검사 신청");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check_apply_map, null);

        FrameLayout btn1 = (FrameLayout) view.findViewById(R.id.Seoul_btn);
        FrameLayout btn2 = (FrameLayout) view.findViewById(R.id.Gyeonggi_btn);
        FrameLayout btn3 = (FrameLayout) view.findViewById(R.id.Incheon_btn);
        FrameLayout btn4 = (FrameLayout) view.findViewById(R.id.Busan_btn);
        FrameLayout btn5 = (FrameLayout) view.findViewById(R.id.Ulsan_btn);
        FrameLayout btn6 = (FrameLayout) view.findViewById(R.id.Gyeongnam_btn);
        FrameLayout btn7 = (FrameLayout) view.findViewById(R.id.Daegu_btn);
        FrameLayout btn8 = (FrameLayout) view.findViewById(R.id.Gyeonbuk_btn);
        FrameLayout btn9 = (FrameLayout) view.findViewById(R.id.Daejeon_btn);
        FrameLayout btn10 = (FrameLayout) view.findViewById(R.id.Chungnam_btn);
        FrameLayout btn11 = (FrameLayout) view.findViewById(R.id.Chungbuk_btn);
        FrameLayout btn12 = (FrameLayout) view.findViewById(R.id.Jeonbuk_btn);
        FrameLayout btn13 = (FrameLayout) view.findViewById(R.id.Gwangju_btn);
        FrameLayout btn14 = (FrameLayout) view.findViewById(R.id.Jeonnam_btn);
        FrameLayout btn15 = (FrameLayout) view.findViewById(R.id.Gangwon_btn);
        FrameLayout btn16 = (FrameLayout) view.findViewById(R.id.Jeju_btn);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);
        btn11.setOnClickListener(this);
        btn12.setOnClickListener(this);
        btn13.setOnClickListener(this);
        btn14.setOnClickListener(this);
        btn15.setOnClickListener(this);
        btn16.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        Bundle args = new Bundle();

        switch (view.getId()) {

            case R.id.Seoul_btn:
                args.putString("kind", "1");
                args.putString("name", getString(R.string.seoul));
                break;
            case R.id.Gyeonggi_btn:
                args.putString("kind", "2");
                args.putString("name", getString(R.string.gyeonggi));
                break;
            case R.id.Incheon_btn:
                args.putString("kind", "3");
                args.putString("name", getString(R.string.incheon));
                break;
            case R.id.Busan_btn:
                args.putString("kind", "4");
                args.putString("name", getString(R.string.busan));
                break;
            case R.id.Ulsan_btn:
                args.putString("kind", "5");
                args.putString("name", getString(R.string.ulsan));
                break;
            case R.id.Gyeongnam_btn:
                args.putString("kind", "6");
                args.putString("name", getString(R.string.gyeonnam));
                break;
            case R.id.Daegu_btn:
                args.putString("kind", "7");
                args.putString("name", getString(R.string.daegu));
                break;
            case R.id.Gyeonbuk_btn:
                args.putString("kind", "8");
                args.putString("name", getString(R.string.gyeonbuk));
                break;
            case R.id.Daejeon_btn:
                args.putString("kind", "9");
                args.putString("name", getString(R.string.daejeon));
                break;
            case R.id.Chungnam_btn:
                args.putString("kind", "10");
                args.putString("name", getString(R.string.chungnam));
                break;
            case R.id.Chungbuk_btn:
                args.putString("kind", "11");
                args.putString("name", getString(R.string.chungbuk));
                break;
            case R.id.Jeonbuk_btn:
                args.putString("kind", "12");
                args.putString("name", getString(R.string.jeonbuk));
                break;
            case R.id.Gwangju_btn:
                args.putString("kind", "13");
                args.putString("name", getString(R.string.gwangju));
                break;
            case R.id.Jeonnam_btn:
                args.putString("kind", "14");
                args.putString("name", getString(R.string.jeonnam));
                break;
            case R.id.Gangwon_btn:
                args.putString("kind", "15");
                args.putString("name", getString(R.string.gangwon));
                break;
            case R.id.Jeju_btn:
                args.putString("kind", "16");
                args.putString("name", getString(R.string.jeju));
                break;
            case R.id.Seoul_btn2:
                args.putString("kind", "1");
                args.putString("name", getString(R.string.seoul));
                break;
            case R.id.Gyeonggi_btn2:
                args.putString("kind", "2");
                args.putString("name", getString(R.string.gyeonggi));
                break;
            case R.id.Incheon_btn2:
                args.putString("kind", "3");
                args.putString("name", getString(R.string.incheon));
                break;
            case R.id.Busan_btn2:
                args.putString("kind", "4");
                args.putString("name", getString(R.string.busan));
                break;
            case R.id.Ulsan_btn2:
                args.putString("kind", "5");
                args.putString("name", getString(R.string.ulsan));
                break;
            case R.id.Gyeongnam_btn2:
                args.putString("kind", "6");
                args.putString("name", getString(R.string.gyeonnam));
                break;
            case R.id.Daegu_btn2:
                args.putString("kind", "7");
                args.putString("name", getString(R.string.daegu));
                break;
            case R.id.Gyeonbuk_btn2:
                args.putString("kind", "8");
                args.putString("name", getString(R.string.gyeonbuk));
                break;
            case R.id.Daejeon_btn2:
                args.putString("kind", "9");
                args.putString("name", getString(R.string.daejeon));
                break;
            case R.id.Chungnam_btn2:
                args.putString("kind", "10");
                args.putString("name", getString(R.string.chungnam));
                break;
            case R.id.Chungbuk_btn2:
                args.putString("kind", "11");
                args.putString("name", getString(R.string.chungbuk));
                break;
            case R.id.Jeonbuk_btn2:
                args.putString("kind", "12");
                args.putString("name", getString(R.string.jeonbuk));
                break;
            case R.id.Gwangju_btn2:
                args.putString("kind", "13");
                args.putString("name", getString(R.string.gwangju));
                break;
            case R.id.Jeonnam_btn2:
                args.putString("kind", "14");
                args.putString("name", getString(R.string.jeonnam));
                break;
            case R.id.Gangwon_btn2:
                args.putString("kind", "15");
                args.putString("name", getString(R.string.gangwon));
                break;
            case R.id.Jeju_btn2:
                args.putString("kind", "16");
                args.putString("name", getString(R.string.jeju));
                break;
        }

        movePage(CheckApplyListFragment.newInstance(), args);
    }
}
