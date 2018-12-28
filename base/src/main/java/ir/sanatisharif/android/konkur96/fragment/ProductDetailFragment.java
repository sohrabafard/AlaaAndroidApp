package ir.sanatisharif.android.konkur96.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uncopt.android.widget.text.justify.JustifiedTextView;
import com.viewpagerindicator.CirclePageIndicator;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.activity.SettingActivity;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.model.Events;
import ir.sanatisharif.android.konkur96.model.IncredibleOffer;
import ir.sanatisharif.android.konkur96.model.ProductSliderModel;
import ir.sanatisharif.android.konkur96.model.ShopItem;
import ir.sanatisharif.android.konkur96.ui.view.autoscrollviewpager.AutoScrollViewPager;
import ir.sanatisharif.android.konkur96.ui.view.autoscrollviewpager.ProductImageSliderAdapter;
import ir.sanatisharif.android.konkur96.utils.ShopUtils;

public class ProductDetailFragment extends BaseFragment {


    Toolbar pageToolbar;
    AutoScrollViewPager imgSlider;
    CirclePageIndicator imgSliderIndicator;


    TextView txtName, txtAuthor, txtAtrr, txtComment, txtPrice;
    JustifiedTextView txtShortDesc, txtDesc;


    private ProductImageSliderAdapter adapter;
    private ArrayList<ProductSliderModel> items = new ArrayList<>();


    public static ProductDetailFragment newInstance(ShopItem item) {

        Bundle args = new Bundle();
        args.putSerializable("item", item);
        ProductDetailFragment fragment = new ProductDetailFragment();
        fragment.setArguments(args);
        return fragment;

    }

    public static ProductDetailFragment newInstance(IncredibleOffer item) {

        Bundle args = new Bundle();
        args.putSerializable("item", item);
        ProductDetailFragment fragment = new ProductDetailFragment();
        fragment.setArguments(args);
        return fragment;

    }

    public static ProductDetailFragment newInstance() {

        Bundle args = new Bundle();
        ProductDetailFragment fragment = new ProductDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_detail, container, false);
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

        items.add(new ProductSliderModel("1",
                "https://sanatisharif.ir/image/9/1280/500/asiatech-alaa222%20%281%29_20180812155522.jpg?1", 1));
        items.add(new ProductSliderModel("2",
                "https://sanatisharif.ir/image/9/1280/500/IMG_20180920_004154_394_20180919201252.jpg?1", 2));
        items.add(new ProductSliderModel("3",
                "https://sanatisharif.ir/image/9/1280/500/BIG-SLIDE-1%20%282%29_20180429193412.jpg?1", 3));
        items.add(new ProductSliderModel("4",
                "https://sanatisharif.ir/image/9/1280/500/BIG-SLIDE-7_20181015143731.jpg?1", 4));

        //---------------------- End slider ----------------------------------------------------

        adapter.notifyDataSetChanged();

        //---------------------- Details ----------------------------------------------------

        txtName.setText("بسته طلایی کنکور تمام دروس");
        txtAuthor.setText("کازیان");

        txtPrice.setText(ShopUtils.formatPrice(200000) + " تومان ");

        txtShortDesc.setText("لورم ایپسوم متن ساختگی با تولید سادگی نامفهوم از صنعت چاپ و با استفاده از طراحان گرافیک است.لورم ایپسوم متن ساختگی با تولید سادگی نامفهوم از صنعت چاپ و با استفاده از طراحان گرافیک است. ");
        txtDesc.setText("لورم ایپسوم متن ساختگی با تولید سادگی نامفهوم از صنعت چاپ و با استفاده از طراحان گرافیک است. چاپگرها و متون بلکه روزنامه و مجله در ستون و سطرآنچنان که لازم است و برای شرایط فعلی تکنولوژی مورد نیاز و کاربردهای متنوع با هدف بهبود ابزارهای کاربردی می باشد. کتابهای زیادی در شصت و سه درصد گذشته، حال و آینده شناخت فراوان جامعه و متخصصان را می طلبد تا با نرم افزارها شناخت بیشتری را برای طراحان رایانه ای علی الخصوص طراحان خلاقی و فرهنگ پیشرو در زبان فارسی ایجاد کرد. در این صورت می توان امید داشت که تمام و دشواری موجود در ارائه راهکارها و شرایط سخت تایپ به پایان رسد و زمان مورد نیاز شامل حروفچینی دستاوردهای اصلی و جوابگوی سوالات پیوسته اهل دنیای موجود طراحی اساسا مورد استفاده قرار گیرد. ");



    }

    private void initView(View v) {


        initImageSlider(v);
        initDetails(v);

        setHasOptionsMenu(true);
        setToolbar(pageToolbar, "آلاء مجری توسعه عدالت آموزشی");

    }

    private void initDetails(View v){

        //Product Name
        txtName = v.findViewById(R.id.txt_name);
        txtAuthor = v.findViewById(R.id.txt_author);
        txtAtrr = v.findViewById(R.id.txt_atrr);
        txtComment = v.findViewById(R.id.txt_comment);

        txtPrice = v.findViewById(R.id.txt_price);

        txtShortDesc = v.findViewById(R.id.txt_short_desc);
        txtDesc = v.findViewById(R.id.txt_desc);

        //Set Typeface
        txtName.setTypeface(AppConfig.fontIRSensLight);
        txtAuthor.setTypeface(AppConfig.fontIRSensLight);
        txtAtrr.setTypeface(AppConfig.fontIRSensLight);
        txtComment.setTypeface(AppConfig.fontIRSensLight);

        txtPrice.setTypeface(AppConfig.fontIRSensLight);

        txtShortDesc.setTypeface(AppConfig.fontIRSensLight);
        txtDesc.setTypeface(AppConfig.fontIRSensLight);
    }

    private void initImageSlider(View v){

        //image Slider
        imgSlider = v.findViewById(R.id.view_pager);
        imgSlider.startAutoScroll(50000);
        imgSlider.setBorderAnimation(true);
        imgSlider.getLayoutParams().height = 250;

        //image Slider Indicator
        imgSliderIndicator = v.findViewById(R.id.indicator);

        //Set adapter
        adapter = new ProductImageSliderAdapter(AppConfig.context, items);
        imgSlider.setAdapter(adapter);
        imgSlider.startAutoScroll();

        //Set Indicator
        imgSliderIndicator.setViewPager(imgSlider);

    }



}

