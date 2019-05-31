package ir.sanatisharif.android.konkur96.api;

import java.util.List;

import io.reactivex.Observable;
import ir.sanatisharif.android.konkur96.api.Models.ContentModel;
import ir.sanatisharif.android.konkur96.api.Models.MainModel;
import ir.sanatisharif.android.konkur96.model.filter.Filter;
import ir.sanatisharif.android.konkur96.model.main_page.lastVersion.LastVersion;
import ir.sanatisharif.android.konkur96.model.user.User;
import ir.sanatisharif.android.konkur96.model.user.UserInfo;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Mohamad on 12/26/2018.
 */

public interface MainApi {

    @Headers({
            "ContentModel-Type: application/json",
            "Accept: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @GET("/")
    Observable<MainModel> getMainPage();

    // @GET("c/{id}")
    //  Call<ContentModel> getContent(@Path("id") String id);
    @Headers({
            "ContentModel-Type: application/json",
            "Accept: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @GET
    Observable<ContentModel> getContent(@Url String url, @Header("Authorization") String token);

    @Headers({
            "ContentModel-Type: application/json",
            "Accept: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @POST("api/login")
    Observable<UserInfo> getLoginUserInfo(@Body User user);

    /**
     * contentOnly
     * c?set=191&contentOnly=1
     */
    @Headers({
            "ContentModel-Type: application/json",
            "Accept: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @GET("c")
    Observable<Filter> getContentOnly(
            @Query("set") String id,
            @Query("contentOnly") int contentOnly);

    @Headers({
            "ContentModel-Type: application/json",
            "Accept: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @GET("c")
    Observable<Filter> getFilterBySearch(
            @Query("search") String search);

    @Headers({
            "ContentModel-Type: application/json",
            "Accept: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @GET("c")
    Observable<Filter> getFilterTags(@Query("tags[]") List<String> tags);

    @Headers({
            "ContentModel-Type: application/json",
            "Accept: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @GET
    Observable<Filter> getFilterTagsByUrl(@Url String url);

    @Headers({
            "ContentModel-Type: application/json",
            "Accept: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @POST("/api/v1/user/{user_id}/firebasetoken")
    Observable<Object> sendFirebaseToken(
            @Path("user_id") int user_id,
            @Query("token") String firebaseToken,
            @Header("Authorization") String token);


    @Headers({
            "ContentModel-Type: application/json",
            "Accept: application/json",
            "X-Requested-With: XMLHttpRequest"
    })
    @GET("api/v1/lastVersion")
    Observable<LastVersion> getLastVersion();

}
