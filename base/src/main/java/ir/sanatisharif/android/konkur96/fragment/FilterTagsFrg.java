package ir.sanatisharif.android.konkur96.fragment;

import android.animation.Animator;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.adapter.MyFilterPagerAdapter;
import ir.sanatisharif.android.konkur96.api.MainApi;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.dialog.FilterCoursesDialogFrg;
import ir.sanatisharif.android.konkur96.dialog.NotInternetDialogFrg;
import ir.sanatisharif.android.konkur96.listener.ICheckNetwork;
import ir.sanatisharif.android.konkur96.listener.ScrollOnRecycler;
import ir.sanatisharif.android.konkur96.listener.api.IServerCallbackObject;
import ir.sanatisharif.android.konkur96.model.Events;
import ir.sanatisharif.android.konkur96.model.TabControl;
import ir.sanatisharif.android.konkur96.model.filter.Filter;
import ir.sanatisharif.android.konkur96.model.filter.Pamphlet;

/**
 * Created by Mohamad on 10/13/2018.
 */

public class FilterTagsFrg extends BaseFragment implements
        MaterialSearchView.OnQueryTextListener,
        MaterialSearchView.SearchViewListener,
        View.OnClickListener,
        ICheckNetwork, ScrollOnRecycler {

    private static final int NUMBER_TABS = 5;
    private static final int SET = 0;
    private static final int PRODUCT = 1;
    private static final int VIDEO = 2;
    private static final int PAMPHLET = 3;
    private static final int ARTICLE = 4;
    private TabControl[] tabControls;
    private List<FilterShowEntityFrg> filterShowEntityFrgArrayList;
    private List<String> titles = new ArrayList<>();
    private MyFilterPagerAdapter myFilterAdapter;
    private Toolbar mToolbar;
    private TextView txtToolbarTitle;
    private LinearLayout root;
    private TabLayout tabLayout;
    private FrameLayout frameViewPager;
    private FloatingActionButton fabFilter;
    private MaterialSearchView searchView;

    private LinearLayout loaderParent;
    private ProgressBar loader;
    private ViewPager viewPager;
    private List<String> params;

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
                new TabControl(SET, "set", "دروس", R.drawable.ic_set, false),
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_filter_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        super.onCreateOptionsMenu(menu, inflater);
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
        ((AppCompatActivity) getActivity()).setTitle("");
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        txtToolbarTitle = mToolbar.findViewById(R.id.txtToolbarTitle);
        txtToolbarTitle.setText("");

        loaderParent = view.findViewById(R.id.loaderParent);
        loader = view.findViewById(R.id.loader);
        AppConfig.getInstance().changeProgressColor(loader);
        searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(this);
        searchView.setOnSearchViewListener(this);
        fabFilter.setOnClickListener(this);

        //view pager
        viewPager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ffffff"));
        tabLayout.setSelectedTabIndicatorHeight((int) (3 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#ff0000"), Color.parseColor("#00ff00"));
        //---
        tabLayout.setVisibility(View.GONE);
        viewPager.animate().translationY(0);

        //binding
        filterShowEntityFrgArrayList = new ArrayList<>();
        myFilterAdapter = new MyFilterPagerAdapter(getContext(), getChildFragmentManager(), filterShowEntityFrgArrayList);
        viewPager.setAdapter(myFilterAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager, Filter filter) {

        int tabCount = 0, selected_first_index = -1;

        //reset
        for (TabControl t : tabControls) {
            t.setShow(false);
            //Log.i("LOG", "setupViewPager:22 " + t.getId() +" "+t.getTitle()+ " " + t.isShow());
        }
        if (filter.getResult().getSet() != null) {
            tabControls[SET].setShow(true);
            Log.i("LOG", "setupViewPager: " + SET + " " + tabControls[SET].isShow());
        }
        if (filter.getResult().getVideo() != null) {
            tabControls[VIDEO].setShow(true);
            Log.i("LOG", "setupViewPager:getVideo  " + VIDEO + " " + tabControls[VIDEO].isShow());
        }
        if (filter.getResult().getPamphlet() != null) {
            tabControls[PAMPHLET].setShow(true);
            Log.i("LOG", "setupViewPager:getPamphlet  " + PAMPHLET + " " + tabControls[PAMPHLET].isShow());
        }
        if (filter.getResult().getArticle() != null) {
            tabControls[ARTICLE].setShow(true);
            Log.i("LOG", "setupViewPager:getArticle  " + ARTICLE + " " + tabControls[ARTICLE].isShow());
        }
        if (filter.getResult().getProduct() != null) {
            tabControls[PRODUCT].setShow(true);
            Log.i("LOG", "setupViewPager:getProduct  " + PRODUCT + " " + tabControls[PRODUCT].isShow());
        }

        //new instantiate and adding to viewpager
        filterShowEntityFrgArrayList.clear();
        for (int i = 0; i < NUMBER_TABS; i++) {
            if (tabControls[i].isShow()) {
                FilterShowEntityFrg f = new FilterShowEntityFrg();//new object
                f.setScrollOnRecycler(this);
                filterShowEntityFrgArrayList.add(f);
              //  Log.i("LOG", "setupViewPager: " + i + " " + tabControls[i].getTitle());
            }
        }
        myFilterAdapter.notifyDataSetChanged();

//        for (int i = 0; i < NUMBER_TABS; i++) {
//            if (tabControls[i].isShow()) {
//                Log.i("LOG", "setupViewPager: " +i+" "+tabControls[i].getTitle());
//            }
//        }

        for (int i = 0; i < NUMBER_TABS; i++) {
            if (tabControls[i].isShow()) {
                TabLayout.Tab tab = tabLayout.getTabAt(tabCount++);
                tab.setIcon(tabControls[i].getIcon());
                tab.getIcon().setAlpha(100);
                if (selected_first_index == -1)
                    selected_first_index = i;
                //Log.i("LOG", "setupViewPager:t " + i + " " + tabControls[i].getTitle());
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

    private void getDataBySearch(String query) {

        loaderParent.setVisibility(View.VISIBLE);

        MainApi.getInstance().getFilterBySearchCall(query, new IServerCallbackObject() {
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

    private void getDataByUrl() {

        loaderParent.setVisibility(View.VISIBLE);

        MainApi.getInstance().getFilterTagsByUrl(getArguments().getString("url"), new IServerCallbackObject() {
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

        MainApi.getInstance().getFilterTagsByList(params, new IServerCallbackObject() {
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

        Filter filter = (Filter) obj;

        setupViewPager(viewPager, filter);
        int tabCount = 0;

        if (filter.getResult().getSet() != null) {
            filterShowEntityFrgArrayList.get(tabCount++).setToSetFilterCourses(filter.getResult().getSet());
        }
        if (filter.getResult().getProduct() != null) {
            tabCount++;
        }
        if (filter.getResult().getVideo() != null) {
            filterShowEntityFrgArrayList.get(tabCount++).setVideoCourses(filter.getResult().getVideo());
        }
        if (filter.getResult().getPamphlet() != null) {
            filterShowEntityFrgArrayList.get(tabCount++).setPamphletCourses(filter.getResult().getPamphlet());
        }
        if (filter.getResult().getArticle() != null) {
            filterShowEntityFrgArrayList.get(tabCount++).setArticleCourses(filter.getResult().getArticle());
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

    private void showNotInternetDialogFrg() {

        if (!AppConfig.showNoInternetDialog)
            new NotInternetDialogFrg().setNoInternetCallback(new NotInternetDialogFrg.NoInternetCallback() {
                @Override
                public void onClickOk() {
                    getDataByUrl();
                }
            }).show(getFragmentManager(), "");
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
    public boolean onQueryTextSubmit(String query) {
        if (!TextUtils.isEmpty(query))
            getDataBySearch(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onSearchViewShown() {
        hideTab();
    }

    @Override
    public void onSearchViewClosed() {
        showTab();
    }
}

