package ir.sanatisharif.android.konkur96.handler;

import android.annotation.SuppressLint;
import android.app.Activity;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ir.sanatisharif.android.konkur96.api.Models.MainModel;
import ir.sanatisharif.android.konkur96.api.Models.PaymentVerificationRequest;
import ir.sanatisharif.android.konkur96.api.Models.ProductModel;
import ir.sanatisharif.android.konkur96.api.ShopAPI;
import ir.sanatisharif.android.konkur96.api.ZarinPalAPI;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.model.ProductType;

public class RepositoryImpl implements Repository {

    @Inject
    ShopAPI shopAPI;

    @Inject
    ZarinPalAPI zarinPalAPI;

    public RepositoryImpl(Activity activity) {

        ((AppConfig) activity.getApplication()).getAppComponent().inject(this);
    }

    @SuppressLint("CheckResult")
    @Override
    public void getMainShop(ApiCallBack callBack) {

        shopAPI.getMain()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MainModel>() {
                               @Override
                               public void accept(MainModel mainModel) throws Exception {
                                   callBack.onResponse(new Result.Success(mainModel));
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                callBack.onResponse(new Result.Error(throwable.getMessage()));
                            }
                        });
    }

    @SuppressLint("CheckResult")
    @Override
    public void getNextPage(String url, ApiCallBack callBack) {

        shopAPI.getPagination(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mainModel -> callBack.onResponse(new Result.Success(mainModel)),
                        throwable -> callBack.onResponse(new Result.Error(throwable.getMessage())));

    }

    @SuppressLint("CheckResult")
    @Override
    public void getNextPageProduct(String url, ApiCallBack callBack) {

        shopAPI.getPaginationProduct(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ResultModel -> callBack.onResponse(new Result.Success(ResultModel)),
                        throwable -> callBack.onResponse(new Result.Error(throwable.getMessage())));

    }


    @SuppressLint("CheckResult")
    @Override
    public void getMore(String url, ApiCallBack callBack) {

        shopAPI.getMore(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ResultModel -> callBack.onResponse(new Result.Success(ResultModel)),
                        throwable -> callBack.onResponse(new Result.Error(throwable.getMessage())));

    }

    @SuppressLint("CheckResult")
    @Override
    public void getPrice(ProductType type, String productId, ArrayList<Integer> products, ArrayList<Integer> mainAttributeValues, ArrayList<Integer> extraAttributeValues, ApiCallBack callBack) {

        if (type == ProductType.CONFIGURABLE) {

            shopAPI.getPrice(productId, mainAttributeValues, extraAttributeValues)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(GETPriceModel -> callBack.onResponse(new Result.Success(GETPriceModel)),
                            throwable -> callBack.onResponse(new Result.Error(throwable.getMessage())));

        } else if (type == ProductType.SELECTABLE) {

            shopAPI.getPriceSelectable(productId, products, extraAttributeValues)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(GETPriceModel -> callBack.onResponse(new Result.Success(GETPriceModel)),
                            throwable -> callBack.onResponse(new Result.Error(throwable.getMessage())));
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public void paymentVerification(PaymentVerificationRequest body, ApiCallBack callBack) {

        zarinPalAPI.paymentVerification(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ResultModel -> callBack.onResponse(new Result.Success(ResultModel)),
                        throwable -> callBack.onResponse(new Result.Error(throwable.getMessage())));

    }


    @SuppressLint("CheckResult")
    @Override
    public void addToShopCard(String token, int productId,
                              ArrayList<Integer> attribute,
                              ArrayList<Integer> products,
                              ArrayList<Integer> extraAttribute,
                              ApiCallBack callBack) {

        shopAPI.addToShopCard(("Bearer " + token), productId, attribute, products, extraAttribute)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(AddToCardListModel -> callBack.onResponse(new Result.Success(AddToCardListModel)),
                        throwable -> callBack.onResponse(new Result.Error(throwable.getMessage())));

    }


    @SuppressLint("CheckResult")
    @Override
    public void cardReview(String token, ApiCallBack callBack) {

        shopAPI.cardReview(("Bearer " + token))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(CardReviewModel -> callBack.onResponse(new Result.Success(CardReviewModel)),
                        throwable -> callBack.onResponse(new Result.Error(throwable.getMessage())));

    }

    @SuppressLint("CheckResult")
    @Override
    public void notifyTransaction(String token, String cost, String authority, String refId, ApiCallBack callBack) {

        shopAPI.notifyTransaction(("Bearer " + token), cost, authority, refId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ErrorBase -> callBack.onResponse(new Result.Success(ErrorBase)),
                        throwable -> callBack.onResponse(new Result.Error(throwable.getMessage())));

    }


    @SuppressLint("CheckResult")
    @Override
    public void getDashboard(String token, String userId, ApiCallBack callBack) {

        shopAPI.getDashboard(("Bearer " + token), userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(myProductsModel -> callBack.onResponse(new Result.Success(myProductsModel)),
                        throwable -> callBack.onResponse(new Result.Error(throwable.getMessage())));

    }


    @SuppressLint("CheckResult")
    @Override
    public void delProductFromCard(String token, String orderproductId, ApiCallBack callBack) {

        shopAPI.delProductFromCard(("Bearer " + token), orderproductId, "delete")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ErrorBase -> callBack.onResponse(new Result.Success(ErrorBase)),
                        throwable -> callBack.onResponse(new Result.Error(throwable.getMessage())));

    }


    @SuppressLint("CheckResult")
    @Override
    public void getPaymentUrl(String token, ApiCallBack callBack) {

        shopAPI.getPaymentUrl(("Bearer " + token))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(PaymentUrlModel -> callBack.onResponse(new Result.Success(PaymentUrlModel)),
                        throwable -> callBack.onResponse(new Result.Error(throwable.getMessage())));

    }

    @SuppressLint("CheckResult")
    @Override
    public void getProductByUrl(String url, String token, ApiCallBack callBack) {

        shopAPI.getProductByUrl(url, ("Bearer " + token))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ProductModel>() {
                               @Override
                               public void accept(ProductModel ProductModel) throws Exception {
                                   callBack.onResponse(new Result.Success(ProductModel));
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                callBack.onResponse(new Result.Error(throwable.getMessage()));
                            }
                        });
    }

}
