package ir.sanatisharif.android.konkur96.api;

import java.util.List;

import io.reactivex.Observable;
import ir.sanatisharif.android.konkur96.model.DataCourse;
import ir.sanatisharif.android.konkur96.model.filter.Filter;
import ir.sanatisharif.android.konkur96.model.main_page.MainPagesInfo;
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

  /*
   EX: www.app.net/api/searchtypes/862189/filters?Type=6&SearchText=School

   @GET("/api/searchtypes/{Id}/filters")
    Call<FilterResponse> getFilterList(
        @Path("Id") long customerId,
        @Query("Type") String responseType,
        @Query("SearchText") String searchText);*/


    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Requested-With: XMLHttpRequest"})
    @GET(" ")
    Observable<MainPagesInfo> getMainPage();

    // @GET("c/{id}")
    //  Call<DataCourse> getDetailsCourseByURL(@Path("id") String id);
    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Requested-With: XMLHttpRequest"})
    @GET
    Observable<DataCourse> getDetailsCourseByURL(@Url String url, @Header("Authorization") String token);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Requested-With: XMLHttpRequest"})
    @POST("api/login")
    Observable<UserInfo> getLoginUserInfo(@Body User user);

    /**
     * contentOnly
     * c?set=191&contentOnly=1
     */
    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Requested-With: XMLHttpRequest"})
    @GET("c")
    Observable<Filter> getContentOnly(
            @Query("set") String id,
            @Query("contentOnly") int contentOnly);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Requested-With: XMLHttpRequest"})
    @GET("c")
    Observable<Filter> getFilterBySearch(
            @Query("search") String search);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Requested-With: XMLHttpRequest"})
    @GET("c")
    Observable<Filter> getFilterTags(@Query("tags[]") List<String> tags);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Requested-With: XMLHttpRequest"})
    @GET
    Observable<Filter> getFilterTagsByUrl(@Url String url);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Requested-With: XMLHttpRequest"})
    @POST("/api/v1/user/{user_id}/firebasetoken")
    Observable<Object> sendFirebaseToken(
            @Path("user_id") int user_id,
            @Query("token") String firebaseToken,
            @Header("Authorization") String token);


    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Requested-With: XMLHttpRequest"})
    @GET("api/v1/lastVersion")
    Observable<LastVersion> getLastVersion();

}
