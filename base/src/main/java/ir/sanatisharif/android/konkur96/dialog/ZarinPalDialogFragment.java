package ir.sanatisharif.android.konkur96.dialog;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.account.AccountInfo;
import ir.sanatisharif.android.konkur96.adapter.ProductAttrAdapter;
import ir.sanatisharif.android.konkur96.api.Models.AttributeModel;
import ir.sanatisharif.android.konkur96.api.Models.GETPriceModel;
import ir.sanatisharif.android.konkur96.api.Models.ProductModel;
import ir.sanatisharif.android.konkur96.fragment.DashboardMainFrg;
import ir.sanatisharif.android.konkur96.handler.Repository;
import ir.sanatisharif.android.konkur96.handler.RepositoryImpl;
import ir.sanatisharif.android.konkur96.handler.Result;
import ir.sanatisharif.android.konkur96.model.ProductType;
import ir.sanatisharif.android.konkur96.utils.ShopUtils;

import static ir.sanatisharif.android.konkur96.app.AppConstants.ACCOUNT_TYPE;

@SuppressLint("ValidFragment")
public class ZarinPalDialogFragment extends DialogFragment {

    private int finalPrice;

    private TextView txtTotalPrice;
    private CardView btnGoToZarinPal;
    private ProgressBar progPrice;


    private ProductModel model;
    private int totalPrice;
    private ProductType type;

    private List<Integer> attrList;
    private List<Integer> attrExtraList;
    private List<Integer> selectableIdList;


    private Repository repository;
    private AccountInfo accountInfo;


    @SuppressLint("ValidFragment")
    public ZarinPalDialogFragment(ProductType type, ProductModel model, int totalPrice,
                                  List<Integer> selectableIdList, List<Integer> attrList, List<Integer> attrExtraList) {

        this.model = model;
        this.totalPrice = totalPrice;
        this.type = type;

        this.selectableIdList = selectableIdList;
        this.attrList = attrList;
        this.attrExtraList = attrExtraList;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialog_view_zarinpal, container, false);

        txtTotalPrice = v.findViewById(R.id.txt_total_price);
        btnGoToZarinPal = v.findViewById(R.id.btn_goToZarinPal);
        progPrice = v.findViewById(R.id.prog_price);

        setPrice();
        getPrice();

        btnGoToZarinPal.setOnClickListener(view -> {

            if (finalPrice > 0){

                 openZarinPal();

            }


        });

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        return v;
    }


    @SuppressLint("SetTextI18n")
    private void setPrice() {


        if (totalPrice> 0) {

            txtTotalPrice.setText(ShopUtils.formatPrice(totalPrice) + " تومان ");

        } else {

            txtTotalPrice.setText(ShopUtils.formatPrice(0) + " تومان ");
        }
    }


    @SuppressLint("SetTextI18n")
    private void getPrice() {

        ArrayList<Integer> mainAttributeValues = new ArrayList<>(attrList);
        ArrayList<Integer> extraAttributeValues = new ArrayList<>(attrExtraList);
        ArrayList<Integer> products = new ArrayList<>(selectableIdList);
        progPrice.setVisibility(View.VISIBLE);

        repository = new RepositoryImpl(getActivity());

        repository.getPrice(type, String.valueOf(model.getId()), products, mainAttributeValues, extraAttributeValues, data -> {
            progPrice.setVisibility(View.GONE);
            if (data instanceof Result.Success) {

                GETPriceModel temp = (GETPriceModel) ((Result.Success) data).value;

                if (null == temp.getError()){

                    if (temp.getCost().getMfinal() > 0) {
                        finalPrice = temp.getCost().getMfinal();
                        txtTotalPrice.setText(ShopUtils.formatPrice(temp.getCost().getMfinal()) + " تومان ");

                    } else {

                        txtTotalPrice.setText(ShopUtils.formatPrice(0) + " تومان ");
                    }

                }else {

                    Log.d("Error", temp.getError().getMessage());
                    txtTotalPrice.setText(ShopUtils.formatPrice(totalPrice) + " تومان ");

                }


            } else {

                Log.d("Test", (String) ((Result.Error) data).value);
            }


        });


    }

    private void openZarinPal(){

        accountInfo = new AccountInfo(getContext(), getActivity());

        if (accountInfo.ExistAccount(ACCOUNT_TYPE)) {

              Log.e("Test","zarin pal");
        }

    }
}
