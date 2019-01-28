package ir.sanatisharif.android.konkur96.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import ir.sanatisharif.android.konkur96.R;

public class WebLandingActivity extends BaseFragment {

    private WebView webView;
    private String recivedURL= "https://sanatisharif.ir";

    @Override
    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_web, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        loadURL(recivedURL);

    }

    private void initView(View view) {

        webView = view.findViewById(R.id.web_view);
    }

    private void loadURL(String url) {
        webView.loadUrl(url);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }


    public static WebLandingActivity newInstance(String url) {

        Bundle args = new Bundle();
        args.putString("URL_KEY", url);
        WebLandingActivity fragment = new WebLandingActivity();
        fragment.setArguments(args);
        return fragment;
    }

}
