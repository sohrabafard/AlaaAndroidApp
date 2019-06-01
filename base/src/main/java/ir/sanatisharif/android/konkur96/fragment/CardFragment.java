package ir.sanatisharif.android.konkur96.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Objects;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.activity.SettingActivity;
import ir.sanatisharif.android.konkur96.adapter.CardReviewProductAdapter;
import ir.sanatisharif.android.konkur96.api.Models.CardReviewModel;
import ir.sanatisharif.android.konkur96.api.Models.PaymentUrlModel;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.handler.Repository;
import ir.sanatisharif.android.konkur96.handler.RepositoryImpl;
import ir.sanatisharif.android.konkur96.handler.Result;
import ir.sanatisharif.android.konkur96.model.Events;
import ir.sanatisharif.android.konkur96.utils.AuthToken;
import ir.sanatisharif.android.konkur96.utils.ShopUtils;
import ir.sanatisharif.android.konkur96.utils.Utils;

import static ir.sanatisharif.android.konkur96.app.AppConfig.currentActivity;
import static ir.sanatisharif.android.konkur96.app.AppConfig.showNoInternetDialog;

public class CardFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, CardReviewProductAdapter.DeleteListener {
    
    private int finalPrice;
    
    private Toolbar            pageToolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    
    private Repository               repository;
    private LinearLayoutManager      linearLayoutManager;
    private CardReviewProductAdapter adapter;
    
    private RecyclerView productsRecyclerView;
    private CardView     btnShowFactor;
    private TextView     txtPriceBase, txtPriceDiscount, txtPriceFinal;
    
    private Context  mContext;
    private Activity mActivity;
    
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
        mActivity = currentActivity;
        repository = new RepositoryImpl(mActivity);
        mContext = getContext();
        
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
        
        adapter.setItems(new ArrayList<>());
        
        AuthToken.getInstant().get(mContext, mActivity, new AuthToken.Callback() {
            @Override
            public void run(@NonNull String token) {
                repository.cardReview(token, data -> {
                    
                    if (data instanceof Result.Success) {
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setData((CardReviewModel) ((Result.Success) data).value);
                                swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(false));
                            }
                        });
                        
                    } else {
                        Log.d("Test", (String) ((Result.Error) data).value);
                    }
                    
                });
            }
            
            @Override
            public void nill() {
            
            }
        });
    }
    
    @SuppressLint("SetTextI18n")
    private void setData(CardReviewModel data) {
        
        //---------------------- set cardReviewModel data ---------------------------------------------
        finalPrice = data.getPrice().getMfinal();
        
        //---------------------- set txt data ---------------------------------------------
        txtPriceBase.setText(this.getString(R.string.totalPrice) +
                             ShopUtils.formatPrice(data.getPrice().getBase()) +
                             this.getString(R.string.toman));
        txtPriceDiscount.setText(this.getString(R.string.discount_price) +
                                 ShopUtils.formatPrice(data.getPrice().getDiscount()) +
                                 this.getString(R.string.toman));
        txtPriceFinal.setText(this.getString(R.string.payable_price) +
                              ShopUtils.formatPrice(data.getPrice().getMfinal()) +
                              this.getString(R.string.toman));
        
        //---------------------- convert -------------------------------------------------------
        
        adapter.setItems(data.getItems());
        
        btnShowFactor.setOnClickListener(view -> {
            
            /*openZarinPal();*/
            
            getUrl();
            
        });
        
        
    }
    
    private void getUrl() {
        if (!Utils.isConnected()) {
            showDialog();
            return;
        }
        AuthToken.getInstant().get(mContext, mActivity, new AuthToken.Callback() {
            @Override
            public void run(@NonNull String token) {
                repository.getPaymentUrl(token, data -> {
                    
                    if (data instanceof Result.Success) {
                        
                        String url = ((PaymentUrlModel) ((Result.Success) data).value).getUrl();
                        if (null != url) {
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    openWebView(url);
                                }
                            });
                        }
                    } else {
                        
                        Log.d("Test", (String) ((Result.Error) data).value);
                    }
                    
                    
                });
            }
            
            @Override
            public void nill() {
            
            }
        });
    }
    
    public void showDialog() {
        final Dialog dialog = new Dialog(new ContextThemeWrapper(currentActivity,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_no_internet);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        
        showNoInternetDialog = true;
        Button    btnOK    = dialog.findViewById(R.id.btnOK);
        ImageView imgCLose = dialog.findViewById(R.id.imgCLose);
        
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                showNoInternetDialog = false;
            }
        });
        imgCLose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNoInternetDialog = false;
                dialog.dismiss();
            }
        });
        
        dialog.show();
        
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
        productsRecyclerView.setHasFixedSize(false);
        linearLayoutManager =
                new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        productsRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new CardReviewProductAdapter(getContext(), this::delete);
        productsRecyclerView.setAdapter(adapter);
        productsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        
        
        setHasOptionsMenu(true);
        setToolbar(pageToolbar, "سبد خرید");
        
    }
    
    
    @Override
    public void onRefresh() {
        getData();
    }
    
    private void openWebView(String url) {
        
        try {
            Uri uri = Uri.parse(url);
            Intent
                    intent =
                    new Intent(Intent.ACTION_VIEW, uri);
            @SuppressLint("WrongConstant") ResolveInfo
                    resolvable =
                    getActivity().getPackageManager().resolveActivity(intent, PackageManager.GET_INTENT_FILTERS);
            if (resolvable != null) {
                startActivity(intent);
            }
        }
        catch (NullPointerException e) {
            Log.e("Alla", e.getMessage(), e);
        }
    }
    
    public void delete(int id) {
        AuthToken.getInstant().get(mContext, mActivity, new AuthToken.Callback() {
            @Override
            public void run(@NonNull String token) {
                repository.delProductFromCard(token, String.valueOf(id), data -> {
                    
                    if (data instanceof Result.Success) {
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "با موفقیت حدف شد.", Toast.LENGTH_SHORT).show();
                                getData();
                            }
                        });
                    } else {
                        Log.d("Test", (String) ((Result.Error) data).value);
                    }
                });
            }
            
            @Override
            public void nill() {
            
            }
        });
    }
    
    @Override
    public void onClickDelete(int id) {
        
        delete(id);
        
    }
}

