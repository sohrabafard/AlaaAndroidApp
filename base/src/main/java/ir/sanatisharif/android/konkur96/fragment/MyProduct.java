package ir.sanatisharif.android.konkur96.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.adapter.MainBoughtItemAdapter;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.model.BannerItem;
import ir.sanatisharif.android.konkur96.model.BoughtItem;
import ir.sanatisharif.android.konkur96.model.IncredibleOffer;
import ir.sanatisharif.android.konkur96.model.MainBoughtItem;
import ir.sanatisharif.android.konkur96.model.ViewSlider;

public class MyProduct extends BaseFragment {

    Toolbar pageToolbar;
    RecyclerView productMainRecyclerView;


    private MainBoughtItemAdapter adapter;
    private ArrayList<MainBoughtItem> items = new ArrayList<>();


    public static MyProduct newInstance() {

        Bundle args = new Bundle();

        MyProduct fragment = new MyProduct();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_myproduct, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        setDummyData();

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

        MainBoughtItem item = new MainBoughtItem();
        item.setId(0);
        item.setType(AppConstants.BOUGHT_SLIDER_ITEM);
        item.setItems(v);
        items.add(item);
        //---------------------- End slider ----------------------------------------------------


        //-------------------row 1 >> incredible_offer-----------------------------------------
     /*   ArrayList<IncredibleOffer> incredibleOffers = new ArrayList<>();

        IncredibleOffer incredibleOffer = new IncredibleOffer();
        incredibleOffer.setId(0);
        incredibleOffer.setTitle("بسته ویژه ریاضیات کنکور");
        incredibleOffer.setAuthor(" کازیان ");
        incredibleOffer.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/171003105152.jpg?w=253&h=142");
        //incredibleOffer.setPrice(400000);
        //incredibleOffer.setDiscount(200000);
        incredibleOffers.add(incredibleOffer);

        incredibleOffer = new IncredibleOffer();
        incredibleOffer.setId(1);
        incredibleOffer.setTitle("بسته ویژه فیزیک کنکور");
        incredibleOffer.setAuthor("حسینی");
        incredibleOffer.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/171005032754.jpg?w=253&h=142");
        //incredibleOffer.setPrice(800000);
        //incredibleOffer.setDiscount(400000);
        incredibleOffers.add(incredibleOffer);

        incredibleOffer = new IncredibleOffer();
        incredibleOffer.setId(2);
        incredibleOffer.setTitle("بسته ویژه ادبیات کنکور");
        incredibleOffer.setAuthor(" حسام الدین حلالی");
        incredibleOffer.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/physics1809261648.jpg?w=253&h=142");
        //incredibleOffer.setPrice(100000);
        //incredibleOffer.setDiscount(20000);
        incredibleOffers.add(incredibleOffer);


        item = new MainBoughtItem();
        item.setId(0);
        item.setType(AppConstants.INCREDIBLEOFFER_ITEM_SET);
        item.setTitle("پیشنهاد شگفت انگیز");
        item.setItems(incredibleOffers);
        items.add(item);*/
        //----------------------- End row 1 ---------------------------------------------


        //--------------------------- row 2 ----------------------------------------------
        ArrayList<BoughtItem> productItemSets = new ArrayList<>();

        BoughtItem boughtItem = new BoughtItem();
        boughtItem.setId(0);
        boughtItem.setTitle("صفر تا صد فیزیک کنکور");
        boughtItem.setAuthor(" کازیان ");
        boughtItem.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/physics1809261648.jpg?w=253&h=142");
        boughtItem.setPrice(0);
        productItemSets.add(boughtItem);

        boughtItem = new BoughtItem();
        boughtItem.setId(1);
        boughtItem.setTitle("صفر تا صد شیمی کنکور");
        boughtItem.setAuthor(" مهدی صنیعی ");
        boughtItem.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/shimi_1809301705.jpg?w=253&h=142");
        boughtItem.setPrice(0);
        productItemSets.add(boughtItem);

        boughtItem = new BoughtItem();
        boughtItem.setId(2);
        boughtItem.setTitle("صفر تا صد حسابان کنکور");
        boughtItem.setAuthor(" محمد صادق ثابتی ");
        boughtItem.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/hesaban_1806091555.jpg?w=253&h=142");
        boughtItem.setPrice(0);
        productItemSets.add(boughtItem);


        boughtItem = new BoughtItem();
        boughtItem.setId(3);
        boughtItem.setTitle("صفر تا صد ریاضی تجربی کنکور");
        boughtItem.setAuthor(" محمد صادق ثابتی ");
        boughtItem.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/riyazi_tajrobi_1809261626.jpg?w=253&h=142");
        boughtItem.setPrice(0);
        productItemSets.add(boughtItem);

        boughtItem = new BoughtItem();
        boughtItem.setId(4);
        boughtItem.setTitle("صفر تا صد عربی کنکور");
        boughtItem.setAuthor(" پدرام علیمرادی");
        boughtItem.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/arabi_1806091641.jpg?w=253&h=142");
        boughtItem.setPrice(0);
        productItemSets.add(boughtItem);

        boughtItem = new BoughtItem();
        boughtItem.setId(5);
        boughtItem.setTitle("صفر تا صد منطق کنکور");
        boughtItem.setAuthor(" حسام الدین حلالی");
        boughtItem.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/171005032754.jpg?w=253&h=142");
        boughtItem.setPrice(0);
        productItemSets.add(boughtItem);


        item = new MainBoughtItem();
        item.setId(0);
        item.setType(AppConstants.CATEGORY_BOUGHT_ITEM_SET);
        item.setTitle("بسته های کنکور نظام جدید ");
        item.setItems(productItemSets);
        items.add(item);
        //--------------------------- End row 2 ----------------------------------------------


        //--------------------------- row 3 -------------------------------------------------
       /* productItemSets = new ArrayList<>();
        boughtItem = new BoughtItem();
        boughtItem.setId(0);
        boughtItem.setTitle("زیست کنکور");
        boughtItem.setAuthor(" ابوالفضل جعفری");
        boughtItem.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/171125105021.jpg?w=253&h=142");
        boughtItem.setPrice(40000);
        productItemSets.add(boughtItem);

        boughtItem = new BoughtItem();
        boughtItem.setId(1);
        boughtItem.setTitle("آرایه های ادبی");
        boughtItem.setAuthor(" ابوالفضل جعفری");
        boughtItem.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/170917011741.jpg?w=253&h=142");
        boughtItem.setPrice(5000000);
        productItemSets.add(boughtItem);

        boughtItem = new BoughtItem();
        boughtItem.setId(2);
        boughtItem.setTitle("مشاوره");
        boughtItem.setAuthor(" محمد علی امینی راد");
        boughtItem.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/140701010549.jpg?w=253&h=142");
        boughtItem.setPrice(600000);
        productItemSets.add(boughtItem);

        boughtItem = new BoughtItem();
        boughtItem.setId(3);
        boughtItem.setTitle("شیمی شب کنکور");
        boughtItem.setAuthor(" مهدی صنیعی");
        boughtItem.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/170920034146.jpg?w=253&h=142");
        boughtItem.setPrice(59000);
        productItemSets.add(boughtItem);


        boughtItem = new BoughtItem();
        boughtItem.setId(3);
        boughtItem.setTitle("نکته و تست فیزیک");
        boughtItem.setAuthor(" پیمان طلوعی");
        boughtItem.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/170925055613.jpg?w=253&h=142");
        boughtItem.setPrice(1000);
        productItemSets.add(boughtItem);


        item = new MainBoughtItem();
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

        item = new MainBoughtItem();
        item.setId(0);
        item.setType(AppConstants.SHOP_BANNER_ITEM);
        item.setTitle("صفر تا صد");
        item.setItems(bannerItems);
        items.add(item);
        //--------------------------- End row 4 >> Banner -------------------------------------------


        //--------------------------- row 5 -------------------------------------------------
        productItemSets = new ArrayList<>();
        boughtItem = new BoughtItem();
        boughtItem.setId(AppConstants.CATEGORY_ITEM_SET);
        boughtItem.setTitle("صفر تا صد زبان");
        boughtItem.setAuthor("علی اکبر عزتی");
        boughtItem.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/zaban11_1810070959.jpg?w=253&h=142");
        boughtItem.setPrice(6754000);
        productItemSets.add(boughtItem);

        boughtItem = new BoughtItem();
        boughtItem.setId(1);
        boughtItem.setTitle("زیست یازدهم");
        boughtItem.setAuthor(" جلال موقاری");
        boughtItem.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/zist_yazdahom_1810011438.jpg?w=253&h=142");
        boughtItem.setPrice(70000);
        productItemSets.add(boughtItem);

        boughtItem = new BoughtItem();
        boughtItem.setId(2);
        boughtItem.setTitle("صفر تا صد فیزیک یازدهم");
        boughtItem.setAuthor(" فرشید داداشی");
        boughtItem.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/170920041635.jpg?w=253&h=142");
        boughtItem.setPrice(87654321);
        productItemSets.add(boughtItem);

        boughtItem = new BoughtItem();
        boughtItem.setId(3);
        boughtItem.setTitle("زیست یازدهم");
        boughtItem.setAuthor(" عباس راسنی");
        boughtItem.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/171019113948.jpg?w=460&h=259");
        boughtItem.setPrice(500000);
        productItemSets.add(boughtItem);


        item = new MainBoughtItem();
        item.setId(3);
        item.setType(AppConstants.CATEGORY_SHOP_ITEM_SET);
        item.setTitle("مقطع یازدهم");
        item.setItems(productItemSets);
        items.add(item);*/
        //--------------------------- End row 5 -------------------------------------------------

        adapter.notifyDataSetChanged();
    }



    private void initView(View v) {

        //recyclerView
        productMainRecyclerView = v.findViewById(R.id.recyclerView_main_bought);
        productMainRecyclerView.setNestedScrollingEnabled(false);
        productMainRecyclerView.setHasFixedSize(true);
        productMainRecyclerView.setLayoutManager(new LinearLayoutManager(AppConfig.context, LinearLayoutManager.VERTICAL, false));
        adapter = new MainBoughtItemAdapter(AppConfig.context, items);
        adapter.setSize(AppConfig.width, AppConfig.height);
        productMainRecyclerView.setAdapter(adapter);

        setHasOptionsMenu(true);
        setToolbar(pageToolbar, "آلاء مجری توسعه عدالت آموزشی");

    }


}


