package com.greencross.medigene.affiliatedhospitals;

import java.math.BigDecimal;
import java.math.RoundingMode;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.greencross.medigene.R;
import com.greencross.medigene.base.BaseFragment;
import com.greencross.medigene.base.CommonActionBar;

/**
 * 건강검진 제휴병원 - 메인 화면
 */
public class AffiliatedHospitalsFragment extends BaseFragment implements OnClickListener {
    public LinearLayout table;
    public LinearLayout map;
    private FragmentActivity fac;

    private FrameLayout mmmm;

    private ImageView b2tn1;
    private ImageView b2tn2;
    private ImageView b2tn3;
    private ImageView b2tn4;
    private ImageView b2tn5;
    private ImageView b2tn6;
    private ImageView b2tn7;
    private ImageView b2tn8;
    private ImageView b2tn9;
    private ImageView b2tn10;
    private ImageView b2tn11;
    private ImageView b2tn12;
    private ImageView b2tn13;
    private ImageView b2tn14;
    private ImageView b2tn15;
    private ImageView b2tn16;

    private Button table_btn;
    private Button map_btn;

    public static Fragment newInstance() {
        AffiliatedHospitalsFragment fragment = new AffiliatedHospitalsFragment();
        return fragment;
    }


    @Override
    public void loadActionbar(CommonActionBar actionBar) {
        super.loadActionbar(actionBar);
        actionBar.setActionBarTitle("건강검진 제휴병원");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.affiliatedhospitals_fragment, null);

        fac = getActivity();
        table = (LinearLayout) view.findViewById(R.id.table);
        map = (LinearLayout) view.findViewById(R.id.map);
        table_btn = (Button) view.findViewById(R.id.change_table_btn);
        map_btn = (Button) view.findViewById(R.id.change_map_btn);
        table_btn.setOnClickListener(this);
        map_btn.setOnClickListener(this);

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

        mmmm = (FrameLayout) view.findViewById(R.id.area);

        b2tn1 = (ImageView) view.findViewById(R.id.Seoul_btn2);
        b2tn2 = (ImageView) view.findViewById(R.id.Gyeonggi_btn2);
        b2tn3 = (ImageView) view.findViewById(R.id.Incheon_btn2);
        b2tn4 = (ImageView) view.findViewById(R.id.Busan_btn2);
        b2tn5 = (ImageView) view.findViewById(R.id.Ulsan_btn2);
        b2tn6 = (ImageView) view.findViewById(R.id.Gyeongnam_btn2);
        b2tn7 = (ImageView) view.findViewById(R.id.Daegu_btn2);
        b2tn8 = (ImageView) view.findViewById(R.id.Gyeonbuk_btn2);
        b2tn9 = (ImageView) view.findViewById(R.id.Daejeon_btn2);
        b2tn10 = (ImageView) view.findViewById(R.id.Chungnam_btn2);
        b2tn11 = (ImageView) view.findViewById(R.id.Chungbuk_btn2);
        b2tn12 = (ImageView) view.findViewById(R.id.Jeonbuk_btn2);
        b2tn13 = (ImageView) view.findViewById(R.id.Gwangju_btn2);
        b2tn14 = (ImageView) view.findViewById(R.id.Jeonnam_btn2);
        b2tn15 = (ImageView) view.findViewById(R.id.Gangwon_btn2);
        b2tn16 = (ImageView) view.findViewById(R.id.Jeju_btn2);

