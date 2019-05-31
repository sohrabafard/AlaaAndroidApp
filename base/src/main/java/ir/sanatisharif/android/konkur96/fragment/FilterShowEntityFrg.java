package ir.sanatisharif.android.konkur96.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.adapter.FilterAdapter;
import ir.sanatisharif.android.konkur96.api.Models.ContentModel;
import ir.sanatisharif.android.konkur96.api.Models.PaginationDataModel;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.dialog.NotInternetDialogFrg;
import ir.sanatisharif.android.konkur96.handler.MainRepository;
import ir.sanatisharif.android.konkur96.listener.ICheckNetwork;
import ir.sanatisharif.android.konkur96.listener.ScrollOnRecycler;
import ir.sanatisharif.android.konkur96.listener.api.IServerCallbackObject;
import ir.sanatisharif.android.konkur96.model.PaginationModel;
import ir.sanatisharif.android.konkur96.model.filter.FilterBaseModel;
import ir.sanatisharif.android.konkur96.model.filter.FilterModel;


/**
 * Created by Mohamad on 10/13/2018.
 */

public class FilterShowEntityFrg extends BaseFragment implements ICheckNetwork {

    private RecyclerView                                   myRecyclerView;
    private NestedScrollView                               nestedScrollView;
    private FilterAdapter                                  adapter;
    private List<FilterBaseModel>                          mList      = new ArrayList<>();
    private PaginationModel<? extends PaginationDataModel> pagination;
    private int                                            type       = -1;
    private boolean                                        repeatLoad = true;
    private ScrollOnRecycler                               scrollOnRecycler;
    private MainRepository                                 repository;

    public static FilterShowEntityFrg newInstance() {

        Bundle              args     = new Bundle();
        FilterShowEntityFrg fragment = new FilterShowEntityFrg();
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
        if (repository == null)
            repository = new MainRepository(Objects.requireNonNull(getActivity()));
        return inflater.inflate(R.layout.fragment_filter_video, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (repository == null)
            repository = new MainRepository(Objects.requireNonNull(getActivity()));
        initView(view);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initView(View v) {

        LinearLayoutManager
                manager =
                new LinearLayoutManager(AppConfig.context, LinearLayoutManager.VERTICAL, false);
        myRecyclerView = v.findViewById(R.id.recyclerView);
        nestedScrollView = v.findViewById(R.id.nestedScrollView);

        myRecyclerView.setNestedScrollingEnabled(false);
        myRecyclerView.setHasFixedSize(false);
        myRecyclerView.setLayoutManager(manager);
        adapter = new FilterAdapter(getContext(), mList);

        myRecyclerView.setAdapter(adapter);
        myRecyclerView.setItemViewCacheSize(30);
        myRecyclerView.setDrawingCacheEnabled(true);
        myRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        adapter.notifyDataSetChanged();

        nestedScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {

                int index = nestedScrollView.getChildCount() - 1;
                if (index < 0)
                    index = 0;
                View view = nestedScrollView.getChildAt(index);
                int  i    = nestedScrollView.getHeight() + nestedScrollView.getScrollY();
                int  diff = (view.getBottom() - i);

                if (diff == 0) {

                    if (pagination != null) {
                        String nextPageUrl = pagination.getNextPageUrl();

                        if (nextPageUrl != null) {
                            Log.i("Alaa\\FilterShowFrg", nextPageUrl);
                            getData(nextPageUrl);
                        }
                    }
                }
            }
        });
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    scrollOnRecycler.scrollUp();
                } else {
                    scrollOnRecycler.scrollDown();
                }
            }
        });

    }

    //<editor-fold desc="set data">
    public <T extends PaginationDataModel & FilterBaseModel> void setDataModels(PaginationModel<T> pagination) {

        this.pagination = pagination;

        try {
            DetailsVideoFrg.pagination = (PaginationModel<ContentModel>) pagination;
        }
        catch (ClassCastException ex) {
            DetailsVideoFrg.pagination = null;
        }

        type = AppConstants.FILTER_VIDEO;
        mList.clear();
        mList.addAll(pagination.getData());
    }
    //</editor-fold>

    //<editor-fold desc="get Data from server">
    void getData(String nextUrl) {
        //TODO: issue
        repeatLoad = true;
        repository.getFilterTagsByUrl(nextUrl, new IServerCallbackObject() {
            @Override
            public void onSuccess(Object obj) {
                FilterModel filter = (FilterModel) obj;
                int         size   = mList.size();
                if (type == AppConstants.FILTER_VIDEO) {
                    Log.i("LOG", "onFailure: onSuccess");
                    //reset pagination
                    pagination = filter.getResult().getVideo();
                    mList.addAll(filter.getResult().getVideo().getData());
                } else if (type == AppConstants.FILTER_PAMPHLET) {
                    pagination = filter.getResult().getPamphlet();
                    mList.addAll(filter.getResult().getPamphlet().getData());
                } else if (type == AppConstants.FILTER_ARTICLE) {
                    pagination = filter.getResult().getArticle();
                    mList.addAll(filter.getResult().getArticle().getData());
                } else if (type == AppConstants.FILTER_SET) {
                    pagination = filter.getResult().getSet();
                    mList.addAll(filter.getResult().getSet().getData());
                } else if (type == AppConstants.FILTER_PRODUCT) {
                    pagination = filter.getResult().getProduct();
                    mList.addAll(filter.getResult().getProduct().getData());
                }
//                adapter.notifyItemMoved(size, mList.size() - 1);
                adapter.notifyItemRangeInserted(size, mList.size() - size);
            }

            @Override
            public void onFailure(String message) {
                // failLoadDialog();
                Log.i("LOG", "onFailure: faailllll " + message);
            }
        });
    }
    //</editor-fold>


    public void setScrollOnRecycler(ScrollOnRecycler scrollOnRecycler) {
        this.scrollOnRecycler = scrollOnRecycler;
    }

    public FragmentManager getHostFragmentManager() {
        FragmentManager fm = getFragmentManager();
        if (fm == null && isAdded()) {
            fm = ((AppCompatActivity) getActivity()).getSupportFragmentManager();
        }
        return fm;
    }

    void show() {

        try {
            if (!AppConfig.showNoInternetDialog) {
                final String nextUrl = pagination.getNextPageUrl();
                NotInternetDialogFrg
                        dialogFrg =
                        new NotInternetDialogFrg().setNoInternetCallback(new NotInternetDialogFrg.NoInternetCallback() {
                            @Override
                            public void onClickOk() {
                                if (nextUrl != null) {
                                    getData(nextUrl);
                                }
                            }
                        });
                dialogFrg.show(getHostFragmentManager(), "");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void onCheckNetwork(boolean flag) {
        if (flag) {
        } else {
            show();
        }
    }
}

