package ir.sanatisharif.android.konkur96.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.activity.SettingActivity;
import ir.sanatisharif.android.konkur96.adapter.MainShopItemAdapter;
import ir.sanatisharif.android.konkur96.api.Models.MainModel;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.dialog.MyAlertDialogFrg;
import ir.sanatisharif.android.konkur96.handler.Repository;
import ir.sanatisharif.android.konkur96.handler.RepositoryImpl;
import ir.sanatisharif.android.konkur96.handler.Result;
import ir.sanatisharif.android.konkur96.model.Events;
import ir.sanatisharif.android.konkur96.model.MainShopItem;
import ir.sanatisharif.android.konkur96.ui.component.paginate.paginate.myPaginate;
import ir.sanatisharif.android.konkur96.utils.ShopUtils;

public class ShopMainFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    boolean isPaginate = false;
    private RecyclerView            shopMainRecyclerView;
    private Toolbar                 mToolbar;
    private SwipeRefreshLayout      swipeRefreshLayout;
    private Repository              repository;
    private myPaginate              paginate;
    private LinearLayoutManager     linearLayoutManager;
    private MainModel               mainModel;
    private MainShopItemAdapter     adapter;
    private ArrayList<MainShopItem> items = new ArrayList<>();

    public static ShopMainFragment newInstance() {

        Bundle args = new Bundle();

        ShopMainFragment fragment = new ShopMainFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_shop, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        repository = new RepositoryImpl(getActivity());

        initView(view);
        getData();
    }

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
            Intent intent          = new Intent(Intent.ACTION_VIEW, Uri.parse(alaaTelegramUrl));
            intent.setPackage("org.telegram.messenger");

            try {
                if (intent.resolveActivity(AppConfig.context.getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(alaaTelegramUrl));
                }
                startActivity(intent);
            }
            catch (Exception ex) {
                Toast.makeText(AppConfig.context, "@alaa_sanatisharif", Toast.LENGTH_LONG).show();
            }

        }

        return super.onOptionsItemSelected(item);
    }

    public FragmentManager getHostFragmentManager() {
        FragmentManager fm = getFragmentManager();
        if (fm == null && isAdded()) {
            fm = getActivity().getSupportFragmentManager();
        }
        return fm;
    }

    private void getData() {

        items.clear();
        adapter.notifyDataSetChanged();

        swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(true));

        repository.getMainShop(data -> {

            if (data instanceof Result.Success) {

                setData((MainModel) ((Result.Success) data).value, true);
                swipeRefreshLayout.setRefreshing(false);

            } else {

                Log.d("Test", (String) ((Result.Error) data).value);
                swipeRefreshLayout.setRefreshing(false);
            }


        });


    }

    private void getDataPaginat() {

        if (isPaginate) {

            swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(true));

            repository.getNextPage(mainModel.getBlock().getNextPageUrl(), data -> {

                if (data instanceof Result.Success) {

                    setData((MainModel) ((Result.Success) data).value, false);
                    swipeRefreshLayout.setRefreshing(false);

                } else {

                    Log.d("Test", (String) ((Result.Error) data).value);
                    swipeRefreshLayout.setRefreshing(false);
                }


            });

        }


    }


    private void setData(MainModel data, Boolean first) {

        //---------------------- set mainModel data ---------------------------------------------
        mainModel = data;


        //---------------------- set paginate data ----------------------------------------------
        if (null != mainModel && null != mainModel.getBlock().getNextPageUrl()) {

            isPaginate = true;
            paginate.setNoMoreItems(false);

        } else {

            isPaginate = false;
            paginate.setNoMoreItems(true);
        }


        //---------------------- convert -------------------------------------------------------
        items.addAll(ShopUtils.convertToMainShopModel(data, first));


        //---------------------- update adapter ------------------------------------------------
        adapter.notifyDataSetChanged();
    }

    private void initView(View v) {

        // swipeRefreshLayout
        swipeRefreshLayout = v.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(AppConfig.colorSwipeRefreshing);
        swipeRefreshLayout.setOnRefreshListener(this);
        //recyclerView
        shopMainRecyclerView = v.findViewById(R.id.recyclerView_main_shop);
        shopMainRecyclerView.setNestedScrollingEnabled(false);
        shopMainRecyclerView.setHasFixedSize(true);
        linearLayoutManager =
                new LinearLayoutManager(AppConfig.context, RecyclerView.VERTICAL, false);
        shopMainRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MainShopItemAdapter(AppConfig.context, items);
        //adapter.setSize(AppConfig.width, AppConfig.height);
        shopMainRecyclerView.setAdapter(adapter);
        shopMainRecyclerView.setItemAnimator(new DefaultItemAnimator());
        paginate = myPaginate.with(shopMainRecyclerView)
                .setOnLoadMoreListener(this::getDataPaginat)
                .build();

        setHasOptionsMenu(true);
        setToolbar(mToolbar, "فروشگاه آلاء");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (null != paginate) {

            paginate.unbind();
        }
    }


    @Override
    public void onRefresh() {
        items.clear();
        getData();
    }
}

