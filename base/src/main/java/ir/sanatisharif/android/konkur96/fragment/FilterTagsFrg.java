package ir.sanatisharif.android.konkur96.fragment;

import android.animation.Animator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.activity.ActivityBase;
import ir.sanatisharif.android.konkur96.adapter.MyFilterAdapter;
import ir.sanatisharif.android.konkur96.api.MainApi;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.dialog.FilterCoursesDialogFrg;
import ir.sanatisharif.android.konkur96.dialog.NotInternetDialogFrg;
import ir.sanatisharif.android.konkur96.listener.ICheckNetwork;
import ir.sanatisharif.android.konkur96.listener.api.IServerCallbackObject;
import ir.sanatisharif.android.konkur96.model.Events;
import ir.sanatisharif.android.konkur96.model.TabControl;
import ir.sanatisharif.android.konkur96.model.filter.Filter;
import ir.sanatisharif.android.konkur96.ui.view.MDToast;
import ir.sanatisharif.android.konkur96.utils.Utils;

/**
 * Created by Mohamad on 10/13/2018.
 */

public class FilterTagsFrg extends BaseFragment implements
        MaterialSearchView.OnQueryTextListener,
        MaterialSearchView.SearchViewListener,
        View.OnClickListener,
        ICheckNetwork {

    private static final int NUMBER_TABS = 5;
    private static final int SET = 0;
    private static final int PRODUCT = 1;
    private static final int VIDEO = 2;
    private static final int PAMPHLET = 3;
    private static final int ARTICLE = 4;
    private TabControl[] tabControls;
    private List<FilterShowEntityFrg> filterShowEntityFrgArrayList;
    private List<String> titles = new ArrayList<>();
    private MyFilterAdapter myFilterAdapter;
    private Toolbar mToolbar;
    private LinearLayout root;
    private MaterialSearchView searchView;
    private TabLayout tabLayout;
    private FloatingActionButton fabFilter;

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
                new TabControl(SET, "set", "دروس", false),
                new TabControl(PRODUCT, "product", "محصولات", false),
                new TabControl(VIDEO, "video", "ویدیو", false),
                new TabControl(PAMPHLET, "pamphlet", "جزوه", false),
                new TabControl(ARTICLE, "article", "مقاله", false)
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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

        if (item.getItemId() == R.id.action_refresh) {
            getDataByUrl();

        } else if (item.getItemId() == android.R.id.home) {
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
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).setTitle("");
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);


        loaderParent = view.findViewById(R.id.loaderParent);
        loader = view.findViewById(R.id.loader);
        loader.getIndeterminateDrawable().setColorFilter(0xFFFFB700, android.graphics.PorterDuff.Mode.MULTIPLY);
        searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(this);
        searchView.setOnSearchViewListener(this);
        fabFilter.setOnClickListener(this);

        //view pager
        viewPager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#b28000"));
        tabLayout.setSelectedTabIndicatorHeight((int) (3 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));
        //---
        tabLayout.setVisibility(View.GONE);
        viewPager.animate().translationY(0);

        //binding
        filterShowEntityFrgArrayList = new ArrayList<>();
        myFilterAdapter = new MyFilterAdapter(getContext(), getChildFragmentManager(), filterShowEntityFrgArrayList);
        viewPager.setAdapter(myFilterAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager, Filter filter) {

        int tabCount = 0;

        //reset
        for (TabControl t : tabControls)
            t.setShow(false);

        if (filter.getResult().getSet() != null) {
            tabControls[SET].setShow(true);
        }
        if (filter.getResult().getProduct() != null) {
            tabControls[PRODUCT].setShow(true);
        }
        if (filter.getResult().getVideo() != null) {
            tabControls[VIDEO].setShow(true);
        }
        if (filter.getResult().getPamphlet() != null) {
            tabControls[PAMPHLET].setShow(true);
        }
        if (filter.getResult().getArticle() != null) {
            tabControls[ARTICLE].setShow(true);
        }

        //new instantiate and adding to viewpager
        filterShowEntityFrgArrayList.clear();
        for (int i = 0; i < NUMBER_TABS; i++) {
            if (tabControls[i].isShow()) {
                FilterShowEntityFrg f = new FilterShowEntityFrg();//new object
                filterShowEntityFrgArrayList.add(f);
            }
        }
        myFilterAdapter.notifyDataSetChanged();

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(myFilterAdapter.getTabView(i, tabControls[i].getTitle()));
        }

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

    private void showTab() {

        tabLayout
                .animate()
                .scaleY(1)
                .setDuration(200)
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
                .scaleY(0)
                .setDuration(50)
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

    @Override
    public void onCheckNetwork(boolean flag) {
        if (!flag)//if false
            showNotInternetDialogFrg();
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

    private class MyFilterAdapter1 extends FragmentPagerAdapter {

        private final List<FilterShowEntityFrg> mFragmentList;
        private final List<String> mFragmentTitleList;

        public MyFilterAdapter1(FragmentManager fm) {
            super(fm);
            mFragmentList = new ArrayList<>();
            mFragmentTitleList = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentTitleList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        public View getTabView(int position) {

            View v = LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
            TextView tv = (TextView) v.findViewById(R.id.txtTab);
            tv.setText(mFragmentTitleList.get(position));
            ImageView img = (ImageView) v.findViewById(R.id.imgTab);
            // img.setImageResource(imageResId[position]);
            return v;
        }

        public void addFragment(FilterShowEntityFrg fragment, String title, int position) {
            mFragmentList.add(position, fragment);
            mFragmentTitleList.add(position, title);
        }

        public void addFragmentList(List<FilterShowEntityFrg> fragments, List<String> titles) {
            mFragmentList.addAll(fragments);
            mFragmentTitleList.addAll(titles);
        }

        public void removeFragment(int position) {
            mFragmentList.remove(position);
            notifyDataSetChanged();
        }
    }
}


