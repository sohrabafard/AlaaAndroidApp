package ir.sanatisharif.android.konkur96.api;

import io.reactivex.Observable;
import ir.sanatisharif.android.konkur96.api.Models.MainModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;

public interface ShopAPI {

    String BASE_URL = "http://79.127.123.246:8080/";

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Requested-With: XMLHttpRequest"})
    @GET("shop")
    Observable<MainModel> getMain();

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Requested-With: XMLHttpRequest"})
    @GET
    Call<MainModel> getPagination(@Url String url);
}
