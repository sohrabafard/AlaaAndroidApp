package ir.sanatisharif.android.konkur96.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.activity.SettingActivity;
import ir.sanatisharif.android.konkur96.adapter.MyProductSetAdapter;
import ir.sanatisharif.android.konkur96.api.Models.SetModel;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.model.Events;

public class MyProductSet extends BaseFragment {

    Toolbar      pageToolbar;
    RecyclerView productSetRecyclerView;
    private LinearLayoutManager linearLayoutManager;

    private MyProductSetAdapter adapter;
    private ArrayList<SetModel> items = new ArrayList<>();


    public static MyProductSet newInstance(ArrayList<SetModel> list) {

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


