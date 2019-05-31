package ir.sanatisharif.android.konkur96.fragment;

import android.animation.Animator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.adapter.MyFilterPagerAdapter;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.dialog.FilterCoursesDialogFrg;
import ir.sanatisharif.android.konkur96.dialog.NotInternetDialogFrg;
import ir.sanatisharif.android.konkur96.handler.MainRepository;
import ir.sanatisharif.android.konkur96.listener.ICheckNetwork;
import ir.sanatisharif.android.konkur96.listener.ScrollOnRecycler;
import ir.sanatisharif.android.konkur96.listener.api.IServerCallbackObject;
import ir.sanatisharif.android.konkur96.model.Events;
import ir.sanatisharif.android.konkur96.model.TabControl;
import ir.sanatisharif.android.konkur96.model.filter.FilterModel;

/**
 * Created by Mohamad on 10/13/2018.
 */

public class FilterTagsFrg extends BaseFragment implements
        View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener,
        ICheckNetwork, ScrollOnRecycler {

    private static final int                       NUMBER_TABS = 5;
    private static final int                       SET         = 0;
    private static final int                       PRODUCT     = 1;
    private static final int                       VIDEO       = 2;
    private static final int                       PAMPHLET    = 3;
    private static final int                       ARTICLE     = 4;
    private              TabControl[]              tabControls;
    private              List<FilterShowEntityFrg> filterShowEntityFrgArrayList;
    private              List<String>              titles      = new ArrayList<>();
    private              MyFilterPagerAdapter      myFilterAdapter;
    private              Toolbar                   mToolbar;
    private              TextView                  txtToolbarTitle;
    private              LinearLayout              root;
    private              TabLayout                 tabLayout;
    private              FrameLayout               frameViewPager;
    private              FloatingActionButton      fabFilter;

    private LinearLayout   loaderParent;
    private ProgressBar    loader;
    private ViewPager      viewPager;
    private List<String>   params;
    private MainRepository repository;

    public static FilterTagsFrg newInstance(String url, ArrayList<String> tags) {

        Bundle args = new Bundle();
        args.putString("url", url);
        args.putStringArrayList("tags", tags);
        FilterTagsFrg fragment = new FilterTagsFrg();
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
        return inflater.inflate(R.layout.fragment_tags_filter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        repository = new MainRepository(Objects.requireNonNull(getActivity()));
        setTabContent();
        initView(view);

        params = new ArrayList<>();
        if (getArguments().getString("url") != null)
            getDataByUrl();
        else {
            params = getArguments().getStringArrayList("tags");
            getDataByList();
        }
    }

    private void setTabContent() {
        tabControls = new TabControl[]{
                new TabControl(SET, "set", "دروس", R.drawable.is_play_list, false),
                new TabControl(PRODUCT, "product", "محصولات", R.drawable.ic_buy, false),
                new TabControl(VIDEO, "video", "ویدیو", R.drawable.ic_video_24dp, false),
                new TabControl(PAMPHLET, "pamphlet", "جزوه", R.drawable.ic_pamphlet, false),
                new TabControl(ARTICLE, "article", "مقاله", R.drawable.ic_article_32, false)
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DetailsVideoFrg.pagination = null;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            Events.CloseFragment closeFragment = new Events.CloseFragment();
            closeFragment.setTagFragments("");
            EventBus.getDefault().post(closeFragment);
        }

        return super.onOptionsItemSelected(item);
    }


    private void initView(View view) {

        setHasOptionsMenu(true);
        root = view.findViewById(R.id.root);
        fabFilter = view.findViewById(R.id.fabFilter);
        mToolbar = view.findViewById(R.id.toolbar);
        frameViewPager = view.findViewById(R.id.frameViewPager);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        getActivity().setTitle("");
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        txtToolbarTitle = mToolbar.findViewById(R.id.txtToolbarTitle);
        txtToolbarTitle.setText("");

        loaderParent = view.findViewById(R.id.loaderParent);
        loader = view.findViewById(R.id.loader);
        AppConfig.getInstance().changeProgressColor(loader);
        fabFilter.setOnClickListener(this);

        //view pager
        viewPager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ffffff"));
        tabLayout.setSelectedTabIndicatorHeight((int) (3 *
                                                       getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#ff0000"), Color.parseColor("#00ff00"));
        //---
        tabLayout.setVisibility(View.GONE);
        viewPager.animate().translationY(0);

        //binding
        filterShowEntityFrgArrayList = new ArrayList<>();
        myFilterAdapter =
                new MyFilterPagerAdapter(getContext(), getChildFragmentManager(), filterShowEntityFrgArrayList);
        viewPager.setAdapter(myFilterAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager, FilterModel filter) {

        int tabCount = 0, selected_first_index = -1;

        //reset
        for (TabControl t : tabControls) {
            t.setShow(false);
        }
        if (filter.getResult().getSet() != null) {
            tabControls[SET].setShow(true);
            //  Log.i("LOG", "setupViewPager: " + SET + " " + tabControls[SET].isShow());
        }
        if (filter.getResult().getVideo() != null) {
            tabControls[VIDEO].setShow(true);
            // Log.i("LOG", "setupViewPager:getVideo  " + VIDEO + " " + tabControls[VIDEO].isShow());
        }
        if (filter.getResult().getPamphlet() != null) {
            tabControls[PAMPHLET].setShow(true);
            // Log.i("LOG", "setupViewPager:getPamphlet  " + PAMPHLET + " " + tabControls[PAMPHLET].isShow());
        }
        if (filter.getResult().getArticle() != null) {
            tabControls[ARTICLE].setShow(true);
            // Log.i("LOG", "setupViewPager:getArticle  " + ARTICLE + " " + tabControls[ARTICLE].isShow());
        }
        if (filter.getResult().getProduct() != null) {
            tabControls[PRODUCT].setShow(true);
            // Log.i("LOG", "setupViewPager:getProduct  " + PRODUCT + " " + tabControls[PRODUCT].isShow());
        }

        //new instantiate and adding to viewpager
        filterShowEntityFrgArrayList.clear();
        for (int i = 0; i < NUMBER_TABS; i++) {
            if (tabControls[i].isShow()) {
                FilterShowEntityFrg f = new FilterShowEntityFrg();//new object
                f.setScrollOnRecycler(this);
                filterShowEntityFrgArrayList.add(f);
            }
        }
        myFilterAdapter.notifyDataSetChanged();

        for (int i = 0; i < NUMBER_TABS; i++) {
            if (tabControls[i].isShow()) {
                TabLayout.Tab tab = tabLayout.getTabAt(tabCount++);
                tab.setIcon(tabControls[i].getIcon());
                tab.getIcon().setAlpha(100);
                if (selected_first_index == -1)
                    selected_first_index = i;
            }
        }
        if (selected_first_index > -1) {
            txtToolbarTitle.setText(tabControls[selected_first_index].getTitle());
            tabLayout.getTabAt(0).getIcon().setAlpha(255);
        }


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getIcon() != null) {
                    txtToolbarTitle.setText(tabControls[tab.getPosition()].getTitle());
                    tab.getIcon().setAlpha(255);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getIcon() != null)
                    tab.getIcon().setAlpha(100);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        showTab();

    }

    //<editor-fold desc="getDataBySearch">
    private void getDataBySearch(String query) {

        loaderParent.setVisibility(View.VISIBLE);

        repository.getFilterBySearchCall(query, new IServerCallbackObject() {
            @Override
            public void onSuccess(Object obj) {

                mapToViewpager(obj);
                loaderParent.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(String message) {
                loaderParent.setVisibility(View.GONE);
            }
        });
    }
    //</editor-fold>

    private void getDataByUrl() {

        loaderParent.setVisibility(View.VISIBLE);

        repository.getFilterTagsByUrl(getArguments().getString("url"), new IServerCallbackObject() {
            @Override
            public void onSuccess(Object obj) {

                mapToViewpager(obj);
                loaderParent.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(String message) {
                loaderParent.setVisibility(View.GONE);
            }
        });
    }

    private void getDataByList() {

        loaderParent.setVisibility(View.VISIBLE);

        repository.getFilterTagsByList(params, new IServerCallbackObject() {
            @Override
            public void onSuccess(Object obj) {

                mapToViewpager(obj);
                loaderParent.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(String message) {
                loaderParent.setVisibility(View.GONE);
            }
        });
    }

    private void mapToViewpager(Object obj) {

        FilterModel filter = (FilterModel) obj;

        setupViewPager(viewPager, filter);
        int tabCount = 0;

        if (filter.getResult().getSet() != null) {
            filterShowEntityFrgArrayList.get(tabCount++).setDataModels(filter.getResult().getSet());
        }
        if (filter.getResult().getProduct() != null) {
            filterShowEntityFrgArrayList.get(tabCount++).setDataModels(filter.getResult().getProduct());
        }
        if (filter.getResult().getVideo() != null) {
            filterShowEntityFrgArrayList.get(tabCount++).setDataModels(filter.getResult().getVideo());
        }
        if (filter.getResult().getPamphlet() != null) {
            filterShowEntityFrgArrayList.get(tabCount++).setDataModels(filter.getResult().getPamphlet());
        }
        if (filter.getResult().getArticle() != null) {
            filterShowEntityFrgArrayList.get(tabCount++).setDataModels(filter.getResult().getArticle());
        }
    }

    private void showTab() {

        tabLayout
                .animate()
                .alpha(1)
                //.setDuration(200)
                .setInterpolator(new AccelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        tabLayout.setVisibility(View.VISIBLE);
                        viewPager.animate().translationY(0);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                }).start();
    }

    private void hideTab() {
        tabLayout
                .animate()
                .alpha(0)
                //.setDuration(50)
                .setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        tabLayout.setVisibility(View.GONE);
                        viewPager.animate().translationY(0);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
    }

    public FragmentManager getHostFragmentManager() {
        FragmentManager fm = getFragmentManager();
        if (fm == null && isAdded()) {
            fm = getActivity().getSupportFragmentManager();
        }
        return fm;
    }

    private void showNotInternetDialogFrg() {
        try {
            if (!AppConfig.showNoInternetDialog) {
                NotInternetDialogFrg
                        dialogFrg =
                        new NotInternetDialogFrg().setNoInternetCallback(new NotInternetDialogFrg.NoInternetCallback() {
                            @Override
                            public void onClickOk() {
                                getDataByUrl();
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
    public void onClick(View view) {

        new FilterCoursesDialogFrg().setFilterSelectedCallback(new FilterCoursesDialogFrg.FilterSelectedCallback() {
            @Override
            public void onList(List<String> filters) {

                if (filters != null) {
                    //checked and remove
                    params = filters;
                    getDataByList();
                }
            }
        }).show(getFragmentManager(), "FilterCoursesDialogFrg");
    }

    @Override
    public void scrollUp() {
        if (fabFilter.getVisibility() == View.VISIBLE)
            fabFilter.hide();
    }

    @Override
    public void scrollDown() {
        if (fabFilter.getVisibility() == View.GONE)
            fabFilter.show();
    }

    @Override
    public void onCheckNetwork(boolean flag) {
        if (!flag)//if false
            showNotInternetDialogFrg();
    }

    @Override
    public void onRefresh() {

    }

}


