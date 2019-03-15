package ir.sanatisharif.android.konkur96.dialog;

import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.Nullable;
import ir.sanatisharif.android.konkur96.BuildConfig;
import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.account.AccountInfo;
import ir.sanatisharif.android.konkur96.adapter.ProductAttrAdapter;
import ir.sanatisharif.android.konkur96.api.Models.AttributeModel;
import ir.sanatisharif.android.konkur96.api.Models.GETPriceModel;
import ir.sanatisharif.android.konkur96.api.Models.MainModel;
import ir.sanatisharif.android.konkur96.api.Models.PaymentRequest;
import ir.sanatisharif.android.konkur96.api.Models.PaymentResponse;
import ir.sanatisharif.android.konkur96.api.Models.ProductModel;
import ir.sanatisharif.android.konkur96.fragment.DashboardMainFrg;
import ir.sanatisharif.android.konkur96.handler.Repository;
import ir.sanatisharif.android.konkur96.handler.RepositoryImpl;
import ir.sanatisharif.android.konkur96.handler.Result;
import ir.sanatisharif.android.konkur96.model.ProductType;
import ir.sanatisharif.android.konkur96.model.user.User;
import ir.sanatisharif.android.konkur96.utils.ShopUtils;

import static ir.sanatisharif.android.konkur96.app.AppConstants.ACCOUNT_TYPE;

@SuppressLint("ValidFragment")
public class ZarinPalDialogFragment extends DialogFragment {

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



        addToShopCard();



        cardShowCard.setOnClickListener(view -> {


        });

        cardClose.setOnClickListener(view -> this.dismiss());

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        return v;
    }


    private void addToShopCard(){

        ArrayList<Integer> attribute = new ArrayList<>(attrList);
        ArrayList<Integer> products = new ArrayList<>(selectableIdList);
        ArrayList<Integer> extraAttribute = new ArrayList<>(attrExtraList);

        repository = new RepositoryImpl(getActivity());
        accountInfo = new AccountInfo(getContext(), getActivity());
        user = accountInfo.getInfo(ACCOUNT_TYPE);


        progPrice.setVisibility(View.VISIBLE);

        if (accountInfo.ExistAccount(ACCOUNT_TYPE)) {

//            accountInfo.getExistingAccountAuthToken(ACCOUNT_TYPE, "Alla");

            repository.addToShopCard("", model.getId(), attribute, products, extraAttribute, data -> {
                progPrice.setVisibility(View.GONE);
                if (data instanceof Result.Success) {

                    GETPriceModel temp = (GETPriceModel) ((Result.Success) data).value;


                } else {

                    Log.d("Test", (String) ((Result.Error) data).value);
                }


            });
        }

    }


    private void openZarinPal(){

        if (accountInfo.ExistAccount(ACCOUNT_TYPE)) {

            String uMobile = "نامشخص";

            if (user.getMobile() != null){

                uMobile = user.getMobile();
            }

            String desc = "اپ آلاء" + " - " + "نسخه" + String.valueOf(BuildConfig.VERSION_CODE) +

                    " - " + uMobile + " - " + "محصولات: " + model.getName();

            PaymentRequest body = new PaymentRequest("55eb1362-08d4-42ee-8c74-4c5f5bef37d4",
                    finalPrice,
                    "alla://sanatisharif.ir/zarinpal/?a=" + String.valueOf(finalPrice),
                    desc);

            repository.paymentRequest(body, data -> {

                if (data instanceof Result.Success) {

                    PaymentResponse payment = (PaymentResponse) ((Result.Success) data).value;

                    if (payment.getStatus() == 100){

                        String url = "https://www.zarinpal.com/pg/StartPay/" + payment.getAuthority() + "/MobileGate";

                        openWebView(url);

                    }else {

                        Toast.makeText(getContext(), "خطا : " + String.valueOf(payment.getStatus()) , Toast.LENGTH_LONG).show();
                    }

                } else {

                    Toast.makeText(getContext(), "خطایی رخ داده لطفا دوباره امتحان کنید." , Toast.LENGTH_LONG).show();
                }


            });
        }

    }
    private void openWebView(String url){

        try {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            @SuppressLint("WrongConstant") ResolveInfo resolvable = getActivity().getPackageManager().resolveActivity(intent, PackageManager.GET_INTENT_FILTERS);
            if (resolvable != null) {
                startActivity(intent);
            }
        } catch (NullPointerException e) {
            Log.e("Alla", e.getMessage(), e);
        }
    }
}
