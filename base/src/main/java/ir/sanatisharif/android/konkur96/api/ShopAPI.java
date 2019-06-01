package ir.sanatisharif.android.konkur96.api;

import java.util.ArrayList;

import io.reactivex.Observable;
import ir.sanatisharif.android.konkur96.api.Models.AddToCardListModel;
import ir.sanatisharif.android.konkur96.api.Models.CardReviewModel;
import ir.sanatisharif.android.konkur96.api.Models.ErrorBase;
import ir.sanatisharif.android.konkur96.api.Models.GETPriceModel;
import ir.sanatisharif.android.konkur96.api.Models.MainModel;
import ir.sanatisharif.android.konkur96.api.Models.PaymentUrlModel;
import ir.sanatisharif.android.konkur96.api.Models.ProductIndexResultModel;
import ir.sanatisharif.android.konkur96.api.Models.ProductModel;
import ir.sanatisharif.android.konkur96.api.Models.myProductsModel;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ShopAPI {
    
    //String BASE_URL = "http://79.127.123.246:8080/";
    
    @Headers({
            "ContentModel-Type: application/json",
            "Accept: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @GET("shop")
    Observable<MainModel> getMain();
    
    @Headers({
            "ContentModel-Type: application/json",
            "Accept: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @GET
    Observable<MainModel> getPagination(@Url String url);
    
    
    @Headers({
            "ContentModel-Type: application/json",
            "Accept: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @GET
    Observable<ProductIndexResultModel> getPaginationProduct(@Url String url);
    
    @Headers({
            "ContentModel-Type: application/json",
            "Accept: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @GET
    Observable<ProductIndexResultModel> getMore(@Url String url);
    
    @Headers({
            "ContentModel-Type: application/json",
            "Accept: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @POST("api/v1/getPrice/{product_id}")
    Observable<GETPriceModel> getPrice(@Path("product_id") String productId, @Query("mainAttributeValues[]") ArrayList<Integer> mainAttributeValues, @Query("extraAttributeValues[]") ArrayList<Integer> extraAttributeValues);
    
    @Headers({
            "ContentModel-Type: application/json",
            "Accept: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @POST("api/v1/getPrice/{product_id}")
    Observable<GETPriceModel> getPriceSelectable(@Path("product_id") String productId, @Query("products[]") ArrayList<Integer> mainAttributeValues, @Query("extraAttributeValues[]") ArrayList<Integer> extraAttributeValues);
    
    
    @Headers({
            "ContentModel-Type: application/json",
            "Accept: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @POST("api/v1/orderproduct")
    Observable<AddToCardListModel> addToShopCard(@Header("Authorization") String token,
                                                 @Query("product_id") int productId,
                                                 @Query("attribute[]") ArrayList<Integer> attribute,
                                                 @Query("products[]") ArrayList<Integer> products,
                                                 @Query("extraAttribute[]") ArrayList<Integer> extraAttribute);
    
    
    @Headers({
            "ContentModel-Type: application/json",
            "Accept: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @GET("api/v1/checkout/review")
    Observable<CardReviewModel> cardReview(@Header("Authorization") String token);
    
    
    @Headers({
            "ContentModel-Type: application/json",
            "Accept: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @POST("api/v1/transaction")
    Observable<ErrorBase> notifyTransaction(@Header("Authorization") String token,
                                            @Query("cost") String cost,
                                            @Query("authority") String authority,
                                            @Query("refId") String refId);
    
    @Headers({
            "ContentModel-Type: application/json",
            "Accept: application/json"
    })
    @GET("api/v1/user/{user_id}/dashboard")
    Observable<myProductsModel> getDashboard(@Header("Authorization") String token, @Path("user_id") String userId);
    
    @Headers({
            "ContentModel-Type: application/json",
            "Accept: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @POST("api/v1/orderproduct/{orderproduct_id}")
    Observable<ErrorBase> delProductFromCard(@Header("Authorization") String token,
                                             @Path("orderproduct_id") String orderproductId,
                                             @Query("_method") String _method);
    
    @Headers({
            "ContentModel-Type: application/json",
            "Accept: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @GET("api/v1/getPaymentRedirectEncryptedLink")
    Observable<PaymentUrlModel> getPaymentUrl(@Header("Authorization") String token);
    
    @Headers({
            "ContentModel-Type: application/json",
            "Accept: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @GET
    Observable<ProductModel> getProductByUrl(@Url String url, @Header("Authorization") String token);
}
