package ir.sanatisharif.android.konkur96.api;

import java.util.ArrayList;

import io.reactivex.Observable;
import ir.sanatisharif.android.konkur96.api.Models.AddToCardListModel;
import ir.sanatisharif.android.konkur96.api.Models.AddToCardModel;
import ir.sanatisharif.android.konkur96.api.Models.CardReviewModel;
import ir.sanatisharif.android.konkur96.api.Models.GETPriceModel;
import ir.sanatisharif.android.konkur96.api.Models.MainModel;
import ir.sanatisharif.android.konkur96.api.Models.ResultModel;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ShopAPI {

    String BASE_URL = "http://79.127.123.246:8080/";

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Requested-With: XMLHttpRequest"})
    @GET("shop")
    Observable<MainModel> getMain();

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Requested-With: XMLHttpRequest"})
    @GET
    Observable<MainModel> getPagination(@Url String url);


    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Requested-With: XMLHttpRequest"})
    @GET
    Observable<ResultModel> getPaginationProduct(@Url String url);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Requested-With: XMLHttpRequest"})
    @GET
    Observable<ResultModel> getMore(@Url String url);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Requested-With: XMLHttpRequest"})
    @POST("api/v1/getPrice/{product_id}")
    Observable<GETPriceModel> getPrice(@Path("product_id") String productId, @Query("mainAttributeValues[]") ArrayList<Integer> mainAttributeValues, @Query("extraAttributeValues[]") ArrayList<Integer> extraAttributeValues);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Requested-With: XMLHttpRequest"})
    @POST("api/v1/getPrice/{product_id}")
    Observable<GETPriceModel> getPriceSelectable(@Path("product_id") String productId, @Query("products[]") ArrayList<Integer> mainAttributeValues, @Query("extraAttributeValues[]") ArrayList<Integer> extraAttributeValues);


    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Requested-With: XMLHttpRequest"})
    @POST("api/v1/orderproduct")
    Observable<AddToCardListModel> addToShopCard(@Header("Authorization") String token,
                                                 @Query("product_id") int productId,
                                                 @Query("attribute[]") ArrayList<Integer> attribute,
                                                 @Query("products[]") ArrayList<Integer> products,
                                                 @Query("extraAttribute[]") ArrayList<Integer> extraAttribute);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Requested-With: XMLHttpRequest"})
    @GET("api/v1/checkout/review")
    Observable<CardReviewModel> cardReview(@Header("Authorization") String token);
}
