package ir.sanatisharif.android.konkur96.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.account.AccountInfo;
import ir.sanatisharif.android.konkur96.account.AuthenticatorActivity;
import ir.sanatisharif.android.konkur96.adapter.MyProductAdapter;
import ir.sanatisharif.android.konkur96.api.Models.ProductModel;
import ir.sanatisharif.android.konkur96.api.Models.WalletModel;
import ir.sanatisharif.android.konkur96.api.Models.myProductsModel;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.handler.Repository;
import ir.sanatisharif.android.konkur96.handler.RepositoryImpl;
import ir.sanatisharif.android.konkur96.handler.Result;
import ir.sanatisharif.android.konkur96.model.user.User;
import ir.sanatisharif.android.konkur96.utils.AuthToken;

import static ir.sanatisharif.android.konkur96.app.AppConstants.ACCOUNT_TYPE;

public class MyProduct extends Fragment {

    private RecyclerView        productMainRecyclerView;
    private TextView            txtWallet;
    private LinearLayoutManager linearLayoutManager;

    private Repository  repository;
    private AccountInfo accountInfo;
    private User        user;

    private MyProductAdapter        adapter;
    private ArrayList<ProductModel> items = new ArrayList<>();

    private myProductsModel myProductsModel;
    private Context         mContext;
    private Activity        mActivity;

    public static MyProduct newInstance() {

        Bundle args = new Bundle();

        MyProduct fragment = new MyProduct();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_myproduct, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        accountInfo = new AccountInfo(getContext(), getActivity());
        user = accountInfo.getInfo(ACCOUNT_TYPE);
        mActivity = getActivity();
        repository = new RepositoryImpl(mActivity);
        mContext = getContext();

        initView(view);
        getData();

    }

    private void initView(View v) {

        txtWallet = v.findViewById(R.id.txt_wallet);

        //recyclerView
        productMainRecyclerView = v.findViewById(R.id.recyclerView_main_bought);
        productMainRecyclerView.setNestedScrollingEnabled(false);
        productMainRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(AppConfig.context);
        productMainRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MyProductAdapter(getContext(), items);
        productMainRecyclerView.setAdapter(adapter);
        productMainRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }


    private void getData() {

        adapter.setItems(new ArrayList<>());

        AuthToken.getInstant().get(mContext, mActivity, new AuthToken.Callback() {
            @Override
            public void run(@NonNull String token) {
                String userId = String.valueOf(user.getId());
                repository.getDashboard(token, userId, data -> {
                    if (data instanceof Result.Success) {
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    ir.sanatisharif.android.konkur96.api.Models.myProductsModel
                                            value =
                                            (myProductsModel) ((Result.Success) data).value;
                                    setData(value);
                                }
                                catch (Exception e) {
                                    Log.e("Alaa\\MyProduct", "User-id:" + userId + "\n\r" +
                                                             "getDashboard - cast to model error");
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else {
                        try {
                            Log.d("Alaa\\MyProduct", (String) ((Result.Error) data).value);
                        }
                        catch (Exception ex) {
                            Log.d("Alaa\\MyProduct", ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void nill() {
                startActivity(new Intent(mActivity, AuthenticatorActivity.class));
            }
        });
    }

    private void setData(@NonNull myProductsModel data) {

        Gson        gson        = new Gson();
        WalletModel
                    walletModel =
                gson.fromJson(String.valueOf(user.getInfo().getWallet()).replace("[", "").replace("]", ""), WalletModel.class);


        //---------------------- set mainModel data ---------------------------------------------
        myProductsModel = data;


        String tempBlance = String.valueOf((int) Float.parseFloat(walletModel.getBlance()));

        String walletText = tempBlance + getString(R.string.toman);
        txtWallet.setText(walletText);

        adapter.setItems(data.getData().get(0).getProducts());
    }

}
