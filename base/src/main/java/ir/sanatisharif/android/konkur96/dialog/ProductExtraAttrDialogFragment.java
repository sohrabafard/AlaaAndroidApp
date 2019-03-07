package ir.sanatisharif.android.konkur96.dialog;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
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
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.handler.Repository;
import ir.sanatisharif.android.konkur96.handler.RepositoryImpl;
import ir.sanatisharif.android.konkur96.handler.Result;
import ir.sanatisharif.android.konkur96.model.MainAttrType;
import ir.sanatisharif.android.konkur96.utils.ShopUtils;

@SuppressLint("ValidFragment")
public class ProductExtraAttrDialogFragment extends DialogFragment {

    private List<Integer> attrList;
    private List<Integer> attrExtraList = new ArrayList<>();
    private int id;
    ArrayList<AttributeModel> extraAttrList;

    private LinearLayout bodyExtraAttr;
    private Repository repository;

    @SuppressLint("ValidFragment")
    public ProductExtraAttrDialogFragment(int id, List<Integer> attrList, ArrayList<AttributeModel> extraAttrList) {

        this.id = id;
        this.attrList = attrList;
        this.extraAttrList = extraAttrList;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialog_view_extra_attr, container, false);

        bodyExtraAttr = v.findViewById(R.id.body_extra_attr);

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

        bodyExtraAttr.addView(textView);
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
            bodyExtraAttr.addView(checkBox);
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

        bodyExtraAttr.addView(spinner);

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

    @SuppressLint("SetTextI18n")
    private void getPrice() {

        ArrayList<Integer> mainAttributeValues = new ArrayList<>(attrList);
        ArrayList<Integer> extraAttributeValues = new ArrayList<>(attrExtraList);
//        progPrice.setVisibility(View.VISIBLE);

        repository = new RepositoryImpl(getActivity());

        repository.getPrice(String.valueOf(id), mainAttributeValues, extraAttributeValues, data -> {
//            progPrice.setVisibility(View.GONE);
            if (data instanceof Result.Success) {

                GETPriceModel temp = (GETPriceModel) ((Result.Success) data).value;

                if (null == temp.getError()){

                    if (temp.getCost().getMfinal() > 0) {

//                        txtPrice.setText(ShopUtils.formatPrice(temp.getCost().getMfinal()) + " تومان ");

                    } else {

//                        txtPrice.setText(ShopUtils.formatPrice(0) + " تومان ");
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
