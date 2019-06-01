package ir.sanatisharif.android.konkur96.handler;

import java.util.ArrayList;

import io.reactivex.annotations.Nullable;
import ir.sanatisharif.android.konkur96.api.Models.PaymentVerificationRequest;
import ir.sanatisharif.android.konkur96.model.ProductType;

public interface Repository {
    
    void getMainShop(ApiCallBack callBack);
    
    void getNextPage(String url, ApiCallBack callBack);
    
    void getNextPageProduct(String url, ApiCallBack callBack);
    
    void getMore(String url, ApiCallBack callBack);
    
    void getPrice(ProductType type, String productId, ArrayList<Integer> products, ArrayList<Integer> mainAttributeValues, ArrayList<Integer> extraAttributeValues, ApiCallBack callBack);
    
    void paymentVerification(PaymentVerificationRequest body, ApiCallBack callBack);
    
    void addToShopCard(String token, int productId, @Nullable ArrayList<Integer> attribute, @Nullable ArrayList<Integer> products, @Nullable ArrayList<Integer> extraAttribute, ApiCallBack callBack);
    
    void cardReview(String token, ApiCallBack callBack);
    
    void notifyTransaction(String token, String cost, String authority, String refId, ApiCallBack callBack);
    
    void getDashboard(String token, String userId, ApiCallBack callBack);
    
    void delProductFromCard(String token, String orderproductId, ApiCallBack callBack);
    
    void getPaymentUrl(String token, ApiCallBack callBack);
    
    void getProductByUrl(String url, String token, ApiCallBack callBack);
    
}
