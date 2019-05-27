package ir.sanatisharif.android.konkur96.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.account.AccountInfo;
import ir.sanatisharif.android.konkur96.api.Models.AddToCardListModel;
import ir.sanatisharif.android.konkur96.api.Models.ProductModel;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.fragment.CardFragment;
import ir.sanatisharif.android.konkur96.handler.Repository;
import ir.sanatisharif.android.konkur96.handler.RepositoryImpl;
import ir.sanatisharif.android.konkur96.handler.Result;
import ir.sanatisharif.android.konkur96.model.ProductType;
import ir.sanatisharif.android.konkur96.model.user.User;
import ir.sanatisharif.android.konkur96.utils.AuthToken;

import static ir.sanatisharif.android.konkur96.activity.MainActivity.addFrg;
import static ir.sanatisharif.android.konkur96.app.AppConstants.ACCOUNT_TYPE;

@SuppressLint("ValidFragment")
public class ZarinPalDialogFragment extends DialogFragment {

    private static final String TAG = "Alaa\\ZarinPalDialogFrg";
    private int finalPrice;

    private TextView txtTitle, txtDesc;
    private ProgressBar progPrice;
    private CardView cardShowCard, cardClose;


    private ProductModel model;
    private int totalPrice;
    private ProductType type;

    private List<Integer> attrList;
    private List<Integer> attrExtraList;
    private List<Integer> selectableIdList;


    private Repository repository;
    private AccountInfo accountInfo;
    private User user;
    Activity activity;

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


        progPrice = v.findViewById(R.id.prog_price);
        txtTitle = v.findViewById(R.id.txt_title);
        txtDesc = v.findViewById(R.id.txt_desc);

        cardShowCard = v.findViewById(R.id.btn_showCard);
        cardClose = v.findViewById(R.id.btn_close);

        repository = new RepositoryImpl(getActivity());
        accountInfo = new AccountInfo(getContext(), getActivity());
        user = accountInfo.getInfo(ACCOUNT_TYPE);

        activity = getActivity();

        addToShopCard();


        cardShowCard.setOnClickListener(view -> {

            addFrg(CardFragment.newInstance(), "CardFragment");
            this.dismiss();
        });

        cardClose.setOnClickListener(view -> this.dismiss());

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        return v;
    }


    private void addToShopCard() {


        ArrayList<Integer> attribute = new ArrayList<>(attrList);
        ArrayList<Integer> products = new ArrayList<>(selectableIdList);
        ArrayList<Integer> extraAttribute = new ArrayList<>(attrExtraList);

        progPrice.setVisibility(View.VISIBLE);


        DialogFragment frg = this;
        AuthToken.getInstant().get(activity, new AuthToken.Callback() {
            @Override
            public void run(@NonNull String token) {
                activity.runOnUiThread(() ->
                        repository.addToShopCard(token, model.getId(), attribute, products, extraAttribute, data -> {
                            progPrice.setVisibility(View.GONE);
                            if (data instanceof Result.Success) {
                                AddToCardListModel temp = (AddToCardListModel) ((Result.Success) data).value;

                                if (null == temp.getError()) {
                                    Toast.makeText(AppConfig.context, frg.getString(R.string.add_to_cart_successfully), Toast.LENGTH_SHORT).show();
                                    addFrg(CardFragment.newInstance(), "CardFragment");
                                    frg.dismiss();
                                } else {

                                    Toast.makeText(AppConfig.context, temp.getError().getMessage(), Toast.LENGTH_SHORT).show();
                                    addFrg(CardFragment.newInstance(), "CardFragment");
                                    frg.dismiss();
                                }

                            } else {
                                Log.d("Test", (String) ((Result.Error) data).value);
                                Toast.makeText(AppConfig.context, frg.getString(R.string.try_again), Toast.LENGTH_LONG).show();
                                frg.dismiss();
                            }
                        }));

            }

            @Override
            public void nill() {

            }
        });

    }


}
