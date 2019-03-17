package ir.sanatisharif.android.konkur96.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ir.sanatisharif.android.konkur96.R;

import ir.sanatisharif.android.konkur96.activity.ActivityBase;
import ir.sanatisharif.android.konkur96.activity.SettingActivity;
import ir.sanatisharif.android.konkur96.adapter.MainItemAdapter;
import ir.sanatisharif.android.konkur96.api.MainApi;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.dialog.NotInternetDialogFrg;
import ir.sanatisharif.android.konkur96.listener.ICheckNetwork;
import ir.sanatisharif.android.konkur96.listener.api.IServerCallbackObject;
import ir.sanatisharif.android.konkur96.model.Events;
import ir.sanatisharif.android.konkur96.model.MainItem;
import ir.sanatisharif.android.konkur96.model.main_page.Datum;
import ir.sanatisharif.android.konkur96.model.main_page.MainPagesInfo;
import ir.sanatisharif.android.konkur96.model.main_page.Set;
import ir.sanatisharif.android.konkur96.ui.GlideApp;
import ir.sanatisharif.android.konkur96.ui.view.MDToast;


/**
 * Created by Mohamad on 10/13/2018.
 */

public class AllaMainFrg extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener,
        ICheckNetwork {

    private Toolbar mToolbar;
    private RecyclerView myRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private MainItemAdapter adapter;
    private ArrayList<MainItem> items = new ArrayList<>();

    public static AllaMainFrg newInstance() {

        Bundle args = new Bundle();
        AllaMainFrg fragment = new AllaMainFrg();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        AppConfig.mInstance.setICheckNetwork(this);
    }

    @Override
    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alla, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        getData();
    }

    //<editor-fold desc="menu">
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.actionSetting) {
        }
        if (id == android.R.id.home) {
            Events.CloseFragment closeFragment = new Events.CloseFragment();
            closeFragment.setTagFragments("");
            EventBus.getDefault().post(closeFragment);

        } else if (id == R.id.actionSetting) {
            startActivity(new Intent(AppConfig.currentActivity, SettingActivity.class));

        }

        return super.onOptionsItemSelected(item);
    }
    //</editor-fold>

    //<editor-fold desc="initView">
    private void initView(View v) {

        //recyclerView
        swipeRefreshLayout = v.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(AppConfig.colorSwipeRefreshing);
        myRecyclerView = v.findViewById(R.id.recyclerView);
        myRecyclerView.setNestedScrollingEnabled(false);
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(AppConfig.context, LinearLayoutManager.VERTICAL, false));
        adapter = new MainItemAdapter(AppConfig.context, items, GlideApp.with(this));
        adapter.setSize(AppConfig.width, AppConfig.height);
        myRecyclerView.setAdapter(adapter);

        setHasOptionsMenu(true);
        setToolbar(mToolbar, "آلاء مجری توسعه عدالت آموزشی");

        //listener
        swipeRefreshLayout.setOnRefreshListener(this);

    }
    //</editor-fold>

    //<editor-fold desc="get data from server">
    private void getData() {

        items.clear();
        adapter.notifyDataSetChanged();

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });

        MainApi.getInstance().mainPages(new IServerCallbackObject() {
            @Override
            public void onSuccess(Object obj) {
                Log.i("LOG", "onFailure1: onSuccess");
                mapData((MainPagesInfo) obj);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(String message) {
                Log.i("LOG", "onFailure1: fai");
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    //</editor-fold>

    //<editor-fold desc="data mapping to list">
    private void mapData(MainPagesInfo mainPagesInfo) {

        //sliders
        MainItem item = new MainItem();
        item.setType(AppConstants.ITEM_SLIDER);
        item.setSliders(mainPagesInfo.getMainBanner());
        items.add(item);

        //sets - contents- products- banners
        for (int i = 0; i < mainPagesInfo.getBlock().getData().size(); i++) {

            Datum block = mainPagesInfo.getBlock().getData().get(i);

            item = new MainItem();
            item.setId(block.getId());
            item.setTitle(block.getTitle());
            item.setUrl(block.getUrl());
            item.setOffer(block.getOffer());
            item.setType(AppConstants.HEADER_DATA);
            items.add(item);

            if (block.getSets().size() > 0) {
                item = new MainItem();
                item.setType(AppConstants.ITEM_SET);
                item.setSets(block.getSets());
                items.add(item);
            }
            if (block.getContents().size() > 0) {
                item = new MainItem();
                item.setType(AppConstants.ITEM_CONTENT);
                item.setContents(block.getContents());
                items.add(item);
            }
            if (block.getBanners().size() > 0) {
                item = new MainItem();
                item.setType(AppConstants.ITEM_BANNER);
                item.setBanners(block.getBanners());
                items.add(item);
            }
        }

        adapter.notifyDataSetChanged();
    }
    //</editor-fold>

    //<editor-fold desc="onRefresh">
    @Override
    public void onRefresh() {

        items.clear();
        getData();
    }

    //</editor-fold>


    private void showNotInternetDialogFrg() {

        if (!AppConfig.showNoInternetDialog)
            new NotInternetDialogFrg().setNoInternetCallback(new NotInternetDialogFrg.NoInternetCallback() {
                @Override
                public void onClickOk() {
                    getData();
                }
            }).show(getFragmentManager(), "");
    }


    @Override
    public void onCheckNetwork(boolean flag) {
        if (!flag)//if false
            showNotInternetDialogFrg();
        else {
            if (items.size() == 0)
                getData();
        }
    }
}

