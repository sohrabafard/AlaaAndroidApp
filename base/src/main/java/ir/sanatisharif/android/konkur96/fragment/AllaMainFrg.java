package ir.sanatisharif.android.konkur96.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Objects;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.activity.SettingActivity;
import ir.sanatisharif.android.konkur96.adapter.MainItemAdapter;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.dialog.MyAlertDialogFrg;
import ir.sanatisharif.android.konkur96.dialog.NotInternetDialogFrg;
import ir.sanatisharif.android.konkur96.handler.MainRepository;
import ir.sanatisharif.android.konkur96.listener.ICheckNetwork;
import ir.sanatisharif.android.konkur96.listener.api.IServerCallbackObject;
import ir.sanatisharif.android.konkur96.model.Events;
import ir.sanatisharif.android.konkur96.model.MainItem;
import ir.sanatisharif.android.konkur96.model.main_page.Datum;
import ir.sanatisharif.android.konkur96.model.main_page.MainPagesInfo;


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
    private MainRepository repository;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        repository = new MainRepository(Objects.requireNonNull(getActivity()));

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


        if (id == android.R.id.home) {
            Events.CloseFragment closeFragment = new Events.CloseFragment();
            closeFragment.setTagFragments("");
            EventBus.getDefault().post(closeFragment);

        } else if (id == R.id.actionSetting) {
            startActivity(new Intent(AppConfig.currentActivity, SettingActivity.class));
        } else if (id == R.id.actionSettingSupportBuy) {

            MyAlertDialogFrg alert = new MyAlertDialogFrg();
            alert.setTitle(getString(R.string.settingsSupportBuy));
            alert.setMessage(getString(R.string.supportBuy));
            alert.setHtml(true);
            alert.show(getHostFragmentManager(), "alert");
        } else if (id == R.id.actionSettingTelegram) {
            String alaaTelegramUrl = "https://telegram.me/joinchat/AAAAADwv5Wn78qn7-PT8fQ";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(alaaTelegramUrl));
            intent.setPackage("org.telegram.messenger");

            try {
                if (intent.resolveActivity(AppConfig.context.getPackageManager()) != null) {
                    startActivity(intent);
                }else {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(alaaTelegramUrl));
                }
                startActivity(intent);
            }catch (Exception ex){
                Toast.makeText(AppConfig.context,"@alaa_sanatisharif",Toast.LENGTH_LONG).show();
            }

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
        adapter = new MainItemAdapter(AppConfig.context, items);
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

        repository.mainPages(new IServerCallbackObject() {
            @Override
            public void onSuccess(Object obj) {
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

            if (block.getSets() != null && block.getSets().size() > 0) {
                item = new MainItem();
                item.setType(AppConstants.ITEM_SET);
                item.setSets(block.getSets());
                items.add(item);
            }
            if (block.getContents() != null && block.getContents().size() > 0) {
                item = new MainItem();
                item.setType(AppConstants.ITEM_CONTENT);
                item.setContents(block.getContents());
                items.add(item);
            }
            if (block.getBanners() != null && block.getBanners().size() > 0) {
                item = new MainItem();
                item.setType(AppConstants.ITEM_BANNER);
                item.setBanners(block.getBanners());
                items.add(item);
            }
            if (block.getProducts() != null && block.getProducts().size() > 0) {
                item = new MainItem();
                item.setType(AppConstants.ITEM_PRODUCT);
                item.setProducts(block.getProducts());
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

    public FragmentManager getHostFragmentManager() {
        FragmentManager fm = getFragmentManager();
        if (fm == null && isAdded()) {
            fm = ((AppCompatActivity)getActivity()).getSupportFragmentManager();
        }
        return fm;
    }
    private void showNotInternetDialogFrg() {
        try {
            if (!AppConfig.showNoInternetDialog) {
                NotInternetDialogFrg dialogFrg = new NotInternetDialogFrg().setNoInternetCallback(new NotInternetDialogFrg.NoInternetCallback() {
                    @Override
                    public void onClickOk() {
                        getData();
                    }
                });
                dialogFrg.show(getHostFragmentManager(), "");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
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

