package ir.sanatisharif.android.konkur96.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.activity.SettingActivity;
import ir.sanatisharif.android.konkur96.adapter.ProductBonsAdapter;
import ir.sanatisharif.android.konkur96.adapter.SelectableProductAdapter;
import ir.sanatisharif.android.konkur96.Models.AttributeDataModel;
import ir.sanatisharif.android.konkur96.Models.AttributeModel;
import ir.sanatisharif.android.konkur96.Models.GETPriceModel;
import ir.sanatisharif.android.konkur96.Models.PriceModel;
import ir.sanatisharif.android.konkur96.Models.ProductModel;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.dialog.ProductAttrDialogFragment;
import ir.sanatisharif.android.konkur96.dialog.ProductExtraAttrDialogFragment;
import ir.sanatisharif.android.konkur96.dialog.ZarinPalDialogFragment;
import ir.sanatisharif.android.konkur96.handler.Repository;
import ir.sanatisharif.android.konkur96.handler.RepositoryImpl;
import ir.sanatisharif.android.konkur96.handler.Result;
import ir.sanatisharif.android.konkur96.interfaces.LogUserActionsOnPublicContentInterface;
import ir.sanatisharif.android.konkur96.model.Events;
import ir.sanatisharif.android.konkur96.model.IncredibleOffer;
import ir.sanatisharif.android.konkur96.model.MainAttrType;
import ir.sanatisharif.android.konkur96.model.ProductType;
import ir.sanatisharif.android.konkur96.model.SelectableProduct;
import ir.sanatisharif.android.konkur96.utils.GalleryWorker;
import ir.sanatisharif.android.konkur96.utils.ShopUtils;
import ir.sanatisharif.android.konkur96.utils.Utils;

import static ir.sanatisharif.android.konkur96.app.AppConfig.currentActivity;
import static ir.sanatisharif.android.konkur96.app.AppConfig.showNoInternetDialog;

public class ProductDetailFragment extends BaseFragment {
    
    
    Toolbar pageToolbar;
    private LogUserActionsOnPublicContentInterface mUserAction;
    private CardView                               cardAttrProduct, cardSampleProduct;
    
    private ImageView   image;
    private FrameLayout intro;
    
    private TextView txtName, txtAuthor, txtAtrr, txtComment, txtPrice, txtMainAttrCom, txtDiscount,
            txtFinalPriceTop;
    
    private RecyclerView selectableRecyclerView;
    
    private JustifiedTextView txtShortDesc, txtDesc;
    
    private ProgressBar progPrice;
    
    private CardView cardDesc, cardBon, btnAddToCard;
    private LinearLayout bodyMainAttr, bodySelectable;
    
    private GalleryWorker imgGallery;
    
    private RecyclerView bonsRecyclerView;
    
    private ProductModel model;
    private ProductType  type;
    
    private List<Integer>           attrList         = new ArrayList<>();
    private List<Integer>           attrExtraList    = new ArrayList<>();
    private List<Integer>           selectableIdList = new ArrayList<>();
    private ArrayList<ProductModel> selectableList   = new ArrayList<>();
    
    private Repository repository;
    
