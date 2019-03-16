package ir.sanatisharif.android.konkur96.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
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
import ir.sanatisharif.android.konkur96.adapter.MainShopItemAdapter;
import ir.sanatisharif.android.konkur96.api.Models.AddToCardListModel;
import ir.sanatisharif.android.konkur96.api.Models.CardReviewModel;
import ir.sanatisharif.android.konkur96.api.Models.MainModel;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.handler.Repository;
import ir.sanatisharif.android.konkur96.handler.RepositoryImpl;
import ir.sanatisharif.android.konkur96.handler.Result;
import ir.sanatisharif.android.konkur96.model.Events;
import ir.sanatisharif.android.konkur96.model.MainShopItem;
import ir.sanatisharif.android.konkur96.model.user.User;
import ir.sanatisharif.android.konkur96.ui.component.paginate.paginate.myPaginate;
import ir.sanatisharif.android.konkur96.utils.ShopUtils;

import static ir.sanatisharif.android.konkur96.app.AppConstants.ACCOUNT_TYPE;
import static ir.sanatisharif.android.konkur96.app.AppConstants.AUTHTOKEN_TYPE_FULL_ACCESS;

public class CardFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    private Toolbar pageToolbar;
    private SwipeRefreshLayout swipeRefreshLayout;

    private Repository repository;
    private AccountInfo accountInfo;
    private User user;

    private CardReviewModel cardReviewModel;

    private ArrayList<CardReviewModel> items = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;

    private RecyclerView productsRecyclerView;

    public static CardFragment newInstance() {

        Bundle args = new Bundle();

        CardFragment fragment = new CardFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_card_shop, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        repository = new RepositoryImpl(getActivity());
        accountInfo = new AccountInfo(getContext(), getActivity());
        user = accountInfo.getInfo(ACCOUNT_TYPE);

        initView(view);
        getData();
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

    private void getData() {

        if (accountInfo.ExistAccount(ACCOUNT_TYPE)) {

            accountInfo.getExistingAccountAuthToken(ACCOUNT_TYPE, AUTHTOKEN_TYPE_FULL_ACCESS, new AccountInfo.AuthToken() {
                @Override
                public void onToken(String token) {

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(true));

                            repository.cardReview(token, data -> {

                                if (data instanceof Result.Success) {

                                    setData((CardReviewModel) ((Result.Success) data).value, true);
                                    swipeRefreshLayout.setRefreshing(false);

                                } else {

                                    Log.d("Test", (String) ((Result.Error) data).value);
                                    swipeRefreshLayout.setRefreshing(false);
                                }


                            });

                        }
                    });

                }
            });
        }


    }

    private void setData(CardReviewModel data, Boolean first) {

        //---------------------- set cardReviewModel data ---------------------------------------------
        cardReviewModel = data;



    }

    private void initView(View v) {

        //swipeRefreshLayout
        swipeRefreshLayout = v.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(AppConfig.colorSwipeRefreshing);
        swipeRefreshLayout.setOnRefreshListener(this);
        //recyclerView
        productsRecyclerView = v.findViewById(R.id.recyclerView_card_shop);
        productsRecyclerView.setHasFixedSize(true);
        linearLayoutManager =new LinearLayoutManager(AppConfig.context, LinearLayoutManager.VERTICAL, false);
        productsRecyclerView.setLayoutManager(linearLayoutManager);
//        adapter = new MainShopItemAdapter(AppConfig.context, items);
//        adapter.setSize(AppConfig.width, AppConfig.height);
//        shopMainRecyclerView.setAdapter(adapter);
//        shopMainRecyclerView.setItemAnimator(new DefaultItemAnimator());


        setHasOptionsMenu(true);
        setToolbar(pageToolbar, "آلاء مجری توسعه عدالت آموزشی");

    }



    @Override
    public void onRefresh() {
        items.clear();
        getData();
    }
}

