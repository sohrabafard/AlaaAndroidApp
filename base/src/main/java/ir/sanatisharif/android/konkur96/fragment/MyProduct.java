package ir.sanatisharif.android.konkur96.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.TextView;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.account.AccountInfo;
import ir.sanatisharif.android.konkur96.activity.SettingActivity;
import ir.sanatisharif.android.konkur96.adapter.MainBoughtItemAdapter;
import ir.sanatisharif.android.konkur96.adapter.MoreProductAdapter;
import ir.sanatisharif.android.konkur96.adapter.MyProductAdapter;
import ir.sanatisharif.android.konkur96.api.Models.AddToCardListModel;
import ir.sanatisharif.android.konkur96.api.Models.ProductModel;
import ir.sanatisharif.android.konkur96.api.Models.ResultModel;
import ir.sanatisharif.android.konkur96.api.Models.WalletModel;
import ir.sanatisharif.android.konkur96.api.Models.myProductsModel;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.handler.Repository;
import ir.sanatisharif.android.konkur96.handler.RepositoryImpl;
import ir.sanatisharif.android.konkur96.handler.Result;
import ir.sanatisharif.android.konkur96.model.user.User;

import static ir.sanatisharif.android.konkur96.app.AppConstants.ACCOUNT_TYPE;
import static ir.sanatisharif.android.konkur96.app.AppConstants.AUTHTOKEN_TYPE_FULL_ACCESS;

public class MyProduct extends Fragment {

    private RecyclerView productMainRecyclerView;
    private TextView txtWallet;
    private GridLayoutManager gridLayoutManager;

    private Repository repository;
    private AccountInfo accountInfo;
    private User user;

    private MyProductAdapter adapter;
    private ArrayList<ProductModel> items = new ArrayList<>();

    private myProductsModel myProductsModel;

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

        repository = new RepositoryImpl(getActivity());
        accountInfo = new AccountInfo(getContext(), getActivity());
        user = accountInfo.getInfo(ACCOUNT_TYPE);

        initView(view);
        getData();

    }

    private void initView(View v) {

        txtWallet = v.findViewById(R.id.txt_wallet);

        //recyclerView
        productMainRecyclerView = v.findViewById(R.id.recyclerView_main_bought);
        productMainRecyclerView.setNestedScrollingEnabled(false);
        productMainRecyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(AppConfig.context, 3);
        productMainRecyclerView.setLayoutManager(gridLayoutManager);
        adapter = new MyProductAdapter(getContext(), items);
        productMainRecyclerView.setAdapter(adapter);
        productMainRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }


    private void getData() {

        //  swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(true));

        if (accountInfo.ExistAccount(ACCOUNT_TYPE)) {

            accountInfo.getExistingAccountAuthToken(ACCOUNT_TYPE, AUTHTOKEN_TYPE_FULL_ACCESS, token ->

                    getActivity().runOnUiThread(() ->

                            repository.getDashboard(token, String.valueOf(user.getId()), data -> {

                                if (data instanceof Result.Success) {

                                    setData((myProductsModel) ((Result.Success) data).value);
                                   // swipeRefreshLayout.setRefreshing(false);
                                } else {

                                    Log.d("Test", (String) ((Result.Error) data).value);
                                   // swipeRefreshLayout.setRefreshing(false);
                                }


                            })));

        }

//        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjNhNDMzNmNjMzU1NGY0MzFmM2JiYTA2NTMwYjk4YmNmZTQ1MzZhMjY0OWVjZDQ1OWM3MzIxYTQyNDdhZjkwNzEyYmUyYjZlOThlZTQ2OWU1In0.eyJhdWQiOiIxIiwianRpIjoiM2E0MzM2Y2MzNTU0ZjQzMWYzYmJhMDY1MzBiOThiY2ZlNDUzNmEyNjQ5ZWNkNDU5YzczMjFhNDI0N2FmOTA3MTJiZTJiNmU5OGVlNDY5ZTUiLCJpYXQiOjE1NTI0NjY1NDgsIm5iZiI6MTU1MjQ2NjU0OCwiZXhwIjoxNTg0MDg4OTQ4LCJzdWIiOiIyMzcwMjAiLCJzY29wZXMiOltdfQ.gD3QsDN6C0MM66GuEJcdpQzWvrurdD6SyVr-L3HzOc5b76pTSg5n3hMw5Jdq4Jca-9muqkCf9EwAHMN1Qmnt4WXAHC6SmnJ_M4FOmXN5fD67Ink_5Z7CkthIJVlPcE3TJXlkDq5PbzC58bNppr19wMGea9EAkYy33q3Vb4-rV-GjMdIi_2vYkp8fTIpa2HacbCCueqlc_OHZRpewik69ANYn8YMSCy0XzXLzM6bnwxLsJVL8UCblu0Mm4SnlcMIK4O_80dWK7RCWlYFSP0gDnK_IYzbSosUYXVtSjAj3llW-1TT8QoueOZYVv-NexwtPCj2SO65-CenYmmSB40ATt1jrF043MhJmdr1fs_u5zMplWMD58cZeZRRg0QaioSuSyvdFtAqBZOlqGc_Gk_vdwpu-YqNbYgUSlQLndNNihVrGbGgEiSoYx1SIkGTW1PkGM-_l6eUFoIizOYUVyKXTSPNjzxDuFsSSXnf4LIXJY_4DO_-GtkN19W1tPRLcpXFY7HDQ8JwgkxnBDygQb814psmBNZM4aqCWfHP94dY9qZyv7iK0uGtyvl8ek93ntmMMjiQVTy7c1N4k73jgeU0RgipvUvslb7RBivCp_GmFACyjeoTrB7_DM_qZp09dVX-BSUCqiC7_uPf_n2ojL24hUs5XfswUwc8cQNaGOYnkilw";
//
//        repository.getDashboard(token, String.valueOf(user.getId()), data -> {
//
//            if (data instanceof Result.Success) {
//
//                setData((myProductsModel) ((Result.Success) data).value);
//                swipeRefreshLayout.setRefreshing(false);
//            } else {
//
//                Log.d("Test", (String) ((Result.Error) data).value);
//                swipeRefreshLayout.setRefreshing(false);
//            }
//
//
//        });

    }

    private void setData(myProductsModel data) {

        Gson gson = new Gson();
        WalletModel walletModel = gson.fromJson(String.valueOf(user.getInfo().getWallet()).replace("[", "").replace("]", ""), WalletModel.class);



        //---------------------- set mainModel data ---------------------------------------------
        myProductsModel = data;


        String tempBlance = String.valueOf((int) Float.parseFloat(walletModel.getBlance()));

        txtWallet.setText(tempBlance + " تومان ");

        items.addAll(data.getData().get(0).getProducts());

        //---------------------- update adapter ------------------------------------------------
        adapter.notifyDataSetChanged();
    }

}


