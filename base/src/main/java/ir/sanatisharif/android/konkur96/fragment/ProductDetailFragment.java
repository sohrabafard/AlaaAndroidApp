package ir.sanatisharif.android.konkur96.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.util.ArrayUtils;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.activity.SettingActivity;
import ir.sanatisharif.android.konkur96.adapter.ProductBonsAdapter;
import ir.sanatisharif.android.konkur96.api.Models.AttributeDataModel;
import ir.sanatisharif.android.konkur96.api.Models.AttributeModel;
import ir.sanatisharif.android.konkur96.api.Models.GETPriceModel;
import ir.sanatisharif.android.konkur96.api.Models.MainModel;
import ir.sanatisharif.android.konkur96.api.Models.ProductModel;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.dialog.ProductAttrDialogFragment;
import ir.sanatisharif.android.konkur96.dialog.ProductExtraAttrDialogFragment;
import ir.sanatisharif.android.konkur96.handler.Repository;
import ir.sanatisharif.android.konkur96.handler.RepositoryImpl;
import ir.sanatisharif.android.konkur96.handler.Result;
import ir.sanatisharif.android.konkur96.model.Events;
import ir.sanatisharif.android.konkur96.model.IncredibleOffer;
import ir.sanatisharif.android.konkur96.model.MainAttrType;
import ir.sanatisharif.android.konkur96.model.ProductType;
import ir.sanatisharif.android.konkur96.model.Video;
import ir.sanatisharif.android.konkur96.utils.GalleryWorker;
import ir.sanatisharif.android.konkur96.utils.ShopUtils;

public class ProductDetailFragment extends BaseFragment {


    Toolbar pageToolbar;
    private CardView cardAttrProduct, cardSampleProduct;

    private ImageView image;
    private FrameLayout intro;

    private TextView txtName, txtAuthor, txtAtrr, txtComment, txtPrice, txtMainAttrCom;
    private JustifiedTextView txtShortDesc, txtDesc;

    private ProgressBar progPrice;

    private CardView cardDesc, cardBon, btnAddToCard;
    private LinearLayout bodyMainAttr;

    private GalleryWorker imgGallery;

    private RecyclerView bonsRecyclerView;

    private ProductModel model;
    private ProductType type;

    private List<Integer> attrList = new ArrayList<>();
    private List<Integer> attrExtraList = new ArrayList<>();

    private Repository repository;

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

        repository = new RepositoryImpl(getActivity());

        initModel();
        if (model != null) {

            initView(view);
            initAction();
            setData();
            imgGallery = new GalleryWorker(getContext());
        }

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

