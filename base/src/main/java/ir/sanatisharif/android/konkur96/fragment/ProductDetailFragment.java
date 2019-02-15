package ir.sanatisharif.android.konkur96.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

import org.greenrobot.eventbus.EventBus;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.activity.SettingActivity;
import ir.sanatisharif.android.konkur96.api.Models.ProductModel;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.dialog.ProductAttrDialogFragment;
import ir.sanatisharif.android.konkur96.model.Events;
import ir.sanatisharif.android.konkur96.model.IncredibleOffer;
import ir.sanatisharif.android.konkur96.model.ProductType;
import ir.sanatisharif.android.konkur96.model.Video;
import ir.sanatisharif.android.konkur96.utils.ShopUtils;

public class ProductDetailFragment extends BaseFragment {


     Toolbar pageToolbar;
     CardView cardAttrProduct;

     ImageView image;

     Dialog dialog;

     TextView txtName, txtAuthor, txtAtrr, txtComment, txtPrice;
     JustifiedTextView txtShortDesc, txtDesc;

    private ProductModel model;
    private ProductType type;


    public static ProductDetailFragment newInstance(ProductModel item) {

        Bundle args = new Bundle();
        args.putParcelable("item", item);
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


        initModel();
        if (model != null){

            initView(view);
            initAction();
            setData();
        }


//        cardAttrProduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showLayoutDialog();
//            }
//        });
//
//
//      //todo : add video player
//
//
//        Glide.with(getContext())
//                .load("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/171125105021.jpg?w=253&h=142")
//                .into(imgVideoRelatedOne);
//
//        Glide.with(getContext())
//                .load("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/170917011741.jpg?w=253&h=142")
//                .into(imgVideoRelatedTwo);
//
//        spinnerMainProduct.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
//        spinnerMainProduct.setOnItemSelectedListener(spinnerMainProduct.getOnItemSelectedListener());
//        ArrayAdapter<CharSequence> mainProductAdapter = ArrayAdapter.createFromResource(getContext(),
//                R.array.dummy_main_product, android.R.layout.simple_spinner_item);
//
//        mainProductAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerMainProduct.setAdapter(mainProductAdapter);
//
//
//
//        spinnerExtraProduct.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
//        spinnerExtraProduct.setOnItemSelectedListener(spinnerMainProduct.getOnItemSelectedListener());
//        ArrayAdapter<CharSequence> extraProductAdapter = ArrayAdapter.createFromResource(getContext(),
//                R.array.dummy_extra_product, android.R.layout.simple_spinner_item);
//
//        extraProductAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerExtraProduct.setAdapter(extraProductAdapter);
//
//
//
//        spinnerExtraProductDay.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
//        spinnerExtraProductDay.setOnItemSelectedListener(spinnerMainProduct.getOnItemSelectedListener());
//        ArrayAdapter<CharSequence> extraProductAdapterDay = ArrayAdapter.createFromResource(getContext(),
//                R.array.dummy_extra_product_day, android.R.layout.simple_spinner_item);
//
//        extraProductAdapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerExtraProductDay.setAdapter(extraProductAdapterDay);

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

    private void initModel(){

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            model = bundle.getParcelable("item");
            type = ShopUtils.getType(model.getType());
        }
    }

    private void initView(View v) {

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

        image = v.findViewById(R.id.img);

        cardAttrProduct = v.findViewById(R.id.card_attr_product);

        //Set Typeface
        txtName.setTypeface(AppConfig.fontIRSensLight);
        txtAuthor.setTypeface(AppConfig.fontIRSensLight);
        txtAtrr.setTypeface(AppConfig.fontIRSensLight);
        txtComment.setTypeface(AppConfig.fontIRSensLight);

        txtPrice.setTypeface(AppConfig.fontIRSensLight);

        txtShortDesc.setTypeface(AppConfig.fontIRSensLight);
        txtDesc.setTypeface(AppConfig.fontIRSensLight);

    }

    private void initAction(){

        cardAttrProduct.setOnClickListener(v -> showAtrrDialog());
    }

    private void setData(){

        //---------------------- introvideo ----------------------------------------------------

        setIntroVideo(model.getIntroVideo());

        //---------------------- Details ----------------------------------------------------

        txtName.setText(model.getName());

        setPrice();

        txtShortDesc.setText(ShopUtils.setHTMLText(model.getShortDescription()));

        txtDesc.setText(ShopUtils.setHTMLText(model.getLongDescription()));

        Glide.with(AppConfig.context)
                .load(model.getPhoto())
                .into(image);


    }

    private void showAtrrDialog() {

        FragmentManager fm = getFragmentManager();
        DialogFragment newFragment = new ProductAttrDialogFragment(model.getAttributes().getInformation());
        newFragment.show(fm, "ProductAttr");
    }

    private void setIntroVideo(String url){

        Video video = ShopUtils.createVideoModelByURL(url);
        if (video != null) {
            VideoPlayFrg videoPlayFrg = VideoPlayFrg.newInstance(video, false);
            getFragmentManager().beginTransaction()
                    .add(R.id.intro_video, videoPlayFrg, "videoPlayFrg")
                    .commit();
        }

    }

    private void setPrice(){

        if (type == ProductType.SIMPLE){

            if (model.getAmount() > 0 ){

                txtPrice.setText(ShopUtils.formatPrice(model.getAmount()) + " تومان ");

            }else {

                txtPrice.setText(ShopUtils.formatPrice(0) + " تومان ");
            }

        }else {

            txtPrice.setVisibility(View.GONE);
        }
    }



}

