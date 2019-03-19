package ir.sanatisharif.android.konkur96.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
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

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.account.AccountInfo;
import ir.sanatisharif.android.konkur96.activity.SettingActivity;
import ir.sanatisharif.android.konkur96.adapter.MyProductAdapter;
import ir.sanatisharif.android.konkur96.adapter.MyProductSetAdapter;
import ir.sanatisharif.android.konkur96.api.Models.ProductModel;
import ir.sanatisharif.android.konkur96.api.Models.ProductSetModel;
import ir.sanatisharif.android.konkur96.api.Models.myProductsModel;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.handler.Repository;
import ir.sanatisharif.android.konkur96.handler.RepositoryImpl;
import ir.sanatisharif.android.konkur96.handler.Result;
import ir.sanatisharif.android.konkur96.model.Events;
import ir.sanatisharif.android.konkur96.model.user.User;

import static ir.sanatisharif.android.konkur96.app.AppConstants.ACCOUNT_TYPE;

public class MyProductSet extends BaseFragment {

    Toolbar pageToolbar;
    RecyclerView productSetRecyclerView;
    private LinearLayoutManager linearLayoutManager;

    private MyProductSetAdapter adapter;
    private ArrayList<ProductSetModel> items = new ArrayList<>();


    public static MyProductSet newInstance(ArrayList<ProductSetModel> list) {

        Bundle args = new Bundle();

        MyProductSet fragment = new MyProductSet();
        args.putParcelableArrayList("list", list);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_myproduct_set, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initList();
        initView(view);
        setData();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main_shop, menu);
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


    private void initView(View v) {

        //recyclerView
        productSetRecyclerView = v.findViewById(R.id.recyclerView_main_bought_set);
        productSetRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(AppConfig.context);
        productSetRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MyProductSetAdapter(getContext(), items);
        productSetRecyclerView.setAdapter(adapter);
        productSetRecyclerView.setItemAnimator(new DefaultItemAnimator());

        setHasOptionsMenu(true);
        setToolbar(pageToolbar, "آلاء مجری توسعه عدالت آموزشی");

    }


    private void setData() {

        //---------------------- update adapter ------------------------------------------------
        adapter.notifyDataSetChanged();
    }

    private void initList() {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            items = bundle.getParcelableArrayList("list");
        }
    }





}