    private int totalPrice = 0;
    
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
    
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        
        try {
            mUserAction = (LogUserActionsOnPublicContentInterface) context;
        }
        catch (ClassCastException ex) {
            throw new ClassCastException(context.toString()
                                         +
                                         " must implement LogUserActionsOnPublicContentInterface");
        }
    }
    
    @Override
    public void onDetach() {
        super.onDetach();
        mUserAction = null;
    }
    
    @Override
    public void onStart() {
        super.onStart();
        
        if (mUserAction != null && model != null)
            mUserAction.userStartedViewingAParticularPage(model);
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
        txtFinalPriceTop = v.findViewById(R.id.txt_final_price_top);
        selectableRecyclerView = v.findViewById(R.id.recycler_selectable);
        
        
        txtPrice = v.findViewById(R.id.txt_price);
        txtDiscount = v.findViewById(R.id.txt_discount);
        
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
        bodySelectable = v.findViewById(R.id.body_selectable);
        
        bonsRecyclerView = v.findViewById(R.id.recycler_bons);
        
        progPrice = v.findViewById(R.id.prog_price);
        
        //SetModel Typeface
        txtName.setTypeface(AppConfig.fontIRSensLight);
        txtAuthor.setTypeface(AppConfig.fontIRSensLight);
        txtAtrr.setTypeface(AppConfig.fontIRSensLight);
        txtComment.setTypeface(AppConfig.fontIRSensLight);
        txtMainAttrCom.setTypeface(AppConfig.fontIRSensLight);
        
        txtPrice.setTypeface(AppConfig.fontIRSensLight);
        txtDiscount.setTypeface(AppConfig.fontIRSensLight);
        txtFinalPriceTop.setTypeface(AppConfig.fontIRSensLight);
        
        txtFinalPriceTop.setVisibility(View.GONE);
        txtDiscount.setPaintFlags(txtDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        
        txtShortDesc.setTypeface(AppConfig.fontIRSensLight);
        txtDesc.setTypeface(AppConfig.fontIRSensLight);
        
        
    }
    
    private void initAction() {
        
        cardAttrProduct.setOnClickListener(v -> showAtrrDialog());
        cardSampleProduct.setOnClickListener(v -> showSampleProduct());
        btnAddToCard.setOnClickListener(v -> {
            if (!Utils.isConnected()) {
                showDialog();
                return;
            }
            if (modelExtraAttributeIsNull()) {
                handleBtnAddToCardWhenModelExtraAttributeIsNull();
                return;
            }
            handleBtnAddToCardWhenModelExtraAttributeIsNotNull();
        });
    }
    
    public void showDialog() {
        final Dialog dialog = new Dialog(new ContextThemeWrapper(currentActivity,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_no_internet);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        
        showNoInternetDialog = true;
        Button    btnOK    = dialog.findViewById(R.id.btnOK);
        ImageView imgCLose = dialog.findViewById(R.id.imgCLose);
        
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                showNoInternetDialog = false;
            }
        });
        imgCLose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNoInternetDialog = false;
                dialog.dismiss();
            }
        });
        
        dialog.show();
        
    }
    
    private void handleBtnAddToCardWhenModelExtraAttributeIsNotNull() {
        if (type == ProductType.CONFIGURABLE) {
            if (attrList.size() > 0) {
                showExtraAtrrDialog();
                return;
            }
            Toast.makeText(getContext(), "لطفا یک مورد را انتخاب کنید", Toast.LENGTH_LONG).show();
            return;
        }
        if (type == ProductType.SELECTABLE) {
            if (selectableIdList.size() > 0) {
                showExtraAtrrDialog();
                return;
            }
            Toast.makeText(getContext(), "لطفا یک مورد را انتخاب کنید", Toast.LENGTH_LONG).show();
            return;
        }
        if (type == ProductType.SIMPLE) {
            showExtraAtrrDialog();
        }
    }
    
    private void handleBtnAddToCardWhenModelExtraAttributeIsNull() {
        if (type == ProductType.CONFIGURABLE) {
            if (attrList.size() > 0) {
                showZarinPalDialog();
                return;
            }
            Toast.makeText(getContext(), "لطفا یک مورد را انتخاب کنید", Toast.LENGTH_LONG).show();
            return;
        }
        if (type == ProductType.SELECTABLE) {
            if (selectableIdList.size() > 0) {
                showZarinPalDialog();
                return;
            }
            Toast.makeText(getContext(), "لطفا یک مورد را انتخاب کنید", Toast.LENGTH_LONG).show();
            return;
        }
        if (type == ProductType.SIMPLE) {
            showZarinPalDialog();
        }
    }
    
    private boolean modelExtraAttributeIsNull() {
        return null == model.getAttributes().getExtra();
    }
    
    
    @SuppressLint("SetTextI18n")
    private void setData() {
        
        //---------------------- introvideo ----------------------------------------------------
        
        setIntroVideo(model.getIntroVideo());
        
        //---------------------- Details ----------------------------------------------------
        
        txtName.setText(model.getName());
        
        setPrice();
        
        txtShortDesc.setText(ShopUtils.setHTMLText(model.getShortDescription()));
        
        if (null != model.getLongDescription()) {
            
            txtDesc.setText(ShopUtils.setHTMLText(model.getLongDescription()));
            
        } else {
            
            cardDesc.setVisibility(View.GONE);
        }
        
        
        Glide.with(AppConfig.context)
                .load(model.getPhoto())
                .thumbnail(0.1f)
                .into(image);
        
        if (null != model.getBons() && model.getBons().size() > 0) {
            
            //setadapter
            ProductBonsAdapter         adapter        = new ProductBonsAdapter(model.getBons());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            bonsRecyclerView.setLayoutManager(mLayoutManager);
            bonsRecyclerView.setItemAnimator(new DefaultItemAnimator());
            bonsRecyclerView.setAdapter(adapter);
            
        } else {
            
            cardBon.setVisibility(View.GONE);
            
        }
        
        
        setSelectable();
        setMainAttr();
        
        
    }
    
    private void setSelectable() {
        
        if (type == ProductType.SELECTABLE) {
            
            bodySelectable.setVisibility(View.VISIBLE);
            
            ArrayList<SelectableProduct>
                    items =
                    ShopUtils.convertToSelectableProductModel(model.getChildren());
            
            SelectableProductAdapter
                    adapter =
                    new SelectableProductAdapter(getContext(), items, new SelectableProductAdapter.CheckListeners() {
                        @Override
                        public void onItemCheck(ProductModel model, int position, boolean isFirst) {
                            
                            selectableList.add(model);
                            addToSelectableIdList(model.getId(), isFirst);
                            final Handler handler = new Handler();
                            handler.postDelayed(() -> getPrice(), 700);
                            
                        }
                        
                        @Override
                        public void onItemUncheck(ProductModel model, int position) {
                            
                            selectableList.remove(model);
                            removeToSelectableIdList(model.getId());
                            final Handler handler = new Handler();
                            handler.postDelayed(() -> getPrice(), 700);
                        }
                    });
            
            
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            selectableRecyclerView.setLayoutManager(mLayoutManager);
            selectableRecyclerView.setItemAnimator(new DefaultItemAnimator());
            selectableRecyclerView.setAdapter(adapter);
            
            
        }
    }
    
    @SuppressLint("SetTextI18n")
    private void setMainAttr() {
        
        if (type == ProductType.CONFIGURABLE) {
            
            bodyMainAttr.setVisibility(View.VISIBLE);
            
            for (AttributeModel attr : model.getAttributes().getMain()) {
                
                MainAttrType type = ShopUtils.getMainAttrType(attr);
                
                if (type == MainAttrType.SIMPLE) {
                    
                    createSimpleAttr(attr);
                    
                } else if (type == MainAttrType.CHECKBOX) {
                    
                    createCheckBoxAttr(attr);
                    
                } else if (type == MainAttrType.DROPDOWN) {
                    
                    createDropDownAttr(attr);
                }
            }
        }
    }
    
    @SuppressLint("SetTextI18n")
    private void createSimpleAttr(AttributeModel attr) {
        
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                100);
        
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
    
    private void createCheckBoxAttr(AttributeModel attr) {
        
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                100);
        
        if (null != attr.getTitle() && !attr.getTitle().isEmpty()) {
            
            createTxtTitle(attr.getTitle());
        }
        
        
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
                if (check) {
                    
                    addToAttrList((int) compoundButton.getTag());
                    
                } else {
                    
                    removeToAttrList((int) compoundButton.getTag());
                }
                
                getPrice();
            });
            bodyMainAttr.addView(checkBox);
        }
    }
    
    private void createDropDownAttr(AttributeModel attr) {
        
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                100);
        
        if (null != attr.getTitle() && !attr.getTitle().isEmpty()) {
            
            createTxtTitle(attr.getTitle());
        }
        
        List<String> spinnerArray = new ArrayList<>();
        @SuppressLint("UseSparseArrays")
        HashMap<String, Integer> spinnerMap = new HashMap<>();
        
        Spinner spinner = new Spinner(getContext());
        spinner.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        spinner.setLayoutParams(params);
        spinner.setPadding(15, 5, 15, 5);
        for (AttributeDataModel attrData : attr.getData()) {
            
            spinnerArray.add(attrData.getName());
            spinnerMap.put(attrData.getName(), attrData.getId());
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
                    int    id      = spinnerMap.get(spinner);
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
    
    @SuppressLint("SetTextI18n")
    private void createTxtTitle(String title) {
        
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        
        TextView textView = new TextView(getContext());
        textView.setLayoutParams(params);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(15);
        textView.setGravity(Gravity.RIGHT);
        textView.setPadding(15, 5, 15, 5);
        textView.setTypeface(AppConfig.fontIRSensLight);
        
        textView.setText(title + " : ");
        
        bodyMainAttr.addView(textView);
    }
    
    private void showAtrrDialog() {
        
        if (null != model.getAttributes().getInformation()) {
            
            FragmentManager fm = getFragmentManager();
            DialogFragment
                    newFragment =
                    new ProductAttrDialogFragment(model.getAttributes().getInformation());
            newFragment.show(fm, "ProductAttr");
        }
        
    }
    
    private void showExtraAtrrDialog() {
        
        FragmentManager fm = getFragmentManager();
        DialogFragment newFragment = new ProductExtraAttrDialogFragment(type, model.getId(),
                totalPrice,
                attrList,
                model.getAttributes().getExtra(),
                selectableIdList,
                selectableList,
                model);
        
        newFragment.show(fm, "ProductExtraAttr");
        
    }
    
    
    private void showZarinPalDialog() {
        
        FragmentManager fm = getFragmentManager();
        DialogFragment
                newFragment =
                new ZarinPalDialogFragment(type, model, totalPrice, selectableIdList, attrList, attrExtraList);
        
        assert fm != null;
        newFragment.show(fm, "ZarinPalDialog");
    }
    
    
    private void showSampleProduct() {
        
        if (null != model.getSamplePhotos()) {
            
            imgGallery.setImages(model.getSamplePhotos());
            imgGallery.openFullView(0);
        }
        
    }
    
    private void setIntroVideo(String url) {
        
        if (null != url) {
            
            DetailsVideoFrg videoPlayFrg = DetailsVideoFrg.newInstance(url);
            getFragmentManager().beginTransaction()
                    .add(R.id.intro_video, videoPlayFrg, "videoPlayFrg")
                    .commit();
            
        } else {
            
            intro.setVisibility(View.GONE);
        }
        
    }
    
    @SuppressLint("SetTextI18n")
    private void setPrice() {
        
        if (model == null) {
            Crashlytics.log("ProductModel is null!!");
            return;
        }
        PriceModel price = model.getPrice();
        if (price == null) {
            Crashlytics.log("price is null - ProductModel:" + model.getId());
            return;
        }
        int mfinal = price.getMfinal();
        if (mfinal > 0) {
            totalPrice = mfinal;
            txtPrice.setText(ShopUtils.formatPrice(mfinal) + " تومان ");
            
        } else {
            
            txtPrice.setText(ShopUtils.formatPrice(0) + " تومان ");
        }
        
        if (price.getDiscount() > 0) {
            
            txtDiscount.setText(ShopUtils.formatPrice(price.getBase()) + " تومان ");
            txtFinalPriceTop.setVisibility(View.VISIBLE);
            txtFinalPriceTop.setText(txtPrice.getText());
            
        } else {
            
            txtDiscount.setText("");
        }
        
        
    }
    
    
    private void addToAttrList(int val) {
        
        if (!attrList.contains(val)) {
            
            attrList.add(val);
        }
    }
    
    private void removeToAttrList(int val) {
        
        if (attrList.contains(val)) {
            
            attrList = ShopUtils.removeElements(attrList, val);
        }
    }
    
    
    private void addToSelectableIdList(int val, boolean isFirst) {
        
        if (!selectableIdList.contains(val)) {
            
            if (isFirst) {
                
                selectableIdList.add(0, val);
                
            } else {
                
                selectableIdList.add(val);
            }
            
            
        }
    }
    
    private void removeToSelectableIdList(int val) {
        
        if (selectableIdList.contains(val)) {
            
            selectableIdList = ShopUtils.removeElements(selectableIdList, val);
        }
    }
    
    @SuppressLint("SetTextI18n")
    private void getPrice() {
        
        ArrayList<Integer> mainAttributeValues  = new ArrayList<>(attrList);
        ArrayList<Integer> extraAttributeValues = new ArrayList<>(attrExtraList);
        ArrayList<Integer> products             = new ArrayList<>(selectableIdList);
        Collections.sort(products);
        progPrice.setVisibility(View.VISIBLE);
        
        repository.getPrice(type, String.valueOf(model.getId()), products, mainAttributeValues, extraAttributeValues, data -> {
            progPrice.setVisibility(View.GONE);
            if (data instanceof Result.Success) {
                
                GETPriceModel temp = (GETPriceModel) ((Result.Success) data).value;
                
                if (null == temp.getError()) {
                    
                    if (temp.getCost().getMfinal() > 0) {
                        totalPrice = temp.getCost().getMfinal();
                        txtPrice.setText(
                                ShopUtils.formatPrice(temp.getCost().getMfinal()) + " تومان ");
                        
                    } else {
                        
                        txtPrice.setText(ShopUtils.formatPrice(0) + " تومان ");
                    }
                    
                } else {
                    
                    
                    Log.d("Error", temp.getError().getMessage());
                    totalPrice = model.getPrice().getMfinal();
                    txtPrice.setText(
                            ShopUtils.formatPrice(model.getPrice().getMfinal()) + " تومان ");
                }
                
                
            } else {
                
                Log.d("Test", (String) ((Result.Error) data).value);
            }
            
            
        });
        
        
    }
    
    
}

