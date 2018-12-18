package ir.sanatisharif.android.konkur96.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;

import ir.sanatisharif.android.konkur96.activity.MainActivity;
import ir.sanatisharif.android.konkur96.activity.SettingActivity;
import ir.sanatisharif.android.konkur96.adapter.MainItemAdapter;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.helper.FileManager;
import ir.sanatisharif.android.konkur96.listener.OnItemClickListener1111;
import ir.sanatisharif.android.konkur96.model.BannerItem;
import ir.sanatisharif.android.konkur96.model.CategoryItemSet;
import ir.sanatisharif.android.konkur96.model.Content;
import ir.sanatisharif.android.konkur96.model.Events;
import ir.sanatisharif.android.konkur96.model.MainItem;
import ir.sanatisharif.android.konkur96.model.ViewSlider;


/**
 * Created by Mohamad on 10/13/2018.
 */

public class AllaMainFrg extends BaseFragment {

    RecyclerView myRecyclerView;
    Toolbar mToolbar;

    private MainItemAdapter adapter;
    private ArrayList<MainItem> items = new ArrayList<>();

    public static AllaMainFrg newInstance() {

        Bundle args = new Bundle();

        AllaMainFrg fragment = new AllaMainFrg();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alla, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initView(view);

        setDummyData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id== R.id.actionSetting)
        {

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


    private void setDummyData() {

        //slider

        ArrayList<ViewSlider> v = new ArrayList<>();
        v.add(new ViewSlider("1",
                "https://sanatisharif.ir/image/9/1280/500/asiatech-alaa222%20%281%29_20180812155522.jpg?1",
                "https://sanatisharif.ir/v/asiatech", AppConstants.LINK_TO_EXTERNAL));
        v.add(new ViewSlider("2",
                "https://sanatisharif.ir/image/9/1280/500/IMG_20180920_004154_394_20180919201252.jpg?1",
                "https://sanatisharif.ir/product/225/", AppConstants.LINK_TO_EXTERNAL));
        v.add(new ViewSlider("3",
                "https://sanatisharif.ir/image/9/1280/500/BIG-SLIDE-1%20%282%29_20180429193412.jpg?1",
                "https://sanatisharif.ir/c", AppConstants.LINK_TO_EXTERNAL));

        v.add(new ViewSlider("4",
                "https://sanatisharif.ir/image/9/1280/500/BIG-SLIDE-7_20181015143731.jpg?1",
                "https://sanatisharif.ir/product/226/", AppConstants.LINK_TO_EXTERNAL));

        MainItem item = new MainItem();
        item.setId(0);
        item.setType(AppConstants.SLIDER_ITEM);
        item.setItems(v);
        items.add(item);


        //row 1-> dir

        ArrayList<CategoryItemSet> videoItemSets = new ArrayList<>();

        CategoryItemSet videoItemSet = new CategoryItemSet();
        videoItemSet.setId(0);
        videoItemSet.setTitle("صفر تا صد فیزیک کنکور");
        videoItemSet.setAuthor(" کازیان ");
        videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/physics1809261648.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);

        videoItemSet = new CategoryItemSet();
        videoItemSet.setId(1);
        videoItemSet.setTitle("صفر تا صد شیمی کنکور");
        videoItemSet.setAuthor(" مهدی صنیعی ");
        videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/shimi_1809301705.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);

