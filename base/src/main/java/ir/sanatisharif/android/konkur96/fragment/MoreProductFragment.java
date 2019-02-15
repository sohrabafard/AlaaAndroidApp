package ir.sanatisharif.android.konkur96.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import ir.sanatisharif.android.konkur96.activity.SettingActivity;
import ir.sanatisharif.android.konkur96.adapter.MoreProductAdapter;
import ir.sanatisharif.android.konkur96.api.Models.ProductModel;
import ir.sanatisharif.android.konkur96.api.Models.ResultModel;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.handler.Repository;
import ir.sanatisharif.android.konkur96.handler.RepositoryImpl;
import ir.sanatisharif.android.konkur96.handler.Result;
import ir.sanatisharif.android.konkur96.model.Events;
import ir.sanatisharif.android.konkur96.ui.component.paginate.paginate.myPaginate;

public class MoreProductFragment extends BaseFragment{

    Toolbar pageToolbar;

    private RecyclerView recyclerMoreProduct;
    private RecyclerView.Adapter mAdapter;
    private GridLayoutManager gridLayoutManager;


    Repository repository;

    ResultModel resultModel;

    boolean isPaginate = false;

    private String url;

    myPaginate paginate;

    private MoreProductAdapter adapter;

    private ArrayList<ProductModel> items = new ArrayList<>();

    public static MoreProductFragment newInstance(String url) {

        Bundle args = new Bundle();
        args.putString("url", url);
        MoreProductFragment fragment = new MoreProductFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_more_product, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        repository = new RepositoryImpl(getActivity());

        initURL();
        initView(view);
        if (null != url){
            getData();
        }




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

    private void initURL(){

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            url = bundle.getString("url");
        }
    }

    private void initView(View view) {

        //recyclerView
        recyclerMoreProduct = view.findViewById(R.id.recycler_more_product);
        recyclerMoreProduct.setNestedScrollingEnabled(false);
        recyclerMoreProduct.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(AppConfig.context, 3);
        recyclerMoreProduct.setLayoutManager(gridLayoutManager);
        adapter = new MoreProductAdapter(AppConfig.context, items);
        recyclerMoreProduct.setAdapter(adapter);
        recyclerMoreProduct.setItemAnimator(new DefaultItemAnimator());
        paginate = myPaginate.with(recyclerMoreProduct)
                .setOnLoadMoreListener(() -> getDataPaginat())
                .build();

        setHasOptionsMenu(true);
        setToolbar(pageToolbar, "آلاء مجری توسعه عدالت آموزشی");
    }

    private void getData() {

        repository.getMore(url, data -> {

            if (data instanceof Result.Success) {

                setData((ResultModel) ((Result.Success) data).value);

            } else {

                Log.d("Test", (String) ((Result.Error) data).value);
            }


        });


    }

    private void getDataPaginat() {

        if (isPaginate){

            repository.getNextPageProduct(resultModel.getResult().getNext_page_url(), data -> {

                if (data instanceof Result.Success) {

                    setData((ResultModel) ((Result.Success) data).value);

                } else {

                    Log.d("Test", (String) ((Result.Error) data).value);
                }


            });

        }


    }

    private void setData(ResultModel data) {

        //---------------------- set mainModel data ---------------------------------------------
        resultModel = data;


        //---------------------- set paginate data ----------------------------------------------
        if (null != resultModel && null != resultModel.getResult().getNext_page_url()){

            isPaginate = true;
            paginate.setNoMoreItems(false);

        }else {

            isPaginate = false;
            paginate.setNoMoreItems(true);
        }


        //---------------------- convert -------------------------------------------------------
        items.addAll(data.getResult().getData());


        //---------------------- update adapter ------------------------------------------------
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (null !=  paginate){

            paginate.unbind();
        }
    }
}
