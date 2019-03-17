package ir.sanatisharif.android.konkur96.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
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
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.BuildConfig;
import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.account.AccountInfo;
import ir.sanatisharif.android.konkur96.activity.SettingActivity;
import ir.sanatisharif.android.konkur96.adapter.CardReviewProductAdapter;
import ir.sanatisharif.android.konkur96.adapter.MainShopItemAdapter;
import ir.sanatisharif.android.konkur96.api.Models.AddToCardModel;
import ir.sanatisharif.android.konkur96.api.Models.CardReviewModel;
import ir.sanatisharif.android.konkur96.api.Models.PaymentRequest;
import ir.sanatisharif.android.konkur96.api.Models.PaymentResponse;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.handler.Repository;
import ir.sanatisharif.android.konkur96.handler.RepositoryImpl;
import ir.sanatisharif.android.konkur96.handler.Result;
import ir.sanatisharif.android.konkur96.model.Events;
import ir.sanatisharif.android.konkur96.model.user.User;
import ir.sanatisharif.android.konkur96.utils.ShopUtils;

import static ir.sanatisharif.android.konkur96.app.AppConstants.ACCOUNT_TYPE;
import static ir.sanatisharif.android.konkur96.app.AppConstants.AUTHTOKEN_TYPE_FULL_ACCESS;

public class CardFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private int finalPrice;

    private Toolbar pageToolbar;
    private SwipeRefreshLayout swipeRefreshLayout;

    private Repository repository;
    private AccountInfo accountInfo;
    private User user;

    private CardReviewModel cardReviewModel;

    private ArrayList<AddToCardModel> items = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private CardReviewProductAdapter adapter;

    private RecyclerView productsRecyclerView;
    private CardView btnShowFactor;
    private TextView txtPriceBase, txtPriceDiscount, txtPriceFinal;

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

            accountInfo.getExistingAccountAuthToken(ACCOUNT_TYPE, AUTHTOKEN_TYPE_FULL_ACCESS, token ->
                    getActivity().runOnUiThread(() -> {

                        swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(true));

                        repository.cardReview(token, data -> {

                            if (data instanceof Result.Success) {

                                setData((CardReviewModel) ((Result.Success) data).value);
                                swipeRefreshLayout.setRefreshing(false);

                            } else {

                                Log.d("Test", (String) ((Result.Error) data).value);
                                swipeRefreshLayout.setRefreshing(false);
                            }


                        });

                    }));
        }


    }

    @SuppressLint("SetTextI18n")
    private void setData(CardReviewModel data) {

        //---------------------- set cardReviewModel data ---------------------------------------------
        finalPrice = data.getPrice().getMfinal();

        //---------------------- set txt data ---------------------------------------------
        txtPriceBase.setText("مبلغ کل: " + ShopUtils.formatPrice(data.getPrice().getBase()) + " تومان ");
        txtPriceDiscount.setText("سود شما از خرید: " + ShopUtils.formatPrice(data.getPrice().getDiscount()) + " تومان ");
        txtPriceFinal.setText("مبلغ قابل‌پرداخت: " + ShopUtils.formatPrice(data.getPrice().getMfinal()) + " تومان ");

        //---------------------- set cardReviewModel data ---------------------------------------------
        cardReviewModel = data;

        //---------------------- convert -------------------------------------------------------
        items.addAll(ShopUtils.convertToAddToCardModelList(data));


        //---------------------- update adapter ------------------------------------------------
        adapter.notifyDataSetChanged();

        btnShowFactor.setOnClickListener(view -> {

            openZarinPal();
        });


    }

    private void initView(View v) {

        //text
        txtPriceBase = v.findViewById(R.id.txt_price_base);
        txtPriceDiscount = v.findViewById(R.id.txt_price_discount);
        txtPriceFinal = v.findViewById(R.id.txt_price_final);

        btnShowFactor = v.findViewById(R.id.btn_show_factor);

        //swipeRefreshLayout
        swipeRefreshLayout = v.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(AppConfig.colorSwipeRefreshing);
        swipeRefreshLayout.setOnRefreshListener(this);
        //recyclerView
        productsRecyclerView = v.findViewById(R.id.recyclerView_card_shop);
        productsRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(AppConfig.context, LinearLayoutManager.VERTICAL, false);
        productsRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new CardReviewProductAdapter(getContext(), items, () -> {

        });
        productsRecyclerView.setAdapter(adapter);
        productsRecyclerView.setItemAnimator(new DefaultItemAnimator());


        setHasOptionsMenu(true);
        setToolbar(pageToolbar, "آلاء مجری توسعه عدالت آموزشی");

    }


    @Override
    public void onRefresh() {
        items.clear();
        getData();
    }



    private void openZarinPal(){

        if (accountInfo.ExistAccount(ACCOUNT_TYPE)) {

            String uMobile = "نامشخص";

            if (user.getMobile() != null){

                uMobile = user.getMobile();
            }

            String desc = "اپ آلاء" + " - " + "نسخه" + String.valueOf(BuildConfig.VERSION_CODE) +

                    " - " + uMobile + " - " + "محصولات: " + ShopUtils.getProductNames(cardReviewModel);

            PaymentRequest body = new PaymentRequest("55eb1362-08d4-42ee-8c74-4c5f5bef37d4",
                    finalPrice,
                    "alla://sanatisharif.ir/zarinpal/?a=" + String.valueOf(finalPrice),
                    desc);

            repository.paymentRequest(body, data -> {

                if (data instanceof Result.Success) {

                    PaymentResponse payment = (PaymentResponse) ((Result.Success) data).value;

                    if (payment.getStatus() == 100){

                        String url = "https://www.zarinpal.com/pg/StartPay/" + payment.getAuthority() + "/MobileGate";

                        openWebView(url);

                    }else {

                        Toast.makeText(getContext(), "خطا : " + String.valueOf(payment.getStatus()) , Toast.LENGTH_LONG).show();
                    }

                } else {

                    Toast.makeText(getContext(), "خطایی رخ داده لطفا دوباره امتحان کنید." , Toast.LENGTH_LONG).show();
                }


            });
        }

    }
    private void openWebView(String url){

        try {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            @SuppressLint("WrongConstant") ResolveInfo resolvable = getActivity().getPackageManager().resolveActivity(intent, PackageManager.GET_INTENT_FILTERS);
            if (resolvable != null) {
                startActivity(intent);
            }
        } catch (NullPointerException e) {
            Log.e("Alla", e.getMessage(), e);
        }
    }
}