        videoItemSet = new CategoryItemSet();
        videoItemSet.setId(2);
        videoItemSet.setTitle("صفر تا صد حسابان کنکور");
        videoItemSet.setAuthor(" محمد صادق ثابتی ");
        videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/hesaban_1806091555.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);


        videoItemSet = new CategoryItemSet();
        videoItemSet.setId(3);
        videoItemSet.setTitle("صفر تا صد ریاضی تجربی کنکور");
        videoItemSet.setAuthor(" محمد صادق ثابتی ");
        videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/riyazi_tajrobi_1809261626.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);

        videoItemSet = new CategoryItemSet();
        videoItemSet.setId(4);
        videoItemSet.setTitle("صفر تا صد عربی کنکور");
        videoItemSet.setAuthor(" پدرام علیمرادی");
        videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/arabi_1806091641.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);

        videoItemSet = new CategoryItemSet();
        videoItemSet.setId(5);
        videoItemSet.setTitle("صفر تا صد منطق کنکور");
        videoItemSet.setAuthor(" حسام الدین حلالی");
        videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/171005032754.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);


        item = new MainItem();
        item.setId(0);
        item.setType(AppConstants.CATEGORY_ITEM_SET);
        item.setTitle("کنکور نظام جدید ");
        item.setItems(videoItemSets);
        items.add(item);

        //-------------------content-----------------------------------------

        //row 2-> content
        ArrayList<Content> contentItemSets = new ArrayList<>();

        Content contentItemSet = new Content();
        contentItemSet.setId(0);
        contentItemSet.setTitle("صفر تا صد فیزیک کنکور");
        contentItemSet.setAuthor(" کازیان ");
        contentItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/physics1809261648.jpg?w=253&h=142");
        contentItemSets.add(contentItemSet);

        contentItemSet = new Content();
        contentItemSet.setId(6);
        contentItemSet.setTitle("صفر تا صد منطق کنکور");
        contentItemSet.setAuthor(" حسام الدین حلالی");
        contentItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/171005032754.jpg?w=253&h=142");
        contentItemSets.add(contentItemSet);


        item = new MainItem();
        item.setId(0);
        item.setType(AppConstants.CONTENT_ITEM_SET);
        item.setTitle("محتوا");
        item.setItems(contentItemSets);
        items.add(item);

        //row 3 -> category

        videoItemSets = new ArrayList<>();
        videoItemSet = new CategoryItemSet();
        videoItemSet.setId(0);
        videoItemSet.setTitle("زیست کنکور");
        videoItemSet.setAuthor(" ابوالفضل جعفری");
        videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/171125105021.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);

        videoItemSet = new CategoryItemSet();
        videoItemSet.setId(1);
        videoItemSet.setTitle("آرایه های ادبی");
        videoItemSet.setAuthor(" ابوالفضل جعفری");
        videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/170917011741.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);

        videoItemSet = new CategoryItemSet();
        videoItemSet.setId(2);
        videoItemSet.setTitle("مشاوره");
        videoItemSet.setAuthor(" محمد علی امینی راد");
        videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/140701010549.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);

        videoItemSet = new CategoryItemSet();
        videoItemSet.setId(3);
        videoItemSet.setTitle("شیمی شب کنکور");
        videoItemSet.setAuthor(" مهدی صنیعی");
        videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/170920034146.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);


        videoItemSet = new CategoryItemSet();
        videoItemSet.setId(3);
        videoItemSet.setTitle("نکته و تست فیزیک");
        videoItemSet.setAuthor(" پیمان طلوعی");
        videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/170925055613.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);


        item = new MainItem();
        item.setId(1);
        item.setType(AppConstants.CATEGORY_ITEM_SET);
        item.setTitle("کنکور نظام قدیم");
        item.setItems(videoItemSets);
        items.add(item);

        //  //row 4-> banner
        ArrayList<BannerItem> bannerItems = new ArrayList<>();
        BannerItem bannerItem = new BannerItem();
        bannerItem.setId(1);
        bannerItem.setTitle("ریاضی");
        bannerItem.setUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/171003105152.jpg?w=253&h=142");
        bannerItems.add(bannerItem);

        bannerItem = new BannerItem();
        bannerItem.setId(2);
        bannerItem.setTitle("فیزیک");
        bannerItem.setUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/170920125924.jpg?w=253&h=142");
        bannerItems.add(bannerItem);

        item = new MainItem();
        item.setId(0);
        item.setType(AppConstants.BANNER_ITEM);
        item.setTitle("همه چیز!");
        item.setItems(bannerItems);
        items.add(item);


        //row 5 -> category

        videoItemSets = new ArrayList<>();
        videoItemSet = new CategoryItemSet();
        videoItemSet.setId(AppConstants.CATEGORY_ITEM_SET);
        videoItemSet.setTitle("صفر تا صد زبان");
        videoItemSet.setAuthor("علی اکبر عزتی");
        videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/zaban11_1810070959.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);

        videoItemSet = new CategoryItemSet();
        videoItemSet.setId(1);
        videoItemSet.setTitle("زیست یازدهم");
        videoItemSet.setAuthor(" جلال موقاری");
        videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/zist_yazdahom_1810011438.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);

        videoItemSet = new CategoryItemSet();
        videoItemSet.setId(2);
        videoItemSet.setTitle("صفر تا صد فیزیک یازدهم");
        videoItemSet.setAuthor(" فرشید داداشی");
        videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/170920041635.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);

        videoItemSet = new CategoryItemSet();
        videoItemSet.setId(3);
        videoItemSet.setTitle("زیست یازدهم");
        videoItemSet.setAuthor(" عباس راسنی");
        videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/171019113948.jpg?w=460&h=259");
        videoItemSets.add(videoItemSet);


        item = new MainItem();
        item.setId(3);
        item.setType(0);
        item.setTitle("مقطع یازدهم");
        item.setItems(videoItemSets);
        items.add(item);

        adapter.notifyDataSetChanged();
    }

    private void initView(View v) {

        //recyclerView
        myRecyclerView = v.findViewById(R.id.recyclerView);
        myRecyclerView.setNestedScrollingEnabled(false);
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(AppConfig.context, LinearLayoutManager.VERTICAL, false));
        adapter = new MainItemAdapter(AppConfig.context, items);
        adapter.setSize(AppConfig.width, AppConfig.height);
        myRecyclerView.setAdapter(adapter);

        setHasOptionsMenu(true);
        setToolbar(mToolbar, "آلاء مجری توسعه عدالت آموزشی");

    }


    public void showToolBar(String title) {

       /* toolbar = (Toolbar) findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        toolbarTitle = (TextView) toolbar.findViewById(R.id.txtToolbarTitle);
        toolbarTitle.setText(title);*/
    }
}