        b2tn1.setOnClickListener(this);
        b2tn2.setOnClickListener(this);
        b2tn3.setOnClickListener(this);
        b2tn4.setOnClickListener(this);
        b2tn5.setOnClickListener(this);
        b2tn6.setOnClickListener(this);
        b2tn7.setOnClickListener(this);
        b2tn8.setOnClickListener(this);
        b2tn9.setOnClickListener(this);
        b2tn10.setOnClickListener(this);
        b2tn11.setOnClickListener(this);
        b2tn12.setOnClickListener(this);
        b2tn13.setOnClickListener(this);
        b2tn14.setOnClickListener(this);
        b2tn15.setOnClickListener(this);
        b2tn16.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        Bundle args = new Bundle();
        if (view.getId() == R.id.change_map_btn) {
            table.setVisibility(View.INVISIBLE);
            map.setVisibility(View.VISIBLE);
            map_btn.setVisibility(View.INVISIBLE);
            table_btn.setVisibility(View.VISIBLE);
            setMapImagePosition();

        } else if (view.getId() == R.id.change_table_btn) {
            table.setVisibility(View.VISIBLE);
            map.setVisibility(View.INVISIBLE);
            table_btn.setVisibility(View.INVISIBLE);
            map_btn.setVisibility(View.VISIBLE);
        } else {

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

            movePage(AffiliatedHospitalsList.newInstance(), args);
        }
    }

    /**
     * 지도에서 지역명 위치 계산
     */
    private void setMapImagePosition() {
        BigDecimal width800 = new BigDecimal(mmmm.getWidth());
        BigDecimal height1280 = new BigDecimal(mmmm.getHeight());

        float aoo = 0.8f;

        /** 이미지 크기 계산 */
        BigDecimal a = new BigDecimal(aoo * 7.69);//104
        int aa = width800.divide(a, RoundingMode.CEILING).setScale(0, BigDecimal.ROUND_CEILING).intValue();
        BigDecimal b = new BigDecimal(aoo * 6.77);//118
        int bb = width800.divide(b, RoundingMode.CEILING).setScale(0, BigDecimal.ROUND_CEILING).intValue();

        float ao = 1;
        float bo = 1;

        /** 이미지 위치 계산 - 숫자가 크면 왼쪽  숫자가 크면 위쪽*/
        BigDecimal a1 = new BigDecimal(ao * 4.6);//서울
        BigDecimal a2 = new BigDecimal(bo * 5.2);
        BigDecimal b1 = new BigDecimal(ao * 3.8);//경기
        BigDecimal b2 = new BigDecimal(bo * 3.5);
        BigDecimal c1 = new BigDecimal(ao * 17.4);//인천
        BigDecimal c2 = new BigDecimal(bo * 4.1);
        BigDecimal d1 = new BigDecimal(ao * 1.32);//부산b
        BigDecimal d2 = new BigDecimal(bo * 1.46);
        BigDecimal e1 = new BigDecimal(ao * 1.24);//울산b
        BigDecimal e2 = new BigDecimal(bo * 1.66);
        BigDecimal f1 = new BigDecimal(ao * 2.0);//경남
        BigDecimal f2 = new BigDecimal(bo * 1.5);
        BigDecimal g1 = new BigDecimal(ao * 1.51);//대구b
        BigDecimal g2 = new BigDecimal(bo * 1.92);
        BigDecimal h1 = new BigDecimal(ao * 1.68);//경북
        BigDecimal h2 = new BigDecimal(bo * 2.38);
        BigDecimal i1 = new BigDecimal(ao * 2.52);//대전b
        BigDecimal i2 = new BigDecimal(bo * 2.30);
        BigDecimal j1 = new BigDecimal(ao * 5.4);//충남a
        BigDecimal j2 = new BigDecimal(bo * 2.5);
        BigDecimal k1 = new BigDecimal(ao * 2.48);//충북a
        BigDecimal k2 = new BigDecimal(bo * 2.86);
        BigDecimal l1 = new BigDecimal(ao * 4.0);//전북a
        BigDecimal l2 = new BigDecimal(bo * 1.74);
        BigDecimal m1 = new BigDecimal(ao * 13.2);//광주b
        BigDecimal m2 = new BigDecimal(bo * 1.46);
        BigDecimal n1 = new BigDecimal(ao * 4.8);//전남
        BigDecimal n2 = new BigDecimal(bo * 1.28);
        BigDecimal o1 = new BigDecimal(ao * 2.0);//강원
        BigDecimal o2 = new BigDecimal(bo * 6.0);
        BigDecimal p1 = new BigDecimal(ao * 1.36);//제주
        BigDecimal p2 = new BigDecimal(bo * 1.23);

        RelativeLayout.LayoutParams tvParams = new RelativeLayout.LayoutParams(aa, aa);
        tvParams.setMargins(width800.divide(a1, RoundingMode.CEILING).setScale(0, BigDecimal.ROUND_CEILING).intValue(), height1280.divide(a2, RoundingMode.CEILING).setScale(0, BigDecimal.ROUND_CEILING).intValue(), 0, 0);
        b2tn1.setLayoutParams(tvParams);

        RelativeLayout.LayoutParams tvParams2 = new RelativeLayout.LayoutParams(aa, aa);
        tvParams2.setMargins(width800.divide(b1, RoundingMode.CEILING).setScale(0, BigDecimal.ROUND_CEILING).intValue(), height1280.divide(b2, RoundingMode.CEILING).setScale(0, BigDecimal.ROUND_CEILING).intValue(), 0, 0);
        b2tn2.setLayoutParams(tvParams2);

        RelativeLayout.LayoutParams tvParams3 = new RelativeLayout.LayoutParams(aa, aa);
        tvParams3.setMargins(width800.divide(c1, RoundingMode.CEILING).setScale(0, BigDecimal.ROUND_CEILING).intValue(), height1280.divide(c2, RoundingMode.CEILING).setScale(0, BigDecimal.ROUND_CEILING).intValue(), 0, 0);
        b2tn3.setLayoutParams(tvParams3);

        RelativeLayout.LayoutParams tvParams4 = new RelativeLayout.LayoutParams(bb, bb);
        tvParams4.setMargins(width800.divide(d1, RoundingMode.CEILING).setScale(0, BigDecimal.ROUND_CEILING).intValue(), height1280.divide(d2, RoundingMode.CEILING).setScale(0, BigDecimal.ROUND_CEILING).intValue(), 0, 0);
        b2tn4.setLayoutParams(tvParams4);

        RelativeLayout.LayoutParams tvParams5 = new RelativeLayout.LayoutParams(bb, bb);
        tvParams5.setMargins(width800.divide(e1, RoundingMode.CEILING).setScale(0, BigDecimal.ROUND_CEILING).intValue(), height1280.divide(e2, RoundingMode.CEILING).setScale(0, BigDecimal.ROUND_CEILING).intValue(), 0, 0);
        b2tn5.setLayoutParams(tvParams5);

        RelativeLayout.LayoutParams tvParams6 = new RelativeLayout.LayoutParams(aa, aa);
        tvParams6.setMargins(width800.divide(f1, RoundingMode.CEILING).setScale(0, BigDecimal.ROUND_CEILING).intValue(), height1280.divide(f2, RoundingMode.CEILING).setScale(0, BigDecimal.ROUND_CEILING).intValue(), 0, 0);
        b2tn6.setLayoutParams(tvParams6);

        RelativeLayout.LayoutParams tvParams7 = new RelativeLayout.LayoutParams(bb, bb);
        tvParams7.setMargins(width800.divide(g1, RoundingMode.CEILING).setScale(0, BigDecimal.ROUND_CEILING).intValue(), height1280.divide(g2, RoundingMode.CEILING).setScale(0, BigDecimal.ROUND_CEILING).intValue(), 0, 0);
        b2tn7.setLayoutParams(tvParams7);

        RelativeLayout.LayoutParams tvParams8 = new RelativeLayout.LayoutParams(aa, aa);
        tvParams8.setMargins(width800.divide(h1, RoundingMode.CEILING).setScale(0, BigDecimal.ROUND_CEILING).intValue(), height1280.divide(h2, RoundingMode.CEILING).setScale(0, BigDecimal.ROUND_CEILING).intValue(), 0, 0);
        b2tn8.setLayoutParams(tvParams8);

        RelativeLayout.LayoutParams tvParams9 = new RelativeLayout.LayoutParams(bb, bb);
        tvParams9.setMargins(width800.divide(i1, RoundingMode.CEILING).setScale(0, BigDecimal.ROUND_CEILING).intValue(), height1280.divide(i2, RoundingMode.CEILING).setScale(0, BigDecimal.ROUND_CEILING).intValue(), 0, 0);
        b2tn9.setLayoutParams(tvParams9);

        RelativeLayout.LayoutParams tvParams10 = new RelativeLayout.LayoutParams(aa, aa);
        tvParams10.setMargins(width800.divide(j1, RoundingMode.CEILING).setScale(0, BigDecimal.ROUND_CEILING).intValue(), height1280.divide(j2, RoundingMode.CEILING).setScale(0, BigDecimal.ROUND_CEILING).intValue(), 0, 0);
        b2tn10.setLayoutParams(tvParams10);

        RelativeLayout.LayoutParams tvParams11 = new RelativeLayout.LayoutParams(aa, aa);
        tvParams11.setMargins(width800.divide(k1, RoundingMode.CEILING).setScale(0, BigDecimal.ROUND_CEILING).intValue(), height1280.divide(k2, RoundingMode.CEILING).setScale(0, BigDecimal.ROUND_CEILING).intValue(), 0, 0);
        b2tn11.setLayoutParams(tvParams11);

        RelativeLayout.LayoutParams tvParams12 = new RelativeLayout.LayoutParams(aa, aa);
        tvParams12.setMargins(width800.divide(l1, RoundingMode.CEILING).setScale(0, BigDecimal.ROUND_CEILING).intValue(), height1280.divide(l2, RoundingMode.CEILING).setScale(0, BigDecimal.ROUND_CEILING).intValue(), 0, 0);
        b2tn12.setLayoutParams(tvParams12);

        RelativeLayout.LayoutParams tvParams13 = new RelativeLayout.LayoutParams(bb, bb);
        tvParams13.setMargins(width800.divide(m1, RoundingMode.CEILING).setScale(0, BigDecimal.ROUND_CEILING).intValue(), height1280.divide(m2, RoundingMode.CEILING).setScale(0, BigDecimal.ROUND_CEILING).intValue(), 0, 0);
        b2tn13.setLayoutParams(tvParams13);

        RelativeLayout.LayoutParams tvParams14 = new RelativeLayout.LayoutParams(aa, aa);
        tvParams14.setMargins(width800.divide(n1, RoundingMode.CEILING).setScale(0, BigDecimal.ROUND_CEILING).intValue(), height1280.divide(n2, RoundingMode.CEILING).setScale(0, BigDecimal.ROUND_CEILING).intValue(), 0, 0);
        b2tn14.setLayoutParams(tvParams14);

        RelativeLayout.LayoutParams tvParams15 = new RelativeLayout.LayoutParams(aa, aa);
        tvParams15.setMargins(width800.divide(o1, RoundingMode.CEILING).setScale(0, BigDecimal.ROUND_CEILING).intValue(), height1280.divide(o2, RoundingMode.CEILING).setScale(0, BigDecimal.ROUND_CEILING).intValue(), 0, 0);
        b2tn15.setLayoutParams(tvParams15);

        RelativeLayout.LayoutParams tvParams16 = new RelativeLayout.LayoutParams(aa, aa);
        tvParams16.setMargins(width800.divide(p1, RoundingMode.CEILING).setScale(0, BigDecimal.ROUND_CEILING).intValue(), height1280.divide(p2, RoundingMode.CEILING).setScale(0, BigDecimal.ROUND_CEILING).intValue(), 0, 0);
        b2tn16.setLayoutParams(tvParams16);

    }

}
