package com.greencross.medigene.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greencross.medigene.R;
import com.greencross.medigene.base.BaseFragment;
import com.greencross.medigene.base.CommonActionBar;
import com.greencross.medigene.base.IBaseFragment;

/**
 * Created by insystemscompany on 2017. 2. 28..
 */

public class SettingAgreeFragment extends BaseFragment implements IBaseFragment {

    String appVersion;

    public static Fragment newInstance() {
        SettingAgreeFragment fragment = new SettingAgreeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_agree, container, false);
        return view;
    }


    @Override
    public void loadActionbar(CommonActionBar actionBar) {
        super.loadActionbar(actionBar);
        actionBar.setActionBarTitle(getString(R.string.text_agree));
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        CFontTextView versionTextView = (CFontTextView) view.findViewById(R.id.setting_version_textview);
//        versionTextView.setText(PackageUtil.getVersionInfo(getContext()));
//
//        PackageInfo i = null;
//        try {
//            i = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
//            appVersion = i.versionName;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        String appV = appVersion.replace(".", "");
    }

}
