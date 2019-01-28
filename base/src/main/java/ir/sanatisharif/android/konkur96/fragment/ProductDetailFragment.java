package ir.sanatisharif.android.konkur96.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
import ir.sanatisharif.android.konkur96.model.Video;
import ir.sanatisharif.android.konkur96.ui.view.autoscrollviewpager.AutoScrollViewPager;
import ir.sanatisharif.android.konkur96.ui.view.autoscrollviewpager.ProductImageSliderAdapter;
import ir.sanatisharif.android.konkur96.utils.ShopUtils;

public class ProductDetailFragment extends BaseFragment {


    Toolbar pageToolbar;
    AutoScrollViewPager imgSlider;
    CirclePageIndicator imgSliderIndicator;
    CardView cardAttrProduct;

    ImageView imgVideoRelatedOne;
    ImageView imgVideoRelatedTwo;

    Dialog dialog;

    Spinner spinnerMainProduct;
    Spinner spinnerExtraProduct;
    Spinner spinnerExtraProductDay;

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

        cardAttrProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLayoutDialog();
            }
        });


      //todo : add video player


        Glide.with(getContext())
                .load("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/171125105021.jpg?w=253&h=142")
                .into(imgVideoRelatedOne);

        Glide.with(getContext())
                .load("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/170917011741.jpg?w=253&h=142")
                .into(imgVideoRelatedTwo);

        spinnerMainProduct.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        spinnerMainProduct.setOnItemSelectedListener(spinnerMainProduct.getOnItemSelectedListener());
        ArrayAdapter<CharSequence> mainProductAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.dummy_main_product, android.R.layout.simple_spinner_item);

        mainProductAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMainProduct.setAdapter(mainProductAdapter);



        spinnerExtraProduct.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        spinnerExtraProduct.setOnItemSelectedListener(spinnerMainProduct.getOnItemSelectedListener());
        ArrayAdapter<CharSequence> extraProductAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.dummy_extra_product, android.R.layout.simple_spinner_item);

        extraProductAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerExtraProduct.setAdapter(extraProductAdapter);



        spinnerExtraProductDay.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        spinnerExtraProductDay.setOnItemSelectedListener(spinnerMainProduct.getOnItemSelectedListener());
        ArrayAdapter<CharSequence> extraProductAdapterDay = ArrayAdapter.createFromResource(getContext(),
                R.array.dummy_extra_product_day, android.R.layout.simple_spinner_item);

        extraProductAdapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerExtraProductDay.setAdapter(extraProductAdapterDay);

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

        imgVideoRelatedOne = v.findViewById(R.id.img_related_video);
        imgVideoRelatedTwo = v.findViewById(R.id.img_related_video_two);

        cardAttrProduct = v.findViewById(R.id.card_attr_product);

        spinnerMainProduct = v.findViewById(R.id.spinner_main_product);
        spinnerExtraProduct = v.findViewById(R.id.spinner_extra_product);
        spinnerExtraProductDay = v.findViewById(R.id.spinner_extra_product_day);
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


    private void showLayoutDialog() {

        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_view_attr);
        dialog.show();
    }



}