    private void initModel() {

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

    private void initDetails(View v) {

        //Product Name
        txtName = v.findViewById(R.id.txt_name);
        txtAuthor = v.findViewById(R.id.txt_author);
        txtAtrr = v.findViewById(R.id.txt_atrr);
        txtComment = v.findViewById(R.id.txt_comment);

        txtPrice = v.findViewById(R.id.txt_price);

        txtShortDesc = v.findViewById(R.id.txt_short_desc);
        txtDesc = v.findViewById(R.id.txt_desc);
        txtMainAttrCom = v.findViewById(R.id.txt_main_attr_com);

        image = v.findViewById(R.id.img);


        intro = v.findViewById(R.id.intro_video);

        cardAttrProduct = v.findViewById(R.id.card_attr_product);
        cardSampleProduct = v.findViewById(R.id.card_sample_product);

        cardDesc = v.findViewById(R.id.card_desc);
        cardBon = v.findViewById(R.id.card_bon);

        btnAddToCard = v.findViewById(R.id.btn_addToCard);

        bodyMainAttr = v.findViewById(R.id.body_main_attr);

        bonsRecyclerView = v.findViewById(R.id.recycler_bons);

        progPrice = v.findViewById(R.id.prog_price);

        //Set Typeface
        txtName.setTypeface(AppConfig.fontIRSensLight);
        txtAuthor.setTypeface(AppConfig.fontIRSensLight);
        txtAtrr.setTypeface(AppConfig.fontIRSensLight);
        txtComment.setTypeface(AppConfig.fontIRSensLight);
        txtMainAttrCom.setTypeface(AppConfig.fontIRSensLight);

        txtPrice.setTypeface(AppConfig.fontIRSensLight);

        txtShortDesc.setTypeface(AppConfig.fontIRSensLight);
        txtDesc.setTypeface(AppConfig.fontIRSensLight);



    }

    private void initAction() {

        cardAttrProduct.setOnClickListener(v -> showAtrrDialog());
        cardSampleProduct.setOnClickListener(v -> showSampleProduct());
        btnAddToCard.setOnClickListener(v -> {
            if ( null != model.getAttributes().getExtra()){

                showExtraAtrrDialog();
            }
        });
    }


    @SuppressLint("SetTextI18n")
    private void setData() {

        //---------------------- introvideo ----------------------------------------------------

        setIntroVideo(model.getIntroVideo());

        //---------------------- Details ----------------------------------------------------

        txtName.setText(model.getName());

        setPrice();

        txtShortDesc.setText(ShopUtils.setHTMLText(model.getShortDescription()));

        if (null != model.getLongDescription()){

            txtDesc.setText(ShopUtils.setHTMLText(model.getLongDescription()));

        }else {

            cardDesc.setVisibility(View.GONE);
        }



        Glide.with(AppConfig.context)
                .load(model.getPhoto())
                .into(image);

        if (null != model.getBons() && model.getBons().size() > 0){

            //setadapter
            ProductBonsAdapter adapter = new ProductBonsAdapter(model.getBons());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            bonsRecyclerView.setLayoutManager(mLayoutManager);
            bonsRecyclerView.setItemAnimator(new DefaultItemAnimator());
            bonsRecyclerView.setAdapter(adapter);

        }else {

            cardBon.setVisibility(View.GONE);

        }

        setMainAttr();



    }

    @SuppressLint("SetTextI18n")
    private void setMainAttr(){

        if (type == ProductType.CONFIGURABLE) {

            bodyMainAttr.setVisibility(View.VISIBLE);

            for (AttributeModel attr : model.getAttributes().getMain()){

                MainAttrType type = ShopUtils.getMainAttrType(attr);

                if (type == MainAttrType.SIMPLE){

                    createSimpleAttr(attr);

                }else if (type == MainAttrType.CHECKBOX){

                    createCheckBoxAttr(attr);

                }else if (type == MainAttrType.DROPDOWN){

                    createDropDownAttr(attr);
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void createSimpleAttr(AttributeModel attr){

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        TextView textView = new TextView(getContext());
        textView.setLayoutParams(params);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(17);
        textView.setGravity(Gravity.RIGHT);
        textView.setPadding(15, 5, 15, 5);
        textView.setTypeface(AppConfig.fontIRSensLight);

        StringBuilder data = new StringBuilder();
        for (AttributeDataModel attrData : attr.getData()) {

            data.append(" ").append(attrData.getName()).append(" ");

        }
        textView.setText(attr.getTitle() + " : " + data);

        bodyMainAttr.addView(textView);
    }

    private void createCheckBoxAttr(AttributeModel attr){

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);


        for (AttributeDataModel attrData : attr.getData()) {

            CheckBox checkBox = new CheckBox(getContext());
            checkBox.setLayoutParams(params);
            checkBox.setText(attrData.getName());
            checkBox.setTag(attrData.getId());
            checkBox.setTextColor(Color.BLACK);
            checkBox.setTextSize(17);
            checkBox.setGravity(Gravity.RIGHT);
            checkBox.setPadding(15, 5, 15, 5);
            checkBox.setTypeface(AppConfig.fontIRSensLight);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                checkBox.setButtonTintList(ContextCompat.getColorStateList(getContext(), R.color.checkboxtint));
            }
            checkBox.setOnCheckedChangeListener((compoundButton, check) -> {
                if (check){

                     addToAttrList((int) compoundButton.getTag());

                }else {

                    removeToAttrList((int) compoundButton.getTag());
                }

                getPrice();
            });
            bodyMainAttr.addView(checkBox);
        }
    }

    private void createDropDownAttr(AttributeModel attr){

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        List<String> spinnerArray = new ArrayList<>();
        @SuppressLint("UseSparseArrays")
        HashMap<String, Integer> spinnerMap = new HashMap<>();

        Spinner spinner = new Spinner(getContext());
        spinner.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        spinner.setLayoutParams(params);
        for (AttributeDataModel attrData : attr.getData()) {

            spinnerArray.add(attrData.getName());
            spinnerMap.put(attrData.getName(),attrData.getId());
        }
        ArrayAdapter<String> attrAdapter = new ArrayAdapter<>(
                getContext(), android.R.layout.simple_spinner_item, spinnerArray);

        attrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(attrAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long ij) {
                for (int i = 0; i < spinnerArray.size(); i++) {
                    String spinner = spinnerArray.get(i);
                    int id = spinnerMap.get(spinner);
                    removeToAttrList(id);
                }

                addToAttrList(spinnerMap.get(spinnerArray.get(position)));
                getPrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        bodyMainAttr.addView(spinner);

    }

    private void showAtrrDialog() {

        FragmentManager fm = getFragmentManager();
        DialogFragment newFragment = new ProductAttrDialogFragment(model.getAttributes().getInformation());
        newFragment.show(fm, "ProductAttr");

    }

    private void showExtraAtrrDialog() {

        FragmentManager fm = getFragmentManager();
        DialogFragment newFragment = new ProductExtraAttrDialogFragment(model.getId(), attrList, model.getAttributes().getExtra());
        newFragment.show(fm, "ProductExtraAttr");

    }

    private void showSampleProduct() {

        imgGallery.setImages(model.getSamplePhotos());
        imgGallery.openFullView(0);

    }

    private void setIntroVideo(String url) {

//        if (null != url){
//
//            Video video = ShopUtils.createVideoModelByURL(url);
//            if (video != null) {
//                VideoPlayFrg videoPlayFrg = VideoPlayFrg.newInstance(video.getPath());
//                getFragmentManager().beginTransaction()
//                        .add(R.id.intro_video, videoPlayFrg, "videoPlayFrg")
//                        .commit();
//            }
//
//        }else {
//
//            intro.setVisibility(View.GONE);
//        }

    }

    @SuppressLint("SetTextI18n")
    private void setPrice() {


        if (model.getPrice().getMfinal() > 0) {

            txtPrice.setText(ShopUtils.formatPrice(model.getPrice().getMfinal()) + " تومان ");

        } else {

            txtPrice.setText(ShopUtils.formatPrice(0) + " تومان ");
        }
    }


    private void addToAttrList(int val){

        if (!attrList.contains(val)) {

            attrList.add(val);
        }
    }

    private void removeToAttrList(int val){

        if (attrList.contains(val)) {

            attrList = ShopUtils.removeElements(attrList, val);
        }
    }

    @SuppressLint("SetTextI18n")
    private void getPrice() {

        ArrayList<Integer> mainAttributeValues = new ArrayList<>(attrList);
        ArrayList<Integer> extraAttributeValues = new ArrayList<>(attrExtraList);
        progPrice.setVisibility(View.VISIBLE);

        repository.getPrice(String.valueOf(model.getId()), mainAttributeValues, extraAttributeValues, data -> {
            progPrice.setVisibility(View.GONE);
            if (data instanceof Result.Success) {

                GETPriceModel temp = (GETPriceModel) ((Result.Success) data).value;

                if (null == temp.getError()){

                    if (temp.getCost().getMfinal() > 0) {

                        txtPrice.setText(ShopUtils.formatPrice(temp.getCost().getMfinal()) + " تومان ");

                    } else {

                        txtPrice.setText(ShopUtils.formatPrice(0) + " تومان ");
                    }

                }else {

                    Toast.makeText(getContext(),temp.getError().getMessage(),Toast.LENGTH_LONG).show();
                }


            } else {

                Log.d("Test", (String) ((Result.Error) data).value);
            }


        });


    }


}

