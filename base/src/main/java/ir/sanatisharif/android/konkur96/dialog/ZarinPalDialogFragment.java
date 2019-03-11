package ir.sanatisharif.android.konkur96.dialog;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.adapter.ProductAttrAdapter;
import ir.sanatisharif.android.konkur96.api.Models.AttributeModel;
import ir.sanatisharif.android.konkur96.api.Models.ProductModel;
import ir.sanatisharif.android.konkur96.utils.ShopUtils;

@SuppressLint("ValidFragment")
public class ZarinPalDialogFragment extends DialogFragment {



    private TextView txtTotalPrice;
    private CardView btnGoToZarinPal;


    private ProductModel model;
    private int totalPrice;


    @SuppressLint("ValidFragment")
    public ZarinPalDialogFragment(ProductModel model, int totalPrice) {

        this.model = model;
        this.totalPrice = totalPrice;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialog_view_zarinpal, container, false);

        txtTotalPrice = v.findViewById(R.id.txt_total_price);
        btnGoToZarinPal = v.findViewById(R.id.btn_goToZarinPal);

        setPrice();

        btnGoToZarinPal.setOnClickListener(view -> {


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
}
