package com.greencross.medigene;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.greencross.medigene.base.BaseFragment;
import com.greencross.medigene.base.CommonActionBar;
import com.greencross.medigene.component.CDialog;

/**
 * Created by insystemscompany on 2017. 6. 15..
 */


public class WebViewFragment extends BaseFragment {

    private TextView mTextViewWeight;


    public static Fragment newInstance() {
        WebViewFragment fragment = new WebViewFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.webview_fragment, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void loadActionbar(CommonActionBar actionBar) {
        super.loadActionbar(actionBar);

        if (getArguments() != null) {
            String title = getArguments().getString(CommonActionBar.ACTION_BAR_TITLE);
            if (TextUtils.isEmpty(title) == false) {
                actionBar.setActionBarTitle(title);
            }
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        WebView webView = (WebView) view.findViewById(R.id.default_webview);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true); // javascript를 실행할 수 있도록 설정
        settings.setJavaScriptCanOpenWindowsAutomatically (true);   // javascript가 window.open()을 사용할 수 있도록 설정
        settings.setBuiltInZoomControls(false); // 안드로이드에서 제공하는 줌 아이콘을 사용할 수 있도록 설정
        settings.setSupportMultipleWindows(false); // 여러개의 윈도우를 사용할 수 있도록 설정
        settings.setSupportZoom(false); // 확대,축소 기능을 사용할 수 있도록 설정
        settings.setBlockNetworkImage(false); // 네트워크의 이미지의 리소스를 로드하지않음
        settings.setLoadsImagesAutomatically(true); // 웹뷰가 앱에 등록되어 있는 이미지 리소스를 자동으로 로드하도록 설정
        settings.setUseWideViewPort(true); // wide viewport를 사용하도록 설정
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 웹뷰가 캐시를 사용하지 않도록 설정

        webView.setWebViewClient(new WebViewClientClass());
        webView.setWebChromeClient(new setWebChromClient());


        if (getArguments() != null) {
            String goUrl = getArguments().getString(PARAMS_ACTION_URL);
            if (TextUtils.isEmpty(goUrl) == false) {
                webView.loadUrl(goUrl);
            }
        }
    }


    private class WebViewClientClass extends WebViewClient {

//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            return false;
//        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

            return super.shouldOverrideUrlLoading(view, request);
        }
    }



    private class setWebChromClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress >= 100) {
                hideProgress();
            } else {
                showProgress();
            }
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result) {
            CDialog.showDlg(getContext(), message);
            return true;
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
//            if (!TextUtils.isEmpty(title)) {
//                Log.i(TAG, "onReceivedTitle=" + title);
//                setTitle(title);
//            } else {
//                mTitle = "soil";
//            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}