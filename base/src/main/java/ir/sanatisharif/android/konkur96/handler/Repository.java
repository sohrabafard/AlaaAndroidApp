package ir.sanatisharif.android.konkur96.handler;

import java.util.ArrayList;

import io.reactivex.annotations.Nullable;
import ir.sanatisharif.android.konkur96.api.Models.PaymentRequest;
import ir.sanatisharif.android.konkur96.api.Models.PaymentVerificationRequest;
import ir.sanatisharif.android.konkur96.model.ProductType;

public interface Repository {

    void getMainShop(ApiCallBack callBack);
    void getNextPage(String url, ApiCallBack callBack);
    void getNextPageProduct(String url, ApiCallBack callBack);
    void getMore(String url, ApiCallBack callBack);
    void getPrice(ProductType type, String productId, ArrayList<Integer> products, ArrayList<Integer> mainAttributeValues, ArrayList<Integer> extraAttributeValues, ApiCallBack callBack);
    void paymentRequest(PaymentRequest body, ApiCallBack callBack);
    void paymentVerification(PaymentVerificationRequest body, ApiCallBack callBack);
    void addToShopCard(String token, int productId, @Nullable ArrayList<Integer> attribute, @Nullable ArrayList<Integer> products, @Nullable ArrayList<Integer> extraAttribute, ApiCallBack callBack);
}
