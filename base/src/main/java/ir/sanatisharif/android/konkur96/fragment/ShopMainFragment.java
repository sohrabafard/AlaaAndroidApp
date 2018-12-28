package ir.sanatisharif.android.konkur96.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import ir.sanatisharif.android.konkur96.adapter.MainShopItemAdapter;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.model.BannerItem;
import ir.sanatisharif.android.konkur96.model.CategoryItemSet;
import ir.sanatisharif.android.konkur96.model.Content;
import ir.sanatisharif.android.konkur96.model.Events;
import ir.sanatisharif.android.konkur96.model.IncredibleOffer;
import ir.sanatisharif.android.konkur96.model.MainShopItem;
import ir.sanatisharif.android.konkur96.model.ShopItem;
import ir.sanatisharif.android.konkur96.model.ViewSlider;

public class ShopMainFragment extends BaseFragment {

    RecyclerView shopMainRecyclerView;
    Toolbar pageToolbar;

    private MainShopItemAdapter adapter;
    private ArrayList<MainShopItem> items = new ArrayList<>();

    public static ShopMainFragment newInstance() {

        Bundle args = new Bundle();

        ShopMainFragment fragment = new ShopMainFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_shop, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initView(view);

        setDummyData();
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


    private void setDummyData() {

        //---------------------- slider ----------------------------------------------------
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

        MainShopItem item = new MainShopItem();
        item.setId(0);
        item.setType(AppConstants.SHOP_SLIDER_ITEM);
        item.setItems(v);
        items.add(item);
        //---------------------- End slider ----------------------------------------------------


        //-------------------row 1 >> incredible_offer-----------------------------------------
        ArrayList<IncredibleOffer> incredibleOffers = new ArrayList<>();

        IncredibleOffer incredibleOffer = new IncredibleOffer();
        incredibleOffer.setId(0);
        incredibleOffer.setTitle("بسته ویژه ریاضیات کنکور");
        incredibleOffer.setAuthor(" کازیان ");
        incredibleOffer.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/171003105152.jpg?w=253&h=142");
        incredibleOffer.setPrice(400000);
        incredibleOffer.setDiscount(200000);
        incredibleOffers.add(incredibleOffer);

        incredibleOffer = new IncredibleOffer();
        incredibleOffer.setId(1);
        incredibleOffer.setTitle("بسته ویژه فیزیک کنکور");
        incredibleOffer.setAuthor("حسینی");
        incredibleOffer.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/171005032754.jpg?w=253&h=142");
        incredibleOffer.setPrice(800000);
        incredibleOffer.setDiscount(400000);
        incredibleOffers.add(incredibleOffer);

        incredibleOffer = new IncredibleOffer();
        incredibleOffer.setId(2);
        incredibleOffer.setTitle("بسته ویژه ادبیات کنکور");
        incredibleOffer.setAuthor(" حسام الدین حلالی");
        incredibleOffer.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/physics1809261648.jpg?w=253&h=142");
        incredibleOffer.setPrice(100000);
        incredibleOffer.setDiscount(20000);
        incredibleOffers.add(incredibleOffer);


        item = new MainShopItem();
        item.setId(0);
        item.setType(AppConstants.INCREDIBLEOFFER_ITEM_SET);
        item.setTitle("پیشنهاد شگفت انگیز");
        item.setItems(incredibleOffers);
        items.add(item);
        //----------------------- End row 1 ---------------------------------------------


        //--------------------------- row 2 ----------------------------------------------
        ArrayList<ShopItem> productItemSets = new ArrayList<>();

        ShopItem shopItem = new ShopItem();
        shopItem.setId(0);
        shopItem.setTitle("صفر تا صد فیزیک کنکور");
        shopItem.setAuthor(" کازیان ");
        shopItem.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/physics1809261648.jpg?w=253&h=142");
        shopItem.setPrice(400000);
        productItemSets.add(shopItem);

        shopItem = new ShopItem();
        shopItem.setId(1);
        shopItem.setTitle("صفر تا صد شیمی کنکور");
        shopItem.setAuthor(" مهدی صنیعی ");
        shopItem.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/shimi_1809301705.jpg?w=253&h=142");
        shopItem.setPrice(200000);
        productItemSets.add(shopItem);

        shopItem = new ShopItem();
        shopItem.setId(2);
        shopItem.setTitle("صفر تا صد حسابان کنکور");
        shopItem.setAuthor(" محمد صادق ثابتی ");
        shopItem.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/hesaban_1806091555.jpg?w=253&h=142");
        shopItem.setPrice(300000);
        productItemSets.add(shopItem);


        shopItem = new ShopItem();
        shopItem.setId(3);
        shopItem.setTitle("صفر تا صد ریاضی تجربی کنکور");
        shopItem.setAuthor(" محمد صادق ثابتی ");
        shopItem.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/riyazi_tajrobi_1809261626.jpg?w=253&h=142");
        shopItem.setPrice(100000);
        productItemSets.add(shopItem);

        shopItem = new ShopItem();
        shopItem.setId(4);
        shopItem.setTitle("صفر تا صد عربی کنکور");
        shopItem.setAuthor(" پدرام علیمرادی");
        shopItem.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/arabi_1806091641.jpg?w=253&h=142");
        shopItem.setPrice(800000);
        productItemSets.add(shopItem);

        shopItem = new ShopItem();
        shopItem.setId(5);
        shopItem.setTitle("صفر تا صد منطق کنکور");
        shopItem.setAuthor(" حسام الدین حلالی");
        shopItem.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/171005032754.jpg?w=253&h=142");
        shopItem.setPrice(700000);
        productItemSets.add(shopItem);


        item = new MainShopItem();
        item.setId(0);
        item.setType(AppConstants.CATEGORY_SHOP_ITEM_SET);
        item.setTitle("بسته های کنکور نظام جدید ");
        item.setItems(productItemSets);
        items.add(item);
        //--------------------------- End row 2 ----------------------------------------------


        //--------------------------- row 3 -------------------------------------------------
        productItemSets = new ArrayList<>();
        shopItem = new ShopItem();
        shopItem.setId(0);
        shopItem.setTitle("زیست کنکور");
        shopItem.setAuthor(" ابوالفضل جعفری");
        shopItem.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/171125105021.jpg?w=253&h=142");
        shopItem.setPrice(40000);
        productItemSets.add(shopItem);

        shopItem = new ShopItem();
        shopItem.setId(1);
        shopItem.setTitle("آرایه های ادبی");
        shopItem.setAuthor(" ابوالفضل جعفری");
        shopItem.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/170917011741.jpg?w=253&h=142");
        shopItem.setPrice(5000000);
        productItemSets.add(shopItem);

        shopItem = new ShopItem();
        shopItem.setId(2);
        shopItem.setTitle("مشاوره");
        shopItem.setAuthor(" محمد علی امینی راد");
        shopItem.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/140701010549.jpg?w=253&h=142");
        shopItem.setPrice(600000);
        productItemSets.add(shopItem);

        shopItem = new ShopItem();
        shopItem.setId(3);
        shopItem.setTitle("شیمی شب کنکور");
        shopItem.setAuthor(" مهدی صنیعی");
        shopItem.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/170920034146.jpg?w=253&h=142");
        shopItem.setPrice(59000);
        productItemSets.add(shopItem);


        shopItem = new ShopItem();
        shopItem.setId(3);
        shopItem.setTitle("نکته و تست فیزیک");
        shopItem.setAuthor(" پیمان طلوعی");
        shopItem.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/170925055613.jpg?w=253&h=142");
        shopItem.setPrice(1000);
        productItemSets.add(shopItem);


        item = new MainShopItem();
        item.setId(1);
        item.setType(AppConstants.CATEGORY_SHOP_ITEM_SET);
        item.setTitle("بسته های کنکور نظام قدیم");
        item.setItems(productItemSets);
        items.add(item);
        //--------------------------- End row 3 -------------------------------------------------

        //--------------------------- row 4 >> Banner -------------------------------------------
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

        item = new MainShopItem();
        item.setId(0);
        item.setType(AppConstants.SHOP_BANNER_ITEM);
        item.setTitle("صفر تا صد");
        item.setItems(bannerItems);
        items.add(item);
        //--------------------------- End row 4 >> Banner -------------------------------------------


        //--------------------------- row 5 -------------------------------------------------
        productItemSets = new ArrayList<>();
        shopItem = new ShopItem();
        shopItem.setId(AppConstants.CATEGORY_ITEM_SET);
        shopItem.setTitle("صفر تا صد زبان");
        shopItem.setAuthor("علی اکبر عزتی");
        shopItem.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/zaban11_1810070959.jpg?w=253&h=142");
        shopItem.setPrice(6754000);
        productItemSets.add(shopItem);

        shopItem = new ShopItem();
        shopItem.setId(1);
        shopItem.setTitle("زیست یازدهم");
        shopItem.setAuthor(" جلال موقاری");
        shopItem.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/zist_yazdahom_1810011438.jpg?w=253&h=142");
        shopItem.setPrice(70000);
        productItemSets.add(shopItem);

        shopItem = new ShopItem();
        shopItem.setId(2);
        shopItem.setTitle("صفر تا صد فیزیک یازدهم");
        shopItem.setAuthor(" فرشید داداشی");
        shopItem.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/170920041635.jpg?w=253&h=142");
        shopItem.setPrice(87654321);
        productItemSets.add(shopItem);

        shopItem = new ShopItem();
        shopItem.setId(3);
        shopItem.setTitle("زیست یازدهم");
        shopItem.setAuthor(" عباس راسنی");
        shopItem.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/171019113948.jpg?w=460&h=259");
        shopItem.setPrice(500000);
        productItemSets.add(shopItem);


        item = new MainShopItem();
        item.setId(3);
        item.setType(AppConstants.CATEGORY_SHOP_ITEM_SET);
        item.setTitle("مقطع یازدهم");
        item.setItems(productItemSets);
        items.add(item);
        //--------------------------- End row 5 -------------------------------------------------

        adapter.notifyDataSetChanged();
    }

    private void initView(View v) {

        //recyclerView
        shopMainRecyclerView = v.findViewById(R.id.recyclerView_main_shop);
        shopMainRecyclerView.setNestedScrollingEnabled(false);
        shopMainRecyclerView.setHasFixedSize(true);
        shopMainRecyclerView.setLayoutManager(new LinearLayoutManager(AppConfig.context, LinearLayoutManager.VERTICAL, false));
        adapter = new MainShopItemAdapter(AppConfig.context, items);
        adapter.setSize(AppConfig.width, AppConfig.height);
        shopMainRecyclerView.setAdapter(adapter);

        setHasOptionsMenu(true);
        setToolbar(pageToolbar, "آلاء مجری توسعه عدالت آموزشی");

    }

}

