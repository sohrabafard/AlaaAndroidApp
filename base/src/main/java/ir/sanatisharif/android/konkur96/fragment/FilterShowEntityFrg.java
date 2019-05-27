package ir.sanatisharif.android.konkur96.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.adapter.FilterAdapter;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.dialog.NotInternetDialogFrg;
import ir.sanatisharif.android.konkur96.handler.MainRepository;
import ir.sanatisharif.android.konkur96.listener.ICheckNetwork;
import ir.sanatisharif.android.konkur96.listener.ScrollOnRecycler;
import ir.sanatisharif.android.konkur96.listener.api.IServerCallbackObject;
import ir.sanatisharif.android.konkur96.model.filter.ArticleRoot;
import ir.sanatisharif.android.konkur96.model.filter.Filter;
import ir.sanatisharif.android.konkur96.model.filter.FilterBaseModel;
import ir.sanatisharif.android.konkur96.model.filter.Pagination;
import ir.sanatisharif.android.konkur96.model.filter.PamphletRoot;
import ir.sanatisharif.android.konkur96.model.filter.SetFilterProductRoot;
import ir.sanatisharif.android.konkur96.model.filter.SetFilterRoot;
import ir.sanatisharif.android.konkur96.model.filter.VideoRoot;

/**
 * Created by Mohamad on 10/13/2018.
 */

public class FilterShowEntityFrg extends BaseFragment implements ICheckNetwork {

    private RecyclerView myRecyclerView;
    private NestedScrollView nestedScrollView;
    private FilterAdapter adapter;
    private List<FilterBaseModel> mList = new ArrayList<>();
    private Pagination pagination;
    private int type = -1;
    private boolean repeatLoad = true;
    private ScrollOnRecycler scrollOnRecycler;
    private MainRepository repository;

    public static FilterShowEntityFrg newInstance() {

        Bundle args = new Bundle();
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

        LinearLayoutManager manager = new LinearLayoutManager(AppConfig.context, LinearLayoutManager.VERTICAL, false);
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

                View view = nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1);
                int i = nestedScrollView.getHeight() + nestedScrollView.getScrollY();
                int diff = (view.getBottom() - i);

                if (diff == 0 ) {

                    if (pagination != null) {
                        String nextPageUrl = pagination.getNextPageUrl();

                        if (nextPageUrl != null) {
                            Log.i("Alaa\\FilterShowFrg",nextPageUrl);
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
    public void setVideoCourses(VideoRoot videoRoot) {

        pagination = videoRoot;
        DetailsVideoFrg.pagination = pagination;
        type = AppConstants.FILTER_VIDEO;
        mList.clear();
        mList.addAll(videoRoot.getData());
    }

    public void setPamphletCourses(PamphletRoot pamphletRoot) {

        pagination = pamphletRoot;
        type = AppConstants.FILTER_PAMPHLET;
        mList.clear();
        mList.addAll(pamphletRoot.getData());
    }

    public void setArticleCourses(ArticleRoot articleRoot) {

        pagination = articleRoot;
        type = AppConstants.FILTER_ARTICLE;
        mList.clear();
        mList.addAll(articleRoot.getData());
    }

    public void setToSetFilterCourses(SetFilterRoot setFilterRoot) {

        pagination = setFilterRoot;
        type = AppConstants.FILTER_SET;
        mList.clear();
        mList.addAll(setFilterRoot.getData());
    }

    public void setToProduct(SetFilterProductRoot product) {

        pagination = product;
        type = AppConstants.FILTER_PRODUCT;
        mList.clear();
        mList.addAll(product.getData());

    }
    //</editor-fold>

    //<editor-fold desc="get Data from server">
    void getData(String nextUrl) {
        //TODO: issue
        Toast.makeText(AppConfig.context,"دریافت از سرور ...",Toast.LENGTH_SHORT).show();
        repeatLoad = true;
        repository.getFilterTagsByUrl(nextUrl, new IServerCallbackObject() {
            @Override
            public void onSuccess(Object obj) {

                Toast.makeText(AppConfig.context,"دریافت شد.",Toast.LENGTH_SHORT).show();
                Filter filter = (Filter) obj;
                int size = mList.size();
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
                adapter.notifyItemRangeInserted(size,mList.size() - size);
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

    void show() {

        if (!AppConfig.showNoInternetDialog)
            new NotInternetDialogFrg().setNoInternetCallback(new NotInternetDialogFrg.NoInternetCallback() {
                @Override
                public void onClickOk() {
                    if (pagination.getNextPageUrl() != null) {
                        getData(pagination.getNextPageUrl());
                    }
                }
            }).show(getFragmentManager(), "");
    }

    @Override
    public void onCheckNetwork(boolean flag) {
        if (flag) {
        } else {
            show();
        }
    }
}

