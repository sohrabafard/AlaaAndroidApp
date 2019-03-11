package ir.sanatisharif.android.konkur96.dialog;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.adapter.ProductAttrAdapter;
import ir.sanatisharif.android.konkur96.api.Models.AttributeDataModel;
import ir.sanatisharif.android.konkur96.api.Models.AttributeModel;
import ir.sanatisharif.android.konkur96.api.Models.GETPriceModel;
import ir.sanatisharif.android.konkur96.api.Models.ProductModel;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.handler.Repository;
import ir.sanatisharif.android.konkur96.handler.RepositoryImpl;
import ir.sanatisharif.android.konkur96.handler.Result;
import ir.sanatisharif.android.konkur96.model.MainAttrType;
import ir.sanatisharif.android.konkur96.model.ProductType;
import ir.sanatisharif.android.konkur96.utils.ShopUtils;

@SuppressLint("ValidFragment")
public class ProductExtraAttrDialogFragment extends DialogFragment {

    private ProductType type;

    private List<Integer> attrList;
    private List<Integer> attrExtraList = new ArrayList<>();
    private int id, totalPrice;
    private ArrayList<AttributeModel> extraAttrList;
    private List<Integer> selectableIdList;
    private ArrayList<ProductModel> selectableList;

    private LinearLayout bodyExtraAttr;
    private ProgressBar progPrice;
    private CardView btnAddToCard;
    private TextView txtPrice;

    private Repository repository;

    private ProductModel model;


    @SuppressLint("ValidFragment")
    public ProductExtraAttrDialogFragment(ProductType type, int id, int totalPrice,
                                          List<Integer> attrList,
                                          ArrayList<AttributeModel> extraAttrList,
                                          List<Integer> selectableIdList,
                                          ArrayList<ProductModel> selectableList,
                                          ProductModel model) {

        this.type = type;
        this.id = id;
        this.totalPrice = totalPrice;
        this.attrList = attrList;
        this.extraAttrList = extraAttrList;
        this.selectableIdList = selectableIdList;
        this.selectableList = selectableList;
        this.model = model;


    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialog_view_extra_attr, container, false);

        bodyExtraAttr = v.findViewById(R.id.body_extra_attr);
        progPrice = v.findViewById(R.id.prog_price);
        txtPrice = v.findViewById(R.id.txt_price);
        btnAddToCard = v.findViewById(R.id.btn_addToCard);

        if (totalPrice > 0) {

            txtPrice.setText(ShopUtils.formatPrice(totalPrice) + " تومان ");

        } else {

            txtPrice.setText(ShopUtils.formatPrice(0) + " تومان ");
        }


        btnAddToCard.setOnClickListener(view -> btnAddClick());

        for (AttributeModel attr : extraAttrList){

            MainAttrType type = ShopUtils.getMainAttrType(attr);

            if (type == MainAttrType.SIMPLE){

                createSimpleAttr(attr);

            }else if (type == MainAttrType.CHECKBOX){

                createCheckBoxAttr(attr);

            }else if (type == MainAttrType.DROPDOWN){

                createDropDownAttr(attr);
            }
        }

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        return v;
    }


    private void btnAddClick(){


        if (type == ProductType.CONFIGURABLE){

            if (attrList.size() > 0){

                showZarinPalDialog();

            }else {

                Toast.makeText(getContext(),"لطفا یک مورد را انتخاب کنید", Toast.LENGTH_LONG).show();
            }
        }else if (type == ProductType.SELECTABLE){

            if (selectableIdList.size() > 0 ){

                showZarinPalDialog();

            }else {

                Toast.makeText(getContext(),"لطفا یک مورد را انتخاب کنید", Toast.LENGTH_LONG).show();
            }
        }else if (type == ProductType.SIMPLE){

            showZarinPalDialog();
        }
    }


    @SuppressLint("SetTextI18n")
    private void createSimpleAttr(AttributeModel attr){

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

        bodyExtraAttr.addView(textView);
    }

    private void createCheckBoxAttr(AttributeModel attr){

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                100);

        if (null != attr.getTitle() && !attr.getTitle().isEmpty()){

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
                if (check){

                    addToAttrList((int) compoundButton.getTag());

                }else {

                    removeToAttrList((int) compoundButton.getTag());
                }

                getPrice();
            });
            bodyExtraAttr.addView(checkBox);
        }
    }

    private void createDropDownAttr(AttributeModel attr){

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                100);

        if (null != attr.getTitle() && !attr.getTitle().isEmpty()){

            createTxtTitle(attr.getTitle());
        }

        List<String> spinnerArray = new ArrayList<>();
        @SuppressLint("UseSparseArrays")
        HashMap<String, Integer> spinnerMap = new HashMap<>();

        Spinner spinner = new Spinner(getContext());
        spinner.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        spinner.setLayoutParams(params);
        spinner.setPadding(15, 5, 15, 5);
        spinnerArray.add("انتخاب کنید");
        spinnerMap.put("انتخاب کنید",-1);
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

        bodyExtraAttr.addView(spinner);

    }

    @SuppressLint("SetTextI18n")
    private void createTxtTitle(String title){

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

        bodyExtraAttr.addView(textView);
    }

    private void addToAttrList(int val){

        if (!attrExtraList.contains(val)) {

            attrExtraList.add(val);
        }
    }

    private void removeToAttrList(int val){

        if (attrExtraList.contains(val)) {

            attrExtraList = ShopUtils.removeElements(attrExtraList, val);
        }
    }


    private void showZarinPalDialog() {

        FragmentManager fm = getFragmentManager();
        DialogFragment newFragment = new ZarinPalDialogFragment(model, totalPrice);

        newFragment.show(fm, "ZarinPalDialog");

    }


    @SuppressLint("SetTextI18n")
    private void getPrice() {

        ArrayList<Integer> mainAttributeValues = new ArrayList<>(attrList);
        ArrayList<Integer> extraAttributeValues = new ArrayList<>(attrExtraList);
        ArrayList<Integer> products = new ArrayList<>(selectableIdList);
        progPrice.setVisibility(View.VISIBLE);

        repository = new RepositoryImpl(getActivity());

        repository.getPrice(type,String.valueOf(id), products, mainAttributeValues, extraAttributeValues, data -> {
            progPrice.setVisibility(View.GONE);
            if (data instanceof Result.Success) {

                GETPriceModel temp = (GETPriceModel) ((Result.Success) data).value;

                if (null == temp.getError()){

                    if (temp.getCost().getMfinal() > 0) {
                        totalPrice = temp.getCost().getMfinal();
                        txtPrice.setText(ShopUtils.formatPrice(temp.getCost().getMfinal()) + " تومان ");

                    } else {

                        txtPrice.setText(ShopUtils.formatPrice(0) + " تومان ");
                    }

                }else {

                    Log.d("Error", temp.getError().getMessage());
                    txtPrice.setText(ShopUtils.formatPrice(totalPrice) + " تومان ");

                }


            } else {

                Log.d("Test", (String) ((Result.Error) data).value);
            }


        });


    }
}
