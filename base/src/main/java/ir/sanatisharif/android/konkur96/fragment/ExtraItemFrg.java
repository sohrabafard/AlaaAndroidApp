package ir.sanatisharif.android.konkur96.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.activity.ActivityBase;
import ir.sanatisharif.android.konkur96.adapter.FilterAdapter;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.handler.MainRepository;
import ir.sanatisharif.android.konkur96.listener.OnItemClickListener;
import ir.sanatisharif.android.konkur96.listener.api.IServerCallbackObject;
import ir.sanatisharif.android.konkur96.model.Events;
import ir.sanatisharif.android.konkur96.api.Models.ContentModel;
import ir.sanatisharif.android.konkur96.model.filter.FilterBaseModel;
import ir.sanatisharif.android.konkur96.model.filter.FilterModel;
import ir.sanatisharif.android.konkur96.ui.view.MDToast;
import ir.sanatisharif.android.konkur96.utils.Utils;

/**
 * Created by Mohamad on 10/13/2018.
 */

public class ExtraItemFrg extends BaseFragment {

    private Toolbar               mToolbar;
    private RecyclerView          myRecyclerView;
    private SwipeRefreshLayout    swipeRefreshLayout;
    private FilterAdapter         adapter;
    private List<FilterBaseModel> mList = new ArrayList<>();
    private List<String>          params;

    private MainRepository repository;

    public static ExtraItemFrg newInstance(String url) {

        Bundle args = new Bundle();
        args.putString("url", url);
        ExtraItemFrg fragment = new ExtraItemFrg();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_extra_video, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        repository = new MainRepository(Objects.requireNonNull(getActivity()));
        initView(view);

        params = Utils.getParamsFromUrl(getArguments().getString("url"));
        if (params != null)
            getData();
        else
            ActivityBase.toastShow(getResources().getString(R.string.errNotExistTags), MDToast.TYPE_ERROR);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:

                Events.CloseFragment closeFragment = new Events.CloseFragment();
                closeFragment.setTagFragments("");
                EventBus.getDefault().post(closeFragment);

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initView(View v) {

        setHasOptionsMenu(true);
        mToolbar = v.findViewById(R.id.toolbar);
        setToolbar(mToolbar, "");

        swipeRefreshLayout = v.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(AppConfig.colorSwipeRefreshing);
        myRecyclerView = v.findViewById(R.id.recyclerView);
        myRecyclerView.setNestedScrollingEnabled(false);
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(AppConfig.context, LinearLayoutManager.VERTICAL, false));
        adapter = new FilterAdapter(AppConfig.context, mList);
        myRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object item, View view, RecyclerView.ViewHolder vh) {

            }
        });
    }

    private void getData() {

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });

        repository.getContentOnlyCall(params.get(0), new IServerCallbackObject() {
            @Override
            public void onSuccess(Object obj) {

                FilterModel filter = (FilterModel) obj;
                if (filter.getResult().getVideo() != null) {
                    List<ContentModel> videos = filter.getResult().getVideo().getData();
                    mList.addAll(videos);
                }

                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onFailure(String message) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


}

