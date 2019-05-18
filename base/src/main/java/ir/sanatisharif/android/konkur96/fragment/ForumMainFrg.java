package ir.sanatisharif.android.konkur96.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.greenrobot.eventbus.EventBus;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.activity.ActivityBase;
import ir.sanatisharif.android.konkur96.activity.SettingActivity;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.dialog.MyWaitingDialog;
import ir.sanatisharif.android.konkur96.model.Events;
import ir.sanatisharif.android.konkur96.ui.view.MDToast;

/**
 * Created by Mohamad on 10/13/2018.
 */

public class ForumMainFrg extends BaseFragment {

    private WebView webView;
    private Toolbar mToolbar;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static ForumMainFrg newInstance() {

        Bundle args = new Bundle();

        ForumMainFrg fragment = new ForumMainFrg();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forum, container, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            Events.CloseFragment closeFragment = new Events.CloseFragment();
            closeFragment.setTagFragments("");
            EventBus.getDefault().post(closeFragment);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        setting();

        webView.loadUrl("https://forum.sanatisharif.ir/");
        webView.setWebViewClient(new MyBrowser());

    }

    private void initView() {

        mToolbar = getView().findViewById(R.id.toolbar);
        setToolbar(mToolbar, "انجمن آلاء");
        webView = getView().findViewById(R.id.webView);
        swipeRefreshLayout = getView().findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(AppConfig.colorSwipeRefreshing);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.loadUrl("https://forum.sanatisharif.ir/");
            }
        });
    }

    private void setting() {

        // Configure related browser settings
        WebSettings webSettings = webView.getSettings();
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setJavaScriptEnabled(true);
        // Enable responsive layout
        webSettings.setUseWideViewPort(true);
        // Zoom out if the content width is greater than the width of the viewport
        webSettings.setLoadWithOverviewMode(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

    }

    private class MyBrowser extends WebViewClient {

        MyWaitingDialog waitingDialog;

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (waitingDialog == null) {
                waitingDialog = new MyWaitingDialog(getContext());
                waitingDialog.setDialog().show();
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            swipeRefreshLayout.setRefreshing(false);
            if (waitingDialog != null) {
                waitingDialog.dismiss();
            }
        }

        @Override
        public void onPageCommitVisible(WebView view, String url) {
            super.onPageCommitVisible(view, url);


        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}